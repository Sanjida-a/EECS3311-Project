package databaseDAO;

import java.util.ArrayList;

import middleLayer.Owner;
import middleLayer.Patient;
import middleLayer.Pharmacist;
import middleLayer.User;

public class UserDAO {
	
	private ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	
	public UserDAO() throws ClassNotFoundException {
		try {
			User owner = new Owner(1111111111, 11111111);
			User pharma1 = new Pharmacist(1234567890,12345678);
			User patient = new Patient("Smith", "John", "5324 yonge St", 1112223333, 1111122222, 11111222);
			allUsernamesAndPasswordsList.add(owner);
			allUsernamesAndPasswordsList.add(pharma1);
			allUsernamesAndPasswordsList.add(patient);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<User> getListOfUsers() {
		return allUsernamesAndPasswordsList;
	
	}
	
	public void addPatient( Patient newPatient) {
		allUsernamesAndPasswordsList.add(newPatient);
	}
}
