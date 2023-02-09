package middleLayer;

import java.util.ArrayList;

public class ListOfUsers {
	
	private static ListOfUsers ListOfUsersInstance;
	private ArrayList<Patient> allPatientUsersList = new ArrayList<Patient>();
	
	private ListOfUsers() {
	}
	
	public static ListOfUsers getInstance(){
        if (ListOfUsersInstance == null) {
        	ListOfUsersInstance = new ListOfUsers();
        }
        return ListOfUsersInstance;
    }

	public void addPatientToList(Patient newPatient) {
		ListOfUsersInstance.allPatientUsersList.add(newPatient);
	}
	
	//Minh moved some methods to Owner and Pharmacist class to comply with SOLID principles

	public ArrayList<Patient> getAllPatientUsersList() {
		return allPatientUsersList;
	}

	public void setAllPatientUsersList(ArrayList<Patient> allPatientUsersList) {
		this.allPatientUsersList = allPatientUsersList;
	}
		
}
