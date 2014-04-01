/*
	Class Name: ConversationDatabase.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 10, 2013 ; 12:46 pm
	School:		AY Jackson SS
	Purpose: 	Stores all the conversations that has occurred within the company and perform functions 
					on the conversations
 */

import java.text.*;
import java.util.*; 
import java.io.*;

public class ConversationDatabase {
	// fields
	private ArrayList<Conversation> conversations;
	private Company company;

	// ConversationDatabase: constructor; reads from conversation text files, filling the ArrayList in
	public ConversationDatabase(Company company) {
		int convoNum = 1; // tracks the conversation that is trying to be read 
		String fileName = "";
		conversations = new ArrayList<Conversation>(0);
		this.company = company;
		
		try {
			// continues reading from text file until text file is not found
			do {
				fileName = "Conversations\\convo" + convoNum + ".txt";
			
				BufferedReader in = new BufferedReader(new FileReader(fileName));
				
				// if file exists (no exception caught), create the conversation
				Conversation conversation = new Conversation(fileName, company);
				
				// add to ArrayList
				//if (!conversation.getDeleted()){
					conversations.add(conversation);
			//	}
								
				convoNum++;
			} while(true);
		} catch (IOException iox) {		
		} 		
	}
	
 	// createConversation: creates a conversation file with header and the message passed in
	public void createConversation(Message newMessage) {
		// variables
		Conversation newConvo;
		String fileName = "Conversations\\convo" + (conversations.size()+1) + ".txt";
		int numUsers = 1 + newMessage.getReceiver().length; // adds the sender and the number of receivers

		Date date = new Date();
		SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy 'at' kk:mm"); 
	
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			
			// conversation ID
			out.write((conversations.size()+1)+"");
			out.newLine();
			
			// time last messaged
			out.write(formattedDate.format(date));
			out.newLine();
			
			// number of users
			out.write(numUsers+"");
			out.newLine();
		
			// users in convo
			out.write(newMessage.getSender().getIDNum());
			out.newLine();			
			for (int i = 0 ; i < newMessage.getReceiver().length ; i++) {
				out.write(newMessage.getReceiver()[i].getIDNum());
				out.newLine();
			}
			
			// unread for all users except sender
			out.write("read");
			out.newLine();
			for (int i = 0 ; i < newMessage.getReceiver().length ; i++) {
				out.write("unread");
				out.newLine();
			}
			
			// number of messages
			out.write("0");
			out.newLine();	
			
			out.close();
			
			// initialize new conversation and add the new message to it
			newConvo = new Conversation(fileName, company);
			newConvo.addMessage(newMessage);	
			conversations.add(newConvo);
								
		} catch (IOException iox) {
			System.out.println("*** Error reading from text file ***");
		} 
	}
	
	// findConversation: returns a conversation with the same users as the passed in message
	public Conversation findConversation(Message newMessage) {
		for (int i = 0 ; i < conversations.size() ; i++) {
			if (conversations.get(i).matchMessage(newMessage)) {
				return conversations.get(i);
			}
		}
		return null;
	}
	
	// getConversations: accessor
	public ArrayList<Conversation> getConversations() {
		return conversations;	
	}
	
	public void delete(User currentUser){
		for (int i=0; i<conversations.size(); i++){
			if (conversations.get(i).equals(currentUser)){
				conversations.get(i).delete();
			}
		}
	}
	
} 