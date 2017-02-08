import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class TileManager extends JPanel{
	
	private final int TILE_WIDTH = 32;
	private final int TILE_HEIGHT = 32;
	private final int SCREEN_MAX_TILES_WIDTH = 40;
	int[] tilearray;
	int[] testarray = new int[880];
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
		loadMap("map.png");
	}
	
	public void drawTiles(Graphics g){
		/*
		 * for tilearray.length
		 * drawtile
		 * if i > screenwidth, x++
		 */
		int currentHeight = 0;
		int currentWidth = 0;

		
		
		for(int i = 0; i< testarray.length; i++){
			tileToDraw = image.getSubimage((testarray[i]%16)*32,32*(testarray[i]/16), TILE_WIDTH, TILE_HEIGHT);
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
	
	public void loadMap(String path){
		File file = new File(path);
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawTiles(g);
		System.out.println("redrawing");
	}
}
