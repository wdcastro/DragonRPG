public class PlaylistManager extends Thread{
	MusicPlayback mp;
	boolean isRunning = false;
	
	
	public PlaylistManager(){
		
	}
	
	public void togglePause(){
		System.out.println("toggling pause");
		mp.togglePause();
	}
	
	public void stopMusic(){
		isRunning = false;
		mp.stop();
	}
	
	public void playSong(String name){
		mp = new MusicPlayback(name);
		mp.play();
	}
	
	public void run(){
		isRunning = true;
		playSong("abc.mp3");
	}
	
	public void nextSong(){
		// some logic determining next song
	}
	
}
