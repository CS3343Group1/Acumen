package classifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * This class aims to count all the words and its occurrences of one file
 * Result will be stored in HashMap
 * Refactor from Classifier.java and CountFrequency 
 * @version 5
 * @author zfang6
 *
 */
public class SingleFileWordCount {

	private String category;//the category this file belongs to
	private Hashtable <String, Integer> countTable;//Key: word Value: occurrences 
	private String filePath;
	private String outputPath;//file path to store the word frequency count file
	private int wordsNum;//total words number
	
	public SingleFileWordCount(String category, String filePath, String outputPath) {
		super();
		this.category = category;
		this.filePath = filePath;
		this.outputPath = outputPath;
		this.countTable = new Hashtable<String, Integer>();
		getWordFrequency();	
//		write2File(this.outputPath);
	}
	
	public String getCategory() {
		return category;
	}
	public Hashtable<String, Integer> getCountTable() {
		return countTable;
	}
	
	public int getWordsNum(){
		return this.wordsNum;
	}
	
	private void getWordFrequency(){
		ReadContent reader = new ReadContent(filePath);
		String contents[] = reader.processDocument();
		//TODO: please test the correctness of countTable 
		this.wordsNum = contents.length;
		
		for(String word : contents){
			if(!countTable.containsKey(word))
				countTable.put(word, 1);
			else{
				int count = countTable.get(word) + 1;
				countTable.put(word, count);
			}
		}
	}
	
	//store the word count map to a configuration file
	public void write2File(String destinationPath){
		try{    
			File file = new File(destinationPath);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator<Entry<String, Integer>> iter = countTable.entrySet().iterator();
			
			//write to file
			while(iter.hasNext()){
				Entry<String, Integer> entry = iter.next();
				bw.write(entry.getKey() + "\t" + entry.getValue());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
