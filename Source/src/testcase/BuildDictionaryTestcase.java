package testcase;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import classifier.BuildDictionary;
import classifier.SingleFileWordCount;

import org.junit.Before;
import org.junit.Test;

public class BuildDictionaryTestcase {
	BuildDictionary buildDictionary = new BuildDictionary();
	ArrayList<Hashtable<String, Integer>> wordCountFileList=new ArrayList<Hashtable<String, Integer>>();
	
	@Before
	public void setUp() throws Exception {
		File testfloder=new File("./testdata/testfiles/C1");
		File[] files = testfloder.listFiles();
		
		for(File file : files)
			wordCountFileList.add(new SingleFileWordCount("C1", file.getPath(), "./testdata/testoutput/"+file.getName()+".wordCount.txt").getCountTable());
		
	}

	@Test
	public void buildDictTest() throws FileNotFoundException {
		String commonWord1="body the ";
		String totalWord1="brave	1 brightness	1 advanced	1 arm	2 health	2 hair	1 tall	2 eye	1 ";
		
		buildDictionary.buildDict("./testdata/testoutput/dictionary/common_word.txt", "./testdata/testoutput/dictionary/total_word.txt", wordCountFileList);
		
		Scanner scanner=new Scanner(new FileReader("./testdata/testoutput/dictionary/common_word.txt"));
		String commonWord2="";
		String totalWord2="";
		while(scanner.hasNextLine())
			commonWord2+=scanner.nextLine()+" ";
		scanner.close();
		
		scanner=new Scanner(new FileReader("./testdata/testoutput/dictionary/total_word.txt"));
		while(scanner.hasNextLine())
			totalWord2+=scanner.nextLine()+" ";
		scanner.close();
		
		assertEquals(commonWord1,commonWord2);
		assertEquals(totalWord1,totalWord2);
	}

}
