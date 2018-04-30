package imp;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message;
	private String serverIP;
	private Socket connection;
	
	//Here the output is to the server and input is into the client.
	
	public Client(String host) {
		
		super ("Client Machine");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				
				new ActionListener () {
					
					public void actionPerformed (ActionEvent event) {
						
						sendMessage(event.getActionCommand());
						//Here, this method takes whatever is typed and sends it.
						userText.setText("");
						
						
						
						
					}
					
				}
				
				);
		
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add (new JScrollPane(chatWindow));
		setSize(800,600);
		setVisible(true);
		
			
		
	}
	public void startRunning() {
		
		try {
			
			connectToServer();
			setUpStreams();
			whileChatting();
			
			
		}
		
		catch(EOFException e) {
			
			showMessage("\n Client has terminated the connection.");
			
		}
		catch(IOException ioe) {
			
			ioe.printStackTrace();
			closeAll();
			
		}
		
		
		
	}
	
	private void connectToServer () throws IOException  {
		
		showMessage("Attempting connection. Please wait...\n");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		//6789 is the port on the server.
		
		showMessage("Connection secure! Connected to: " + connection.getInetAddress().getHostName());
		
				
	}
	
	private void setUpStreams() throws IOException{
		
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Your streams are now good to go! \n");		
			
				
	}
	
	
	private void whileChatting () throws IOException {
		
		ableToType(true);
		
		do {
			try {
				
				message = (String) input.readObject();
				showMessage("\n" + message);
				
				
			}
			
			catch(ClassNotFoundException e) {
				
				showMessage("\nMessage could not be received due to invalid type.");
				
			}
			
		}
		
		while(!message.equalsIgnoreCase("SERVER - END" ));
		
		
		
	}
	
	private void closeAll() {
		
		showMessage("Closing all streams...");
		ableToType(false);
		try {
		input.close();
		output.close();
		connection.close();
		}
		catch(IOException e) {
			
			e.printStackTrace();
						
		}
		
		
		
	}
	
	private void sendMessage(String message) {
		
		try {
			
			
			output.writeObject("CLIENT - " + message);
			output.flush();
			showMessage("\nCLIENT - " + message);
			
		}
		
		catch(IOException ioe) {
			
			chatWindow.append("Something went wrong. Error received.");
			
		}
		
	}
	
	private void showMessage(final String m) {
		
		SwingUtilities.invokeLater(
				
				new Runnable() {
					
					public void run() {
						
						chatWindow.append(m);
						
						
					}
					
					
					
				}
				
				
				
				
				);
		
		
		
		
		
		
	}
	
	private void ableToType(final boolean tof) {
		 
SwingUtilities.invokeLater(
				
				new Runnable() {
					
					public void run() {
						
						userText.setEditable(tof);
									
					}
												
				}
								
				);
		
		
		
		
	}
	
	
	
	
	
	
}
