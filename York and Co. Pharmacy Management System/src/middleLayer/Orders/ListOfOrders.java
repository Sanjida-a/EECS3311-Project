package middleLayer.Orders;

import java.util.ArrayList;

import databaseDAO.OrderData.OrderDAO;
import databaseDAO.OrderData.OrderRoot;
import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.MerchandiseInventory.Merchandise;

public class ListOfOrders {
	private Inventory merList = Inventory.getInstance();
	private static ListOfOrders ListOfOrdersInstance = null;
	private ArrayList<Order> allOrdersList;
	//private ArrayList<User> allUsersList = new ArrayList<User>();
	
	private OrderRoot _orderDAO; // Dependency Injection Principle
	
	private ListOfOrders() { //constructor of all singleton classes should be private
		try {
			_orderDAO = new OrderDAO();
			allOrdersList = _orderDAO.getListOfAllOrders();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// all singleton classes must implement this method
	public static ListOfOrders getInstance(){
        if (ListOfOrdersInstance == null) {
        	ListOfOrdersInstance = new ListOfOrders();
        }
        return ListOfOrdersInstance;
    }
	
	public void setOrderDAO(OrderRoot dao) {
		this._orderDAO = dao;
		try {
			allOrdersList = _orderDAO.getListOfAllOrders();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// keeps allOrdersList updated by reading from database
	public void updateOrderListFromDatabase() { // always try to update at the beginning and end of each method
		try {
			allOrdersList = _orderDAO.getListOfAllOrders(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// getter method
	public ArrayList<Order> getListofAllOrders() {
		updateOrderListFromDatabase(); //updates from database first before returning
		return allOrdersList;
	}
	
//	public void addOrderToPatient(int _patientID, int _medicationId, int _qty , int _numOfRefills) throws Exception {
//		// add to correct patient once we implement patient list 
//		// first search patient list w same patientId
//		// then add this order to that patient's instance var of orders
//		 medicationID = _medicationId;
//		 patientID = _patientID;
//		 quantityBought = _qty;		
//		 numOfRefills = _numOfRefills;	 
//		 _orderDao = new OrderDAO();
//		 _merDao = new MerchandiseDAO();
//	}
	
	public void addOrderToDatabase(Order o, Prescription p) throws Exception {
		
		Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
		
		if (getMer.getQuantity() <= 0 || getMer.getisValid() == false || getMer.getQuantity() < o.getQuantityBought()) {
			throw new Exception("Check inventory!");
		}
		_orderDAO.addToOrderTable(o);
		
		if (o.getIsPrescription() == true) {
//			Prescription p = new Prescription(o.getMedicationID(), o.getPatientID(), )
			_orderDAO.addToPrescriptionTable(p);
		}
		
//		Merchandise foundMedication = merList.searchMerchandiseWithID(medicationID); // can't be null bc successfully returns from saveToOrder()
//		int newQuant = getMer.getQuantity() - o.getQuantityBought();
//		getMer.setQuantity(newQuant);
//		_merDao.updateQuantPurchase(medicationID, quantityBought);
		
		merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
//		_merDao.updateMedicationInDatabase(medicationID, foundMedication);
		this.updateOrderListFromDatabase();
	}
	
//	public void refillOrderPatient(int _patientID, int _medicationId, int _qty) throws Exception {
//		 medicationID = _medicationId;
//		 patientID = _patientID;
//		 quantityBought = _qty;				 
//		 _orderDao = new OrderDAO();
//		 _merDao = new MerchandiseDAO();
//	} 
	
	public void addRefillToDatabase(Order o) throws Exception {
		
		Boolean prescriptionExists = _orderDAO.checkIfExistsInPrescriptionTable(o.getPatientID(), o.getMedicationID());
		
		if (prescriptionExists == false) {
			throw new Exception("No record found of prescription!");
		}
		
		Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
		if (getMer.getQuantity() <= 0 || getMer.getisValid() == false || getMer.getQuantity() < o.getQuantityBought()) {
			throw new Exception("Check inventory!");
		}
		
		int refillLeft = _orderDAO.numOfRefill(o.getPatientID(), o.getMedicationID());
		if ( o.getQuantityBought() > refillLeft) {
			throw new Exception( "Only have " + refillLeft +  "refills left!");
		}
		
		_orderDAO.addRefillToOrderTable(o);
//		_merDao.updateQuantPurchase(medicationID, quantityBought);
		merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
		this.updateOrderListFromDatabase();
	}
	
//	public Boolean checkEnoughQuantity(Merchandise m, int quantityWantToBuy) {
//		int quantityInInventory = m.quantity;
//		
//		if (quantityWantToBuy > quantityInInventory) {
//			return false;
//		}
//		
//		return true;
//		
//	}


//	public double calculateRevenue() { //calls calculateSellingPrice() to calculate total revenue
//		ArrayList<Order> copyofList = new ArrayList<Order>(allOrdersList);
//		double total = 0.00;
//		for(int i = 0; i <copyofList.size(); i++){
//			double sellingPrice = copyofList.get(i).calculateSellingPrice();
//			total += copyofList.get(i).quantityBought * sellingPrice ;
//		}
//		return total;
		
//	}

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