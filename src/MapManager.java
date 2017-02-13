import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;


public class MapManager extends Thread{
	
	private int playerx;
	private int playery;
	private String levelName;
	private int camerayscope;
	private int cameraxscope;
	
	private int camL = 0;
	private int camR = 40;
	private int camU = 0;
	private int camD = 21;
	
	MapLocation currentLocation = MapLocation.TEST_CITY;
	TileManager tilemanager;
	
	public MapManager(GraphicsContext gc){
		tilemanager = new TileManager(gc);
		playerx = 21;
		playery = 10;
		cameraxscope = 40;
		camerayscope = 21;

	}
	
	public void run(){
		loadCity();
		draw();
	}

	public void loadCity(){
		switch(currentLocation){
		case TEST_CITY:
			tilemanager.loadTileMap("untitled");
			break;
		default:
			break;
		}
	}
	
	public void update(){
		tilemanager.update();
		//System.out.println("x: "+ playerx+" y: "+playery);
		//System.out.println("left: "+unitsLeft+" right: "+unitsRight+" up: "+unitsUp+" down: "+unitsDown);
	}
	
	public void draw(){
		tilemanager.drawAllLayersTrim(camU,camL);
		tilemanager.drawPlayer(playerx,playery,camU,camL);
	}
	
	public void moveDown(){
		playery++;
		if(playerx>tilemanager.getMapHeight()){
			playerx = tilemanager.getMapHeight();
		}
		if(playery-camU<Game.SCREEN_HEIGHT/64){
			System.out.println("player not centered, not moving cam");
			return;
		}
		if(camD==tilemanager.getMapHeight()){
			System.out.println("collision on down");
			return;
		} else {
			System.out.println("no collision on down, moving camera");
			camU++;
			camD++;
		}
	}
	
	public void moveUp(){
		playery--;
		if(playery<0){
			playery=0;
		}
		if(camD-playery<Game.SCREEN_HEIGHT/64){
			System.out.println("player not centered, not moving cam");
			return;
		}
		if(camU==0){
			System.out.println("collision on up");
			return;
		} else {
			System.out.println("no collision on right, moving camera");
			camU--;
			camD--;
		}
	}
	
	public void moveLeft(){
		playerx--;
		if(playerx<0){
			playerx = 0;
		}
		if(camR-playerx<Game.SCREEN_WIDTH/64){
			System.out.println("player not centered, not moving cam");
			return;
		}
		if(camL==0){
			System.out.println("collision on left");
			return;
		} else {
			System.out.println("no collision on left, moving camera");
			camR--;
			camL--;
		}
	}
	
	public void moveRight(){
		playerx++;
		if(playerx>tilemanager.getMapWidth()){
			playerx = tilemanager.getMapWidth();
		}
		if(playerx-camL<Game.SCREEN_WIDTH/64){
			System.out.println("player not centered, not moving cam");
			return;
		}
		if(camR==tilemanager.getMapWidth()){
			System.out.println("collision on right");
			return;
		} else {
			System.out.println("no collision on right, moving camera");
			camR++;
			camL++;
		}
	}
	
	public void doKeyboardAction(KeyEvent e){ // will have to do boolean isAnimating and isKeyDown to constantly update char while held
		switch (e.getCode()){
		case W:
			// some other action
			moveUp();
			break;
		case A:
			// some other action 
			moveLeft();
			break;	
		case S:
			moveDown();
			// some other action 
			break;	
		case D:
			moveRight();
			// some other action 
			break;
		default:
			break;	
		}
		draw();
	}
	

}
