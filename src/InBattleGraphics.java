
import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class InBattleGraphics {

	Image player0;
	Image player1;
	Image player2;
	Image player3;
	Image player0_grey;
	Image player1_grey;
	Image player2_grey;
	Image player3_grey;
	Image enemy;
	Image background;
	double player0Scale = 1;
	double player1Scale = 1;
	double player2Scale = 1;
	double player3Scale = 1;
	double enemyScale;
	Group root;
	Label attacktext;
	double attacktextTimer;
	final double attacktextTimerDelay = 1500;
	boolean showEnemy = false;
	boolean attacktextShowing = false;
	boolean isInMenu = true;
	
	int currentCharacterHighlighted = 0;
	GraphicsContext context;
	BattleManager battlemanager;
	
	public InBattleGraphics(GameThread gamethread, BattleManager bm){
		
		context = gamethread.getContext();
		root = gamethread.getRootNode();
		battlemanager = bm;
		
		
		
	}
	
	public void initialise(){
		loadImages();
		attacktext = new Label();
		attacktext.setTextFill(Color.RED);
		attacktext.setFont(new Font("Arial", 30));
		Image image = new Image(new File("res/misc/dragoon chibi.png").toURI().toString());
		attacktext.setGraphic(new ImageView(image));
		attacktext.setText("HELLO WORLD");
	}

	
	public void draw(){
		//draw background
		//draw enemy
		//loop through party, find who to draw
		
		
		
		context.drawImage(background, 0,0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		if(!isInMenu){
			if(player0!=null){
				context.drawImage(player0, Game.SCREEN_WIDTH-player0.getWidth()/player0Scale, Game.SCREEN_HEIGHT*0.1,player0.getWidth()/player0Scale, player0.getHeight()/player0Scale);
			}
			if(player1!=null){
				context.drawImage(player1, Game.SCREEN_WIDTH-player1.getWidth()/player1Scale, Game.SCREEN_HEIGHT*0.1,player1.getWidth()/player1Scale, player1.getHeight()/player1Scale);
			}
			if(player2!=null){
				context.drawImage(player2, Game.SCREEN_WIDTH-player2.getWidth()/player2Scale,Game.SCREEN_HEIGHT*0.1,player2.getWidth()/player2Scale,player2.getHeight()/player2Scale);
			}
			if(player3!=null){
				context.drawImage(player3, Game.SCREEN_WIDTH-player3.getWidth()/player3Scale, Game.SCREEN_HEIGHT*0.1,player3.getWidth()/player0Scale, player3.getHeight()/player3Scale);
			}
		} else {	
		//System.out.println(battlemanager.getCurrentCaster());
			
			if(player0!=null){
				if(battlemanager.getCurrentCaster() == 0){
					context.drawImage(player0, Game.SCREEN_WIDTH-player0.getWidth()/player0Scale, Game.SCREEN_HEIGHT*0.1,player0.getWidth()/player0Scale, player0.getHeight()/player0Scale);
				} else {
					context.drawImage(player0_grey, Game.SCREEN_WIDTH-player0.getWidth()/player0Scale, Game.SCREEN_HEIGHT*0.1,player0.getWidth()/player0Scale, player0.getHeight()/player0Scale);
				}
			}
			if(player1!=null){
				if(battlemanager.getCurrentCaster() == 1){
					context.drawImage(player1, Game.SCREEN_WIDTH-player1.getWidth()/player1Scale, Game.SCREEN_HEIGHT*0.1,player1.getWidth()/player1Scale, player1.getHeight()/player1Scale);
				} else {
					context.drawImage(player1_grey, Game.SCREEN_WIDTH-player1.getWidth()/player1Scale, Game.SCREEN_HEIGHT*0.1,player1.getWidth()/player1Scale, player1.getHeight()/player1Scale);
				}
			}
			if(player2!=null){
				if(battlemanager.getCurrentCaster() == 2 ){
					context.drawImage(player2, Game.SCREEN_WIDTH-player2.getWidth()/player2Scale,Game.SCREEN_HEIGHT*0.1,player2.getWidth()/player2Scale,player2.getHeight()/player2Scale);
				} else {
					context.drawImage(player2_grey, Game.SCREEN_WIDTH-player2.getWidth()/player2Scale,Game.SCREEN_HEIGHT*0.1,player2.getWidth()/player2Scale,player2.getHeight()/player2Scale);	
				}
				
			}
			if(player3!=null){
				if(battlemanager.getCurrentCaster() == 3){
					context.drawImage(player3, Game.SCREEN_WIDTH-player3.getWidth()/player3Scale, Game.SCREEN_HEIGHT*0.1,player3.getWidth()/player0Scale, player3.getHeight()/player3Scale);
				} else {
					context.drawImage(player3_grey, Game.SCREEN_WIDTH-player3.getWidth()/player3Scale, Game.SCREEN_HEIGHT*0.1,player3.getWidth()/player0Scale, player3.getHeight()/player3Scale);
				}
			}
		}
		
		if(showEnemy){
			context.drawImage(enemy, 0, Game.SCREEN_HEIGHT * 0.1,enemy.getWidth()/enemyScale, enemy.getHeight()/enemyScale);
		}
	}

	
	public void loadImages(){
		Character[] party = battlemanager.getParty();
		for(int i = 0; i < party.length; i++){
			if(party[i] == null){
				continue;
			} else {
				switch(i){
				case 0:
					player0 = new Image(new File("res/character/"+party[i].getName()+"_fight.png").toURI().toString());
					player0_grey = new Image(new File("res/character/"+party[i].getName()+"_grey.png").toURI().toString());
					player0Scale = player0.getHeight()/(Game.SCREEN_HEIGHT*0.75);
					System.out.println("player 0 loaded");
					break;
				case 1:
					player1 = new Image(new File("res/character/"+party[i].getName()+"_fight.png").toURI().toString());
					player1_grey = new Image(new File("res/character/"+party[i].getName()+"_grey.png").toURI().toString());
					player1Scale = player1.getHeight()/(Game.SCREEN_HEIGHT*0.75);
					System.out.println("player 1 loaded");
					break;
				case 2:
					player2 = new Image(new File("res/character/"+party[i].getName()+"_fight.png").toURI().toString());
					player2_grey = new Image(new File("res/character/"+party[i].getName()+"_grey.png").toURI().toString());
					player2Scale = player2.getHeight()/(Game.SCREEN_HEIGHT*0.75);
					System.out.println("player 2 loaded");
					break;
				case 3:
					player3 = new Image(new File("res/character/"+party[i].getName()+"_fight.png").toURI().toString());
					player3_grey = new Image(new File("res/character/"+party[i].getName()+"_grey.png").toURI().toString());
					player3Scale = player3.getHeight()/(Game.SCREEN_HEIGHT*0.75);
					System.out.println("player 3 loaded");
					break;
				default:
					System.out.println("LOGIC ERROR: Tried to load an image that isnt a player number 0-3 in InBattleGraphics");
				}
			}
			
		}

		enemy = new Image(new File("res/enemy/golem.png").toURI().toString());
		enemyScale = enemy.getHeight()/(Game.SCREEN_HEIGHT*0.75);
		
		background = new Image(new File("res/background/throne room.jpg").toURI().toString());
		
	}
	
	public void showEnemy(boolean show){
		showEnemy = show;
	}
	
	public void showAttackText(String text){
		attacktext.setText(text);
		attacktextShowing = true;
		attacktextTimer = 0;

		new AnimationTimer(){

			@Override
			public void handle(long arg0) {
				if(attacktextShowing){
					attacktextTimer += Game.delta_time/Game.MILLIS_TO_NANOS;
					if(attacktextTimer >= attacktextTimerDelay){
						hideAttackText();
					}
				} else {
					this.stop();
				}
			}
			
		}.start();
		Platform.runLater(new Runnable(){
			public void run(){
				if(!root.getChildren().contains(attacktext))
				{
					root.getChildren().add(attacktext);
				}
			}
		});
	}
	
	public void hideAttackText(){
		attacktextShowing = false;
		Platform.runLater(new Runnable(){
			public void run(){
				root.getChildren().remove(attacktext);
			}
		});
	}
}
