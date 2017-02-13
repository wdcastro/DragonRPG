import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PlaylistManager extends Thread{
	MusicPlayback mp;
	int currentSong = 0;
	ArrayList<String> playlist = new ArrayList<String>();
	
	public PlaylistManager(){
		
	}
	
	public void togglePause(){
		System.out.println("Toggling pause");
		mp.togglePause();
	}
	
	public void stopMusic(){
		mp.stopMusic();
	}
	
	public void playSong(String name){
		System.out.println("Playing "+name);
		mp = new MusicPlayback(name);
		mp.start();
	}
	
	public void run(){
		loadPlaylist("default.txt");
		playSong(playlist.get(currentSong));
	}
	
	public void update(){
		if(mp.isFinished()){
			System.out.println("PlaylistManager: Song finished");
			nextSong();
		}
	}
	
	public void nextSong(){
		currentSong++;
		if(currentSong >= playlist.size()){
			currentSong = 0; //Loop to beginning
		}
		stopMusic();
		playSong(playlist.get(currentSong));		
	}
	
	public void loadPlaylist(String name){
		FileIOManager filemanager = new FileIOManager();
		byte[] bytesRead = filemanager.readBytesFromFile(name);
		try {
			String playliststr = new String(bytesRead, "UTF-8");
			String[] playlistarray = playliststr.split(Game.newLine);
			for(int i = 0; i < playlistarray.length; i++){
				playlist.add(playlistarray[i]);
				System.out.print(playlist.get(i));
				System.out.print(", ");
			}
			if (playlist.size() > 0){
				currentSong = 0;
			} else {
				System.err.println("No songs loaded");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
