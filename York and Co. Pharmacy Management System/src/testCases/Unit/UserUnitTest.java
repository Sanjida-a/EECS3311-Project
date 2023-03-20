package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import middleLayer.Users.*;

class UserUnitTest {
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	/*@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}*/

	/*
	 * since this is abstract class, the methods in this class are 
	 * tested in Owner class, which is a subclass of this one, instead.
	 * 
	 */


}
