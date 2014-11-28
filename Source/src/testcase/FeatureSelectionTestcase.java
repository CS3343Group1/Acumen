package testcase;

import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import classifier.FeatureSelection;
import org.junit.Before;
import org.junit.Test;

public class FeatureSelectionTestcase {
	FeatureSelection featureSelection=new FeatureSelection();
	ArrayList<Entry<String, Double>> features1 ;
	@Before
	public void setUp() throws Exception {
		Hashtable<String, Double> temp=new Hashtable<String, Double>();
		temp.put("health",0.3182570841474064);
		temp.put("tall",0.3182570841474064);
		temp.put("arm",0.3182570841474064);
		temp.put("america",0.3182570841474064);
		temp.put("strong",0.3182570841474064);
		temp.put("technology",0.1323041247188982);
		features1= new ArrayList<Entry<String, Double>>(temp.entrySet());
	}

	@Test
	public void test() {
		try{
			featureSelection.featureSelect(6, "./testdata/testoutput/matrix/IG.txt", "./testdata/testoutput/dictionary/featureWord.txt");
			
			Field features2=FeatureSelection.class.getDeclaredField("features");
			features2.setAccessible(true);
			System.out.println(features2.get(featureSelection));
//			System.out.println(features1);
			ArrayList<Entry<String, Double>> features3=(ArrayList<Entry<String, Double>>) features2.get(featureSelection);
			for(Entry feature : features1)
				assertEquals(true, features3.contains(feature));
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
