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
import java.util.List;
import java.util.Map.Entry;

/**
 * This class reads the matrix file and then compute the information gain accordingly
 * @author zfang6
 *
 */
public class CalculateIG {
	private int categoryNum, totFileNum;
	private ArrayList<Integer> num_file_in_one_cat = new ArrayList<Integer>();
	private HashMap<String, ArrayList<Short>> matrix = new HashMap<String, ArrayList<Short>>();
	private Hashtable<String, Double> info_gain = new Hashtable<String, Double>();
	private String igPath;//path to store the IG
	
	/**
	 * 
	 * @param matrixPath file path that stores the matrix
	 * @param catMap file path that stores category map
	 */
	public CalculateIG(String matrixPath, String catMap, String igPath){
		this.igPath = igPath;
		initMatrix(matrixPath, catMap);
	}

	private void initMatrix(String matrixPath, String catMap){
		File matrixFile = new File(matrixPath);
		File catFile = new File(catMap);
		
		this.categoryNum = 0;
		this.totFileNum = 0;
		try{
			BufferedReader matrixReader = new BufferedReader(new FileReader(matrixFile));
			BufferedReader catReader = new BufferedReader(new FileReader(catFile));
			
			String line = null;
			while((line = catReader.readLine()) != null){
				if(line.trim().isEmpty())
					continue;
				String tokens [] = line.split("\t");
				int fileCount = Integer.parseInt(tokens[3]);//number of files in one category
				num_file_in_one_cat.add(fileCount);
				totFileNum += fileCount;
				categoryNum++;
			}
			catReader.close();
			
			//read matrix
			while((line = matrixReader.readLine()) != null){
				if(line.trim().isEmpty())
					continue;
				String tokens [] = line.split("\t");
				ArrayList<Short> rows = new ArrayList<Short>();
				for(int i=1; i<tokens.length; i++){
					rows.add(Short.parseShort(tokens[i]));
				}
				matrix.put(tokens[0].trim(), rows);
				
			}
			matrixReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void getIG(){
		double entropy = Math.log(categoryNum);
		Iterator<Entry<String, ArrayList<Short>>> iter = matrix.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, ArrayList<Short>> entry = iter.next();
			String word = entry.getKey();
			ArrayList<Short> oneRow = entry.getValue();
			int wFileCount = 0;//total file number that contains this word
			int [] wFileCountCat = new int[this.categoryNum];//file number containing this word in each category
			double pw = 0;//No. files containing word/Total file number
			double[] pcw = new double[this.categoryNum];//percentage of each category when word appears
			double[] pcw_b = new double[this.categoryNum];//percentage of each category when words do not appear
			
			int position =0;
			for(int i=0; i<this.categoryNum; i++){
				for(int j=0; j<num_file_in_one_cat.get(i); j++){
					if(oneRow.get(position) > 0)
						wFileCountCat[i]++;
					position ++;
				}
				wFileCount += wFileCountCat[i];
			}
			pw = 1.0 * wFileCount / this.totFileNum;
			for(int i=0; i<this.categoryNum; i++){
				pcw[i] = 1.0*wFileCountCat[i]/wFileCount;
				pcw_b[i] = 1.0*(num_file_in_one_cat.get(i)-wFileCountCat[i])/(this.totFileNum - wFileCount);//Note that pcw_b may be 0
			}
			double entropy_w = 0, entropy_wb =0;
			
			for(int i=0; i<this.categoryNum; i++){
				entropy_w += pcw[i] * Math.log(pcw[i] + Double.MIN_VALUE);//to avoid pcw[i] = 0 case
				entropy_wb += pcw_b[i] * Math.log(pcw_b[i] + Double.MIN_VALUE);//to avoid pcw_b[i] = 0 case
			}
			double ig = entropy + pw * entropy_w + (1 - pw) * entropy_wb;
			info_gain.put(word, ig);
		}
		write2File();
		//TODO:Please test the IG, to get IG, use:
		//CalculateIG obj = new CalculateIG("path1","path2","path3");
		//obj.getIG();
	}
	
	public void write2File(){
		try{    
			File file = new File(igPath);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator<String> iter = info_gain.keySet().iterator();
			
			//write to file
			while(iter.hasNext()){
				String word = iter.next();
				bw.write(word + "\t" + info_gain.get(word));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
}
