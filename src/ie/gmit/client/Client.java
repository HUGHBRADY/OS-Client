package ie.gmit.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message="";
 	String ipaddress;
 	boolean usernameFree, loggedIn;
 	String innerMenu;
 	Scanner sc;
	Client(){}
	void run(){
		sc = new Scanner(System.in);
		try{
			//1. creating a socket to connect to the server
			System.out.println("Enter your IP Address");
			ipaddress = "127.0.0.1";
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to "+ipaddress+" in port 2004");
			
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			System.out.println("Hello");
			
			//3: Communicating with the server
			do {		
				try	{	
					message = (String)in.readObject();			// Read in outer menu
					System.out.println(message);				// Print outer menu
					message = sc.next();						// Enter user input
					sendMessage(message);						// Send user input
					
					// Register
					if(message.compareToIgnoreCase("1")==0){	// Select Register
						do {
							message = (String)in.readObject();		// Receive prompt for user name
							System.out.println(message);			// Print prompt
							message = sc.next();					// Enter user name
							sendMessage(message);					// Send user name to server
							
							message = (String)in.readObject();		// Receive prompt for password
							System.out.println(message);			// Print prompt
							message = sc.next();					// Enter password
							sendMessage(message);					// Send password to server
							
							// Check if user name is free
							message = (String)in.readObject();		// Receive confirmation message
							System.out.println(message);			// Print confirmation message
							message = (String)in.readObject();		// Receive confirmation
							if (message.equals("true"))
								usernameFree = true;
							else 
								usernameFree = false;
						} while (usernameFree == false);
					}
					// Log in
					else if(message.compareToIgnoreCase("2")==0){ 	
						message = (String)in.readObject();			// Prompt for user name
						System.out.println(message);				// Print prompt
						message = sc.next();						// Enter user name 
						sendMessage(message);						// Send user name
						
						message = (String)in.readObject();			// Prompt for password
						System.out.println(message);				// Print prompt
						message = sc.next();						// Enter password
						sendMessage(message);						// Send password
					
						// check if logged in
						message = (String)in.readObject();			// Receive confirmation					
						System.out.println("Confirm this: "+message);
						if (message.equals("true"))	
						{
							System.out.println("Login OK");
							
							
							do {
								message = (String)in.readObject();		
								System.out.println(message);			// Menu
								
								innerMenu = sc.next();					// Enter choice
								sendMessage(innerMenu);					// Send choice
								
								// Add Fitness Record
								if (innerMenu.equals("1")){
									String mode;
									String duration;
									boolean valid=false;
									
									message = (String)in.readObject();		
									System.out.println(message);			// Option Selected Message
									message = (String)in.readObject();	
									System.out.println(message);			// Prompt for mode
									do {
										message = sc.next();
										sendMessage(message);				// Send mode
										message = (String)in.readObject();	// 
										
										if (message.equalsIgnoreCase("cycling")){
											valid = true;	
											mode = "Cycling";
										}
										else if (message.equalsIgnoreCase("walking")){
											valid = true;	
											mode = "Walking";
										}
										else if (message.equalsIgnoreCase("running")){
											valid = true;	
										}
										else
											System.out.println("Invalid entry. Enter walking, running or cycling, or enter their initials.");
									} while(valid==false);
									
									
								}
								// Add Fitness Record
								else if (innerMenu.equals("2")){
									
									message = (String)in.readObject();		
									System.out.println(message);			// Option Selected Message
								}
								// Add Fitness Record
								else if (innerMenu.equals("3")){
									
									message = (String)in.readObject();		
									System.out.println(message);			// Option Selected Message
								}
								// Add Fitness Record
								else if (innerMenu.equals("4")){
									
									message = (String)in.readObject();		
									System.out.println(message);			// Option Selected Message
								}
								// Add Fitness Record
								else if (innerMenu.equals("5")){
									
									message = (String)in.readObject();		
									System.out.println(message);			// Option Selected Message
								}
								// Add Fitness Record
								else {
									
									message = (String)in.readObject();		
									System.out.println(message);			// Invalid choice message
								}
								
							} while (!innerMenu.equals("6"));
							// INNER MENU
							
							
						}
						else if (message.equals("false")) {
							System.out.println("Username or password incorrect");
						}
					}
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			}while(!message.equals("3"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg){
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[]){
		Client client = new Client();
		client.run();
	}
}