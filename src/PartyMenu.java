import java.io.File;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;



public class PartyMenu {
	
	BorderPane holderPane;
	VBox partyPane;
	Pane detailPane;
	StackPane selectionPane;
	boolean selectionMode = false;
	
	GameThread gamethread;
	
	public PartyMenu(GameThread gt){
		gamethread = gt;
		
		holderPane = new BorderPane();
		holderPane.setMinWidth(Game.SCREEN_WIDTH);
		holderPane.setMinHeight(Game.SCREEN_HEIGHT);
		holderPane.getStylesheets().add(new File("res/stylesheets/partyscreen.css").toURI().toString());
		holderPane.getStyleClass().add("holderpane");
		
		partyPane = new VBox();
		//partyPane.setHgap(10);
		//partyPane.setPrefColumns(1);
		//partyPane.setPrefRows(4);
		partyPane.setMinWidth(Game.SCREEN_WIDTH*0.2);
		partyPane.setMinHeight(Game.SCREEN_HEIGHT*0.8);
		partyPane.setMaxWidth(Game.SCREEN_WIDTH*0.2);
		partyPane.setMaxHeight(Game.SCREEN_HEIGHT*0.8);
		//partyPane.setLayoutX(0);
		//partyPane.setLayoutY(0);
		partyPane.getStyleClass().add("partypane");
		
		detailPane = new Pane();
		detailPane.setMinWidth(Game.SCREEN_WIDTH*0.7);
		detailPane.setMinHeight(Game.SCREEN_HEIGHT*0.8);
		//detailPane.setLayoutX(Game.SCREEN_WIDTH*0.2);
		//detailPane.setLayoutY(0);
		detailPane.getStyleClass().add("detailpane");
		
		holderPane.setRight(detailPane);
		holderPane.setLeft(partyPane);
		
		Label player1 = new Label(gamethread.playerdatamanager.getParty()[1].getName());
		player1.setMinWidth(partyPane.getWidth());
		player1.getStyleClass().add("playerdetails");
		
		Label player2 = new Label(gamethread.playerdatamanager.getParty()[2].getName());
		player2.getStyleClass().add("playerdetails");
		player2.setMinWidth(partyPane.getWidth());
		
		Label player3 = new Label("EMPTY");
		player3.getStyleClass().add("emptyplayerdetails");
		player3.setMinWidth(partyPane.getWidth());
		
		Label player4 = new Label("EMPTY");
		player4.getStyleClass().add("emptyplayerdetails");
		player4.setMinWidth(partyPane.getWidth());
		/*GridPane.setConstraints(player1, 1,1);
		GridPane.setConstraints(player2, 1,2);
		GridPane.setConstraints(player3, 1,3);
		GridPane.setConstraints(player4, 1,4);
		*/
		partyPane.getChildren().addAll(player1, player2, player3 ,player4);
		
		
	}

	public void draw() {
		// TODO Auto-generated method stub
		
	}

	public void show() {
		Platform.runLater(new Runnable(){
			public void run(){
				gamethread.getBasePane().getChildren().add(holderPane);
			}
		});
		
		
	}
	
	public void hide() {
		Platform.runLater(new Runnable(){
			public void run(){
				gamethread.getBasePane().getChildren().remove(holderPane);
			}
		});
		
	}
	
	public void handleMouseClick(MouseEvent e) {// could just do buttons
		if(e.getButton() == MouseButton.SECONDARY){
			hide();
			gamethread.setGameState(GameState.IN_CITY);
		}
		if(selectionMode){
			
		} else {
			
		}
		
	}

	public void handleKeyRelease(KeyEvent e) {
		if(e.getCode().toString()=="ENTER" || e.getCode().toString()=="ESCAPE"){
			hide();
			gamethread.setGameState(GameState.IN_CITY);
		}

		
	}

}
