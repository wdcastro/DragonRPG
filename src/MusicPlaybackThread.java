import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class MusicPlaybackThread extends Thread {

	Media media;
	MediaPlayer mediaplayer;
	private volatile boolean isPlaying = false;
	boolean isPaused = false;
	
	public MusicPlaybackThread(String songname){
		media = new Media(new File(songname).toURI().toString());
		mediaplayer = new MediaPlayer(media);
	}
	
	public void start(){
		mediaplayer.play();
		isPlaying = true;
		while(isPlaying){
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
		isPlaying = false;
	}

}
