package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import middleLayer.Users.*;
//done
class UserUnitTest {
	
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
	void testSetUsername() {
		Owner subject1 = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
		subject1.setUsername(1);
		assertEquals(subject1.username, 1);
	}

	@Test
	void testSetPassword() {
		Owner subject1 = new Owner(0, 0);
		subject1.setPassword(1);
		assertEquals(subject1.password, 1);
	}

	@Test
	void testGetUsername() {
		Owner subject1 = new Owner(0,0);
		assertEquals(subject1.getUsername(), 0);

	}

	@Test
	void testGetPassword() {
		Owner subject1 = new Owner(0, 0);
		assertEquals(subject1.getPassword(), 0);

	}


}
