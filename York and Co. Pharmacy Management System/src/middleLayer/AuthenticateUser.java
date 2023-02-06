package middleLayer;

import java.util.Arrays;

public class AuthenticateUser {
	
//	int num;
//	
//	public AuthenticateUser() {
//		this.num = 10;
//	}
//	
	public static boolean checkUserValid(int username, int password) {
		
		System.out.println("TEST METHOD");
//		int usernameInt = Integer.parseInt(username);
		System.out.println("Username: " + username);
		System.out.println("Username: " + password);
//		String pass = Arrays.toString(password);
//		int p = Integer.parseInt(new String(pass));
//		System.out.println("pass " + pass);
		
		// IF IT IS A VALID USERNAME+PASS, RETURN TRUE
		// ELSE RETURN FALSE
        StringBuilder sb = new StringBuilder();
 
//        // Creating a string using append() method
//        for (int i = 0; i < password.length; i++) {
//            sb.append(password[i]);
//        }String pass = sb.toString();
//        System.out.println("pass " + pass);
//       
//        int passInteger = Integer.parseInt(pass);
//        System.out.println("pass " + passInteger);
        return true;
//        System.out.println("practice test");
        
	}

}
