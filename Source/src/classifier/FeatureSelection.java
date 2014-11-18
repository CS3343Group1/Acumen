package classifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FeatureSelection {

	private static ArrayList<Entry<String, Double>> features = new ArrayList<Entry<String, Double>>();//list stores all the selected feature words
	
	public static void featureSelect(int n, String igPath, String fileStoragePath){
		
		ArrayList<Entry<String, Double>> sortedList = sortMap(igPath);
		
		for(int i=0; i<n; i++){
			features.add(sortedList.get(i));
		}
		
		writeFeatureFile(fileStoragePath);
	}
	
	//sort map according to the value
	private static ArrayList<Entry<String, Double>> sortMap(String igPath){
		Hashtable<String, Double> IGMap = new Hashtable<String, Double>();
	
		//Read IG from file
		try{
			File igFile = new File(igPath);
			BufferedReader igReader = new BufferedReader(new FileReader(igFile));
			String line = null;
			while((line = igReader.readLine()) != null){
				if(line.trim().isEmpty())//omit empty line
					continue;
				String tokens[] = line.split("\t");
				IGMap.put(tokens[0].trim(), Double.parseDouble(tokens[1]));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
		ArrayList<Entry<String, Double>> sortedList = new ArrayList<Entry<String, Double>>(IGMap.entrySet());
		
		//sort the list according to map value
		Collections.sort(sortedList, new Comparator<Map.Entry<String, Double>>(){		
			
			public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2){
				
				//add "-" to sort in descending order
				return -Double.compare(entry1.getValue(), entry2.getValue());
				
				//error for below code, reason: some values are NaN, which will cause exception
//				double difference = entry1.getValue() - entry2.getValue();
////				System.out.println(difference);
//				if(difference > 0)
//					return 1;
//				else if(difference == 0)
//					return 0;
//				else
//					return -1;
				
			}
		});
	
		return sortedList;
	}
	
	private static void writeFeatureFile(String fileStoragePath){
		try{
			File file=new File(fileStoragePath);
			file.createNewFile();
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			
			Iterator<Entry<String, Double>> iter= features.iterator();
			while(iter.hasNext()){
				Entry<String, Double> featureWord = iter.next();
				bw.write(featureWord.getKey() + "\t" + featureWord.getValue());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}