package RAS;
import java.util.Iterator;
import java.util.Scanner;

public class TestMain {

	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {		
		AccountList AccountList = new AccountList();
		Account AccountPlaceHolder = new Account();
		JDBCAccountList JDBCAccountList = new JDBCAccountList();
		JDBC_OrderList jd = new JDBC_OrderList();
		JDBC_ItemList it = new JDBC_ItemList();
		JDBC_Floor fl = new JDBC_Floor();
		JDBC_ReservationList rl = new JDBC_ReservationList();

		//Temporary Login Function
		//Does not divide up menus yet
		System.out.println("            WELCOME TO RAS\n\t     Burger Joint\n555 Imaginary Lane, Imagination, NY 11111\n\nLogin: ");

		boolean LoginLoop = true;
		String LoginPin = "";
		Account Account = null;

		while (LoginLoop){
			Scanner input = new Scanner(System.in); //Prevents infinite loop
			LoginPin = input.next();
			if (JDBCAccountList.PinChecker(LoginPin)){ //Checks if pin is in the system and if it matches
				Account = new Account();
				Account.setPin(LoginPin);
				System.out.println("Login successful.\n");
				LoginLoop = false;
			}
			else{
				System.out.println("Incorrect Pin. Try again.\nLogin: ");
			}
		}
		//End Temporary Login Function

		//IF MANAGER
		if(JDBCAccountList.JDBCgetPosition(LoginPin).equals("Manager"))
		{
			input = new Scanner(System.in);
			int interfaceMenu = 0;
			do{
				//input = new Scanner(System.in);
				System.out.println("Select an option: \n1) Account Management \n2) Table Management \n3) Reservation Management \n4) Order Management \n5) Logout \n6) Quit");
				interfaceMenu = input.nextInt();
				switch(interfaceMenu){
				//Account Management
				case 1:

					int menu=0;
					do{

						System.out.println("Select an option:\n1: Add Employee\n2: Remove Employee\n3: Edit Employee"
								+ "\n4: View Daily Sales \n5: View Manager Report \n6: Go back\n");
						boolean menuCheck = true;
						while(menuCheck){
							try{
								Scanner input = new Scanner(System.in);
								menu = input.nextInt();
								if(menu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									menuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(menu){
						
						case 1: //AddEmployee
							String newName = "";
							String newPin = "";
							String newPosition = "";
							Double newSales = 0.0;

							boolean SalesLoop = true;
							boolean NameLoop = true;
							boolean PositionLoop = true;
							boolean PinLoop = true;

							while (NameLoop){
								System.out.println("Enter a Name: ");
								Scanner input = new Scanner(System.in); //Prevents infinite loop
								if(ValidName(newName = input.next()) ){ //Checks for numbers
									NameLoop = false;
								}
								else{
									System.out.println("No numbers allowed.");
								}
							}

							while (PinLoop){
								System.out.println("Enter a Pin: ");
								Scanner input = new Scanner(System.in); //Prevents infinite loop
								newPin = input.next();
								if (!duplicate(newPin)){ //Checks if it is duplicate
									PinLoop = false;
								}
								else{
									System.out.println("Duplicate Pin. Try again.");
								}
							}

							while (PositionLoop)
							{
								System.out.println("--------\nManager \nServer \nBusboy \nWaiter \nKitchen \nCook \nHost \n--------");
								System.out.println("Enter one of the above position terms: ");
								Scanner input = new Scanner(System.in); //Prevents infinite loop
								//Checks for numbers and if there are matching terms
								if(ValidName(newPosition = input.next()) & (newPosition.equals("Manager")||
										newPosition.equals("Server")||newPosition.equals("Busboy")||
										newPosition.equals("Waiter")|| newPosition.equals("Kitchen")
										||newPosition.equals("Cook")) ||newPosition.equals("Host") ){ 
									PositionLoop = false;
								}
								else{
									System.out.println("Submission must match one of the position terms.");
								}
							}
							//Realistically we don't set sales when creating employee
							//				while(SalesLoop){
							//					try{
							//						SalesLoop = false;
							//						Scanner input = new Scanner(System.in); //Prevents infinite loop
							//						System.out.println("Enter Sales: ");
							//						newSales = input.nextDouble();
							//					}
							//					catch(Exception e){
							//						System.out.println("Invalid submission. Input a number for sales: ");
							//						SalesLoop = true;
							//					}
							//				}

							try{
								AccountList.AddEmployee(newName, newPin, newPosition, 0.0);
								System.out.println("Employee is successfully added.");}
							catch(Exception e){}
							//Add Employee to ArrayList AccountList
							break;


						case 2: //RemoveEmployee
							int ID = -1;
							boolean Loop = true;

							while(Loop){
								try{
									Loop = false;
									Scanner input = new Scanner(System.in); //Prevents infinite loop
									System.out.println("Enter ID of Employee to remove: ");
									ID = input.nextInt();
								}
								catch(Exception e){
									System.out.println("Invalid submission. Input an ID number: ");
									Loop = true;
								}
							}
							try{
								AccountList.RemoveEmployee(ID);
								System.out.println("Employee is removed.");

								//Shows the account to the user one last time using toString.
							}
							catch(Exception e){}
							break;

						case 3: //Edit Employee

							boolean editLoop = true;
							int newID = -999;
							int currentID = -999;
							String currentPin = "";
							String currentName = "";
							String currentPosition = "";
							double currentHours = 0.0;
							double currentSales = 0.0;
							double currentPayRate = 0.0;

							//ID
							do{ //Loop keeps happening if there's an invalid 
								//submission or if there's no existing ID.
								try{
									editLoop = false;
									Scanner input = new Scanner(System.in); //Prevents infinite loop
									System.out.println("Enter a valid existing ID of an employee to be edited: ");
									newID = input.nextInt();
									currentPin = JDBCAccountList.JDBCgetPin(newID);
									currentName = JDBCAccountList.JDBCgetName(Account.getPin());
									currentPosition = JDBCAccountList.JDBCgetPosition(Account.getPin());
								}
								catch(Exception e){
									System.out.println("");
									editLoop = true;
								}
							}
							while(editLoop & !IDduplicate(newID));

							//Collect all current variables
							Iterator<Account> iterator = AccountList.AccountList.iterator();
							while (iterator.hasNext()) {//Goes through the ArrayList
								AccountPlaceHolder.setEqualTo((Account)iterator.next());
								//setEqualTo method will set the AccountPlaceHolder values 
								//to be equal to the iterator ones
								int CheckID = AccountPlaceHolder.getID();
								if(CheckID == newID){
									currentID = newID;
									currentPin = JDBCAccountList.JDBCgetPin(newID);
									currentName = JDBCAccountList.JDBCgetName(Account.getPin());
									currentPosition = JDBCAccountList.JDBCgetPosition(Account.getPin());
									currentHours = AccountPlaceHolder.getHours();
									currentSales = AccountPlaceHolder.getSales();
									currentPayRate = AccountPlaceHolder.getPayRate();
								}
							}

							//Name
							boolean QuestionLoop = true;
							String AnswerQuestion = "";

							while (QuestionLoop){
								System.out.println("Change Name? (Yes or No): ");
								Scanner input = new Scanner(System.in); //Prevents infinite loop
								AnswerQuestion = input.next();
								if(ValidName(AnswerQuestion) & (AnswerQuestion.equals("Yes") || 
										AnswerQuestion.equals("No") || AnswerQuestion.equals("yes")
										|| AnswerQuestion.equals("no"))){ //Checks for numbers and for Yes or No
									QuestionLoop = false;
								}
							}
							if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("yes")){
								QuestionLoop = true;
								while(QuestionLoop){
									System.out.println("Enter new Name: ");
									Scanner input = new Scanner(System.in); //Prevents infinite loop
									if(ValidName(AnswerQuestion = input.next())){		
										QuestionLoop = false;
									}
								}
								currentName = AnswerQuestion;
							}

							//Pin 
							QuestionLoop = true;
							AnswerQuestion = "";

							while(QuestionLoop){
								System.out.println("Change Pin? (Yes or No): ");
								Scanner input = new Scanner(System.in); //Prevents infinite loop
								AnswerQuestion = input.next();
								if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("No") || AnswerQuestion.equals("yes")
										|| AnswerQuestion.equals("no")) {
									QuestionLoop = false;
								}
							}
							if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("yes")){
								QuestionLoop = true;
								while(QuestionLoop){
									System.out.println("Enter new Pin: ");
									Scanner input = new Scanner(System.in); //Prevents infinite loop
									if(!duplicate(AnswerQuestion=input.next())){
										QuestionLoop = false;
									}
								}
								currentPin = AnswerQuestion;
							}

							//Position
							QuestionLoop = true;
							AnswerQuestion = "";

							while(QuestionLoop){
								System.out.println("Change Position? (Yes or No): ");
								Scanner input = new Scanner (System.in); //Prevents infinite loop
								AnswerQuestion = input.next();
								if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("No") || AnswerQuestion.equals("yes")
										|| AnswerQuestion.equals("no")) {
									QuestionLoop = false;
								}					
							}
							if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("yes")){
								QuestionLoop = true;
								while(QuestionLoop){
									System.out.println("Enter new Position: ");
									Scanner input = new Scanner(System.in); //Prevents infinite loop
									AnswerQuestion = input.next();
									if(AnswerQuestion.equals("Manager") || AnswerQuestion.equals("Server") || AnswerQuestion.equals("Waiter")
											||AnswerQuestion.equals("Busboy") ||AnswerQuestion.equals("Kitchen") ||AnswerQuestion.equals("Cook")){
										QuestionLoop = false;
									}
								}
								currentPosition= AnswerQuestion;
							}

							//Hours
							QuestionLoop = true;
							double AnswerQuestionDouble = 0.0;
							AnswerQuestion = "";

							while(QuestionLoop){
								System.out.println("Change Hours? (Yes or No): ");
								Scanner input = new Scanner (System.in); //Prevents infinite loop
								AnswerQuestion = input.next();
								if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("No") || AnswerQuestion.equals("yes")
										|| AnswerQuestion.equals("no")) {
									QuestionLoop = false;
								}	
							}

							if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("yes")){
								QuestionLoop = true;
								while(QuestionLoop){
									System.out.println("Enter new Hours (at least 0.0): ");
									Scanner input = new Scanner (System.in); //Prevents infinite loop
									try{
										AnswerQuestionDouble = input.nextDouble();
										if(AnswerQuestionDouble >= 0.0){
											QuestionLoop = false;
										}
									}
									catch(Exception e){System.out.println("Must be a number.");}
								}
								currentHours = AnswerQuestionDouble;
							}

							//Sales
							QuestionLoop = true;
							AnswerQuestionDouble = 0.0;
							AnswerQuestion = "";

							while(QuestionLoop){
								System.out.println("Change Sales? (Yes or No): ");
								Scanner input = new Scanner (System.in); //Prevents infinite loop
								AnswerQuestion = input.next();
								if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("No") || AnswerQuestion.equals("yes")
										|| AnswerQuestion.equals("no")) {
									QuestionLoop = false;
								}	
							}

							if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("yes")){
								QuestionLoop = true;
								while(QuestionLoop){
									System.out.println("Enter new Sales (at least 0.0): ");
									Scanner input = new Scanner (System.in); //Prevents infinite loop
									try{
										AnswerQuestionDouble = input.nextDouble();
										if(AnswerQuestionDouble >= 0.0){
											QuestionLoop = false;
										}
									}
									catch(Exception e){System.out.println("Must be a number.");}
								}
								currentSales = AnswerQuestionDouble;
							}

							//PayRate
							QuestionLoop = true;
							AnswerQuestionDouble = 0.0;
							AnswerQuestion = "";

							while(QuestionLoop){
								System.out.println("Change PayRate? (Yes or No): ");
								Scanner input = new Scanner (System.in); //Prevents infinite loop
								AnswerQuestion = input.next();
								if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("No") || AnswerQuestion.equals("yes")
										|| AnswerQuestion.equals("no")) {
									QuestionLoop = false;
								}	
							}

							if(AnswerQuestion.equals("Yes") || AnswerQuestion.equals("yes")){
								QuestionLoop = true;
								while(QuestionLoop){
									System.out.println("Enter new PayRate (at least 7.0): ");
									Scanner input = new Scanner (System.in); //Prevents infinite loop
									try{
										AnswerQuestionDouble = input.nextDouble();
										if(AnswerQuestionDouble >= 7.0){
											QuestionLoop = false;
										}
									}
									catch(Exception e){System.out.println("Must be a number.");}
								}
								currentPayRate = AnswerQuestionDouble;
							}

							//Call the AccountList EditEmployee function
							try{AccountList.EditEmployee(newID, currentPin, currentName, currentPosition, currentHours, currentSales, currentPayRate);
							System.out.println("Employee edited successfully.");}
							catch(Exception e){}

							break;


						case 4: //ViewDailySales
							try{
								//It uses the Account object of the user who logged in
								//in order to directly print out the toString from it
								System.out.println(Account.toString());
							}
							catch(Exception e){}

							break;

						case 5: //View Manager Report
							try{
								System.out.println(AccountList.toString());
							}
							catch(Exception e){}
							break;

						case 6: //Go back
							break;

						default: //Wrong Option
							System.out.println("You failed to pick the right option.");
							break;
						}
					}
					while (menu!=6); //So menu!=X where X= case to quit
					//The condition to close menu is for both quit and logout because
					//if it isn't coded to happen during logout, then you would have 
					//to quit multiple times for every time you logged in and out
					break;
				case 2: //case 2 team 2
					int tmenu=0;
					do{

						System.out.println("Select an option:\n1: Add Table\n2: Remove Table\n3: View Floor \n4: SeatTable \n5: ViewDirtyTables \n6: CleanTable"
								+"\n7: PayOrder Testing \n8: Go Back\n");
						boolean tmenuCheck = true;
						while(tmenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								tmenu = input.nextInt();
								if(tmenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									tmenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(tmenu){


						case 1:
							System.out.println("-----------------------------------------------");
							System.out.println("Add Table: Enter Table Number");
							int num1 =input.nextInt();
							System.out.println("Add Table: Enter Table Size");
							int size1 = input.nextInt();
							boolean test1 = fl.addTable(num1,size1);
							if(test1==true)
								System.out.println("Table #"+num1+" successfully added");
							else
								System.out.println("Table #"+num1+" already exists");
							System.out.println("-----------------------------------------------");
							break;
						case 2:
							System.out.println("-----------------------------------------------");
							System.out.println("Remove Table: Enter Table Number");
							int numDel =input.nextInt();
							Table r1 = fl.removeTable(numDel);
							if(r1==null)
								System.out.println("Table #"+numDel+" was not found");
							else
							{
								System.out.println("The following Table was removed:");
								System.out.println(r1.toString());
							}
							System.out.println("-----------------------------------------------");
							break;
						case 3:
							System.out.println("-----------------------------------------------");
							System.out.print(fl.toString());
							System.out.println("-----------------------------------------------");
							break;
						case 4:
							System.out.println("---------------------------------------------");
							System.out.println("SeatTable: Enter Table Number");
							int numSeat = input.nextInt();
							int seatTest = fl.setTableStatus(numSeat, "Full");
							if(seatTest!=0)
								System.out.println("Table #"+numSeat+"'s status was changed to Full");
							else
								System.out.println("ERROR: Table #"+numSeat+" is not Ready");
							System.out.println("---------------------------------------------");
							break;
						case 5:
							System.out.println("---------------------------------------------");
							System.out.print(fl.viewDirtyTables());
							System.out.println("---------------------------------------------");
							break;
						case 6:
							System.out.println("---------------------------------------------");
							System.out.println("CleanTable: Enter Table Number");
							int numClean = input.nextInt();
							int cleanTest = fl.setTableStatus(numClean, "Ready");
							if(cleanTest!=0)
								System.out.println("Table #"+numClean+"'s status was changed to Ready");
							else
								System.out.println("ERROR: Table #"+numClean+" is not Dirty");
							System.out.println("---------------------------------------------");
							break;

						case 7: //REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION |
							System.out.println("---------------------------------------------");
							System.out.println("PayOrder Testing: Enter Table Number");
							int numPaid = input.nextInt();
							int payTest = fl.setTableStatus(numPaid, "Dirty");
							if(payTest!=0)
								System.out.println("Table #"+numPaid+"'s status was changed to Dirty");
							else
								System.out.println("ERROR: Table #"+numPaid+" is not Full");
							System.out.println("---------------------------------------------");
							break;
						case 8:
							break;
						}
					}
					while(tmenu!=8);
					break;
				case 3: //ReservationList
					int rmenu=0;
					do{

						System.out.println("Select an option:\n1: View Reservations\n2: Add Reservation\n3: Remove Reservation \n4: Search Reservation"
								+ "\n5: Go Back\n");
						boolean rmenuCheck = true;
						while(rmenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								rmenu = input.nextInt();
								if(rmenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									rmenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(rmenu){
						case 1:
							System.out.println("Reservations: ");
							System.out.println(rl.viewReservations());
							break;
						case 2:
							System.out.println("Add Reservation: Enter the reservation name");
							String name = input.next();
							System.out.println("Add Reservation: Enter reservation size");
							int size = input.nextInt();
							boolean test = rl.addReservation(name,size);
							if(test == true)
								System.out.print("Your reservation has been added\n");
							else
								System.out.print("Whoops Something Went Wrong!\n");
							break;
						case 3:
							System.out.println("Remove Reservation: Please enter the reservation name");
							String key = input.next();
							Reservation r = rl.removeReservation(key);
							if(r==null)
								System.out.println("There were no reservations found for "+key);
							else
							{
								System.out.println("The following reservation was removed:");
								System.out.println(r.toString());
							}
							break;
						case 4:
							System.out.println("Search Reservation: Please enter the reservation name");
							String nameKey = input.next();
							System.out.println("We've found " + rl.searchReservation(nameKey) + " reservation(s) for " + nameKey);
							break;
						case 5: break;}
					}
					while(rmenu != 5);
					break;
				case 4: //team 3 ORDER
					int omenu=0;
					do{

						System.out.println("Select an option:\n1: View Menu\n2: Send Order\n3: Discount Item \n4: Void Item \n5: View Waiting Orders\n6: Edit Order"
								+ "\n7: Add Item\n8: Delete Item\n9: Serve Ticket\n10: Pay Order\n11: Go Back");
						boolean omenuCheck = true;
						while(omenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								omenu = input.nextInt();
								if(omenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									omenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(omenu){
						
						case 30: Order order = new Order(1, LoginPin, null, 10, 1);
						System.out.println(jd.payOrder(20.0, order)); break;

						case 1:
							System.out.println(it.getItems()); 
							break;

						case 2:
							System.out.println(it.getItems());
							System.out.println("Select 3 items and add to array");
							Item[] items = new Item[3];

							for(int i=0; i<items.length;i++){
								items[i] = it.selectItem(input.nextInt()); 
							}

							System.out.println("Enter Table Number");
							int num = input.nextInt();
							//System.out.println("Enter Account Name");
							//String name = input.next();
							boolean test =jd.SendOrder(num, JDBCAccountList.JDBCgetName(LoginPin), items);
							if(test = true){
								System.out.print("Order Successfully Sent\n");;
							}
							else
								System.out.print("Whoops Something Went Wrong!\n");
							break;

						case 3:
							System.out.println("1. 10%   2. 25%\n"+
									"3. 50%   4. 100%\n\n"
									+ "Select a discount rate");
							int p = input.nextInt();
							if(p==1)
								p=10;
							if(p==2)
								p=25;
							if(p==3)
								p=50;
							if(p==4)
								p=100;
							System.out.println("Select an OrderNum");
							int id=input.nextInt();
							String s = jd.getOrder(id).altString();
							System.out.print(s+"\n");
							Order o=jd.getOrder(id);
							double d =jd.applyDiscount(o, p);
							System.out.println("New Total "+d);
							//System.out.println(o.toString()+"\n");
							break;

						case 4://void Item
							boolean	Question = true;
							String aQuestion = "";
							int order_num = 0;
							int item=0;
							while(Question){
								System.out.println("Would you Like to Void Item? (Yes or No): ");
								Scanner input = new Scanner (System.in); //Prevents infinite loop
								aQuestion = input.next();
								if(aQuestion.equals("Yes") || aQuestion.equals("No")||aQuestion.equals("yes")|| aQuestion.equals("no")
										||aQuestion.equals("YES")|| aQuestion.equals("NO")) {
									Question = false;
								}	
							}

							if(aQuestion.equals("Yes")||aQuestion.equals("yes")||aQuestion.equals("YES")){
								Question = true;

								while(Question){



									Scanner input = new Scanner (System.in); //Prevents infinite loop
									try{
										System.out.println("Enter Order Number\n");
										order_num = input.nextInt();//issue

										System.out.println(" Enter Item ");
										item=input.nextInt();
										Question=false;

									}
									catch(Exception e){System.out.println("Must be a number.");}
								}
								try{
									if(jd.VoidItem(item,order_num)){
										System.out.println("Item Voided");

									}
									else{

										System.out.println("Item Voided");

									}
								}
								catch(Exception e){System.out.println("Must be a number.");}
							}

							break;

						case 5:	//viewWaitng Order
							System.out.println("Waiting Orders");
							System.out.println(jd.veiw_Waiting_Order());
							break;

						case 6:
							System.out.println("Enter the Order Number you would like to add to");
							int id1 = input.nextInt();
							System.out.println("Add the item you would like to add to the order");
							String addedItems = input.next();
							if(jd.EditOrder(id1, addedItems)){
								System.out.println("Item successfully added!\n");
							}
							else{
								System.out.println("Whoops, something went wrong!\n");
							}
							break;

						case 7:
							System.out.println("Enter name for new Item");
							String item_Name = input.next();
							System.out.println("Enter a price for the new Item");
							double item_price = input.nextDouble();
							System.out.println("Enter type for the new Item");
							String item_type = input.next();
							if(it.addItem(item_Name, item_price, item_type)){
								System.out.println("New Item Created!\n");
							}
							else{
								System.out.println("Whoops something went wrong!\n");
							}
							break;

						case 8:
							System.out.println("Enter id of Item you would like to delete");
							int item_id = input.nextInt();
							if(it.deleteItem(item_id)){
								System.out.println("Item successfully deleted\n");
							}
							else{
								System.out.println("Whoops, something went wrong!\n");
							}
							break;

						case 9:
							System.out.println("Enter Order Number you would like to serve");
							int orderid = input.nextInt();
							if(jd.ServeTicket(orderid))
							{
								System.out.println("Order: "+orderid+" was removed");
								System.out.println("Waiting Orders");
								System.out.println(jd.veiw_Waiting_Order());
							}
							else{
								System.out.println("Whoops! Something went wrong!");
							}
						case 10:
							System.out.println("Enter Order Number you would like to collect payment");
							int orderid2 = input.nextInt();
							Order payOrder = jd.getOrder(orderid2);
							//payOrder.toString();
							System.out.println("Enter Payment amount");
							double pay = input.nextDouble();
							//System.out.println("Remaining balance: "+jd.payOrder(pay, payOrder));


						case 11:
							break;

						default: //Wrong Option
							System.out.println("You failed to pick the right option.");
							break;
						}
					}while(omenu!=11);
					break;

				case 5: //Logout
					main(args);
					//Recursion to recall the main class
					break;

				case 6: //Quit 
					System.out.println("RAS is now offline.");
					System.exit(0);
					break;

				}//End do while

			}//Interface do while
			while(interfaceMenu != 5 || interfaceMenu != 6);
		}//END IF MANAGER

		//IF BUSBOY
		if (JDBCAccountList.JDBCgetPosition(LoginPin).equals("Busboy")){
			input = new Scanner(System.in);
			int interfaceMenu = 0;
			do{
				//input = new Scanner(System.in);
				System.out.println("Select an option: \n1) Table Management \n2) Logout \n3) Quit");
				interfaceMenu = input.nextInt();
				switch(interfaceMenu){

				case 1: //Table Management (Busboy)
					int tmenu=0;
					do{

						System.out.println("Select an option:\n1: View Dirty Tables \n2: Clean Table"
								+"\n3: Go Back\n");
						boolean tmenuCheck = true;
						while(tmenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								tmenu = input.nextInt();
								if(tmenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									tmenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(tmenu){


						case 1:
							System.out.println("---------------------------------------------");
							System.out.print(fl.viewDirtyTables());
							System.out.println("---------------------------------------------");
							break;
						case 2:
							System.out.println("---------------------------------------------");
							System.out.println("CleanTable: Enter Table Number");
							int numClean = input.nextInt();
							int cleanTest = fl.setTableStatus(numClean, "Ready");
							if(cleanTest!=0)
								System.out.println("Table #"+numClean+"'s status was changed to Ready");
							else
								System.out.println("ERROR: Table #"+numClean+" is not Dirty");
							System.out.println("---------------------------------------------");
							break;

						case 3:
							break;
						}
					}
					while(tmenu!=3);
					break;


				case 2: //Logout
					main(args);
					//Recursion to recall the main class
					break;

				case 3: //Quit 
					System.out.println("RAS is now offline.");
					System.exit(0);
					break;

				}//End do while

			}//Interface do while
			while(interfaceMenu != 2 || interfaceMenu != 3);
		}
		//END IF BUSBOY

		//IF KITCHEN/COOK
		if(JDBCAccountList.JDBCgetPosition(LoginPin).equals("Cook") || JDBCAccountList.JDBCgetPosition(LoginPin).equals("Kitchen")){
			input = new Scanner(System.in);
			int interfaceMenu = 0;
			do{
				//input = new Scanner(System.in);
				System.out.println("Select an option: \n1) Order Management \n2) Logout \n3) Quit");
				interfaceMenu = input.nextInt();
				switch(interfaceMenu){


				case 1: //team 3 ORDER
					int omenu=0;
					do{

						System.out.println("Select an option:\n1: View Waiting Orders \n2: Serve Ticket \n3: Go Back");
						boolean omenuCheck = true;
						while(omenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								omenu = input.nextInt();
								if(omenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									omenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(omenu){


						case 1:	//viewWaitng Order
							System.out.println("Waiting Orders");
							System.out.println(jd.veiw_Waiting_Order());
							break;



						case 2:
							System.out.println("Enter Order Number you would like to serve");
							int orderid = input.nextInt();
							if(jd.ServeTicket(orderid))
							{
								System.out.println("Order: "+orderid+" was removed");
								System.out.println("Waiting Orders");
								System.out.println(jd.veiw_Waiting_Order());
							}
							else{
								System.out.println("Whoops! Something went wrong!");
							}


						case 3:
							break;

						default: //Wrong Option
							System.out.println("You failed to pick the right option.");
							break;
						}
					}while(omenu!=3);
					break;

				case 2: //Logout
					main(args);
					//Recursion to recall the main class
					break;

				case 3: //Quit 
					System.out.println("RAS is now offline.");
					System.exit(0);
					break;

				}//End do while

			}//Interface do while
			while(interfaceMenu != 2 || interfaceMenu != 3);
		}
		//END IF KITCHEN/COOK

		//IF SERVER
		if(JDBCAccountList.JDBCgetPosition(LoginPin).equals("Server") || JDBCAccountList.JDBCgetPosition(LoginPin).equals("Waiter")){
			input = new Scanner(System.in);
			int interfaceMenu = 0;
			do{
				//input = new Scanner(System.in);
				System.out.println("Select an option: \n1) Account Management \n2) Order Management \n3) Logout \n4) Quit");
				interfaceMenu = input.nextInt();
				switch(interfaceMenu){
				//Account Management
				case 1:

					int menu=0;
					do{

						System.out.println("Select an option:\n1: View Daily Sales \n2: Go back\n");
						boolean menuCheck = true;
						while(menuCheck){
							try{
								Scanner input = new Scanner(System.in);
								menu = input.nextInt();
								if(menu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									menuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(menu){


						case 1: //ViewDailySales
							try{
								//It uses the Account object of the user who logged in
								//in order to directly print out the toString from it
								System.out.println(Account.toString());
							}
							catch(Exception e){}

							break;


						case 2: //Go back
							break;

						default: //Wrong Option
							System.out.println("You failed to pick the right option.");
							break;
						}
					}
					while (menu!=2); //So menu!=X where X= case to quit
					//The condition to close menu is for both quit and logout because
					//if it isn't coded to happen during logout, then you would have 
					//to quit multiple times for every time you logged in and out
					break;
				
				
				case 2: //team 3 ORDER SERVER/WAITER
					int omenu=0;
					do{

						System.out.println("Select an option: \n1: Send Order \n2: View Waiting Orders\n3: Edit Order"
								+ "\n4: Pay Order\n5: Go Back");
						boolean omenuCheck = true;
						while(omenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								omenu = input.nextInt();
								if(omenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									omenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(omenu){

						case 1:
							System.out.println(it.getItems());
							System.out.println("Select 3 items and add to array");
							Item[] items = new Item[3];

							for(int i=0; i<items.length;i++){
								items[i] = it.selectItem(input.nextInt()); 
							}

							System.out.println("Enter Table Number");
							int num = input.nextInt();
							//System.out.println("Enter Account Name");
							//String name = input.next();
							boolean test =jd.SendOrder(num, JDBCAccountList.JDBCgetName(LoginPin), items);
							if(test = true){
								System.out.print("Order Successfully Sent\n");;
							}
							else
								System.out.print("Whoops Something Went Wrong!\n");
							break;

				


						case 2:	//viewWaitng Order
							System.out.println("Waiting Orders");
							System.out.println(jd.veiw_Waiting_Order());
							break;

						case 3:
							System.out.println("Enter the Order Number you would like to add to");
							int id1 = input.nextInt();
							System.out.println("Add the item you would like to add to the order");
							String addedItems = input.next();
							if(jd.EditOrder(id1, addedItems)){
								System.out.println("Item successfully added!\n");
							}
							else{
								System.out.println("Whoops, something went wrong!\n");
							}
							break;


						case 4:
							System.out.println("Enter Order Number you would like to collect payment");
							int orderid2 = input.nextInt();
							Order payOrder = jd.getOrder(orderid2);
							//payOrder.toString();
							System.out.println("Enter Payment amount");
							double pay = input.nextDouble();
							//System.out.println("Remaining balance: "+jd.payOrder(pay, payOrder));


						case 5:
							break;

						default: //Wrong Option
							System.out.println("You failed to pick the right option.");
							break;
						}
					}while(omenu!=5);
					break;

				case 3: //Logout
					main(args);
					//Recursion to recall the main class
					break;

				case 4: //Quit 
					System.out.println("RAS is now offline.");
					System.exit(0);
					break;

				}//End do while

			}//Interface do while
			while(interfaceMenu != 3 || interfaceMenu != 4);
		}
		//END IF SERVER
		
		//IF TABLE
		if(JDBCAccountList.JDBCgetPosition(LoginPin).equals("Host")){
			input = new Scanner(System.in);
			int interfaceMenu = 0;
			do{
				//input = new Scanner(System.in);
				System.out.println("Select an option: \n1) Table Management \n2) Reservation Management \n3) Logout \n4) Quit");
				interfaceMenu = input.nextInt();
				switch(interfaceMenu){
				
				case 1: //case 2 team 2 HOST
					int tmenu=0;
					do{

						System.out.println("Select an option:\n1: Add Table\n2: Remove Table\n3: View Floor \n4: SeatTable \n5: ViewDirtyTables"
								+"\n6: PayOrder Testing \n7: Go Back\n");
						boolean tmenuCheck = true;
						while(tmenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								tmenu = input.nextInt();
								if(tmenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									tmenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(tmenu){


						case 1:
							System.out.println("-----------------------------------------------");
							System.out.println("Add Table: Enter Table Number");
							int num1 =input.nextInt();
							System.out.println("Add Table: Enter Table Size");
							int size1 = input.nextInt();
							boolean test1 = fl.addTable(num1,size1);
							if(test1==true)
								System.out.println("Table #"+num1+" successfully added");
							else
								System.out.println("Table #"+num1+" already exists");
							System.out.println("-----------------------------------------------");
							break;
						case 2:
							System.out.println("-----------------------------------------------");
							System.out.println("Remove Table: Enter Table Number");
							int numDel =input.nextInt();
							Table r1 = fl.removeTable(numDel);
							if(r1==null)
								System.out.println("Table #"+numDel+" was not found");
							else
							{
								System.out.println("The following Table was removed:");
								System.out.println(r1.toString());
							}
							System.out.println("-----------------------------------------------");
							break;
						case 3:
							System.out.println("-----------------------------------------------");
							System.out.print(fl.toString());
							System.out.println("-----------------------------------------------");
							break;
						case 4:
							System.out.println("---------------------------------------------");
							System.out.println("SeatTable: Enter Table Number");
							int numSeat = input.nextInt();
							int seatTest = fl.setTableStatus(numSeat, "Full");
							if(seatTest!=0)
								System.out.println("Table #"+numSeat+"'s status was changed to Full");
							else
								System.out.println("ERROR: Table #"+numSeat+" is not Ready");
							System.out.println("---------------------------------------------");
							break;
						case 5:
							System.out.println("---------------------------------------------");
							System.out.print(fl.viewDirtyTables());
							System.out.println("---------------------------------------------");
							break;


						case 6: //REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION | REMOVE THIS FROM FINAL VERSION |
							System.out.println("---------------------------------------------");
							System.out.println("PayOrder Testing: Enter Table Number");
							int numPaid = input.nextInt();
							int payTest = fl.setTableStatus(numPaid, "Dirty");
							if(payTest!=0)
								System.out.println("Table #"+numPaid+"'s status was changed to Dirty");
							else
								System.out.println("ERROR: Table #"+numPaid+" is not Full");
							System.out.println("---------------------------------------------");
							break;
						case 7:
							break;
						}
					}
					while(tmenu!=7);
					break;
				case 2: //ReservationList
					int rmenu=0;
					do{

						System.out.println("Select an option:\n1: View Reservations\n2: Add Reservation\n3: Remove Reservation \n4: Search Reservation"
								+ "\n5: Go Back\n");
						boolean rmenuCheck = true;
						while(rmenuCheck){
							try{
								Scanner input = new Scanner(System.in);
								rmenu = input.nextInt();
								if(rmenu > 0){
									//The value -1 will be prevented so that
									//use cases not being used can be set to that
									rmenuCheck = false;
								}
								else{System.out.println("Incorrect option, try again: ");}
							}
							catch(Exception e){System.out.println("Incorrect option, try again: ");}
						}
						switch(rmenu){
						case 1:
							System.out.println("Reservations: ");
							System.out.println(rl.viewReservations());
							break;
						case 2:
							System.out.println("Add Reservation: Enter the reservation name");
							String name = input.next();
							System.out.println("Add Reservation: Enter reservation size");
							int size = input.nextInt();
							boolean test = rl.addReservation(name,size);
							if(test == true)
								System.out.print("Your reservation has been added\n");
							else
								System.out.print("Whoops Something Went Wrong!\n");
							break;
						case 3:
							System.out.println("Remove Reservation: Please enter the reservation name");
							String key = input.next();
							Reservation r = rl.removeReservation(key);
							if(r==null)
								System.out.println("There were no reservations found for "+key);
							else
							{
								System.out.println("The following reservation was removed:");
								System.out.println(r.toString());
							}
							break;
						case 4:
							System.out.println("Search Reservation: Please enter the reservation name");
							String nameKey = input.next();
							System.out.println("We've found " + rl.searchReservation(nameKey) + " reservation(s) for " + nameKey);
							break;
						case 5: break;}
					}
					while(rmenu != 5);
					break;
				

				case 3: //Logout
					main(args);
					//Recursion to recall the main class
					break;

				case 4: //Quit 
					System.out.println("RAS is now offline.");
					System.exit(0);
					break;

				}//End do while

			}//Interface do while
			while(interfaceMenu != 3 || interfaceMenu != 4);
		}
		//END IF SERVER

	}//End Main 

	//Checks for numbers in Name inputs
	public static boolean ValidName(String name)
	{
		if (name.matches(".*[0-9].*")){ 

			return false;
		} 
		return true;
	}//End ValidName

	//Duplicate Pin Checker
	public static boolean duplicate(String Pin){
		Iterator iterator = AccountList.AccountList.iterator();
		while(iterator.hasNext()){
			if(((Account) iterator.next()).getPin().equals(Pin)){return true;}
		}
		return false; //Returning false means that there are no duplicates

	}//End duplicateChecker

	//Duplicate ID Checker
	public static boolean IDduplicate(int ID){
		Iterator iterator = AccountList.AccountList.iterator();
		while(iterator.hasNext()){
			if(((Account) iterator.next()).getID() == ID){return true;}
		}
		return false; //Returning false means that there are no duplicates

	}//End IDChecker



}//End Class