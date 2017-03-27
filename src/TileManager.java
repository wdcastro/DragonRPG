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
	private int TILES_PER_ROW_ON_SCREEN = 40;
	private int TILES_PER_COLUMN_ON_SCREEN = 21;
	
	private int spawnx = 0;
	private int spawny = 0;
	
	String imgsrc;
	TileLayer background = new TileLayer();
	TileLayer objects = new TileLayer();
	Interactable[] interactablelayer;
	TileLayer collision = new TileLayer();
	
	Image image;
	Image playerimage;
	Image tileToDraw;
	
	GraphicsContext context;
	
	public TileManager(GameThread gamethread){
		context = gamethread.getContext();	
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
	
	public void drawAllLayersTrim(int up, int left){
		drawTrim(up, left, 1);
		drawTrim(up, left, 2);
	}
	
	public void drawPlayer(int x, int y, int up, int left){
		context.drawImage(playerimage, (x-left)*TILE_WIDTH, (y-up)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
	}
	
	public void drawTrim(int up, int left, int j){
		int numTilesAcrossToDraw = Game.SCREEN_WIDTH/getTileWidth(); // need to modify these with the number of tiles wide/high so that they dont loop and draw
		int numRowsDownToDraw = Game.SCREEN_HEIGHT/getTileHeight(); // if ( screen width - player x  > tiles to render/2) stop rendering, stop panning camera
		int firstTile = (MAP_WIDTH*up)+left;
		
		int[] layer;
		if(j == 1){
			layer = background.getMap();
		} else {
			layer = objects.getMap();
		}
		
		for(int currentRow = 0; currentRow < numRowsDownToDraw; currentRow++){
			for(int currentTileInRow = 0; currentTileInRow < numTilesAcrossToDraw; currentTileInRow++){
				if(layer[firstTile+currentTileInRow+currentRow*MAP_WIDTH]!=-1){
					context.drawImage(image, (layer[firstTile+currentTileInRow+currentRow*MAP_WIDTH]%TILES_PER_LINE_IN_SOURCE)*getTileWidth(), (layer[firstTile+currentTileInRow+currentRow*MAP_WIDTH]/TILES_PER_LINE_IN_SOURCE)*getTileHeight(), getTileWidth(), getTileHeight(), currentTileInRow * getTileWidth(), currentRow * getTileHeight(), getTileWidth(), getTileHeight());
				}
			}
		}
		
	}
	
	public void loadMapImage(String path){
		File file = new File("res/mapdata/"+path);
		try{
			image = new Image(file.toURI().toString());
			TILES_PER_LINE_IN_SOURCE = (int) (image.getWidth()/getTileWidth());
			System.out.println("Tiles per line in source is "+TILES_PER_LINE_IN_SOURCE);
			playerimage = new Image(new File("res/misc/dragoon chibi.png").toURI().toString());
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
		loadTileLayer(mapname+"_Tile Layer 2.csv", 3);
	}
	
	public void loadMapInfo(String path){
		
		FileIOManager filemanager = new FileIOManager();
		byte[] bytesRead = filemanager.readBytesFromFile("res/mapdata/"+path);

		
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
				case "spawnx":
					i++;
					spawnx = Integer.parseInt(info[i]);
					break;
				case "spawny":
					i++;
					spawny = Integer.parseInt(info[i]);
					break;
				case "source":
					i++;
					imgsrc = info[i];
					System.out.println("imgsrc is "+imgsrc);
					break;
					
				}
			}
			
			TILES_PER_ROW_ON_SCREEN = Game.SCREEN_WIDTH/TILE_WIDTH;
			TILES_PER_COLUMN_ON_SCREEN = Game.SCREEN_HEIGHT/TILE_HEIGHT;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadTileLayer(String path, int layerNumber){

		FileIOManager filemanager = new FileIOManager();
		byte[] bytesRead = filemanager.readBytesFromFile("res/mapdata/"+path);
		
		
		
		try {
			String byteContents = new String(bytesRead, "UTF-8");
			String[] tilestrarray = byteContents.replace(Game.newLine, ",").split(",");
			System.out.println("Is tilestrarray empty? "+(tilestrarray.length==0));
			
			switch(layerNumber){
			case 1:
				background.loadInto(tilestrarray);
				break;
			case 2:
				objects.loadInto(tilestrarray);
				int[] objmap = objects.getMap();
				interactablelayer = new Interactable[objmap.length];
				for(int i = 0; i<objmap.length; i++){
					if(objmap[i]!=-1){
						interactablelayer[i] = new Apples();
						System.out.println("interactable added at " + i);
					}
				}
				break;
			case 3:
				collision.loadInto(tilestrarray);
				int[] map = collision.getMap();
				for(int i = 0; i<map.length; i++){
					System.out.print(map[i]);
					System.out.print(", ");
				}
				break;
			default:
				System.err.println("Invalid Tile Layer int");
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkCollision(int x, int y){
		System.out.println(x+" "+y);
		if(x==-1 || y==-1 || x> MAP_WIDTH || y>MAP_HEIGHT){
			System.out.println("true 1");
			return true;
		} else{
			System.out.println("all adjacent tiles free " + (collision.getMap()[(y*MAP_WIDTH)+x] == -1));
			return collision.getMap()[(y*MAP_WIDTH)+x] != -1;
		}
	}


	public int getTileHeight() {
		return TILE_HEIGHT;
	}

	public void setTileHeight(int height) {
		TILE_HEIGHT = height;
	}


	public int getTileWidth() {
		return TILE_WIDTH;
	}


	public void setTileWidth(int width) {
		TILE_WIDTH = width;
	}
	
	public int getTilesPerRow(){
		return TILES_PER_ROW_ON_SCREEN;
	}
	
	public int getTilesPerColumn(){
		return TILES_PER_COLUMN_ON_SCREEN;
	}
	
	public int getInitialSpawnX(){
		return spawnx;
	}
	
	public int getInitialSpawnY(){
		return spawny;
	}
	
	public Interactable[] getInteractables(){
		return interactablelayer;
	}

}
