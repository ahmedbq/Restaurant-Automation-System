package RAS;

public class Account{
	JDBCAccountList JDBCAccountList = new JDBCAccountList();

	//Instance Variables
	private String Pin; 
	private String Name; 
	private String Position; 
	private int ID; 
	private double Hours; 
	private double Sales;
	private double PayRate;

	//Constructors
	public Account(){
		this.Name = "";
		this.Pin = "";
		this.Position = "";
		this.ID = -1;
		this.Hours = 0.0;
		this.Sales = 0.0;
		this.PayRate = 9.0;
	}

	public Account(String newPin, String newName, String newPosition, 
			int newID, double newHours, double newSales, double newPayRate){
		this.Name = newName;
		this.Pin = newPin;
		this.Position = newPosition;
		this.ID = newID;
		this.Hours = newHours;
		this.Sales = newSales;
		this.PayRate = newPayRate;
	}//End Constructors

	//Getters
	public String getPin() {
		return Pin;
	}

	public String getName() {
		return Name;
	}

	public String getPosition() {
		return Position;
	}

	public int getID() {
		return ID;
	}

	public double getHours() {
		return Hours;
	}

	public double getSales() {
		return Sales;
	}

	public double getPayRate() {
		return PayRate;
	}

	//Setters

	public boolean setID(int ID) {
		try{this.ID = ID;
		return true;}
		catch(Exception e){
			return false;
		}
	}

	public boolean setName(String Name) {
		try{this.Name = Name;
		return true;}
		catch(Exception e){
			return false;
		}
	}

	public boolean setPin(String Pin) {
		try{this.Pin = Pin;
		return true;}
		catch(Exception e){
			return false;
		}
	}

	public boolean setPosition(String Position) {
		try{this.Position = Position;
		return true;}
		catch(Exception e){
			return false;
		}
	}

	public boolean setSales(double Sales) {
		try{this.Sales = Sales;
		return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public boolean setHours(double Hours) {
		try{this.Hours = Hours;
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public boolean setPayRate(double PayRate) {
		try{this.Hours = Hours;
		return true;			
		}
		catch(Exception e){
			return false;
		}
	}

	public void setEqualTo(Account iterator) {
		setName(iterator.getName());
		setID(iterator.getID());
		setPosition(iterator.getPosition());
		setHours(iterator.getHours());
		setSales(iterator.getSales());
		setPin(iterator.getPin());
		setPayRate(iterator.getPayRate());
	}

	public String toString() {
		//Grabbing information from database
		String JDBCName = JDBCAccountList.JDBCgetName(Pin);
		String JDBCPosition = JDBCAccountList.JDBCgetPosition(Pin);
		int JDBCID = JDBCAccountList.JDBCgetID(Pin);
		double JDBCTotalSales = JDBCAccountList.JDBCgetSales(Pin);
		
		//According to a specific Position, PayRate and Hours will be returned
		if(JDBCPosition.equals("Manager")){
			PayRate = 20.0;
			Hours = 8.0;
		}
		else if(JDBCPosition.equals("Server")){
			PayRate = 10.0;
			Hours = 10.0;
		}
		else if(JDBCPosition.equals("Busboy")){
			PayRate = 7.0;
			Hours = 8.0;
		}
		else if(JDBCPosition.equals("Waiter")){
			PayRate = 10.0;
			Hours = 10.0;
		}
		else if(JDBCPosition.equals("Kitchen")){
			PayRate = 13.0;
			Hours = 8.0;
		}
		else if(JDBCPosition.equals("Cook")){
			PayRate = 13.0;
			Hours = 8.0;
		}
		
		return "Name \t\tPosition \tID \tHours \tSales \tPayRate \n" + JDBCName + "\t\t" + JDBCPosition
				+"\t\t" + JDBCID + "\t" + Hours + "\t$" + JDBCTotalSales + "\t$" + PayRate + "\n";
		//Will not return pin due to security issues
		//However, the Manager Report will show the PIN
	}


}//End Class	