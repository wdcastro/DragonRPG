import java.io.File;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class MoveSelectScreen {

	/*
	 * 
	 */
	String[] spellqueue;
	
	Character[] currentparty;
	GameThread gamethread;
	
	int currentCaster = -1;
	int firstCaster = -1;
	int numCasters;
	boolean isPicking;
	
	Image image;
	
	Button spell0;
	Button spell1;
	Button spell2;
	Button spell3;
	
	VBox vbox;


	public MoveSelectScreen(Character[] party, GameThread gamethread){
		this.gamethread = gamethread;
		currentparty = party;
		
		initialize();
		
		
		vbox = new VBox();
		vbox.getStylesheets().add(new File("res/stylesheets/moveselectscreen.css").toURI().toString());
		vbox.setMinHeight(Game.SCREEN_HEIGHT*0.50);
		vbox.setMinWidth(Game.SCREEN_WIDTH*0.30);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setSpacing(10);
		vbox.setLayoutY(Game.SCREEN_HEIGHT*0.10);
		vbox.getStyleClass().add("moveselectbox");
                 
		
		System.out.println("numCasters is "+ numCasters);
		//numMaxSpells = get currentChar character from character array and then get the number of max spells from that - this might lag. if it does, preload all values
		//there needs to be a player array somewhere
		//there needs to be a spell array for each player that is accessed with currentSpell
	}
	
	public void findFirstCaster(){
		for(int i = 0; i < currentparty.length; i++){
			if(currentparty[i]!=null){
				if(firstCaster == -1){
					firstCaster = i;
				}
				//firstCaster = i;
				numCasters++;
			}
		}
	}
	
	public void gotoFirstCaster(){
		currentCaster = firstCaster;
	}
	
	public void nextCaster(){
		for(int i = currentCaster; i< currentparty.length; i++){
			if(currentparty[i]!=null){
				currentCaster = i;
			}
		}
	}
	
	public void previousCaster(){
		for(int i = currentCaster; i>=0; i--){
			if(currentparty[i]!=null){
				currentCaster = i;
			}
		}
	}
	
	public void initialize(){
		// get number of spells, number of characters
		findFirstCaster();
		image = new Image(new File("res/misc/dragoon chibi.png").toURI().toString());
	}
	
	//public void reset?
	
	
	public void startPick(){
		//currentCaster = 0;
		gotoFirstCaster();
		isPicking = true;
		spellqueue = new String[4];
		show();
	}
	
	public void finishPick(){
		//turn all numbers into spells and characters so you end up saving time when undoing
		System.out.println("finished pick");
		isPicking = false;
		hide();
	}
	
	public void handleMouseClick(MouseEvent e){
		System.out.println("mouse click");
		if(e.getButton() == MouseButton.PRIMARY){
			//finish picking, give arrays to battle manager
			finishPick();
		} else if (e.getButton() == MouseButton.SECONDARY){
			//undo pick, maybe move this to its own method
			if(currentCaster > 0){
				previousCaster();
				hide();
				show();
				//graphic changes, theres bound to be animation anwyay so maybe put it in update
			} else {
				//play cannot sound
			}
		}
	}
	
	public void handleKeyRelease(KeyEvent e){
		/*
		 * on wasd, change current highlighted
		 * currentHighlighted = player.equipment.getSpell(int)
		 * on x
		 */
		switch(e.getCode()){
		case Q:
			pickSpell(0);
			break;
		case W:
			pickSpell(1);
			break;
		case E:
			pickSpell(2);
			break;
		case R:
			pickSpell(3);
			break;
		default:
			break;
		}

	}
	
	public void setButtons(){		
		//todo set sound effects for clicks
		spell0 = new Button(currentparty[currentCaster].getSpells()[0]);
		
		spell0.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(0);

			}
			
		});
		spell1 = new Button(currentparty[currentCaster].getSpells()[1]);
		spell1.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(1);

			}
			
		});
		spell2 = new Button(currentparty[currentCaster].getSpells()[2]);
		spell2.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(2);

			}
			
		});
		spell3 = new Button(currentparty[currentCaster].getSpells()[3]);
		spell3.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(3);

			}
			
		});
		
		spell0.setMaxWidth(Game.SCREEN_WIDTH*0.25);
		spell1.setMaxWidth(Game.SCREEN_WIDTH*0.25);
		spell2.setMaxWidth(Game.SCREEN_WIDTH*0.25);
		spell3.setMaxWidth(Game.SCREEN_WIDTH*0.25);
		
		spell0.setGraphic(new ImageView(image));
	}
	
	public void show(){
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				setButtons();
				drawButtons();
				gamethread.getRootNode().getChildren().add(vbox);
			}
			
		});
		
	}
	
	public void hide(){
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				gamethread.getRootNode().getChildren().remove(vbox);
				removeButtons();
				
			}
			
		});
	}
	

	public void removeButtons() {
		vbox.getChildren().remove(spell0);
		vbox.getChildren().remove(spell1);
		vbox.getChildren().remove(spell2);
		vbox.getChildren().remove(spell3);
		
		
	}
	
	public void drawButtons(){
		vbox.getChildren().add(spell0);
		vbox.getChildren().add(spell1);
		vbox.getChildren().add(spell2);
		vbox.getChildren().add(spell3);
		
		
		
	}
	
	public String[] getSpellQueue(){
		return spellqueue;
	}
	
	public void pickSpell(int spell){
		System.out.println("currentCaster: "+ currentCaster +" spell is "+spell);
		spellqueue[currentCaster] = currentparty[currentCaster].getSpells()[spell];
		nextCaster();
		hide();
		show();
	}
	
	public int getCurrentCaster(){
		return currentCaster;
	}


}
