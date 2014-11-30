package testcase;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import classifier.Classifier;

public class ClassifierTestcase {
	Classifier c=new Classifier();
	PrintStream oldPrintStream;
	ByteArrayOutputStream bos;  

	@Before
	public void setUp() throws Exception {
		setOutput();
	}

	private void setOutput() throws Exception
	{
		oldPrintStream = System.out;
		bos = new ByteArrayOutputStream();  
		System.setOut(new PrintStream(bos)); 
	}
	
	private String getOutput() //throws Exception
	{
		System.setOut(oldPrintStream);
		return bos.toString();
	}

	@Test
	//test no argument input
	public void MainTest1() {
		try {
			String [] args = {};
			c.main(args);
			assertEquals("Please input at least 1 argument \n [file to be classified]",getOutput().trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void MainTest2() {
		System.out.println("asdfasdf");
		try {
			String [] args = {"./Release/training_data/", "./testdata/systemTestInput.txt"};
			c.main(args);
			String x = getOutput().trim();
			
			assertEquals("Belongs to computer",getOutput().trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
