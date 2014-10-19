package source;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

public final class CountFrequency {
	
	/**
	 * 
	 * @param category: news type
	 * @param content: the content of all news belonging to this topic
	 * @return Classifier which contains the frequency count of words and category information 
	 */
	public static Classifier BuildDictionary(String category, String [] content){
		Hashtable<String, Integer> dict = new Hashtable<String, Integer>();
		for(int i=0; i < content.length; i++) {
			if (content[i] == null) break;
			
			if(content[i].matches(".*\\d.*"))
				continue;
			if(dict.containsKey(content[i])){
				Integer count = dict.get(content[i]) + 1;
				dict.put(content[i], count);
			}else
				dict.put(content[i], 1);
		}
		Classifier categoryDic = new Classifier(category, dict);
		return categoryDic;
	}

	/**
	 * ZHU Renjie
	 * To sort the hushtable and put it into an arraylist
	 * @param countTable: count table to be sorted
	 * @param category: the category indicating the topic
	 * @return null 
	 */
	public static void sortValue(Hashtable<String, Integer> countTable, String category) throws IOException {
	
	    //Transfer as List and sort it
	    ArrayList<Map.Entry<String, Integer>> list = new ArrayList(countTable.entrySet());
	    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
	      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	         return o2.getValue().compareTo(o1.getValue());
	     }});
	
	    System.out.println(list);
	    writeDic(list, category);
	 }
	
	/**
	 * ZHU Renjie
	 * To output the sorted arraylist
	 * @param list: an arraylist which contains sorted words
	 * @param category: the category indicating the topic
	 * @return null 
	 */
	public static void writeDic( ArrayList<Map.Entry<String, Integer>> list, String category) throws IOException {
	     PrintWriter out = new PrintWriter(new File(category+"_Dictonary.txt"));
	     
	     out.println( "Rank\t\tWord\t\tCount");
	     
	     for (int i=0; i < list.size(); i++ ) {
	    	 out.println((i+1) + "\t\t" + list.get(i).getKey() + "\t\t" + list.get(i).getValue());
	     }

	     out.close();
	}
//toDo
/*
1. Add the case for the empty file input
*/
}