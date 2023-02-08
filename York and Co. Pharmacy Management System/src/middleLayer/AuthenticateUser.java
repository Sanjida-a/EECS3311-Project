package middleLayer;

import java.util.ArrayList;
import java.util.Arrays;

import databaseDAO.UserDAO;

public class AuthenticateUser { // singleton???
	
//	int num;
//	
//	public AuthenticateUser() {
//		this.num = 10;
//	}
//	
	//made static is that fine?
//	private static ArrayList<int[]> allUsernamesAndPasswordsList = new ArrayList<int[]>();
//	private static ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	
	//Minh moved to constructor to create object when program started
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
	
	public boolean checkUserValid(int username, int password) {
		
		ArrayList<User> _users = _userDAO.getListOfUsers();
		Boolean foundUsername = false;
		for (int i = 0; i < _users.size(); i++) {
			if ((_users.get(i).getUsername() == username) && (_users.get(i).getPassword() == password)) {
				foundUsername = true;
			}
		}
		
		if (foundUsername == false) {
			System.out.println("IN FALSE");
			return false;
		}
				
		System.out.println("IN TRUE");
        return true;
		 
		// authenticate TODO
		// password hashing
		
		
		
//		int usernameInt = Integer.parseInt(username);
//		System.out.println("Username: " + username);
//		System.out.println("Pass: " + password);
//		String pass = Arrays.toString(password);
//		int p = Integer.parseInt(new String(pass));
//		System.out.println("pass " + pass);
		// Creating a string using append() method
//		StringBuilder sb = new StringBuilder();
//      for (int i = 0; i < password.length; i++) {
//          sb.append(password[i]);
//      }String pass = sb.toString();
//      System.out.println("pass " + pass);
//      int passInteger = Integer.parseInt(pass);
		// IF IT IS A VALID USERNAME+PASS, RETURN TRUE
		// ELSE RETURN FALSE
		
		//--hardcoded without objects--//
//		int[] ownerUandP = {1111111111, 11111111};
//		int[] pharmacistUandP = {1234567890, 12345678};
//		
//		allUsernamesAndPasswordsList.add(ownerUandP);
//		allUsernamesAndPasswordsList.add(pharmacistUandP);
//		
//		Boolean foundUsername = false;
//		
//		for (int i = 0; i < allUsernamesAndPasswordsList.size(); i++) {
//			if ((((allUsernamesAndPasswordsList.get(i))[0]) == username) && (((allUsernamesAndPasswordsList.get(i))[1]) == password)) {
//				foundUsername = true;
//			}
//		}
		//--end--//
		
		//--BELOW IS WITH PROPER OBJECTS--//
		
	
			
////		User ownerUser = new Owner(1111111111, 11111111);
////		User pharmacistUser = new Pharmacist(1234567890, 12345678);
//		allUsernamesAndPasswordsList.add(ownerUser);
//		allUsernamesAndPasswordsList.add(pharmacistUser);
		
//		Boolean foundUsername = false;
//		for (int i = 0; i < allUsernamesAndPasswordsList.size(); i++) {
//			if ((allUsernamesAndPasswordsList.get(i).getUsername() == username) && (allUsernamesAndPasswordsList.get(i).getPassword() == password)) {
//				foundUsername = true;
//			}
//		}
//		
//		if (foundUsername == false) {
////			System.out.println("IN FALSE");
//			return false;
//		}
//      
////		System.out.println("IN TRUE");
//        return true;
  
        
	}
}
