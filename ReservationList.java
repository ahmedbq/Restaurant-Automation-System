package RAS;

public class ReservationList 
{
	//private variables
	private String name= "";
	private int size = 0;
	private int reservationCounter = 0;
	private Reservation reservationArray[];
	
	//default constructors
	ReservationList(String name, int size)
	{
		Reservation r1 = new Reservation(name,size);
	}
	
	ReservationList(Reservation r2)
	{
		name = r2.getReservationName();
		size = r2.getReservationSize();
	}
	
	//add a reservation
	public boolean addReservation(Reservation r)
	{
		reservationArray[reservationCounter] = r;
		reservationCounter++;
		return true;
	}
	
	//search for a reservation
	public int searchReservation(String name)
	{
		for(int i=0;i<reservationCounter;i++)
		{
			if(reservationArray[i].getReservationName()==name)
				return i;
		}
		return -1;
	}
	
	//remove a reservation
	public Reservation removeReservation(String name)
	{
		int i = searchReservation(name);
		if(i==-1)
		{
			return null;//the reservation did not exist
		}
		else
		{
			reservationArray[i].setReservationName("null");
			reservationArray[i].setReservationSize(0);
			reservationCounter--;
			return reservationArray[i];
		}
	}
	
	//toString
	public String toSrting()
	{
		String print = " ";
		for(int i=1; i<reservationCounter; i++)
		{
			print += reservationArray[i].toString();
		}
		return "These are the reservations: " + print;
	}

}