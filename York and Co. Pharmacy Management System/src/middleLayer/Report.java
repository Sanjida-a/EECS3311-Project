package middleLayer;

import java.util.ArrayList;

public class Report {
	
	private ListOfOrders listOfOrders;
	private ArrayList<Order> allOrders = new ArrayList<>();
	
	public Report() {
		listOfOrders = ListOfOrders.getInstance();
		allOrders = listOfOrders.getListofAllOrders();
	}
	
	public double calculateRevenue () throws Exception {
		double revenue = 0.0;		
		
		allOrders = listOfOrders.getListofAllOrders();
		
		for ( Order e : allOrders) {
			revenue = revenue + e.getTotalPriceOfOrder();
		}
		return revenue;
		
	}
	
	public double calculateProfit () throws Exception {
		double revenue = calculateRevenue();
		double profit = revenue * 0.3;		
		return profit;
		
	}
	
	 public String seeSummaryOfSales() {
		
		allOrders = listOfOrders.getListofAllOrders();
		
	 	String output = "";
	 	for (Order e : allOrders){

             output = String.format("Order number: %d Medication ID: %d Quantity bought: %d Total price: %.2f\n", //added .2, without it 5.00 becomes 5.0000000
            		 e.getOrderNum(),e.getMedicationID(),e.getQuantityBought(),  e.getTotalPriceOfOrder() );
	 }
	 	return output;
	 }
	

	 
	

}
