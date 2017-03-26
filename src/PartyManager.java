import javafx.scene.input.KeyEvent;

public class PartyManager {
	
	BlackDragon black = new BlackDragon("Iltas", 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 100);
	WhiteDragon white = new WhiteDragon("Asthea", 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000);
	Character[] party = new Character[4];
	int currentChar = 0;
	
	
	boolean isShowing = false;
	GameThread gamethread;
	
	public PartyManager(GameThread gamethread){
		this.gamethread = gamethread;
		party[2] = black;
		party[1] = white;

	}
	
	public void addToParty(Character character, int position){		
		party[position] = character;		
	}
	
	public void removeFromParty(int position){
		party[position] = null;
	}
	
	public Character[] getParty(){
		return party;		 
	}
	
	public void draw(){//TODO: PARTY CONTROL MENU
		//paint player portraits
		//paint player names
		//paint spell list
	}
	
	public void show(){
		isShowing = true;
		draw();
	}
	
	public void hide(){
		isShowing = false;
	}
	
	public void handleKeyRelease(KeyEvent e){
		switch(e.getCode()){
		case A:
			break;
		case D:
			break;
		case ESCAPE:
			hide();
			gamethread.setGameState(GameState.IN_MENU);
		default:
			break;
		}
	}

}
