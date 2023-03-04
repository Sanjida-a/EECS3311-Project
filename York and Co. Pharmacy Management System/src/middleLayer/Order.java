package middleLayer;

import databaseDAO.OrderDAO;

public class Order {
	static int orderNumberClassVar = 0; // keeps track of numbering all orders
	
	private Inventory merList = Inventory.getInstance();
	private OrderDAO _orderDao;
	
	int orderNum;

	int medicationID;
	int patientID;
	int quantityBought;
	String isPres;
	int numOfRefills;
	
	public void addOrderToPatient(int _patientID, int _medicationId, int _qty , int _numOfRefills) throws Exception {
		// add to correct patient once we implement patient list 
		// first search patient list w same patientId
		// then add this order to that patient's instance var of orders
		 medicationID = _medicationId;
		 patientID = _patientID;
		 quantityBought = _qty;		
		 numOfRefills = _numOfRefills;	 
		 _orderDao = new OrderDAO();
	}
	
	public void Save() throws Exception {
		
		_orderDao.saveToOrder(patientID, medicationID, quantityBought , numOfRefills);
	}
	
	public void refillOrderPatient(int _patientID, int _medicationId, int _qty) throws Exception {
		 medicationID = _medicationId;
		 patientID = _patientID;
		 quantityBought = _qty;				 
		 _orderDao = new OrderDAO();
	} 
	
	public void refillAdd() throws Exception {
		_orderDao.refillSave(patientID, medicationID, quantityBought);
	}
	
	public Boolean checkEnoughQuantity(Merchandise m, int quantityWantToBuy) {
		int quantityInInventory = m.quantity;
		
		if (quantityWantToBuy > quantityInInventory) {
			return false;
		}
		
		return true;
		
	}
	
	public int calculateRevenue() {
		return 0;
	}
	public String seeSummaryOfSales() {
		return "";
	}
}
