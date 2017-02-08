public class MusicPlayback extends Thread{
	MusicPlaybackThread mpbt;
	boolean isPlaying = false;
	
	
	public MusicPlayback(){
		
	}
	
	public void togglePause(){
		mpbt.togglePause();
	}
	
	public void stopMusic(){
		mpbt.stopMusic();
	}
	
	public void playSong(String name){
		mpbt = new MusicPlaybackThread(name);
		mpbt.start();
	}
	
	public void run(){
		playSong("abc.mp3");
	}
}
