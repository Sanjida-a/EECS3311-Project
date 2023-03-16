package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import databaseDAO.UserData.UserStub;
import middleLayer.Users.*;

class ListOfPatientsUnitTest {
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Test
	void testUpdatePatientListFromDatabase() {

		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		patients.updatePatientListFromDatabase();
		assertEquals(patients.getAllPatientsList(), stub.patientList);
	}

	@Test
	void testSearchPatientWithID() {
	
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		Patient result;
		Patient expected;
		result = patients.searchPatientWithID(1111122222);
		expected = stub.patientList.get(0);
		assertEquals(result, expected);
		result = patients.searchPatientWithID(99);
		assertEquals(result, null);
	}

	@Test
	void testModifyPatientDetails1() {
	
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		
		assertThrows(Exception.class, () -> patients.modifyPatientDetails(99, null, null, null, null));

		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		fname.setText("");
		lname.setText("");
		phoneNum.setText("");
		address.setText("");

		assertThrows(Exception.class, () -> patients.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
	}
	
	@Test
	void testModifyPatientDetails2() {
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		String result = new String();
		fname.setText("fname");
		try {
			fname.setText("Smith");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = patients.getAllPatientsList().get(0).getFirstName();
			assertEquals(result, "SMITH");
			
			lname.setText("John");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = patients.getAllPatientsList().get(0).getLastName();
			assertEquals(result, "JOHN");
			
			phoneNum.setText("1112223333");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = Long.toString( patients.getAllPatientsList().get(0).getPhoneNum());
			assertEquals(result, "1112223333");
			
			address.setText("5324 yonge St");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = patients.getAllPatientsList().get(0).getAddress();
			assertEquals(result, "5324 YONGE ST");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
