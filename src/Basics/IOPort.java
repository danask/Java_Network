package Basics;

import java.io.*;
import java.net.*;


// cmd: netstat -na

public class IOPort
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		int maxNumber = 65535;
		
		
		// Check whether TCP port opens or not 
		// if opened, shows 'open'
		// if closed, then open it and close.
		
		for(int i = 1; i <= maxNumber; i++)
		{
			try{
				ss = new ServerSocket(i); // open and close test!!
				System.out.println(i + "th TCP port is available");
				ss.close();
			}catch(IOException ie){
				System.out.println(i + "th TCP port is opened");
			}
		}
	}

}
