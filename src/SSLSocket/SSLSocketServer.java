package SSLSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

// SSL 3.0 (defacto std) --> TLS(official std)
// no certificate

public class SSLSocketServer
{

	public static void main(String[] args) throws IOException, KeyManagementException
	{
		// TODO Auto-generated method stub
		int port = 10789;
		
		Scanner scan = new Scanner(System.in);
		
		SSLContext context;
		
		System.out.println("Server Start!!!"); 
		
		try
		{
			context = SSLContext.getInstance("SSL");
			context.init(null, null, null);
			SSLServerSocketFactory factory = context.getServerSocketFactory();
			
			String[] suites = factory.getSupportedCipherSuites();
			SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(port);
			serverSocket.setEnabledCipherSuites(suites);
			SSLSocket socket = (SSLSocket) serverSocket.accept();
			System.out.println("Server : " + socket.getSession().getCipherSuite()); // password
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			
			
			// waiting for client input
			while(true)
			{
				String text = bufferedReader.readLine();
				
				if(text != null)
				{
					System.out.println("client> " + text);
					
					if(text.equals("fin") || text.equals("FIN"))
					{
						System.out.println("Send ACK");
						text = "ACK";
						printWriter.println(text);
						System.out.println("Send FIN");
						text = "FIN";
					}
					else if(text.equals("ack") || text.equals("ACK"))
					{
						break;
					}
					
					printWriter.println(text);
				}
				
			}
			System.out.println("System is terminated");
			
			
			
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
