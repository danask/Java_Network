package Basics;

import java.io.*;
import java.net.*;


public class MulticastSniffer
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		InetAddress group = null;
		int port = 0;
		
		try
		{
			group = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
		}
		catch(ArrayIndexOutOfBoundsException | NumberFormatException | UnknownHostException e)
		{
			System.err.println("Usage : Java MulticastSniffer multicast_address port");
			System.exit(1);
		}
		
		MulticastSocket ms = null;
		
		try
		{
			// Join
			
			ms = new MulticastSocket(port);
			ms.joinGroup(group);
			
			byte[] buffer = new byte[8192];
			
			// Receive
			
			while(true)
			{
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);
				String s = new String(dp.getData(), "8859_1");
				System.out.println(s);
			}
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		finally
		{
			// Leave
			
			if(ms != null)
			{
				try{
					ms.leaveGroup(group);
					ms.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}

}


/*

M-SEARCH * HTTP/1.1
HOST: 239.255.255.250:1900
MAN: "ssdp:discover"
MX: 1
ST: urn:dial-multiscreen-org:service:dial:1
USER-AGENT: Google Chrome/59.0.3071.115 Windows

ssdp:alive
SERVER: Network Printer Server UPnP/1.0 V3.00.01.11     DEC-14-2015
USN: uuid:16a65700-007c-1000-bb49-30cda7bf9a29::urn:schemas-upnp-org:service:PrintBasic:1

NOTIFY * HTTP/1.1
HOST: 239.255.255.250:1900
CACHE-CONTROL: max-age=60
LOCATION: http://192.168.0.22:5200/Printer.xml
NT: urn:schemas-upnp-org:service:PrintBasic:1
NTS: ssdp:alive
SERVER: Network Printer Server UPnP/1.0 V3.00.01.11     DEC-14-2015
USN: uuid:16a65700-007c-1000-bb49-30cda7bf9a29::urn:schemas-upnp-org:service:PrintBasic:1

....

*/
