//ONLY SENDS MESSAGES IF CONVO IS NOT EMPTY


/*
	Class Name: EmployeeList.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 11, 2013 ; 5:35 pm
	School:		AY Jackson SS
	Purpose: 	Handles the list of employees other than the current user, and the functions of sorting,
					viewing profiles and sending messages to those employees
*/

import java.util.*;

public class EmployeeList{
	
	//Fields
	private User currentUser;
	private ArrayList<User> contacts;
	//private User [] contacts;
	private ArrayList<Conversation> contactConversations;
	private int startIndex;
	private int endIndex;
	private ConversationDatabase convoDatabase;
	private Company company;
	
	//Constructor - Initializes the fields
	public EmployeeList (User currentUser, Company company, ConversationDatabase database){
		convoDatabase = database;
		ArrayList<Conversation> conversations = database.getConversations();
		
		this.currentUser = currentUser;
		this.company = company;
		
		contacts = new ArrayList<User>(company.getUsers().size() - 1);
		contactConversations = new ArrayList<Conversation>(0);
		
		
		//Setting up the list of employees
		int count = 0;
		for (int i = 0; i<company.getUsers().size(); i++){
			if (!currentUser.equals(company.getUsers().get(i))){
				contacts.add(company.getUsers().get(i));
				count+=1;
			}
		}
		
		//Finding the user - related conversations
// 		System.out.println ("DEBUG: "+conversations.size());
		boolean found = false;		
		for (int z = 0; z<contacts.size(); z++){
			found = false;
			for (int i = 0; i<conversations.size() && !found; i++){
				//System.out.println("looping");
				if (!conversations.get(i).getDeleted() && conversations.get(i).equals(contacts.get(z)) && conversations.get(i).equals(currentUser)){
					contactConversations.add(conversations.get(i));
					found = true;
				}
			}
			
			if (!found){
				Conversation temp = new Conversation();
				contactConversations.add(temp);
			}
		}	
		
		
		//Setting up the indexes
		startIndex = 0;
		//if (contacts.length>10){
			endIndex =  10;
		//}else{
		//	endIndex = contacts.length;
		//}
	}
	
	//runner - displays the employee list and runs its functions
	public void runner(){
	
		final String DASHES = "---------------------------------------------------------------------";
		Scanner sc=new Scanner (System.in);
		String input = null;
		String profileInput;
		String convoInput;
		String options;
		int chatIndex;
		boolean validInput = false;
		boolean validProfileInput = false;
		boolean validConvoInput = false;
		int tempEndIndex = endIndex;
		
		
		
		do{ //loops until the user decide to exit by pressing 'x'
			System.out.println (this);
			System.out.print ("Enter choice: ");
			
			
			validInput = false;
			
			//loops until the user enters a valid input
			while (!validInput){
				input = sc.nextLine();
				System.out.println(DASHES);
				try{
					chatIndex = Integer.parseInt(input);
					//checks if the user has decided to view one of their contact's profile
					for (int i=startIndex; i<endIndex; i++){
						tempEndIndex = i+1;
						if (chatIndex-1 == i){
							System.out.println ();
							System.out.println ();
							validInput = true;
							System.out.println (contacts.get(i));
							
							options = "[S] New Message";
							for (int z=0; z<69-options.length(); z++){
								System.out.print (" ");
							}
							System.out.println (options);
							options = "[C] View Conversation";
							for (int z=0; z<69-options.length(); z++){
								System.out.print (" ");
							}
							System.out.println (options);
							if (currentUser instanceof Admin){
								options = "[R] Remove User";
								for (int z = 0; z<69-options.length(); z++){
									System.out.print (" ");
								}
								System.out.println (options);
								
							}
							System.out.println (DASHES);
							
							validProfileInput = false;
							System.out.print ("Enter choice: ");
							while (!validProfileInput){
								
								profileInput = sc.nextLine();
								
								
								if (profileInput.equalsIgnoreCase("s")){ //if the user wishes to send a message to the contact
									validProfileInput = true;
									User [] receiver = {contacts.get(i)};
									Message newMessage = new Message (currentUser, receiver); // PROBLEM where's receiver initialised? right above?
									
									if (newMessage.getSender()!=null){
										if (contactConversations.get(i).getEmpty()){
											convoDatabase.createConversation(newMessage);
											contactConversations.set(i, convoDatabase.getConversations().get(convoDatabase.getConversations().size()-1));
										}else{
											contactConversations.get(i).addMessage(newMessage);
										}
									}
									
								}else if (profileInput.equalsIgnoreCase("c")){ //if the user decides to view their entire conversation
									validProfileInput = true;
									
									if (contactConversations.get(i).getEmpty()){ //EMPTY
										System.out.println();
										System.out.println ("*** No existing conversation ***");
										
									}else{
										contactConversations.get(i).runner(currentUser);
									}
									
								}else if (profileInput.equals("x")){
									validProfileInput = true;
								}else if (profileInput.equalsIgnoreCase("R") && currentUser instanceof Admin){
									validProfileInput= true;
									company.removeUser(contacts.get(i));
									convoDatabase.delete(contacts.get(i));
									contacts.remove(i);
									
								}
								
								if (!validProfileInput){
									System.out.println("*** Invalid Input ***");
									if (currentUser instanceof Admin){
										System.out.print("(S or C or R): ");
									}else{
										System.out.print("(S or C): ");
									}
								}
							}					
							
							
						}
					}
					
				}catch (ArrayIndexOutOfBoundsException ax){
					validInput = false;
				}catch(NumberFormatException nx){
					
					if (input.equalsIgnoreCase("a")){
						validInput = true;
						sortByChatFrequency();
					}else if (input.equalsIgnoreCase("b")){
						validInput = true;
						sortByLastName();
					}else if (input.equals("x")){
						validInput = true;
					}else if (input.equalsIgnoreCase("n")){
						validInput = true;
						if (endIndex<contacts.size()){
							startIndex+=10;
							endIndex+= 10;
						}
					}else if (input.equalsIgnoreCase("p")){
						validInput = true;
						if (startIndex>0){
							endIndex-=10;
							startIndex-=10;
						}
					}
								
				}
				
				if (!validInput){
					System.out.println ("*** Invalid Input ***");
					if (tempEndIndex>startIndex+1){
						System.out.println ("(a, b, N, P or "+ (startIndex+1) + " to "+(tempEndIndex) + "): ");				
					}else{
						System.out.println ("(a, b, N, P or "+ (startIndex+1) + "): ");
					}
				}
			}
			
			System.out.println();
			System.out.println();

		}while (!input.equals("x"));
	}
	
	
	//next - changes the display range to the next page
	public void next(){
		if (endIndex<contacts.size()){
			endIndex += 10;
			startIndex +=10;
		}
	}
	
	//previous - changes the dislay range to the previous page
	public void previous(){
		startIndex-=10;
		
		if (endIndex>10){
			endIndex-=10;
		}
		if (startIndex<0){
			startIndex = 0;
		}
	}
	
	//sortByLastName - sorts the list of contacts by last name
	public void sortByLastName(){
		
		for (int i=0; i<contacts.size()-1; i++){
			int minIndex = i;
			User min = contacts.get(i);
			for (int j=i+1; j<contacts.size(); j++){
				if (min.compareTo(contacts.get(j))>0){
					min = contacts.get(j);
					minIndex = j;
				}
			}

			contacts.set(minIndex, contacts.get(i));
			contacts.set(i, min);
			Conversation temp = contactConversations.get(i);
			contactConversations.set(i, contactConversations.get(minIndex));
			contactConversations.set(minIndex, temp);
		}		
	}
	
	//sortByChatFrequency - sorts the contacts by order of chat frequency
	public void sortByChatFrequency(){
		boolean exit = false;
		for (int i=0; i<contactConversations.size() && !exit; i++){
			exit = sortByChatFrequency(contactConversations.size()-1, true);
		}
	}
	
	public boolean sortByChatFrequency(int count, boolean exit){
		if (count==0){
			return exit;
		}else{
			if (contactConversations.get(count).compareToNumMessage(contactConversations.get(count-1)) > 0){
				Conversation temp = contactConversations.get(count);
				contactConversations.set(count, contactConversations.get(count-1));
				contactConversations.set(count-1, temp);
				User temp2 = contacts.get(count);
				contacts.set(count, contacts.get(count-1));
				contacts.set(count-1, temp2);
				exit = false;
			}
			return sortByChatFrequency (count-1, exit);
		}
	}
	
	
	//toString
	public String toString(){
	
		final String DASHES = "---------------------------------------------------------------------\n";
		String output = "";
		
		output += "Employee List";
		for (int i=output.length(); i<59; i++){
			output += " ";
		}
		output += "[x] Return\n";
		output += DASHES;
		
		try{
			for (int i=startIndex; i<endIndex; i++){
				output += "["+(i+1)+"] " + contacts.get(i).getUserInfo().getSurname() + ", ";
				output += contacts.get(i).getUserInfo().getName() + " ";
				output += "["+contactConversations.get(i).getNumMessages()+"] \n";
			}
		}catch(IndexOutOfBoundsException ax){
		}
		
		String next ="[N] Next / [P] Previous";
		for (int i=0; i<69-next.length(); i++){
			output += " ";
		}
		output += next+ "\n";
		output += DASHES;
		output += "[a] Sort by Chat Frequency [b] Sort by Last Name";
		
		return output;
	}
	
	//accessor
	public  ArrayList<User> getContacts(){
		return contacts;
	}
	
}