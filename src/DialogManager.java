import java.util.ArrayDeque;


public class DialogManager extends Thread{
	
	DialogBox dialogbox;
	boolean isReady = false;
	boolean isCutscene = false;
	GameThread gamethread;

	ArrayDeque<String> lines = new ArrayDeque<String>();
	
	public DialogManager(GameThread gamethread){
		this.gamethread = gamethread;
		dialogbox = new DialogBox(gamethread);
		isReady = true;
	}
	
	public void addLines(String[] linesToAdd){
		for(int i = 0; i< linesToAdd.length; i++){

			System.out.println(linesToAdd[i]);
			lines.addLast(linesToAdd[i]);
		}
	}
	
	public void addLines(String lineToAdd){
		System.out.println(lineToAdd);
			lines.addLast(lineToAdd);

	}
	
	public void clearLines(){
		lines.clear();
	}
	
	public void showDialogBox(boolean isCutscene){
		this.isCutscene = isCutscene;
		dialogbox.setContent(lines.removeFirst());
		dialogbox.show();
	}
	
	public void forward() {
		if(dialogbox.isTyping){
			dialogbox.skip();
		} else if(!lines.isEmpty()){

			dialogbox.hide();
			dialogbox.setContent(lines.removeFirst());
			dialogbox.show();
		}
		else { // finished
			dialogbox.hide();
			if(isCutscene){
				isCutscene = false;
				gamethread.cutscenemanager.cutsceneCallback();
			}
		}
	}

	synchronized public boolean isReady() {
		return isReady;
	}


	public boolean isShowing() {
		return dialogbox.isShowing();
	}


	public void update() {
		dialogbox.update();
		
	}

	public void printLines() {
		System.out.println(lines);
	}
	

}
