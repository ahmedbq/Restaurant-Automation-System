package RAS;

public class Item {
	
	private int ItemId;
	private String ItemName;
	private String ItemType;
	private double ItemPrice;
	
	

	public Item(int ItemId, String ItemName, String ItemType, double ItemPrice){
		this.ItemId = ItemId;
		this.ItemName = ItemName;
		this.ItemType = ItemType;
		this.ItemPrice = ItemPrice;
	}
	
	public int getId(){
		return ItemId;
	}
	
	public void setgetId(int ItemId){
		this.ItemId = ItemId;
	}
	
	public String getItemName(){
		return ItemName;
	}
	
	public void setItemName(String ItemName){
		this.ItemName = ItemName;
	}
	
	public String ItemType(){
		return ItemType;
	}
	
	public void setItemType(String ItemType){
		this.ItemType = ItemType;
	}
	
	public double getItemPrice(){
		return ItemPrice;
	}
	
	public void setItemPrice(double ItemPrice){
		this.ItemPrice = ItemPrice;
	}
	
	public String toString(){
		String itemToString = ItemId + " " + ItemName +" " + ItemPrice;
		
		return itemToString;
	}
	

}
