package middleLayer.Orders;

import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.MerchandiseInventory.Merchandise;
import middleLayer.Users.ListOfUsers;
import middleLayer.Users.Patient;

public class Order { //ALL ORDER: both OTC and Prescription
	private Inventory merList = Inventory.getInstance();
	private ListOfUsers userList = ListOfUsers.getInstance();
	
	int orderNum;
	int medicationID;
	long patientID;
	int quantityBought;
	double totalPriceOfOrder;
	boolean isPrescription;

	// constructor for front end
	public Order(int medicationID, int patientID, int quantityBought) throws Exception{
		
		this.medicationID = medicationID;
		this.patientID = patientID;
		
		if (quantityBought < 0) {
			throw new Exception("Quantity Bought Must Be Non-Negative!");    
		}
		this.quantityBought = quantityBought;
		
		Merchandise mFound = merList.searchMerchandiseWithID(medicationID);
		if (mFound == null) {
			throw new Exception("Medication doesn't exist!");
		}
		
		Patient pFound = userList.searchPatientWithID(patientID);
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
		this.totalPriceOfOrder = mFound.getPrice()*quantityBought;
		this.isPrescription = !mFound.getisOTC();
	}
	
	
	// constructor for reading from database
	public Order(int orderNum,int medicationID, long patientID2, int quantityBought, double totalPriceOfOrder, boolean isPrescription){
		this.orderNum = orderNum;
		this.medicationID = medicationID;
		this.patientID = patientID2;
		this.quantityBought = quantityBought;
		this.totalPriceOfOrder = totalPriceOfOrder;
		this.isPrescription = isPrescription;
	}
	
	public Order(Order o){
	 	this.orderNum = o.orderNum;
		this.medicationID = o.medicationID;
		this.patientID = o.patientID;
		this.quantityBought = o.quantityBought;
		this.totalPriceOfOrder = o.totalPriceOfOrder;
		this.isPrescription = o.isPrescription;
 	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public boolean getIsPrescription() {
		return isPrescription;
	}

	public void setIsPrescription(boolean isPrescription) {
		this.isPrescription = isPrescription;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public void setMedicationID(int medicationID) {
		this.medicationID = medicationID;
	}

	public void setQuantityBought(int quantityBought) {
		this.quantityBought = quantityBought;
	}

	public void setTotalPriceOfOrder(double totalPriceOfOrder) {
		this.totalPriceOfOrder = totalPriceOfOrder;
	}

	public int getQuantityBought() {
		return quantityBought;
	}

	public double getTotalPriceOfOrder() {
		return totalPriceOfOrder;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public int getMedicationID() {
		return medicationID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(	this.orderNum == ((Order)obj).orderNum && 
		this.medicationID == ((Order)obj).medicationID &&
		this.patientID == ((Order)obj).patientID &&
		this.quantityBought == ((Order)obj).quantityBought &&
		this.totalPriceOfOrder == ((Order)obj).totalPriceOfOrder &&
		this.isPrescription == ((Order)obj).isPrescription) {
			return true;
		}
		return false;

	}
	
	@Override
	public String toString() {
		return String.format("Order number: %d Medication ID: %d Quantity bought: %d Total price: %.2f\n",
            		 this.getOrderNum(),this.getMedicationID(),this.getQuantityBought(),  this.getTotalPriceOfOrder() );
		
	}



	
}

