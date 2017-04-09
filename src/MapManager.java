import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class MapManager extends Thread{
	
	private int playerx;
	private int playery;
	GameThread gt;
	
	private int camL = 0;
	private int camR = 40;
	private int camU = 0;
	private int camD = 21;
	
	private boolean isReady = false;
	private boolean[] isDown = {false,false,false,false};
	
	public float movementTimer = 180;
	public final float movementCooldown = 180;
	


	MapLocation currentLocation = MapLocation.TEST_CITY;
	TileManager tilemanager;
	
	public MapManager(GameThread gamethread){
		gt = gamethread;
		tilemanager = new TileManager(gamethread);

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
		
		//check for cutscene
		if(gt.cutscenemanager.isCutscenePending()){
			gt.cutscenemanager.startCutscene();
		}
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
		
		if(movementTimer >= movementCooldown && !gt.mapanimationcontroller.isAnimating && !gt.cutscenemanager.isCutsceneActive() && !gt.dialogmanager.dialogbox.isShowing){
			if(isDown[0] == true){
				moveUp();
			}
			if(isDown[1] == true){
				moveLeft();
			}
			if(isDown[2] == true){
				moveDown();
			}
			if(isDown[3] == true){
				moveRight();
				
			}
			movementTimer = 0;
		} else {
			movementTimer += Game.delta_time/Game.MILLIS_TO_NANOS;
		}
	}
	
	public void draw(){
		tilemanager.drawAllLayersTrim(camU,camL);
		tilemanager.drawPlayer(playerx,playery,camU, camL);
	}
	
	public void moveDown(){
			if(!tilemanager.checkCollision(playerx, playery+1)){
				if(camD!=tilemanager.getMapHeight()-1 && playery>(camD+camU)/2){
					camD++;
					camU++;
				}
				if(playery<camD-1){
					playery++;
				}
			}
			if(gt.mapanimationcontroller.isAnimating){
				new Thread(){
					public void run(){
						try {
							this.sleep((long) movementCooldown);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						gt.mapanimationcontroller.animationCallback();
					}
				}.start();
			}
	}
	
	public void moveUp(){
			if(!tilemanager.checkCollision(playerx, playery-1)){
				if(camU!=0 && playery<(camU+camD)/2){
					camU--;
					camD--;
				}
				if(playery>0){
					playery--;
				}
			}
			if(gt.mapanimationcontroller.isAnimating){
				new Thread(){
					public void run(){
						try {
							this.sleep((long) movementCooldown);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						gt.mapanimationcontroller.animationCallback();
					}
				}.start();
			}
	}
	
	public void moveLeft(){
			if(!tilemanager.checkCollision(playerx-1, playery)){
				if(camL!=0 && playerx<(camR+camL)/2){
					camL--;
					camR--;
				}
				if(playerx>0){
					playerx--;
				}
			}
			if(gt.mapanimationcontroller.isAnimating){
				new Thread(){
				public void run(){
					try {
						this.sleep((long) movementCooldown);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gt.mapanimationcontroller.animationCallback();
				}
			}.start();
			}
	}
	
	public void moveRight(){
			if(!tilemanager.checkCollision(playerx+1, playery)){
				if(camR!=tilemanager.getMapWidth()-1 && playerx>(camR+camL)/2){
					camL++;
					camR++;
				}
				if(playerx<camR-1){
					playerx++;
				}
			}
			if(gt.mapanimationcontroller.isAnimating){
				new Thread(){
					public void run(){
						try {
							this.sleep((long) movementCooldown);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						gt.mapanimationcontroller.animationCallback();
					}
				}.start();
			}
	}
	
	public void handleKeyRelease(KeyEvent e){ // will have to do boolean isAnimating and isKeyDown to constantly update char while held
			switch (e.getCode()){
			case W:
				isDown[0] = false;
				break;
			case A:
				isDown[1] = false;
				break;	
			case S:
				isDown[2] = false;
				break;	
			case D:
				isDown[3] = false;
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
	
	public void handleKeyPressed(KeyEvent e){ // will have to do boolean isAnimating and isKeyDown to constantly update char while held
		switch (e.getCode()){
		case W:

			isDown[0] = true;
			break;
		case A:

			isDown[1] = true;
			break;	
		case S:

			isDown[2] = true;
			break;	
		case D:

			isDown[3] = true;
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

	public void handleMouseClick(MouseEvent e) {
		if (e.getButton() == MouseButton.PRIMARY){
			
			// 0 - tile width; x is 0; tile width - tilewidth * 2; x is 1; tile width*2 - tilewidth *3; x is 2;
			double mousex = e.getX();
			double mousey = e.getY();
			double tilex = Math.floor(mousex/tilemanager.getTileWidth());
			double tiley = Math.floor(mousey/tilemanager.getTileHeight());
			//use this to access array location
			System.out.println("("+tilex+","+tiley+")");
			if ((camU + tiley) == playery && (camL+tilex)==playerx){
				gt.dialogmanager.addLines("This is me");
				gt.dialogmanager.showDialogBox(false);
			} else {
				int arraynumber = (int) (((camU + tiley) * tilemanager.getMapWidth()) + (camL+tilex));
				System.out.println("arraynumber to access is: "+arraynumber);
				Interactable item = tilemanager.getInteractables()[arraynumber];
				
				if (item != null){
					if(Math.abs(playerx-(camL+tilex)) <= 1 && Math.abs(playery-(camU+tiley)) <= 1){
						System.out.println("player is adjacent");
						item.nearInteract();
						gt.dialogmanager.addLines(item.getInteractText());

						gt.dialogmanager.showDialogBox(false);
					}
					else {
						item.farInteract();
						gt.dialogmanager.addLines(item.getInteractText());
						gt.dialogmanager.showDialogBox(false);
					}
				} else {
					System.out.println("item is null");
				}
			}
			
		}
		else if (e.getButton() == MouseButton.SECONDARY){
			gt.dialogmanager.printLines();
		}
		
	}

	

}
