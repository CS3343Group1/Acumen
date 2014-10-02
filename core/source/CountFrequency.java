package source;

import java.util.Hashtable;

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
}

//toDo
/*
1. Add the case for the empty file input
*/