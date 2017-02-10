package RAS;
public class Reservation 
{
	//public Reservation[] reservationArray;//just added
	public String name;
	public int size;
	int reservationCounter;
	
	public Reservation()
	{
		name = " ";
		size = 0;	
	}
		
	public Reservation(String nameEntered,int sizeEntered)
	{
		name = nameEntered;
		size = sizeEntered;
	}
	
	
	boolean setReservationSize(int sizeEntered)
	{
		size = sizeEntered;
		return true;
	}
	
	boolean setReservationName(String nameEntered)
	{
		name = nameEntered;
		return true;
	}
		
	int getReservationSize()
	{
		return size;
	}
	
	String getReservationName()
	{
		return name;
	}
	public String toString()
	{
		return ("The party name is: " + name + "\nThe party size is: " + size);
	}

}