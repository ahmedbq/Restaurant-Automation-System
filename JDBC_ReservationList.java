package RAS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBC_ReservationList 
{
	public Statement myStat;
	public ResultSet myRs;
	public int intmyRs;
	public int exc;
	
	Connection conn = Driver.getConnection();
		
//	public String name;
	
	//View Reservation
	public List<String> viewReservations()
	 {
		List<String> resultStr = new ArrayList<String>();
		
		try
		{	
			//1: Create a statement 
			myStat = conn.createStatement();
			
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from Reservation");
			
			//3: Process the result set
			while (myRs.next())
			{
				resultStr.add(myRs.getString("reserveName")+" "+myRs.getInt("reserveSize"));
			}
		}
		//catch any exception
		catch(Exception e){
			e.printStackTrace();
		}
		return resultStr;
		
	}
	
	//Add Reservation
	public boolean addReservation(String reserveName, int reserveSize)
	{		
		//List<String> resultStr = new ArrayList<String>();
		
			try
			{	
				//1: Create a statement 
				myStat = conn.createStatement();
				
				//2: Execute SQL
				exc = myStat.executeUpdate("INSERT INTO Reservation (reserveName, reserveSize) VALUES ('"+reserveName+"',"+reserveSize+");");
			}
			//catch any exception
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return true;
	}
	
	public Reservation removeReservation(String name)
	{
		int nameSearch = searchReservation(name);
		if(nameSearch == 0 || nameSearch == -1) //Reservation does not exist
		{
			return null;
		}
		else //Reservation does exist
		{
			try
			{
				String name1 = "";
				int size=0;
				//1: Create a statement 
				myStat = conn.createStatement();
				//2: Execute SQL
				myRs = myStat.executeQuery("select * from Reservation WHERE reserveName = '"+name+"';");
				//3: Process the result set
				while (myRs.next())
				{
					name1=myRs.getString("reserveName"); //Retrieve Reservation Name from database
					size=myRs.getInt("reserveSize"); //Retrieve Reservation Size from database
				}	
				PreparedStatement st = conn.prepareStatement("DELETE FROM Reservation WHERE reserveName = '" +name+ "' ;");
				st.executeUpdate();
				Reservation r = new Reservation(name1,size);
				return r; //return the removed Reservation
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	}
	
	//Search Reservation
	public int searchReservation(String name)
	{
		try
		{
			//String check = " ";
			int nameSearch = 0; //represents reservation name

			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			myRs = myStat.executeQuery("select reserveName from Reservation WHERE (reserveName = '"+name+"');");
			//3: Process the result set
			while (myRs.next())
			{
				//check = (myRs.getString("reserveName"));
				nameSearch++;
			}	
			return nameSearch; //return the number of found reservations
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1; //Reservation not found
	}
	
	//toString
	public String toString()
	{
		String r = "";
		try
		{
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from Reservation");
			//3: Process the result set
			while (myRs.next())
			{
				r+=("Reservation ID: "+myRs.getInt("reserveID")+ "  Name: "+myRs.getString("reserveName")+"  Size: "+myRs.getInt("reserveSize")+"\n");
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
}