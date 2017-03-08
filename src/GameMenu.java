import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;




public class GameMenu {
	
	GraphicsContext context;
	private boolean isShowing = false;
	private Image image;
	private int currentlySelected = 0;
	private final int numItems = 8;
	private GameThread gamethread;
	String[] itemList = {"Party", "Item", "Craft", "Quest", "Map", "Save", "Menu", "Close"};
	
	public GameMenu(GameThread gamethread){
		this.gamethread = gamethread;
		context = gamethread.getContext();
		image = new Image(new File("menu.png").toURI().toString());
		
	}

	public void draw(){
		context.drawImage(image, 0, 0, Game.SCREEN_WIDTH*0.25, Game.SCREEN_HEIGHT);
	}
	

	
	public void show(){
		isShowing = true;
		draw();
		System.out.println("Showing menu? :" + isShowing);
		
	}
	
	public void hide(){
		isShowing = false;
		System.out.println("Showing menu? :" + isShowing);
	}
	
	public void selectNext(){
		currentlySelected++;
		if(currentlySelected > numItems-1){
			currentlySelected = 0;
		}
		System.out.println("currentlySelected: "+itemList[currentlySelected]);
	}
	
	public void selectPrev(){
		currentlySelected--;
		if(currentlySelected < 0){
			currentlySelected = numItems-1;
		}
		System.out.println("currentlySelected: "+itemList[currentlySelected]);
	}
	
	public boolean isShowing(){
		return isShowing;
	}
	
	public void handleKeyRelease(KeyEvent e){
		switch(e.getCode()){
		case W:
			selectPrev();
			break;
		case S:
			selectNext();
			break;
		case ENTER:
			enterNextMenu();
			break;
		case ESCAPE:
			isShowing = false;
			gamethread.setGameState(GameState.IN_CITY);
			break;
		default:
			break;	
		}
	}

	
	public void enterNextMenu(){
		System.out.println("inside enterNextMenu()");
		System.out.println("currentlySelected: "+ currentlySelected);
		switch (currentlySelected){
		case 0:
			System.out.println("Enter party screen");
			hide();
			gamethread.setGameState(GameState.PARTY_SCREEN);
			break;
		case 1:
			System.out.println("Enter item screen");
			break;
		case 2:
			System.out.println("Enter craft screen");
			break;
		case 3:
			System.out.println("Enter quest screen");
			break;
		case 4:
			System.out.println("Enter map screen");
			break;
		case 5:
			System.out.println("Enter save screen");
			break;
		case 6:
			System.out.println("Back to main menu");
			break;
		case 7:
			System.out.println("Close dialogue");
			isShowing = false;
			gamethread.setGameState(GameState.IN_CITY);
			break;
		default:
			break;
		}
	}
	

}
