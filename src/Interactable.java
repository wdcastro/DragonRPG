
public abstract class Interactable {
	protected String comment = "this is an interactable";
	
	public void nearInteract(){
		System.out.println(comment);
	}
	
	public void farInteract(){
		System.out.println(comment);
	}
	
	public String getInteractText(){
		return comment;
	}

}
