package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import databaseDAO.superDAO;
import databaseDAO.UserData.UserStub;
import middleLayer.Users.*;

class PharmacistUnitTest {
	
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
	void testAddPatient() {
		
		Pharmacist subject1 = new Pharmacist(0, 0);
		UserStub stub = new UserStub();
		subject1.set_userDAO(stub);
		ArrayList<Patient> expected = new ArrayList<Patient>();
		ArrayList<Patient> result = new ArrayList<Patient>();
		try {
			subject1.addPatient("test", "name", "address", 1111144444, 1111144444, 11111444);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
		expected.add(new Patient("TEST", "NAME", "ADDRESS", 1111144444, 1111144444, 11111444));

		result = stub.getListOfAllPatients();
		assertEquals(expected, result);

		
	}	

	@Test
	void testSearchPatientByName() {
		/*the implementation of the method SearchPatientByName() for this class
		 * is exactly identical to the one of the same method in Owner class
		 * no need to test twice 
		 */
		
	}

}
