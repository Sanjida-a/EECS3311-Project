package middleLayer;

import java.sql.SQLException;
import java.util.ArrayList;

import databaseDAO.UserDAO;
import presentation.DisplayLogin;

public class Pharmacist extends User {
	
	private UserDAO _userDAO;
//	private Inventory merListByPhar = Inventory.getInstance();
	private ListOfPatients listOfPatientsByPhar = ListOfPatients.getInstance();
	
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
	
	public void addPatient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth) throws Exception {
		
		Patient newPatient = new Patient(firstName, lastName, address, phoneNum, healthCardNum, dateOfBirth);
		try {
			_userDAO.addPatientToDatabase(newPatient);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	// implementation of inherited abstract method from User superclass
//	public ArrayList<Merchandise> searchMedicineByName (String name) {
//		
//		ArrayList<Merchandise> searchMedNamePhar = new ArrayList <Merchandise> ();
//		
//		for (Merchandise i : merListByPhar.getMerchandise()) {
//			if (i.getName().compareTo(name) == 0) {
//				searchMedNamePhar.add(i);
//			}
//		}
//		
//		return searchMedNamePhar;
//	}
//	
	// implementation of inherited abstract method from User superclass
//	public ArrayList<Merchandise> searchMedicineByType (MERCHANDISE_TYPE type) {
//		
//		ArrayList<Merchandise> searchMedTypePhar = new ArrayList <Merchandise> ();
//		
//		for (Merchandise i : merListByPhar.getMerchandise()) {
//			if (i.getType() == type) {
//				searchMedTypePhar.add(i);
//			}
//		}
//		
//		return searchMedTypePhar;
//	}
//	
	public ArrayList<Patient> searchPatientByName (String patientName, String typeOfSearch) {
		
		ArrayList<Patient> searchResult = new ArrayList <Patient> ();
		
		if (typeOfSearch.equals("FirstName")) {
			for (Patient p : listOfPatientsByPhar.getAllPatientsList()) {
				if (p.getFirstName().equals(patientName)) {
					searchResult.add(p);
				}
			}
		}
		else if (typeOfSearch.equals("LastName")) {
			for (Patient p : listOfPatientsByPhar.getAllPatientsList()) {
				if (p.getLastName().equals(patientName)) {
					searchResult.add(p);
				}
			}
		}
		else { //typeOfSearch contains full name (first + last name)
			int indexOfSpace = patientName.indexOf(' ');
			String firstName = patientName.substring(0, indexOfSpace);
			String lastName = patientName.substring(indexOfSpace);
			for (Patient p : listOfPatientsByPhar.getAllPatientsList()) {
				if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
					searchResult.add(p);
				}
			}
		}
		
		return searchResult;
	}
	
}
