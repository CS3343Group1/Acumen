package classifier;

public class Classifier {
	public static void main(String [] args){
		//2 arguments accepted, training data path and user input file path
		if(args.length < 1){
			System.out.println("Please input at least 1 argument \n [file to be classified] optional: [user defined training data path]");
			System.exit(0);
		}
		if(args.length == 1){//use our own training data set
			String basePath = Classifier.class.getProtectionDomain().getCodeSource().getLocation().getPath().split("bin")[0];
			String inFilePath = args[0];
			String trainingDataRoot = basePath + "/Release/training_data/";
			String matrixRoot = basePath +"/Release/config/matrix/";
			String mapRoot = basePath + "/Release/config/map/";
			String vectorRoot = basePath + "/Release/config/vector/";
			String dictionaryRoot = basePath + "/Release/config/dictionary";
			
			//if word count matrix does not exist, build it
			File wordCountMatrixFile = new File(matrixRoot+ca)
			if()
		}
	}
}
