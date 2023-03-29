package testCases.Unit;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.UserData.UserRoot;
import databaseDAO.UserData.UserStub;
import middleLayer.NegativeInputException;
import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.Users.ListOfUsers;
import middleLayer.Users.Owner;
import middleLayer.Users.Patient;
import middleLayer.Users.User;
import presentation.USER;

public class ListOfUsersUnitTest {

private static ListOfUsers listOfUsers;
private static UserRoot stub;
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeEach
	public void before() {
		try {
			//superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			stub = new UserStub();
			listOfUsers = ListOfUsers.getInstance(stub);
			listOfUsers.set_userDAO(stub);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Test
	void testUpdatePatientListFromDatabase() {
		listOfUsers.updatePatientListFromDatabase();
		assertEquals(((UserStub)stub).getListOfAllPatients(), listOfUsers.getAllPatientsList());
	}
	
	@Test
	void testUpdateCredentialsListFromDatabase() {
		listOfUsers.updateCredentialsListFromDatabase();
		assertEquals(((UserStub)stub).getListOfUsernamesAndPasswords(), listOfUsers.getAllCredentialsList());
	}
	@Test
	void testSearchPatientWithID1() {
		Patient result;
		Patient expected;
		result = listOfUsers.searchPatientWithID(1111122222);
		expected = listOfUsers.getAllPatientsList().get(0);
		assertEquals(expected, result );

	}
	
	@Test
	void testSearchPatientWithID2() {
		Patient result;

		result = listOfUsers.searchPatientWithID(99);
		assertEquals(null, result);
	}
	@Test
	void testModifyPatientDetails1() {	
		
		String result = null;
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		fname.setText("");
		lname.setText("");
		phoneNum.setText("");
		address.setText("");
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		try {
			listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("One of fName, lName, PhoneNum is required. Please fill in at least one of them.", result);
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
	}
	
	@Test
	void testModifyPatientDetails2() {
		

		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		String result = new String();
		fname.setText("fname");
		try {
			fname.setText("Smith");
			listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
			result = listOfUsers.getAllPatientsList().get(0).getFirstName();
			assertEquals(result, "SMITH");
			
			lname.setText("John");
			listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
			result = listOfUsers.getAllPatientsList().get(0).getLastName();
			assertEquals(result, "JOHN");
			
			phoneNum.setText("1112223333");
			listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
			result = Long.toString( listOfUsers.getAllPatientsList().get(0).getPhoneNum());
			assertEquals(result, "1112223333");
			
			address.setText("5324 yonge St");
			listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
			result = listOfUsers.getAllPatientsList().get(0).getAddress();
			assertEquals(result, "5324 YONGE ST");
			
		} catch (Exception e) {
			
			fail();
		}
	}
	@Test
	void testModifyPatientDetails3() {

		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();

		phoneNum.setText("-1112223333");
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		assertThrows(NegativeInputException.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
	}
	
	@Test
	void testModifyPatientDetails4() {
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		String result = null;
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		phoneNum.setText("111222");
		try {
			listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Phone Number must be a 10 digit number", result);
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
	}
	@Test
	void testModifyPatientDetails5() {
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		String result = null;
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		phoneNum.setText("11122233334");
		try {
			listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Phone Number must be a 10 digit number", result);
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
	}
	
	@Test 
	void testModifyPatientDetails6() {
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(99, null, null, null, null));
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
	}
	
	@Test
	void testAddPatient1() {

		ArrayList<Patient> expected = new ArrayList<Patient>();
		ArrayList<Patient> result = new ArrayList<Patient>();
		try {
			listOfUsers.addPatient("test", "name", "address", 1111144444, 1111144444, 11111444);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 19990101));
		expected.add(new Patient("JANE", "DOE", "5000 YONGE ST", 2223334444L, 2222233333L, 19990202));
		expected.add(new Patient("TEST", "NAME", "ADDRESS", 1111144444, 1111144444, 11111444));
		
		result = listOfUsers.getAllPatientsList();
		assertEquals(expected, result);

	}	
	
	@Test 
	void testAddPatient2() {
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		String result = null;
		try {
			listOfUsers.addPatient("test", "name", "address", -1111144444, 1111144444, 11111444);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Phone number must be a positive number", result);
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, -1111144444, 11111444));
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
		
	}
	@Test 
	void testAddPatient3() {
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		String result = null;
		try {
			listOfUsers.addPatient("test", "name", "address", 1111144444, -1111144444, 11111444);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Health card number must be a positive number", result);

		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
		
	}
	@Test 
	void testAddPatient4() {
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		String result = null;
		try {
			listOfUsers.addPatient("test", "name", "address", 1111144444, 111114444, 11111444);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Health Card Number must be a 10 digit number", result);

		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
		
	}
	@Test 
	void testAddPatient5() {
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		String result = null;
		try {
			listOfUsers.addPatient("test", "name", "address", 1111144444, 11111444444L, 11111444);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Health Card Number must be a 10 digit number", result);

		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
		
	}
	@Test 
	void testAddPatient6() {
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		String result = null;
		try {
			listOfUsers.addPatient("test", "name", "address", 111114444, 1111144444, 11111444);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Phone Number must be a 10 digit number", result);
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, -1111144444, 11111444));
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
		
	}
	@Test 
	void testAddPatient7() {
		ArrayList<Patient> before = listOfUsers.getAllPatientsList();
		ArrayList<Patient> after;
		String result = null;
		try {
			listOfUsers.addPatient("test", "name", "address", 11111444444L, 1111144444, 11111444);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals("Phone Number must be a 10 digit number", result);
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, -1111144444, 11111444));
		after = listOfUsers.getAllPatientsList();
		assertEquals(before, after);
		
	}


	@Test
	void testSearchPatientByName1() {
		ArrayList<Patient> result = new ArrayList<Patient>();
		ArrayList<Patient> expected = new ArrayList<Patient>();
		try {
			result = listOfUsers.searchPatientByName("SMITH", "FirstName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 19990101));
		assertEquals(expected, result);
		
	}
	@Test
	void testSearchPatientByName2() {
		ArrayList<Patient> result = new ArrayList<Patient>();
		ArrayList<Patient> expected = new ArrayList<Patient>();
		try {
			result = listOfUsers.searchPatientByName("DOE", "LastName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expected.add(new Patient("JANE", "DOE", "5000 YONGE ST", 2223334444L, 2222233333L, 19990202));
		assertEquals(expected, result);
		
	}
	@Test
	void testSearchPatientByName3() {
		ArrayList<Patient> result = new ArrayList<Patient>();
		ArrayList<Patient> expected = new ArrayList<Patient>();
		try {
			result = listOfUsers.searchPatientByName("SMITH JOHN", "FullName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 19990101));
		assertEquals(expected, result);
		
	}
	@Test
	void testSearchPatientByName4() {
		ArrayList<Patient> result = new ArrayList<Patient>();
		ArrayList<Patient> expected = new ArrayList<Patient>();
		try {
			result = listOfUsers.searchPatientByName("wrong name", "FullName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(expected, result);
	}
	@Test
	void testSearchPatientByName5() {

		assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", ""));
	}
	
	@Test
	void testSpecificPatientDetails1() {
		ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> result = null;
		Patient pat1 = new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 19990101);
		expected.add("PATIENT with Healthcard Number " + 1111122222 + " DETAILS\n");
		expected.add("First Name: " + pat1.getFirstName() + "\n");
		expected.add("Last Name: " + pat1.getLastName() + "\n");
		expected.add("Address: " + pat1.getAddress() + "\n");
		expected.add("Phone Number: " + Long.toString(pat1.getPhoneNum()) + "\n");
		expected.add("Health Card Number: " + Long.toString(pat1.getHealthCardNum()) + "\n");
		expected.add("Date Of Birth: " + Integer.toString(pat1.getDateOfBirth()) + "\n");
		try {
			result = listOfUsers.specificPatientDetails(1111122222, USER.OWNER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(expected, result);
		
	}
	@Test
	void testSpecificPatientDetails2() {
		ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> result = null;
		Patient pat1 = new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 19990101);
		expected.add("PATIENT with Healthcard Number " + 1111122222 + " DETAILS\n");
		expected.add("First Name: " + pat1.getFirstName() + "\n");
		expected.add("Last Name: " + pat1.getLastName() + "\n");
		expected.add("Address: " + pat1.getAddress() + "\n");
		expected.add("Phone Number: " + Long.toString(pat1.getPhoneNum()) + "\n");
		expected.add("Health Card Number: " + Long.toString(pat1.getHealthCardNum()) + "\n");
		expected.add("Date Of Birth: " + Integer.toString(pat1.getDateOfBirth()) + "\n");
		try {
			result = listOfUsers.specificPatientDetails(1111122222, USER.PHARMACIST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(expected, result);
		
	}
	@Test
	void testSpecificPatientDetails3() {
		ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> result = null;
		Patient pat1 = new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 19990101);
		expected.add("YOUR DETAILS\n");
		expected.add("First Name: " + pat1.getFirstName() + "\n");
		expected.add("Last Name: " + pat1.getLastName() + "\n");
		expected.add("Address: " + pat1.getAddress() + "\n");
		expected.add("Phone Number: " + Long.toString(pat1.getPhoneNum()) + "\n");
		expected.add("Health Card Number: " + Long.toString(pat1.getHealthCardNum()) + "\n");
		expected.add("Date Of Birth: " + Integer.toString(pat1.getDateOfBirth()) + "\n");
		try {
			result = listOfUsers.specificPatientDetails(1111122222, USER.PATIENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(expected, result);
		
	}
	
	
}
