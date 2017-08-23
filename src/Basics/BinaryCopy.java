
package Basics;

import java.io.*;


public class BinaryCopy
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		File src = new File("c:\\windows\\explorer.exe");
		File dest = new File("c:\\Temp\\explorer.bin");
		
		FileInputStream fin = null;
		FileOutputStream fout = null;
		
		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		
		
		int c;
		
		
		try{
			fin = new FileInputStream(src);
			fout = new FileOutputStream(dest);
			
			//bin = new BufferedInputStream(fin);
			bout = new BufferedOutputStream(fout);

			// src file -> bufferedOutput(fout);
			
			while((c = fin.read()) != -1)
			{
				bout.write((char)c);
			}
			//bin.close();
			bout.close();
			
			fin.close();
			fout.close();
			
		}catch(IOException ie){
			System.out.println("Error in copy");
		}
	}

}
