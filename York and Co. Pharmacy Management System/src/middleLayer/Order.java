package middleLayer;

import databaseDAO.MerchandiseDAO;
import databaseDAO.OrderDAO;

public class Order {
	static int orderNumberClassVar = 0; // keeps track of numbering all orders
	
	private Inventory merList = Inventory.getInstance();
	private OrderDAO _orderDao;
	private MerchandiseDAO _merDao;
	
	int orderNum;

	int medicationID;
	int patientID;
	int quantityBought;
	// String isPres;
	int numOfRefills;
	double priceAtPurchase;

	public Order(){
		super();
	}

	public Order(int orderNum,int medicationID, int patientID, int quantityBought, double priceAtPurchase ){
		this.orderNum = orderNum;
		this.medicationID = medicationID;
		this.patientID = patientID;
		this.quantityBought = quantityBought;
		this.priceAtPurchase = priceAtPurchase;
	}

	public void addOrderToPatient(int _patientID, int _medicationId, int _qty , int _numOfRefills) throws Exception {
		// add to correct patient once we implement patient list 
		// first search patient list w same patientId
		// then add this order to that patient's instance var of orders
		 medicationID = _medicationId;
		 patientID = _patientID;
		 quantityBought = _qty;		
		 numOfRefills = _numOfRefills;	 
		 _orderDao = new OrderDAO();
		 _merDao = new MerchandiseDAO();
	}
	
	public void Save() throws Exception {
		
		_orderDao.saveToOrder(patientID, medicationID, quantityBought , numOfRefills);
		_merDao.updateQuantPurchase(medicationID, quantityBought);
		
	}
	
	public void refillOrderPatient(int _patientID, int _medicationId, int _qty) throws Exception {
		 medicationID = _medicationId;
		 patientID = _patientID;
		 quantityBought = _qty;				 
		 _orderDao = new OrderDAO();
		 _merDao = new MerchandiseDAO();
	} 
	
	public void refillAdd() throws Exception {
		_orderDao.refillSave(patientID, medicationID, quantityBought);
		_merDao.updateQuantPurchase(medicationID, quantityBought);
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

	public int getQuantityBought() {
		return quantityBought;
	}

	public double getPriceAtPurchase() {
		return priceAtPurchase;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public int getMedicationID() {
		return medicationID;
	}


	
}

