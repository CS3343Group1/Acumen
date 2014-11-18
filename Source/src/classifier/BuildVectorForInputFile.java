package classifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class BuildVectorForInputFile {

	 private ArrayList<String> features;//store feature words
	 private String inFile;
	 private String featurePath;//feature file path
	 private String vectorPath;//document vector storage file path
	 private String matrixPath;//training data
	 private ArrayList<Double> dv;//key: document name, value: doc vector before normalization
	 private HashMap<String, ArrayList<Short>> matrix;
	 
	public BuildVectorForInputFile(String inFile, String matrixPath, String featurePath, String vectorPath){
		this.inFile = inFile;
		this.matrixPath = matrixPath;
		this.features = new ArrayList<String>();
		this.dv = new ArrayList<Double>();
		matrix = new HashMap<String, ArrayList<Short>>();
		this.featurePath = featurePath;
		this.vectorPath = vectorPath;
		initFeatures();
	}

	//load all the feature words from feature file
	private void initFeatures(){
		try{
			BufferedReader bfReader = new BufferedReader(new FileReader(this.featurePath));
			String line;
			while((line = bfReader.readLine()) != null){
				this.features.add(line.split("\\s+")[0]);
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

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//build the document vector
	public void buildVector(){
		SingleFileWordCount sf = new SingleFileWordCount(null,inFile,null);
		Hashtable<String, Integer> table = sf.getCountTable();
		
		for(int j=0; j<features.size(); j++){//for each feature word
			String word = features.get(j);
			if(table.containsKey(word)){
				int tf = table.get(word);//word appearing frequency in this doc
				int Ni = 0;//number of files containing this word
				ArrayList<Short> row = matrix.get(word);
				for(int k =0; k<row.size(); k++)
					if(row.get(k)>0)
						Ni++;
				
				int totFileNum = row.size();
				double weight = -1.0*tf*Math.log(1.0*Ni/totFileNum + Double.MIN_VALUE);
				dv.add(weight);
			}else
				dv.add(0.0);
			
		}
		
		write2File();
	}
	
	private void write2File(){
		File vectorFile = new File(this.vectorPath);
		try{
			vectorFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(vectorFile));
			Iterator iter = dv.iterator();
			while (iter.hasNext()) {
				bw.write(""+iter.next()+"\t");
			}
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
