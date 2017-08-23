package Basics;

import java.net.*;
import java.io.*;


public class URLCheck
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		try{
			URL url = new URL("http://www.google.com");
			BufferedReader bfrd;
			String line;
			
			System.out.println("Port : " + url.getPort());
			System.out.println("Protocol : " + url.getProtocol());
			System.out.println("Host : " + url.getHost());
			System.out.println("Path : " + url.getPath());
			System.out.println("File : " + url.getFile());
			
			bfrd = new BufferedReader(new InputStreamReader(url.openStream()));
			
			while((line = bfrd.readLine()) != null)
			{
				System.out.println(line);
				
			}
			
			bfrd.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
