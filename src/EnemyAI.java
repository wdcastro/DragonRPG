
public class EnemyAI extends Thread {
	
	private boolean isAttacking = false; 
	Character attackTarget;
	Character me;
	long attackTimer = 0;
	final int attackCooldown = 5;
	
	public EnemyAI(Character[] party, Character me){
		//TO-DO: make ai smarter and more fleshed out attack patterns
		for(int i = 0; i<party.length; i++){
			if(party[i] != null){
				attackTarget = party[i];
				break;
			}
		}
		this.me = me;
	}
	
	public void attack(Character target){
		System.out.println(me.getName() + " attacks " + target.getName() + " with "+ me.getSpells()[(int) Math.round(Math.random()*3)]);
		 // instead of 3 it should be number of moves -1
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
