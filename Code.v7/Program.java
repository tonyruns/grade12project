/*
	Class Name: Program.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 8, 2013 ; 6:33 pm
	School:		AY Jackson SS
	Purpose: 	Where the program begins running from, handles the welcome menu and main menu choices
 */

import java.util.*;

public class Program {
	// fields
	private static Company company;
	private static Settings userSettings;
	private static Inbox inbox;
	private static ConversationDatabase allConvos;
	private static EmployeeList contacts;
	private static User currentUser;
	
	// main: calls the runner
	public static void main(String[] args) {
		runner();
	}
	
	// runner: runs the program involving the welcomeMenu and mainMenu methods
	public static void runner() {
		do {
			company = new Company();
			
			currentUser = welcomeMenu();
			
			if (currentUser != null) {
				//System.out.println();
				mainMenu();
			}
			
		} while (currentUser != null);	
	}
	
	// welcomeMenu: outputs the welcome menu and allows user to create a new User or log in
	public static User welcomeMenu() {
		// variable;
		String choice;
		boolean valid = false;
		Scanner sc = new Scanner(System.in);
		
		// keeps looping until valid login, valid user creation, or exit ('x')
		do {
			valid = false;
			System.out.println("Welcome                                                      [x] Exit");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("[1] Login");
			System.out.println("[2] New User");
			System.out.println("---------------------------------------------------------------------");
			System.out.print("Enter choice: ");
			choice = sc.nextLine();
			
			// loops intil choice is valid
			while (!valid) {
				// catching for input that can't be parsed
				try {
					while(!choice.equals("x") && !(Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= 2)) {
						System.out.println();
						System.out.println("*** Input is invalid ***");
						System.out.print("Enter choice (1 to 2 or x): ");
						choice = sc.nextLine();
					}
					valid = true;
				} catch (NumberFormatException nfe) {
					choice = "0";
// 					System.out.println();
// 					System.out.println("*** Input is invalid ***");
// 					System.out.print("Enter choice (1 to 2 or x): ");
// 					choice = sc.nextLine();
				}
			}			

			System.out.println("---------------------------------------------------------------------");						
			System.out.println();
			System.out.println();
				
			if (choice.equals("1")) {
				String username;
				String password;
				String tryAgain = "N";
				
				// keep looping until valid login or user doesn't want to try again
				do {
					if (tryAgain.equalsIgnoreCase("N")) {
						System.out.println("Login");
						System.out.println("---------------------------------------------------------------------");
					}
					System.out.print("Username: ");
					username = sc.nextLine();
					System.out.print("Password: ");
					password = sc.nextLine();
					
					System.out.println("---------------------------------------------------------------------");						
					System.out.println();
					System.out.println();
					
					User user = company.logIn(username, password);
					
					// if valid login, return the user
					if (user != null) {
						return user;
					} else {
						System.out.println("Login");
						System.out.println("---------------------------------------------------------------------");
						System.out.println("*** Username/Password is invalid ***");
						System.out.print("Try again? (Y/N): ");
						tryAgain = sc.nextLine();
						
						while (!tryAgain.equalsIgnoreCase("Y") && !tryAgain.equalsIgnoreCase("N")) {
							System.out.println();
							System.out.println("*** Input is invalid ***");
							System.out.print("Enter choice (Y or N): ");
							tryAgain = sc.nextLine();
						} 
					}
				} while (!tryAgain.equalsIgnoreCase("N"));
				
				System.out.println("---------------------------------------------------------------------");						
				System.out.println();
				System.out.println();
			} else if (choice.equals("2")) {
				User user = company.createUser();
				
				// if valid creation, return the user
				if (user != null) {
					return user;
				}				
			} else {
				// if user enters 'x', return null
				return null;
			}	
		} while(true);		
	}
	
	// outputs the menu, and branches off to the various functions of the program in classes
	public static void mainMenu() {
		// variables
		String choice = "";
		boolean valid = false; // represents a valid input
		Scanner sc = new Scanner(System.in);
		
		// loops until user logs out
		do {
			valid = false;
			System.out.println("Menu                                                       [x] Logout");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("*** What would you like to do? ***");
			System.out.println("[1] View Inbox");
			System.out.println("[2] Compose New Message");
			System.out.println("[3] View Employees");
			System.out.println("[4] Settings");
			System.out.println("---------------------------------------------------------------------");
			System.out.print("Enter choice: ");
			choice = sc.nextLine(); // not sure why you changed this..?
			
			// loops until valid choice
			while (!valid) {
// 				choice = sc.nextLine();

				try {
					while(!choice.equals("x") && !(Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= 4)) {
						System.out.println();
						System.out.println("*** Input is invalid ***");
						System.out.print("Enter choice (1 to 4, or x): ");
						choice = sc.nextLine();
					}
					valid = true;
				} catch (NumberFormatException nfe) {
 					choice = "0";
				}
			}

			System.out.println("---------------------------------------------------------------------");						
			System.out.println();
			System.out.println();
			
			if (choice.equals("1")) {
				allConvos = new ConversationDatabase(company);
				inbox = new Inbox(currentUser, allConvos);
				
				// runs inbox
				inbox.runner();				
			} else if (choice.equals("2")) {
				allConvos = new ConversationDatabase(company);
			
				Message newMessage = new Message(currentUser, company);
				
				// checks if message was created (no early exit with "x")
				if (newMessage.getSender() == null) {
				} else {
					// finding convo to put newMessage				
					Conversation matchingConvo = allConvos.findConversation(newMessage);
					
					if (matchingConvo != null) {
						matchingConvo.addMessage(newMessage);
					} else {
						allConvos.createConversation(newMessage);
					}
				}				
			} else if (choice.equals("3")) {
				allConvos = new ConversationDatabase(company);
				company = new Company();				
				contacts = new EmployeeList(currentUser, company, allConvos);
				
				// runs employee list
				contacts.runner();		
			} else if (choice.equals("4")) {
				allConvos = new ConversationDatabase(company);
				company = new Company();
				userSettings = new Settings(currentUser, company, allConvos);
				
				// runs settings
				userSettings.runner();		
			}
			// if choice is x, while loop will exit			
		} while(!choice.equals("x"));	
	}
		
}