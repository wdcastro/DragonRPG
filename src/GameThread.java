import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameThread extends Thread{
	SystemState gameState;
	SpellManager sm;
	BattleManager bm;
	TileManager tiles;

	MusicPlayback mp = new MusicPlayback();
	
	
	
	boolean isRunning = true;

	
	public GameThread(GraphicsContext gc){
		
		sm = new SpellManager();
		bm = new BattleManager();		
		tiles = new TileManager(gc);
		
	}
	
	public void run(){
		gameState = SystemState.IN_BATTLE;
		mp.start();
		while(isRunning){
			update();
		}
	}
	
	public void update(){
		bm.update();
	}
	
	public void handleKeyRelease(KeyEvent e){
		//System.out.println("keyrelease in thread reached");
		//if(e.getKeyCode() == KeyEvent.VK_0){
		//	System.out.println("about to toggle pause");
		//	mp.togglePause();
		//} else {
			if(gameState == SystemState.IN_BATTLE){
				bm.doKeyboardAction(e);
			} else {
				doKeyRelease(e);
			}
		//}
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
		mp.stopMusic();		
		isRunning = false;		
	}
}
