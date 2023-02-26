package middleLayer;

import java.util.ArrayList;

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

//	public void setAllPatientUsersList(ArrayList<Patient> allPatientUsersList) {
//		this.allPatientsList = allPatientUsersList;
//	}
//	
//	public ArrayList<Patient> getAllUsersList() {
//		return allPatientsList;
//	}
		
}
