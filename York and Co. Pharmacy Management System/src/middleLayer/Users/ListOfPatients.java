package middleLayer.Users;

import java.util.ArrayList;

import javax.swing.JTextField;

import databaseDAO.UserData.UserDAO;
import databaseDAO.UserData.UserRoot;



public class ListOfPatients {
	
	private static ListOfPatients ListOfUsersInstance = null;
	private ArrayList<Patient> allPatientsList;
	//private ArrayList<User> allUsersList = new ArrayList<User>();
	private UserRoot _userDAO;
	
	private ListOfPatients() { //constructor of all singleton classes should be private
		try {
			_userDAO = new UserDAO();
			allPatientsList = _userDAO.getListOfAllPatients();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}
	
	// all singleton classes must implement this method
	public static ListOfPatients getInstance(){
        if (ListOfUsersInstance == null) {
        	ListOfUsersInstance = new ListOfPatients();
        }
        return ListOfUsersInstance;
    }
	public void set_userDAO(UserRoot dao) {
		this._userDAO = dao;
		try {
			allPatientsList = this._userDAO.getListOfAllPatients();
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
	
	// search for a patient in the patient list with certain healthcardNum/ID 
	public Patient searchPatientWithID(long patientHealthCard){
	    	Patient foundPWithID = null;
	    	updatePatientListFromDatabase();
	    	for (int i = 0; i < allPatientsList.size(); i ++){
	    		if (allPatientsList.get(i).getHealthCardNum() == patientHealthCard){
	    			foundPWithID = allPatientsList.get(i);
	    		}
	    	}

	    	
	    	return foundPWithID; //if patient with such ID not found, return null
	}

	
	// modifies the details of the medication
	// OCP Followed (all in 1 method instead of 4 methods)
	public boolean modifyPatientDetails(long patientHealthCard, JTextField fName, JTextField lName, JTextField phoneNum, JTextField address) throws Exception {
		
		String[] inputsAsStrings = new String[4];
		
		inputsAsStrings[0] = fName.getText().toUpperCase();
		inputsAsStrings[1] = lName.getText().toUpperCase();
		inputsAsStrings[2] = phoneNum.getText();
		inputsAsStrings[3] = address.getText().toUpperCase();

		Patient specificPatient = this.searchPatientWithID(patientHealthCard); // checks if patient with that healthcardNum exists in system
		
		if (specificPatient == null) { // if such patient doesn't exist, can't modify details
    		return false;
    	}
		
		// if all textboxes left empty, nothing to modify --> exception for popup in front end
		if (inputsAsStrings[0].isEmpty() && inputsAsStrings[1].isEmpty() && inputsAsStrings[2].isEmpty() && inputsAsStrings[3].isEmpty()) { 
			throw new Exception("One of fName, lName, PhoneNum is required");
		}
		
		// 4 if's below make sure to update the patients accordingly based on whether anything was typed in for any of the textboxes
		if (!(inputsAsStrings[0].isEmpty())) {
			specificPatient.setFirstName(inputsAsStrings[0]);
		}
		
		if (!(inputsAsStrings[1].isEmpty())) {
			specificPatient.setLastName(inputsAsStrings[1]);
		}
		if (!(inputsAsStrings[2].isEmpty())) {
			if (Long.parseLong(inputsAsStrings[2]) < 0) {
				throw new Exception("Phone Number has to be a non-negative number");
			}
			specificPatient.setPhoneNum(Long.parseLong(inputsAsStrings[2]));
		}
		
		if (!(inputsAsStrings[3].isEmpty())) {
			specificPatient.setAddress(inputsAsStrings[3]);
		}
    	
    	// modify database accordingly
    	_userDAO.updatePatientInDatabase(patientHealthCard, specificPatient);
    	
    	//once database is updated, also updated this class's list variable by reading from the database
    	updatePatientListFromDatabase();
    	
    	return true;
    }

//	public void setAllPatientUsersList(ArrayList<Patient> allPatientUsersList) {
//		this.allPatientsList = allPatientUsersList;
//	}
//	
//	public ArrayList<Patient> getAllUsersList() {
//		return allPatientsList;
//	}
		
}
