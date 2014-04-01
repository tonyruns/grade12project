/*
	Class Name: Employee.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 9, 2013 ; 5:35 pm
	School:		AY Jackson SS
	Purpose: 	Used to denote a standard Employee, subclass of User
*/

public class Employee extends User{
	
	//Constructor
	public Employee (String id, UserInfo userInfo, WorkInfo workInfo, LoginInfo loginInfo){
		super (id, userInfo, workInfo, loginInfo);
	}
	
}