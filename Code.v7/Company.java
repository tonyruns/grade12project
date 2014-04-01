
/*
	Class Name: Company.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 14, 2013 ; 1:19 pm
	School:		AY Jackson SS
	Purpose: 	holds all the users (employees) in the company, and is responsible for handling login 
					and the creation of users 
 */
 
 import java.util.*;
 import java.io.*;
  
 public class Company {
 	// fields
 	private ArrayList<User> users;
	private int numUsers;
 	private static String usersTextFile = "users.txt";	
	
	// empty constructor of the Company class, initializing fields by reading from usersTextFile
	public Company() {
		//System.out.println ("[Debug] entered company constructor");
		try {
			BufferedReader in = new BufferedReader(new FileReader(usersTextFile));
			
			try{
				numUsers = Integer.parseInt(in.readLine());
				in.readLine();
			}catch(NumberFormatException nx){
				//When the file is empty
				numUsers = 0;
			}
			
			// initialize users array
			users = new ArrayList<User>(numUsers);
			
			// looping through all users
			for (int i = 0 ; i < numUsers ; i++) {
				// temp variables to be passed into User constructor
				String userType = in.readLine();
				String idNum = in.readLine();				
				LoginInfo loginInfo = new LoginInfo(in.readLine(), in.readLine());
				UserInfo userInfo = new UserInfo(in.readLine(), in.readLine(), in.readLine().charAt(0), in.readLine());
				WorkInfo workInfo = new WorkInfo(in.readLine(), in.readLine(), in.readLine());			

				if (userType.equals("Admin")) {
					User newUser = new Admin(idNum, userInfo, workInfo, loginInfo);
					users.add(newUser);
				} else {
					User newUser = new Employee(idNum, userInfo, workInfo, loginInfo);
					users.add(newUser);				
				}
				
				// space after every user
				in.readLine();	
			}			
		} catch (IOException iox) {
			System.out.println("*** Error reading from text file ***");
		}		
	}
	
	public User createUser() {
		// variables		
		String choice;
		boolean usernameTaken = true; // for checking for a valid username //NEW - initalized as true
		Scanner sc = new Scanner(System.in);
		

		String idNum = "" + (users.size() + 1);
		String name;
		String surname;
		String gender;
		String description;
		String department;
		String shiftStartTime;
		String shiftEndTime;
		String username;
		String password;
		UserInfo userInfo;
		WorkInfo workInfo;
		LoginInfo loginInfo;
	
		System.out.println("New User                                                   [x] Return");
		System.out.println("---------------------------------------------------------------------");
		System.out.println("*** User Type ***");
		System.out.print("[A] Admin / [E] Employee: ");
		choice = sc.nextLine();
		
		while (!choice.equalsIgnoreCase("A") && !choice.equalsIgnoreCase("E") && !choice.equals("x")) {
			System.out.println();
			System.out.println("*** Input is invalid ***");
			System.out.print("Enter choice (A or E): ");
			choice = sc.nextLine();
		}

		// continues to ask each question until info complete or 'x' is inputted
		if (!choice.equals("x")) {
			System.out.println();		
			System.out.println("*** General Information ***");
			System.out.print("First name: ");
			name = sc.nextLine();
		
			if (!name.equals("x")) {
				System.out.print("Surname: ");
				surname = sc.nextLine();
				
				if (!surname.equals("x")) {
					System.out.print("Gender [M] Male / [F] Female: ");
					gender = sc.nextLine();
					
					while(!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F")) {
						System.out.println();
						System.out.println("*** Input is invalid ***");
						System.out.print("Enter choice (M or F): ");
						gender = sc.nextLine();
					}
					
					if (!gender.equals("x")) {
						System.out.print("Personal Description: ");
						description = sc.nextLine();
						
						if (!description.equals("x")) {
							System.out.println();
							System.out.println("*** Work Information ***");
							System.out.print("Department: ");
							department = sc.nextLine();
							
							if (!department.equals("x")) {
								System.out.print("Shift Start Time [hour:minute]: ");
								shiftStartTime = sc.nextLine();
								
								while (!shiftStartTime.equals("x") && !validTime(shiftStartTime)) {
									System.out.println();
									System.out.println("*** Input is invalid ***");
									System.out.print("Enter choice ([1 to 23]:[0 to 59]): ");
									shiftStartTime = sc.nextLine();		
								}
								
								if (!shiftStartTime.equals("x")) {
									System.out.print("Shift End Time [hour:minute]: ");
									shiftEndTime = sc.nextLine();
									
									while (!shiftEndTime.equals("x") && !validTime(shiftEndTime)) {
										System.out.println();
										System.out.println("*** Input is invalid ***");
										System.out.print("Enter choice ([1 to 23]:[0 to 59]): ");
										shiftEndTime = sc.nextLine();		
									}
									
									if (!shiftEndTime.equals("x")) {
										System.out.println();
										System.out.println("*** Login Information ***");
										System.out.print("Username: ");
										username = sc.nextLine();
										
										while (usernameTaken) {
											usernameTaken = false;
											
											for (int i = 0 ; i < numUsers && !usernameTaken; i++) {
												if (users.get(i).equalsUsername(username)) {
													usernameTaken = true;
												}
											}
										
											if (usernameTaken) {
												System.out.println();
												System.out.println("*** Username is taken ***");
												System.out.print("Username: ");
												username = sc.nextLine();	
											}												
										}
										
										if (!username.equals("x")) {
											System.out.print("Password: ");
											password = sc.nextLine();
											
											if (!password.equals("x")) {
												userInfo = new UserInfo(name, surname, gender.charAt(0), description);
												workInfo = new WorkInfo(department, shiftStartTime, shiftEndTime);
												loginInfo = new LoginInfo(username, password);
																				
												System.out.println("---------------------------------------------------------------------");	
												System.out.println();
												System.out.println();												
												
												numUsers+=1; //NEW
												// creating and adding user to users based on user type
												if (choice.equalsIgnoreCase("A")) {
													User newUser = new Admin(idNum, userInfo, workInfo, loginInfo);
													addUser(newUser);
													return newUser;
												} else {
													User newUser = new Employee(idNum, userInfo, workInfo, loginInfo);
													addUser(newUser);
													return newUser;
												}										
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println("---------------------------------------------------------------------");	
		System.out.println();
		System.out.println();
		
		return null;			
	}
	
	// validTime: checks to see if the time passed in has valid values and is in a valid format
	public boolean validTime(String time) {
		// split minute and hour
		String[] split = time.split(":");
		
		try {
			int hour = Integer.parseInt(split[0]);
			int minute = Integer.parseInt(split[1]);
			
			// checking if hour and minute values are valid times of the day
			if ((hour >= 1 && hour <= 23) && (minute >= 0 && minute <= 59)) {
				return true;
			}
		} catch (NumberFormatException nfe) {
		} catch (ArrayIndexOutOfBoundsException ax){
		}
		
		return false;
	}
	
	//addUser: runner calls this, which then refers to the recursive method
	public void addUser (User user){
		if (numUsers > 0) {
			addUser(user, 0, numUsers-1);
		} else {
			addUser(user, 0, 0);		
		}
	}
		
	// addUser: adds the passed in user to the users array in a logical index
	public void addUser(User user, int startIndex, int endIndex) {
		//try{

			if (endIndex-startIndex>0 && numUsers!=0) {
				int middleIndex = (startIndex + endIndex) / 2;
				
				if (user.compareTo(users.get(middleIndex)) > 0) {
					addUser(user, middleIndex+1, endIndex);
				} else if (user.compareTo(users.get(middleIndex)) < 0) {
					addUser(user, startIndex, middleIndex-1);
				} else {
					users.add(middleIndex, user);
				}
			// runs when program has narrowed down to one index
			} else {
				// checking if the left side is a valid placement
				//CHANGED
				try {
					if (user.compareTo(users.get(startIndex-1)) >= 0) {
						users.add(startIndex, user);
					}else{	
						try {
							if (user.compareTo(users.get(startIndex+1)) <= 0) {
								users.add(startIndex+1, user);
							}
						} catch (IndexOutOfBoundsException ioo0bx) {
							// adds new index to end of ArrayList
							users.add(user);
						}
					}
					
				} catch (IndexOutOfBoundsException ioobx) {
				
					users.add(0, user);
					
				}
			
			}
			//rewriting the text file
			writeTextFile();
	}

	// removeUser: matches the passed user with a user in the users ArrayList and removes it
	public void removeUser(User user) {
		boolean found = false;
		
		//Updates the id Num of the other users, to ensure that no future idNums are duplicates
		for (int i=0; i<users.size(); i++){
			if (user.compareToID(users.get(i))<0){
				users.get(i).updateIDNum();
			}
		}
		
		//Removes the user
		for (int i = 0 ; i < users.size() && !found; i++) {
			if (user.equals(users.get(i))) {
				found = true;
				
				users.remove(i);
				numUsers--;
			}
		}
		
		writeTextFile();
	}
	
	
	// writeTextFile: rewrites the text file usersTextFile by using the current users ArrayList
	public void writeTextFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(usersTextFile));
			
			out.write(numUsers + "");
			out.newLine();
			out.newLine();
			
			for (int i = 0 ; i < users.size() ; i++) {
						
				if (users.get(i) instanceof Admin) {
					out.write("Admin");
				} else {
					out.write("Employee");
				}
				out.newLine();
				
				out.write (users.get(i).getIDNum());
				out.newLine();
				
				out.write(users.get(i).getLoginInfo().getUsername());
				out.newLine();
				
				out.write(users.get(i).getLoginInfo().getPassword());
				out.newLine();
				
				out.write(users.get(i).getUserInfo().getName());
				out.newLine();
				
				out.write(users.get(i).getUserInfo().getSurname());
				out.newLine();

				out.write(users.get(i).getUserInfo().getGender() + "");
				out.newLine();					

				out.write(users.get(i).getUserInfo().getDescription());
				out.newLine();

				out.write(users.get(i).getWorkInfo().getDepartment());
				out.newLine();	

				out.write(users.get(i).getWorkInfo().getShiftStartTime());
				out.newLine();

				out.write(users.get(i).getWorkInfo().getShiftEndTime());
				out.newLine();
													
				out.newLine();
			}
			
			out.close();
		} catch (IOException iox) {
			System.out.println("*** Error reading from text file ***");
		}
	}	

	// logIn: runs findUser and checkPassword to attempt to log the user in
	public User logIn(String name, String password) {
		User user = findUser(name);
		
		if (user == null) {
			return null;
		} else {
			// if password matches user, return the user
			if (checkPassword(user, password)) {
				return user;
			} else {
				return null;
			}
		}
	}	

	// findUser: finds a user with the same name as the name passed in the method, returning it
	public User findUser(String name) {
		for (int i = 0 ; i < users.size() ; i++) {
			if (users.get(i).equalsUsername(name)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	// findUserID: finds user with same idNum as the idNum passed in
	public User findUserID(String idNum) {
		for (int i = 0 ; i < users.size() ; i++) {
			if (users.get(i).equalsIDNum(idNum)) {
				return users.get(i);
			}
		}
		return null;
	}

	// checkPassword: checks if password matches with the user's password
	public boolean checkPassword(User user, String password) {
		if (user.equalsPassword(password)) {
			return true;
		}
		return false;
	}
	
	// getTextFile: accessor
	public String getTextFile() {
		return usersTextFile;
	}	
	
	// getUsers: accessor
	public ArrayList<User> getUsers() {
		return users;
	}
	
 } 
