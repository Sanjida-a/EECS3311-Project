package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import databaseDAO.UserStub;
import middleLayer.AuthenticateUser;
import presentation.USER;
//done
class AuthenticateUserUnitTest {

	@Test
	void testCheckUserValid() {
		//fail("Not yet implemented");

		AuthenticateUser auth = AuthenticateUser.getInstance();
		UserStub stub = new UserStub();
		auth.set_userDAO(stub);
		
		USER result;
		//input account info for PATIENT
		result = auth.checkUserValid(1111122222, 11111222);
		assertEquals(USER.PATIENT, result );
		//input account info for OWNER
		result = auth.checkUserValid(1111111111, 11111111);
		assertEquals( USER.OWNER, result );
		//input account info for PHARMACIST
		result = auth.checkUserValid(1234567890, 12345678);
		assertEquals(USER.PHARMACIST, result );
	}

}
