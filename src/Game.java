
public class Game {
	
	public static boolean isRunning = false;
	public static int delta_time;
	
	public static final int MAX_FPS = 10;
	public static final int FRAME_SKIPS = 1000/MAX_FPS;
	public static final int MAX_FRAME_SKIP = 10;
	
	
	

	public static void main(String args[]){
		// display splash screens
		// load user configs
		// initialise things
		GamePanel gamepanel = new GamePanel();
		isRunning = true;

		long last_time = System.nanoTime();
		
		int totalFrames = 0;
		int current_game_tick = 0;

		
		while(isRunning){
			long time = System.nanoTime();
		    delta_time = (int) ((time - last_time));
		    last_time = time;


			/*if(System.currentTimeMillis() - start_time >= 1000){
				System.out.println(totalFrames);
				totalFrames = 0;
				start_time = System.currentTimeMillis();
			}*/
			gamepanel.update();
		}
		System.exit(0);
		
	}
}
