package databaseDAO;

import java.util.ArrayList;

import middleLayer.MERCHANDISE_FORM;
import middleLayer.MERCHANDISE_TYPE;
import middleLayer.Merchandise;
/*
 * Merchandise(int medicationID, String name, int quantity, double price, MERCHANDISE_TYPE type, MERCHANDISE_FORM form, boolean isOTC, String description
 * 
 */
public class MerchandiseStub implements MerchandiseRoot {
	public ArrayList<Merchandise> allInventoryStub = new ArrayList<Merchandise>();
	
	public MerchandiseStub() {
		Merchandise merc1 = new Merchandise(1, "pill1", 10, 2.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
		Merchandise merc2 = new Merchandise(2, "pill2", 9, 3.00, MERCHANDISE_TYPE.COUGH, MERCHANDISE_FORM.TABLET, true, "description for piil2", true);
		Merchandise merc3 = new Merchandise(3, "pill3", 8, 4.00, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, false, "description for pill3", true);
		Merchandise merc4 = new Merchandise(4, "pill4", 7, 5.00, MERCHANDISE_TYPE.SINUS, MERCHANDISE_FORM.LIQUID, false, null, true);
		allInventoryStub.add(merc1);
		allInventoryStub.add(merc2);
		allInventoryStub.add(merc3);
		allInventoryStub.add(merc4);
	}
	@Override
	public ArrayList<Merchandise> getListOfMerchandise() {
		// TODO Auto-generated method stub
		return allInventoryStub;
	}

	@Override
	public void updateMedicationInDatabase(int medIDOfModifiedMedication, Merchandise actualMedicationObject) {
		// TODO Auto-generated method stub
		for(Merchandise m : allInventoryStub) {
			if(m.getMedicationID() == medIDOfModifiedMedication) {
				m = actualMedicationObject;
				break;
			}
		}

	}

	@Override
	public void deleteMedicationInDatabase(int medIDOfDeletedMedication) {
		// TODO Auto-generated method stub
		for(int i = 0; i < allInventoryStub.size(); i ++) {
			if(allInventoryStub.get(i).getMedicationID() == medIDOfDeletedMedication) {
				allInventoryStub.get(i).setIsValid(false);
			}
		}
		
	}

	@Override
	public void addMedicationToDatabase(Merchandise newMedication) {
		// TODO Auto-generated method stub
		allInventoryStub.add(newMedication);

	}
	@Override
	public void updateQuantPurchase(int merID, int quantBought) {
		// TODO Auto-generated method stub
		for(int i = 0; i < allInventoryStub.size(); i ++) {
			if(allInventoryStub.get(i).getMedicationID() == merID) {
				int newQuant = allInventoryStub.get(i).getQuantity() - quantBought;
				allInventoryStub.get(i).setQuantity(newQuant);
			}
		}
	}

}
