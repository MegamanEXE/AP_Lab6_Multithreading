import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class crawler implements Runnable{
	Thread indexerThread;
	Thread indexerThread2;
	Thread searchThread;
	String startingPath;
	public crawler(String argStartingPath){
		startingPath = argStartingPath;
	}
	
	public void run() 
	{
		//String startPath = "D:\\";
		int MAX_DEPTH = 500;
		int currentDepth = 0;
		
		String fileSearch = "Megaman";
		String txtSearch = "no idea";
		
		File[] demFiles = new File(startingPath).listFiles();
		Map<String, String> fileIndex = new HashMap<String, String>();
		Map<String, String> textIndex = new HashMap<String, String>();
		
		
		//To get things started
		for(File f: demFiles){
			//System.out.println(f.getName());
			System.out.println(f.getPath());
			if(f.isHidden()){
				continue;	//Don't index hidden files
			}else{
				fileIndex.put(f.getName(), f.getPath());
				if(f.getName().contains(".txt")){
					textIndex.put(f.getName(), f.getAbsolutePath());
					String toWrite = f.getName() + " | " +f.getAbsolutePath() + "\n";
					indexerThread = new Thread(new indexer(toWrite));
					indexerThread.start();
				}
			}
			
		}
		
		//The actual crawling begins
		Map<String, String> temp = new HashMap<String, String>(); //workaround for concurrent access and modification
		
		for(Map.Entry<String, String> it : fileIndex.entrySet()){
			//Don't go over the limit
			if(currentDepth==MAX_DEPTH)
				break;	
			
			String p = it.getKey(); //Current key in consideration
			if(p.contains(".txt")){
				textIndex.put(p, fileIndex.get(p));
			}else{
				File[] localFiles = new File(fileIndex.get(p)).listFiles();
				
				for(File f : localFiles){
					if(f.isHidden()){
						continue;
					}else{
						temp.put(f.getName(), f.getAbsolutePath());
						String toWrite = f.getName() + " | " +f.getAbsolutePath() + "\n";
						indexerThread2 = new Thread(new indexer(toWrite));
						indexerThread2.start();
					}
				}
				currentDepth++;
			}
		}
		
		fileIndex.putAll(temp); //put back all the temporary entries into the main index
		
		//Print hash map for checking
		//System.out.println(fileIndex);
		
		//Searching file (WAS HERE)
		boolean fileFound = false;
		for(String k : fileIndex.keySet()){
			if(fileIndex.get(k).contains(fileSearch)){
				System.out.println("Found at: " +fileIndex.get(k));
				fileFound = true;
			}
		}
		
		if(fileFound==false){
			System.out.println("File not found, or at least at this depth");
		}
		
		//Searching text
		searchThread = new Thread(new searcher(txtSearch, textIndex));
		searchThread.start();
		
}
}
