import java.util.ArrayList;


public class PartyManager {
	
	BlackDragon black = new BlackDragon("Black", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	WhiteDragon white = new WhiteDragon("random grill", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	ArrayList<Character> party = new ArrayList<Character>();
	
	public PartyManager(){
		addToParty(black);	
		addToParty(white);
	}
	
	public void addToParty(Character character){		
		party.add(character);		
	}
	
	public ArrayList<Character> getParty(){
		return party;		 
	}

}
