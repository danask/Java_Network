package Basics;

import java.io.*;
import java.net.*;
import java.util.*;


// PingCheck

public class PingCheck 
{
    public static void main(String[] args)
    {
	    	String ip = "www.google.com";
	        String pingResult = "";
	        String pingCmd = "ping "+ ip;
	
	        // Runtime Object : OS I/F via JVM, not JAVA class
	
	        try{
	            Runtime r = Runtime.getRuntime(); // static method, newX
	            Process p = r.exec(pingCmd); // external prog.
	
	            BufferedReader in = new BufferedReader((new InputStreamReader(p.getInputStream())));
	            String inputLine;
	
	            while((inputLine = in.readLine()) !=null)
	            {
	                System.out.println(inputLine);
	                pingResult += inputLine;
	            }
	            in.close();
	        }catch (IOException e){
	            e.printStackTrace();
	        }
    
    }
}