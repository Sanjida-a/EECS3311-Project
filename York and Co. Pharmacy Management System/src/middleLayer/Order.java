package middleLayer;

public class Order {
	static int orderNumberClassVar = 0; // keeps track of numbering all orders
	
	int orderNum;
//	Merchandise itemBrought;
//	Patient patientBought;
	int medicationID;
	int patientID;
	int quantityBought;
	double priceAtPurchase;
	
	public void addOrderToPatient(int patientID) {
		// add to correct patient once we implement patient list 
		// first search patient list w same patientId
		// then add this order to that patient's instance var of orders
	}
	
	public int calculateRevenue() {
		return 0;
	}
	public String seeSummaryOfSales() {
		return "";
	}
}
