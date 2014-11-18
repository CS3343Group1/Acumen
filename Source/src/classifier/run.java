package classifier;

import java.util.Hashtable;

public class run {
	public static void main(String [] args){
//		BuildWordCountMatrix test = new BuildWordCountMatrix("/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Source/training_data","/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config");
//		test.buildMatrix();
//		SingleFileWordCount test2 = new SingleFileWordCount("adf", "/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Source/training_data2/computer/x",
//				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/out.txt");
//		Hashtable <String, Integer> test = new Hashtable<String, Integer>();
//		System.out.print(test.get("asdf"));
//		CalculateIG test3 = new CalculateIG("/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/WordCountMatrix.txt",
//				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/categoryMap.txt",
//				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/IG.txt");
//		test3.getIG();
		FeatureSelection.featureSelect(1000, "/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/IG.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/featureWord.txt");
		BuildDocVector test = new BuildDocVector("/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/WordCountMatrix.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/WordNumInFileList.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/featureWord.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/vector.txt");
		test.buildVector();
//		
		BuildVectorForInputFile test2 = new BuildVectorForInputFile("/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/test.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/WordCountMatrix.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/featureWord.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/vector2.txt");
		test2.buildVector();
		
		CalculateSimilarity cs =  new CalculateSimilarity("/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/vector.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/vector2.txt",
				"/home/zfang6/Documents/Cityu/CS3343/Project/Acumen-V2/Release/config/categoryMap.txt"); 
		cs.classify();
	}
}
