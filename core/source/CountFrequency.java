package source;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public final class CountFrequency {
	
	/**
	 * @author ZHU Renjie
	 * @amended on 31/10/2014
	 * @param category: news type
	 * @param content: the content of all news belonging to this topic
	 * @return Classifier which contains the frequency count of words and category information 
	 */
	public static Classifier BuildDictionary(String category, String [] content, HashMap<String, Integer> useless_words){
		Hashtable<String, Integer> dict = new Hashtable<String, Integer>();
		for(int i=0; i < content.length; i++) {
			if (content[i] == null) break;
			
			if(content[i].matches(".*\\d.*"))
				continue;
			//V02s
			if(useless_words.containsKey(content[i]))
				continue;
			//V02e
			if(dict.containsKey(content[i])){
				Integer count = dict.get(content[i]) + 1;
				dict.put(content[i], count);
			}else
				dict.put(content[i], 1);
		}
		Classifier categoryDic = new Classifier(category, dict);
		return categoryDic;
	}

//	/**
//	 * ZHU Renjie
//	 * To sort the hushtable and put it into an arraylist
//	 * @param countTable: count table to be sorted
//	 * @param category: the category indicating the topic
//	 * @return null 
//	 */
//	public static void sortValue(Hashtable<String, Integer> countTable, String category) throws IOException {
//	
//	    //Transfer as List and sort it
//	    ArrayList<Map.Entry<String, Integer>> list = new ArrayList(countTable.entrySet());
//	    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
//	      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//	         return o2.getValue().compareTo(o1.getValue());
//	     }});
//	
//	    System.out.println(list);
//	    /*Jason*/
////	    writeDic(list, category);
//	 }
//	
//	/**
//	 * ZHU Renjie
//	 * To output the sorted arraylist
//	 * @param list: an arraylist which contains sorted words
//	 * @param category: the category indicating the topic
//	 * @return null 
//	 */
//	public static void writeDic( ArrayList<Map.Entry<String, Integer>> list, String category) throws IOException {
//	     PrintWriter out = new PrintWriter(new File(category+"_Dictonary.txt"));
//	     
//	     out.println( "Rank\t\tWord\t\tCount");
//	     
//	     for (int i=0; i < list.size(); i++ ) {
//	    	 out.println((i+1) + "\t\t" + list.get(i).getKey() + "\t\t" + list.get(i).getValue());
//	     }
//
//	     out.close();
//	}
	
	/**
	 * Sort the value according to the word count
	 * @amended by Jason Y J WANG
	 * @amended on 28/10/2014
	 * @param list
	 */
	public static void sortValue(ArrayList<Map.Entry<String, Integer>> list){
	    //Transfer as List and sort it
	    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
	      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	         return o2.getValue().compareTo(o1.getValue());
	     }});
	 }

	/**
	 * Write the word count to files
	 * @amended by Jason Y J WANG
	 * @amended on 28/10/2014
	 * @param countTable
	 * @param title
	 * @param category
	 * @param parent_path
	 * @throws IOException
	 */
	public static void writeDic(Hashtable<String, Integer> countTable, String title, String category, String parent_path) throws IOException {
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList(countTable.entrySet());
		
		sortValue(list);
		
		PrintWriter out = null;
		
		if(!title.equals(category)){
			String path = parent_path+"File_Dictionary/"+category+"_FileDic/";
			makeDir(path);
			out = new PrintWriter(new File(path + title+"_Dictonary.txt"));
		} else {
			String path = parent_path+"Category_Dictionary/";
			makeDir(path);
			out = new PrintWriter(new File(path+ title+"_Dictonary.txt"));
		}
		
		out.println( "Rank\t\tWord\t\tCount");
    
		for (int i=0; i < list.size(); i++ ) {
				out.println((i+1) + "\t\t" + list.get(i).getKey().toUpperCase() + "\t\t" + list.get(i).getValue());
		}
		
		out.close();
	}
	
	/*------------------------------------------PRIVATE METHODS------------------------------------------*/
	
	/**
	 * Make a directory by given name, if exists, do nothing
	 * @author Jason Y J WANG
	 * @created on 28/10/2014
	 * @param name
	 * @throws IOException
	 */
	protected static void makeDir(String name) throws IOException{
		File file_folder = new File(name);
		if(!file_folder.exists()){
			file_folder.mkdirs();	
		}
	}
	
//toDo
/*
1. Add the case for the empty file input
*/
}