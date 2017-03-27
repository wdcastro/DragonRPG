
public class Apples extends Interactable{
	


	public Apples(){
		comment = "There are some apples there";
	}
	
	@Override
	public void nearInteract(){
		comment = "Yummy apples";
	}
	
	@Override
	public void farInteract(){
		comment = "There are some apples there";
	}
}
