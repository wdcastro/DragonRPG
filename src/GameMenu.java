import java.io.File;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;




public class GameMenu {
	
	GraphicsContext context;
	private boolean isShowing = false;
	private Image image;
	private int currentlySelected = 0;
	private final int numItems = 8;
	private GameThread gamethread;
	String[] itemList = {"Party", "Item", "Craft", "Quest", "Map", "Save", "Menu", "Close"};
	
	VBox vbox;
	
	public GameMenu(GameThread gamethread){
		this.gamethread = gamethread;
		context = gamethread.getContext();
		image = new Image(new File("res/hud/menu.png").toURI().toString());
		vbox = new VBox();
		vbox.getStylesheets().add(new File("res/stylesheets/gamemenu.css").toURI().toString());
		vbox.setMinHeight(Game.SCREEN_HEIGHT);
		vbox.setMinWidth(Game.SCREEN_WIDTH*0.20);
		vbox.setSpacing(Game.SCREEN_HEIGHT/12);
		vbox.setLayoutX(0);
		vbox.setLayoutY(0);
		vbox.getStyleClass().add("pausemenu");
		Button[] buttons = generateMenuItems();
		for(int i = 0; i < buttons.length; i++){
			buttons[i].setMaxWidth(vbox.getMinWidth());
			/*buttons[i].setStyle(" -fx-border-color: transparent;" +
    "-fx-border-width: 0;" +
    "-fx-background-radius: 0;" +
    "-fx-background-color: transparent;" +
    "-fx-font-size: 20px;" + // 12 
    "-fx-text-fill: #828282;");*/
			vbox.getChildren().add(buttons[i]);
		}
		
	}

	public void draw(){
		//context.drawImage(image, 0, 0, Game.SCREEN_WIDTH*0.25, Game.SCREEN_HEIGHT);
	}
	
	public Button[] generateMenuItems(){
		Button[] buttons = new Button[8];
		buttons[0] = new Button("Party");
		buttons[0].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());
						
						currentlySelected = 0;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		buttons[1] = new Button("Items");
		buttons[1].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());

						currentlySelected = 1;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		buttons[2] = new Button("Craft");
		buttons[2].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());

						currentlySelected = 2;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		buttons[3] = new Button("Quest");
		buttons[3].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());

						currentlySelected = 3;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		buttons[4] = new Button("Map");
		buttons[4].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());

						currentlySelected = 4;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		buttons[5] = new Button("Save game");
		buttons[5].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());

						currentlySelected = 5;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		buttons[6] = new Button("Title screen");
		buttons[6].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());

						currentlySelected = 6;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		buttons[7] = new Button("Close menu");
		buttons[7].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("res/sound/buttonclick.mp3").toURI().toString());

						currentlySelected = 7;
						enterNextMenu();
						click.play();	
					}
				}).start();
			}
			
		});
		return buttons;
		
	}
	
	public void show(){
		isShowing = true;
		draw();
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				gamethread.getRootNode().getChildren().add(vbox);
				
			}
			
		});
		System.out.println("Showing menu? :" + isShowing);
		
	}
	
	public void hide(){
		isShowing = false;
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				gamethread.getRootNode().getChildren().remove(vbox);
				
			}
			
		});
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
			isShowing = false;
			hide();
			gamethread.setGameState(GameState.IN_CITY);
			break;
		case ESCAPE:
			isShowing = false;
			hide();
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
