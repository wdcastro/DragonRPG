import java.util.ArrayList;

public class PlayerDataManager {
	
	BlackDragon black = new BlackDragon("Iltas", 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 100);
	WhiteDragon white = new WhiteDragon("Asthea", 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000);
	Character[] party = new Character[4];
	public static ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>(10);
	InventoryItem potion = new InventoryItem("Potion", 10);

	InventoryItem herb = new InventoryItem("Herb", 10);
	InventoryItem scrolls = new InventoryItem("Scrolls", 10);
	int currentChar = 0;
	
	
	boolean isShowing = false;
	GameThread gamethread;
	
	public PlayerDataManager(){
		party[2] = black;
		party[1] = white;
		inventory.add(potion);
		inventory.add(herb);
		inventory.add(scrolls);
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


}
