
package testCases.IntegrationTests;


import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import middleLayer.Users.AuthenticateUser;
import presentation.USER;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;


class AuthenticateUserTest {
	
	private static AuthenticateUser authenticateUserInstance;
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			authenticateUserInstance = AuthenticateUser.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
    @Test
    void getInstance() {

    }
    
    @Test
    void checkUserInValidTest() { //for incorrect entry
        assertEquals(null, authenticateUserInstance.checkUserValid(1, 1)); // no such owner or pharmacist or patient can exist (bc 4 digit OR 10 digit)
    }

    @Test
    void checkUserValidOwnerLogin() {
        assertEquals(USER.OWNER, authenticateUserInstance.checkUserValid(1111, 1111));
    }

    @Test
    void checkUserValidPharamcistLogin() {
        assertEquals(USER.PHARMACIST, authenticateUserInstance.checkUserValid(1234, 1234));
    }

    @Test
    void checkUserValidPatientLogin() {
        assertEquals(USER.PATIENT, authenticateUserInstance.checkUserValid(1111122222, 11111222));
    }
}
