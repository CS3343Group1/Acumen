package classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Class CalculateSimilarity. Using the vector matrix and user input file vector to calculate the similarity
 * between two files. Larger cos value means higher similarity.
 */
public class CalculateSimilarity {
	
	/** The in doc vector. */
	private ArrayList<Double> inDocVector = new ArrayList<Double>();
	
	/** The vector matrix. */
	private ArrayList<ArrayList<Double>> vectorMatrix = new ArrayList<ArrayList<Double>>();
	
	/** The similarity. */
	private HashMap<Integer,Double> similarity = new HashMap<Integer,Double>();
	
	/** The cat map. */
	private HashMap<Integer, String> catMap = new HashMap<Integer, String>();
	
	/** The start index of cat. */
	private ArrayList<Integer> startIndexOfCat = new ArrayList<Integer>();
	
	/**
	 * Instantiates a new calculate similarity by reading vector matrix from vectorMatrixPath and input file
	 * vector from inFileVectorPath.  
	 *
	 * @param vectorMatrixPath the vector matrix path that contains vectors for all training files.
	 * @param inFileVectorPath the in file vector path that contains vector for the file to be classified
	 * @param catMapPath the cat map path that store the mapping relationship between matrix rows and file names.
	 */
	public CalculateSimilarity(String vectorMatrixPath, String inFileVectorPath, String catMapPath){
		try{
			BufferedReader bf = new BufferedReader(new FileReader(vectorMatrixPath));
			String line;
			while((line = bf.readLine()) != null){
				String numbers[] = line.split("\t");
				ArrayList<Double> oneVector = new ArrayList<Double>();
				for(String num : numbers){
					if(!num.trim().isEmpty())
						oneVector.add(Double.parseDouble(num));
				}
				vectorMatrix.add(oneVector);
			}
			bf.close();
			
			bf = new BufferedReader(new FileReader(inFileVectorPath));
			while((line = bf.readLine()) != null){
				String numbers[] = line.split("\t");
				for(String num : numbers){
					if(!num.trim().isEmpty())
						inDocVector.add(Double.parseDouble(num));
				}
			}
			bf.close();
			
			bf = new BufferedReader(new FileReader(catMapPath));
			while((line = bf.readLine()) != null){
				if(line.trim().isEmpty())
					continue;
				String tokens[] = line.split("\t");
				catMap.put(Integer.parseInt(tokens[1]), tokens[0].trim());
				startIndexOfCat.add(Integer.parseInt(tokens[2]));
			}
			bf.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * classify the document. By calculating the cos value between input file and all training files, we select the 10% highest 
	 * cos values, and see the category that most files belongs to which it the possible result.
	 */
	public void classify(){
		for(int i=0; i<vectorMatrix.size(); i++){
			similarity.put(i, cos(inDocVector, vectorMatrix.get(i)));
		}
		
		ArrayList<Entry<Integer, Double>> sortedList = new ArrayList<Entry<Integer, Double>>(similarity.entrySet());
		
		//sort the list according to map value
		Collections.sort(sortedList, new Comparator<Map.Entry<Integer, Double>>(){		
			
			public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2){
				//add "-" to sort in descending order
				return -Double.compare(entry1.getValue(), entry2.getValue());
			}
		});
		
		//take top 10% and get its category
		int [] categories = new int[catMap.size()];
		for(int i=0; i<sortedList.size()*0.1; i++){
			categories[categoryBelongsTo(sortedList.get(i).getKey())] ++;
		}
		
		//find the max value
		int max = 0, resultCat = 0;
		for(int i=0; i<categories.length; i++){
			if(categories[i] > max){
				max = categories[i];
				resultCat = i;
			}
				
		}
		
		System.out.println("Belongs to " + catMap.get(resultCat));
		
	}
	
	/**
	 * calculate the cos.
	 *
	 * @param v1 the v1
	 * @param v2 the v2
	 * @return the double
	 */
	private double cos(ArrayList<Double> v1, ArrayList<Double> v2) {
		 double res = 0.0;
		 double mul = 0.0;
		 double p1 = 0.0, p2 = 0.0;
		 for (int i = 0; i < v1.size(); i++) {
			double one = v1.get(i);
		 	double two = v2.get(i);
		 	mul += one * two;
		 	p1 += Math.pow(one, 2);
		 	p2 += Math.pow(two, 2);
		 }
		 res = Math.abs(mul) / Math.sqrt(p1 * p2);
		 return res;
	}
	
	/**
	 * Category belongs to.
	 *
	 * @param fileIndex the file index
	 * @return the int
	 */
	private int categoryBelongsTo(int fileIndex){
		for(int i=0; i<startIndexOfCat.size(); i++){
			if(i == startIndexOfCat.size() -1)
				return i;
			if(fileIndex >= startIndexOfCat.get(i) && fileIndex < startIndexOfCat.get(i+1))
				return i;
		}
		return -1;
	}
}
