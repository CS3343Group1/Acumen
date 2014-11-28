package classifier;

import java.io.File;
import java.io.IOException;

public class Classifier {
	public static void main(String [] args) throws IOException{
		//2 arguments accepted, training data path and user input file path
		if(args.length < 1){
			System.out.println("Please input at least 1 argument \n [file to be classified]");
//			System.exit(0);
		}
		else{
			String basePath = Classifier.class.getProtectionDomain().getCodeSource().getLocation().getPath().split("Source")[0];
			String inFilePath, trainingDataRoot;
			boolean needRetrain = false;
			
			if(args.length == 1){
				inFilePath = args[0];
				trainingDataRoot = basePath+"/Release/training_data";//default training data root, not suitable for running in eclipse, relative path problem
			}else{
				inFilePath = args[1];
				trainingDataRoot = args[0];
				needRetrain = true;
			}
			
			//create these directories if not exist
			File directory;
			String configRoot = basePath + "/Release/config";
			String matrixRoot = basePath +"/Release/config/matrix";
			directory = new File(matrixRoot);
			directory.mkdirs();
			String mapRoot = basePath + "/Release/config/map";
			directory = new File(mapRoot);
			directory.mkdirs();
			String vectorRoot = basePath + "/Release/config/vector";
			directory = new File(vectorRoot);
			directory.mkdirs();
			String dictionaryRoot = basePath + "/Release/config/dictionary";
			directory = new File(dictionaryRoot);
			directory.mkdirs();
			String tempRoot = basePath + "Release/config/temp";//store user input file vector
			directory = new File(tempRoot);
			directory.mkdirs();
			
			
			//if word count matrix does not exist, build it
			File wordCountMatrixFile = new File(matrixRoot+"/wordCountMatrix.txt");
			if(!wordCountMatrixFile.exists() || needRetrain){
				BuildWordCountMatrix bm = new BuildWordCountMatrix(trainingDataRoot,configRoot);
				bm.buildMatrix();
			}
			
			//if IG not exist, build it
			File igFile = new File(matrixRoot+"/IG.txt");
			if(!igFile.exists() || needRetrain){
				CalculateIG calIG = new CalculateIG(matrixRoot+"/wordCountMatrix.txt", mapRoot+"/categoryMap.txt",matrixRoot+"/IG.txt");
				calIG.getIG();
			}
			
			//if feature word not exist, get it
			File featureFile = new File(dictionaryRoot+"/featureWord.txt");
			if(!featureFile.exists() || needRetrain){
				//1000 is the number of feature words we want to use
				FeatureSelection.featureSelect(1000, matrixRoot+"/IG.txt",dictionaryRoot+"/featureWord.txt");
			}
			
			//if vector matrix not exist, build it
			File vectorMatrixFile = new File(vectorRoot+"/vectorMatrix.txt");
			if(!vectorMatrixFile.exists() || needRetrain){
				BuildDocVector buildV = new BuildDocVector(matrixRoot+"/wordCountMatrix.txt",matrixRoot+"/wordNumInFileList.txt",
						dictionaryRoot+"/featureWord.txt", vectorRoot+"/vectorMatrix.txt");
				buildV.buildVector();
			}
			
			/************************************Build vector for user input file***********************************************************/
			File inFile = new File(inFilePath);
			BuildVectorForInputFile buildVForInput = new BuildVectorForInputFile(inFilePath,matrixRoot+"/wordCountMatrix.txt",
					dictionaryRoot+"/featureWord.txt", tempRoot+"/vector"+inFile.getName());
			buildVForInput.buildVector();
			
			
			CalculateSimilarity cs =  new CalculateSimilarity(vectorRoot+"/vectorMatrix.txt", tempRoot+"/vector"+inFile.getName(),
					mapRoot+"/categoryMap.txt"); 
			cs.classify();
		}
	}
}
