import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class DialogBox extends Thread{
	String text = "asdf";
	Label label = new Label();
	int index = 0;
	VBox vbox;
	GameThread gt;
	String s = "";
	float timer = 0;
	float timePerChar = 50;
	boolean isShowing = false;
	boolean isReady = false;
	boolean isTyping = false;
	
	public DialogBox(GameThread gamethread){
		gt = gamethread;
	}
	
	public void run(){
		vbox = new VBox();
		vbox.setMinHeight(Game.SCREEN_HEIGHT*0.30);
		vbox.setMinWidth(Game.SCREEN_WIDTH*0.90);
		vbox.setSpacing(10);
		vbox.setLayoutX(Game.SCREEN_WIDTH*0.05);
		vbox.setLayoutY(Game.SCREEN_HEIGHT*0.70);
		vbox.setStyle(//"-fx-padding: 10;" + 
                 "-fx-border-style: solid inside;" + 
                 "-fx-border-width: 2;" +
                 "-fx-border-insets: 5;" + 
                 "-fx-border-radius: 5;" + 
                 "-fx-border-color: blue;" +
                 "-fx-background-color: #000000;");
		label.setText("");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Vernada",20));
        label.toFront();
        vbox.getChildren().add(label);
		isReady = true;
		show();
	}
	
	public void show(){
		timer = 0;
		isTyping = true;
		Platform.runLater(new Runnable(){
			public void run(){
				isShowing = true;
				index = 0;
				gt.getRootNode().getChildren().add(vbox);
			}
		});
	}
	
	public void hide(){
		Platform.runLater(new Runnable(){
			public void run(){
				label.setText("");
				isShowing = false;
				gt.getRootNode().getChildren().remove(vbox);
			}
		});
	}
	
	public void setText(String s){
		text = s;
	}
	
	public void update(){
		if(isTyping){
		timer += Game.delta_time/Game.MILLIS_TO_NANOS;
		}

		//System.out.println(timer);
		if(index <= text.length() && timer >= timePerChar){
			s = text.substring(0, index);
			label.setText(s);
			index++;
			timer = 0;
		}
		
		if(index > text.length()){
			isTyping = false;
		}
	}
	
	public void skip(){
		label.setText(text);
		index = text.length();
		isTyping = false;
	}

	public boolean isReady() {
		return isReady;
	}
	
	public boolean isShowing(){
		return isShowing;
	}
	
	public boolean isTyping(){
		return isTyping;
	}
	
	
	
	
}

