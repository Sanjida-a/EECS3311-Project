package middleLayer.Orders;

import java.util.ArrayList;

import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseRoot;
import databaseDAO.OrderData.OrderDAO;
import databaseDAO.OrderData.OrderRoot;
import databaseDAO.UserData.UserRoot;
import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.MerchandiseInventory.Merchandise;
import middleLayer.Users.AuthenticateUser;
import middleLayer.Users.ListOfUsers;
import middleLayer.Users.Patient;
import presentation.USER;

public class ListOfOrders {
	private Inventory merList = null;
	private static ListOfOrders ListOfOrdersInstance = null;
	private ArrayList<Order> allOrdersList;
	private ListOfUsers userList;
	
	private OrderRoot _orderDAO; // Dependency Injection Principle
	
	private ListOfOrders() { //constructor of all singleton classes should be private
		try {
			this._orderDAO = new OrderDAO();
			this.allOrdersList = _orderDAO.getListOfAllOrders();
			this.merList = Inventory.getInstance();
			this.userList = ListOfUsers.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//this is a part of dependency injection for unit tests
	private ListOfOrders(OrderRoot orderDAO, MerchandiseRoot merDAO, UserRoot userDAO) {
		_orderDAO = orderDAO;
		try {
			//System.out.println("LIstOfOrders constructor called with two DAOs");
			this.merList = Inventory.getInstance(merDAO);
			this.allOrdersList = _orderDAO.getListOfAllOrders();
			this.userList = ListOfUsers.getInstance(userDAO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	public static ListOfOrders getInstance(OrderRoot orderDAO, MerchandiseRoot merDAO, UserRoot userDAO) {
        if (ListOfOrdersInstance == null) {
        	//System.out.println("LIstOforders.getInstance is called with two DAOs");
        	ListOfOrdersInstance = new ListOfOrders(orderDAO, merDAO, userDAO);
        }
        return ListOfOrdersInstance;
	}
	
	public void setOrderDAO(OrderRoot orderDAO, MerchandiseRoot mercDAO, UserRoot userDAO ) {
		this._orderDAO = orderDAO;
		this.merList.set_merDAO(mercDAO);
		this.userList.set_userDAO(userDAO);
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
	
		//Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
		if (o.quantityBought <= 0) {
			throw new Exception("Quantity Bought Must Be Positive!");    
		}
	
		Merchandise mFound = merList.searchMerchandiseWithID(o.medicationID);
		
		if (mFound == null) {
			throw new Exception("Medication doesn't exist!");
		}
		o.setTotalPriceOfOrder(mFound.getPrice()*o.quantityBought);
		o.setIsPrescription(!mFound.getisOTC());
		Patient pFound = userList.searchPatientWithID(o.patientID);
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		if (!mFound.getisOTC()) {
			throw new Exception ("Not an OTC!");
		}
		
		if (mFound.getQuantity() <= 0 || mFound.getisValid() == false || mFound.getQuantity() < o.getQuantityBought()) {
			//System.out.println(mFound);
			throw new Exception("Check inventory!");
		}
		_orderDAO.addToOrderTable(o);
		
	//	if (o.getIsPrescription() == true) {
	//		_orderDAO.addToPrescriptionTable(p);
	//	}
		
		merList.decreaseQuantity(mFound.getMedicationID(), o.getQuantityBought());
		this.updateOrderListFromDatabase();
	}
	public void addPresOrderToDb(Prescription p) throws Exception {
//		if(p.getOriginalNumOfRefills() == 0){
//			throw new Exception("Please enter refill numbers!");
//		}
//		else{
			if (p.getOriginalNumOfRefills() <= 0) {
				throw new Exception ("Refills must be positive");
			}
//		}
		
		
		Merchandise getMer = merList.searchMerchandiseWithID(p.getMedicationID());
		if (getMer == null) {
			throw new Exception("Medication doesn't exist!");
		}
		if (getMer.getisOTC()) {
			throw new Exception("Not an Rx!");
		}
		Patient pFound = userList.searchPatientWithID(p.patientID);
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
	
		
		
		_orderDAO.addToPrescriptionTable(p);
		
	//	if (o.getIsPrescription() == true) {
	//		_orderDAO.addToPrescriptionTable(p);
	//	}
		
		this.updateOrderListFromDatabase();
	}
//	public void addPresQuantToDb(Order o) throws Exception {
//		
//	Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
//	
//	if (getMer.getisOTC()) {
//		throw new Exception("Not an Rx!");
//	}
//	_orderDAO.addToOrderTable(o);
//	
////	if (o.getIsPrescription() == true) {
////		_orderDAO.addToPrescriptionTable(p);
////	}
//	
//	this.updateOrderListFromDatabase();
//}

	public void addRefillToDatabase(Order o) throws Exception {
		
		Boolean prescriptionExists = _orderDAO.checkIfExistsInPrescriptionTable(o.getPatientID(), o.getMedicationID());
		
		if (prescriptionExists == false) {
			throw new Exception("No record found of prescription!");
		}
		if (o.quantityBought <= 0) {
			throw new Exception("Quantity Bought Must Be Positive!");    
		}
		

		
		int refillLeft = _orderDAO.numOfRefill(o.getPatientID(), o.getMedicationID());
		if (refillLeft <= 0) {
			throw new Exception ("No refills left!");
		}
		if ( o.getQuantityBought() > refillLeft) {
			throw new Exception( "Only have " + refillLeft +  " refills left!");
		}
		Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
		if (getMer.getQuantity() <= 0 || getMer.getisValid() == false || getMer.getQuantity() < o.getQuantityBought()) {
			throw new Exception("Check inventory!");
		}
		o.setTotalPriceOfOrder(getMer.getPrice()*o.quantityBought);
		
		_orderDAO.addRefillToOrderTable(o);
		merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
		this.updateOrderListFromDatabase();

	}
	
	// aiza added for easy access
	public ArrayList<Order> specificPatientOrderHistory(long healthCardID) throws Exception {
		
		Patient pFound = userList.searchPatientWithID(healthCardID);
		
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
		this.updateOrderListFromDatabase();	
		
		ArrayList<Order> specificPatientOrderList = new ArrayList<Order>();
	
		for (Order o : allOrdersList) {
			if (o.getPatientID() == healthCardID) {
				specificPatientOrderList.add(o);
			}
		}
		
		return specificPatientOrderList;
		
	}
	
	// aiza added below method for Itr3 detailed story, userType defines if Owner/Pharmacist OR patient itself is calling this method (follows OCP)
	public ArrayList<String> outputOrderHistoryDetails(long healthCardID, USER userType) throws Exception {
		
		ArrayList<Order> ordersOfPatient = this.specificPatientOrderHistory(healthCardID);
		
		ArrayList<String> orderHistoryDetails = new ArrayList<String>();
		
		if (ordersOfPatient.isEmpty()) {
			if (userType == USER.OWNER || userType == USER.PHARMACIST) {
				orderHistoryDetails.add("Patient with health card number " + healthCardID + " has not made any orders.");
			}
			else {
				orderHistoryDetails.add("You have not made any orders.");
			}
			return orderHistoryDetails;
		}
		
		if (userType == USER.OWNER || userType == USER.PHARMACIST) {
			orderHistoryDetails.add("ORDER HISTORY OF PATIENT WITH HEALTH CARD NUMBER " + healthCardID + "\n\n");
		}
		else {
			orderHistoryDetails.add("YOUR ORDERS:\n\n");
		}
		
		int orderNum = 1;
		Merchandise associatedMedication = null;
		for (Order o : ordersOfPatient) {
			String oneFullOrder = "";
			oneFullOrder += "ORDER #" + orderNum + "\n";
			oneFullOrder += "Medication ID: " + o.getMedicationID() + "\nQuantity bought: " + o.getQuantityBought() + "\nTotal price: " + o.getTotalPriceOfOrder(); 
			associatedMedication = merList.searchAllValidAndInvalidMerchandiseWithID(o.getMedicationID());
			String OTCorRx = "Rx";
			if (associatedMedication.getisOTC() == true) {
				OTCorRx = "OTC";
			}
			oneFullOrder += "\n\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\nOTCorRx:" + OTCorRx + "\n\n";
			
			oneFullOrder += "\n";
			orderHistoryDetails.add(oneFullOrder);
			orderNum++;
		}
		
		return orderHistoryDetails;
	}
	
	public double specificPatientMoneySpent(long healthCardID) throws Exception {
		
		ArrayList<Order> ordersOfPatient = this.specificPatientOrderHistory(healthCardID);
		
		double totalMoneySpentByPatient = 0;
		for (Order o : ordersOfPatient) {
			totalMoneySpentByPatient += o.getTotalPriceOfOrder();
		}
		
		return totalMoneySpentByPatient;
	}
	
}