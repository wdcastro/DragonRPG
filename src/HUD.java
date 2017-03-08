import java.io.File;

import javafx.scene.image.Image;


public class HUD {
	
	boolean isShowing = false;
	
	float p1hpx;
	float p1hpy;
	float p2hpx;
	float p2hpy;
	float p3hpx;
	float p3hpy;
	float p4hpx;
	float p4hpy;
	float timerx1;
	float timerx2;
	float timerprogress;
	float timery;
	
	long pickTimerDelay;
	GameThread gamethread;
	Image timerbar;
	
	
	public HUD(GameThread gamethread, long pickTimerDelay){
		this.gamethread = gamethread;
		this.pickTimerDelay = pickTimerDelay;
		System.out.println("pickTimerDelay: "+pickTimerDelay);
		timerbar = new Image(new File("timer bar.png").toURI().toString());
		timerx1 = Game.SCREEN_WIDTH/10;
		timerx2 = Game.SCREEN_WIDTH*8/10;
		System.out.println("timerx2: "+timerx2);
		timery = Game.SCREEN_HEIGHT/10;
	}
	
	public void update(long nextPickTimer){

		if(nextPickTimer>pickTimerDelay){
			nextPickTimer = pickTimerDelay;

		}
		timerprogress = timerx2*((float)(nextPickTimer/Game.MILLIS_TO_NANOS))/((float)(pickTimerDelay/Game.MILLIS_TO_NANOS));

	}
	
	public void draw(){
		gamethread.getContext().drawImage(timerbar, timerx1, timery, timerprogress, 5);// instead of 5 should be the height of the bar
	}
}
