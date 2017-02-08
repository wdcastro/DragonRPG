import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameThread extends Thread{
	
	final int MAX_FPS = 100;
	final int MILLIS_BETWEEN_FRAMES = 1000/MAX_FPS;

	final int MAX_DRAWS = 60;
	final int MILLIS_BETWEEN_DRAWS = 1000/MAX_DRAWS;
	
	final long MILLIS_TO_NANOS = 1000000;
	
	SystemState gameState;
	SpellManager sm;
	BattleManager bm;
	TileManager tiles;

	PlaylistManager pm = new PlaylistManager();
	
	boolean isRunning = true;

	
	public GameThread(GraphicsContext gc){
		sm = new SpellManager();
		bm = new BattleManager();		
		tiles = new TileManager(gc);		
	}
	
	public void run(){
		gameState = SystemState.IN_BATTLE;
		pm.start();
		long lastUpdateTime = System.nanoTime();
		long timeSinceLastUpdate = 0;// Time Elapsed
		long timeUntilNextUpdate = MILLIS_BETWEEN_FRAMES;
		long fpstimer = 0;
		int fpscount = 0;
		while(isRunning){
			timeSinceLastUpdate = System.nanoTime() - lastUpdateTime; // now - last update's timestamp
			timeUntilNextUpdate = MILLIS_BETWEEN_FRAMES*MILLIS_TO_NANOS-timeSinceLastUpdate;
			if(timeUntilNextUpdate <= 0){
				fpscount++;
				update();
				lastUpdateTime = System.nanoTime();
				fpstimer+=timeSinceLastUpdate;
				if(fpstimer > 1000*MILLIS_TO_NANOS){
					fpstimer = 0;
					System.out.println("FPS: "+ fpscount);
					fpscount = 0;
				}
			} else {
				try {
					Thread.sleep(timeUntilNextUpdate/MILLIS_TO_NANOS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		}
	}
	
	public void update(){
		bm.update();
	}
	
	public void handleKeyRelease(KeyEvent e){
		if(e.getCode().toString() == "DIGIT0"){
			pm.togglePause();
		} else {
			if(gameState == SystemState.IN_BATTLE){
				bm.doKeyboardAction(e);
			} else {
				doKeyRelease(e);
			}
		}
	}
	
	public void handleMouseClick(MouseEvent e){
		if(gameState == SystemState.IN_BATTLE){
			bm.doMouseAction(e);
		} else {
			doMouse(e);
		}
	}
	
	public void doKeyRelease(KeyEvent e){
		switch (e.getCode()){
		case X:
			// some other action
			break;
		case Z:
			// some other action 
			break;	
		}
	}
	
	public void doMouse(MouseEvent e){
		
	}
	
	public void stopAllThreads(){
		System.out.println("Stopping all threads");
		pm.stopMusic();		
		isRunning = false;		
	}
}
