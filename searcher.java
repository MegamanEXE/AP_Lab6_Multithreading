import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class searcher implements Runnable{
	
	Map<String, String> textIndex;
	String stringToSearch;
	public searcher(String toSearch, Map<String, String> argtextIndex) {
		textIndex = argtextIndex;
		stringToSearch = toSearch;
	}
	
	private synchronized void search(){
		System.out.println("Searching thread running...");
		boolean txtFound = false;
		for(String k : textIndex.keySet()){
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(textIndex.get(k))); //Open each text file to search
				
				String buffer;
				while ((buffer = in.readLine()) != null) {
					if(buffer.contains(stringToSearch)){
						System.out.println("Text found in file: " +textIndex.get(k));
						txtFound = true;
						//return textIndex.get(k);
						
					}
				}
				if(txtFound == false){
					System.out.println("Text not found in any file, or at least at this depth");
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void run(){
		search();
	}
}
