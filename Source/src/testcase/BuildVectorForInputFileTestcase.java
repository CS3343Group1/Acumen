package testcase;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import classifier.BuildDocVector;
import classifier.BuildVectorForInputFile;
import org.junit.Before;
import org.junit.Test;

public class BuildVectorForInputFileTestcase {
	BuildVectorForInputFile buildVectorForInputFile=new BuildVectorForInputFile("./testdata/testinput.txt","./testdata/testoutput/matrix/wordCountMatrix.txt","./testdata/testoutput/dictionary/featureWord.txt","./testdata/testoutput/vector/vector2.txt");
	ArrayList<Double> dv1= new ArrayList<Double>();
	HashMap<String, ArrayList<Short>> matrix1=new HashMap<String, ArrayList<Short>>();
	
	@Before
	public void setUp() throws Exception {
		dv1.add(Double.parseDouble("0.0"));
		dv1.add(Double.parseDouble("0.0"));
		dv1.add(Double.parseDouble("1.0986122886681098"));
		dv1.add(Double.parseDouble("0.0"));
		dv1.add(Double.parseDouble("1.0986122886681098"));
		dv1.add(Double.parseDouble("0.0"));
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
	}

	@Test
	public void test() {
		try{
			buildVectorForInputFile.buildVector();
			Field dv2 = BuildVectorForInputFile.class.getDeclaredField("dv");
			Field matrix2 = BuildVectorForInputFile.class.getDeclaredField("matrix");
			dv2.setAccessible(true);
			matrix2.setAccessible(true);
			assertEquals(dv1, dv2.get(buildVectorForInputFile));
			assertEquals(matrix1, matrix2.get(buildVectorForInputFile));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
