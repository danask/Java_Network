package Non_Block_IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Client
{
	private Abortable abortable = new Abortable();
	private ClientThread clientThread;
	
	public static void main(String[] args) throws Exception
	{
		Client client = new Client();
		client.start("127.0.0.1", Server.PORT_NUMBER);
		
		Thread.sleep(500);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true)
		{
			String line = reader.readLine();
			
			if(line.equals("quit"))
				break;
			
			try{
				client.sayToServer(line);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				break;
			}
		}
		
		client.stop();
		System.out.println("BYE");
		
	}
	
	public void start(String host, int port)
	{
		abortable.init();
		
		if(clientThread == null || !clientThread.isAlive())
		{
			clientThread = new ClientThread(abortable, host, port);
			clientThread.start();
		}
		
	}
	
	public void stop()
	{
		abortable.done = true;
		
		if(clientThread != null && clientThread.isAlive())
		{
			clientThread.interrupt();
		}
	}
	
	public void sayToServer(String text) throws IOException
	{
		clientThread.sayToServer(text);
	}
	
	public class ClientThread extends Thread
	{
		private Abortable abortable;
		private String host;
		private int port;
		private SocketChannel client;
		
		public ClientThread(Abortable abortable, String host, int port)
		{
			this.abortable = abortable;
			this.host = host;
			this.port = port;
		}
		
		public void sayToServer(String text) throws IOException
		{
			int len = client.write(ByteBuffer.wrap(text.getBytes()));
			System.out.printf("[write :: text : %s / len : %d] \n", text, len);
		}
		
		@Override
		public void run()
		{
			super.run();
			
			boolean done = false;
			Selector selector = null;
			Charset cs = Charset.forName("UTF-8");
			
			try{
				System.out.println("Client :: started");
				
				client = SocketChannel.open();
				client.configureBlocking(false);
				client.connect(new InetSocketAddress(host, port));
				
				selector = Selector.open();
				client.register(selector, SelectionKey.OP_READ);
				
				while(!Thread.interrupted() && !abortable.isDone() && !client.finishConnect())
				{
					Thread.sleep(10);
				}
				
				System.out.println("Client :: connected");
				
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				
				while(!Thread.interrupted() && !abortable.isDone() && !done)
				{
					selector.select(3000);
					
					Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
					
					while(!Thread.interrupted() && !abortable.isDone() && !done && iter.hasNext())
					{
						SelectionKey key = iter.next();
						
						if(key.isReadable())
						{
							int len = client.read(buffer);
							
							if(len < 0) // input value : -1 --> quit
							{
								System.out.println("Client :: server closed");
								done = true;
								break;
							}
							else if(len == 0)
							{
								continue;
							}
							buffer.flip();
							
							
							CharBuffer cb = cs.decode(buffer);
							
							System.out.printf("From Server : ");
							
							while(cb.hasRemaining())
							{
								System.out.printf("%c",  cb.get());
							}
							System.out.println();
							
							buffer.compact();
							
						}
					
					}
				}
			
			}
			 catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
		}
		
	}
	
}


/*

Client :: started
Client :: connected
From Server : Welcome
test
[write :: text : test / len : 4] 

[write :: text :  / len : 0] 
aaaaaaaaaaaaaaaaaaaaaa
[write :: text : aaaaaaaaaaaaaaaaaaaaaa / len : 22] 
fin
[write :: text : fin / len : 3] 

[write :: text :  / len : 0] 
exit
[write :: text : exit / len : 4] 
quit
BYE
 
 */
		























