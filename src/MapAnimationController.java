import java.util.ArrayDeque;

public class MapAnimationController{
	boolean isAnimating = false;
	ArrayDeque<String> animations = new ArrayDeque<String>();
	GameThread gamethread;

	float animationCooldown = 1800;
	float animationTimer = 0;
	
	public MapAnimationController(GameThread gt){

		gamethread = gt;
	}
	
	
	public void update(){
	}
	
	public void addAnimations(String[] animationsToAdd){
		for(int i = 0; i<animationsToAdd.length;i++){
			animations.addLast(animationsToAdd[i]);
		}
		
	}
	
	public void startAnimation(){
		isAnimating = true;
		animationCallback();
	}
	
	public void animationCallback(){
		if(!animations.isEmpty()){
			String[] animationInput = animations.removeLast().split(" ");
			if(animationInput[2].equals("right")){
				gamethread.mapmanager.moveRight();
				
			} else if(animationInput[2].equals("left")){
				gamethread.mapmanager.moveLeft();
			}
		} else {
			isAnimating = false;
			gamethread.cutscenemanager.cutsceneCallback();
		}
	}
}
