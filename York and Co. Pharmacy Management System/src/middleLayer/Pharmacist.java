package middleLayer;

import java.util.ArrayList;

public class Pharmacist extends User {
	
	private Inventory merListByPhar = Inventory.getInstance();
	
	// having only this constructor avoids having an owner without a username and password
	public Pharmacist(int username, int password) {
		this.username = username;
		this.password = password;
	}
	
	//Minh moved 2 methods here from ListOfUsers
	public Pharmacist getPharmacistUser() {
		return this;
	}

	public void setPharmacistUser(Pharmacist pharmacistUser) {
		this.username = pharmacistUser.username;
		this.password = pharmacistUser.password;
	}
	
	public void addPatient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth) {
		// maybe have textboxes for each parameter so users can actually pharmacists can actually type in there???
		Patient newPatient = new Patient(firstName, lastName, address, phoneNum, healthCardNum, dateOfBirth);
		
		ListOfUsers listOfUsersInstance = ListOfUsers.getInstance();
		listOfUsersInstance.addPatientToList(newPatient);
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
