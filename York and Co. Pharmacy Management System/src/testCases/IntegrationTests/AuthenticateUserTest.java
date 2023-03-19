
package testCases.IntegrationTests;


import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import middleLayer.Users.AuthenticateUser;
import presentation.USER;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;


class AuthenticateUserTest {
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
    @Test
    void getInstance() {

    }
    @Test
    void checkUserValidTest() { //for incorrect entry
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(null, val.checkUserValid(2344445, 2344445));
    }

    @Test
    void checkUserValidOwnerLogin() {
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.OWNER, val.checkUserValid(1111, 1111));
    }

    @Test
    void checkUserValidPharamcistLogin() {
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.PHARMACIST, val.checkUserValid(1234, 1234));
    }

    @Test
    void checkUserValidPatientLogin() {
    	
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.PATIENT, val.checkUserValid(1111122222, 11111222));
    }
}
