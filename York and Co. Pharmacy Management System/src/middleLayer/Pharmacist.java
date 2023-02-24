package middleLayer;

import java.util.ArrayList;

import databaseDAO.UserDAO;
import presentation.DisplayLogin;

public class Pharmacist extends User {
	
	private UserDAO _userDAO;
	private Inventory merListByPhar = Inventory.getInstance();
	
	// having only this constructor avoids having an owner without a username and password
	public Pharmacist(int username, int password) {
		try {
			_userDAO = new UserDAO();
			this.username = username;
			this.password = password;
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		
	}
	
	
	public Pharmacist getPharmacistUser() {
		return this;
	}

	public void setPharmacistUser(Pharmacist pharmacistUser) {
		this.username = pharmacistUser.username;
		this.password = pharmacistUser.password;
	}
	
	public void addPatient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth) {
		
		Patient newPatient = new Patient(firstName, lastName, address, phoneNum, healthCardNum, dateOfBirth);
		try {
			_userDAO.addPatient(newPatient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// implementation of inherited abstract method from User superclass
	public ArrayList<Merchandise> searchOTCMedicineByName (String name) {
		
		ArrayList<Merchandise> searchMedNamePhar = new ArrayList <Merchandise> ();
		
		for (Merchandise i : merListByPhar.getMerchandise()) {
			if (i.getName().compareTo(name) == 0) {
				searchMedNamePhar.add(i);
			}
		}
		
		return searchMedNamePhar;
	}
	
	// implementation of inherited abstract method from User superclass
	public ArrayList<Merchandise> searchOTCMedicineByType (MERCHANDISE_TYPE type) {
		
		ArrayList<Merchandise> searchMedTypePhar = new ArrayList <Merchandise> ();
		
		for (Merchandise i : merListByPhar.getMerchandise()) {
			if (i.getType() == type) {
				searchMedTypePhar.add(i);
			}
		}
		
		return searchMedTypePhar;
	}
	

	
}
