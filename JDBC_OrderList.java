package RAS;

//import java.util.ArrayList;
//import java.util.List;
import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;


public class JDBC_OrderList {

	public Statement myStat;
	public ResultSet myRs;
	public int intmyRs;
	public int exc;

	Connection conn = Driver.getConnection();

	public boolean SendOrder(int tableNum, String accName,Item[] item){

		String order = "";
		for(int i=0; i<item.length;i++){
			order+=item[i].getId();
		}

		double total = 0;
		for(int i=0; i<item.length;i++){
			total+=item[i].getItemPrice();
		}

		try{	
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			exc = myStat.executeUpdate("INSERT INTO Orders (Table_Num,Acc_Name,Item_List,Order_Total) VALUES ("+tableNum+",'"+accName+"','"+order+"',"+total+");");           
			//3: Process the result set
		}
		//catch any exception
		catch(Exception e){
			e.printStackTrace();
		}

		return true;

	}

	public Order getOrder(int id){
		Order resultOrder = null;

		try{	
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			myRs = myStat.executeQuery("select * from orders WHERE Order_Num = "+id+";");
			//3: Process the result set
			while (myRs.next()){
				String itemidslist = myRs.getString("Item_List");
				Item[] finalArr=Order.parseId(itemidslist);

				resultOrder = new Order(myRs.getInt("Table_Num"), myRs.getString("Acc_Name"),finalArr,myRs.getInt("Order_Num"), myRs.getDouble("Order_Total"));
				//itemString = resultItem.toString();
			}
		}
		//catch any exception
		catch(Exception e){
			e.printStackTrace();
		}
		return resultOrder;

	}

	public double applyDiscount(Order order, double percentage){
		double total = order.getTotal();
		int id = order.getOrderNum();
		double ntotal = total -(total*(percentage/100));

		try{	
			//1: Create a statement 
			myStat = conn.createStatement();
			//2: Execute SQL
			intmyRs = myStat.executeUpdate("update orders set Order_Total ="+ntotal+" WHERE Order_Num = "+id+";");

		}
		catch(Exception e){
			e.printStackTrace();
		}

		return ntotal;

	}


	public boolean EditOrder(int ID, String newItem){
		String item_List = null;

		try{
			myStat = conn.createStatement();
			//Statement created

			myRs = myStat.executeQuery("Select * FROM Orders WHERE Order_Num = "+ID+";");
			while (myRs.next()){
				item_List = myRs.getString("Item_List") + newItem;
			}
			exc = myStat.executeUpdate("UPDATE Orders SET Item_List = '"+item_List+"' WHERE Order_Num = "+ID+";");

			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;}
	}
	public String veiw_Waiting_Order(){
		String order_list = "";

		try{
			Statement myStmt = conn.createStatement();//...Create Statement
			ResultSet myRs = myStmt.executeQuery("select * from Orders");//...Execute SQL query Table name is employee

			Queue<String> myQueue = new LinkedList<String>();
			Queue<Integer> myNumbers = new LinkedList<Integer>();
			myQueue.add("");
			myQueue.add("");
			myNumbers.add(1);
			myNumbers.add(2);

			while (myRs.next()){


				order_list += " OrderNumber: \n"+myRs.getInt("Order_Num") + "\n Table Number: \n " + myRs.getInt("Table_Num") +"\n Item List: \n " + myRs.getString("Item_List")+"\n Order Stat: \n "+myRs.getString("Order_Status");

			}

		}
		catch(Exception exc){
			exc.printStackTrace();

		}
		return order_list;



	}

	public boolean VoidItem(int item,int id) {
		try{
			String collect="";

			Statement myStmt = conn.createStatement();//...Create Statement
			myRs = myStmt.executeQuery("select * from orders WHERE Order_Num = "+id+";");//...Execute SQL query Table chooseing the OrderNum

			while (myRs.next()){
				collect=myRs.getString("Item_List");

				StringBuilder bulid = new StringBuilder(collect);

				for(int i=0;i<collect.length();i++)
				{
					int compare=Character.getNumericValue(collect.charAt(i));

					if(compare==item){//go through each charater and compare item that integer thats input

						bulid.deleteCharAt(i); 
						collect=bulid.toString();
						intmyRs = myStmt.executeUpdate("update orders set Item_List = '"+collect+"' WHERE Order_Num = "+id+";");		
					}
					//else
					//	System.out.println("nothing in here");
				}


			}
		}
		catch(Exception exc){

			return false;

		}
		return true;

	}

	public boolean ServeTicket(int orderid){

		try{
			myStat = conn.createStatement();
			//Statement created

			exc = myStat.executeUpdate("UPDATE Orders SET Order_Status = 'Served' WHERE Order_Num = "+orderid+";");
			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;}
		
		return true;
	}
	
	public double payOrder(double amount, Order order){
        double total = order.getTotal();
        int id = order.getOrderNum();
        //double ntotal = (total-amount);
        double tax = .005;
        int orderNum=0;
        int tableNum=0;
        String accName="";
        String itemList="";
        String orderStatus="";
        double orderTotal=0;
        String orderDate="";
        
        int OrNum = order.getTableNum();
        JDBC_Floor fl = new JDBC_Floor();

        try
        {    
            total  = ( total * tax ) + total;
            //1: Create a statement 
            myStat = conn.createStatement();
            //2: Execute SQL
            myRs = myStat.executeQuery("select * from Orders WHERE Order_Num = "+OrNum+";");
			//3: Process the result set
			while (myRs.next())
			{
				orderNum=myRs.getInt("Order_Num");
				tableNum=myRs.getInt("Table_Num"); //Retrieve Table Number from database
				accName=myRs.getString("Acc_Name");
				itemList=myRs.getString("Item_List");
				orderStatus=myRs.getString("Order_Status");
				orderTotal=myRs.getDouble("Order_Total");
				orderDate=myRs.getString("Order_Date");
			}	
            //INSERT INTO persons_table select * from customer_table where person_name = 'tom';
            //DELETE FROM customer_table where person_name = 'tom';
            
            String sql="INSERT INTO Orders (Order_Num,Table_Num,Acc_Name,Item_List,Order_Status,Order_Total,Order_Date) VALUES ("+orderNum+","+tableNum+",'"+accName+"','"+itemList+"','"+orderStatus+"',"+orderTotal+",'"+orderDate+"');";
            myStat.executeUpdate(sql);
            
            myStat.executeUpdate("Delete From Orders WHERE Order_Num = "+id+";");
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        fl.setTableStatus(tableNum,"Dirty");
        return total;
    }
}




