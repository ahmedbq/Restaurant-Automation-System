package RAS;
/**
 * @author Rob McCartney
 * @version 1.0
 * Floor class
 * collection of Table objects
 */
public class Floor {
	
	JDBC_Floor fl = new JDBC_Floor();
	
	/**
	 * toString method
	 * returns string representation of Floor array
	 * @return string containing the size, number and status of all Table objects in the Floor array
	 */
	public String toString()
	{
		return fl.toString();
	}
	
	/**
	 * addNewTable method
	 * adds a Table object to the Floor array
	 * @param t representing the Table object to be added
	 * @return boolean representing the success/failure of the addition
	 */
	public boolean addTable(int number, int size)
	{
		return fl.addTable(number, size);
	}
	
	/**
	 * removeTable method
	 * removes a Table from the Floor array based on tableNumber
	 * @param number integer representing table number
	 * @return Table representing the Table object removed from the Floor array
	 */
	public Table removeTable(int number)
	{
		return fl.removeTable(number);
	}
	
	/**
	 * searchFloor method
	 * searches the Floor array for the given Table number
	 * @param number integer representing the table number
	 * @return integer representing the index of the found Table object in the Floor array
	 */
	public int searchFloor(int number)
	{
		return fl.searchFloor(number);
	}
	
	public String viewDirtyTables()
	{
		return fl.viewDirtyTables();
	}
	
	public int setTableStatus(int number, String newStatus)
	{
		return fl.setTableStatus(number, newStatus);
	}

}
