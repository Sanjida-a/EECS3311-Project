package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import databaseDAO.UserStub;
import databaseDAO.superDAO;
import middleLayer.AuthenticateUser;
import presentation.USER;
import middleLayer.Pharmacist;
//done
class AuthenticateUserUnitTest {

	@Test
	void testCheckUserValid() throws Exception {
		//fail("Not yet implemented");
		//this dependency cannot be removed unless the design in some classes changes
		//tester should enter his/her own SQL database root password
		superDAO.setPassword("Motp1104#");
		AuthenticateUser auth = AuthenticateUser.getInstance();
		UserStub stub = new UserStub();
		//to make the instance of Pharmacist in the list work with the stub database
		//((Pharmacist)stub.allUsernamesAndPasswordsList.get(1)).set_userDAO(stub);
		
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
