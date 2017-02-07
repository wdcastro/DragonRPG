import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;


public class MoveSelectScreen {
	/*
	 * TODO: limit party size casters
	 */
	
	ArrayList<String> spellqueue = new ArrayList<String>();
	//ArrayList<Character> casterqueue = new ArrayList<Character>();
	
	int[] spellintegers;
	ArrayList<Character> currentparty;
	
	int currentCaster;
	int numCasters;
	boolean isPicking;


	public MoveSelectScreen(ArrayList<Character> party){
		currentparty = party;
		numCasters = party.size();
		System.out.println("numCasters is "+ numCasters);
		startPick();
		//numMaxSpells = get currentChar character from character array and then get the number of max spells from that - this might lag. if it does, preload all values
		//there needs to be a player array somewhere
		//there needs to be a spell array for each player that is accessed with currentSpell
	}
	

	
	public void initialize(){
		// get number of spells, number of characters
	}
	
	//public void reset?
	
	public void startPick(){
		currentCaster = 0;	
		spellintegers = new int[numCasters];
		isPicking = true;
	}
	
	public void finishPick(){
		//turn all numbers into spells and characters so you end up saving time when undoing
		isPicking = false;
		for(int i = 0; i < spellintegers.length; i++){
			spellqueue.add((currentparty.get(i).getEquipment())[0].getSpell(spellintegers[i])); // party's character's equipment's spell
			System.out.println(currentparty.get(i).getEquipment()[0].getSpell(spellintegers[i]));
		}
		spellintegers = null;
	}
	
	public void handleMouseClick(MouseEvent e){
		if(SwingUtilities.isLeftMouseButton(e)){
			//finish picking, give arrays to battle manager
			finishPick();
		} else if (SwingUtilities.isRightMouseButton(e)){
			//undo pick, maybe move this to its own method
			if(currentCaster > 0){
				currentCaster--;
				//graphic changes, theres bound to be animation anwyay so maybe put it in update
			} else {
				//play cannot sound
			}
		}
		
	}
	
	public void handleKeyRelease(KeyEvent e){
		/*
		 * on wasd, change current highlighted
		 * currentHighlighted = player.equipment.getSpell(int)
		 * on x
		 */
		switch(e.getKeyCode()){
		case KeyEvent.VK_Q:
			pickSpell(0);
			break;
		case KeyEvent.VK_W:
			pickSpell(1);
			break;
		case KeyEvent.VK_E:
			pickSpell(2);
			break;
		case KeyEvent.VK_R:
			pickSpell(3);
			break;
		}

	}
	
	public void display(){
		
	}
	
	public void hide(){
		
	}
	
	public void showWeapons(){
		
	}
	
	public void hideWeapons(){
		
	}
	
	public ArrayList<String> getSpellQueue(){
		return spellqueue;
	}
	
	public void pickSpell(int spell){
		System.out.println("currentCaster: "+ currentCaster +" spell is "+spell);
		spellintegers[currentCaster] = spell;
		if(currentCaster != numCasters -1){
			currentCaster++;
		}
	}
	


}
