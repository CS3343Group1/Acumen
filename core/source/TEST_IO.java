package source;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TEST_IO {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_copyArray() {
		try {
			File test_file = new File("test.txt");
			test_file.createNewFile();
			IO test_io = new IO(test_file.getAbsolutePath(), false);
			String [] test_array = {"google+",  "facebook", "gta"};
			test_io.copyArray(test_array);
			
			assertEquals(3, (int)test_io.getIndex());
			
			assertEquals("google+", (String)test_io.getWords()[0]);
			assertEquals("facebook", (String)test_io.getWords()[1]);
			assertEquals("gta", (String)test_io.getWords()[2]);	
		} catch (IOException e) {
		}
	}
	
	@Test
	public void test_isInteger(){
		assertEquals(true, IO.isInteger("111"));
		assertEquals(false, IO.isInteger("1s1"));
		assertEquals(false, IO.isInteger("sss"));
	}
	
}
