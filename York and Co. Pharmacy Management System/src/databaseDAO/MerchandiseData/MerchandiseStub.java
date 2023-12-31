package databaseDAO.MerchandiseData;

import java.util.ArrayList;

import middleLayer.MerchandiseInventory.*;

public class MerchandiseStub implements MerchandiseRoot {
	public ArrayList<Merchandise> allInventoryStub = new ArrayList<Merchandise>();
	
	public MerchandiseStub() {
		Merchandise merc1 = new Merchandise(1, "pill1", 10, 2.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
		Merchandise merc2 = new Merchandise(2, "pill2", 9, 3.00, MERCHANDISE_TYPE.COUGH, MERCHANDISE_FORM.TABLET, true, "description for piil2", true);
		Merchandise merc3 = new Merchandise(3, "pill3", 8, 4.00, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, false, "description for pill3", true);
		Merchandise merc4 = new Merchandise(4, "pill4", 7, 5.00, MERCHANDISE_TYPE.SINUS, MERCHANDISE_FORM.LIQUID, false, null, true);
		Merchandise merc5 = new Merchandise(5, "pill5", 6, 3.00, MERCHANDISE_TYPE.SINUS, MERCHANDISE_FORM.LIQUID, true, null, false);
		allInventoryStub.add(merc1);
		allInventoryStub.add(merc2);
		allInventoryStub.add(merc3);
		allInventoryStub.add(merc4);
		allInventoryStub.add(merc5);
	}
	
	@Override
	public ArrayList<Merchandise> getListOfMerchandise() {
		ArrayList<Merchandise> result = new ArrayList<Merchandise>();
		for(Merchandise m : allInventoryStub) {
			if(m.isValid()) {
				result.add(m);
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<Merchandise> getListOfValidAndInvalidMerchandise() {
		return allInventoryStub;
	}

	@Override
	public void updateMedicationInDatabase(int medIDOfModifiedMedication, Merchandise actualMedicationObject) {
		for(Merchandise m : allInventoryStub) {
			if(m.getMedicationID() == medIDOfModifiedMedication) {
				allInventoryStub.set(medIDOfModifiedMedication-1, actualMedicationObject);
				break;
			}
		}

	}

	@Override
	public void deleteMedicationInDatabase(int medIDOfDeletedMedication) {
		for(int i = 0; i < allInventoryStub.size(); i ++) {
			if(allInventoryStub.get(i).getMedicationID() == medIDOfDeletedMedication) {
				allInventoryStub.get(i).setIsValid(false);
			}
		}
		
	}

	@Override
	public void addMedicationToDatabase(Merchandise newMedication) {
		allInventoryStub.add(newMedication);
	}
}
