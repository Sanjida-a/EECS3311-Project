package middleLayer;

import presentation.DisplayLogin;

public class Pharmacist extends User {
	public Pharmacist(int username, int password) {
		this.username = username;
		this.password = password;
	}
	
	public void addPatient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth) {
		// maybe have textboxes for each parameter so users can actually pharmacists can actually type in there???
		Patient newPatient = new Patient(firstName, lastName, address, phoneNum, healthCardNum, dateOfBirth);
		
		ListOfUsers listOfUsersInstance = ListOfUsers.getInstance();
		listOfUsersInstance.addPatientToList(newPatient);
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
	}
}
