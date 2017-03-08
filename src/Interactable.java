
public abstract class Interactable {
	protected String comment = "this is an interactable";
	
	public void interact(){
		System.out.println(comment);
	}
	
	public String getInteractText(){
		return comment;
	}

}
