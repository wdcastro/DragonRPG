public class MusicPlayback{
	MusicPlaybackThread mpbt;
	
	public MusicPlayback(){
		mpbt = new MusicPlaybackThread("abc.mp3");
		mpbt.start();
	}
	
	public void togglePause(){
		mpbt.togglePause();
	}
}
