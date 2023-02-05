package middleLayer;

import java.util.Arrays;

public class AuthenticateUser {
	
//	int num;
//	
//	public AuthenticateUser() {
//		this.num = 10;
//	}
//	
	public static void checkUserValid(String username, char[] password) {
		
		System.out.println("TEST");
		int usernameInt = Integer.parseInt(username);
		System.out.println("Username: " + usernameInt);
//		String pass = Arrays.toString(password);
//		int p = Integer.parseInt(new String(pass));
//		System.out.println("pass " + pass);
		
		
        StringBuilder sb = new StringBuilder();
 
        // Creating a string using append() method
        for (int i = 0; i < password.length; i++) {
            sb.append(password[i]);
        }String pass = sb.toString();
        System.out.println("pass " + pass);
       
        int passInteger = Integer.parseInt(pass);
        System.out.println("pass " + passInteger);
	}

}
