import java.awt.List;
import java.io.File;
import java.util.ArrayList;

public class server {
	public static void main(String[] args){
		//Get initial file names
		ArrayList<String> fileNames = new ArrayList<String>();
		File[] demFiles = new File("D:\\").listFiles();
		
		for(File f: demFiles){
			System.out.println(f.getPath());
			if(f.isHidden()){
				continue;	//Don't index hidden files
			}else{
				fileNames.add(f.getAbsolutePath());
			}
			
		}
		
		for(String name : fileNames){
			Thread someThread = new Thread(new crawler(name));
			someThread.start();
		}
		/*
		Thread first = new Thread(new crawler("D:\\"));
		first.start();
		*/
	}
}
