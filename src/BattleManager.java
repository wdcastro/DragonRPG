
import java.util.ArrayList;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BattleManager{
// controls characters, status effects, damage, hp, etc
	EnemyAI enemyai;
	ArrayList<String> spellqueue;
	MoveSelectScreen moveselect;
	PartyManager partymanager;
	InBattleGraphics inbattlegraphics;
	
	int currentChar;
	int shieldedChar = 0;
	int partysize;
	long nextPickTimer = 0;
	long pickTimerDelay = 15000000000L;
	long testingPickTimerDelay = 5000000000L;
	final long finalShieldDuration = 750 * Game.MILLIS_TO_NANOS;
	final long finalShieldCooldown = 5000 * Game.MILLIS_TO_NANOS;
	long currentShieldDuration = 750 * Game.MILLIS_TO_NANOS;
	long currentShieldCooldown = 5000 * Game.MILLIS_TO_NANOS;

	
	/*
	 * BATTLE_READY
	 * PLAYER_TURN
	 * ENEMY_TURN
	 * FIGHT_OVER
	 */
	
	BattleState currentState = BattleState.BATTLE_READY;
	
	public BattleManager(PartyManager pm, GameThread gamethread){
		partymanager = pm;
		enemyai = new EnemyAI(partymanager.getParty(), new Slime());
		moveselect = new MoveSelectScreen(partymanager.getParty(), gamethread);
	}
	
	public void doKeyboardAction(KeyEvent e){
		/*
		 * Check state (if in menu)
		 * get spell queue
		 * 
		 * Check state (if in battle)
		 * execute spell
		 */
		if(currentState == BattleState.MOVE_SELECT){
			moveselect.handleKeyRelease(e);
			//check for if spellcaster has changed for animations
		} else {
			switch(e.getCode()){
			case TAB:
				if(nextPickTimer >= testingPickTimerDelay){
					currentState = BattleState.MOVE_SELECT;
					nextPickTimer = 0;
					System.out.println("Entering move select");
					moveselect.startPick();
					
					//pause animations, sounds etc
				} else {
					//play cannot sound
					System.out.println("Pick timer not reached: "+nextPickTimer/1000000000L);
				}
				break;
			case Q:
				System.out.println("Switching char 0");
				currentChar = 0;
				break;
			case W:
				if(partysize > 1){
					System.out.println("Switching char 1");
					currentChar = 1;
				} else {
					System.out.println("Cannot switch char, size too small");
				}
				break;
			case E:
				if(partysize > 2){
					System.out.println("Switching char 2");
					currentChar = 2;
				} else {
					System.out.println("Cannot switch char, size too small");
				}
				break;
			case R:
				if(partysize > 3){
					System.out.println("Switching char 3");
					currentChar = 3;
				} else {
					System.out.println("Cannot switch char, size too small");
				}
				break;
			default:
				break;
			}
		}
	}
	
	public void handleMouseClick(MouseEvent e){
		if(currentState == BattleState.MOVE_SELECT){
			moveselect.handleMouseClick(e);
			if(moveselect.isPicking == false){
				System.out.println("Finished picking, moving to Battle");
				currentState = BattleState.IN_BATTLE;
				spellqueue = moveselect.getSpellQueue();
			}
		} else if(currentState == BattleState.IN_BATTLE){
			if(e.getButton() == MouseButton.PRIMARY){ // Possible efficiency increase here, cpu calls on ArrayList
				if(spellqueue.get(currentChar)!= "cd"){
					System.out.println("Casting spell " + spellqueue.get(currentChar));
					spellqueue.set(currentChar, "cd");
				} else {
					System.out.println("Spell on cd");
				}
			} else if (e.getButton() == MouseButton.SECONDARY){
				if(currentShieldCooldown >= finalShieldCooldown){
					System.out.println("Activating shield");
					shieldedChar = currentChar;
					partymanager.getParty()[currentChar].setShield(true);
					currentShieldCooldown = 0;
					currentShieldDuration = 0;
				} else {
					System.out.println("Shield on cooldown. Time remaining : " + ((finalShieldCooldown-currentShieldCooldown)/(Game.MILLIS_TO_NANOS*1000)));
				}
			}
		}
	}
	
	
	public void startFight(){
		// TODO: checks for who fights first
		System.out.println("Battle Starting");
		System.out.println("Move Select screen displays");
		currentChar = 0;
		partysize = partymanager.getParty().length;
		currentState = BattleState.MOVE_SELECT;
		moveselect.startPick();
		enemyai.start();
	}

	
	synchronized public BattleState getCurrentBattleState(){
		return currentState;
	}
	
	synchronized public void update(){
		if(currentState == BattleState.IN_BATTLE){
			nextPickTimer += Game.delta_time;
			enemyai.update();
		}
		
		
		
		if(currentShieldCooldown < finalShieldCooldown){
			currentShieldCooldown += Game.delta_time;
			
			if(partymanager.getParty()[shieldedChar].isShielded){
				if(currentShieldDuration < finalShieldDuration){
					currentShieldDuration += Game.delta_time;
				} else {
					System.out.println("Shield turned off.");
					partymanager.getParty()[shieldedChar].setShield(false);
				}
			}
			
			
		}
	}
	
	public long getPickTimerDelay(){

		//System.out.println("testingPickTimerDelay: "+testingPickTimerDelay);
		return testingPickTimerDelay;
		
	}
	
	synchronized public long getCurrentPickTimer(){
		//System.out.println("nextPickTimer: "+nextPickTimer);
		return nextPickTimer;
		
	}

}
