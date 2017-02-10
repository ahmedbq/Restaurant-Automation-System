package RAS;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class JDBC_ItemList {
	
	public Statement myStat;
	public ResultSet myRs;
	public int exc;
	Connection conn = Driver.getConnection();
	
	//returns all current items in the db
	public List<String> getItems(){
		List<String> resultStr = new ArrayList<String>();
		
		try{	
			//1: Create a statement 
			myStat = conn.createStatement();
			
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from Item_List");
			
			//3: Process the result set
			while (myRs.next()){
				resultStr.add(myRs.getString("Item_ID")+" "+myRs.getString("Item_Name"));
			}
		}
		//catch any exception
		catch(Exception e){
			e.printStackTrace();
		}
		return resultStr;
		
	}
	
	//this function returns a item from the db table Item_List
	public Item selectItem(int id){
		Item resultItem = null;
		try{	
			//1: Create a statement 
			myStat = conn.createStatement();
			
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from Item_List WHERE Item_ID = "+id+";");
			
			//3: Process the result set
			while (myRs.next()){
			resultItem = new Item(myRs.getInt("Item_ID"), myRs.getString("Item_Name"), myRs.getString("Item_Type"), myRs.getDouble("Item_Price"));
			//itemString = resultItem.toString();
			}
		}
		//catch any exception
		catch(Exception e){
			e.printStackTrace();
		}
		return resultItem;
		
	}
	
	
	public boolean addItem(String name, double price, String type){
		try{	
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			exc = myStat.executeUpdate("INSERT INTO Item_List (Item_Name,Item_Type,Item_Price) VALUES ('"+name+"','"+type+"',"+price+");");           
			//3: Process the result set
		}
		//catch any exception
		catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public boolean deleteItem(int id){
		try{	
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			exc = myStat.executeUpdate("DELETE FROM Item_List WHERE Item_ID = '" +id+ "';");           
			//3: Process the result set
		}
		//catch any exception
		catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
		
	}

	
	

}
