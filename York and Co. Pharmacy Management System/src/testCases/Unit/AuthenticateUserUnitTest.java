package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import databaseDAO.UserData.UserStub;
import middleLayer.Users.AuthenticateUser;
import middleLayer.Users.ListOfUsers;
import presentation.USER;

class AuthenticateUserUnitTest {
	
	private static ListOfUsers listOfUsers;
	
	@BeforeAll
	public static void before() {
		try {

			listOfUsers = ListOfUsers.getInstance(new UserStub());
			
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Test
	void testCheckUserValid1()  {

		AuthenticateUser auth = AuthenticateUser.getInstance();

		
		USER result;
		//input account info for OWNER
		result = auth.checkUserValid(1111, 1111);
		assertEquals( USER.OWNER, result );
		
		//input account info for PHARMACIST
		result = auth.checkUserValid(1234, 1234);
		assertEquals(USER.PHARMACIST, result );
		
		//input account info for PATIENT
		result = auth.checkUserValid(1111122222, 19990101);
		assertEquals(USER.PATIENT, result );


	}
	
	@Test
	void testCheckUserValid2() {
		AuthenticateUser auth = AuthenticateUser.getInstance();
		assertEquals(null, auth.checkUserValid(2222, 2222));
	}

}
