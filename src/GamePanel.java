import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class GamePanel extends JFrame implements KeyListener, MouseListener, FocusListener {
	
	boolean xDown = false;
	GameThread gamethread;
	TileManager tiles = new TileManager();

	
	public GamePanel(){
		this.setSize(Game.SCREEN_WIDTH,Game.SCREEN_HEIGHT);
		this.setTitle("Amazing RPG Simulator 2017");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(tiles);
		addFocusListener(this);
		addKeyListener(this);
		addMouseListener(this);
		this.setVisible(true);
		gamethread = new GameThread();
		gamethread.run();
	}
	
	public void update(){		
		gamethread.update();
	}
	
	//--------------------------------------------------------------
	// Code for input listening
	//--------------------------------------------------------------
	
	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("is on EDT " + SwingUtilities.isEventDispatchThread());
		System.out.println("Focus gained");		
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("is on EDT " + SwingUtilities.isEventDispatchThread());
		System.out.println("Focus lost");
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {	
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
		gamethread.handleMouseClick(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gamethread.handleKeyRelease(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
