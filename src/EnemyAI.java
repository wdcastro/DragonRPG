import java.util.ArrayList;


public class EnemyAI extends Thread {
	
	private boolean isAttacking = false; 
	Character attackTarget;
	Character me;
	long attackTimer = 0;
	final int attackCooldown = 5;
	
	public EnemyAI(ArrayList<Character> party, Character me){
		attackTarget = party.get(1);
		this.me = me;
	}
	
	public void attack(Character target){
		System.out.println(me.getName() + " attacks " + target.getName());
		target.getAttacked(me, me.getSpells()[0], 100);
		attackTimer = 0;
	}	
	
	public void run(){
		
	}
	
	public void update(){
		attackTimer += Game.delta_time;
		if(attackTimer >= 5 * 1000 * Game.MILLIS_TO_NANOS){
			attack(attackTarget);
		}
	}

}
