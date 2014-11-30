package classifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * @version 5
 * Refactor from CountFrequency.java
 * The word frequency count of a single file will contain all the words in file, but before constructing the matrix, we need to filter out 
 * commonly used words and if one word appears in all files, we add it into the common dictionary
 * 
 * Two dictionaries will be built: common word dictionary and total word dictionary 
 * @author zfang6
 *
 */
public class BuildDictionary {
	
	/**
	 * Build word list dictionary, common word dictionary which contains useless words like "a", "the" and words that appears in all the training files
	 * Total word dictionary which contains all the other words. 
	 * @param commonDictPath  path to store the common dictionary
	 * @param totalDictPath  Path to store the total dictionary
	 * @param wordCountFileList a set of tables which contains all the words and their occurrences in each file
	 */
	public static void buildDict(String commonDictPath, String totalDictPath, ArrayList<Hashtable<String, Integer>> wordCountFileList){
		//read the words in common dictionary
		ReadContent reader = new ReadContent(commonDictPath);
		HashSet<String> comWordSet = new HashSet<String>();//to enhance the searching process
		for(String word : reader.processDocument())
			comWordSet.add(word);
		
		HashMap<String, Integer> totalWords = new HashMap<String, Integer>();
		int fileNum = wordCountFileList.size();
		
		for(Hashtable<String, Integer> table : wordCountFileList){
			for(String word: table.keySet()){
				if(comWordSet.contains(word))
					continue;	
				if(totalWords.containsKey(word)){
					int count = totalWords.get(word) + 1;
					totalWords.put(word, count);
				}else
					totalWords.put(word, 1);
			}
		}
		
		//filter the common word and build the word dictionary
		for(String word : totalWords.keySet()){
			if(totalWords.get(word) == fileNum)//every file have this word
				comWordSet.add(word);
		}
		
		writeToFile(commonDictPath, totalDictPath, comWordSet, totalWords);
	}
	
	/**
	 * Write to file.
	 *
	 * @param commonDictPath the common dict path
	 * @param totalDictPath the total dict path
	 * @param comWordSet the com word set
	 * @param totalWords the total words
	 */
	private static void writeToFile(String commonDictPath, String totalDictPath, HashSet<String> comWordSet, HashMap<String, Integer> totalWords){
		File comDictFile = new File(commonDictPath);
		File totDictFile = new File(totalDictPath);
		try{

			totDictFile.createNewFile();
	
			BufferedWriter comBW = new BufferedWriter(new FileWriter(comDictFile));
			BufferedWriter totBW = new BufferedWriter(new FileWriter(totDictFile));
			
			for(String word : comWordSet){
				comBW.write(word);
				comBW.newLine();
			}
			
			for(String word : totalWords.keySet()){
				if(comWordSet.contains(word))
					continue;
				totBW.write(word + "\t" + totalWords.get(word));
				totBW.newLine();
			}
			
			comBW.flush();
			totBW.flush();
			comBW.close();
			totBW.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
