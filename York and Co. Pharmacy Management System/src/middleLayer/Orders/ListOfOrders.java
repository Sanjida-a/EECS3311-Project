package middleLayer.Orders;

import java.util.ArrayList;

import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseRoot;
import databaseDAO.OrderData.OrderDAO;
import databaseDAO.OrderData.OrderRoot;
import databaseDAO.UserData.UserRoot;
import middleLayer.NegativeInputException;
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
	private ArrayList<Prescription> allPresList;
	
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
	
	public ArrayList<Prescription> getListofAllPres() {
		try {
			allPresList = _orderDAO.getListOfAllPres(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allPresList;
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
	
	// returns whether need to output low in stock reminder
	public boolean addOrderToDatabase(Order o) throws Exception {
		
		Patient pFound = userList.searchPatientWithID(o.patientID);
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
		Merchandise mFound = merList.searchMerchandiseWithID(o.medicationID);
		if (mFound == null) {
			throw new Exception("Medication doesn't exist!");
		}
		
		//Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
		if (o.quantityBought <= 0) {
			throw new NegativeInputException("Quantity Bought Must Be Positive (at least 1)!");    
		}
	
		if (!mFound.getisOTC()) {
			throw new Exception ("Not an OTC! Use the \"Give Refill for a prescription\" button");
		}
		
		if (mFound.getQuantity() <= 0 || mFound.getQuantity() < o.getQuantityBought()) {
			throw new Exception("Check quantity in stock for medication! Not enough!");
		}
		
		o.setTotalPriceOfOrder(mFound.getPrice()*o.quantityBought);
		o.setIsPrescription(!mFound.getisOTC());

		_orderDAO.addToOrderTable(o);
		
	//	if (o.getIsPrescription() == true) {
	//		_orderDAO.addToPrescriptionTable(p);
	//	}
		
		boolean lowInStockReminder = merList.decreaseQuantity(mFound.getMedicationID(), o.getQuantityBought());
		this.updateOrderListFromDatabase();
		
		return lowInStockReminder;
	}
	
	// adds prescription FORM to prescription table in database
	public void addPresFormToDb(Prescription p) throws Exception {
//		if(p.getOriginalNumOfRefills() == 0){
//			throw new Exception("Please enter refill numbers!");
//		}
//		else{
		Patient pFound = userList.searchPatientWithID(p.patientID);
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
		Merchandise getMer = merList.searchMerchandiseWithID(p.getMedicationID());
		if (getMer == null) {
			throw new Exception("Medication doesn't exist!");
		}
		
		if (getMer.getisOTC()) {
			throw new Exception("Not an Rx! You can only add prescription forms for Rx Medications");
		}
		
		if (p.getOriginalNumOfRefills() <= 0) {
			throw new Exception ("Refills must be positive (at least 1)!");
		}
//		}
		
		
		int presNumToBeUpd = _orderDAO.checkIfExistsInPrescriptionTable(p.getPatientID(), p.getMedicationID());
		
		if (presNumToBeUpd != -1) {
			int refillsLeft = _orderDAO.numOfRefill(p.getPatientID(), p.getMedicationID());
			int newRefills = p.originalNumOfRefills + refillsLeft;
			_orderDAO.updatePresDB(presNumToBeUpd, newRefills);
		}
		else {
		_orderDAO.addToPrescriptionTable(p);		
		}
		
		this.updateOrderListFromDatabase();
	}
	
	// adds prescription ORDER/Rx refill to ORDER table in database
	// returns whether need to output low in stock reminder
	public boolean addRefillToDatabase(Order o) throws Exception {
		
		Patient pFound = userList.searchPatientWithID(o.patientID);
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
		Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
		if (getMer == null) {
			throw new Exception("Medication doesn't exist!");
		}
		
		if (o.quantityBought <= 0) {
			throw new NegativeInputException("Quantity Bought Must Be Positive (at least 1)!");    
		}
		
		if (getMer.getisOTC()) {
			throw new Exception ("Not an Rx! Use the \"Add OTC Order\" button");
		}
		
//		Boolean prescriptionExists = _orderDAO.checkIfExistsInPrescriptionTable(o.getPatientID(), o.getMedicationID());
		int prescriptionExists = _orderDAO.checkIfExistsInPrescriptionTable(o.getPatientID(), o.getMedicationID());
		
//		if (prescriptionExists == false) {
		if (prescriptionExists == -1) {
			throw new Exception("No record found of prescription under Patient with Health Card Number: " + o.getPatientID() + ". Add prescription form into system first.");
		}
		
		// if no exception, prescription does exist in system. Now check if enough refills
		int refillLeft = _orderDAO.numOfRefill(o.getPatientID(), o.getMedicationID());
		if (refillLeft <= 0) {
			throw new Exception ("0 refills left!");
		}
		if ( o.getQuantityBought() > refillLeft) {
			throw new Exception( "Not enough refills! Only have " + refillLeft +  " refills left!");
		}
		
//		Merchandise getMer = merList.searchMerchandiseWithID(o.getMedicationID());
		if (getMer.getQuantity() <= 0 ||  getMer.getQuantity() < o.getQuantityBought()) {
			throw new Exception("Check quantity in stock for medication! Not enough!");
		}
		
		o.setTotalPriceOfOrder(getMer.getPrice()*o.quantityBought);
		o.setIsPrescription(!getMer.getisOTC());
		
		_orderDAO.addRefillToOrderTable(o);
		
		boolean lowInStockReminder = merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
		this.updateOrderListFromDatabase();
		
		return lowInStockReminder;
	}
	
	// aiza added for easy access
	public ArrayList<Order> specificPatientOrderHistory(long healthCardID) throws Exception {
		
		if (healthCardID < 0) {
			throw new NegativeInputException("Please enter a positive health card number");
		}
		
		if (!((1000000000 <= healthCardID) && (healthCardID <= 9999999999L))) {
			throw new Exception("Please enter a valid 10-digit health card number");
		}
		
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
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\nOTCorRx: " + OTCorRx + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			orderHistoryDetails.add(oneFullOrder);
			orderNum++;
		}
		
		return orderHistoryDetails;
	}
	
	public ArrayList<Prescription> specificPatientPres(long healthCardID) throws Exception {
		
		if (healthCardID < 0) {
			throw new NegativeInputException("Please enter a positive health card number");
		}
		
		if (!((1000000000 <= healthCardID) && (healthCardID <= 9999999999L))) {
			throw new Exception("Please enter a valid 10-digit health card number");
		}
		
		Patient pFound = userList.searchPatientWithID(healthCardID);
		
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
	//	this.updateOrderListFromDatabase();	
		getListofAllPres();
		ArrayList<Prescription> specificPatientPres = new ArrayList<Prescription>();
	
		for (Prescription p : allPresList) {
			if (p.getPatientID() == healthCardID) {
				specificPatientPres.add(p);
			}
		}
		
		return specificPatientPres;
		
	}
	
	public ArrayList<String> outputPresRefill(long healthCardID, USER userType) throws Exception {
		
		ArrayList<Prescription> presOfPatient = specificPatientPres(healthCardID);
		
		ArrayList<String> refillsDetails = new ArrayList<String>();
		
		if (presOfPatient.isEmpty()) {
			if (userType == USER.OWNER || userType == USER.PHARMACIST) {
				refillsDetails.add("Patient with health card number " + healthCardID + " does not have any prescription forms in the system.");
			}
			else {
				refillsDetails.add("You have not entered any prescription forms into the system.");
			}
			return refillsDetails;
		}
		
		if (userType == USER.OWNER || userType == USER.PHARMACIST) {
			refillsDetails.add("PRESCRIPTION FORM HISTORY OF PATIENT WITH HEALTH CARD NUMBER " + healthCardID + "\n\n");
		}
		else {
			refillsDetails.add("YOUR PRESCRIPTION FORMS:\n\n");
		}
		
		int presNum = 1;
		Merchandise associatedMedication = null;
		for (Prescription p : presOfPatient) {
			String oneFullOrder = "";
			oneFullOrder += "PRESCRIPTION FORM #" + presNum + "\n";
			oneFullOrder += "Medication ID: " + p.getMedicationID() + "\n" + "Number of Refills Left: " + _orderDAO.numOfRefill(healthCardID, p.getMedicationID()); 
			associatedMedication = merList.searchAllValidAndInvalidMerchandiseWithID(p.getMedicationID());
			
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			refillsDetails.add(oneFullOrder);
			presNum++;
		}
		
		return refillsDetails;
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