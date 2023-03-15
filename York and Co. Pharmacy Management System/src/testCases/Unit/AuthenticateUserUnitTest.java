package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import databaseDAO.UserData.UserStub;
import middleLayer.Users.AuthenticateUser;
import presentation.USER;

class AuthenticateUserUnitTest {
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Test
	void testCheckUserValid() throws Exception {

		AuthenticateUser auth = AuthenticateUser.getInstance();
		UserStub stub = new UserStub();
		
		auth.set_userDAO(stub);
		
		USER result;
		//input account info for OWNER
		result = auth.checkUserValid(1111111111, 11111111);
		assertEquals( USER.OWNER, result );
		
		//input account info for PHARMACIST
		result = auth.checkUserValid(1234567890, 12345678);
		assertEquals(USER.PHARMACIST, result );
		
		//input account info for PATIENT
		result = auth.checkUserValid(1111122222, 11111222);
		assertEquals(USER.PATIENT, result );


	}

}
