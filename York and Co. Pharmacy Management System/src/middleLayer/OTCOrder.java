package middleLayer;

public class OTCOrder extends Order{
	
	private OTCOrder() {
		orderNumberClassVar++;
		
		this.orderNum = orderNumberClassVar;
	}
	
	public boolean checkIfOrderPossible(int medicationID, int patientID, int quantityBought) {
		Inventory inv = Inventory.getInstance();
		Merchandise m = inv.searchMerchandiseWithID(medicationID); //locate merchandise
		// check if patient exists using database?
		
		boolean returnValue = false;
		
		if (m == null) {
			// order unsuccessful
		}
		else {
			boolean enoughQuantity = super.checkEnoughQuantity(m, quantityBought);
			
			if (enoughQuantity == false) {
				// order unsuccessful
			}
			else {
				returnValue = true;
				OTCOrder newOrder = new OTCOrder();
				
				newOrder.medicationID = medicationID;
				newOrder.patientID = patientID;
				newOrder.quantityBought = quantityBought;
	//			newOrder.priceAtPurchase = m.price; // will give price at purchase time
				
				//this.addOrderToPatient(patientBought);
			}
		}

		return returnValue;
	}
}
