import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class SpellManager {
	//open file, read spells on load
	//translate them to effects return values
	File spellFile;
	byte[] bytesRead;
	int	amountRead;
	int remainingFileSize;
	int currentOffset = 0;
	
	public SpellManager() {
		initialise();
	}
	
	private void initialise() {
		
		System.out.println("Initialising SpellManager");
		try	{
			
			spellFile = new File("spells.dgn");
			
			if (spellFile.exists()){
				remainingFileSize = (int) spellFile.length();
				bytesRead = new byte[(int) remainingFileSize];
				System.out.println("spellFile length is " + spellFile.length());
				
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(spellFile));
				// The root directory is DragonRPG, above bin and src
				
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
				// We're done
				in.close();
				String textToPrint = new String(bytesRead, "UTF-8");
				System.out.println(textToPrint);
			} else {
				System.out.println("File doesn't exist");
					
			}

		} catch (FileNotFoundException e) {
			System.err.println("SpellManager: initialise: FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("SpellManager: initialise: IOException");
			e.printStackTrace();
		}
	}
	
	
}
