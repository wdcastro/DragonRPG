import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;


public class MapManager extends Thread{
	
	private int playerx;
	private int playery;
	private String levelName;
	
	private int camL = 0;
	private int camR = 40;
	private int camU = 0;
	private int camD = 21;
	
	private boolean isReady = false;
	
	private boolean useCollision = true;
	
	MapLocation currentLocation = MapLocation.TEST_CITY;
	TileManager tilemanager;
	
	public MapManager(GraphicsContext gc){
		tilemanager = new TileManager(gc);
	}
	
	synchronized public boolean isReady(){
		return isReady;
	}
	
	public void run(){
		loadCity();
		playerx = tilemanager.getInitialSpawnX();
		playery = tilemanager.getInitialSpawnY();
		if(playerx<tilemanager.getTilesPerRow()/2){ // hitting left boundary
			camL = 0;
			camR = tilemanager.getTilesPerRow();
		} else if (playerx+(tilemanager.getTilesPerRow()/2) > tilemanager.getMapWidth()){ // hitting right boundary
			camR = tilemanager.getMapWidth();
			camL = camR - tilemanager.getTilesPerRow();	
		} else { //hitting neither
			camL = playerx - tilemanager.getTilesPerRow()/2;
			camR = (playerx + tilemanager.getTilesPerRow()/2)-1;
		}
		if(playery<tilemanager.getTilesPerColumn()/2){ // hitting up boundary
			camU = 0;
			camD = tilemanager.getTilesPerColumn();
		} else if (playery+(tilemanager.getTilesPerColumn()/2) > tilemanager.getMapHeight()){ // hitting bottom boundary
			camD = tilemanager.getMapHeight();
			camU = camR - tilemanager.getTilesPerColumn();	
		} else { //hitting neither
			camU = playerx - tilemanager.getTilesPerColumn()/2;
			camD = (playerx + tilemanager.getTilesPerColumn()/2)-1;
		}
		
		isReady = true;
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
		tilemanager.drawPlayer(playerx,playery,camU, camL);
	}
	
	public void moveDown(){
		if(useCollision){
			if(!tilemanager.checkCollision(playerx, playery+1)){
				if(camD!=tilemanager.getMapHeight() && playery>(camD+camU)/2){
					camD++;
					camU++;
				}
				if(playery<camD-1){
					playery++;
				}
			}
		}
	}
	
	public void moveUp(){
		if(useCollision){
			if(!tilemanager.checkCollision(playerx, playery-1)){
				if(camU!=0 && playery<(camU+camD)/2){
					camU--;
					camD--;
				}
				if(playery>0){
					playery--;
				}
			}
		}
		
	}
	
	public void moveLeft(){
		if(useCollision){
			if(!tilemanager.checkCollision(playerx-1, playery)){
				if(camL!=0 && playerx<(camR+camL)/2){
					camL--;
					camR--;
				}
				if(playerx>0){
					playerx--;
				}
			}
		}
	}
	
	public void moveRight(){
		if(useCollision){
			if(!tilemanager.checkCollision(playerx, playery+1)){
				if(camR!=tilemanager.getMapWidth() && playerx>(camR+camL)/2){
					camL++;
					camR++;
				}
				if(playerx<camR-1){
					playerx++;
				}
			}
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
		case F:
			System.out.println("camL :"+camL);

			System.out.println("camR :"+camR);

			System.out.println("camU :"+camU);

			System.out.println("camD :"+camD);

			System.out.println("playerx :"+playerx);

			System.out.println("playery :"+playery);
			break;
		default:
			break;	
		}
		draw();
	}
	

}
