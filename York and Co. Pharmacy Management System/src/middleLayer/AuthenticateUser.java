package middleLayer;

import java.util.ArrayList;

import databaseDAO.UserDAO;
import presentation.USER;

public class AuthenticateUser {
	
	private UserDAO _userDAO;
	private static AuthenticateUser instance = null;
	
	private AuthenticateUser() { //constructor of all singleton classes should be private
		try {
			_userDAO = new UserDAO();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}
	
	public static AuthenticateUser getInstance() {
		if (instance == null) {
			instance = new AuthenticateUser();
		}		
		return instance;
	}
	
	public USER checkUserValid(int username, int password) {
		ArrayList<User> _users;
		try {
			_users = _userDAO.getListOfUsernamesAndPasswords(); //always makes sure variable is updated before checking

			for (int i = 0; i < _users.size(); i++) {
				if ((_users.get(i).getUsername() == username) && (_users.get(i).getPassword() == password)) {
					return USER.getValue(_users.get(i).getClass().getSimpleName());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
//	public boolean checkPatientValid(int ID) {
//		ArrayList<Patient> patient;
//		try {
//			patient = _userDAO.getListOfPatient();
//
//			for (int i = 0; i < patient.size(); i++) {
//				if (patient.get(i).getID() == ID)  {
//					return true;
//				}
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return false;
//	}
	
}
