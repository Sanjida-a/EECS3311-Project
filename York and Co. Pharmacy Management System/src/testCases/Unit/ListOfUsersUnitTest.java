package testCases.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import databaseDAO.UserData.UserStub;
import middleLayer.Users.ListOfUsers;
import middleLayer.Users.Patient;

public class ListOfUsersUnitTest {

private static ListOfUsers listOfUsers;
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			listOfUsers = ListOfUsers.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Test
	void testUpdatePatientListFromDatabase() {

		
		UserStub stub = new UserStub();
		listOfUsers.set_userDAO(stub);
		listOfUsers.updatePatientListFromDatabase();
		assertEquals(listOfUsers.getAllPatientsList(), stub.patientList);
	}
	
	@Test
	void testAddPatient1() {
		
		UserStub stub = new UserStub();
		listOfUsers.set_userDAO(stub);
		ArrayList<Patient> expected = new ArrayList<Patient>();
		ArrayList<Patient> result = new ArrayList<Patient>();
		try {
			listOfUsers.addPatient("test", "name", "address", 1111144444, 1111144444, 11111444);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		expected.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
		expected.add(new Patient("TEST", "NAME", "ADDRESS", 1111144444, 1111144444, 11111444));

		result = stub.getListOfAllPatients();
		assertEquals(expected.get(0), result.get(0));
		assertEquals(expected.get(1), result.get(1));

		
	}	
	
	@Test 
	void testAddPatient2() {
		
		UserStub stub = new UserStub();
		listOfUsers.set_userDAO(stub);
	
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", -1111144444, 1111144444, 11111444));
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, -1111144444, 11111444));
		assertThrows(Exception.class, () -> listOfUsers.addPatient("test", "name", "address", 1111144444, 1111144444, -11111444));
		
	}

	@Test
	void testSearchPatientWithID() {
	
		UserStub stub = new UserStub();
		listOfUsers.set_userDAO(stub);
		Patient result;
		Patient expected;
		result = listOfUsers.searchPatientWithID(1111122222);
		expected = stub.patientList.get(0);
		assertEquals(result, expected);
		result = listOfUsers.searchPatientWithID(99);
		assertEquals(result, null);
	}

	@Test
	void testModifyPatientDetails1() {
	
		UserStub stub = new UserStub();
		listOfUsers.set_userDAO(stub);
		
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
		
		UserStub stub = new UserStub();
		listOfUsers.set_userDAO(stub);
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
		
		UserStub stub = new UserStub();
		listOfUsers.set_userDAO(stub);
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		phoneNum.setText("-1112223333");
		assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
	}
	
}
