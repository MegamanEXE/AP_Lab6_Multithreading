import java.io.*;

public class indexer implements Runnable{
	String buffer;
	
	indexer(String contentToWrite){
		buffer = contentToWrite;
	}
	
	private synchronized void writeToFile(String toWrite){
		try {
			System.out.println("Writing to file...");
			BufferedWriter bf = new BufferedWriter(new FileWriter("D:\\crawlerIndex.txt",true));
			bf.write(buffer);
			
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void run(){
		writeToFile(buffer);
		
	}
}
