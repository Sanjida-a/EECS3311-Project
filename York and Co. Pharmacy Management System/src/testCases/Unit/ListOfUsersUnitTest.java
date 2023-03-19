package testCases.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.UserData.UserRoot;
import databaseDAO.UserData.UserStub;
import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.Users.ListOfUsers;
import middleLayer.Users.Owner;
import middleLayer.Users.Patient;

public class ListOfUsersUnitTest {

private static ListOfUsers listOfUsers;
private static UserRoot stub;
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
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
		assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(99, null, null, null, null));

		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		fname.setText("");
		lname.setText("");
		phoneNum.setText("");
		address.setText("");

		assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
	}
	
	@Test
	void testModifyPatientDetails2() {
		
		//UserStub stub = new UserStub();
		//listOfUsers.set_userDAO(stub);
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
			
			e.printStackTrace();
		}
	}
	@Test
	void testModifyPatientDetails3() {
		
		//UserStub stub = new UserStub();
		//listOfUsers.set_userDAO(stub);
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		phoneNum.setText("-1112223333");
		assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
	}
	
	@Test
	void testModifyPatientDetails4() {
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		phoneNum.setText("111222");
		assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
		phoneNum.setText("11122233334");
		assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
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
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
		expected.add(new Patient("JANE", "DOE", "5000 YONGE ST", 2223334444L, 2222233333L, 22222333));
		expected.add(new Patient("TEST", "NAME", "ADDRESS", 1111144444, 1111144444, 11111444));
		
		result = listOfUsers.getAllPatientsList();
		assertEquals(expected, result);

	}	
	
	@Test 
	void testAddPatient2() {

		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", -1111144444, 1111144444, 11111444));
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, -1111144444, 11111444));
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, 1111144444, -11111444));
		
	}
	@Test 
	void testAddPatient3() {

		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 111114444, 1111144444, 11111444));
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, 111114444, 11111444));	
	}
	@Test 
	void testAddPatient4() {
		
		//UserStub stub = new UserStub();
		//listOfUsers.set_userDAO(stub);
	
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 11111444444L, 1111144444, 11111444));
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, 11111444444L, 11111444));	
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
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
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
		expected.add(new Patient("JANE", "DOE", "5000 YONGE ST", 2223334444L, 2222233333L, 22222333));
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
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
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
			e.printStackTrace();
		}
		assertEquals(expected, result);
	}
	@Test
	void testSearchPatientByName5() {

		assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", ""));
	}
	
	
}
