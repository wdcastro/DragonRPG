
public class TileLayer {
	private int[] map;

	public void loadInto(String[] array){
		//tilearrayString = textToPrint.split(Game.newLine);
		System.out.println("TileLayer inside loadInto");
		map = new int[array.length];
		for(int i = 0; i < array.length; i++){
			map[i] = Integer.parseInt(array[i]);
			//System.out.println(">>> "+map[i]);
		}
		
	}
	
	public boolean isEmpty(){
		return map.length == 0;
	}
	
	public int[] getMap(){
		return map;
	}
}
