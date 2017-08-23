package UDPMenu;

import java.util.*;
import java.io.*;
import java.net.*;

public class UDPClient1
{

	public static void main(String[] args) throws IOException
	{
		DatagramSocket socket = new DatagramSocket();
		byte buffer[] = new byte[256];

		// Server Info
		InetAddress address = InetAddress.getByName("localhost");
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1234); // UDP(Datagram)
		
		// send to server
		socket.send(packet);
		
		
		// get from server
		packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
				
		String receivedString = new String(packet.getData());
		System.out.println("Today's Menu is " + receivedString);
	}

}
