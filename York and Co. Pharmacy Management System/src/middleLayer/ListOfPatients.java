package middleLayer;

import java.util.ArrayList;

import javax.swing.JTextField;

import databaseDAO.UserDAO;

public class ListOfPatients {
	
	private static ListOfPatients ListOfUsersInstance = null;
	private ArrayList<Patient> allPatientsList;
	//private ArrayList<User> allUsersList = new ArrayList<User>();
	private UserDAO _userDAO;
	
	private ListOfPatients() { //constructor of all singleton classes should be private
		try {
			_userDAO = new UserDAO();
			allPatientsList = _userDAO.getListOfAllPatients();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}
	
	public static ListOfPatients getInstance(){
        if (ListOfUsersInstance == null) {
        	ListOfUsersInstance = new ListOfPatients();
        }
        return ListOfUsersInstance;
    }

//	public void addPatientToList(Patient newPatient) {
//		//_userDAO.addPatient(newPatient);
//	}
	public void updatePatientListFromDatabase() { // always try to update at the beginning and end of each method
		try {
			allPatientsList = _userDAO.getListOfAllPatients(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//look into this
	public ArrayList<Patient> getAllPatientsList() {
		updatePatientListFromDatabase(); //updates from database first
		return allPatientsList;
	}
	
	 public Patient searchPatientWithID(int patientHealthCard){
	    	Patient foundPWithID = null;
	    	
	    	for (int i = 0; i < allPatientsList.size(); i ++){
	    		if (allPatientsList.get(i).getHealthCardNum() == patientHealthCard){
	    			foundPWithID = allPatientsList.get(i);
	    		}
	    	}
	    	return foundPWithID;
	 }
	
	public boolean modifyPatientDetails(int patientHealthCard, JTextField fName, JTextField lName, JTextField phoneNum, JTextField address) throws Exception {
		
		String[] inputsAsStrings = new String[4];
		
		inputsAsStrings[0] = fName.getText();
		inputsAsStrings[1] = lName.getText();
		inputsAsStrings[2] = phoneNum.getText();
		inputsAsStrings[3] = address.getText();
		
		Patient specificPatient = this.searchPatientWithID(patientHealthCard);
		
		if (specificPatient == null) {
    		return false;
    	}
		
		if (inputsAsStrings[0].isEmpty() && inputsAsStrings[1].isEmpty() && inputsAsStrings[2].isEmpty() && inputsAsStrings[3].isEmpty()) {
			throw new Exception();
		}
		
		if (!(inputsAsStrings[0].isEmpty())) {
			specificPatient.setFirstName(inputsAsStrings[0]);
		}
		
		if (!(inputsAsStrings[1].isEmpty())) {
			specificPatient.setLastName(inputsAsStrings[1]);
		}
		
		if (!(inputsAsStrings[2].isEmpty())) {
			specificPatient.setPhoneNum(Integer.parseInt(inputsAsStrings[2]));
		}
		
		if (!(inputsAsStrings[3].isEmpty())) {
			specificPatient.setAddress(inputsAsStrings[3]);
		}
    	
    	// modify database accordingly
    	_userDAO.updatePatientInDatabase(patientHealthCard, specificPatient);
    	
    	//once database is updated, also updated this class's list variable
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
