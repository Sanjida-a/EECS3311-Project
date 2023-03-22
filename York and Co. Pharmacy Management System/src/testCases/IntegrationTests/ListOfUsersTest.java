package testCases.IntegrationTests;

import databaseDAO.superDAO;
import databaseDAO.UserData.UserDAO;
import databaseDAO.UserData.UserRoot;
import databaseDAO.UserData.UserStub;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import middleLayer.Users.*;
import presentation.USER;

import org.junit.platform.commons.annotation.Testable;

import javax.swing.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class ListOfUsersTest {
	
	private static ListOfUsers listOfUsers;

//	private static superDAO _DAO = null;
	private static Connection conToDB;
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			conToDB = superDAO.getCon();
			listOfUsers = ListOfUsers.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
	void searchPatientWithIDTest1() {
		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		
		Patient chosenPatient = listOfPatients.get(0);
		long chosenID = listOfPatients.get(0).getHealthCardNum();
		
		Patient result = listOfUsers.searchPatientWithID(chosenID);
		assertEquals(chosenPatient.toString(), result.toString());
	}
	
	@Test
	void searchPatientWithIDTest2() {
		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		
		if (listOfPatients.size() > 1) {
			Patient chosenPatient = listOfPatients.get(1);
			long chosenID = listOfPatients.get(1).getHealthCardNum();
			
			
			Patient result = listOfUsers.searchPatientWithID(chosenID);
			assertEquals(chosenPatient.toString(), result.toString());
		}
	
	}
	
	@Test
	void searchPatientWithIDTest3() { // ID doesn't exist	
		Patient result = listOfUsers.searchPatientWithID(1); //patientID only 10 digits
	
		assertEquals(null, result);
	}
	
	@Test
    void addPatientTest1() {
       
        ArrayList<Patient> originalListOfPat = listOfUsers.getAllPatientsList();
        
        long newHealthCardNum = 9999999999L;
        for (int i = 0; i < originalListOfPat.size(); i++) {
        	if (originalListOfPat.get(i).getHealthCardNum() == newHealthCardNum) {
        		newHealthCardNum = -1; // patient already exists
        		break;
        	}
        }
        
        if (newHealthCardNum != -1) {
            try {
            	listOfUsers.addPatient("Test", "Man", "5334 yonge St", 1112224444, newHealthCardNum, 11222012);
        	} catch (Exception e) {
     			e.printStackTrace();
     		}
            
            ArrayList<Patient> newListOfPat = listOfUsers.getAllPatientsList();
            
            assertEquals(originalListOfPat.size()+1, newListOfPat.size());
        }

        else { //health card ID already exist
    		assertThrows(Exception.class, () -> listOfUsers.addPatient("TEST", "MAN", "5334 YONGE ST", 1112224444, 9999999999L, 11222012));
        	
        }
       
    }
   
    @Test
    void addPatientTest2() { // negative health card num
       
        long newHealthCardNum = -555;
      
        assertThrows(Exception.class, () -> listOfUsers.addPatient("another", "test", "123 fake street", 647, newHealthCardNum, 20221010));
       
    }
    
    @Test
    void addPatientTest3() { // less than 10 digit Health card num
        assertThrows(Exception.class, () -> listOfUsers.addPatient("New", "Test", "123 Fake Street", 4161112222L, 2, 11222012));
    }
    
    @Test
    void addPatientTest4() { // negative phone number
        assertThrows(Exception.class, () -> listOfUsers.addPatient("New", "Test", "123 Fake Street", -416, 123789, 11222012));
    }
    
    @Test
    void addPatientTest5() { // less than 10 digit phone number
        assertThrows(Exception.class, () -> listOfUsers.addPatient("New", "Test", "123 Fake Street", 416, 123789, 11222012));
    }
    
    @Test
    void modifyPatientDetailsTest1() {
    	
        JTextField fname = new JTextField();
        fname.setText("newFirstName");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("");
        address.setText("");
      
        try {
        	listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
            ArrayList<Patient> newList = listOfUsers.getAllPatientsList();
            
            int indexOfChangedPatient = -1;
        	for (int i = 0; i < newList.size(); i++) {
        		if (newList.get(i).getHealthCardNum() == 1111122222) {
        			indexOfChangedPatient = i;
        			break;
        		}
        	}
            
            assertEquals("NEWFIRSTNAME", newList.get(indexOfChangedPatient).getFirstName());
            
            //back to normal
            fname.setText("Smith");
            listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
        }
        catch (Exception e) {
        	
        }
        
    }
    
    @Test
    void modifyPatientDetailsTest2() { // negative phoneNum
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("-1234567890");
        address.setText("");
        
        assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
      
    }
    
    @Test
    void modifyPatientDetailsTest3() { // less than 10 digit phoneNum
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("1234");
        address.setText("");
        
        assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
      
    }
    
    @Test
    void modifyPatientDetailsTest4() { // all Jtextfields empty (nothing to be modified)
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("");
        address.setText("");
        
        assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
    }
	
	@Test
    void searchFNameTest1(){
		
		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		for (Patient p: listOfPatients) {
			if (p.getFirstName().compareToIgnoreCase("SMITH") == 0) {
				answer.add(p);
			}
		}
		
		ArrayList<Patient> methodResult = null;
		try {
			methodResult = listOfUsers.searchPatientByName("Smith", "FirstName");
		} catch (Exception e1) {
		}

		assertEquals(answer.size(), methodResult.size());
		
		for (int i = 0; i < answer.size(); i++) {
			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
		}
    }

	@Test
    public void searchFNameTest1NEWSQLTRY(){
		
		
		
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Patient where firstName = 'Smith';";
			Statement statement = conToDB.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int dateOfBirth;
			String firstName, lastName, address;
			long phoneNumber, healthCardNumber;
			
			Patient patient;
			
			while (result.next()) { 
				firstName =  result.getString("firstName").toUpperCase();
				lastName =  result.getString("lastName").toUpperCase();
				address =  result.getString("Address").toUpperCase();
				phoneNumber =  result.getLong("phoneNumber");
				healthCardNumber =  result.getLong("healthCardNumber");
				dateOfBirth =  result.getInt("dateOfBirth");
				
				patient = new Patient(firstName, lastName, address, phoneNumber, healthCardNumber, dateOfBirth);
				
				
				answer.add(patient);
			}

		} catch (Exception e) {
		}
		
		ArrayList<Patient> methodResult = null;
		try {
			methodResult = listOfUsers.searchPatientByName("Smith", "FirstName");
		} catch (Exception e1) {
		}

		assertEquals(answer.size(), methodResult.size());
		
		for (int i = 0; i < answer.size(); i++) {
			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
		}
		
    }
	
    @Test
    void searchFNameTest2(){ // first name doesn't exist (assuming no patient with name NonExistentName)
		
        try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentName", "FirstName").toString());
		} catch (Exception e) {
		}
    }

	@Test
	void searchLNameTest1(){
		
		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		for (Patient p: listOfPatients) {
			if (p.getLastName().compareToIgnoreCase("John") == 0) {
				answer.add(p);
			}
		}
		
		ArrayList<Patient> methodResult = null;
		try {
			methodResult = listOfUsers.searchPatientByName("John", "LastName");
		} catch (Exception e1) {
		}
		
		assertEquals(answer.size(), methodResult.size());
		
		for (int i = 0; i < answer.size(); i++) {
			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
		}
	}

	@Test
	void searchLNameTest2(){ // first name doesn't exist (assuming no patient with name NonExistentName)
		
		try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentName", "LastName").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @Test
    void searchFullNameTest1() { 
		
    	ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		for (Patient p: listOfPatients) {
			if ((p.getFirstName().compareToIgnoreCase("Smith") == 0) && (p.getLastName().compareToIgnoreCase("John") == 0)) {
				answer.add(p);
			}
		}
		
		ArrayList<Patient> methodResult = null;
		try {
			methodResult = listOfUsers.searchPatientByName("SMITH JOHN", "FullName");
		} catch (Exception e1) {
		}
		
		assertEquals(answer.size(), methodResult.size());
		
		for (int i = 0; i < answer.size(); i++) {
			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
		}
    }
    
    @Test
    void searchFullNameTest2() { // first and last name doesn't exist (assuming no patient with name NonExistentFirst NonExistentLast)
		
    	try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentFirst NonExistentLast", "FullName").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
//    @Test
//    void searchPatientByNameTestValidInput4() {
//		try {
//			superDAO.setPassword(pass);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	Owner subject1 = new Owner(0, 0);
//    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
//    	 result = null;
//		try {
//			ArrayList<Patient> result = subject1.searchPatientByName("", "FirstName");
//			
//			comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
//	    	assertNotEquals(comparator1.toString(), result.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    }
    
    @Test
    void searchPatientByNameTest1() { //empty string for patient name
		try {
			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "FirstName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTest2() {
		try {
			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "LastName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTest3() {
		try {
			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "FullName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTest4()  { //should ignore second word and ignore spacing
    	ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		for (Patient p: listOfPatients) {
			if (p.getFirstName().compareToIgnoreCase("SMITH") == 0) {
				answer.add(p);
			}
		}
		
		ArrayList<Patient> methodResult = null;
		ArrayList<Patient> methodResult2 = null;
		ArrayList<Patient> methodResult3 = null;
		try {
			methodResult = listOfUsers.searchPatientByName("SMITH Man", "FirstName");
			methodResult2 = listOfUsers.searchPatientByName("Smith ", "FirstName");
			methodResult3 = listOfUsers.searchPatientByName(" SMITH", "FirstName");
		} catch (Exception e1) {
		}

		assertEquals(answer.size(), methodResult.size());
		assertEquals(answer.size(), methodResult2.size());
		assertEquals(answer.size(), methodResult3.size());
		
		for (int i = 0; i < answer.size(); i++) {
			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
			assertEquals(answer.get(i).toString(), methodResult2.get(i).toString());
			assertEquals(answer.get(i).toString(), methodResult3.get(i).toString());
		}
    }

    @Test
    void searchPatientByNameTest5()  {	//should ignore spacing
    	
    	ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		for (Patient p: listOfPatients) {
			if (p.getLastName().compareToIgnoreCase("John") == 0) {
				answer.add(p);
			}
		}
		
		ArrayList<Patient> methodResult = null;
		ArrayList<Patient> methodResult2 = null;
		try {
			methodResult = listOfUsers.searchPatientByName(" JOHN", "LastName");
			methodResult2 = listOfUsers.searchPatientByName("JOHN ", "LastName");
			
		} catch (Exception e1) {
		}

		assertEquals(answer.size(), methodResult.size());
		assertEquals(answer.size(), methodResult2.size());
		
		for (int i = 0; i < answer.size(); i++) {
			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
			assertEquals(answer.get(i).toString(), methodResult2.get(i).toString());
		
		}
    }
    
    @Test
    void specificPatientDetailsTest1() {
    	
    	long specificHealthCard = 1111122222;
    	Patient p = listOfUsers.searchPatientWithID(1111122222);
    	
    	try {
			ArrayList<String> result = listOfUsers.specificPatientDetails(specificHealthCard, USER.OWNER);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
   
}
