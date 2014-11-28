package testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classifier.ReadContent;

public class ReadContentTestcase {
	ReadContent readContentForSingleFile=new ReadContent("./testdata/testfiles/C1/C1_file3.txt");
	ReadContent readContentForFiles=new ReadContent("./testdata/testfiles/C1");
	ReadContent readContentForSplit=new ReadContent("./testdata/testSplit.txt");

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void processDocumentTestForSingleFile() {
		String[] words = {"arm", "health", "body", "tall", "health", "the"};
		String[] result = readContentForSingleFile.processDocument();
		
		assertArrayEquals(words, result);
	}
	
	@Test
	public void processDocumentTestForFiles() {
		String[] words = {"eye", "brightness", "health", "body", "advanced", "the", "tall", "body", "arm", "brave", "hair", "the", "arm", "health", "body", "tall", "health", "the"};
		String[] result = readContentForFiles.processDocument();
		
		assertArrayEquals(words, result);
	}
	
	@Test
	public void processDocumentTestForSplit() {
		String[] words = {"body", "body", "body", "body", "body", "body", "body", "body", "body", "body", "body", "body", "body"};
		String[] result = readContentForSplit.processDocument();
		
		assertArrayEquals(words, result);
	}
	
}
