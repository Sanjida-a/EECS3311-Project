package middleLayer;

import java.util.ArrayList;

public class ListOfUsers {
	
	private static ListOfUsers ListOfUsersInstance;
//	private Owner ownerUser;
//	private Pharmacist pharmacistUser;
	private ArrayList<Patient> allPatientUsersList = new ArrayList<Patient>(); //should i initialize in getInstance() or is it fine here?
	
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
	
	//Minh moved to Owner and Pharmacist class to comply with principle

//	public Owner getOwnerUser() {
//		return ownerUser;
//	}
//
//	public void setOwnerUser(Owner ownerUser) {
//		this.ownerUser = ownerUser;
//	}

//	public Pharmacist getPharmacistUser() {
//		return pharmacistUser;
//	}
//
//	public void setPharmacistUser(Pharmacist pharmacistUser) {
//		this.pharmacistUser = pharmacistUser;
//	}

	public ArrayList<Patient> getAllPatientUsersList() {
		return allPatientUsersList;
	}

	public void setAllPatientUsersList(ArrayList<Patient> allPatientUsersList) {
		this.allPatientUsersList = allPatientUsersList;
	}
	
//	import java.util.ArrayList;
//	class Inventory{
//	    private static Inventory singletonInstance;
//
//	    ArrayList<Merchandise> list = new ArrayList<Merchandise>();
//
//	    public static Inventory getInstance(){
//	        if (singletonInstance == null)
//	            singletonInstance = new Inventory();
//	        return singletonInstance;
//	    }
//
//	    public void display(){
//	 
		
}
