package middleLayer;

import java.util.ArrayList;

import databaseDAO.UserDAO;

public class ListOfUsers {
	
	private static ListOfUsers ListOfUsersInstance = null;
	private ArrayList<Patient> allPatientUsersList = new ArrayList<Patient>();
	private ArrayList<User> allUsersList = new ArrayList<User>();
	private UserDAO _userDAO;
	
	public ListOfUsers() {
		try {
			_userDAO = new UserDAO();
			allUsersList = _userDAO.getListOfUsers();
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
	}
	
	public static ListOfUsers getInstance(){
        if (ListOfUsersInstance == null) {
        	ListOfUsersInstance = new ListOfUsers();
        }
        return ListOfUsersInstance;
    }

	public void addPatientToList(Patient newPatient) {
		_userDAO.addPatient(newPatient);
	}

	public ArrayList<Patient> getAllPatientUsersList() {
		return allPatientUsersList;
	}

	public void setAllPatientUsersList(ArrayList<Patient> allPatientUsersList) {
		this.allPatientUsersList = allPatientUsersList;
	}
	
	public ArrayList<User> getAllUsersList() {
		return allUsersList;
	}
		
}
