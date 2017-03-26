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
	
	public MainMenu(){


		bgimage = new Image(new File("res/background/background.png").toURI().toString());
		
	}
	
	public void cropImage(){
		
	}
	
	public void determineMenuItemValues(){
		
		
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
