   /*
	Class Name: UserInfo.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 9, 2013 ; 2:49 pm
	School:		AY Jackson SS
	Purpose: 	contains general user information including full name, gender, and personal 
	*/

   import java.util.*;
   public class UserInfo{
   
   	//fields
      private String name;
      private String surname;
      private char gender;
      private String description;
    
    	 //Constructor 
      public  UserInfo(String name, String surname, char gender, 
       String description){
         
         this.name=name;
         this.surname=surname;
         this.gender=gender;
         this.description=description;
         
      }
   
     	//setDescription: set description to new Description
      public void setDescription(String newDescription){
         description=newDescription;
      }
     
     	//toString: outputs the fields in the format of shown in the settings screen
      public String toString(){
         String output;
         output="***General Information***";
         output+="\nFirst Name: "+name;
         output+="\nSurname: "+surname;
         output+="\nGender: "+gender;
         output+="\nPersonal Description: ";
			
			String [] content = description.split(" ");
			int length = 22;
			for (int i=0; i<content.length; i++){
				if (length+content[i].length()<69){
					output+=content[i]+ " ";
					length+=content[i].length()+1;
				}else{
					output+= "\n                     " + content[i];
					length = 22+content[i].length();
				}
				
			}
			      
         return output;    
      }
     
    	 //getName: get field name
      public String getName(){
         return name;
      } 
     
     	//getSurame: get field surname
      public String getSurname(){
         return surname;
      }
      
   	//getGender: get field gender
      public char getGender(){
         return gender;
      }
      
   	//getDescription: get field descrition 
      public String getDescription(){
         return description;
      }
   	
   
   }