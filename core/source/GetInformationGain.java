package source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public final class GetInformationGain {
	public static final int num_file_word_appear(String folderPath){
		File [] flist = (new File(folderPath)).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".txt");
		    }
		});
		
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
					if(i<5)
					System.out.println("+++++++++++++++"+i+"++++++++++++"+aword);
					if(result.containsKey(aword)){
//						System.out.println("++++++++++++"+result.get(aword)+"+++++++++++++++");
						int temp = result.get(aword)+1;
						result.put(aword, temp);
					} else {
						result.put(aword, 1);
					}	
				}
			}
			br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		Set keys = result.keySet();

		   for (Iterator k = keys.iterator(); k.hasNext();)
		   {
		       String key = (String) k.next();
		       String value = result.get(key)+"";
//		       System.out.println(key+"-----"+value);
		   }
		return 0;
	}
}
