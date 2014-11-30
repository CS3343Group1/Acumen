package classifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Build word count matrix and also total word count of each file(store in a separate configuration file).
 *
 * @author zfang6
 */
public class BuildWordCountMatrix {
	
	/** The file name map. */
	private Hashtable<String, Integer> categoryMap, fileNameMap;//map the file names and categories to integers
	
	/** The output path. */
	private String trainingDataPath, outputPath;
	
	/** The word count set. */
	private ArrayList<SingleFileWordCount> wordCountSet;//store the word count for all files
	
	/** The file num in category. */
	private ArrayList<Integer> fileNumInCategory;//number of files in each category
	
	/**
	 * Build word count matrix.
	 * Row: the occurrences of one word in each file
	 * Column: all training files
	 *
	 * @param trainingDataPath the training data root folder path
	 * @param outputPath the output path to store the matrix
	 */
	public BuildWordCountMatrix(String trainingDataPath, String outputPath){
		this.trainingDataPath = trainingDataPath;
		this.outputPath = outputPath;
		categoryMap = new Hashtable<String, Integer>();
		fileNameMap = new Hashtable<String, Integer>();
		wordCountSet = new ArrayList<SingleFileWordCount>();
		fileNumInCategory = new ArrayList<Integer>();
	}
	
	/**
	 * build the matrix and store the mapping relationships to configuration file.
	 */
	public void buildMatrix(){
		File rootFolder = new File(trainingDataPath);
		//TODO: here I assume the file hierarchy is valid, to be refactored
		File categories [] = rootFolder.listFiles();
		File categoryMapFile = new File(outputPath + "/map/categoryMap.txt");
		File docMapFile = new File(outputPath + "/map/documentMap.txt");
		try {
			categoryMapFile.createNewFile();
			docMapFile.createNewFile();
			BufferedWriter catBW = new BufferedWriter(new FileWriter(categoryMapFile));
			BufferedWriter docBW = new BufferedWriter(new FileWriter(docMapFile));
			
			
			for(File folder : categories){
				//write to categoryMap file
				String folderName = folder.getName();
				int catIndex = categoryMap.size();//map value of this category
				categoryMap.put(folderName, catIndex);
				catBW.write(folderName+"\t"+catIndex+"\t");
				
				
				//write to documentMap file & build the matrix
				//TODO: we assume under the directory of training data, all are folders, file will cause exception here
				File subFiles [] = folder.listFiles();
				Arrays.sort(subFiles);
				int startIndexOfThisCat = fileNameMap.size();
				catBW.write(""+startIndexOfThisCat + "\t");//TODO: bug here, unreadable code if print int
				catBW.write(""+ subFiles.length);
				catBW.newLine();
				
				fileNumInCategory.add(subFiles.length);
				for(File file : subFiles){
					int fileIndex = fileNameMap.size();
					String fileName = file.getName();
					fileNameMap.put(fileName, fileIndex);
					docBW.write(fileName + "\t" +fileIndex);
					docBW.newLine();
					String currentFileOutputPath = outputPath + "/"+folderName+"/"+fileName;
					wordCountSet.add(new SingleFileWordCount(folderName, file.getAbsolutePath(), currentFileOutputPath));
				}
				
			}
			buildWordMatrix();//build matrix by word count of each file 
			catBW.flush();
			docBW.flush();
			catBW.close();
			docBW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * build matrix by word count of each file
	 */
	private void buildWordMatrix(){
		HashMap<String, ArrayList<Integer>> matrix = new HashMap<String, ArrayList<Integer>>();//key: Word; Value: Occurrences in each file
		//TODO: please test the matrix using some test files
		String commonDictPath = outputPath + "/dictionary/common_word.txt";
		String totalDictPath = outputPath + "/dictionary/total_word.txt";
		ArrayList<Hashtable<String, Integer>> wordCountFileList = new ArrayList<Hashtable<String, Integer>>();
		
		//get all the words first, for the convenience of building matrix, do not need to care fileIndex
		for(int i=0; i<wordCountSet.size(); i++){
			wordCountFileList.add(wordCountSet.get(i).getCountTable());
		}
		BuildDictionary.buildDict(commonDictPath, totalDictPath, wordCountFileList);
		
		//Read total dictionary from text
		ReadContent reader = new ReadContent(totalDictPath);
		HashSet<String> totWordList = new HashSet<String>();//to enhance the searching process
		for(String word : reader.processDocument())
			totWordList.add(word);
		
		//build word count dictionary
		for(String word : reader.processDocument()){
			matrix.put(word, new ArrayList<Integer>());
			for(int i=0; i<wordCountSet.size(); i++){
				Hashtable<String, Integer> countTable = wordCountSet.get(i).getCountTable();
				if(countTable.containsKey(word))
					matrix.get(word).add(countTable.get(word));
				else
					matrix.get(word).add(0);
			}
		}
//		for(int i=0; i<wordCountSet.size(); i++){
//			Hashtable<String, Integer> countTable = wordCountSet.get(i).getCountTable();
//			for(String word : countTable.keySet()){
//				if(!totWordList.contains(word))
//					continue;
//				if(matrix.containsKey(word))
//					matrix.get(word).add(countTable.get(word));
//				else{
//					matrix.put(word, new ArrayList<Integer>());
//					matrix.get(word).add(countTable.get(word));
//				}
//			}
//		}	
				
		//get word count list
		ArrayList<Integer> wordNumInFileList = new ArrayList<Integer>();//total word number in each file
		for(int i=0; i<wordCountSet.size(); i++)
			wordNumInFileList.add(wordCountSet.get(i).getWordsNum());
		
		String matrixPath = outputPath + "/matrix/wordCountMatrix.txt";
		printMatrix(matrix, matrixPath);
		String wordNumPath = outputPath + "/matrix/wordNumInFileList.txt";
		printList(wordNumInFileList,wordNumPath);
	}
	
	/**
	 * Prints the matrix.
	 *
	 * @param matrix the matrix
	 * @param matrixPath the matrix path
	 */
	private void printMatrix(HashMap<String, ArrayList<Integer>> matrix, String matrixPath){
		try{
			File file = new File(matrixPath);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
		
//			//print category each file belongs to in the first line
//			
//			for(int i=0; i<fileNumInCategory.size(); i++){
//				for(int j=0; j<fileNumInCategory.get(i); j++)
//					bw.write(i + "\t");
//			}
//			bw.newLine();
			
			for(String word : matrix.keySet()){
				bw.write(word + "\t");//write word to the file
				for(int i=0; i<matrix.get(word).size(); i++){
					bw.write(matrix.get(word).get(i) + "\t");
				}
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints the list.
	 *
	 * @param wordNumInFileList the word num in file list
	 * @param wordNumPath the word num path
	 */
	private void printList(ArrayList<Integer> wordNumInFileList, String wordNumPath){
		try{
			File file = new File(wordNumPath);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			for(int i=0; i < wordNumInFileList.size(); i++)
				bw.write(wordNumInFileList.get(i) + "\t");//write word to the file
			bw.newLine();
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}