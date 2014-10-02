package source;

import java.io.File;

public class mainProcess {
	public static void main(String[] args) {
		String class_path = mainProcess.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String parent_path = class_path.split("core")[0];
		String data_path = parent_path + "raw_data/";
		
		//System.out.println(class_path);
		//System.out.println(data_path);
		
		File folder = new File(data_path);
		File[] categories_path = folder.listFiles();
		Classifier[] categories = new Classifier[categories_path.length];
		
		for(int i=0; i < categories_path.length; i++) {
			//System.out.println(categories_path[i]);
			String[] parts = categories_path[i].toString().split("/");
			String category_name = parts[parts.length - 1];
			//System.out.println(category_name);
			
			IO io = new IO(categories_path[i].toString(), true);
			categories[i] = CountFrequency.BuildDictionary(category_name, io.documentProcessing());
			
			//System.out.println(categories[i].getCategory());
			//System.out.println(categories[i].getCountTable().get("the"));
		}
	}
}
