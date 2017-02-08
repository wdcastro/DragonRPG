import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class TileManager extends JPanel{
	
	private final int TILE_WIDTH = 32;
	private final int TILE_HEIGHT = 32;
	private final int SCREEN_MAX_TILES_WIDTH = 40;
	
	int[] tilearray;
	int[] testarray = new int[880];
	
	byte[] bytesRead;
	int	amountRead;
	int remainingFileSize;
	int currentOffset = 0;
	
	BufferedImage image;
	BufferedImage tileToDraw = new BufferedImage(512,512,BufferedImage.TYPE_INT_RGB);
	
	public TileManager(){
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
		loadMapImage("map.png");
		loadTileMap("tilemap1.txt");
	}
	
	public void drawTiles(Graphics g){
		/*
		 * for tilearray.length
		 * drawtile
		 * if i > screenwidth, x++
		 */
		int currentHeight = 0;
		int currentWidth = 0;

		
		
		for(int i = 0; i< tilearray.length; i++){
			tileToDraw = image.getSubimage((tilearray[i]%16)*32,32*(tilearray[i]/16), TILE_WIDTH, TILE_HEIGHT);
			g.drawImage(tileToDraw, currentWidth*TILE_WIDTH, currentHeight*TILE_HEIGHT, null); //draw tiles
			currentWidth++;
			if(currentWidth%40 == 0 && i!= 0){
				System.out.println("increasing height i is: "+ i);
				currentWidth = 0;
				currentHeight++;
				System.out.println("new currentHeight is " + currentHeight);
			}
		}
	}
	
	public void loadMapImage(String path){
		File file = new File(path);
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			System.out.println(textToPrint);
			
			String[] mapStrArray = textToPrint.split(",");

			tilearray = new int[mapStrArray.length];
			for(int i = 0; i< mapStrArray.length; i++){
				tilearray[i] = Integer.parseInt(mapStrArray[i]);
				System.out.print(tilearray[i]);
			}
			
		} catch(Exception e){
			
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawTiles(g);
		System.out.println("redrawing");
	}
}
