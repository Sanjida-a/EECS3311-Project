
package testCases.IntegrationTests;


import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import middleLayer.Users.AuthenticateUser;
import presentation.USER;
import org.junit.jupiter.api.Assertions;


import static org.junit.jupiter.api.Assertions.*;


class AuthenticateUserTest {
	
	static String pass = "hello@123456";  // TA please change this according to your mySQL password in order for the tests to work
	
    @Test
    void getInstance() {

    }
    @Test
    void checkUserValidTest() { //for incorrect entry
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(null, val.checkUserValid(2344445, 2344445));
    }

    @Test
    void checkUserValidOwnerLogin() {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.OWNER, val.checkUserValid(1111111111, 11111111));
    }

    @Test
    void checkUserValidPharamcistLogin() {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.PHARMACIST, val.checkUserValid(1234567890, 12345678));
    }

    @Test
    void checkUserValidPatientLogin() {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.PATIENT, val.checkUserValid(1111122222, 11111222));
    }
}
