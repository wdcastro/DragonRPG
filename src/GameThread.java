import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class GameThread extends Thread{

	SystemState gameState = SystemState.IN_BATTLE;
	// currently highlighted
	SpellManager sm = new SpellManager();

	BattleManager bm = new BattleManager();
	
	public GameThread(){
		
	}
	
	public void run(){
		
	}
	
	public void update(){
		bm.update();
	}
	
	public void handleKeyRelease(KeyEvent e){
		if(gameState == SystemState.IN_BATTLE){
			bm.doKeyboardAction(e);
		} else {
			doKeyRelease(e);
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
		switch (e.getKeyCode()){
		case KeyEvent.VK_X:
			// some other action
			break;
		case KeyEvent.VK_Z:
			// some other action 
			break;	
		}
	}
	
	public void doMouse(MouseEvent e){
		
	}
}
