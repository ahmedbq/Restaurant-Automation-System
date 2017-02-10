package RAS;
import java.awt.List;
import java.util.Iterator;
import java.util.ArrayList;

public class AccountList{

	//Creating JDBCAccountList Object
	JDBCAccountList JDBCAccountList = new JDBCAccountList();

	//Instance Variables
	private Account AccountPlaceHolder = new Account();
	//An AccountPlaceHolder to hold an Account value
	//that has to be returned when a deletion occurs
	public static ArrayList AccountList;
	//An ArrayList is used in order to store the Account
	//objects rather than the Array for easy access and
	//the lack of a limit.

	public AccountList(){

		AccountList = new ArrayList<Account>(); 

	}

	public boolean AddEmployee(String Name, String Pin, String Position, Double Total_Sales){
		try{
			JDBCAccountList.AddEmployee(Name, Pin, Position, Total_Sales);
			//Calls the JDBC function and then works with the local ArrayList below

			//Initializing these variables
			//They will be certain values
			//according to the database info
			double PayRate = 0.0;
			double Hours = 0;
			int ID = -1;
			//ERROR HERE
			ID = JDBCAccountList.JDBCgetID(Pin);
			//According to a specific Position, its payRate will be set
			if(Position.equals("Manager")){
				PayRate = 20.0;
				Hours = 8.0;
			}
			else if(Position.equals("Server")){
				PayRate = 10.0;
				Hours = 10.0;
			}
			else if(Position.equals("Busboy")){
				PayRate = 7.0;
				Hours = 8.0;
			}
			else if(Position.equals("Waiter")){
				PayRate = 10.0;
				Hours = 10.0;
			}
			else if(Position.equals("Kitchen")){
				PayRate = 13.0;
				Hours = 8.0;
			}
			else if(Position.equals("Cook")){
				PayRate = 13.0;
				Hours = 8.0;
			}
			else if(Position.equals("Host")){
				PayRate = 15.0;
				Hours = 8.0;
			}

			Account newEmployee = new Account(Pin, Name, Position, ID, Hours, Total_Sales, PayRate);
			AccountList.add(newEmployee);
			//Adds Employee into the ArrayList
			return true;}
		catch(Exception e){return false;}
	}//End AddEmployee

	public Account RemoveEmployee(int ID){

		JDBCAccountList.RemoveEmployee(ID);
		//Calls the JDBC function and then works with the local ArrayList below
		Iterator<Account> iterator = AccountList.iterator();
		while (iterator.hasNext()) {//Goes through the ArrayList

			if(iterator.next().getID() == ID){ //May be buggy but since using getID in same if statement

				//Setting the AccountPlaceHolder to be equal to the Account in the ArrayList
				AccountPlaceHolder.setID(((Account)iterator).getID());
				AccountPlaceHolder.setName(((Account)iterator).getName());
				AccountPlaceHolder.setPin(((Account)iterator).getPin());
				AccountPlaceHolder.setPosition(((Account)iterator).getPosition());
				AccountPlaceHolder.setSales(((Account)iterator).getSales());
				AccountPlaceHolder.setHours(((Account)iterator).getHours());
				AccountPlaceHolder.setPayRate(((Account)iterator).getPayRate());

				iterator.remove(); //Should remove element from ArrayList AccountList
				return AccountPlaceHolder;
			}			
		}
		return null;
		//Already not in the ArrayList
	}//End Remove Employee

	public Account EditEmployee(int ID, String newPin, String newName, String newPosition, 
			double newHours, double newSales, double newPayRate)
	{
		//Cannot edit ID, but it is used to find the employee
		JDBCAccountList.EditEmployee(ID, newPin, newName, newPosition, newHours, newSales, newPayRate);
		//Calls the JDBC function and then works with the local ArrayList below
		Iterator<Account> iterator = AccountList.iterator();
		while (iterator.hasNext()) {//Goes through the ArrayList

			int CheckID = iterator.next().getID();
			if(CheckID == ID){

				//Setting the AccountPlaceHolder to be equal to the Account in the ArrayList
				AccountPlaceHolder.setID(((Account)iterator).getID());
				AccountPlaceHolder.setName(((Account)iterator).getName());
				AccountPlaceHolder.setPin(((Account)iterator).getPin());
				AccountPlaceHolder.setPosition(((Account)iterator).getPosition());
				AccountPlaceHolder.setSales(((Account)iterator).getSales());
				AccountPlaceHolder.setHours(((Account)iterator).getHours());
				AccountPlaceHolder.setPayRate(((Account)iterator).getPayRate());

				iterator.remove(); //Should remove element from ArrayList AccountList
				return AccountPlaceHolder;
			}			
		}
		return null;
	}
	
	public String toString(){
		return JDBCAccountList.toString();
	}

}//End Class