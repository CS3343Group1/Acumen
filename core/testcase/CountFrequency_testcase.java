package testcase;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import source.Classifier;
import source.CountFrequency;

public class CountFrequency_testcase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_BuildDictionary_count() {
		String [] test_content = {"facebook", "google+", "facebook", "facebook", "facebook", "facebook", "facebook"};
		Classifier test_cat = CountFrequency.BuildDictionary("", test_content);
		assertEquals(6, (int)test_cat.getCountTable().get("facebook"));
	}
	
	
	@Test
	public void test_BuildDictionary_category() {
		String [] test_content = {};
		Classifier test_cat = CountFrequency.BuildDictionary("social", test_content);
		assertEquals("social", test_cat.getCategory());
	}
}
