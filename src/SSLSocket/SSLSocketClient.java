package SSLSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

// SSL 3.0 (defacto std) --> TLS(official std)
// no certificate

public class SSLSocketClient
{

	public static void main(String[] args) throws IOException, KeyManagementException
	{
		// TODO Auto-generated method stub
		int port = 10789;
		String host = "127.0.0.1";  // server host
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Client Start!!!");

		try
		{
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, null, null);
//			SSLServerSocketFactory factory = context.getServerSocketFactory();			
			SSLSocketFactory factory = context.getSocketFactory(); // different!!
			
			String[] suites = factory.getSupportedCipherSuites();
//			SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(port);
//			serverSocket.setEnabledCipherSuites(suites);	
//			SSLSocket socket = (SSLSocket) serverSocket.accept();
			
			SSLSocket socket = (SSLSocket) factory.createSocket(host,  port);
			socket.setEnabledCipherSuites(suites);
			System.out.println("Client : " + socket.getSession().getCipherSuite()); // password
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			
			
			// waiting for client input
			while(true)
			{
				System.out.println("> ");				
				String text = scan.nextLine();
				
				printWriter.println(text);
				text = bufferedReader.readLine();
				
				if(text != null)
				{
					System.out.println("Server> " + text);
					
					if(text.equals("ack") || text.equals("ACK"))
					{
						text = bufferedReader.readLine();
						
						if(text.equals("fin") || text.equals("FIN"))
						{
							System.out.println("Server> " + text);
							System.out.println("> ack");
							printWriter.println("ack");
							break;
						}
					}
				}
			}
			System.out.println("System Exit");
			
			
			
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
