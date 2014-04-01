   /*
	Class Name: Employee.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 12, 2013 ; 4:57 pm
	School:		AY Jackson SS
	Purpose: 	changes the users information and allows user to delete account
   */
	
	
   import java.util.*;
    public class Settings{
      Scanner sc=new Scanner (System.in);
      //fields
      private User currentUser;
      private Company company;
		private ConversationDatabase convoDatabase; //DEBUG for a removed user
   
       //Contructor:  fills in this.currentUser with local currentUser and this.company with local company
       public Settings(User currentUser, Company company, ConversationDatabase convoDatabase){
         this.currentUser=currentUser;   	
         this.company=company;
			this.convoDatabase = convoDatabase;
      }
      
   	 //runner: prompts for user input, runs different methods based on those choices
       public void runner(){
         String input = "";
      	
         while (!input.equals("x")) {
            System.out.print(this.toString());
            System.out.print("Enter choice: ");
            input = sc.nextLine();
            
            while (!input.equalsIgnoreCase("A") && !input.equalsIgnoreCase("B") && !input.equalsIgnoreCase("C") && !input.equals("x")){
               System.out.println("*** Input is invalid ***");
               System.out.print("Enter choice: ");
               input=sc.nextLine();
            }
            System.out.println("---------------------------------------------------------------------");
            System.out.println();
            System.out.println();
         	     
            if (input.equalsIgnoreCase("A")){
               this.editPassword();
            }
            else if (input.equalsIgnoreCase("B")){
               this.editInfo();
            }
            else if (input.equalsIgnoreCase("C")){
               this.deleteUser();
            }
         }      	
        
      }
      
   	
   	 //editInfo:  outputs edit info screen and prompts for input, runs different methods based on those choices
       public void editInfo(){
         String input = "";
      	
         while (!input.equals("x")) {
            System.out.println("Edit Info                                                  [x] Return");
            System.out.println("---------------------------------------------------------------------");
            System.out.println("[1] Personal Description");
            System.out.println("[2] Shift Start Time");
            System.out.println("[3] Shift End Time");
            System.out.println("[4] Department");
            System.out.println("---------------------------------------------------------------------");
            System.out.print("Enter choice: ");
            input=sc.nextLine();
         	
            while (input.equals("1")==false  &&input.equals("2")==false &&input.equals("3")==false&&input.equals("4")==false
              &&input.equals("x")==false){
               System.out.println("*** Input is invalid ***");
               System.out.print("Enter choice: ");
               input=sc.nextLine();
            }
            
            System.out.println("---------------------------------------------------------------------");
            System.out.println();
            System.out.println();
         	
            if (input.equals("1")){
               this.editPersonalDescription();
            }
            else if (input.equals("2")){
               this.editShiftStartTime();
            }
            else if (input.equals("3")){
               this.editShiftEndTime();
            }
            else if (input.equals("4")){
               this.editDepartment();
            }
         }
      }
      
   	
   	//editPassword: outputs change password screen, prompts for input
       public void editPassword(){
         System.out.println("Change Password                                            [x] Return");
         System.out.println("---------------------------------------------------------------------");
         System.out.print("Old Password: ");
         String password=sc.nextLine();
         
         if (password.equals("x")||password.equals("X")){
         
         }
         else{
            if(company.checkPassword(currentUser,password)==true){
               System.out.print("New Password: ");
               String newPassword=sc.nextLine();
               if(newPassword.equals("x")==false&&newPassword.equals("X")==false){
                  currentUser.getLoginInfo().setPassword(newPassword);
                  company.findUser(currentUser.getLoginInfo().getUsername()).getLoginInfo().setPassword(newPassword);
                  company.writeTextFile();
               }
            }
            else{
               do{
                  System.out.println();
                  System.out.println("*** Input is invalid ***");
                  System.out.print("Old Password: ");
                  password=sc.nextLine();
               }while(company.checkPassword(currentUser,password)==false&&password.equals("X")==false &&password.equals("x")==false);
               
               if (password.equals("x")==false  && password.equals("x")==false){
                  System.out.print("New Password: ");
                  String newPassword=sc.nextLine();
                  currentUser.getLoginInfo().setPassword(newPassword);
                  company.findUser(currentUser.getLoginInfo().getUsername()).getLoginInfo().setPassword(newPassword);
                  company.writeTextFile();
               }
               else{
               }
            }
         
         }
         System.out.println("---------------------------------------------------------------------");
         System.out.println();
         System.out.println();
      } 
      	
   	 //editPersonalDescription: outputs Personal Description screen, prompts for input
       public void editPersonalDescription(){
         System.out.println("Personal Description                                       [x] Return");
         System.out.println("---------------------------------------------------------------------");
         System.out.print("New Description: ");
         String description=sc.nextLine();
         
         if (description.equals("x")||description.equals("X")){
         
         }
         else{
            currentUser.getUserInfo().setDescription(description);
           /* User temp = */company.findUser(currentUser.getLoginInfo().getUsername()).getUserInfo().setDescription(description);
            company.writeTextFile();
         }
         
         System.out.println("---------------------------------------------------------------------");
         System.out.println();
         System.out.println();
      }
      
      //editShiftStartTime: outputs Shift Start Time screen, prompts for input
       public void editShiftStartTime(){
         System.out.println("Shift Start Time                                           [x] Return");
         System.out.println("---------------------------------------------------------------------");
      
         System.out.print("New Time [hour:minute]: ");
         String time=sc.nextLine();
         while (!time.equals("x") && !company.validTime(time)&&!time.equals("X")) {
            System.out.println();
            System.out.println("*** Input is invalid ***");
            System.out.print("Enter choice ([1 to 23]:[0 to 59]): ");
            time = sc.nextLine();		
         }
      
         if (time.equals("x")||time.equals("X")){
         
         }
         else{
            currentUser.getWorkInfo().setShiftStartTime(time);
            company.findUser(currentUser.getLoginInfo().getUsername()).getWorkInfo().setShiftStartTime(time);
            company.writeTextFile();
         }
         
         System.out.println("---------------------------------------------------------------------");
         System.out.println();
         System.out.println();   
      }
      
       public void editShiftEndTime(){
         System.out.println("Shift End Time                                             [x] Return");
         System.out.println("---------------------------------------------------------------------");
         
      	System.out.print("New Time [hour:minute]: ");
         String time=sc.nextLine();
         while (!time.equals("x") && !company.validTime(time)&&!time.equals("X")) {
            System.out.println();
            System.out.println("*** Input is invalid ***");
            System.out.print("Enter choice ([1 to 23]:[0 to 59]): ");
            time = sc.nextLine();		
         }
         
         if (time.equals("x")||time.equals("X")){
         
         }
         else{
            currentUser.getWorkInfo().setShiftEndTime(time);
            company.findUser(currentUser.getLoginInfo().getUsername()).getWorkInfo().setShiftEndTime(time);
            company.writeTextFile();
         }
         
         System.out.println("---------------------------------------------------------------------");
         System.out.println();
         System.out.println();
      }
      
   	 //editDepartment: outputs Department screen, prompts for input
       public void editDepartment(){
         System.out.println("Department                                                 [x] Return");
         System.out.println("---------------------------------------------------------------------");
         System.out.print("New Department: ");
         String department=sc.nextLine();
         
         if (department.equals("x")||department.equals("X")){
         
         }
         else{
            currentUser.getWorkInfo().setDepartment(department);
            company.findUser(currentUser.getLoginInfo().getUsername()).getWorkInfo().setDepartment(department);
            company.writeTextFile();
         }
         
         System.out.println("---------------------------------------------------------------------");
         System.out.println();
         System.out.println();
      }
      
   	 //deleteUser: uses the removeUser function in the Company, which also edits the text file
       public void deleteUser(){
         System.out.println("Delete Account                                             [x] Return");
         System.out.println("---------------------------------------------------------------------");
         System.out.print("Are you sure you want to continue [Y] Yes / [N] No: ");
         String input=sc.nextLine();
         while (input.equals("Y")==false  && input.equals("y")==false  && input.equals("N")==false  && input.equals("n")==false && input.equals("x")==false  && input.equals("X")==false){
            System.out.println("*** Input is invalid ***");
            input=sc.nextLine();
         }
         
         if (input.equals("Y")==true||input.equals("y")==true){
            System.out.print("Password: ");
            String password=sc.nextLine();
            if (company.checkPassword(currentUser,password)==false){
               do{
                  System.out.println();
                  System.out.println("*** Input is invalid ***");
                  System.out.print("\nPassword: ");
                  password=sc.nextLine();
               }while(company.checkPassword(currentUser,password)==false);
            }
            
            
				//DEBUG for removed users
				convoDatabase.delete(currentUser);
				
				company.removeUser(currentUser);
         }

        
         System.out.println("---------------------------------------------------------------------");
         System.out.println(); 
         System.out.println(); 
      }
      
   	 //toString: returns the main setting screen
       public String toString(){
         String output;
         output="Settings                                                   [x] Return";
         output+="\n---------------------------------------------------------------------\n";
         output+=currentUser.getUserInfo().toString() + "\n";
         output+=currentUser.getWorkInfo().toString() + "\n";
         output+="---------------------------------------------------------------------\n";     	
         output+="[a] Change Password  [b] Edit Info  [c] Remove Account\n";
         return output;
      }
        
   }
   	
   
   
   
   
   
   
   
