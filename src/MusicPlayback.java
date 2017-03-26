import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class MusicPlayback extends Thread {
	
	private final int sleep_time = 100;
	Media media;
	MediaPlayer mediaplayer;
	private volatile boolean isPlaying = false;
	boolean isPaused = false;
	boolean isFinished = false;
	
	public MusicPlayback(String songname){
		media = new Media(new File("res/music/"+songname).toURI().toString());
		mediaplayer = new MediaPlayer(media);
	}
	
	public void run(){
		playMusic();
	}
	
	public void playMusic(){
		mediaplayer.play();
		mediaplayer.onEndOfMediaProperty().set(new Runnable(){

			@Override
			public void run() {
				isFinished = true;
				isPlaying = false;
			}
			
		});
		isPlaying = true;
		while(isPlaying){
			//System.out.println("Inside while, MusicPlayback");
			try {
				Thread.sleep(sleep_time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void togglePause(){
		if(isPaused){
			isPaused = false;
			mediaplayer.play();
		} else {
			isPaused = true;
			mediaplayer.pause();
		}
	}
	
	synchronized public void stopMusic(){
		System.out.println("Stopping music");
		mediaplayer.stop();
		mediaplayer.dispose();
		isPlaying = false;
		
	}

	public boolean isFinished() {
		return isFinished;
	}

}
