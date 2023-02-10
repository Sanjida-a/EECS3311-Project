package middleLayer;

public class Order {
	int orderNumber;
	Merchandise itemBrought;
	Patient patientBought;
	int quantity;
	double price;
	
	public int calculateRevenue() {
		return 0;
	}
	public String seeSummaryOfSales() {
		return "";
	}
}
