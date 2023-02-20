package middleLayer;

public class OTCOrder extends Order{
	
	public OTCOrder(int medicationID, int patientID, int quantityBought) {
		orderNumberClassVar++;
		
		this.orderNum = orderNumberClassVar;
		
		Inventory inv = new Inventory();
		Merchandise m = inv.searchMerchandiseWithID(medicationID); //locate merchandise
		
		this.medicationID = medicationID;
		this.patientID = patientID;
		this.quantityBought = quantityBought;
		this.priceAtPurchase = m.price; // will give price at purchase time
		
		//this.addOrderToPatient(patientBought);
	}
}
