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
	public ArrayList<Merchandise> allInventory = new ArrayList<Merchandise>();
	
	public MerchandiseStub() {
		Merchandise merc1 = new Merchandise(1, "pill1", 10, 5.99, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "");
		Merchandise merc2 = new Merchandise(2, "pill2", 9, 6.99, MERCHANDISE_TYPE.COUGH, MERCHANDISE_FORM.TABLET, true, "description for piil2");
		Merchandise merc3 = new Merchandise(3, "pill3", 8, 7.99, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, false, "description for pill3");
		Merchandise merc4 = new Merchandise(4, "pill4", 7, 8.99, MERCHANDISE_TYPE.SINUS, MERCHANDISE_FORM.LIQUID, false, null);
		allInventory.add(merc1);
		allInventory.add(merc2);
		allInventory.add(merc3);
		allInventory.add(merc4);
	}
	@Override
	public ArrayList<Merchandise> getListOfMerchandise() {
		// TODO Auto-generated method stub
		return allInventory;
	}

	@Override
	public void updateMedicationInDatabase(int medIDOfModifiedMedication, Merchandise actualMedicationObject) {
		// TODO Auto-generated method stub
		for(Merchandise m : allInventory) {
			if(m.getMedicationID() == medIDOfModifiedMedication) {
				m = actualMedicationObject;
				break;
			}
		}

	}

	@Override
	public void deleteMedicationInDatabase(int medIDOfDeletedMedication) {
		// TODO Auto-generated method stub
		for(int i = 0; i < allInventory.size(); i ++) {
			if(allInventory.get(i).getMedicationID() == medIDOfDeletedMedication) {
				allInventory.remove(i);
			}
		}
		
	}

	@Override
	public void addMedicationToDatabase(Merchandise newMedication) {
		// TODO Auto-generated method stub
		allInventory.add(newMedication);

	}

}
