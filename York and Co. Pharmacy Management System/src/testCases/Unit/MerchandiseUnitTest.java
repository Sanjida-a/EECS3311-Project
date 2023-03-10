package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;

class MerchandiseUnitTest {
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello@123456");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	/*
	 * there are only constructors, getter and setters
	 * no need to test them
	 */
	@Test
	void test() {
		
	}

}
