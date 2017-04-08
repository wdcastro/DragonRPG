
public class Apples extends Interactable{
	
	boolean existsInInventory = false;


	public Apples(){
		comment = "There are some apples there";
	}
	
	@Override
	public void nearInteract(){
		comment = "Yummy apples";
		existsInInventory = false;
		for(int i = 0; i < PlayerDataManager.inventory.size(); i++){
			if(PlayerDataManager.inventory.get(i).getName() == "Apples"){
				PlayerDataManager.inventory.get(i).count += 10;
				PlayerDataManager.inventory.get(i).updateLabel();
				existsInInventory = true;
				break;
			}
		}
		
		if(!existsInInventory){
			PlayerDataManager.inventory.add(new InventoryItem("Apples",10));
		}
		
	}
	
	@Override
	public void farInteract(){
		comment = "There are some apples there";
	}
}
