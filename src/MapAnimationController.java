import java.util.ArrayList;

public class MapAnimationController {
	boolean isAnimating = false;
	ArrayList<String> animations = new ArrayList<String>();
	private float animationTimer = 180;
	private float animationCooldown = 180;
	
	
	public MapAnimationController(GameThread gt){
		animations.add("right");
		animations.add("right");
	}
	
	public void update(){
		if(isAnimating){
			if(animationTimer >= animationCooldown){
				isAnimating = false;
			}
			animationTimer+=Game.delta_time/Game.MILLIS_TO_NANOS;
		}
	}
	
	public void animate(){
		isAnimating = true;
		animationTimer = 0;
	}
}
