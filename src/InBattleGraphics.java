import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class InBattleGraphics {

	Image image;
	GraphicsContext context;
	
	public InBattleGraphics(GameThread gamethread){
		image = new Image(new File("karate guy.png").toURI().toString());
		context = gamethread.getContext();
	}
	
	public void draw(){
		//draw background
		//draw enemy
		//loop through party, find who to draw
		context.drawImage(image, Game.SCREEN_WIDTH/2, Game.SCREEN_HEIGHT * 0.25);
	}
}
