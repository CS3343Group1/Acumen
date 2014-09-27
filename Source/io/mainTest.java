import java.io.*;
import java.util.*;

public class mainTest{
	public static void main(String[] args)
	{
		IO input=new IO(args[0], false);
		String[] s=input.documentProcessing();
		for(int i=0;i<s.length;i++)
			if(s[i]!=null)
				System.out.println(s[i]);
	}
}