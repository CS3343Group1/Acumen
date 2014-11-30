package classifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

/**
 * The Class BuildDocVector. Build vector for one training file.
 */
public class BuildDocVector {

 	private ArrayList<String> features;//store feature words
 	private int len;//number of feature words
 	private String featurePath;//feature file path
 	private String vectorPath;//document vector storage file path
 	private String matrixPath;//training data
 	private String wordNumListPath;//word number in each file
 	private ArrayList<ArrayList<Double>> dv;//key: document name, value: doc vector before normalization
 	/** The matrix. */
 	private HashMap<String, ArrayList<Short>> matrix;
 	/** The word num in file list. */
 	private ArrayList<Integer> wordNumInFileList;
 	/** The sqr sum. */
 	private double [] sqrSum;
	 
	/**
	 * Instantiates a new builds the doc vector.
	 *
	 * @param matrixPath the matrix path
	 * @param wordNumListPath the word num list path
	 * @param featurePath the feature path
	 * @param vectorPath the vector path
	 */
	public BuildDocVector(String matrixPath, String wordNumListPath, String featurePath, String vectorPath){
		this.matrixPath = matrixPath;
		this.wordNumListPath = wordNumListPath;
		this.features = new ArrayList<String>();
		this.dv = new ArrayList<ArrayList<Double>>();
		matrix = new HashMap<String, ArrayList<Short>>();
		wordNumInFileList = new ArrayList<Integer>();
				
		this.featurePath = featurePath;
		this.vectorPath = vectorPath;
		initFeatures();
	}

	/**
	 * load all the feature words from feature file
	 */
	private void initFeatures(){
		try{
			BufferedReader bfReader = new BufferedReader(new FileReader(this.featurePath));
			String line;
			while((line = bfReader.readLine()) != null){
				this.features.add(line.split("\\s+")[0]);
			}
			bfReader.close();
			
			bfReader = new BufferedReader(new FileReader(this.wordNumListPath));
			while((line = bfReader.readLine()) != null){
				for(String num : line.split("\\s+"))
					if(!num.trim().isEmpty())
						this.wordNumInFileList.add(Integer.parseInt(num));
			}
			bfReader.close();
			
			
			//read matrix
			bfReader = new BufferedReader(new FileReader(this.matrixPath));
			while((line = bfReader.readLine()) != null){
				if(line.trim().isEmpty())
					continue;
				String tokens [] = line.split("\t");
				ArrayList<Short> rows = new ArrayList<Short>();
				for(int i=1; i<tokens.length; i++){
					rows.add(Short.parseShort(tokens[i]));
				}
				matrix.put(tokens[0].trim(), rows);
				
			}
			bfReader.close();

			this.len = this.features.size();
			this.sqrSum = new double[len];
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * build the document vector according to the feature word, normalize vector and store in files
	 */
	public void buildVector(){
		int totFileNum = wordNumInFileList.size();
		for(int i=0; i<totFileNum; i++){//for each file
			for(int j=0; j<features.size(); j++){//for each feature word
				String word = features.get(j);
				int tf = matrix.get(word).get(i);//word appearing frequency in this doc
				int Ni = 0;//number of files containing this word
				ArrayList<Short> row = matrix.get(word);
				for(int k =0; k<totFileNum; k++)
					if(row.get(k)>0)
						Ni++;
				
				double weight = -1.0*tf*Math.log(1.0*Ni/totFileNum + Double.MIN_VALUE);
				sqrSum[j] += Math.pow(weight, 2);
				if(dv.size() <= i){
					ArrayList<Double> v = new ArrayList<Double>();
					v.add(weight);
					dv.add(v);
				}else
					dv.get(i).add(weight);
			}
		} 
		normalizeVector();
	}
	
	/**
	 * normalize vector and store in files
	 */
	private void normalizeVector(){
		Iterator<ArrayList<Double>> iter = dv.iterator();
		File vectorFile = new File(this.vectorPath);
		try{
			vectorFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(vectorFile));
			while (iter.hasNext()) {
				ArrayList<Double> v = iter.next();
				Iterator<Double> it=v.iterator();
				int index = 0;
				while(it.hasNext()){
					double weight = it.next();
					weight/=Math.sqrt(sqrSum[index]);
					bw.write(""+weight+"\t");
					index++;
				}
				bw.newLine();
			}
			
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}