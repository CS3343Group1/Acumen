package Statistics;

import java.util.Hashtable;

import Dictionary.Classifier;

public final class CountFrequency {
	
	/**
	 * 
	 * @param category: news type
	 * @param content: the content of all news belonging to this thpe
	 * @return Classifier which contains the frequency count of words and category information 
	 */
	public static Classifier BuildDictionary(String category, String [] content){
		Hashtable<String, Integer> dict = new Hashtable<String, Integer>();
		for(String word: content){
			if(word.matches(".*\\d.*"))
				continue;
			if(dict.containsKey(word)){
				Integer count = dict.get(word) + 1;
				dict.put(word, count);
			}else
				dict.put(word, 1);
		}
		Classifier categoryDic = new Classifier(category, dict);
		return categoryDic;
	}
}
