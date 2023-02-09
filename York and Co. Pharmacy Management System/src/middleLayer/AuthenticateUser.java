package middleLayer;

import java.util.ArrayList;
import java.util.Arrays;

import databaseDAO.UserDAO;
import presentation.USER;

public class AuthenticateUser { // singleton???
	

	private UserDAO _userDAO;
	private static AuthenticateUser instance = null;
	
	public AuthenticateUser() {
		try {
			_userDAO = new UserDAO();
		} catch (ClassNotFoundException e) {
	
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
		
		ArrayList<User> _users = _userDAO.getListOfUsers();
		//Boolean foundUsername = false;
		for (int i = 0; i < _users.size(); i++) {
			if ((_users.get(i).getUsername() == username) && (_users.get(i).getPassword() == password)) {
				//foundUsername = true;
				return USER.getValue(_users.get(i).getClass().getSimpleName());
			}
		}
		
		return null;
		 
		// authenticate TODO
		// password hashing
		
		
		

  
        
	}
}
