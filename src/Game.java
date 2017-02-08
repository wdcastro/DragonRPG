import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Game extends Application{
	
	//public static boolean isRunning = false;

	
	public static final int MAX_FPS = 10;
	public static final int FRAME_SKIPS = 1000/MAX_FPS;
	public static final int MAX_FRAME_SKIP = 10;
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	
	public static long delta_time = 0;
	public static long last_time = System.nanoTime();
	//DeltaTimeThread timethread = new DeltaTimeThread();
	

	public static void main(String args[]){
		launch();
		
	}
	
	public void start(Stage stage) throws Exception {
		
		// display splash screens
		// load user configs
		// initialise things
		//GamePanel gamepanel = new GamePanel();
		//isRunning = true;
	
		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		root.getChildren().add(canvas);
		//stage.setSize(Game.SCREEN_WIDTH,Game.SCREEN_HEIGHT);
		stage.setTitle("Amazing RPG Simulator 2017");
		stage.setResizable(false);
		//stage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//stage.add(tiles);
		//addFocusListener(stage);

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
		//addKeyListener(stage);
		//addMouseListener(stage);

		stage.setScene(scene);
		stage.show();
		gamethread.start();
	}
	
	public static long deltaTime(){
		Boolean isRunning = true;
		    
		delta_time = ((System.nanoTime() - last_time));
		last_time = System.nanoTime();
		return delta_time;


		/*if(System.currentTimeMillis() - start_time >= 1000){
			System.out.println(totalFrames);
			totalFrames = 0;
			start_time = System.currentTimeMillis();
			}
		*/
	}
}
