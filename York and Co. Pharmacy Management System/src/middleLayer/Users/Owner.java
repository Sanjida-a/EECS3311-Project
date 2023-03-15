package middleLayer.Users;

import java.util.ArrayList;

public class Owner extends User {
	
//	private Inventory merListByOwner = Inventory.getInstance(); 
	private ListOfPatients listOfPatientsByOwner = ListOfPatients.getInstance();
	
	// having only this constructor avoids having an owner without a username and password
	public Owner(long username, int password) {
		this.username = username;
		this.password = password;
	}
	
	// Minh moved 2 methods here from ListOfUsers.java class
	public Owner getOwnerUser() {
		return this;
	}

	public void setOwnerUser(Owner ownerUser) {
		this.username = ownerUser.username;
		this.password = ownerUser.password;
	}
	
	// two below methods have the implementation of the below two methods to decrease repeated code
	// implementation of inherited abstract method from User superclass
//	public ArrayList<Merchandise> searchMedicineByName (String name) {
//		
//		ArrayList<Merchandise> searchMedNameOwner = new ArrayList <Merchandise> ();
//		
//		for (Merchandise i : merListByOwner.getMerchandise()) {
//			if (i.getName().compareTo(name) == 0) {
//				searchMedNameOwner.add(i);
//			}
//		}
//		
//		return searchMedNameOwner;
//	}
//	
//	// implementation of inherited abstract method from User superclass
//	public ArrayList<Merchandise> searchMedicineByType (MERCHANDISE_TYPE type) {
//		
//		ArrayList<Merchandise> searchMedTypeOwner = new ArrayList <Merchandise> ();
//		
//		for (Merchandise i : merListByOwner.getMerchandise()) {
//			if (i.getType() == type) {
//				searchMedTypeOwner.add(i);
//			}
//		}
//		
//		return searchMedTypeOwner;
//	}
	
	// OCP followed to allow search by any type of name (first, last, or both) in 1 method by using an extra parameter to record typeOfSearch
	// searches and returns list of patients in system that have same entered name
	public ArrayList<Patient> searchPatientByName (String patientName, String typeOfSearch) {
		
		ArrayList<Patient> searchResult = new ArrayList <Patient> ();
		
		if (typeOfSearch.equals("FirstName")) {

			for (Patient p : listOfPatientsByOwner.getAllPatientsList()) {
				if (p.getFirstName().equalsIgnoreCase(patientName)) { //changed from to uppercase to ignore case (compare SMITH with Smith doesn't work)
					searchResult.add(p);
				}
			}
		}
		else if (typeOfSearch.equals("LastName")) {
			for (Patient p : listOfPatientsByOwner.getAllPatientsList()) {
				if (p.getLastName().equalsIgnoreCase(patientName)) {
					searchResult.add(p);
				}
			}
		}
		else { //typeOfSearch contains full name (first + last name)
			int indexOfSpace = patientName.indexOf(' ');
			String firstName = patientName.substring(0, indexOfSpace);
			String lastName = patientName.substring(indexOfSpace+1);
			
			for (Patient p : listOfPatientsByOwner.getAllPatientsList()) {
				if (p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)) {
					searchResult.add(p);
				}
			}
		}
		return searchResult;
	}
	
}
