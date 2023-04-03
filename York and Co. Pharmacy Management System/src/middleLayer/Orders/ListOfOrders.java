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
			this.merList = Inventory.getInstance(merDAO);
			this.allOrdersList = _orderDAO.getListOfAllOrders();
			this.userList = ListOfUsers.getInstance(userDAO);

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
	
	public static ListOfOrders getInstance(OrderRoot orderDAO, MerchandiseRoot merDAO, UserRoot userDAO) {
        if (ListOfOrdersInstance == null) {
        	//System.out.println("LIstOforders.getInstance is called with two DAOs");
        	ListOfOrdersInstance = new ListOfOrders(orderDAO, merDAO, userDAO);
        }
        return ListOfOrdersInstance;
	}
	
	// dependency injection principle (database vs stub)
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
	
	// adds OTC order to ORDER table in database
	public boolean addOrderToDatabase(Order o) throws Exception { 
		
		Patient pFound = userList.searchPatientWithID(o.patientID);
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
		Merchandise mFound = merList.searchMerchandiseWithID(o.medicationID);
		if (mFound == null) {
			throw new Exception("Medication doesn't exist!");
		}
		
		if (o.quantityBought <= 0) {
			throw new NegativeInputException("Quantity Bought Must Be Positive (at least 1)!");    
		}
	
		if (!mFound.getisOTC()) {
			throw new Exception ("Not an OTC! Use the \"Give Refill for a prescription\" button");
		}
		
		if (mFound.getQuantity() <= 0 || mFound.getQuantity() < o.getQuantityBought()) {
			throw new Exception("Check quantity in stock for medication! Not enough!");
		}
		
		// if reached here: no exception, and can successfully continue finalizing order object by setting total price isPres variable
		o.setTotalPriceOfOrder(mFound.getPrice()*o.quantityBought);
		o.setIsPrescription(!mFound.getisOTC());
		
		// add new row to database Orders table
		_orderDAO.addToOrderTable(o);
		
		boolean lowInStockReminder = merList.decreaseQuantity(mFound.getMedicationID(), o.getQuantityBought()); // decreaseQuantity method returns boolean value for restock reminder
		
		//once database is updated, also updated this class's list variable by reading from database
		this.updateOrderListFromDatabase();
		
		return lowInStockReminder; // returns true of restock reminder is needed for the medication just made an order for
	}
	
	// adds prescription FORM to prescription table in database
	public void addPresFormToDb(Prescription p) throws Exception {

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
		
		int presNumToBeUpd = _orderDAO.checkIfExistsInPrescriptionTable(p.getPatientID(), p.getMedicationID()); // checks if prescription form record already exists (same patID and same medID)
		
		if (presNumToBeUpd != -1) { // if prescription form record already exists, use presNum primary key to UPDATE an EXISTING row in prescription form table
			_orderDAO.updateRefillsInExistingPresFormInDB(presNumToBeUpd, p.getOriginalNumOfRefills());
		}
		else { // otherwise, add NEW row to prescription form table
		_orderDAO.addToPrescriptionTable(p);		
		}
		
		//once database is updated, also updated this class's list variable by reading from database
		this.updateOrderListFromDatabase();
	}
	
	// adds prescription ORDER/Rx refill to ORDER table in database
	public boolean addRefillToDatabase(Order o) throws Exception { // boolean return value returns whether need to output low in stock reminder (because orders invoke decreaseQuantity method)
		
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
		
		int prescriptionExists = _orderDAO.checkIfExistsInPrescriptionTable(o.getPatientID(), o.getMedicationID()); // in order to add refill order, need prescription form with same patID and medID to exist in system first
		
		if (prescriptionExists == -1) { // if prescription form not found, can't fulfill order
			throw new Exception("No record found of prescription under Patient with Health Card Number: " + o.getPatientID() + ". Add prescription form into system first.");
		}
		
		// if no exception: prescription form does exist in system. Now check if enough refills remain to fulfill order
		int refillLeft = _orderDAO.numOfRemainingRefills(o.getPatientID(), o.getMedicationID());
		if (refillLeft <= 0) {
			throw new Exception ("0 refills left!");
		}
		
		if ( o.getQuantityBought() > refillLeft) {
			throw new Exception( "Not enough refills! Only have " + refillLeft +  " refills left!");
		}
		
		if (getMer.getQuantity() <= 0 ||  getMer.getQuantity() < o.getQuantityBought()) {
			throw new Exception("Check quantity in stock for medication! Not enough!");
		}
		
		// if reached here: no exception, and can successfully continue finalizing order object by setting total price isPres variable
		o.setTotalPriceOfOrder(getMer.getPrice()*o.quantityBought);
		o.setIsPrescription(!getMer.getisOTC());
		
		// add new row to database Orders table
		_orderDAO.addRefillToOrderTable(o);
		
		// decreaseQuantity method returns boolean value for restock reminder
		boolean lowInStockReminder = merList.decreaseQuantity(getMer.getMedicationID(), o.getQuantityBought());
		
		//once database is updated, also updated this class's list variable by reading from database
		this.updateOrderListFromDatabase();
		
		return lowInStockReminder; // returns true of restock reminder is needed for the medication just made an order for
	}
	
	// returns arrayList of all orders (both OTC and Rx refills) made by a specific patient with health card # = healthCardID
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
			if (o.getPatientID() == healthCardID) { // since health card numbers are unique for each patient, using that to distinguish each order
				specificPatientOrderList.add(o);
			}
		}
		
		return specificPatientOrderList;
	}
	
	// returns all orders, including their associated medication details, for a specific patient with health card # = healthCardID
	// userType defines who invoked the method since tiny string formatting details should change accordingly
	public ArrayList<String> outputOrderHistoryDetails(long healthCardID, USER userType) throws Exception {
		
		// invokes method above to simplify this method
		ArrayList<Order> ordersOfPatient = this.specificPatientOrderHistory(healthCardID);
		
		// do not want to print straight up data from database; need to format using strings so that it is readable and makes sense to users
		ArrayList<String> orderHistoryDetails = new ArrayList<String>();
		
		if (ordersOfPatient.isEmpty()) {
			if (userType == USER.OWNER || userType == USER.PHARMACIST) { // based on if admin, need to specify health card number they requested for
				orderHistoryDetails.add("Patient with health card number " + healthCardID + " has not made any orders.");
			}
			else { // otherwise if patient him/herself requested their own orders, need to use refer to them as "You"
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
			
			// finding associated medication for that order so can also retrieve those the medication details for output
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
	
	// returns arrayList of all prescription FORMS in system of a specific patient with health card # = healthCardID
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
		
		getListofAllPres();
		ArrayList<Prescription> specificPatientPres = new ArrayList<Prescription>();
	
		for (Prescription p : allPresList) {
			if (p.getPatientID() == healthCardID) {  // since health card numbers are unique for each patient, using that to distinguish each prescription
				specificPatientPres.add(p);
			}
		}
		
		return specificPatientPres;
	}
	
	// returns all prescription forms, including their associated medication details, for a specific patient with health card # = healthCardID
	// userType defines who invoked the method since tiny string formatting details should change accordingly
	public ArrayList<String> outputPresRefill(long healthCardID, USER userType) throws Exception {
		
		// invokes method above to simplify this method
		ArrayList<Prescription> presOfPatient = specificPatientPres(healthCardID);
		
		// do not want to print straight up data from database; need to format using strings so that it is readable and makes sense to users
		ArrayList<String> refillsDetails = new ArrayList<String>();
		
		if (presOfPatient.isEmpty()) {
			if (userType == USER.OWNER || userType == USER.PHARMACIST) { // based on if admin, need to specify health card number they requested for
				refillsDetails.add("Patient with health card number " + healthCardID + " does not have any prescription forms in the system.");
			}
			else { // otherwise if patient him/herself requested their own orders, need to use refer to them as "You"
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
			oneFullOrder += "Medication ID: " + p.getMedicationID() + "\n" + "Number of Refills Left: " + _orderDAO.numOfRemainingRefills(healthCardID, p.getMedicationID()); 
			
			// finding associated medication for that order so can also retrieve those the medication details for output
			associatedMedication = merList.searchAllValidAndInvalidMerchandiseWithID(p.getMedicationID());
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			refillsDetails.add(oneFullOrder);
			presNum++;
		}
		
		return refillsDetails;
	}
	
	// returns total money spent at pharmacy (wrt orders made) for a specific patient with health card # = healthCardID
	public double specificPatientMoneySpent(long healthCardID) throws Exception {
		
		// invokes previously defined method to simplify this method
		ArrayList<Order> ordersOfPatient = this.specificPatientOrderHistory(healthCardID);
		
		double totalMoneySpentByPatient = 0;
		for (Order o : ordersOfPatient) {
			totalMoneySpentByPatient += o.getTotalPriceOfOrder();
		}
		
		return totalMoneySpentByPatient;
	}
	
}