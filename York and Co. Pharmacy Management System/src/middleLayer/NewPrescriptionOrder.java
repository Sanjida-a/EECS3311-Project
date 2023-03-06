package middleLayer;

public class NewPrescriptionOrder extends Order{
	int numOfRefillsFromDoctor;
	int numOfRefillsRemanining;
	
	// separate database holding refills 
	public NewPrescriptionOrder(int medicationID, int patientID, int quantityBought, int numOfRefillsFromDoctor) {
		orderNumberClassVar++;
		
		this.orderNum = orderNumberClassVar;
		
		Inventory inv = Inventory.getInstance();
		Merchandise m = inv.searchMerchandiseWithID(medicationID); //locate merchandise
		
		this.medicationID = medicationID;
		this.patientID = patientID;
		this.quantityBought = quantityBought;
//		this.priceAtPurchase = m.price; // will give price at purchase time
		
		//this.addOrderToPatient(patientBought);
		this.numOfRefillsFromDoctor = numOfRefillsFromDoctor;
		this.numOfRefillsRemanining = numOfRefillsFromDoctor - quantityBought;
	}
	
	public void refillPrescriptionOrder(int merchandiseID, Patient patientBought, int quantityBought) {
		// display list of orders by a patient
		// get order id
		// pharm just has to ender orderID and quantityBOught
	}
}

//
//Aiza Bajwa, Pill1, bought 1, refillstotal: 10
//refills remaining = 9

//Aiza Bajwa, Pill1, bought 1, numrefillsremaining = 8

// _____________   ___________ __________
