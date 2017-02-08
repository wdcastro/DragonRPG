import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class MusicPlayback {
	
	private final int sleep_time = 100;
	Media media;
	MediaPlayer mediaplayer;
	private volatile boolean isPlaying = false;
	boolean isPaused = false;
	
	public MusicPlayback(String songname){
		media = new Media(new File(songname).toURI().toString());
		mediaplayer = new MediaPlayer(media);
	}
	
	public void play(){
		mediaplayer.play();
		isPlaying = true;
		while(isPlaying){
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
	
	public void stop(){
		System.out.println("Stopping music");
		mediaplayer.stop();
		isPlaying = false;
	}

}
