package middleLayer;

import java.util.ArrayList;

public class Report {

	private ArrayList<Order> orderList = new ArrayList<>();
	
	public double calculateRevenue () throws Exception {
		double revenue = 0.0;		
		ListOfOrders orders = ListOfOrders.getInstance();
		orderList = orders.getListofAllOrders();
		for ( Order e : orderList) {
			revenue = revenue + e.getPriceAtPurchase();
		}
		return revenue;
		
	}
	
	public double calculateProfit () throws Exception {
		double revenue = calculateRevenue();
		double profit = revenue * 0.3;		
		return profit;
		
	}
	
	 public String seeSummaryOfSales() {
		ListOfOrders orders = ListOfOrders.getInstance();
		orderList = orders.getListofAllOrders();
	 	String output = "";
	 	for (Order e : orderList){

             output = String.format("Order number: %d Medication ID: %d Quantity bought: %d Total price: %f\n", 
            		 e.getOrderNum(),e.getMedicationID(),e.getQuantityBought(),  e.getPriceAtPurchase() );
	 }
	 	return output;
	 }
	
	

}
