package testcase;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import classifier.BuildDocVector;
import classifier.BuildWordCountMatrix;

import org.junit.Before;
import org.junit.Test;

public class BuildDocVectorTesecase {
	BuildDocVector buildDocVector=new BuildDocVector("./testdata/testoutput/matrix/wordCountMatrix.txt","./testdata/testoutput/matrix/wordNumInFileList.txt","./testdata/testoutput/dictionary/featureWord.txt","./testdata/testoutput/vector/vector.txt");
	ArrayList<ArrayList<Double>> dv1= new ArrayList<ArrayList<Double>>();
	HashMap<String, ArrayList<Short>> matrix1=new HashMap<String, ArrayList<Short>>();
	ArrayList<Integer> wordNumInFileList1=new ArrayList<Integer>();
	
	@Before
	public void setUp() throws Exception {
		dv1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		dv1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		dv1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("2.1972245773362196"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		dv1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		dv1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("0.0"))));
		dv1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("1.0986122886681098"),Double.parseDouble("1.791759469228055"))));
		matrix1.put("brightness",new ArrayList<Short>(Arrays.asList(Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"))));
		matrix1.put("brave",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"))));
		matrix1.put("japan",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("1"))));
		matrix1.put("technology",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("1"))));
		matrix1.put("america",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("1"),Short.parseShort("0"))));
		matrix1.put("strong",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("1"))));
		matrix1.put("quite",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"))));
		matrix1.put("advanced",new ArrayList<Short>(Arrays.asList(Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("1"))));
		matrix1.put("economy",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("0"))));
		matrix1.put("arm",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"))));
		matrix1.put("health",new ArrayList<Short>(Arrays.asList(Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("2"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"))));
		matrix1.put("hair",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"))));
		matrix1.put("tall",new ArrayList<Short>(Arrays.asList(Short.parseShort("0"),Short.parseShort("1"),Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"))));
		matrix1.put("eye",new ArrayList<Short>(Arrays.asList(Short.parseShort("1"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"),Short.parseShort("0"))));
		wordNumInFileList1.add(6);
		wordNumInFileList1.add(6);
		wordNumInFileList1.add(6);
		wordNumInFileList1.add(4);
		wordNumInFileList1.add(4);
		wordNumInFileList1.add(5);

	}

	@Test
	public void test() {
		try{
			buildDocVector.buildVector();
			Field dv2 = BuildDocVector.class.getDeclaredField("dv");
			Field matrix2 = BuildDocVector.class.getDeclaredField("matrix");
			Field wordNumInFileList2 = BuildDocVector.class.getDeclaredField("wordNumInFileList");
			dv2.setAccessible(true);
			matrix2.setAccessible(true);
			wordNumInFileList2.setAccessible(true);
			
			assertEquals(dv1, dv2.get(buildDocVector));
			assertEquals(matrix1, matrix2.get(buildDocVector));
			assertEquals(wordNumInFileList1, wordNumInFileList2.get(buildDocVector));
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
