package testcase;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import classifier.CalculateSimilarity;
import classifier.SingleFileWordCount;

import org.junit.Before;
import org.junit.Test;

public class CalculateSimilarityTestcase {
	CalculateSimilarity cs=new CalculateSimilarity("./testdata/testoutput/vector/vector.txt","./testdata/testoutput/vector/vector2.txt","./testdata/testoutput/map/categoryMap.txt");
	double[] coses={0.0,0.0,0.0,0.7071067811865476,1.0,0.4082482904638631};
	ArrayList<Double> inDocVector1 = new ArrayList<Double>();
	ArrayList<ArrayList<Double>> vectorMatrix1 = new ArrayList<ArrayList<Double>>();
	HashMap<Integer,Double> similarity1 = new HashMap<Integer,Double>();
	HashMap<Integer, String> catMap1 = new HashMap<Integer, String>();
	ArrayList<Integer> startIndexOfCat1 = new ArrayList<Integer>();
	@Before
	public void setUp() throws Exception {
		cs.classify();
		inDocVector1.add(0.0);
		inDocVector1.add(0.0);
		inDocVector1.add(1.0986122886681098);
		inDocVector1.add(0.0);
		inDocVector1.add(1.0986122886681098);
		inDocVector1.add(0.0);
		vectorMatrix1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.4472135954999579"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		vectorMatrix1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("0.0"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		vectorMatrix1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.8944271909999159"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("0.0"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		vectorMatrix1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"))));
		vectorMatrix1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("0.0"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("0.0"))));
		vectorMatrix1.add(new ArrayList<Double>(Arrays.asList(Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.0"),Double.parseDouble("0.7071067811865476"),Double.parseDouble("1.0"))));
		similarity1.put(0,0.0);
		similarity1.put(1,0.0);
		similarity1.put(2,0.0);
		similarity1.put(3,0.7071067811865476);
		similarity1.put(4,1.0);
		similarity1.put(5,0.4082482904638631);
		catMap1.put(0,"C1");
		catMap1.put(1,"C2");
		startIndexOfCat1.add(0);
		startIndexOfCat1.add(3);

	}
	@Test
	public void attributesTest(){
		try {
			Field inDocVector2=cs.getClass().getDeclaredField("inDocVector");
			Field vectorMatrix2=cs.getClass().getDeclaredField("vectorMatrix");
			Field similarity2=cs.getClass().getDeclaredField("similarity");
			Field catMap2=cs.getClass().getDeclaredField("catMap");
			Field startIndexOfCat2=cs.getClass().getDeclaredField("startIndexOfCat");
			inDocVector2.setAccessible(true);
			vectorMatrix2.setAccessible(true);
			similarity2.setAccessible(true);
			catMap2.setAccessible(true);
			startIndexOfCat2.setAccessible(true);
			
			assertEquals(inDocVector1,inDocVector2.get(cs));
			assertEquals(vectorMatrix1,vectorMatrix2.get(cs));
			assertEquals(similarity1,similarity2.get(cs));
			assertEquals(catMap1,catMap2.get(cs));
			assertEquals(startIndexOfCat1,startIndexOfCat2.get(cs));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	@Test
	public void cosTest(){
		try {
			Method cos = cs.getClass().getDeclaredMethod("cos",ArrayList.class,ArrayList.class);
			Field inDocVector=cs.getClass().getDeclaredField("inDocVector");
			Field vectorMatrix=cs.getClass().getDeclaredField("vectorMatrix");
			inDocVector.setAccessible(true);
			vectorMatrix.setAccessible(true);
			cos.setAccessible(true);
			ArrayList<ArrayList<Double>> temp = (ArrayList<ArrayList<Double>>)vectorMatrix.get(cs);
			for(int i=0;i<temp.size();i++)
				assertEquals(coses[i],(cos.invoke(cs,(ArrayList<Double> )inDocVector.get(cs),temp.get(i))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
