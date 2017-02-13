import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;


public class FileIOManager {
	public FileIOManager(){
		
	}
	
	public byte[] readBytesFromFile(String path){
		File file = new File(path);
		int currentOffset = 0;

		int remainingFileSize = (int) file.length();
		byte[] bytesRead = new byte[(int) remainingFileSize];
		int	amountRead = 0;
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
		} catch(Exception e){
			e.printStackTrace();
		}
		return bytesRead;
	}
		
}
