/*
	Class Name: User.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 9, 2013 ; 5:35 pm
	School:		AY Jackson SS
	Purpose: 	Contains the information that each user has (user ID, UserInfo, WorkInfo, LoginInfo).  
*/

public class User{
	
	//Fields
	private String idNum;
	private UserInfo userInfo;
	private WorkInfo workInfo;
	private LoginInfo loginInfo;
	
	//Constructor 
	public User (String id, UserInfo userInfo, WorkInfo workInfo, LoginInfo loginInfo){
		this.idNum = id;
		this.userInfo = userInfo;
		this.workInfo = workInfo;
		this.loginInfo = loginInfo;
		
	}	
	
	//toString: outputs the UserInfo and WorkInfo of the User, in the format of the profile page
	public String toString(){
	
		final String DASHES = "---------------------------------------------------------------------\n";
		final String  DESCRIPTION_TITLE= "Description: ";
		
		String output = ""; //The String that will be returned
		
				
		output += userInfo.getName() + " " + userInfo.getSurname() + " ["+loginInfo.getUsername()+"]";
		for (int i = output.length(); i<59; i++){
			output += " ";
		}
		output += "[x] Return\n";
		output += DASHES;
		
		output += "Gender: ";
		if (userInfo.getGender() == 'M' || userInfo.getGender() == 'm'){
			output += "Male\n";
		}else{
			output += "Female\n";
		}
		
		//Formatting of the Description 
		String [] words = userInfo.getDescription().split(" ");
		String formattedContent = DESCRIPTION_TITLE;
		String line  = "";
		int num = DESCRIPTION_TITLE.length();
		
		for (int i = 0 ; i < words.length ; i++) {
			if (line.length() + words[i].length() <= 69-num) {
				line += words[i] + " ";
				
				// add to formatted content if it is the last word
				if (words.length-1 == i) {
					formattedContent += line;
				}
			} else {
				// when line is full, go to next line
				if (i != 0) {
					line += "\n             ";
				}
				
				formattedContent += line;
				
				// adding word to next line
				line = words[i] + " ";
				
				// checking to see if word is greater than 69 characters (length of screen)
				if (words[i].length() > 69-num) {
					do {
						formattedContent += words[i].substring(0, 68-num) + "-" + "\n             ";
						words[i] = words[i].substring(68-num);
					} while(words[i].length() > 69-num);
					
					formattedContent += words[i] + "\n             ";
 					line = "";
				}
				
			}
		}
      
		output += formattedContent + "\n";
				
		output += "Department: " + workInfo.getDepartment() + "\n";
		output += "Shift Begins: " + workInfo.getShiftStartTime() + "\n";
		output += "Shift Ends: " + workInfo.getShiftEndTime() + "\n";
		
		return output;
		
		
	}
	
	//compareTo: compares the names of the implicit and explicit User
	public int compareTo (User other){
		
		int num = userInfo.getSurname().compareToIgnoreCase(other.userInfo.getSurname());
		
		if (num == 0){
			num = userInfo.getName().compareToIgnoreCase(other.userInfo.getName());
		}
		
		return num;
	}
	
	//compareToID : compares the id
	public int compareToID (User other){
		int num = Integer.parseInt(idNum);
		int num2 = Integer.parseInt(other.idNum);
		
		return num-num2;
	}
	
	//equals: compares the idNum fields of the implicit and explicit User
	public boolean equals (User other){
		return idNum.equals(other.idNum);
	}
	
	//equalsIDNum: compares the idNum of the implicit User with the local idNum
	public boolean equalsIDNum (String idNum){
		return this.idNum.equals(idNum);
	}
	
	//equalsPassword: compares the password of the implicit User and the local  password
	public boolean equalsPassword (String password){
		return loginInfo.getPassword().equals(password);
	}
	
	//equalsUsername: compares the username of the implicit User and the local username
	public boolean equalsUsername (String username){
		return loginInfo.getUsername().equals(username);
	}
	
	//accessors
	public String getIDNum(){
		return idNum;
	}
	public UserInfo getUserInfo(){
		return userInfo;
	}
	public WorkInfo getWorkInfo(){
		return workInfo;
	}
	public LoginInfo getLoginInfo(){
		return loginInfo;
	}
	
	//updates ID num
	public void updateIDNum(){
		int num = Integer.parseInt(idNum);
		num--;
		idNum = num+"";
	}
}