package middleLayer.Users;

import java.sql.SQLException;
import java.util.ArrayList;

import databaseDAO.UserData.UserDAO;
import databaseDAO.UserData.UserRoot;
import presentation.DisplayLogin;

public class Pharmacist extends User {
	
//	private UserRoot _userDAO;
//	private Inventory merListByPhar = Inventory.getInstance();
//	private ListOfPatients listOfPatientsByPhar = ListOfPatients.getInstance();
	
	// having only this constructor avoids having an owner without a username and password
	// MOVED TO ADMIN
	public Pharmacist(long username, int password) {
//		super(username, password);
		this.username = username;
		this.password = password;
//		try {
//			_userDAO = new UserDAO();
//			this.username = username;
//			this.password = password;
//		} catch (Exception e) {
//	
//			e.printStackTrace();
//		}
		
	}
	
	
	public Pharmacist getPharmacistUser() {
		return this;
	}

	public void setPharmacistUser(Pharmacist pharmacistUser) {
		this.username = pharmacistUser.username;
		this.password = pharmacistUser.password;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pharmacist comparator = (Pharmacist)obj;
		if(this.username != comparator.username ||
			this.password != comparator.password) {
			return false;
		}
		return true;
	}
	// method below moved to Admin class so that both Owner and Pharmacist can add a patient; and to avoid duplication
//	public void addPatient(String firstName, String lastName, String address, long _textFieldPhoneNumber, long _textFieldHCNumber, int dateOfBirth) throws Exception {
//		
//		if (_textFieldPhoneNumber < 0) {
//			throw new Exception("Phone Number has to be a non-negative number");
//		}
//		if (_textFieldHCNumber < 0) {
//			throw new Exception("Health Card Number has to be a non-negative number");
//		}
//		if (dateOfBirth < 0) {
//			throw new Exception("Date Of Birth has to be a non-negative number");
//		}
//		
//		Patient newPatient = new Patient(firstName.toUpperCase(), lastName.toUpperCase(), address.toUpperCase(), _textFieldPhoneNumber, _textFieldHCNumber, dateOfBirth);
//		try {
//			_userDAO.addPatientToDatabase(newPatient);
//		} catch (SQLException e) {
//			throw e;
//		}
//	}
	
	// two below methods have the implementation of the below two methods to decrease repeated code
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
	// method below moved to Admin class so that both Owner and Pharmacist can add a patient; and to avoid duplication
//	public ArrayList<Patient> searchPatientByName (String patientName, String typeOfSearch) {
//		
//		ArrayList<Patient> searchResult = new ArrayList <Patient> ();
//		
//		if (typeOfSearch.equals("FirstName")) {
//			if (patientName.contains(" ")){
//
//				int indexOfSpace = patientName.indexOf(' ');
//				String firstName = patientName.substring(0, indexOfSpace); //added trim to remove white spaces
//				String lastName = patientName.substring(indexOfSpace + 1); //added trim to remove white spaces
//
//				if (firstName.equals("")){
//					patientName = lastName.trim();
//				}
//				else if (lastName.equals("")){
//					patientName = firstName.trim();
//				}
//				else{
//					patientName = firstName.trim();
//				}
//
//			}
//			for (Patient p : listOfPatientsByPhar.getAllPatientsList()) {
//				if (p.getFirstName().equalsIgnoreCase(patientName)) {
//					searchResult.add(p);
//				}
//			}
//		}
//		else if (typeOfSearch.equals("LastName")) {
//			if (patientName.contains(" ")){
//
//				int indexOfSpace = patientName.indexOf(' ');
//				String firstName = patientName.substring(0, indexOfSpace); //added trim to remove white spaces
//				String lastName = patientName.substring(indexOfSpace + 1); //added trim to remove white spaces
//
//
//				if (firstName.equals("")){
//					patientName = lastName.trim();
//				}
//
//				else if (lastName.equals("")){
//					patientName = firstName.trim();
//				}
//				else{
//					patientName = lastName.trim();
//				}
//
//			}
//			patientName.trim();
//			for (Patient p : listOfPatientsByPhar.getAllPatientsList()) {
//				if (p.getLastName().equalsIgnoreCase(patientName)) {
//					searchResult.add(p);
//				}
//			}
//		}
//		else { //typeOfSearch contains full name (first + last name)
//			int indexOfSpace = patientName.indexOf(' ');
//			String firstName = patientName.substring(0, indexOfSpace); //added trim to remove white spaces
//			String lastName = patientName.substring(indexOfSpace + 1); //added trim to remove white spaces
//			for (Patient p : listOfPatientsByPhar.getAllPatientsList()) {
//				if (p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)) {
//
//					searchResult.add(p);
//				}
//			}
//		}
//		
//		return searchResult;
//	}
	
}
