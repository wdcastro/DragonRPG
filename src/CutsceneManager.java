import java.util.ArrayDeque;

public class CutsceneManager {
	
	boolean cutsceneActive = false;
	boolean cutsceneOver = false;
	GameThread gamethread;
	ArrayDeque<String> cutsceneItems = new ArrayDeque<String>();

	public CutsceneManager(GameThread gamethread){
		this.gamethread = gamethread;
	}
	
	public void startCutscene(){
		//load lines and animations
		getCutscene();
		cutsceneCallback();
	}
	
	public void cutsceneCallback(){
		if(!cutsceneOver){
			if(cutsceneItems.peekFirst().startsWith("d")){
				ArrayDeque<String> lines = new ArrayDeque<String>();
				for(int i = 0; i< cutsceneItems.size(); i++){
					if(!cutsceneItems.peekFirst().startsWith("d")){
						break;
					}
					lines.addLast(cutsceneItems.removeFirst());
				}
				System.out.print("Lines: ");
				System.out.println(lines);
				gamethread.dialogmanager.addLines(lines.toArray(new String[lines.size()]));
				gamethread.dialogmanager.showDialogBox(true);
				//check list of actions to do
				if(cutsceneItems.isEmpty()){
					cutsceneOver = true;
				}
			} else if(cutsceneItems.peekFirst().startsWith("a")){
				ArrayDeque<String> animations = new ArrayDeque<String>();
				System.out.println("animation item");
				for(int i = 0; i< cutsceneItems.size(); i++){
					if(!cutsceneItems.peekFirst().startsWith("a")){
						break;
					}
					animations.addLast(cutsceneItems.removeFirst());
				}
				gamethread.mapanimationcontroller.addAnimations(animations.toArray(new String[animations.size()]));
				gamethread.mapanimationcontroller.startAnimation();
				if(cutsceneItems.isEmpty()){
					cutsceneOver = true;
				}
			}
		}
		
	}
	
	public void getCutscene(){ //from file
		cutsceneItems.addLast("d iltas yaharro");
		cutsceneItems.addLast("d iltas idinaxui");
		cutsceneItems.addLast("d iltas blyat");
		cutsceneItems.addLast("a iltas right");
		cutsceneItems.addLast("a iltas right");
		cutsceneItems.addLast("a iltas right");
		cutsceneItems.addLast("d asuna yume de taaaakaku");
		cutsceneItems.addLast("d asuna TOOONNNdaaaaa");
		cutsceneItems.addLast("d asuna HIROGARI ATTEEE");
		cutsceneItems.addLast("a iltas left");
		cutsceneItems.addLast("a iltas left");
		cutsceneItems.addLast("a iltas left");
		
	}
	
	synchronized public boolean isCutscenePending(){
		//check flags
		return true;
	}
	
	synchronized public boolean isCutsceneActive(){
		return cutsceneActive;
	}
}
