package testcase;

import static org.junit.Assert.*;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import source.Classifier;

public class Classifier_testcase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_getCountTable() {
		Hashtable h_social = new Hashtable<String, Integer>();
		h_social.put("facebook", 17);
		h_social.put("google+", 8);
		
		Hashtable h_game = new Hashtable<String, Integer>();
		h_game.put("gta", 15);
		h_game.put("game", 6);
		
		Classifier test_social = new Classifier("social", h_social);
		Classifier test_game = new Classifier("game", h_game);
		
		assertEquals(17, (int)test_social.getCountTable().get("facebook"));
		assertEquals(8, (int)test_social.getCountTable().get("google+"));
		
		assertEquals(15, (int)test_game.getCountTable().get("gta"));
		assertEquals(6, (int)test_game.getCountTable().get("game"));
	}
	
	@Test
	public void test_getCategory() {
		Classifier test_social = new Classifier("social", null);
		Classifier test_game = new Classifier("game", null);
		
		assertEquals("social", test_social.getCategory());
		assertEquals("game", test_game.getCategory());
	}

}
