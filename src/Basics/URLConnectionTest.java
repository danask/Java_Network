package Basics;

import java.io.*;
import java.net.*;
import java.util.*;


public class URLConnectionTest
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		URL url = null;
		URLConnection uc = null;

		
		try
		{
			url = new URL("http://www.google.com");

			uc = url.openConnection();
			uc.connect();
					
			System.out.println("uc = " + uc.toString());
			System.out.println("Interaction = " + uc.getAllowUserInteraction());
			System.out.println("content = " + uc.getContent());
			System.out.println("ContentEncoding = " + uc.getContentEncoding());			
			System.out.println("length = " + uc.getContentLength());
			System.out.println("type = " + uc.getContentType());
			System.out.println("date = " + new Date(uc.getDate()));
			
		}
		catch(MalformedURLException ex)
		{
			ex.printStackTrace();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}

/*

uc = sun.net.www.protocol.http.HttpURLConnection:http://www.google.com
Interaction = false
content = sun.net.www.protocol.http.HttpURLConnection$HttpInputStream@5c647e05
ContentEncoding = null
length = -1
type = text/html; charset=EUC-KR
date = Sun Jul 16 22:54:30 KST 2017

*/