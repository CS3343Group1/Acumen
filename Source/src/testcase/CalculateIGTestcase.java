package testcase;

import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

import classifier.CalculateIG;
import org.junit.Before;
import org.junit.Test;

public class CalculateIGTestcase {
	CalculateIG calculateIG=new CalculateIG("./testdata/testoutput/matrix/wordCountMatrix.txt","./testdata/testoutput/map/categoryMap.txt","./testdata/testoutput/matrix/IG.txt");
	int categoryNum1, totFileNum1;
	ArrayList<Integer> num_file_in_one_cat1 = new ArrayList<Integer>();
	HashMap<String, ArrayList<Short>> matrix1 = new HashMap<String, ArrayList<Short>>();
	Hashtable<String, Double> info_gain1 = new Hashtable<String, Double>();
	@Before
	public void setUp() throws Exception {
		categoryNum1=2;
		totFileNum1=6;
		num_file_in_one_cat1.add(3);
		num_file_in_one_cat1.add(3);
		ArrayList<Short> n=new ArrayList<Short>(Arrays.asList(Short.parseShort("1")));
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
		info_gain1.put("brave",Double.parseDouble("0.1323041247188982"));
		info_gain1.put("brightness",Double.parseDouble("0.1323041247188982"));
		info_gain1.put("technology",Double.parseDouble("0.1323041247188982"));
		info_gain1.put("japan",Double.parseDouble("0.1323041247188982"));
		info_gain1.put("america",Double.parseDouble("0.3182570841474064"));
		info_gain1.put("strong",Double.parseDouble("0.3182570841474064"));
		info_gain1.put("quite",Double.parseDouble("0.1323041247188982"));
		info_gain1.put("economy",Double.parseDouble("0.1323041247188982"));
		info_gain1.put("advanced",Double.parseDouble("0.05663301226513251"));
		info_gain1.put("arm",Double.parseDouble("0.3182570841474064"));
		info_gain1.put("health",Double.parseDouble("0.3182570841474064"));
		info_gain1.put("hair",Double.parseDouble("0.1323041247188982"));
		info_gain1.put("tall",Double.parseDouble("0.3182570841474064"));
		info_gain1.put("eye",Double.parseDouble("0.1323041247188982"));
	}

	@Test
	public void test() {
		try{
			calculateIG.getIG();
			
			Field categoryNum2=CalculateIG.class.getDeclaredField("categoryNum");
			Field totFileNum2=CalculateIG.class.getDeclaredField("totFileNum");
			Field num_file_in_one_cat2=CalculateIG.class.getDeclaredField("num_file_in_one_cat");
			Field matrix2=CalculateIG.class.getDeclaredField("matrix");
			Field info_gain2=CalculateIG.class.getDeclaredField("info_gain");

			categoryNum2.setAccessible(true);
			totFileNum2.setAccessible(true);
			num_file_in_one_cat2.setAccessible(true);
			matrix2.setAccessible(true);
			info_gain2.setAccessible(true);

			assertEquals(categoryNum1,categoryNum2.get(calculateIG));
			assertEquals(totFileNum1,totFileNum2.get(calculateIG));
			assertEquals(num_file_in_one_cat1,num_file_in_one_cat2.get(calculateIG));
			assertEquals(matrix1,matrix2.get(calculateIG));
			assertEquals(info_gain1,info_gain2.get(calculateIG));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
