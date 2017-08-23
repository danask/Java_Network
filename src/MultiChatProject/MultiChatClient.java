package MultiChatProject;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiChatClient implements ActionListener, Runnable
{
	private String ip;
	private String id;
	private Socket socket;
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	
	// Login
	private JPanel loginPanel;
	private JButton loginButton;
	private JLabel label1;
	private JTextField idInput;
	
	// Logout
	private JPanel logoutPanel;
	private JLabel label2;
	private JButton logoutButton;
	
	// Msg
	private JPanel msgPanel;
	private JTextField msgInput;
	
	// Exit
	private JButton exitButton;

	// Main textarea
	private JFrame jframe; 
	private JTextArea msgOut;
	
	private Container tab;
	private CardLayout cLayout;
	private Thread thread;
	
	boolean status;
	
	// Constructor -> Layout
	public MultiChatClient(String ip)
	{
		this.ip = ip;
		
		// login panel
		loginPanel = new JPanel();
		loginPanel.setLayout(new BorderLayout());

		idInput = new JTextField(15);
		loginButton = new JButton("Log-in");
		loginButton.addActionListener(this);
		label1 = new JLabel(" Name : ");
		
		loginPanel.add(label1,BorderLayout.WEST);
		loginPanel.add(idInput,BorderLayout.CENTER);
		loginPanel.add(loginButton,BorderLayout.EAST);
		
		
		// logout panel
		logoutPanel = new JPanel();
		logoutPanel.setLayout(new BorderLayout());		

		label2 = new JLabel();
		logoutButton = new JButton("Log-out");
		logoutButton.addActionListener(this);

		logoutPanel.add(label2, BorderLayout.CENTER);
		logoutPanel.add(logoutButton, BorderLayout.EAST);
		
		
		// msg panel
		msgPanel = new JPanel();
		msgPanel.setLayout(new BorderLayout());

		msgInput = new JTextField(30);
		msgInput.addActionListener(this);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		
		msgPanel.add(msgInput, BorderLayout.CENTER);
		msgPanel.add(exitButton, BorderLayout.EAST);
		
		
		// tab panel
		tab =new JPanel();
		
		cLayout = new CardLayout();
		tab.setLayout(cLayout);

		tab.add(loginPanel, "login");
		tab.add(logoutPanel, "logout");
		
		// frame
		jframe = new JFrame("[Multi-Chat]");
		msgOut = new JTextArea("", 10, 30);
		
		msgOut.setEditable(false);
		
		JScrollPane jPane = new JScrollPane(msgOut, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		jframe.add(tab, BorderLayout.NORTH);
		jframe.add(jPane, BorderLayout.CENTER);
		jframe.add(msgPanel, BorderLayout.SOUTH);
		
		cLayout.show(tab, "log-in"); // cursor in login panel
		
		jframe.pack(); // auto frame size
		jframe.setResizable(false);
		jframe.setVisible(true);
	
	}
	
	
	
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		Object obj = arg0.getSource();
		
		String cmd = null;
		cmd = arg0.getActionCommand().toString();
		System.out.println("[Event] "+ cmd);
		
		if(obj == exitButton)
		{
			outMsg.println(id +  "/" + "logout");
			System.exit(0);
		}
		else if(obj == loginButton)
		{
			id = idInput.getText();
			
			label2.setText(" Name  : " + id);
			cLayout.show(tab, "logout");
			
			connectServer();  // added
		}
		else if(obj == logoutButton)
		{
			// send msg to Server
			outMsg.println(id + "/" + "logout");

			// clear textbox
			msgOut.setText("");
			
			// change to login panel
			cLayout.show(tab,"login");
			outMsg.close();
			
			try{
				inMsg.close();
				socket.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			status = false;
		}
		else if(obj == msgInput)
//		else
		{
			// send to server
			outMsg.println(id +  "/" + msgInput.getText());
			
			// clear input box 
			msgInput.setText("");
		}
		else
		{
			System.out.println("[Client] Wrong Input!!!");
			
		}
		
	}
	
	
	public void connectServer()
	{
		try
		{
			socket = new Socket(ip, 8888);  // ip : server
			System.out.println("[Client] Connected to Server!!");
			
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(), true);
			
			// when login server
			outMsg.println(id + "/" + "login");
			
			// for receiving msg
			thread = new Thread(this);
			thread.start();
		}
		catch(Exception e)
		{
			System.out.println("[MultiChatClient] start() exception occurs!!");
		}
		
	}
	
	
	public void run()
	{
		// received message
		String msg;
		String[] rmsg;
		
		boolean status = true;
		
		while(status)
		{
			try
			{
				msg = inMsg.readLine();
				rmsg = msg.split("/");
				
				// msg to JTextArea
				msgOut.append(rmsg[0] + ">" + rmsg[1] + "\n");
				msgOut.setCaretPosition(msgOut.getDocument().getLength());  // cursor on text
			}
			catch(IOException e)
			{
				status = false;
//				e.printStackTrace();
			}
		}
		
		System.out.println("[MultiChatClinet]" + thread.getName() + " Closed");
	}
	
	public static void main(String[] args)
	{
		MultiChatClient mcc = new MultiChatClient("127.0.0.1");
	}


	
	
	
	
	
	
}
