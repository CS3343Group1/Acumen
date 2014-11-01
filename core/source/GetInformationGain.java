package source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/**
 * @author Jason Y J WANG
 */
public final class GetInformationGain {
	private double entropy;
	private int [] num_file_in_one_cat;
	private int tot_file_num;
	private List<Hashtable<String, Integer>> num_file_word_appear_one_cat = new ArrayList<Hashtable<String, Integer>>();
	private Hashtable<String, Integer> num_file_word_appear_all_cat = new Hashtable<String, Integer>();;
	private Hashtable<String, Double> info_gain = new Hashtable<String, Double>();
	
	/**
	 * @author Jason Y J WANG
	 * @created on 30/10/2014
	 * @amended on 31/10/2014
	 * @param file_dic_path
	 */
	public GetInformationGain(String file_dic_path){
		File [] fileFolder = (new File(file_dic_path)).listFiles();
		this.entropy = Math.log(fileFolder.length);
		num_file_in_one_cat = new int[fileFolder.length];
		
		List<Hashtable<String, Integer>> num_file_word_appear_one_cat_temp = new ArrayList<Hashtable<String, Integer>>();
		
//		System.out.println(fileFolder.length);
		
		for(int i = 0; i < fileFolder.length; i++){
			File [] files_in_one_cat = fileFolder[i].listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".txt");
			    }
			});
			num_file_in_one_cat[i] = files_in_one_cat.length;
			
			Hashtable<String, Integer> temp = new Hashtable<String, Integer>();
			
			temp = num_file_word_appear(files_in_one_cat);
		
			num_file_word_appear_one_cat_temp.add(temp);
		}
		
		tot_file_num = get_total_file_num(num_file_in_one_cat);
		this.num_file_word_appear_one_cat = num_file_word_appear_one_cat_temp;
		this.num_file_word_appear_all_cat = get_file_count_for_all_cat(num_file_word_appear_one_cat_temp);
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param matrix_path
	 */
	public void getIGMatrix(String matrix_path){
		int num_cat = num_file_in_one_cat.length;
		Hashtable<String, Integer> one_cat_count = null;
		Hashtable<String, Integer> all_cat_count = this.num_file_word_appear_all_cat;
		Hashtable<String, List<Double>> pcwMap = new Hashtable<String, List<Double>>();
		Hashtable<String, List<Double>> pcw_bMap = new Hashtable<String, List<Double>>();
		
		
		for(int i = 0; i < num_cat; i++){
			
			one_cat_count = this.num_file_word_appear_one_cat.get(i);
			
			Set<String> keys = one_cat_count.keySet();
			
			Iterator<String> it = keys.iterator();
			
			while(it.hasNext()){
				String key = it.next();
				int tot_num_file;
				int num_file = one_cat_count.get(key);
				
				
				if(all_cat_count.get(key) != null){
					tot_num_file = all_cat_count.get(key);
					double pcw = num_file*1.0/tot_num_file;
					double pcw_b;
					 /*bug*/
					if(tot_file_num-tot_num_file == 0){
						pcw_b = 0;
					} else {
						pcw_b = (num_file_in_one_cat[i]-num_file)*1.0/(tot_file_num-tot_num_file);
//						if(pcw_b <0)
//						System.out.println(pcw_b);
						
//						if(pcw < 0){
//							System.out.println(pcw);
//						}
//						
//						if(num_file_in_one_cat[i]-num_file <0){
//							System.out.println(num_file_in_one_cat[i]-num_file);
//						}
//						
//						if(tot_file_num-tot_num_file <0){
//							System.out.println(tot_file_num-tot_num_file);
//						}
					}
					
					if(pcwMap.get(key) == null){
						List<Double> tp_pcw = new ArrayList<Double>();
						List<Double> tp_pcw_b = new ArrayList<Double>();
						tp_pcw.add(pcw);
						tp_pcw_b.add(pcw_b);
						
						pcwMap.put(key, tp_pcw);
						pcw_bMap.put(key, tp_pcw_b);
					} else {
						List<Double> tp_pcw = pcwMap.get(key);
						List<Double> tp_pcw_b = pcw_bMap.get(key);
						tp_pcw.add(pcw);
						tp_pcw_b.add(pcw_b);
						pcwMap.put(key, tp_pcw);
						pcw_bMap.put(key, tp_pcw_b);
						
//						System.out.println(pcwMap.get(key));
					}	
				} else {
					System.out.println("no key:" + key);
				}	
			}
		}
		
		Set<String> keys = pcwMap.keySet();
		
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext()){
			String key = it.next();
			List<Double> pcwf = pcwMap.get(key);
			List<Double> pcw_bf = pcw_bMap.get(key);
			
			if(pcwf.size() != pcw_bf.size()){
				System.out.println("ERROR");
			} else {
				double pw = all_cat_count.get(key)*1.0/tot_file_num;
				double IG = this.entropy + pw + getLogSum(pcwf) + (1-pw)*getLogSum(pcw_bf);
				
				if(info_gain.get(key) != null){
					System.out.println("ERROR2");
				} else {
					info_gain.put(key, IG);
				}
			}
		}
		 
		try {
			write_IG(matrix_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param list
	 * @return
	 */
	private Hashtable<String, Integer> get_file_count_for_all_cat(List<Hashtable<String, Integer>> list){
		Hashtable<String, Integer> all_cat_count = new Hashtable<String, Integer>();
		
		for(int i = 0; i < list.size(); i++){
			Hashtable<String, Integer> temp = list.get(i);
			
			Set<String> keys = temp.keySet();
			
			Iterator<String> it = keys.iterator();
			
			while(it.hasNext()){
				String key = it.next();
				if(all_cat_count.containsKey(key)){
					all_cat_count.put(key, all_cat_count.get(key)+temp.get(key));
				} else {
					all_cat_count.put(key, temp.get(key));
				}
			}
		}
		
		return all_cat_count;
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param flist
	 * @return
	 */
	private Hashtable<String, Integer> num_file_word_appear(File [] flist){
//		for test
//	public static final int num_file_word_appear(String folderPath){
		
		int flCount = flist.length;
		Hashtable<String, Integer> result = new Hashtable<String, Integer>();
		try{
			BufferedReader br = null;
			for(int i = 0; i < flCount; i++){
				File fl = flist[i];
				br = new BufferedReader(new FileReader(fl));
				
				String aline = br.readLine();
				while((aline=br.readLine()) != null){
					String aword = aline.split("\\t\\t")[1].toUpperCase();
					
					if(result.containsKey(aword)){
						int temp = result.get(aword)+1;
						if(temp>=flCount){
							result.put(aword,  flCount);
						} else{
							result.put(aword, temp);
						}
					} else {
						result.put(aword, 1);
					}	
				}
			}
			br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
//		Set<String> keys = result.keySet();
//
//		   for (Iterator<String> k = keys.iterator(); k.hasNext();)
//		   {
//		       String key = (String) k.next();
//		       String value = result.get(key)+"";
//		       System.out.println(key+"-----"+value);
//		   }
		  return result;
//		   return 0;for test
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param n
	 * @return
	 */
	private static int get_total_file_num(int [] n){
		int sum = 0;
		for(int i = 0; i < n.length; i++){
			sum += n[i];
		}
		return sum;
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param d
	 * @return
	 */
	private static double getLogSum(List<Double> d){
		double temp = 0;
		for(int i = 0; i < d.size(); i++){
			temp+=d.get(i)*getLog(d.get(i));
		}
		return temp;
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param f
	 * @return
	 */
	private static double getLog(double f){
		return f*Math.log(f)*1.0;
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param folderPath
	 * @return
	 */
	public static int count_num_files(String folderPath){
		File [] flist = (new File(folderPath)).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".txt");
		    }
		});
		
		return flist.length;
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 31/10/2014
	 * @param path
	 * @throws IOException
	 */
	private void write_IG(String path) throws IOException{
		Set<String> keys = this.info_gain.keySet();
		
		Iterator<String> it = keys.iterator();
		PrintWriter out = new PrintWriter(new File(path+"/Information_ Gain.txt"));
		
		while(it.hasNext()){
			String key = it.next();
			double ig = this.info_gain.get(key);
			
			out.println(key + "\t\t\t\t\t\t\t\t" +ig);
		}
		
		out.close();
	}
}
