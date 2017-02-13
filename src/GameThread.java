import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameThread extends Thread{	
	
	SystemState gameState;
	SpellManager spellmanager;
	BattleManager battlemanager;
	
	MapManager mapmanager;

	PlaylistManager playlistmanager = new PlaylistManager();

	
	boolean isRunning = true;

	
	public GameThread(GraphicsContext gc){
		spellmanager = new SpellManager();
		battlemanager = new BattleManager();
		mapmanager = new MapManager(gc);
	}
	
	public void run(){
		gameState = SystemState.IN_CITY;
		playlistmanager.start();
		mapmanager.start();
	}
	
	public void update(){
		playlistmanager.update();
		switch (gameState){
		case IN_BATTLE:
			battlemanager.update();
			//hud update
			break;
		case IN_CITY:
			//tiles.update();
			mapmanager.update();
			//should only be called when moving, unless water animations or some shit idk
			break;
		default:
			break;
		}
	}
	
	public void draw(){
		switch (gameState){
		case IN_BATTLE:
			break;
		case IN_CITY:
			//tiles.draw();
			//should only be called when moving
		case MAIN_MENU:
			break;
		case WORLD_MAP:
			break;
		default:
			break;
		}
		
	}
	
	public void handleKeyRelease(KeyEvent e){
		if(e.getCode().toString() == "DIGIT0"){
			playlistmanager.togglePause();
			return;
		} else if(e.getCode().toString() == "DIGIT9"){
			playlistmanager.nextSong();
			return;
		} else {
			switch(gameState){
			case IN_BATTLE:
				battlemanager.doKeyboardAction(e);
				break;
			case IN_CITY:
				mapmanager.doKeyboardAction(e);
			default:
				doKeyRelease(e);
				break;
			}
		}
	}
	
	public void handleMouseClick(MouseEvent e){
		switch(gameState){
		case IN_BATTLE:
			battlemanager.doMouseAction(e);
			break;
		default:
			doMouse(e);
			break;
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
		default:
			break;	
		}
	}
	
	public void doMouse(MouseEvent e){
		
	}
	
	public void stopAllThreads(){
		System.out.println("Stopping all threads");
		playlistmanager.stopMusic();		
		isRunning = false;		
	}
}
