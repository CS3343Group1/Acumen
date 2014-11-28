package testcase;

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;

import classifier.ReadContent;
import classifier.SingleFileWordCount;

import org.junit.Before;
import org.junit.Test;

public class SingleFileWordCountTestcase {
	SingleFileWordCount singleFileWordCount = new SingleFileWordCount("C1", "./testdata/testfiles/C1/C1_file3.txt", "./testdata/testoutput/C1_file3WordCount.txt");
	Hashtable<String, Integer> countTable1 = new Hashtable<String, Integer>();
	@Before
	public void setUp() throws Exception {
		countTable1.put("arm",1);
		countTable1.put("health",2);
		countTable1.put("body",1);
		countTable1.put("tall",1);
		countTable1.put("the",1);
	}

	@Test
	public void getCategoryTest() {
		String category = singleFileWordCount.getCategory();
		assertEquals("C1", category);
	}	
	
	@Test
	public void getCountTableTest() {
		Hashtable<String, Integer> countTable2= singleFileWordCount.getCountTable();
		assertEquals(countTable1, countTable2);
	}
	
	@Test
	public void getWordsNumTest() {
		int wordsNum = singleFileWordCount.getWordsNum();
		assertEquals(6, wordsNum);
	}
	
	@Test
	public void getWordFrequencyTest(){
		try{
			Method getWordFrequency = SingleFileWordCount.class.getDeclaredMethod("getWordFrequency");
			getWordFrequency.setAccessible(true);
			Field countTable2 =  SingleFileWordCount.class.getDeclaredField("countTable");
			countTable2.setAccessible(true);
			
			assertEquals(countTable1, countTable2.get(singleFileWordCount));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void write2FileTest(){
		try{
			String testContent="";
			String file3WordCountFileContent="";
			Iterator<Entry<String, Integer>> iter = countTable1.entrySet().iterator();
			
			while(iter.hasNext()){
				Entry<String, Integer> entry = iter.next();
				testContent+=entry.getKey() + "\t" + entry.getValue();
			}
			
			Field outputpath = SingleFileWordCount.class.getDeclaredField("outputPath");
			outputpath.setAccessible(true);
			singleFileWordCount.write2File(outputpath.get(singleFileWordCount).toString());
			
			Scanner scanner = new Scanner(new FileReader(outputpath.get(singleFileWordCount).toString()));
			while(scanner.hasNextLine())
				file3WordCountFileContent+=scanner.nextLine();
			scanner.close();
			
//			System.out.println(testContent);
//			System.out.println(file3WordCountFileContent);
//			
			assertEquals(testContent,file3WordCountFileContent);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
}
