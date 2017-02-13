import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Game extends Application{
	

	
	public static final int MAX_FPS = 100;
	public static final int MILLIS_BETWEEN_FRAMES = 1000/MAX_FPS;

	public static final int MAX_DRAWS = 60;
	public static final int MILLIS_BETWEEN_DRAWS = 1000/MAX_DRAWS;
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	
	public static final long MILLIS_TO_NANOS = 1000000;
	
	public static long delta_time = 0;
	public static long last_time = System.nanoTime();
	public static final String newLine = System.getProperty("line.separator");

	public static void main(String args[]){
		launch();
		
	}
	
	public void start(Stage stage) throws Exception {
		
		// display splash screens
		// load user configs
		// initialise things

	
		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		root.getChildren().add(canvas);
		stage.setTitle("Amazing RPG Simulator 2017");
		stage.setResizable(false);


		GraphicsContext context = canvas.getGraphicsContext2D();
		
		final GameThread gamethread = new GameThread(context);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent arg0) {
				gamethread.stopAllThreads();
				Platform.exit();
			}
			
		});
		
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
		
		gamethread.start();
		

		Label label = new Label();

		root.getChildren().add(label);
		
		new AnimationTimer() {
			
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
                
                //Game updates
				gamethread.update();
				
				//Draw calls
				gamethread.draw();
			}
			
		}.start();
		stage.setScene(scene);
		stage.show();
	}
}
