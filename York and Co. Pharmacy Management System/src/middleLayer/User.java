package middleLayer;

import java.util.ArrayList;

import presentation.USER;

public abstract class User {
	
	public int username;
	public int password;
	
	public void setUsername(int username) {
		this.username = username;
	}
	
	public void setPassword(int password) {
		this.password = password;
	}
	
	public int getUsername() {
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
	
	// all users implement these same two methods as their parameters have been modified so that the list of medications they have access to can be sent accordingly
	public  ArrayList<Merchandise> searchMedicineByName (String name, USER user) {
		
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
			if (i.getName().compareTo(name) == 0) {
				searchMedResult.add(i);
			}
		}
		
		return searchMedResult;
	}
	
	// abstract method to be implemented by Owner, Pharmacist and Patient (not implemented here because implementation for patient is different)
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
