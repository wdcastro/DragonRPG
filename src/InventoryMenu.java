import java.io.File;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;



public class InventoryMenu {
	
	BorderPane holderPane;
	TilePane detailPane;
	GridPane inventoryPane;
	
	GameThread gamethread;
	
	public InventoryMenu(GameThread gt){
		gamethread = gt;
		
		holderPane = new BorderPane();
		holderPane.setMinWidth(Game.SCREEN_WIDTH);
		holderPane.setMinHeight(Game.SCREEN_HEIGHT);
		holderPane.getStylesheets().add(new File("res/stylesheets/inventoryscreen.css").toURI().toString());
		holderPane.getStyleClass().add("holderpane");
		
		detailPane = new TilePane();
		detailPane.setMinWidth(Game.SCREEN_WIDTH*0.3);
		detailPane.getStyleClass().add("detailpane");
		
		inventoryPane = new GridPane();
		inventoryPane.setMinWidth(Game.SCREEN_WIDTH*0.65);
		inventoryPane.getStyleClass().add("inventorypane");
		
		holderPane.setLeft(inventoryPane);
		holderPane.setRight(detailPane);
		
	}
	
	public void init(){
		//for loop draw all items
	}

	public void draw() {
		// TODO Auto-generated method stub
		
	}

	public void show() {
		Platform.runLater(new Runnable(){
			public void run(){
				gamethread.getRootNode().getChildren().add(holderPane);
			}
		});
		
		
	}
	
	public void hide() {
		Platform.runLater(new Runnable(){
			public void run(){
				gamethread.getRootNode().getChildren().remove(holderPane);
			}
		});
		
	}
	
	public void handleMouseClick(MouseEvent e) {// could just do buttons
		if(e.getButton() == MouseButton.SECONDARY){
			hide();
			gamethread.setGameState(GameState.IN_CITY);
		}
	}

}
