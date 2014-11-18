package classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads content from the given path(Folder or File), and returns a String array
 * This String array will only contain words consisting of [a-zA-Z-], filtering function included
 * Refactor from IO.java
 * @version 5
 * @author zfang6
 *
 */
public class ReadContent {
	private ArrayList<String> words;
	private String inputPath;
	
	public ReadContent(String inputPath){
		this.inputPath = inputPath;
		this.words = new ArrayList<String>();
	}
	
	//Process the path
	public String [] processDocument(){
		File inputFile = new File(inputPath);
		if(!inputFile.exists())
			return new String[0];
		
		if(inputFile.isDirectory()){
			File[] subFiles = inputFile.listFiles();
			
			for(File singleFile : subFiles){
				readAndFilter(singleFile);
			}
		}else{
			readAndFilter(inputFile);
		}
		
		return this.words.toArray(new String[words.size()]);
	}
	
	private void readAndFilter(File srcFile){
		FileReader fr;
		try {
			fr = new FileReader(srcFile);
			 BufferedReader br = new BufferedReader(fr);
			 String line = null;
			 while((line = br.readLine()) != null){
				 //TODO: please add this in test case, see if the split works fine
				 //split by non a-z, A-Z, -, _ ,0-9 characters
				 String tokens[] = line.split("[^a-zA-Z-_0-9]");
				 arrayCopy(tokens);
			 }
			 br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//copy one array to words, omitting the empty elements and words with number
	//Reason we do not split by numbers is to avoid "word2word" -> word word
	private void arrayCopy(String [] arr){
		for(String token: arr){
			if(!token.trim().isEmpty() && token.matches("[a-zA-Z-_]+"))
				this.words.add(token.toLowerCase());//all words in lower case
		} 
	}
}
