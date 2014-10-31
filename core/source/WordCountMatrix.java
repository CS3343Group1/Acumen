package source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class WordCountMatrix {
	/**
	 * Create matrix of word_file count
	 * @author Jason Y J WANG
	 * @created on 30/10/2014
	 * @param category
	 * @param folderPath
	 * @param parent_path
	 * @param flnum
	 * @throws IOException
	 */
	public static final void createMatrix(String category, String folderPath, String parent_path, int flnum) throws IOException{
		
		HashMap<String, ArrayList<Integer>> countMatrix = new HashMap<String, ArrayList<Integer>>();
		
		readFileIntoMemory(countMatrix, folderPath);
		String path = parent_path+"Matrix/";

		CountFrequency.makeDir(path);

		Set<String> keys = countMatrix.keySet();
		Iterator<String> it = keys.iterator();
		
		PrintWriter out = new PrintWriter(new File(path+ category+"_Matrix.txt"));
		while(it.hasNext())
		{		
			String key = (String) it.next();
			ArrayList<Integer> list = countMatrix.get(key);
			
			out.print(key);
			
			for(int i = 0; i < 30-key.length(); i++){
				out.print(" ");
			}

			if(list.size() >= flnum){
				for(int i = 0; i < flnum; i++){
					if(i==0){
						out.print("[");
					}
					
					if(i==flnum-1){
						out.print(list.get(i) + "]");
					} else {
						out.print(format_blank(list.get(i)+""));
					}
					
				}
			} else {
				for(int i = 0; i < list.size(); i++){
					if(i==0){
						out.print("[");
					}
					
					out.print(format_blank(list.get(i) + ""));
				}
				
				for(int i = list.size(); i < flnum; i++){
					if(i==flnum-1){
						out.print("0]");
					} else {
						out.print(format_blank("0"));
					}
				}
			}
			out.println();
		}
		
		out.close();
	}
	
	/**
	 * 
	 * @author Jason Y J WANG
	 * @created on 30/10/2014
	 * @param map
	 * @param folderPath
	 */
	private static void readFileIntoMemory(HashMap<String, ArrayList<Integer>> map, String folderPath){
		File [] files_in_one_cat = (new File(folderPath)).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".txt");
		    }
		});
		
		int flnum = files_in_one_cat.length;
		for(int i = 0; i < flnum; i++){
			File fl = files_in_one_cat[i];
			try {
				BufferedReader br = new BufferedReader(new FileReader(fl));
				
				String aline = br.readLine();
				
				while((aline=br.readLine())!=null){
					String aword = aline.split("\\t\\t")[1].toUpperCase();
					Integer count = Integer.parseInt(aline.split("\\t\\t")[2]);
					if(map.get(aword) == null){
						ArrayList<Integer> list = new ArrayList<Integer>();
						for(int j = 0; j < i; j++){
							list.add(0);
						}
						list.add(count);
						
						map.put(aword.toUpperCase(), list);
					} else {
						ArrayList<Integer> list = map.get(aword);
						list.add(count);
						map.put(aword.toUpperCase(), list);
					}
				}
				
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * @author Jason Y J WANG
	 * @created on 30/10/2014
	 * @param s
	 * @return
	 */
	private static String format_blank(String s){
		if(s.length() == 1){
			return s+","+ "   ";
		} else if(s.length() == 2){
			return s+"," + "  ";
		} else if(s.length() == 3){
			return s +"," + " ";
		}
		return "";
	}
}
