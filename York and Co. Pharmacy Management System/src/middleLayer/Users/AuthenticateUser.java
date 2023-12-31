package middleLayer.Users;

import java.util.ArrayList;

import databaseDAO.UserData.UserDAO;
import databaseDAO.UserData.UserRoot;
import presentation.USER;

public class AuthenticateUser {
	
	private ListOfUsers listOfUsersInstance;
	private static AuthenticateUser instance = null;
	private long userLoggedIn;
	private AuthenticateUser() { //constructor of all singleton classes should be private
		listOfUsersInstance = ListOfUsers.getInstance();
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
		_users = listOfUsersInstance.getAllCredentialsList();

		for (int i = 0; i < _users.size(); i++) {
			if ((_users.get(i).getUsername() == username) && (_users.get(i).getPassword() == password)) {
				this.userLoggedIn = _users.get(i).username;
				return USER.getValue(_users.get(i).getClass().getSimpleName());
			}
		}
		return null; // returns null if credentials do not exist
	}
	
	public long getUserLoggedIn() {
		return this.userLoggedIn;
	}
	
}
