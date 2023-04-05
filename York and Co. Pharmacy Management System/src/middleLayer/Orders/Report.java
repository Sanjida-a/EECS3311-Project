package middleLayer.Orders;

import java.util.ArrayList;

import databaseDAO.MerchandiseData.MerchandiseRoot;
import databaseDAO.OrderData.OrderRoot;
import databaseDAO.UserData.UserRoot;
import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.MerchandiseInventory.Merchandise;


public class Report {
	
	private ListOfOrders listOfOrders;
	private ArrayList<Order> allOrders = new ArrayList<>();
	private Inventory inv;
	
	public Report() {
		listOfOrders = ListOfOrders.getInstance();
		allOrders = listOfOrders.getListofAllOrders();
		inv = Inventory.getInstance();
	}
	
	public Report(OrderRoot orderDAO, MerchandiseRoot merDAO, UserRoot userDAO) {
		listOfOrders = ListOfOrders.getInstance(orderDAO, merDAO, userDAO);
		allOrders = listOfOrders.getListofAllOrders();
		inv = Inventory.getInstance(merDAO);
	}
	
	//Calculate revenue for sales report
	public double calculateRevenue (){
		double revenue = 0.0;		
		
		allOrders = listOfOrders.getListofAllOrders();
		
		for ( Order e : allOrders) {
			revenue = revenue + e.getTotalPriceOfOrder(); // sum of all orders' prices
		}
		return revenue;
		
	}
	
	//Calculate profit for sales report
	public double calculateProfit (){
		double revenue = calculateRevenue();
		double profit = revenue * 0.3;		 // profit is 30% of selling price
		return profit;
		
	}
	
	//To show sales summary in sales report
	public ArrayList<String> seeSummaryOfSales() {
		
		allOrders = listOfOrders.getListofAllOrders();
		
	 	ArrayList<String> output = new ArrayList<String>();
	 	for (Order e : allOrders){
	         output.add(e.toString());
	 	}
	 	return output;
	}
	
	//To show sales summary in sales report
	public ArrayList<String> seeMedicationSales() {
		
		allOrders = listOfOrders.getListofAllOrders();
		ArrayList<Merchandise> merList = inv.getValidAndInvalidMerchandise();
		
	 	ArrayList<String> output = new ArrayList<String>();
	 	
	 	int quantitySold = 0;
	 	double priceSold = 0;
	 	for (Merchandise m : merList) {
	 		for (Order o : allOrders) {
	 			if (m.getMedicationID() == o.getMedicationID()) {
	 				quantitySold += o.getQuantityBought();
	 				priceSold += o.getTotalPriceOfOrder();
	 			}
		 	}
	 		
	 		if (quantitySold != 0) { // only want to display orders that actually were sold
	 			output.add("MEDID: " + m.getMedicationID() + " - " + "Sold: " + quantitySold + ", $" + priceSold + "\n");
	 		}
	 		
	 		//reset for next medication
	 		quantitySold = 0;
	 		priceSold = 0;
	 	}
	 	
	 	return output;
	}
	 
	 public void setOrderDAO(OrderRoot orderDAO, MerchandiseRoot mercDAO, UserRoot userDAO) {
		 this.listOfOrders.setOrderDAO(orderDAO, mercDAO, userDAO);
		 this.allOrders = this.listOfOrders.getListofAllOrders();
		 this.inv.set_merDAO(mercDAO);
	 }

}
