package middleLayer.Users;

import java.util.ArrayList;

import middleLayer.MerchandiseInventory.*;
import presentation.USER;

public abstract class User {
	
	public long username;
	public int password;
	
	public void setUsername(long username) {
		this.username = username;
	}
	
	public void setPassword(int password) {
		this.password = password;
	}
	
	public long getUsername() {
		return this.username;
	}
	
	public int getPassword() {
		return this.password;
	}
	
//	// abstract method to be implemented by Owner, Pharmacist and Patient (not implemented here because implementation for patient is different)
//	public abstract ArrayList<Merchandise> searchMedicineByName (String name);
//	
//	// abstract method to be implemented by Owner, Pharmacist and Patient (not implemented here because implementation for patient is different)
//	public abstract ArrayList<Merchandise> searchMedicineByType (MERCHANDISE_TYPE type);
	
	// REFACTORING #1: CODE SMELL - DUPLICATION: two below methods are the reimplementations of the two methods that have been commented out above to decrease duplicate code in Owner, Pharmacist and Patient classes
	// REFACTORING #1: had to add parameter of type User since all users implement these same two methods but have different access to the list of medications
	public  ArrayList<Merchandise> searchMedicineByName (String name, USER user) {
		
		Inventory inv = Inventory.getInstance(); 
		ArrayList<Merchandise> listToSearchFrom;
		
		if (user == USER.OWNER || user == USER.PHARMACIST) {
			 listToSearchFrom = inv.getMerchandise();
		}
		else { // patients/guests only have access to OTC medicine
			 listToSearchFrom = inv.getOnlyOTCMerchandise();
		}
				 
		ArrayList<Merchandise> searchMedResult = new ArrayList <Merchandise> ();
		
		for (Merchandise i : listToSearchFrom) {
			if (i.getName().compareTo(name) == 0) {
				searchMedResult.add(i);
			}
		}
		
		return searchMedResult;
	}
	
	public  ArrayList<Merchandise> searchMedicineByType (MERCHANDISE_TYPE type, USER user) {
		
		Inventory inv = Inventory.getInstance(); 
		ArrayList<Merchandise> listToSearchFrom;
		
		if (user == USER.OWNER || user == USER.PHARMACIST) {
			 listToSearchFrom = inv.getMerchandise();
		}
		else {
			 listToSearchFrom = inv.getOnlyOTCMerchandise();
		}
		
		ArrayList<Merchandise> searchMedResult = new ArrayList <Merchandise> ();
		
		for (Merchandise i : listToSearchFrom) {
			if (i.getType() == type) {
				searchMedResult.add(i);
			}
		}
		
		return searchMedResult;
	}
	
}
