
public class EnemyAI {
	
	private boolean isAttacking = false; 
	
	public EnemyAI(){
		
	}
	
	public void attack(){
		System.out.println("Enemy attacks");
		isAttacking = false;
	}
	
	public void giveTurn(){
		isAttacking = true;
		attack();
	}
	
	
	public boolean hasFinishedTurn(){
		return isAttacking;
	}

}
