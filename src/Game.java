import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class Game extends Application{
	

	
	public static final int MAX_FPS = 100;
	public static final int MILLIS_BETWEEN_FRAMES = 1000/MAX_FPS;

	public static final int MAX_DRAWS = 60;
	public static final int MILLIS_BETWEEN_DRAWS = 1000/MAX_DRAWS;
	
	public static final int SCREEN_WIDTH = 1920;
	public static final int SCREEN_HEIGHT = 1080;
	
	public static final long MILLIS_TO_NANOS = 1000000;
	
	public static long delta_time = 0;
	public static long last_time = System.nanoTime();
	public static final String newLine = System.getProperty("line.separator");
	
	public static boolean hasClicked = false;
	
	public static SystemState systemstate;
	public static GameThread gamethread;
	public static Scene scene;
	public static Group root;
	public static VBox vb;
	
	public static void main(String args[]){
		launch();
	}
	
	public void start(Stage stage) throws Exception {
		
		// display splash screens
		// load user configs
		// initialise things

		root = new Group();
		scene = new Scene(root, Color.BLACK);
		Canvas canvas = new Canvas(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		root.getChildren().add(canvas);
		stage.setTitle("Amazing RPG Simulator 2017");
		stage.setResizable(false);

		GraphicsContext context = canvas.getGraphicsContext2D();
		
		Label label = new Label();

		root.getChildren().add(label);
		

		systemstate = SystemState.MAIN_MENU;		

		gamethread = new GameThread(context);

		final MainMenu mainmenu = new MainMenu();
		
		vb = setUpMainMenuItems(mainmenu);
		root.getChildren().add(vb);
		
		new AnimationTimer(){
			private long lastUpdate = 0;
			private final long[] frameTimes = new long[100];
		    private int frameTimeIndex = 0 ;
		    private boolean arrayFilled = false ;
		
		    @Override
			public void handle(long now) {
				// FPS Code and Delta Time
				delta_time = now - lastUpdate;
				lastUpdate = now;
				
				long oldFrameTime = frameTimes[frameTimeIndex] ;
	            frameTimes[frameTimeIndex] = now ;
	            frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
	            if (frameTimeIndex == 0) {
	                arrayFilled = true ;
	            }
	            
	            if (arrayFilled) {
	                long elapsedNanos = now - oldFrameTime ;
	                long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
	                double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
	                //System.out.println("Current frame rate: "+ frameRate);
	                label.setText(Double.toString(frameRate));
	
	                label.setTextFill(Color.RED);
	                label.setFont(Font.font("Vernada",20));
	                label.toFront();
	            }
	            if(systemstate == SystemState.IN_GAME){
	            	//Game updates
					gamethread.update();
					
					//Draw calls
					gamethread.draw();
	            } else if(systemstate == SystemState.MAIN_MENU){
	            	mainmenu.drawBackgroundImage(context);
	            }
	          
		    }
		    
		    
		}.start();

		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent arg0) {
				if(systemstate == SystemState.IN_GAME){
					gamethread.stopAllThreads();
				}
				Platform.exit();
			}
			
		});
		

		
		
		
		stage.setScene(scene);
		stage.show();
	}

	public VBox setUpMainMenuItems(MainMenu mainmenu){
		AudioClip hover = new AudioClip(new File("buttonhover.mp3").toURI().toString());

		Button[] b = mainmenu.generateMenuItems();
		VBox vbbuttons = new VBox();
		vbbuttons.setMinHeight(Game.SCREEN_HEIGHT*0.80);
		vbbuttons.setMinWidth(Game.SCREEN_WIDTH);
		vbbuttons.setSpacing(10);

		for(int i = 0; i<b.length;i++){
			
			b[i].setLayoutX(250);
			b[i].setLayoutY(250*i);
			b[i].setMaxWidth(Game.SCREEN_WIDTH*0.20);
			b[i].setOnMouseEntered(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					new Thread(new Runnable(){

						@Override
						public void run() {
							
							if(!hasClicked){
								hover.play();
							}							
						}
					}).start();
				}
				
			});
			vbbuttons.getChildren().add(b[i]);
		}
		vbbuttons.setAlignment(Pos.BOTTOM_CENTER);
		return vbbuttons;
		
	}
	
	synchronized public static void updateGameState(SystemState state){
		switch (state){
		case IN_GAME:
			gamethread.start();
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub

					root.getChildren().remove(vb);
					
				}
				
			});
			while(!gamethread.isReady()){ //loading screen
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
		        @Override
		        public void handle(KeyEvent e) {
		        	gamethread.handleKeyRelease(e);
		        }
		    });
			
			scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent e) {
		        	gamethread.handleMouseClick(e);
		        }
		    });
			systemstate = SystemState.IN_GAME;
        	System.out.println("system is in game========================================");
			break;
		case LOAD_GAME:
			break;
		case MAIN_MENU:
			break;
		case SETTINGS:
			break;
		default:
			break;

		}
	}
	
}
