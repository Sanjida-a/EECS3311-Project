package middleLayer.Users;

import java.sql.SQLException;
import java.util.ArrayList;

import databaseDAO.UserData.UserDAO;
import databaseDAO.UserData.UserRoot;

public abstract class Admin extends User {
	
	private UserRoot _userDAO;
	private ListOfPatients listOfPatientsByAdmin = ListOfPatients.getInstance();
	
	public Admin(long username, int password) {
		try {
			_userDAO = new UserDAO();
			this.username = username;
			this.password = password;
		} catch (Exception e) {

		}
	}
	
	public void addPatient(String firstName, String lastName, String address, long _textFieldPhoneNumber, long _textFieldHCNumber, int dateOfBirth) throws Exception {
			
		if (_textFieldPhoneNumber < 0) {
			throw new Exception("Phone Number has to be a non-negative number");
		}
		if (_textFieldHCNumber < 0) {
			throw new Exception("Health Card Number has to be a non-negative number");
		}
		if (dateOfBirth < 0) {
			throw new Exception("Date Of Birth has to be a non-negative number");
		}
		
		Patient newPatient = new Patient(firstName.toUpperCase(), lastName.toUpperCase(), address.toUpperCase(), _textFieldPhoneNumber, _textFieldHCNumber, dateOfBirth);
		try {
			_userDAO.addPatientToDatabase(newPatient);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	// OCP followed to allow search by any type of name (first, last, or both) in 1 method
	// searches and returns list of patients in system that have same entered name
	public ArrayList<Patient> searchPatientByName (String patientName, String typeOfSearch) throws Exception {
		
		if (patientName.isEmpty()) {
			throw new Exception(); // ensures something is entered 
		}
		
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
			for (Patient p : listOfPatientsByAdmin.getAllPatientsList()) {
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
			for (Patient p : listOfPatientsByAdmin.getAllPatientsList()) {
				if (p.getLastName().equalsIgnoreCase(patientName)) {
					searchResult.add(p);
				}
			}
		}
		else { //typeOfSearch contains full name (first + last name)
			int indexOfSpace = patientName.indexOf(' ');
			String firstName = patientName.substring(0, indexOfSpace); //added trim to remove white spaces
			String lastName = patientName.substring(indexOfSpace + 1); //added trim to remove white spaces
			for (Patient p : listOfPatientsByAdmin.getAllPatientsList()) {
				if (p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)) {

					searchResult.add(p);
				}
			}
		}
		
		return searchResult;
	}
	
	public void set_userDAO(UserRoot dao) {
		this._userDAO = dao;
	}
	
}
