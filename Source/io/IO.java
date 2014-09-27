import java.util.*;
import java.io.*;

public class IO{
	private final int MAX_WORDS_NUMBER = 5000;
	private String[] words;
	private String infile;
	private String infolder;
	private int index;

	public IO(String infile, boolean isFolder){
		if(isFolder)
			this.infolder=infile;
		else
			this.infile=infile;
		this.words=new String[MAX_WORDS_NUMBER];
		this.index=0;
	}

	public String[] documentProcessing(){
		try{
			if(infile==null)
			{
				File folder=new File(infolder);
				File[] files=folder.listFiles();

				for(int i=0;i<files.length;i++)
					readAndProcess(files[i]);
			}
			else
			{
				readAndProcess(new File(infile));
				// System.out.println("file not folder");
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		return words;
	}

	private void readAndProcess(File file){
		try{
			Scanner scanner=new Scanner(new FileReader(file));
			String[] s;
			String line;
			
			while(scanner.hasNextLine())
			{
				line=scanner.nextLine();
				s=line.split(",|\\.|\\s+|\t|\"|\'|”|“");
				// for(int i=0;i<s.length;i++)
				// 	System.out.print(s[i]+"\t");
				// System.out.println();
				copyArray(s);
			}
			
			scanner.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	private void copyArray(String[] s){
		for(int i=0;i<s.length;i++)
		{
			if(!isInteger(s[i])&&s[i].length()!=0)
			{
				words[index]=s[i];
				index++;
			}
		}
	}

	private boolean isInteger(String s){
		try{
			int n=0;
			n=Integer.parseInt(s);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}