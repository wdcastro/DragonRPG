import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameThread extends Thread{	
	
	GameState gameState;
	SpellManager spellmanager;
	BattleManager battlemanager;
	DialogManager dialogmanager;
	
	CutsceneManager cutscenemanager;
	
	GraphicsContext context;
	GameMenu gamemenu;
	
	MapManager mapmanager;
	PlayerDataManager playerdatamanager;
	PlaylistManager playlistmanager;

	HUD hud;
	PartyMenu partymenu;
	InventoryMenu inventorymenu;
	Pane basePane;
	MapAnimationController mapanimationcontroller;
	
	//private boolean isRunning = false;
	private boolean isReady = false;

	
	public GameThread(GraphicsContext gc, Group root){
		context = gc;
		context.setFill(Color.WHITE);
		basePane = new Pane();
		basePane.setMinSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		root.getChildren().add(basePane);
	}
	
	public void run(){
		//isRunning = true;
		
		//to-do: make sure not everything runs at once, set managers to null to save memory
		
		playlistmanager = new PlaylistManager();
		spellmanager = new SpellManager();
		playerdatamanager = new PlayerDataManager();
		battlemanager = new BattleManager(playerdatamanager, this);
		playlistmanager = new PlaylistManager();
		mapmanager = new MapManager(this);
		gamemenu = new GameMenu(this);
		dialogmanager = new DialogManager(this);
		mapanimationcontroller = new MapAnimationController(this);
		cutscenemanager = new CutsceneManager(this);

		
		playlistmanager.start();
		mapmanager.start();
		dialogmanager.start();
		
		while(!playlistmanager.isReady() || !mapmanager.isReady() || !dialogmanager.isReady()){ //check for game modes
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isReady = true;

		setGameState(GameState.IN_CITY);
	}
	
	synchronized public boolean isReady(){
		return isReady;
	}
	
	public void update(){
		//System.out.println(gamemenu.isDone());
		if(dialogmanager.isShowing()){
			dialogmanager.update();
		}
		playlistmanager.update();
		switch (gameState){
		case IN_BATTLE:
			battlemanager.update();
			hud.update(battlemanager.getCurrentPickTimer());
			//hud update
			break;
		case IN_CITY:
			mapmanager.update();
			mapanimationcontroller.update();
			break;
		case IN_MENU:
			break;
		case INVENTORY:
			break;
		default:
			break;
		}
	}
	
	public void draw(){
		context.fillRect(0,0,Game.SCREEN_WIDTH,Game.SCREEN_HEIGHT);
		switch (gameState){
		case IN_BATTLE:
			battlemanager.draw();
			hud.draw();
			break;
		case IN_CITY:
			mapmanager.draw();
			//should only be called when moving, unless water animations or some shit idk
			break;
		case WORLD_MAP:
			break;
		case IN_MENU:
			mapmanager.draw();
			gamemenu.draw();
			break;
		case PARTY_SCREEN:
			mapmanager.draw();
			//partymenu.draw(); //if we have animations
			break;
		case INVENTORY:
			mapmanager.draw();
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
				if(!dialogmanager.dialogbox.isShowing){
					if(e.getCode().toString()=="ENTER"){
						setGameState(GameState.IN_MENU);
					}
					if(e.getCode().toString()=="P"){
						setGameState(GameState.IN_BATTLE);
					}
					mapmanager.handleKeyRelease(e);
				}				
				break;
			case IN_MENU:
				gamemenu.handleKeyRelease(e);
				break;
			case PARTY_SCREEN:
				partymenu.handleKeyRelease(e);
				break;
			default:
				break;
			}
		}
	}
	
	public void handleMouseClick(MouseEvent e){
		if(dialogmanager.isShowing()){
			if(e.getButton() == MouseButton.PRIMARY){

				dialogmanager.forward();
			} else {
			}
		} else {
			switch(gameState){
			case IN_BATTLE:
				battlemanager.handleMouseClick(e);
				break;
			case IN_CITY:
				mapmanager.handleMouseClick(e);
				break;
			case PARTY_SCREEN:
				partymenu.handleMouseClick(e);
				break;
			case INVENTORY:
				inventorymenu.handleMouseClick(e);
				break;
			default:
				doMouse(e);
				break;
			}
		}
	}

	
	public void doMouse(MouseEvent e){
		
	}
	
	public void setGameState(GameState gs){
		gameState = gs;
		//destroy all previous objects i.e set to null for memory and cpu updating
		switch(gs){
		case IN_CITY:
			break;
		case PARTY_SCREEN:
			partymenu = new PartyMenu(this);
			partymenu.show();
			mapmanager.draw();
			break;
		case IN_MENU:
			mapmanager.draw();
			gamemenu.show();
			break;
		case IN_BATTLE:
			hud = new HUD(this, battlemanager.getPickTimerDelay());
			battlemanager.startFight();
			break;
		case INVENTORY:
			inventorymenu = new InventoryMenu(this);
			inventorymenu.show();
			break;
		default:
			break;
		}
	}
	
	public void stopAllThreads(){
		System.out.println("Stopping all threads");
		playlistmanager.stopMusic();		
		//isRunning = false;		
	}
	
	public GraphicsContext getContext(){
		return context;
	}
	
	public Pane getBasePane(){
		return basePane;
	}

	public void handleKeyPressed(KeyEvent e) {
		switch(gameState){
		case IN_BATTLE:
			break;
		case IN_CITY:
			if(!dialogmanager.dialogbox.isShowing && !cutscenemanager.isCutsceneActive()){
				mapmanager.handleKeyPressed(e);
			}
			break;
		case IN_MENU:
			break;
		case PARTY_SCREEN:
			break;
		default:
			break;
		}
		
	}
}
