import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;


public class TileManager extends Thread{
	
	private final int TILE_WIDTH = 32;
	private final int TILE_HEIGHT = 32;
	private final int SCREEN_MAX_TILES_WIDTH = 40;
	
	int[] tilearray;
	int[] testarray = new int[880];
	
	byte[] bytesRead;
	int	amountRead;
	int remainingFileSize;
	int currentOffset = 0;
	
	boolean tilesReady = false;
	
	Image image;
	Image tileToDraw;
	
	GraphicsContext context;
	
	public TileManager(GraphicsContext gc){
		context = gc;
		testarray[0] = 199;
		for(int i = 1; i< 40; i++){
			testarray[i]=0;
		}
		for(int i = 40; i< 880; i++){
			testarray[i]=1;
		}
		for(int i = 80; i< 880; i++){
			testarray[i]=2;
		}
		
	}
	
	public void run(){
		loadMapImage("map.png");
		loadTileMap("tilemap1.txt");
		tilesReady = true;
	}
	
	public void drawTiles(){
		/*
		 * for tilearray.length
		 * drawtile
		 * if i > screenwidth, x++
		 */
		int currentHeight = 0;
		int currentWidth = 0;
		
		ImageView view = new ImageView(image);
		WritableImage croppedImage;
		
		
		for(int i = 0; i< tilearray.length; i++){
			view.setViewport(new Rectangle2D((tilearray[i]%16)*32,(tilearray[i]/16)*32,32,32));
			croppedImage = view.snapshot(null, null);
			context.drawImage(croppedImage, currentWidth*32, currentHeight*32);
			currentWidth++;
			if(currentWidth%40 == 0 && i!= 0){
				//System.out.println("increasing height i is: "+ i);
				currentWidth = 0;
				currentHeight++;
				//System.out.println("new currentHeight is " + currentHeight);
			}
		}
	}
	
	public void loadMapImage(String path){
		File file = new File(path);
		try{
			image = new Image(file.toURI().toString());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadTileMap(String path){
		File file = new File(path);
		remainingFileSize = (int) file.length();
		bytesRead = new byte[(int) remainingFileSize];
		System.out.println("spellFile length is " + file.length());
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			
			while(amountRead != -1)	{
				if(remainingFileSize > 1024){
					amountRead = in.read(bytesRead, currentOffset, 1024);
					System.out.println("read " + amountRead + " bytes");
				} else {
					amountRead = in.read(bytesRead, currentOffset, remainingFileSize);
					System.out.println("read " + amountRead + " bytes");
					amountRead = -1;
				}
				currentOffset += amountRead;
				remainingFileSize -= amountRead;
			}
			
			in.close();
			String textToPrint = new String(bytesRead, "UTF-8");
			//System.out.println(textToPrint);
			
			String[] mapStrArray = textToPrint.split(",");

			tilearray = new int[mapStrArray.length];
			for(int i = 0; i< mapStrArray.length; i++){
				tilearray[i] = Integer.parseInt(mapStrArray[i]);
				//System.out.print(tilearray[i]);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
			drawTiles();
	}

}
