package RAS;

import java.sql.*;
import RAS.Driver;

public class JDBC_Floor 
{
	public Statement myStat;
	public ResultSet myRs;
	public int intmyRs;
	public int exc;
	
	Connection conn = Driver.getConnection();
	
	public boolean addTable(int number, int size)
	{
		String status = "Ready"; //Default all tables to Ready
		int numCheck = searchFloor(number);
		if(numCheck==-1)
		{
			try{	
				//1: Create a statement 
				myStat = conn.createStatement();
				//2: Execute SQL
				exc = myStat.executeUpdate("INSERT INTO RAS_Table (Table_Num,Table_Size,Table_Status) VALUES ("+number+","+size+",'"+status+"');");           
			}
			//catch any exception
			catch(Exception e){
				e.printStackTrace();
			}
		return true;
		}
		else
			return false;
	}
	
	public Table removeTable(int number)
	{
		int numCheck = searchFloor(number);
		if(numCheck==-1) //Table does not exist
		{
			return null;
		}
		else //Table does exist
		{
			try
			{
				int num=0;
				int size=0;
				//1: Create a statement 
				myStat = conn.createStatement();
				//2: Execute SQL
				myRs = myStat.executeQuery("select * from RAS_Table WHERE Table_Num = "+number+";");
				//3: Process the result set
				while (myRs.next())
				{
					num=myRs.getInt("Table_Num"); //Retrieve Table Number from database
					size=myRs.getInt("Table_Size"); //Retrieve Table Size from database
				}	
				PreparedStatement st = conn.prepareStatement("DELETE FROM RAS_Table WHERE Table_Num = '" +number+ "';");
				st.executeUpdate();
				Table t = new Table(num,size);
				return t; //return the removed Table
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	}
	
	public int searchFloor(int number)
	{
		try
		{
			int check = -1; //represents Table Number
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from RAS_Table WHERE Table_Num = "+number+";");
			//3: Process the result set
			while (myRs.next())
			{
				check = myRs.getInt("Table_Num"); //set to the Table Number found in database
			}	
			return check; //return the found Table Number
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return -1; //Table Number not found
	}
	
	public String toString()
	{
		String r = "";
		try
		{
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from RAS_Table");
			//3: Process the result set
			while (myRs.next())
			{
				r+=("Table #"+myRs.getInt("Table_Num")+"  Size="+myRs.getInt("Table_Size")+"  Status="+myRs.getString("Table_Status")+"\n");
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String viewDirtyTables()
	{
		String r = "";
		String status = "Dirty";
		try
		{
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from RAS_Table WHERE Table_Status = '"+status+"';"); //only selects dirty tables
			//3: Process the result set
			while (myRs.next())
			{
				r+=("Table #"+myRs.getInt("Table_Num")+"  Size="+myRs.getInt("Table_Size")+"  Status="+myRs.getString("Table_Status")+"\n"); //same as toString
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(r.equals(""))
			return ("There are currently no dirty tables\n");
		else
			return r;
	}
	
	public int setTableStatus(int number, String newStatus)
	{
		try
		{
			String currentStatus = "";
			//1: Create a statement 
			myStat = conn.createStatement(); 
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from RAS_Table WHERE Table_Num = "+number+";");
			//3: Process the result set
			while (myRs.next())
			{
				currentStatus = myRs.getString("Table_Status"); 
			}	
			if((currentStatus.equals("Dirty") && newStatus.equals("Ready"))||(currentStatus.equals("Ready") && newStatus.equals("Full"))||(currentStatus.equals("Full") && newStatus.equals("Dirty")))
			{
				return myStat.executeUpdate("UPDATE RAS_Table set Table_Status = '"+newStatus+"' WHERE Table_Num = "+number+";"); //returns # of updated records
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0; //nothing was changed
	}

}
