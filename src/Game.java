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
	

	
//	public static final int MAX_FPS = 100;
	//public static final int MILLIS_BETWEEN_FRAMES = 1000/MAX_FPS;

	//public static final int MAX_DRAWS = 60;
	//public static final int MILLIS_BETWEEN_DRAWS = 1000/MAX_DRAWS;
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	
	public static final long MILLIS_TO_NANOS = 1000000;
	
	public static long delta_time = 0;
	public static long last_time = System.nanoTime();
	public static final String newLine = System.getProperty("line.separator");
	
	private boolean hasClicked = false;
	
	private SystemState systemstate;
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GameThread gamethread;
	private GraphicsContext context;
	private MainMenu mainmenu;
	private Label label;
	private AnimationTimer updateLoop;
	
	
	public static void main(String args[]){
		launch();
	}
	
	public void start(Stage stage) throws Exception {
		
		// display splash screens
		// load user configs
		// initialise things
		// set up graphics groups
		
		root = new Group();
		scene = new Scene(root,Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, Color.BLACK);		
		canvas = new Canvas(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		stage.setTitle("Amazing RPG Simulator 2017");
		stage.setResizable(false);



		
		label = new Label();


		systemstate = SystemState.MAIN_MENU;	
		updateGameState(SystemState.MAIN_MENU);


		
		
		updateLoop = new AnimationTimer(){
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
	            
	            switch(systemstate){
	            case IN_GAME:
	            	gamethread.update();
	            	gamethread.draw();
	            	break;
	            case MAIN_MENU:
	            	mainmenu.drawBackgroundImage(context);
	            	break;
				case LOAD_GAME:
					break;
				case SETTINGS:
					break;
				default:
					break;
	            }
		    }
		};
		
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

		Button[] b = generateMenuItems();
		VBox vbbuttons = new VBox();
		vbbuttons.setMinHeight(Game.SCREEN_HEIGHT*0.80);
		vbbuttons.setMinWidth(Game.SCREEN_WIDTH);
		vbbuttons.setSpacing(10);

		for(int i = 0; i<b.length;i++){
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
	
	synchronized public void updateGameState(SystemState state){
		System.out.println("Changing game states to "+state);
		switch (state){
		case IN_GAME:
			if(updateLoop != null){
				System.out.println("stopping update loop");
				updateLoop.stop();
			}
			Platform.runLater(new Runnable(){
				@Override
				public void run(){
					root.getChildren().clear();
					root.getChildren().add(canvas);
					context = canvas.getGraphicsContext2D();
					gamethread = new GameThread(context, root);
					gamethread.start();
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
				}
				
			});
			while(gamethread == null){ //loading screen
				try {
					System.out.println("waiting for gamethread to be created");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(!gamethread.isReady()){ //loading screen
				try {
					System.out.println("waiting for gamethread to finish loading");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			systemstate = SystemState.IN_GAME;
    		updateLoop.start();
			System.out.println("starting update loop");
			break;
		case LOAD_GAME:
			break;
		case MAIN_MENU:
			if(updateLoop!= null){
				System.out.println("stopping update loop");
				updateLoop.stop();
			}
			if(gamethread!=null){
				gamethread = null;
			}
			scene.setOnKeyReleased(new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent e) {
										
				}
				
			});
			scene.setOnMouseReleased(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent e) {
					System.out.println("beep");
				}
				
			});
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					root.getChildren().clear();
					root.getChildren().add(label);
					root.getChildren().add(canvas);
					context = canvas.getGraphicsContext2D();
					mainmenu = new MainMenu();
					VBox vb = setUpMainMenuItems(mainmenu);
					root.getChildren().add(vb);
					systemstate = SystemState.MAIN_MENU;
					updateLoop.start();
					System.out.println("starting update loop");
				}
				
			});			
			break;
		case SETTINGS:
			break;
		default:
			break;

		}
	}
	
	public Button[] generateMenuItems(){
		Button[] buttons = new Button[4];
		buttons[0] = new Button("New Game");
		buttons[0].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("buttonclick.mp3").toURI().toString());
						hasClicked = true;
						updateGameState(SystemState.IN_GAME);
						click.play();	
						// Start new game
					}
				}).start();
			}
			
		});
		buttons[1] = new Button("Load Game");
		buttons[1].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("buttonclick.mp3").toURI().toString());

						updateGameState(SystemState.LOAD_GAME);
						hasClicked = true;
						click.play();	
						// Go to load game screen
					}
				}).start();
			}
			
		});
		buttons[2] = new Button("Settings");
		buttons[2].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						AudioClip click = new AudioClip(new File("buttonclick.mp3").toURI().toString());

						updateGameState(SystemState.SETTINGS);
						hasClicked = true;
						click.play();	
						// Go to settings screen
					}
				}).start();
			}
			
		});
		buttons[3] = new Button("Exit Game");
		buttons[3].setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				new Thread(new Runnable(){

					
					@Override
					public void run() {
						System.exit(0);	
						// Start new game
					}
				}).start();
			}
			
		});
		return buttons;
		
	}
	
}
