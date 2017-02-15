import java.io.File;
import java.io.UnsupportedEncodingException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileManager{
	
	private int MAP_WIDTH = 40;
	private int MAP_HEIGHT = 40;
	private int TILE_WIDTH = 32;
	private int TILE_HEIGHT = 32;
	private int TILES_PER_LINE_IN_SOURCE = 16;
	private int SCREEN_MAX_TILES_WIDTH = 40;
	
	private int player_tile = -1;
	
	String imgsrc;
	TileLayer layer1 = new TileLayer();
	TileLayer layer2 = new TileLayer();
	TileLayer layer3 = new TileLayer();
	
	Image image;
	Image tileToDraw;
	
	GraphicsContext context;
	
	public TileManager(GraphicsContext gc){
		context = gc;	
	}

	
	public void update(){
		//state checks and whatever
	}
	
	public int getMapWidth(){
		return MAP_WIDTH;
	}
	
	public int getMapHeight(){
		return MAP_HEIGHT;
	}
	
	public void draw(){
		//drawLayer(1);
		//drawLayer(2, 0, (Game.SCREEN_WIDTH/TILE_WIDTH) * (Game.SCREEN_HEIGHT/TILE_HEIGHT));
	}
	
	/*
	public void drawLayer(int j){
		int[] layer;
		if(j == 1){
			layer = layer1.getMap();
		} else {
			layer = layer2.getMap();
		}

		//System.out.println("TileManager:drawLayer: Is layer"+j+" null? "+(layer.length ==0));
		int currentHeight = 0;
		int currentWidth = 0;	
		
		for(int i = 0; i< layer.length; i++){ //16 is the number of tiles per line in tileset
			if(layer[i] != -1){
				context.drawImage(image, (layer[i]%TILES_PER_LINE_IN_SOURCE)*TILE_WIDTH, (layer[i]/TILES_PER_LINE_IN_SOURCE)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, currentWidth*TILE_WIDTH, currentHeight*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
			}
			
			currentWidth++;
			if(currentWidth%MAP_WIDTH == 0 && i!= 0){
				//System.out.println("increasing height i is: "+ i);
				currentWidth = 0;
				currentHeight++;
				//System.out.println("new currentHeight is " + currentHeight);
			}
			
		}
	}
	*/
	
	public void drawAllLayersTrim(int up, int left){
		drawTrim(up, left, 1);
		drawTrim(up, left, 2);
	}
	
	public void drawPlayer(int x, int y, int up, int left){
		context.drawImage(image, (player_tile%TILES_PER_LINE_IN_SOURCE)*getTileWidth(), (player_tile/TILES_PER_LINE_IN_SOURCE)*getTileHeight(), getTileWidth(), getTileHeight(), (x-left)*getTileWidth(), (y-up)*getTileHeight(), getTileWidth(), getTileHeight());
	}
	
	public void drawTrim(int up, int left, int j){
		int numTilesAcrossToDraw = Game.SCREEN_WIDTH/getTileWidth();
		int numRowsDownToDraw = Game.SCREEN_HEIGHT/getTileHeight();
		int firstTile = (MAP_WIDTH*up)+left;
		//System.out.println("firstTile is "+firstTile);
		
		
		int[] layer;
		if(j == 1){
			layer = layer1.getMap();
		} else {
			layer = layer2.getMap();
		}
		
		
		//System.out.println(layer[0]);
		
		for(int currentRow = 0; currentRow < numRowsDownToDraw; currentRow++){
			for(int currentTileInRow = 0; currentTileInRow < numTilesAcrossToDraw; currentTileInRow++){
				if(layer[firstTile+currentTileInRow+currentRow*MAP_WIDTH]!=-1){
					context.drawImage(image, (layer[firstTile+currentTileInRow+currentRow*MAP_WIDTH]%TILES_PER_LINE_IN_SOURCE)*getTileWidth(), (layer[firstTile+currentTileInRow+currentRow*MAP_WIDTH]/TILES_PER_LINE_IN_SOURCE)*getTileHeight(), getTileWidth(), getTileHeight(), currentTileInRow * getTileWidth(), currentRow * getTileHeight(), getTileWidth(), getTileHeight());
				}
			}
		}
		
	}
	
	public void loadMapImage(String path){
		File file = new File(path);
		try{
			image = new Image(file.toURI().toString());
			TILES_PER_LINE_IN_SOURCE = (int) (image.getWidth()/getTileWidth());
			System.out.println("Tiles per line in source is "+TILES_PER_LINE_IN_SOURCE);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadTileMap(String mapname){
		loadMapInfo(mapname+"_MapInfo.txt");
		loadMapImage(imgsrc);
		TILES_PER_LINE_IN_SOURCE = (int) (image.getWidth()/getTileWidth());
		loadTileLayer(mapname+"_Tile Layer 1.csv", 1);
		loadTileLayer(mapname+"_Tile Layer 2.csv", 2);
	}
	
	public void loadMapInfo(String path){
		
		FileIOManager filemanager = new FileIOManager();
		byte[] bytesRead = filemanager.readBytesFromFile(path);
		
		try {
			String byteContents = new String(bytesRead, "UTF-8");
			String[] info = byteContents.split(" ");
			
			for(int i = 0; i<info.length; i++){
				switch(info[i]){
				case "width":
					i++;
					MAP_WIDTH = Integer.parseInt(info[i]);
					System.out.println("MAP_WIDTH is "+MAP_WIDTH);
					break;
				case "height":
					i++;
					MAP_HEIGHT = Integer.parseInt(info[i]);
					System.out.println("MAP_HEIGHT is "+MAP_HEIGHT);
					break;
				case "tilewidth":
					i++;
					setTileWidth(Integer.parseInt(info[i]));
					System.out.println("TILE_WIDTH  is "+getTileWidth());
					break;
				case "tileheight":
					i++;
					setTileHeight(Integer.parseInt(info[i]));
					System.out.println("TILE_HEIGHT is "+getTileHeight());
					break;
				case "source":
					i++;
					imgsrc = info[i];
					System.out.println("imgsrc is "+imgsrc);
					break;
					
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadTileLayer(String path, int layerNumber){

		FileIOManager filemanager = new FileIOManager();
		byte[] bytesRead = filemanager.readBytesFromFile(path);
		
		
		
		try {
			String byteContents = new String(bytesRead, "UTF-8");
			String[] tilestrarray = byteContents.replace(Game.newLine, ",").split(",");
			System.out.println("Is tilestrarray empty? "+(tilestrarray.length==0));
			
			switch(layerNumber){
			case 1:
				layer1.loadInto(tilestrarray);
				break;
			case 2:
				layer2.loadInto(tilestrarray);
				break;
			case 3:
				layer3.loadInto(tilestrarray);
				break;
			default:
				System.err.println("Invalid Tile Layer int");
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	public int getTileHeight() {
		return TILE_HEIGHT;
	}


	public void setTileHeight(int tILE_HEIGHT) {
		TILE_HEIGHT = tILE_HEIGHT;
	}


	public int getTileWidth() {
		return TILE_WIDTH;
	}


	public void setTileWidth(int tILE_WIDTH) {
		TILE_WIDTH = tILE_WIDTH;
	}
	
	

}
