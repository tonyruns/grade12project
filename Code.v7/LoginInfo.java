   /*
	Class Name: LoginInfo.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 9, 2013 ; 10:51 pm
	School:		AY Jackson SS
	Purpose: 	holds login information: the username and password
	*/
   
  import java.util.*;

   public class LoginInfo{
   
      //fields
      private String username;
      private String password;
   
   	 //Constructor
      public LoginInfo (String username, String password){
         this.username=username;
         this.password=password;
      
      }
      
   	 //setUsername: setUsername:set username to new username
      public void setUsername(String newUser){
         username=newUser;
      
      }
   	
   	 //setPassword: set password to new password
      public void setPassword(String newPass){
         password=newPass;
      }
     
       //getUsername: get field username
      public String getUsername(){
         return username;
      }
      
   	//getPassword: get field password
      public String getPassword(){
         return password;
      }
   
      
      // public String toString(){
      // 
      // }
   
   
   
   
   
   }