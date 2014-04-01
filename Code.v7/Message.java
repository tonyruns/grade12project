   /*
	Class Name: Employee.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 11, 2013 ; 12:16 am
	School:		AY Jackson SS
	Purpose: 	reads in message 
   */

   import java.util.*;
   import java.text.*;

   public class Message {
      Scanner sc = new Scanner(System.in);
    
      //fields
      private String content;
      private String dateSent; 
      private User sender;
      private User[] receiver;
   
   	// Message: this constructor is called from a conversation or the mainMenu class
      public Message(User currentUser, Company company){
			// variables
         int numReceiver = 1;
			User[] receiver = new User[numReceiver];
			String username;
			String content = "";
      	
         System.out.println("Compose New Message                                          [x] Exit");
         System.out.println("---------------------------------------------------------------------");
         System.out.print("Username: ");
			
 			username = sc.nextLine();
			// while(currentUser.equalsUsername(username)){
// 				System.out.println();
// 				System.out.println ("*** Invalid User! ***");
// 				System.out.print ("Username (Cannot choose yourself): ");
// 				username = sc.nextLine();
// 			}
 
  			receiver[0] = company.findUser(username);  
			   	
         while(!username.equals("x") && (receiver[0] == null || currentUser.equals(receiver[0]))) {
				System.out.println();
				try{
					if (currentUser.equals(receiver[0])){
    		      	System.out.println("*** Invalid User! ***");
						System.out.print("Username (Cannot choose yourself): ");
					}else{
						System.out.println("*** User not found ***");
						System.out.print("Username: ");
					}
				}catch(NullPointerException nx){
					System.out.println("*** User not found ***");
					System.out.print("Username: ");
				}
            
            username = sc.nextLine();
         	
  				receiver[0] = company.findUser(username);  				
         }
		
			if (!username.equals("x")) {	
	         System.out.print("Message: ");
	         content=sc.nextLine();
			}  
			
			// filling in fields
         this.content = content;
         this.sender = currentUser;
         this.receiver = receiver;
         
         Date dNow = new Date();
         SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy 'at' kk:mm");
      
         this.dateSent = ft.format(dNow);
			 
			// if user wanted to exit during input, make sender null so nothing will be sent        
 			if (content.equals("x") || username.equals("x")) {	
				this.sender = null;
			}
			
         System.out.println("---------------------------------------------------------------------");
         System.out.println();
			System.out.println();
      }
   
       
   	 //Constructor: this constructor is called from a User's profile
      public Message(User currentUser, User[] receiver){
         System.out.println("Compose New Message                                          [x] Exit");
         System.out.println("---------------------------------------------------------------------");
         for(int i=0; i<receiver.length; i++){
            System.out.print("Username: ");
            System.out.println(receiver[i].getUserInfo().getName()+" "+receiver[i].getUserInfo().getSurname());
         }
   		
			      
			
         System.out.print("Message: ");
         String content=sc.nextLine();
      	
			// filling in fields
         this.content = content;
         this.sender = currentUser;
         this.receiver = receiver;
         
         Date dNow = new Date();
         SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy 'at' kk:mm");
      
			// checking if the hour is 24, changing it to 0 (for sorting)
			String date = ft.format(dNow);
			
			if (Integer.parseInt(date.substring(14, 16)) == 24) {
				date = date.substring(0, 14) + "00" + date.substring(16);
			}
			
         this.dateSent = date;       
			
			if (content.equals("x")){
            this.sender = null;
         }
      	
         System.out.println("---------------------------------------------------------------------");
         System.out.println();
			System.out.println();
      }
       
   	 
   	//Message: fills the sender, receiver and content fields in the message instance
      public Message(String content, User sender, User[] receiver, String dateSent){
         this.content = content;
         this.sender = sender;
         this.receiver = receiver;    
         this.dateSent = dateSent;
      
      }
       
   	 
   	//toString: outputs the entire message including the date sent and the sender
      public String toString (){
         String output;
      	
         output="[" + dateSent + "] " + sender.getUserInfo().getName() + " " + sender.getUserInfo().getSurname() + " says: \n";
			
			// formatting content (to go to next line)
			String[] words = content.split(" ");
			String line = "";
			String formattedContent = "";
			
			for (int i = 0 ; i < words.length ; i++) {
				if (line.length() + words[i].length() <= 69) {
					line += words[i] + " ";
					
					// add to formatted content if it is the last word
					if (words.length-1 == i) {
						formattedContent += line;
					}
				} else {
					// when line is full, go to next line (only if not the first word) and add to formattedContent the line
					if (i != 0) {
						line += "\n";
					}
					
					formattedContent += line;
					
					// adding word to next line
					line = words[i] + " ";
					
					// checking to see if word is greater than 69 characters (length of screen)
					if (words[i].length() > 69) {
						do {
							formattedContent += words[i].substring(0, 68) + "-" + "\n";
							words[i] = words[i].substring(68);
						} while(words[i].length() > 69);
						
						formattedContent += words[i] + "\n";
	 					line =  "";
					}
					
				}
			}
			
// 						String description = "Description: ";
// 						int num = DESCRIPTION_TITLE.length();
// 						for (int i=0; i<content.length; i++){
// 								if (num+content[i].length()>69){
// 									description+= "\n";
// 									for (int j=0; j<DESCRIPTION_TITLE.length(); j++){
// 										description += " ";
// 									}
// 									num=DESCRIPTION_TITLE.length();
// 								}
// 								num += content[i].length()+1;
// 								description += content[i];				
// 						} 	
        	output += formattedContent + "\n";
		   return output;
      
      }
   
   
   	//getReciever: get field receiver
      public User[] getReceiver(){
         return receiver;
      }
      
   	
   	//getSender: get field sender
      public User getSender(){
         return sender;
      }
        
   	//getContent: get field content
      public String getContent(){
         return content;
      }		
      
   	//getDateSent: get dateSent
      public String getDateSent(){
         return dateSent;
      }
}