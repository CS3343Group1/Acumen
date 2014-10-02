package testcase;

import source.IO;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class IO_testcase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_documentProcessing() {
		IO test_io = new IO(this.getClass().getResource("test.txt").getPath(), false);
		String[] result = test_io.documentProcessing();
		String[] expectation = {"Facebook", "is", "competing", "with", "Google+"};
		
		for(int i=0; i < expectation.length; i++)
			assertEquals(expectation[i], result[i]);
	}

}
