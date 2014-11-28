package testcase;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import classifier.BuildWordCountMatrix;
import classifier.SingleFileWordCount;

public class BulidWordCountMatrixTestcase {
	BuildWordCountMatrix buildWordCountMatrix=new BuildWordCountMatrix("./testdata/testfiles", "./testdata/testoutput");
	Hashtable<String, Integer> categoryMap1=new Hashtable<String, Integer>();
	Hashtable<String, Integer> fileNameMap1=new Hashtable<String, Integer>();
	ArrayList<SingleFileWordCount> wordCountSet1=new ArrayList<SingleFileWordCount>();
	ArrayList<Integer> fileNumInCategory1=new ArrayList<Integer>();
	
	@Before
	public void setUp() throws Exception {
		categoryMap1.put("C1", 0);
		categoryMap1.put("C2", 1);
		fileNameMap1.put("C1_file1.txt", 0);
		fileNameMap1.put("C1_file2.txt", 1);
		fileNameMap1.put("C1_file3.txt", 2);
		fileNameMap1.put("C2_file4.txt", 3);
		fileNameMap1.put("C2_file5.txt", 4);
		fileNameMap1.put("C2_file6.txt", 5);
		File folder=new File("./testdata/testfiles");
		File[] files=folder.listFiles();
		for(File file1 : files)
		{
			if(file1.isDirectory())
			{
				File [] subFiles = file1.listFiles();
				Arrays.sort(subFiles);
				for(File file2 : subFiles)
				{	
					System.out.println(file1.getName()+"\t"+file2.getAbsolutePath()+"\t"+"./testdata/testoutput/"+file1.getName()+"/"+file2.getName());
					wordCountSet1.add(new SingleFileWordCount(file1.getName(),file2.getAbsolutePath(),"./testdata/testoutput/"+file1.getName()+"/"+file2.getName()));
			}}
		}
//		System.out.println(wordCountSet1.size());
		fileNumInCategory1.add(3);
		fileNumInCategory1.add(3);
	}

	@Test
	public void buildMatrixTest() {
		try{
			buildWordCountMatrix.buildMatrix();
			
			Field categoryMap2 = BuildWordCountMatrix.class.getDeclaredField("categoryMap");
			Field fileNameMap2 = BuildWordCountMatrix.class.getDeclaredField("fileNameMap");
			Field wordCountSet2 = BuildWordCountMatrix.class.getDeclaredField("wordCountSet");
			Field fileNumInCategory2 = BuildWordCountMatrix.class.getDeclaredField("fileNumInCategory");
			
			categoryMap2.setAccessible(true);
			fileNameMap2.setAccessible(true);
			wordCountSet2.setAccessible(true);
			fileNumInCategory2.setAccessible(true);
			
			
			assertEquals(categoryMap1, categoryMap2.get(buildWordCountMatrix));
			assertEquals(fileNameMap1, fileNameMap2.get(buildWordCountMatrix));
			
			ArrayList<SingleFileWordCount> temp = (ArrayList<SingleFileWordCount>) wordCountSet2.get(buildWordCountMatrix);
			for(int i=0;i<wordCountSet1.size();i++)
				assertEquals(wordCountSet1.get(i).getCountTable(), temp.get(i).getCountTable());
			
//			assertEquals(wordCountSet1, wordCountSet2.get(buildWordCountMatrix));
			assertEquals(fileNumInCategory1, fileNumInCategory2.get(buildWordCountMatrix));

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
