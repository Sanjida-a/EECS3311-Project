package middleLayer.Users;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTextField;

import databaseDAO.UserData.UserDAO;
import databaseDAO.UserData.UserRoot;
import middleLayer.NegativeInputException;
import middleLayer.Orders.Order;
import presentation.USER;



public class ListOfUsers {
	
	private static ListOfUsers ListOfUsersInstance = null;
	private ArrayList<User> allCredentialsList; // means all users
	private ArrayList<Patient> allPatientsList;
	
	// don't need below
//	private ArrayList<Pharmacist> allPharmacistsList;
//	private Owner onlyOwner;
	//private ArrayList<User> allUsersList = new ArrayList<User>();
	private UserRoot _userDAO;
	
	private ListOfUsers() { //constructor of all singleton classes should be private
		try {
			_userDAO = new UserDAO();
			allPatientsList = _userDAO.getListOfAllPatients();
			allCredentialsList = _userDAO.getListOfUsernamesAndPasswords();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}
	private ListOfUsers(UserRoot dao) {
		try {
			_userDAO = dao;
			allPatientsList = _userDAO.getListOfAllPatients();
			allCredentialsList = _userDAO.getListOfUsernamesAndPasswords();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}
	
	// all singleton classes must implement this method
	public static ListOfUsers getInstance(){
        if (ListOfUsersInstance == null) {
        	ListOfUsersInstance = new ListOfUsers();
        }
        return ListOfUsersInstance;
    }
	
	public static ListOfUsers getInstance(UserRoot dao) {
        if (ListOfUsersInstance == null) {
        	ListOfUsersInstance = new ListOfUsers(dao);
        }
        return ListOfUsersInstance;
	}
	
	public void set_userDAO(UserRoot dao) {
		this._userDAO = dao;
		try {
			allPatientsList = this._userDAO.getListOfAllPatients();
			allCredentialsList = this._userDAO.getListOfUsernamesAndPasswords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// update instance variable by reading from database
	public void updatePatientListFromDatabase() { // always try to update at the beginning and end of each method
		try {
			allPatientsList = _userDAO.getListOfAllPatients(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//getter method
	public ArrayList<Patient> getAllPatientsList() {
		updatePatientListFromDatabase(); //updates from database first
		return allPatientsList;
	}
	
	// update instance variable by reading from database
	public void updateCredentialsListFromDatabase() { // always try to update at the beginning and end of each method
		try {
			allCredentialsList = _userDAO.getListOfUsernamesAndPasswords();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//getter method
	public ArrayList<User> getAllCredentialsList() {
		updateCredentialsListFromDatabase(); //updates from database first
		return allCredentialsList;
	}
	
	// search for a patient in the patient list with certain healthcardNum/ID 
	public Patient searchPatientWithID(long patientHealthCard){
    	Patient foundPWithID = null;
    	updatePatientListFromDatabase(); //just to be safe, keeping instance variable up to date
    	for (int i = 0; i < allPatientsList.size(); i ++){
    		if (allPatientsList.get(i).getHealthCardNum() == patientHealthCard){
    			foundPWithID = allPatientsList.get(i);
    			break;
    		}
    	}

    	return foundPWithID; //if patient with such ID not found, return null
	}
	
	public void addPatient(String firstName, String lastName, String address, long _textFieldPhoneNumber, long _textFieldHCNumber, int dateOfBirth) throws Exception {
		updatePatientListFromDatabase(); //just to be safe, keeping instance variable up to date
		
		if (_textFieldHCNumber < 0) {
			throw new NegativeInputException("Health card number must be a positive number");
		}
		
		if (_textFieldPhoneNumber < 0) {
			throw new NegativeInputException("Phone number must be a positive number");
		}
		
		if (!((1000000000 <= _textFieldHCNumber) && (_textFieldHCNumber <= 9999999999L))) {
			throw new Exception("Health Card Number must be a 10 digit number");
		}
		
		if (!((1000000000 <= _textFieldPhoneNumber) && (_textFieldPhoneNumber <= 9999999999L))) {
			throw new Exception("Phone Number must be a 10 digit number");
		}
		
		Patient newPatient = new Patient(firstName.toUpperCase(), lastName.toUpperCase(), address.toUpperCase(), _textFieldPhoneNumber, _textFieldHCNumber, dateOfBirth);
		try {
			_userDAO.addPatientToDatabase(newPatient);
		} catch (SQLException e) {
			throw e;
		}
		
		// update instance variable from database
		updatePatientListFromDatabase();
	}
	
//	// modifies the details of the patient
//		// OCP Followed (all in 1 method instead of 4 methods)
//	public boolean modifyPatientDetails(long patientHealthCard, JTextField fName, JTextField lName, JTextField phoneNum, JTextField address) throws Exception {
//		updatePatientListFromDatabase(); //just to be safe, keeping instance variable up to date
//		
//		String[] inputsAsStrings = new String[4];
//		
//		inputsAsStrings[0] = fName.getText().toUpperCase();
//		inputsAsStrings[1] = lName.getText().toUpperCase();
//		inputsAsStrings[2] = phoneNum.getText();
//		inputsAsStrings[3] = address.getText().toUpperCase();
//
//		Patient specificPatient = this.searchPatientWithID(patientHealthCard); // checks if patient with that healthcardNum exists in system
//		
//		if (specificPatient == null) { // if such patient doesn't exist, can't modify details
//    		return false;
//    	}
//		
//		// if all textboxes left empty, nothing to modify --> exception for popup in front end
//		if (inputsAsStrings[0].isEmpty() && inputsAsStrings[1].isEmpty() && inputsAsStrings[2].isEmpty() && inputsAsStrings[3].isEmpty()) { 
//			throw new Exception("One of fName, lName, PhoneNum is required");
//		}
//		
//		// 4 if's below make sure to update the patients accordingly based on whether anything was typed in for any of the textboxes
//		if (!(inputsAsStrings[0].isEmpty())) {
//			specificPatient.setFirstName(inputsAsStrings[0]);
//		}
//		
//		if (!(inputsAsStrings[1].isEmpty())) {
//			specificPatient.setLastName(inputsAsStrings[1]);
//		}
//		if (!(inputsAsStrings[2].isEmpty())) {
//			if (!((1000000000 <= Long.parseLong(inputsAsStrings[2])) && (Long.parseLong(inputsAsStrings[2]) <= 9999999999L))) {
//				throw new Exception("Phone Number must be a positive, 10 digit number");
//			}
//			specificPatient.setPhoneNum(Long.parseLong(inputsAsStrings[2]));
//		}
//		
//		if (!(inputsAsStrings[3].isEmpty())) {
//			specificPatient.setAddress(inputsAsStrings[3]);
//		}
//    	
//    	// modify database accordingly
//    	_userDAO.updatePatientInDatabase(patientHealthCard, specificPatient);
//    	
//    	//once database is updated, also updated this class's list variable by reading from the database
//    	updatePatientListFromDatabase();
//    	
//    	return true;
//    }
	
	// modifies the details of the patient
	// OCP Followed (all in 1 method instead of 4 methods)
	// AIZA UPDATED VERSION TO FIX RETURN VALUE & EXCEPTIONS 
	public void modifyPatientDetails(long patientHealthCard, JTextField fName, JTextField lName, JTextField phoneNum, JTextField address) throws Exception {
		updatePatientListFromDatabase(); //just to be safe, keeping instance variable up to date
		
		String[] inputsAsStrings = new String[4];
		
		inputsAsStrings[0] = fName.getText().toUpperCase();
		inputsAsStrings[1] = lName.getText().toUpperCase();
		inputsAsStrings[2] = phoneNum.getText();
		inputsAsStrings[3] = address.getText().toUpperCase();

		Patient specificPatient = this.searchPatientWithID(patientHealthCard); // checks if patient with that healthcardNum exists in system
		
		if (specificPatient == null) { // if such patient doesn't exist, can't modify details
			throw new Exception("Unsuccessful");
    	}
		
		// if all textboxes left empty, nothing to modify --> exception for popup in front end
		if (inputsAsStrings[0].isEmpty() && inputsAsStrings[1].isEmpty() && inputsAsStrings[2].isEmpty() && inputsAsStrings[3].isEmpty()) { 
			throw new Exception("One of fName, lName, PhoneNum is required. Please fill in at least one of them.");
		}
		
		// 4 if's below make sure to update the patients accordingly based on whether anything was typed in for any of the textboxes
		if (!(inputsAsStrings[0].isEmpty())) {
			specificPatient.setFirstName(inputsAsStrings[0]);
		}
		
		if (!(inputsAsStrings[1].isEmpty())) {
			specificPatient.setLastName(inputsAsStrings[1]);
		}
		
		if (!(inputsAsStrings[2].isEmpty())) {
			long longPhoneNum = Long.parseLong(inputsAsStrings[2]); // throws numberFormatException if not a long
			if (longPhoneNum < 0) {
				throw new NegativeInputException("Phone number must be a positive number");
			}
			if (!((1000000000 <= longPhoneNum) && (longPhoneNum <= 9999999999L))) {
				throw new Exception("Phone Number must be a 10 digit number");
			}
			specificPatient.setPhoneNum(longPhoneNum);
		}
		
		if (!(inputsAsStrings[3].isEmpty())) {
			specificPatient.setAddress(inputsAsStrings[3]);
		}
    	
    	// modify database accordingly
    	_userDAO.updatePatientInDatabase(patientHealthCard, specificPatient);
    	
    	//once database is updated, also updated this class's list variable by reading from the database
    	updatePatientListFromDatabase();
    	
    }
	
	// OCP followed to allow search by any type of name (first, last, or both) in 1 method
	// searches and returns list of patients in system that have same entered name
	public ArrayList<Patient> searchPatientByName (String patientName, String typeOfSearch) throws Exception {
		updatePatientListFromDatabase(); //just to be safe, keeping instance variable up to date
	
		ArrayList<Patient> searchResult = new ArrayList <Patient> ();
		
		if (typeOfSearch.equals("FirstName")) {
			if (patientName.contains(" ")){

				int indexOfSpace = patientName.indexOf(' ');
				String firstName = patientName.substring(0, indexOfSpace); //added trim to remove white spaces
				String lastName = patientName.substring(indexOfSpace + 1); //added trim to remove white spaces

				if (firstName.equals("")){
					patientName = lastName.trim();
				}
				else if (lastName.equals("")){
					patientName = firstName.trim();
				}
				else{
					patientName = firstName.trim();
				}

			}
			for (Patient p : allPatientsList) {
				if (p.getFirstName().equalsIgnoreCase(patientName)) {
					searchResult.add(p);
				}
			}
		}
		else if (typeOfSearch.equals("LastName")) {
			if (patientName.contains(" ")){

				int indexOfSpace = patientName.indexOf(' ');
				String firstName = patientName.substring(0, indexOfSpace); //added trim to remove white spaces
				String lastName = patientName.substring(indexOfSpace + 1); //added trim to remove white spaces


				if (firstName.equals("")){
					patientName = lastName.trim();
				}

				else if (lastName.equals("")){
					patientName = firstName.trim();
				}
				else{
					patientName = lastName.trim();
				}

			}
			patientName.trim();
			for (Patient p : allPatientsList) {
				if (p.getLastName().equalsIgnoreCase(patientName)) {
					searchResult.add(p);
				}
			}
		}
		else { //typeOfSearch contains full name (first + last name)
			int indexOfSpace = patientName.indexOf(' ');
			
			if (indexOfSpace == -1) {
				throw new Exception("Please enter both a first and last name to search by Full Name");
			}
			else {
				String firstName = patientName.substring(0, indexOfSpace); //added trim to remove white spaces
				String lastName = patientName.substring(indexOfSpace + 1); //added trim to remove white spaces
				
				if (lastName.isBlank()) {
					throw new Exception("Please enter both a first and last name to search by Full Name");
				}
				
				for (Patient p : allPatientsList) {
					if (p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)) {

						searchResult.add(p);
					}
				}
			}
			
		}
		
		return searchResult;
	}
	
	//aiza added below for Itr3 detailed story
	public ArrayList<String> specificPatientDetails(long healthCardID, USER userType) throws Exception {
		Patient pFound = this.searchPatientWithID(healthCardID);
		
		if (pFound == null) {
			throw new Exception("Patient doesn't exist!");
		}
		
		ArrayList<String> details = new ArrayList<String>();
		
		if (userType == USER.OWNER || userType == USER.PHARMACIST) {
			details.add("PATIENT with Healthcard Number " + healthCardID + " DETAILS\n");
		}
		else {
			details.add("YOUR DETAILS\n");
		}
		
		details.add("First Name: " + pFound.getFirstName() + "\n");
		details.add("Last Name: " + pFound.getLastName() + "\n");
		details.add("Address: " + pFound.getAddress() + "\n");
		details.add("Phone Number: " + Long.toString(pFound.getPhoneNum()) + "\n");
		details.add("Health Card Number: " + Long.toString(pFound.getHealthCardNum()) + "\n");
		details.add("Date Of Birth: " + Integer.toString(pFound.getDateOfBirth()) + "\n");
		
		return details;

	}

//	public void setAllPatientUsersList(ArrayList<Patient> allPatientUsersList) {
//		this.allPatientsList = allPatientUsersList;
//	}
//	
//	public ArrayList<Patient> getAllUsersList() {
//		return allPatientsList;
//	}
		
}
