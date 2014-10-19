package source;

import java.util.Hashtable;

public class Classifier {
	private String category;
	private Hashtable <String, Integer> countTable;
	public Classifier(String category, Hashtable<String, Integer> countTable) {
		super();
		this.category = category;
		this.countTable = countTable;
	}
	public String getCategory() {
		return category;
	}
	public Hashtable<String, Integer> getCountTable() {
		return countTable;
	}
	
	
	
}