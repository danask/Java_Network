package MultiChatProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiChatServer
{
	private ServerSocket ss = null; 	// socket for server
	private Socket s = null; 	// socket for normal comm
	
	ArrayList <ChatThread> chatList = new ArrayList<ChatThread>(); 
	
	public void start()
	{
		try
		{
			ss = new ServerSocket(8888); // besides 1~1023
			System.out.println("Server Start!!");

			// waiting
			while(true)
			{
				s = ss.accept();
				ChatThread chat = new ChatThread();
				chatList.add(chat);
				chat.start(); // thread start!!
			}
		}
		catch(Exception e)
		{
			System.out.println("[MultiChatServer] start() exception occurs!!");
		}
	}
	
	// Broadcasting to all clients
	void msgSendAll(String msg)
	{
		for(ChatThread entry : chatList)
		{
			entry.outMsg.println(msg);
		}
		
	}
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		MultiChatServer server = new MultiChatServer();
		server.start();
	}
	
	
	class ChatThread extends Thread
	{

		String msg; // received msg
		String[] rmsg; // for parsing
		
		private BufferedReader inMsg = null; // based on char
		private PrintWriter outMsg = null;
		
		public void run()
		{
			boolean status = true;
			
			System.out.println("[Server] ChatThread is starting...");
			
			
			try
			{
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(), true); // flush everytime	
				
				
				while(status)
				{
					try
					{
						msg = inMsg.readLine();
						rmsg = msg.split("/");
						
						if(rmsg[1].equals("logout"))
						{
							chatList.remove(this);
							msgSendAll("server/" + rmsg[0] + " is out");
							status = false;
						}
						else if(rmsg[1].equals("login"))
						{
							msgSendAll("server/" + rmsg[0] + " is joined");
						}
						else
						{
							msgSendAll(msg); // Broadcasting
						}

						this.interrupt();   // due to client closing
						System.out.println("[ChatThread] " + this.getName()+" stopped !");
					
					}
					catch(IOException e)
					{
						status = false;
//						e.printStackTrace();
					}
					
				}
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}

}
