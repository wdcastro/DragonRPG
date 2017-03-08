import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class MoveSelectScreen {
	/*
	 * TODO: limit party size casters
	 */
	
	ArrayList<String> spellqueue;
	//ArrayList<Character> casterqueue = new ArrayList<Character>();
	
	int[] spellintegers;
	Character[] currentparty;
	GameThread gamethread;
	
	int currentCaster;
	int numCasters;
	boolean isPicking;
	
	Button spell1;
	Button spell2;
	Button spell3;
	Button spell4;
	
	VBox vbox;


	public MoveSelectScreen(Character[] party, GameThread gamethread){
		this.gamethread = gamethread;
		currentparty = new Character[4];
		numCasters = 0;
		for(int i = 0; i < party.length; i++){
			if(party[i] == null){
				System.out.println(i+" is null");
				continue;
			}
			currentparty[numCasters] = party[i];
			numCasters++;
			System.out.println(party[i].getName());
		}
		
		vbox = new VBox();
		vbox.setMinHeight(Game.SCREEN_HEIGHT*0.50);
		vbox.setMinWidth(Game.SCREEN_WIDTH*0.30);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setSpacing(10);
		vbox.setLayoutY(Game.SCREEN_HEIGHT*0.10);
		vbox.setStyle(//"-fx-padding: 10;" + 
                 "-fx-border-style: solid inside;" + 
                 "-fx-border-width: 2;" +
                 "-fx-border-insets: 5;" + 
                 "-fx-border-radius: 5;" + 
                 "-fx-border-color: blue;");
                 
		
		System.out.println("numCasters is "+ numCasters);
		//numMaxSpells = get currentChar character from character array and then get the number of max spells from that - this might lag. if it does, preload all values
		//there needs to be a player array somewhere
		//there needs to be a spell array for each player that is accessed with currentSpell
	}
	

	
	public void initialize(){
		// get number of spells, number of characters
	}
	
	//public void reset?
	
	
	public void startPick(){
		currentCaster = 0;	
		spellintegers = new int[numCasters];
		isPicking = true;
		spellqueue = new ArrayList<String>();
		show();
	}
	
	public void finishPick(){
		//turn all numbers into spells and characters so you end up saving time when undoing
		System.out.println("finished pick");
		isPicking = false;
		hide();
		for(int i = 0; i < spellintegers.length; i++){
			System.out.println(currentparty[i].getName());
			spellqueue.add((currentparty[i].getEquipment())[0].getSpell(spellintegers[i])); // party's character's equipment's spell
			System.out.println(currentparty[i].getEquipment()[0].getSpell(spellintegers[i]));
		}
		spellintegers = null;
	}
	
	public void handleMouseClick(MouseEvent e){
		System.out.println("mouse click");
		if(e.getButton() == MouseButton.PRIMARY){
			//finish picking, give arrays to battle manager
			finishPick();
		} else if (e.getButton() == MouseButton.SECONDARY){
			//undo pick, maybe move this to its own method
			if(currentCaster > 0){
				currentCaster--;
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
		spell1 = new Button(currentparty[currentCaster].getSpells()[0]);
		spell1.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(0);

			}
			
		});
		spell2 = new Button(currentparty[currentCaster].getSpells()[1]);
		spell2.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(1);

			}
			
		});
		spell3 = new Button(currentparty[currentCaster].getSpells()[2]);
		spell3.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(2);

			}
			
		});
		spell4 = new Button(currentparty[currentCaster].getSpells()[3]);
		spell4.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				pickSpell(3);

			}
			
		});

		spell1.setMaxWidth(Game.SCREEN_WIDTH*0.10);
		spell1.setLayoutX(250);
		spell2.setMaxWidth(Game.SCREEN_WIDTH*0.10);
		spell3.setMaxWidth(Game.SCREEN_WIDTH*0.10);
		spell4.setMaxWidth(Game.SCREEN_WIDTH*0.10);
		
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
		vbox.getChildren().remove(spell1);
		vbox.getChildren().remove(spell2);
		vbox.getChildren().remove(spell3);
		vbox.getChildren().remove(spell4);
		
		
	}
	
	public void drawButtons(){
		vbox.getChildren().add(spell1);
		vbox.getChildren().add(spell2);
		vbox.getChildren().add(spell3);
		vbox.getChildren().add(spell4);
		
		
		
	}
	
	public ArrayList<String> getSpellQueue(){
		return spellqueue;
	}
	
	public void pickSpell(int spell){
		System.out.println("currentCaster: "+ currentCaster +" spell is "+spell);
		spellintegers[currentCaster] = spell;
		if(currentCaster != numCasters -1){
			currentCaster++;
		}
		hide();
		show();
	}
	


}
