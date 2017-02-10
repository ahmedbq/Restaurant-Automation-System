package RAS;
import java.sql.*;

public class JDBCAccountList{

	public static Account JDBCAccountPlaceHolder = new Account();

	//Constructors
	public JDBCAccountList(){}
	//End Constructors

	public Statement myStat;
	public ResultSet myRs;
	public int intmyRs;
	public int exc;

	Connection conn = Driver.getConnection();

	//AddEmployee
	public boolean AddEmployee(String Name, String pin, String Position, Double Total_Sales){

		try{
			myStat = conn.createStatement();
			//Statement created

			exc = myStat.executeUpdate("INSERT INTO Accounts (Acc_Name, Acc_Pin, Acc_Position, Total_Sales) VALUES ('"+Name+"', '"+pin+"', '"+Position+"', "+Total_Sales+");"); 
			//SQL
			//ID is auto-incremented

			return true;
		}
		catch(Exception e){
			e.printStackTrace(); 
			return false;}

	}//End AddEmployee

	public boolean RemoveEmployee(int ID){

		try{
			myStat = conn.createStatement();
			//Statement created

			exc = myStat.executeUpdate("DELETE FROM Accounts WHERE Acc_ID = "+ID+";");
			//SQL
			//This SQL version does not use "*"

			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean EditEmployee(int ID, String newPin, String newName, String newPosition, 
			double newHours, double newSales, double newPayRate){
		try{
			myStat = conn.createStatement();
			//Statement created

			exc = myStat.executeUpdate("DELETE FROM Accounts WHERE Acc_ID = "+ID+";");
			exc = myStat.executeUpdate("INSERT INTO Accounts(Acc_ID, Acc_Name, Acc_Pin, Acc_Position, Total_Sales) VALUES("+ID+", '"+newName+"', '"+newPin+"', '"+newPosition+"', "+newSales+");");
			//SQL
			//This SQL version does not use "*"

			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;}
	}

	public int JDBCgetID(String Pin){
		int ID = -1;

		try{myStat = conn.createStatement();

		myRs = myStat.executeQuery("SELECT Acc_ID FROM Accounts WHERE Acc_Pin = '"+Pin+"';");
		//Get ID from database..
		if(myRs.next()) {
			ID = myRs.getInt("Acc_ID");
			return ID;
		}
		}
		catch(Exception e){
			e.printStackTrace();
			return ID;
		}
		return ID;
	}

	public String JDBCgetName(String Pin){
		String Name = "";

		try{myStat = conn.createStatement();
		myRs = myStat.executeQuery("SELECT Acc_Name FROM Accounts WHERE Acc_Pin = '"+Pin+"';");
		//Get ID from database..
		if(myRs.next()) {
			Name = myRs.getString("Acc_Name");
			return Name;
		}
		}
		catch(Exception e){
			e.printStackTrace();
			return Name;
		}
		return Name;
	}

	//Useful for EditEmployee
	public String JDBCgetPin(int ID){
		String Pin = "";

		try{myStat = conn.createStatement();
		myRs = myStat.executeQuery("SELECT Acc_Pin FROM Accounts WHERE Acc_ID = '"+ID+"';");
		//Get ID from database..
		if(myRs.next()) {
			Pin = myRs.getString("Acc_Pin");
			return Pin;
		}
		}
		catch(Exception e){
			e.printStackTrace();
			return Pin;
		}
		return Pin;
	}


	public String JDBCgetPosition(String Pin){
		String Position = "";

		try{myStat = conn.createStatement();

		myRs = myStat.executeQuery("SELECT Acc_Position FROM Accounts WHERE Acc_Pin = '"+Pin+"';");
		//Get ID from database..
		if(myRs.next()) {
			Position = myRs.getString("Acc_Position");
			return Position;
		}
		}
		catch(Exception e){
			e.printStackTrace();
			return Position;
		}
		return Position;
	}

	public double JDBCgetSales(String Pin){
		double Sales = 0.0;

		try{myStat = conn.createStatement();

		myRs = myStat.executeQuery("SELECT Total_Sales FROM Accounts WHERE Acc_Pin = '"+Pin+"';");
		//Get ID from database..
		if(myRs.next()) {
			Sales = myRs.getDouble("Total_Sales");
			return Sales;
		}
		}
		catch(Exception e){
			e.printStackTrace();
			return Sales;
		}
		return Sales;
	}

	//Checks if Pin exists in the system (for login)
	public boolean PinChecker(String Pin){
		String collectPin;
		try{myStat = conn.createStatement();
		myRs = myStat.executeQuery("SELECT Acc_Pin FROM Accounts WHERE Acc_Pin = '"+Pin+"';");
		//Gets Pin from database
		if(myRs.next()){
			collectPin = myRs.getString("Acc_Pin");
			if(collectPin.equals(Pin)){
				//Pin matches
				return true;
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
			//Cannot find Pin
		}
		return false;
		//Pin doesn't match
	}

	public String toString(){
		String IDCollection = "\tID: ";
		String NameCollection = "\tName: ";
		String PinCollection = "\tPin: ";
		String PositionCollection = "\tPos.: ";
		String SalesCollection = "\tSales: ";
		String PayRateCollection = "\tPayRate: ";
		String HoursCollection = "\tHours: ";

		double StoreSales = 0.0;

		try{myStat = conn.createStatement();		
		myRs = myStat.executeQuery("SELECT * FROM Accounts;");
		//Gets Pin from database
		while(myRs.next()){
			int IDCollect = myRs.getInt("Acc_ID");
			double SalesCollect = myRs.getDouble("Total_Sales");

			IDCollection += "\t\t" + IDCollect + "" + "\t\t";
			NameCollection += "\t\t" + myRs.getString("Acc_Name") + "\t\t";
			PinCollection += "\t\t" +myRs.getString("Acc_Pin") + "\t\t";
			PositionCollection += "\t\t" + myRs.getString("Acc_Position") + "\t\t";
			SalesCollection += "\t\t$" + SalesCollect + "" + "\t\t";

			StoreSales += SalesCollect;

			//PayRate and Hours determined from position
			String PayRate = "";
			String Hours = "";
			if(myRs.getString("Acc_Position").equals("Manager")){
				PayRate = "20.0";
				Hours = "8.0";
			}
			else if(myRs.getString("Acc_Position").equals("Server")){
				PayRate = "10.0";
				Hours = "10.0";
			}
			else if(myRs.getString("Acc_Position").equals("Busboy")){
				PayRate = "7.0";
				Hours = "8.0";
			}
			else if(myRs.getString("Acc_Position").equals("Waiter")){
				PayRate = "10.0";
				Hours = "10.0";
			}
			else if(myRs.getString("Acc_Position").equals("Kitchen")){
				PayRate = "13.0";
				Hours = "8.0";
			}
			else if(myRs.getString("Acc_Position").equals("Cook")){
				PayRate = "13.0";
				Hours = "8.0";
			}
			else if (myRs.getString("Acc_Position").equals("Host")){
				PayRate = "15.0";
				Hours = "8.0";
			}
			PayRateCollection += "\t$" + PayRate + "\t\t\t";
			HoursCollection += "\t\t" + Hours + "\t\t";
		}
		}
		catch(Exception e){
			e.printStackTrace();
			//Empty table
		}

		return IDCollection + "\n" + NameCollection + "\n" + PinCollection + "\n"
		+ PositionCollection + "\n" + SalesCollection + "\n" + PayRateCollection +
		"\n" + HoursCollection + "\n\n\tStore Sales: $" + StoreSales + "\n\n";
	}


}//End Class
