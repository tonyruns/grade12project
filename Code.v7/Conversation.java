//ADDED WRITE TO FILE and changed markAsRead nad addMesage
//ADDED new variable empty


/*
	Class Name: Conversation.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 14, 2013 ; 1:21 pm
	School:		AY Jackson SS
	Purpose: 	Holds the array of messages between the users in the convo. Also contains information 
					such as if the conversation has been read, and the number of messages
 */

import java.text.*;
import java.io.*;
import java.util.*;

public class Conversation {
	// fields
	private String convoID; //NEW changed from String to int
	private String fileName;
	private int numMessages;
	private ArrayList<Message> messages;
	private String timeLastMessaged;
	private User[] usersInConvo;
	private int numUsers;
	private boolean[] read;
	private int startIndex;
	private int endIndex;
	private boolean deleted;
	private boolean empty;
	
	// Conversation: 0 Parameter constructor, sets fields to null, false, and 0
	public Conversation() {
		convoID = null;
		fileName = null;
		numMessages = 0;
		messages = null;
		timeLastMessaged = null;
		usersInConvo = null;
		numUsers = 0;
		read = null;
		startIndex = 0;
		endIndex = 0;
		deleted = false;
		empty = true;
	}
	
	
	// Conversation: 2 Parameter constructor, reads from passed in text file to fill in conversation	
	public Conversation(String fileName, Company company) {
		Scanner sc=new Scanner (System.in);
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			
			// variables for Message
			Message message;
			String dateSent;
			String content;
			String idNum;
			User[] receiver;
			User sender;		
			
			// filling in fields
			empty = false;
			this.fileName = fileName;
			convoID = in.readLine();
			timeLastMessaged = in.readLine();
			
			if (!timeLastMessaged.equals("deleted")){
				this.deleted = false;
				numUsers = Integer.parseInt(in.readLine());
				read = new boolean[numUsers];
				usersInConvo = new User[numUsers];
				receiver = new User[numUsers-1];
				for (int i = 0 ; i < numUsers ; i++) {
					usersInConvo[i] = company.findUserID(in.readLine());
				}
			
				for (int i = 0 ; i < numUsers ; i++) {
					if (in.readLine().equals("read")) {
						read[i] = true;
					} else {
						read[i] = false;
					}
				}
		
				numMessages = Integer.parseInt(in.readLine());
				messages = new ArrayList<Message>(numMessages);
				
				for (int z = 0 ; z < numMessages ; z++) {
					idNum = in.readLine();
					sender = company.findUserID(idNum);
			
					// fill in all usersInConvo other than sender into receiver array
					for (int i = 0 ; i < numUsers-1 ; i++) {
						try{ // DEBUG
							if (!usersInConvo[i].equals(sender)) {
								for (int j = 0 ; j < receiver.length ; j++) {
									if (receiver[i] == null) {
										receiver[i] = usersInConvo[i];
									}
								}
							}
						}catch(NullPointerException nx){
							//System.out.println ("That user no longer exists");
						}
					}
		
					dateSent = in.readLine();
					content = in.readLine();
					
					message = new Message(content, sender, receiver, dateSent);
					// adding message into messages array
					messages.add(message);
				}
				
				endIndex = numMessages-1;
				
				if (numMessages > 10) {
					startIndex = endIndex - 9;
				} else {
					startIndex = 0;
				}	
			
			}else{
				this.deleted = true;
				
			}
				
		} catch (IOException iox) {
			System.out.println("*** Error reading from file ***");
		}		
	}
	
	//runner: runs the functions of the conversations
	public void runner(User currentUser){
		final String DASHES = "---------------------------------------------------------------------";
		Scanner sc = new Scanner (System.in);
		String options;
		String convoInput = "";
		boolean validConvoInput = false;
		boolean owner = false;
		
		
		//loops viewing the conversation until the user decides to exit
		do {				
			validConvoInput = false;
			System.out.print("Conversation with: ");
			
			// finding Conversation with: person
			String convoWith = "";
			for (int i = 0 ; i < usersInConvo.length ; i++) {
				if (!usersInConvo[i].equals(currentUser)) {
					convoWith = usersInConvo[i].getUserInfo().getName() + " " + usersInConvo[i].getUserInfo().getSurname();
				}else{
					owner = true;
				}
			}
         
			// if user's name is too long to fit, then append it                  
			if (convoWith.length() > 39) {
				System.out.print(convoWith.substring(0, 40));
			} else {
				System.out.print(convoWith);
				// adding blank spaces
				for (int i = 0 ; i < 39 - convoWith.length() ; i++) {
					System.out.print(" ");
				}
			}
			
			System.out.println(" [x] Return");			
			System.out.println(DASHES);
			
			System.out.println (this);
			options = "[N] Next / [P] Previous";
			for (int z = 0; z<69-options.length(); z++){
				System.out.print (" ");
			}
			System.out.println (options);
			if (owner){
				options = "[S] New Message";
				for (int z = 0; z<69 - options.length(); z++){
					System.out.print (" ");
				}
				System.out.println (options);
			}
			System.out.println (DASHES);
			
			//Prompt
			System.out.print ("Enter choice: ");
			while (!validConvoInput){
				convoInput = sc.nextLine();
									
				if (convoInput.equalsIgnoreCase("P")){ //previous page
					validConvoInput = true;
					previousPage();
				}else if (convoInput.equalsIgnoreCase("N")){ //next page
					validConvoInput = true;
					nextPage();
				}else if (convoInput.equalsIgnoreCase("S") && owner){ //sends a message
					validConvoInput = true;
					System.out.println ("Arrived");
					User [] receiver = new User [numUsers-1];
					int count = 0;
					for (int i=0; i<numUsers; i++){
						if (!usersInConvo[i].equals(currentUser)){
							receiver[count] = usersInConvo[i];
							count++;
						}
						
					}
					System.out.println();
					System.out.println();
					Message newMessage = new Message (currentUser, receiver);
					this.addMessage(newMessage);
					// updating start and end index
					if (numMessages > 10) {
						startIndex += 1;
						endIndex += 1;
					} else {
						startIndex = 0;
						endIndex = numMessages-1;
					}
				}else if (convoInput.equals("x")){
					validConvoInput = true;
				}
				
				if (!validConvoInput){
					System.out.println ("*** Invalid Input ***");
					if (owner){
						System.out.print ("N, P or S: ");
					}else{
						System.out.print ("N or P: ");
					}
				}
			}
			
			
			
		}while (!convoInput.equals("x"));
		System.out.println ("EXITED");
	}
	
	
	// markAsRead: searches usersInConvo and marks the currentUser's convo to read
	public void markAsRead(User currentUser) {
		boolean marked = false;
		
		for (int i = 0 ; i < numUsers && !marked ; i++) {
			if (currentUser.equals(usersInConvo[i])) {
				read[i] = true;
				marked = true;
			}
		}
		
		writeToFile();
	}
	
	// compareToTime: compares the dateSent of the implicit and explicit conversation (last sent message), return 1 if the implicit is more recent, -1 if less recent, 0 if same 	
	public int compareToTime(Conversation other) {
		// splitting the date format (dd/MM/yyyy 'at' kk:mm) into seperate values
		// I stands for implicit, E for explicit
		String timeI = timeLastMessaged;
		String timeE = other.timeLastMessaged;
		
		int dayI = Integer.parseInt(timeI.substring(0, 2));
		int monthI = Integer.parseInt(timeI.substring(3, 5));
		int yearI = Integer.parseInt(timeI.substring(6, 10));		
		int hourI = Integer.parseInt(timeI.substring(14, 16));
		int minuteI = Integer.parseInt(timeI.substring(17, 19));
		
		int dayE = Integer.parseInt(timeE.substring(0, 2));
		int monthE = Integer.parseInt(timeE.substring(3, 5));
		int yearE = Integer.parseInt(timeE.substring(6, 10));		
		int hourE = Integer.parseInt(timeE.substring(14, 16));
		int minuteE = Integer.parseInt(timeE.substring(17, 19));

		// comparing by year, then month, then day, hour, then minute
		if (yearI > yearE) {
			return 1;
		} else if (yearE > yearI) {
			return -1;
		} else {
			if (monthI > monthE) {
				return 1;
			} else if (monthE > monthI) {
				return -1;
			} else {
				if (dayI > dayE) {
					return 1;
				} else if (dayE > dayI) {
					return -1;
				} else {
					if (hourI > hourE) {
						return 1;
					} else if (hourE > hourI) {
						return -1;
					} else {
						if (minuteI > minuteE) {
							return 1;
						} else if (minuteE < minuteI) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			}
		}			
	}
	
	// compareToNumMessages: compares the numMessages, returning 1 if the implicit is bigger, -1 if smaller, 0 if equal
	public int compareToNumMessage(Conversation other) {
		if (numMessages > other.numMessages) {
			return 1;
		} else if (numMessages < other.numMessages) {
			return -1;
		} else {
			return 0;
		}
	}
	
	// equals: checks if the user passed in is in the conversation
	public boolean equals(User user) {
		for (int i = 0 ; i < numUsers ; i++) {
			try{ //DEBUG
				if (usersInConvo[i].equals(user)) {
					return true;
				}
			}catch(NullPointerException nx){
			}
		}
		return false;
	}
	
	// addMessage: adds message to ArrayList<Message>, while updating the text file
	public void addMessage(Message newMessage) {
		// adding message
		messages.add(newMessage);
		numMessages ++;
		
		// updating read status
		for (int i = 0 ; i < numUsers ; i++) {
			if (usersInConvo[i].equals(newMessage.getSender())) {
				read[i] = true;
			} else {
				read[i] = false;
			}
		}
		writeToFile();
		startIndex++;
		endIndex++;
	}
	
	//writeToFile
	public void writeToFile(){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("Conversations\\convo" + convoID + ".txt")); //NEW - was initially idNum, changed to convoID
		
			out.write(convoID);
			out.newLine();
			Date date = new Date();
			SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy 'at' kk:mm"); 
			out.write (formattedDate.format(date));
			out.newLine();
			
			out.write(numUsers+"");	
			out.newLine();
			
			// user ID
			for (int i = 0 ; i < numUsers ; i++) {
				out.write(usersInConvo[i].getIDNum());
				out.newLine();	
			}
			
			// read/unread
			for (int i = 0 ; i < read.length ; i++) {
				if (read[i]) {
					out.write("read");
				} else {
					out.write("unread");
				}
				out.newLine();
			}
			
			out.write(numMessages + "");
			out.newLine();

			for (int i = 0 ; i < numMessages ; i++) {
				out.write(messages.get(i).getSender().getIDNum());
				out.newLine();
				out.write(messages.get(i).getDateSent());
				out.newLine();
				out.write(messages.get(i).getContent());
				out.newLine();
			}
			
			out.close();
		} catch (IOException iox) {
			System.out.println("*** Error reading from file ***");		
		}		

	}
	
	//delete NEW
	public void delete(){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("Conversations\\convo" + convoID + ".txt")); 
		
			out.write(convoID); //NEW - changed from idNum
			out.newLine();
			out.write("deleted");
			out.newLine();
			out.close();
		} catch (IOException iox) {
			System.out.println("*** Error reading from file ***");		
		}		
	}
	
	// matchMessage: checks to see if the users in the message match the users in the convo
	public boolean matchMessage(Message message) {
		boolean inConvo = false;
		
		// checking if the sender of the message is in convo
		for (int i = 0 ; i < numUsers && !inConvo; i++) {
			if (usersInConvo[i].equals(message.getSender())) {
				inConvo = true;
			}
		}
		
		// if sender is in convo, check the receiver(s)
		if (inConvo) {	
			inConvo = false;
		
			for (int i = 0 ; i < message.getReceiver().length && !inConvo ; i++) {
				inConvo = false;
				for (int j = 0 ; j < numUsers && !inConvo ; j++) {
					if (usersInConvo[j].equals(message.getReceiver()[i])) {
						inConvo = true;
					}
				}
			}
		} else {
			return false;
		}
		
		if (inConvo) {
			return true;
		} else {
			return false;
		}
	}
	
	// isUnread: checks if the user's read boolean is true or false, returning its boolean value
	public boolean isUnread(User currentUser) {
		for (int i = 0 ; i < numUsers ; i++) {
			//System.out.println (usersInConvo[i]);
			if (usersInConvo[i].equals(currentUser)) {
				return (!read[i]);
			}
		}
		return false;
	}
	
	// toString: outputs the 10 messages from start to end index
	public String toString() {
		String output = "";
						
		for (int i = startIndex ; i <= endIndex ; i++) {
			try{
				output += messages.get(i);
				output += "\n";
			}catch (IndexOutOfBoundsException ioobx){
			}
		}
		
		return output;
	}
	
	// previousPage: updates the startIndex and the endIndex (-10)
	public void previousPage() {
		if (startIndex != 0) {
			// if [0, 1, 2, 3... 13], start and end [4, 13], change to [0, 3]
			if (startIndex - 10 < 0) {
				endIndex = startIndex-1;
				startIndex = 0;
			} else {
				startIndex -= 10;
				endIndex -= 10;
			}
		}
	}
	
	// nextPage: updates the startIndex and the endIndex (+10)
	public void nextPage() {
		if (numMessages > 10) {
			// if [0, 1, 2, 3... 13], start and end [0, 3], change to [4, 13]
			if (endIndex-startIndex < 9) {
				startIndex = endIndex + 1;
				endIndex = startIndex + 9;
			} else if (endIndex != numMessages-1) {
				startIndex += 10;
				endIndex += 10;
			}
		}
	}
	
	// getUsersInConvo
	public User[] getUsersInConvo() {
		return usersInConvo;
	}
	
	// getNumUsers
	public int getNumUsers() {
		return numUsers;
	}
	
	// getTimeLastMessaged	
	public String getTimeLastMessaged() {
		return timeLastMessaged;
	}
	
	//getNumMessages
	public int getNumMessages(){
		return numMessages;
	}
	
	//getDeleted
	public boolean getDeleted(){
		return deleted;
	}
	
	//getEmpty;
	public boolean getEmpty(){
		return empty;
	}
}

