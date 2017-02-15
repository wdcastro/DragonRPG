import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;


public class MainMenu {
	
	int currentBGFrame = 1;
	Image bgimage;
	String bgimageStr = "background.png";
		
	public MainMenu(){
		bgimage = new Image(new File(bgimageStr).toURI().toString());
	}
	
	public void cropImage(){
		
	}
	
	public void determineMenuItemValues(){
		
		
	}
	
	public Button[] generateMenuItems(){
		Button[] buttons = new Button[4];
		buttons[0] = new Button("New Game");
		buttons[0].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("buttonclick.mp3").toURI().toString());
						Game.hasClicked = true;
						Game.updateGameState(SystemState.IN_GAME);
						click.play();	
						// Start new game
					}
				}).start();
			}
			
		});
		buttons[1] = new Button("Load Game");
		buttons[1].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("buttonclick.mp3").toURI().toString());
						Game.hasClicked = true;
						click.play();	
						// Go to load game screen
					}
				}).start();
			}
			
		});
		buttons[2] = new Button("Settings");
		buttons[2].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("buttonclick.mp3").toURI().toString());
						Game.hasClicked = true;
						click.play();	
						// Go to settings screen
					}
				}).start();
			}
			
		});
		buttons[3] = new Button("Exit Game");
		buttons[3].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						System.exit(0);	
						// Start new game
					}
				}).start();
			}
			
		});
		return buttons;
		
	}

	public void drawBackgroundImage(GraphicsContext gc) {
		gc.drawImage(bgimage,0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		/*gc.drawImage(bgimage,320*currentBGFrame, 320*currentBGFrame,500,500, 0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		currentBGFrame++;
		if(currentBGFrame==4){
			currentBGFrame = 0;
		}*/
	}
}
