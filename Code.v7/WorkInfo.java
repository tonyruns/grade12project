   /*
	Class Name: UserInfo.java
	Authors:		George Ke, Tony Jin, Fion Chan
	Date: 		Jan. 9, 2013 ; 10:37 pm
	School:		AY Jackson SS
	Purpose: 	contains the work information of the User including depart and shift start/end times
	*/
	
   import java.util.*;
   public class WorkInfo{
   
   	//fields
      private String department;
      private String shiftStartTime;
      private String shiftEndTime;
   
   	 //Constructor 
      public WorkInfo (String department, String shiftStartTime, String shiftEndTime){
       
         this.department=department;
         this.shiftStartTime=shiftStartTime;
         this.shiftEndTime=shiftEndTime;
      	
      }
   
   	//setDepartment: set field department to new department
      public void setDepartment(String newDepartment){
         department=newDepartment;
      }
      
   	//setShiftStartTime: set field shiftStartTime to new newStartTime
      public void setShiftStartTime(String newStartTime){
         shiftStartTime=newStartTime;
      }
      
   	//setShiftEndTime: set field setShiftEndTime to new newEndTime
      public void setShiftEndTime(String newEndTime){
         shiftEndTime=newEndTime;
      }
      
   	//toString: outputs the fields in the format of shown in the settings screen
      public String toString(){
         String output;
         output="\n***Work Information***";
         output+="\nDepartment: "+department;
         output+="\nShift Start Time: "+shiftStartTime;
         output+="\nShift End Time: "+shiftEndTime;
      
         return output;    
      }
     
     	//getDepartment: get field department
      public String getDepartment(){
         return department;
      }
   	
   	//getShiftStartTime: get field shiftStartTime
      public String getShiftStartTime(){
         return shiftStartTime;
      }
   	
   	//shiftEndTime: get field shiftEndTime
      public String getShiftEndTime(){
         return shiftEndTime;
      }
   
   
   
   
   
   }