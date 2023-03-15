package middleLayer.Users;

import java.util.ArrayList;

import databaseDAO.UserData.UserDAO;
import databaseDAO.UserData.UserRoot;
import presentation.USER;

public class AuthenticateUser {
	
	private UserRoot _userDAO;
	private static AuthenticateUser instance = null;
	
	private AuthenticateUser() { //constructor of all singleton classes should be private
		try {
			_userDAO = new UserDAO();
		} catch (Exception e) {
	
			e.printStackTrace();
		}
	}
	
	// singleton classes must have this method
	public static AuthenticateUser getInstance() {
		if (instance == null) {
			instance = new AuthenticateUser();
		}		
		return instance;
	}
	
	// using the list of usernames and passwords retrieved from database, check to see if a login attempt was successful or not
	public USER checkUserValid(long username, int password) {
		ArrayList<User> _users;
		try {
			_users = _userDAO.getListOfUsernamesAndPasswords(); // always makes sure variable is updated before checking

			for (int i = 0; i < _users.size(); i++) {
				if ((_users.get(i).getUsername() == username) && (_users.get(i).getPassword() == password)) {
					return USER.getValue(_users.get(i).getClass().getSimpleName());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public void set_userDAO(UserRoot dao) {
		this._userDAO = dao;
	}
	
}
