package databaseDAO;

import java.util.ArrayList;

import middleLayer.Merchandise;

public interface MerchandiseRoot {
	public ArrayList<Merchandise> getListOfMerchandise() ;
	public void updateMedicationInDatabase(int medIDOfModifiedMedication, Merchandise actualMedicationObject);
	public void deleteMedicationInDatabase(int medIDOfDeletedMedication);
	public void addMedicationToDatabase(Merchandise newMedication);
	public void updateQuantPurchase(int merID, int quantBought);
}
