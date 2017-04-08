import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class InventoryItem extends Label {
	
	String name;
	String flavourtext;
	int count;
	
	public InventoryItem(String name, int count){
		this.name = name;
		this.count = count;
		setText(name+" x"+count);
		setMinWidth(Game.SCREEN_WIDTH*0.15);
		this.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				use();
			}
			
		});
	}
	
	public String getName(){
		return name;
	}
	
	public void updateLabel(){
		setText(name+" x"+count);
	}
	
	public void use(){
		if(count > 0){
			System.out.println(name+" used");
			//apply effect
			count--;
			setText(name+" x"+count);
			//if 0 remove itself
		}
	}

	public int getCount() {
		return count;
	}

}
