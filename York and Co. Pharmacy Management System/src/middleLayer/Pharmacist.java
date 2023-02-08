package middleLayer;

import java.util.ArrayList;

import presentation.DisplayLogin;

public class Pharmacist extends User {
	
	private Inventory merListByPhar = Inventory.getInstance();
	public Pharmacist(int username, int password) {
		this.username = username;
		this.password = password;
	}
	
	//Minh moved 2 methods here from ListOfUsers
	
	public Pharmacist getPharmacistUser() {
		return this;
	}

	public void setPharmacistUser(Pharmacist pharmacistUser) {
		this.username = pharmacistUser.username;
		this.password = pharmacistUser.password;
	}
	
	public void addPatient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth) {
		// maybe have textboxes for each parameter so users can actually pharmacists can actually type in there???
		Patient newPatient = new Patient(firstName, lastName, address, phoneNum, healthCardNum, dateOfBirth);
		
		ListOfUsers listOfUsersInstance = ListOfUsers.getInstance();
		listOfUsersInstance.addPatientToList(newPatient);
	}
	
	public ArrayList<Merchandise> searchOTCMedicineByName (String name) {
		ArrayList<Merchandise> searchMedNamePhar = new ArrayList <Merchandise> ();
		for (Merchandise i : merListByPhar.getMerchandise()) {
			if (i.getName().compareTo(name) == 0) {
				searchMedNamePhar.add(i);
			}
		}
		return searchMedNamePhar;
	}
	
	public ArrayList<Merchandise> searchOTCMedicineByType (MERCHANDISE_TYPE type) {
		ArrayList<Merchandise> searchMedTypePhar = new ArrayList <Merchandise> ();
		for (Merchandise i : merListByPhar.getMerchandise()) {
			if (i.getType() == type) {
				searchMedTypePhar.add(i);
			}
		}
		return searchMedTypePhar;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListOfUsers listOfUsersInstance = ListOfUsers.getInstance();
		System.out.println(listOfUsersInstance.getAllPatientUsersList());
		Pharmacist p1 = new Pharmacist(111,111);
		p1.addPatient("john", "doe", "123 fake street", 911, 1234567890, 02072023);
		System.out.println(listOfUsersInstance.getAllPatientUsersList());
		p1.addPatient("john2", "doe2", "123 fake street", 911, 1234567890, 02072023);
		System.out.println(listOfUsersInstance.getAllPatientUsersList());
		System.out.println(listOfUsersInstance.getAllPatientUsersList().get(1).getFirstName());
		ArrayList<Merchandise> list = p1.searchOTCMedicineByName("Tylenol");
		System.out.println(list);
	}
	
	
}
