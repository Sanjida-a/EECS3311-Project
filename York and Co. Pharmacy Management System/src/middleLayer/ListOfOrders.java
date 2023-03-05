package middleLayer;

import java.util.ArrayList;

import databaseDAO.OrderDAO;
import middleLayer.Order;

public class ListOfOrders {
	
	private static ListOfOrders ListOfOrdersInstance = null;
	private ArrayList<Order> allOrdersList;
	//private ArrayList<User> allUsersList = new ArrayList<User>();
	private OrderDAO OrderDAO;
	
	public ListOfOrders() { //constructor of all singleton classes should be private
		try {
			OrderDAO = new OrderDAO();
			allOrdersList = OrderDAO.getListOfAllOrders();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}
	
	public static ListOfOrders getInstance(){
        if (ListOfOrdersInstance == null) {
        	ListOfOrdersInstance = new ListOfOrders();
        }
        return ListOfOrdersInstance;
    }

	public void updateOrderListFromDatabase() { // always try to update at the beginning and end of each method
		try {
			allOrdersList = OrderDAO.getListOfAllOrders(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Order> getListofAllOrders() {
		updateOrderListFromDatabase(); //updates from database first
		return allOrdersList;
	}


	public double calculateRevenue() { //calls calculateSellingPrice() to calculate total revenue
		ArrayList<Order> copyofList = new ArrayList<Order>(allOrdersList);
		double total = 0.00;
		for(int i = 0; i <copyofList.size(); i++){
			double sellingPrice = copyofList.get(i).calculateSellingPrice();
			total += copyofList.get(i).quantityBought * sellingPrice ;
		}
		return total;
		
	}

	// public int calculateProfit(){
	// 	ArrayList<Order> copyofList2 = new ArrayList<Order>(allOrdersList);
	// 	int cost = 0;
	// 	for(int i = 0; i <copyofList2.size(); i++){
	// 		cost += copyofList2.get(i).quantityBought * copyofList2.get(i).priceAtPurchase;
	// 	}
	// }

	// public int calculateRevenuePerMedicine() {
	// 	ArrayList<Order> copyofList = new ArrayList<Order>(allOrdersList);
	// 	int total = 0;
	// 	for(int i = 0; i <copyofList.size(); i++){
	// 		if(copyofList.get(i).medicationID != copyofList.get(i+1).medicationID){
	// 			total += copyofList.get(i).quantityBought * copyofList.get(i).priceAtPurchase;
	// 		}
	// 	}
	// 	return total;
	// }

	// public String seeSummaryOfSales() {
	// 	ArrayList<Order> copyofList = new ArrayList<Order>(allOrdersList);
	// 	String output = "";
	// 	// int revenuePerMedication;
	// 	for (int i = 0; i < copyofList.size(); i ++){
	// 		output += "OrderNum: " + copyofList.get(i).orderNum+", ";
    //         output += "MedicationID: " + copyofList.get(i).medicationID+", ";
    //         output += "Total: " + (copyofList.get(i).quantityBought * copyofList.get(i).priceAtPurchase) + " \n";
	// 	return output;
	// }
	// }

}