/*
	Class Name: Employee.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 9, 2013 ; 5:52 pm
	School:		AY Jackson SS
	Purpose: 	This is the inbox for each user, it shows and holds the conversations that the user has had.
*/

import java.util.*;

public class Inbox{
	
	//Fields
	private User currentUser;
	private ArrayList<Conversation> conversations;
	private int indexStart;
	private int indexEnd;
	static private ConversationDatabase allConvos;
	
	//Constructor: initializes the fields
	public Inbox (User currentUser, ConversationDatabase allConvos){
		this.currentUser = currentUser;
		this.conversations = new ArrayList<Conversation> (0);
		
		if (currentUser instanceof Employee){
			for (int i=0; i<allConvos.getConversations().size(); i++){
				if (!allConvos.getConversations().get(i).getDeleted() && allConvos.getConversations().get(i).equals(currentUser)){
					this.conversations.add(allConvos.getConversations().get(i));
				}
			}
		}else{
			for (int i=0; i<allConvos.getConversations().size(); i++){
				if (!allConvos.getConversations().get(i).getDeleted()){
					this.conversations.add(allConvos.getConversations().get(i));
				}
			}
		}
		
		indexStart = 0;
		if (conversations!=null){
			indexEnd =  conversations.size(); // changed from 10 to arraylist size
		}else{
			
			indexEnd = 0;
		}
		
		if (indexEnd >10) {
			indexEnd = 10;
		}
		if (indexEnd == 0){
			indexStart = 0;
		}
	}
	
	//runner: this runs the functions are to be used in the inbox
	public void runner(){
		
		Scanner sc = new Scanner (System.in);
		String input = null;
		String convoInput;
		char choice;
		int num;
		int tempEndIndex = 0;
		boolean valid = false;
		
		final String DASHES = "---------------------------------------------------------------------";
		
		
		do{
			//outputs the inbox and its options
			valid = false;
			System.out.println (this);
			System.out.println (DASHES);
			System.out.print("Enter choice: ");
			
			
			while (!valid){
				input = sc.nextLine();
				try{
					num = Integer.parseInt(input);
					if (num>indexStart && num<=indexEnd){
						//boolean exit = false;
						//searches for the conversation to be displayed
						for (int i=indexStart; i<indexEnd /*&& !exit*/; i++){
							tempEndIndex = i;
							if (num-1 == i){
								//exit = true;
								valid = true;
								conversations.get(i).markAsRead(currentUser);
								conversations.get(i).runner(currentUser);							
							}
						}
						
					}
				}catch (IndexOutOfBoundsException ix){
					valid = false;
				}catch(NumberFormatException nx){
			
						
						if (input.equalsIgnoreCase("a")){
							sortByNewest();
							valid = true;
						}else if (input.equalsIgnoreCase("b")){
							sortByOldest();
							valid = true;
						}else if (input.equalsIgnoreCase("c")){
							sortByUnread();
							valid = true;
						}else if (input.equalsIgnoreCase("N")){
							next();
							valid = true;
						} else if (input.equalsIgnoreCase("P")) {
							previous();
							valid = true;
						} else if (input.equals("x")){
							valid = true;
						}	
				}
				
				if (!valid){
					System.out.println();
					System.out.println ("*** Input is invalid ***");
					if (tempEndIndex>indexStart+1){
						System.out.print ("(a, b, c, N, P, or "+ (indexStart+1) + " to "+ (tempEndIndex) +"): ");
					}else{
						System.out.print ("(a, b, c, N, P, or "+ (indexStart+1)+"): ");						
					}
				} else {
					System.out.println(DASHES);
					System.out.println();
					System.out.println();
				}
			}	
		}while(!input.equals("x"));
		
	}
	
	//next: views the next page
	public void next(){
		
		if (indexEnd<conversations.size()){
			indexStart+=10;
			indexEnd+=10;
		}
		
	}

	//previous: views the previous page
	public void previous(){
		if (indexStart>0){
			indexStart -= 10;
			indexEnd -= 10;
		}
	}
	
	//sortByNewest: sorts the inbox by the newest conversations
	public void sortByNewest(){
		
		boolean change = true;
		
		//cocktail sort
		for (int i=0; i<conversations.size() && change; i++){
			change = false;
			
			if ((i+1)%2!=0){
				//starting at the front of the list
				for (int j = 0; j<conversations.size()-1; j++){
					//swaps the items if the implicit conversation is older than the explicit conversation
					//sends the oldest item to the back of the list
					if (conversations.get(j).compareToTime(conversations.get(j+1)) < 0){
						change = true;
						Conversation temp = conversations.get(j);
						conversations.set(j, conversations.get(j+1));
						conversations.set(j+1, temp);
					}
				}
			}else{
				//starting at the back of the list
				for (int j=conversations.size()-1; j>0; j--){
					//swaps the items if the implicit conversation is more recent thatn the explicity conversation
					//brings the most recent item to the front
					if (conversations.get(j).compareToTime (conversations.get(j-1)) > 0){
						change = true;
						Conversation temp = conversations.get(j);
						conversations.set(j, conversations.get(j-1));
						conversations.set(j-1, temp);
					}
				}
			}
		}
	}
	
	//sortByOldest: sorts the inbox by the oldest conversations
	public void sortByOldest(){
		boolean change = true;
		
		//cocktail sort
		for (int i=0; i<conversations.size() && change; i++){
			change = false;
			
			if ((i+1)%2==1){
				//starting at the front of the list
				for (int j = i/2; j<conversations.size()-1; j++){
					//swaps the items if the implicit conversation is older than the explicit conversation
					//sends the most recent conversation to the back
					if (conversations.get(j).compareToTime(conversations.get(j+1)) > 0){
						change = true;
						Conversation temp = conversations.get(j);
						conversations.set(j, conversations.get(j+1));
						conversations.set(j+1, temp);
					}
				}
			}else{
				System.out.println ("YUUUUP");
				//starting at the back of the list
				for (int j=conversations.size()-1; j>0; j--){
					//swaps the items if the implicit conversation is more recent thatn the explicity conversation
					//brings the oldest conversation to the front
					if (conversations.get(j).compareToTime (conversations.get(j-1)) < 0){
						change = true;
						Conversation temp = conversations.get(j);
						conversations.set(j, conversations.get(j-1));
						conversations.set(j-1, temp);
					}
				}
			}
		}

	}
		
	//sortByUnread: arranges the inbox so that the unread conversations appear at the top
	public void sortByUnread(){
		
		Conversation temp;
		int count;
		
		//Insertion sort
		for (int i = 1; i<conversations.size(); i++){
			temp = conversations.get(i); //.isUnread(currentUser);
			count = i;
			
			while (count>0 && temp.isUnread(currentUser) && !conversations.get(count-1).isUnread(currentUser)){
				
				conversations.set(count, conversations.get(count-1));
				count--;
				
			}
			conversations.set(count, temp);
			
			
			
		}
	}
	
	//toString: outputs the conversations ranging from starting index to the ending index, in the desired format
	public String toString(){
		
		final String DASHES = "---------------------------------------------------------------------\n";
		String output = "";
		
		output += "Inbox";
		for (int i = output.length(); i<59; i++){
			output += " ";
		}
		output += "[x] Return\n";
		output += DASHES;
		
		for (int i = indexStart; i<indexEnd; i++){
			try{
				if (conversations.get(i).isUnread(currentUser)){
					output += "["+(i+1)+"] " + "[UNREAD] ";
				}else{
					output += "["+(i+1)+"] ";
				}
				User[] inConvo = conversations.get(i).getUsersInConvo();
				for (int z = 0; z<inConvo.length; z++){
					if (!currentUser.equals(inConvo[z])){
						output += inConvo[z].getUserInfo().getName() + " " + inConvo[z].getUserInfo().getSurname()+ ", ";
					}
				}
				output = output.substring(0, output.length()-2);
				output += " " + conversations.get(i).getTimeLastMessaged() + "\n";
			}catch(IndexOutOfBoundsException ix){
				output+= " \n";
			}
		}
		
		String temp = "[N] Next / [P] Previous";
		for (int i = 0; i<69-temp.length(); i++){
			output += " ";
		}
		output += temp + "\n";
		output += DASHES;
		output += "Sort By: \n";
		output += "[a] Newest [b] Oldest [c] Unread";

		
		return output;
	}
	
}