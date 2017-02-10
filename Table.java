package RAS;

/**
 * @author Rob McCartney
 * @version 1.0
 * table class
 * allows for the creation of Table objects
 * 
 */
public class Table {

	/**
	 * the size of the table 
	 */
	private int tableSize;
	
	/**
	 * the unique number assigned to a table
	 */
	private int tableNumber;
	
	/**
	 * describes the status of the table, limited to "dirty", "ready" and "full"
	 */
	private String tableStatus;
	
	/**
	 * Table default constructor,
	 */
	public Table()
	{
		
	}
	
	/**
	 * Table constructor with parameters, 
	 * creates a Table with tableSize and tableNumber based on parameters,
	 * tableStatus is set to "ready" by default
	 * @param size integer representing table size
	 * @param number integer representing table number
	 */
	public Table(int number, int size)
	{
		tableNumber=number;
		tableSize=size;
		tableStatus="Ready";
	}
	
	/**
	 * getTableNumber method
	 * returns the number of the Table
	 * @return int representing the table number 
	 */
	public int getTableNumber()
	{
		return tableNumber;
	}
	
	/**
	 * getTableSize method
	 * returns the size of the table
	 * @return int representing the table size
	 */
	public int getTableSize()
	{
		return tableSize;
	}
	
	/**
	 * getTableStatus method
	 * returns full, dirty, or ready
	 * @return string representing table status 
	 */
	public String getTableStatus()
	{
		return tableStatus;
	}

	/**    
	 * toString method
	 * returns string of all Table elements
	 * @return string containing the size, number and status of table
	 */
	public String toString()
	{
		return "Table #"+tableNumber+"  Size="+tableSize+"  Status="+tableStatus;
	}
	
}
