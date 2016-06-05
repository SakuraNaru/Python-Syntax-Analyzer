package readfile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Read {
	private List <String> line;

	public Read()
	{
		line=new ArrayList<String>();
		FileInputStream fis=null;
		BufferedReader bfr=null;
		try {
			fis=new FileInputStream("d:\\python.txt");
			bfr=new BufferedReader(new InputStreamReader(fis, "gb2312"));
			String s;
			while((s=bfr.readLine())!=null)
			{
				line.add(s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bfr.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(line.get(0));
	}
	public List<String> getLine()
	{
		return line;
	}
}
