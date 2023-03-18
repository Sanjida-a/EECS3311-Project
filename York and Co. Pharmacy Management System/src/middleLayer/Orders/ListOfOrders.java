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
	
//	public void addOrderToDatabase(Order o, Prescription p) throws Exception {
//		
//		Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
//		
//		if (getMer.getQuantity() <= 0 || getMer.getisValid() == false || getMer.getQuantity() < o.getQuantityBought()) {
//			throw new Exception("Check inventory!");
//		}
//		_orderDAO.addToOrderTable(o);
//		
//		if (o.getIsPrescription() == true) {
//			_orderDAO.addToPrescriptionTable(p);
//		}
//		
//		merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
//		this.updateOrderListFromDatabase();
//	}
	
	public void addOrderToDatabase(Order o) throws Exception {
	
	Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
	
	if (!getMer.getisOTC()) {
		throw new Exception ("Not an OTC!");
	}
	
	if (getMer.getQuantity() <= 0 || getMer.getisValid() == false || getMer.getQuantity() < o.getQuantityBought()) {
		throw new Exception("Check inventory!");
	}
	_orderDAO.addToOrderTable(o);
	
//	if (o.getIsPrescription() == true) {
//		_orderDAO.addToPrescriptionTable(p);
//	}
	
	merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
	this.updateOrderListFromDatabase();
}
	public void addPresOrderToDb(Prescription p) throws Exception {
		
	Merchandise getMer = merList.searchMerchandiseWithID(p.getMedicationID());
	
	if (getMer.getisOTC()) {
		throw new Exception("Not an Rx!");
	}
	_orderDAO.addToPrescriptionTable(p);
	
//	if (o.getIsPrescription() == true) {
//		_orderDAO.addToPrescriptionTable(p);
//	}
	
	this.updateOrderListFromDatabase();
}
	public void addPresQuantToDb(Order o) throws Exception {
		
	Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
	
	if (getMer.getisOTC()) {
		throw new Exception("Not an Rx!");
	}
	_orderDAO.addToOrderTable(o);
	
//	if (o.getIsPrescription() == true) {
//		_orderDAO.addToPrescriptionTable(p);
//	}
	
	this.updateOrderListFromDatabase();
}

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
		if (refillLeft <= 0) {
			throw new Exception ("No refills left!");
		}
		if ( o.getQuantityBought() > refillLeft) {
			throw new Exception( "Only have " + refillLeft +  " refills left!");
		}
		
		_orderDAO.addRefillToOrderTable(o);
		merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
		this.updateOrderListFromDatabase();
	}

}