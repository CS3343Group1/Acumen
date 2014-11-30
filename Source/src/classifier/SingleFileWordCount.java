package classifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * This class aims to count all the words and its occurrences of one file. 
 * Result will be stored in HashMap. 
 * Refactor from Classifier.java and CountFrequency.java. 
 *
 * @author zfang6
 * @version 5
 */
public class SingleFileWordCount {

	/** The category. */
	private String category;//the category this file belongs to
	
	/** The count table. */
	private Hashtable <String, Integer> countTable;//Key: word Value: occurrences 
	
	/** The file path. */
	private String filePath;
	
	/** The output path. */
	private String outputPath;//file path to store the word frequency count file
	
	/** The words num. */
	private int wordsNum;//total words number
	
	/**
	 * Instantiates a new single file word count. Give the input file path and category, output the words and their 
	 * corresponding occurrences to the outputPath given
	 *
	 * @param category the category
	 * @param filePath the file path
	 * @param outputPath the output path
	 */
	public SingleFileWordCount(String category, String filePath, String outputPath) {
		super();
		this.category = category;
		this.filePath = filePath;
		this.outputPath = outputPath;
		this.countTable = new Hashtable<String, Integer>();
		getWordFrequency();	
//		write2File(this.outputPath);
	}
	
	/**
	 * Gets the category. The category this file belongs to.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Gets the count table. Word count table for this document. Key: word; Value: occurrences
	 *
	 * @return  the word frequency count table
	 */
	public Hashtable<String, Integer> getCountTable() {
		return countTable;
	}
	
	/**
	 * Gets the words num. Number of words this documents contains.
	 * Only includes words consists of a-z, A-Z, "-" or "_" 
	 *
	 * @return the words number
	 */
	public int getWordsNum(){
		return this.wordsNum;
	}
	
	/**
	 * Gets the word frequency. Build the word count table of this document. By processing the 
	 * string array passed from ReadContent class
	 *
	 * @return the word frequency count table
	 */
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
	
	/**
	 * store the word count map to a configuration file.
	 *
	 * @param destinationPath the destination path
	 */
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
