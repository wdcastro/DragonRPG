import java.io.File;
import java.util.ArrayDeque;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class DialogBox{
	String text = "asdf";

	Label label = new Label();
	int index = 0;
	VBox vbox;
	GameThread gt;
	String substring = "";
	float timer = 0;
	float timePerChar = 50;
	boolean isShowing = false;
	boolean isReady = false;
	boolean isTyping = false;
	
	public DialogBox(GameThread gamethread){
		gt = gamethread;
		vbox = new VBox();
		vbox.getStylesheets().add(new File("res/stylesheets/dialogbox.css").toURI().toString());
		vbox.setMinHeight(Game.SCREEN_HEIGHT*0.25);
		vbox.setMinWidth(Game.SCREEN_WIDTH*0.90);
		vbox.setSpacing(10);
		vbox.setLayoutX(Game.SCREEN_WIDTH*0.05);
		vbox.setLayoutY(Game.SCREEN_HEIGHT*0.70);
		vbox.getStyleClass().add("dialogbox");
		label.setText("");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Vernada",20));
        label.toFront();
        vbox.getChildren().add(label);
		isReady = true;
	}
	
	public void show(){ 
		index = 0;
		timer = 0;
		isTyping = true;
		Platform.runLater(new Runnable(){
			public void run(){
				isShowing = true;
				if(!gt.getBasePane().getChildren().contains(vbox)){

					gt.getBasePane().getChildren().add(vbox);
				}
			}
		});
	}
	
	public void hide(){
		Platform.runLater(new Runnable(){
			public void run(){
				label.setText("");
				isShowing = false;
				gt.getBasePane().getChildren().remove(vbox);
				
			}
		});
	}
	
	public void setContent(String s){
		text = s;
	}
	
	public void update(){
		if(isTyping){
		timer += Game.delta_time/Game.MILLIS_TO_NANOS;
		}
		
		if(index <= text.length() && timer >= timePerChar){
			substring = text.substring(0, index);
			label.setText(substring);
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

