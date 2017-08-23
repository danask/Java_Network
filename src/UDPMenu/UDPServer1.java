package UDPMenu;

import java.util.*;
import java.io.*;
import java.net.*;

public class UDPServer1
{

	public static void main(String[] args) throws IOException
	{
		DatagramSocket socket = new DatagramSocket(1234); // port #
		boolean listen = true;
		
		while(listen)
		{
			// receive from client
			byte buffer[] = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, 256);
			socket.receive(packet);
			
			// ACK to client
			String menu = LunchMenu.selectMenu();
			buffer = menu.getBytes();
			
			// Client info.
			InetAddress address = packet.getAddress();
			int portNumber = packet.getPort();
			
			// send to each client
			packet = new DatagramPacket(buffer, buffer.length, address, portNumber);
			socket.send(packet);
			
		}
		socket.close();
		
	}

}
