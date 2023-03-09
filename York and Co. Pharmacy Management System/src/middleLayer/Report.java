package middleLayer;

import java.util.ArrayList;
import databaseDAO.OrderRoot;


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
	
	 public ArrayList<String> seeSummaryOfSales() {
		
		allOrders = listOfOrders.getListofAllOrders();
		
	 	ArrayList<String> output = new ArrayList<String>();
	 	for (Order e : allOrders){
             output.add(e.toString());
	 	}
	 	return output;
	 }
	 
	 public void setOrderDAO(OrderRoot dao) {
		 this.listOfOrders.setOrderDAO(dao);
		 this.allOrders = this.listOfOrders.getListofAllOrders();
	 }
	

	 
	

}
