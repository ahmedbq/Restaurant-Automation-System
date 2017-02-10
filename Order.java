package RAS;
import org.apache.commons.lang3.ArrayUtils;



public class Order {
	
	public int tableNum;
	public String UserName;
	public Item[] itemList;
	public int OrderNum;
	public double total;
	
	public Order(int tableNum,  String UserName, Item[] itemList, int OrderNum, double total){
		this.tableNum = tableNum;
		this.UserName = UserName;
		this.itemList = itemList;
		this.OrderNum = OrderNum;
		this.total = total;
	}

	public int getTableNum() {
		return tableNum;
	}

	public void setTableNum(int tableNum) {
		this.tableNum = tableNum;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public Item[] getItemList() {
		return itemList;
	}

	public void setItemList(Item[] itemList) {
		this.itemList = itemList;
	}

	public int getOrderNum() {
		return OrderNum;
	}

	public void setOrderNum(int orderNum) {
		OrderNum = orderNum;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	

	public static Item[] parseId(String str){
		
		JDBC_ItemList il = new JDBC_ItemList();
		Item[] parsedItems = new Item[50];
		for(int i=0;i<str.length();i++){
			//ids[i]=Character.getNumericValue(str.charAt(i));  access the character using .charAt()
			parsedItems[i] = il.selectItem(Character.getNumericValue(str.charAt(i)));
		}
		return parsedItems;
	}
	
	public Item[] deleteItem(Order order,int itemID){
		Item[] list = order.getItemList();
		for(int i=0;i<list.length;i++){
			if(list[i].getId()==itemID){
				list = ArrayUtils.remove(list, i);
		}
		
	}
		return list;
	}
	
	public String toString(){
		String displayItems ="";
		for(int i=0;i<itemList.length;i++){
			displayItems += itemList[i].getItemName()+" ";
		}
		String orderToString = "Order: "+OrderNum+"\n"+"Table No: "+tableNum +"\n"+ "User: "+UserName +"\n"+"Total: "+ total+ "\n";
			//	+ "Items: "+ displayItems;
			
		
		return orderToString;
	}
	
	public String altString(){
		String orderToString = "Order: "+OrderNum+"\n"+"Table No: "+tableNum +"\n"+ "User: "+UserName +"\n"+"Total: "+ total;
			
		return orderToString;
		
	}
}
