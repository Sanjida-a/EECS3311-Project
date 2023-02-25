package middleLayer;

import databaseDAO.OrderDAO;

public class Order {
	static int orderNumberClassVar = 0; // keeps track of numbering all orders
	
	private Inventory merList = Inventory.getInstance();
	private OrderDAO _orderDao;
	
	int orderNum;
//	Merchandise itemBrought;
//	Patient patientBought;
	int medicationID;
	int patientID;
	int quantityBought;
	double priceAtPurchase;
	
	public void addOrderToPatient(int _patientID, int _medicationId, int _qty, double _price) throws Exception {
		// add to correct patient once we implement patient list 
		// first search patient list w same patientId
		// then add this order to that patient's instance var of orders
		 medicationID = _medicationId;
		 patientID = _patientID;
		 quantityBought = _qty;
		 priceAtPurchase = _price;
		 
		 
		 _orderDao = new OrderDAO();
	}
	
	public void Save() {
		
		_orderDao.saveToOrder(patientID, medicationID, quantityBought, priceAtPurchase);
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
