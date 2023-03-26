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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class ListOfUsersTest {
	
	private static ListOfUsers listOfUsers;

//	private static superDAO _DAO = null;
	private static Connection con;
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			con = superDAO.getCon();
			listOfUsers = ListOfUsers.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	//AIZA TO DO: test addPatient for exceptions only!!
	// AIZA TO DO: check/fix modifyPatientDetails and add more cuz of exceptions
	
	@Test
	void searchPatientWithIDTest1() { //always going to be at least 1 patient in system because of smith john
		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
		
		Patient chosenPatient = listOfPatients.get(0);
		long chosenID = listOfPatients.get(0).getHealthCardNum();
		
		Patient result = listOfUsers.searchPatientWithID(chosenID);
		assertEquals(chosenPatient.toString(), result.toString());
	}
	
//	@Test
//	void searchPatientWithIDTest2() {
//		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
//		
//		if (listOfPatients.size() > 1) {
//			Patient chosenPatient = listOfPatients.get(1);
//			long chosenID = listOfPatients.get(1).getHealthCardNum();
//			
//			
//			Patient result = listOfUsers.searchPatientWithID(chosenID);
//			assertEquals(chosenPatient.toString(), result.toString());
//		}
//	
//	}
	
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

        else { //health card ID already exist (for second time the test is run since Test Man patient with same ID already exists)
    		assertThrows(SQLException.class, () -> listOfUsers.addPatient("TEST", "MAN", "5334 YONGE ST", 1112224444, 9999999999L, 11222012));
        }
       
    }
   
    @Test
    void addPatientTestInvalid1() { // negative health card num
       
        long newHealthCardNum = -555;
        
        String expectedMsg = "Health card number must be a positive number";
        
        String resultMsg = null;
      
        try {
        	listOfUsers.addPatient("another", "test", "123 fake street", 647, newHealthCardNum, 20221010);
        }
        catch (NegativeInputException e) { //expected to throw this exception
        	resultMsg = e.getMessage();
        }
        catch (Exception e) {
        	fail();
        }
        
        assertEquals(expectedMsg, resultMsg);   
    }
    
    @Test
    void addPatientTestInvalid2() { // negative phone number
    	 long phoneNum = -4164164169L;
         
         String expectedMsg = "Phone number must be a positive number";
         
         String resultMsg = null;
       
         try {
        	listOfUsers.addPatient("New", "Test", "123 Fake Street", phoneNum, 123789, 11222012);
         }
         catch (NegativeInputException e) { //expected to throw this exception
         	resultMsg = e.getMessage();
         }
         catch (Exception e) {
         	fail();
         }
         
         assertEquals(expectedMsg, resultMsg);   
    }
    
    
    @Test
    void addPatientTestInvalid3() { // less than 10 digit Health card num
    	long healthCardNum = 2;
         
    	String expectedMsg = "Health Card Number must be a 10 digit number";
         
        String resultMsg = null;
       
        try {
        	listOfUsers.addPatient("New", "Test", "123 Fake Street", 4164161234L, healthCardNum, 11222012);
        }
        catch (Exception e) { // expected to throw
        	resultMsg = e.getMessage();
        }
         
        assertEquals(expectedMsg, resultMsg);  
    }
    
    @Test
    void addPatientTestInvalid4() { // less than 10 digit phone num
    	long phoneNum = 416;
         
    	String expectedMsg = "Phone Number must be a 10 digit number";
         
        String resultMsg = null;
       
        try {
        	listOfUsers.addPatient("New", "Test", "123 Fake Street", phoneNum, 1234567890, 11222012);
        }
        catch (Exception e) { // expected to throw
        	resultMsg = e.getMessage();
        }
         
        assertEquals(expectedMsg, resultMsg);  
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
        catch (Exception e) { // no exception expected
        	fail();
        }
        
    }
    
    @Test
    void modifyPatientDetailsInvalid1() { // patient doesn't exist (with ID 1)
        JTextField fname = new JTextField();
        fname.setText("test");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("");
        address.setText("");
        
        String expectedMsg = "Unsuccessful";
        
        String resultMsg = null;
        
        try {
        	listOfUsers.modifyPatientDetails(1, fname, lname, phoneNum, address);
        }
        catch (Exception e) {
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
      
    }
    
    @Test
    void modifyPatientDetailsInvalid2() { // all Jtextfields empty (nothing to be modified)
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("");
        address.setText("");
        
        String expectedMsg = "One of fName, lName, PhoneNum is required. Please fill in at least one of them.";
        
        String resultMsg = null;
        
        try {
        	listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
        }
        catch (Exception e) {
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test
    void modifyPatientDetailsInvalid3() { // negative phoneNum
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("-1234567890");
        address.setText("");
        
        assertThrows(NegativeInputException.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
      
    }
    
    @Test
    void modifyPatientDetailsInvalid4() { // less than 10 digit phoneNum
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("416");
        address.setText("");
        
        String expectedMsg = "Phone Number must be a 10 digit number";
        String resultMsg = null;
        
        try {
        	listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
        }
        catch (Exception e) {
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
    }
    
//	@Test (don't need since have sql replacement below)
//    void searchFNameTest1(){
//		
//		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
//		ArrayList<Patient> answer = new ArrayList<Patient>();
//		
//		for (Patient p: listOfPatients) {
//			if (p.getFirstName().compareToIgnoreCase("SMITH") == 0) {
//				answer.add(p);
//			}
//		}
//		
//		ArrayList<Patient> methodResult = null;
//		try {
//			methodResult = listOfUsers.searchPatientByName("Smith", "FirstName");
//		} catch (Exception e1) {
//		}
//
//		assertEquals(answer.size(), methodResult.size());
//		
//		for (int i = 0; i < answer.size(); i++) {
//			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
//		}
//    }

	@Test
    public void searchFNameTest1(){
		
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Patient WHERE firstName = 'Smith';";
			Statement statement = con.createStatement();
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

		} catch (Exception e) { //not expecting exception
			fail();
		}
		
		ArrayList<Patient> methodResult = null;
		try {
			methodResult = listOfUsers.searchPatientByName("Smith", "FirstName");
		} catch (Exception e1) {
		}

		assertEquals(answer.size(), methodResult.size());
		
//		for (int i = 0; i < answer.size(); i++) {
//			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
//		}
		assertEquals(answer, methodResult); //QUESTION: do we need for loop above or no?
		
    }
	
    @Test
    void searchFNameTest2(){ // first name doesn't exist (assuming no patient with name NonExistentName)
		
        try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentName", "FirstName").toString());
		} catch (Exception e) {  //not expecting exception
			fail();
		}
    }

//	@Test (don't need since have sql replacement below)
//	void searchLNameTest1(){
//		
//		ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
//		ArrayList<Patient> answer = new ArrayList<Patient>();
//		
//		for (Patient p: listOfPatients) {
//			if (p.getLastName().compareToIgnoreCase("John") == 0) {
//				answer.add(p);
//			}
//		}
//		
//		ArrayList<Patient> methodResult = null;
//		try {
//			methodResult = listOfUsers.searchPatientByName("John", "LastName");
//		} catch (Exception e1) {
//		}
//		
//		assertEquals(answer.size(), methodResult.size());
//		
//		for (int i = 0; i < answer.size(); i++) {
//			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
//		}
//	}
	
	@Test
    public void searchLNameTest1(){
		
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Patient WHERE lastName = 'John';";
			Statement statement = con.createStatement();
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

		} catch (Exception e) { // no exception expected
			fail();
		}
		
		ArrayList<Patient> methodResult = null;
		try {
			methodResult = listOfUsers.searchPatientByName("John", "LastName");
		} catch (Exception e1) {
		}

		assertEquals(answer.size(), methodResult.size());
		
//		for (int i = 0; i < answer.size(); i++) {
//			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
//		}
		assertEquals(answer, methodResult); //QUESTION: do we need for loop above or no?
		
    }

	@Test
	void searchLNameTest2(){ // first name doesn't exist (assuming no patient with name NonExistentName)
		
		try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentName", "LastName").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
//    @Test (don't need since have sql replacement below)
//    void searchFullNameTest1() { 
//		
//    	ArrayList<Patient> listOfPatients = listOfUsers.getAllPatientsList();
//		ArrayList<Patient> answer = new ArrayList<Patient>();
//		
//		for (Patient p: listOfPatients) {
//			if ((p.getFirstName().compareToIgnoreCase("Smith") == 0) && (p.getLastName().compareToIgnoreCase("John") == 0)) {
//				answer.add(p);
//			}
//		}
//		
//		ArrayList<Patient> methodResult = null;
//		try {
//			methodResult = listOfUsers.searchPatientByName("SMITH JOHN", "FullName");
//		} catch (Exception e1) {
//		}
//		
//		assertEquals(answer.size(), methodResult.size());
//		
//		for (int i = 0; i < answer.size(); i++) {
//			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
//		}
//    }
    
    @Test 
    public void searchFullNameTest1(){
		
		ArrayList<Patient> answer = new ArrayList<Patient>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Patient WHERE firstName = 'Smith' AND lastName = 'John';";
			Statement statement = con.createStatement();
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
			methodResult = listOfUsers.searchPatientByName("SMITH JOHN", "FullName");
		} catch (Exception e1) {
		}
		
		assertEquals(answer.size(), methodResult.size());
		
//		for (int i = 0; i < answer.size(); i++) {
//			assertEquals(answer.get(i).toString(), methodResult.get(i).toString());
//		}
		assertEquals(answer, methodResult); //QUESTION: do we need for loop above or no?
		
    }
    
    @Test
    void searchFullNameTest2() { // first and last name doesn't exist (assuming no patient with name NonExistentFirst NonExistentLast)
		
    	try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentFirst NonExistentLast", "FullName").toString());
		} catch (Exception e) { //exception not expected
			fail();
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
    
    // these two tests don't work anymore because empty string is checked in the front end
//    @Test
//    void searchPatientByNameTest1() { //empty string for patient name
//		try {
//			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "FirstName"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    }
//    
//    @Test
//    void searchPatientByNameTest2() {
//		try {
//			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "LastName"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    }
    
    @Test
    void searchPatientByNameTest3() { //exception expected bc need both first AND last name (only provided first name)
    	String expectedMsg = "Please enter both a first and last name to search by Full Name";
    	String resultMsg = null;
		try {
			listOfUsers.searchPatientByName("First", "FullName");
		} catch (Exception e) { //exception expected bc need both first AND last name
			resultMsg = e.getMessage();
		}
		
		assertEquals(expectedMsg, resultMsg);
    	
    }
    
    @Test
    void searchPatientByNameTest6() { //exception expected bc need both first AND last name (notice, also has a space)
    	String expectedMsg = "Please enter both a first and last name to search by Full Name";
    	String resultMsg = null;
		try {
			listOfUsers.searchPatientByName("First ", "FullName");
		} catch (Exception e) { //exception expected bc need both first AND last name
			resultMsg = e.getMessage();
		}
		
		assertEquals(expectedMsg, resultMsg);
    	
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
    void searchPatientByNameTest4NEWSQLTRY()  { //should ignore second word and ignore spacing
    	
    	ArrayList<Patient> answer = new ArrayList<Patient>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Patient WHERE firstName = 'Smith';";
			Statement statement = con.createStatement();
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
    void searchPatientByNameTest5NEWSQLTRY()  {	//should ignore spacing
    	
    	ArrayList<Patient> answer = new ArrayList<Patient>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Patient WHERE lastName = 'John';";
			Statement statement = con.createStatement();
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
    
//    test below not needed because same as just reading from query (--> would be same thing as what method in logic layer does)
//    @Test
//    void specificPatientDetailsTest1() {
//    	
//    	long specificHealthCard = 1111122222;
//    	Patient p = listOfUsers.searchPatientWithID(1111122222);
//    	
//    	try {
//			ArrayList<String> result = listOfUsers.specificPatientDetails(specificHealthCard, USER.OWNER);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
   
}
