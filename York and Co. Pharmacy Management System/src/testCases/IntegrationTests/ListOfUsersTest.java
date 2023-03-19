package testCases.IntegrationTests;

import databaseDAO.superDAO;
import databaseDAO.UserData.UserStub;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import middleLayer.Users.*;

import org.junit.platform.commons.annotation.Testable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class ListOfUsersTest {
	
	private static ListOfUsers listOfUsers;

	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work

			listOfUsers = ListOfUsers.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	// AIZA TO-DO: make all tests like this one and also add tests for HCnum and phoneNum not being 10 digits long
	@Test
    void searchFName(){
		
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
    void searchFNameFalse(){ 
		
        try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentName", "FirstName").toString());
		} catch (Exception e) {
		}
    }

	@Test
	void searchLName(){
		
		try {
			assertEquals("[First name: SMITH, Last name: JOHN, Address: 5324 YONGE ST, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", listOfUsers.searchPatientByName("John", "LastName").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void searchLNameFalse(){ 
		
		try {
			assertEquals("[]", listOfUsers.searchPatientByName("NonExistentName", "LastName").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    
    @Test
    void searchPatientByNameTestValidInput1()  {
		
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName("SMITH", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput2(){
	
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName("JOHN", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput3() {
		
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName("SMITH JOHN", "FullName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
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
    void searchPatientByNameTestValidInput4() {
		
    
		try {
			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "FirstName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTestValidInput5() {
		
    
		try {
			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "LastName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTestValidInput6() {
		
	
    	
		try {
			assertThrows(Exception.class, () -> listOfUsers.searchPatientByName("", "FullName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTestValidInput13()  {	
        
      
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName("SMITH Man", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }


    @Test
    void searchPatientByNameTestValidInput8()  {	
        
      
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName("SMITH ", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput9()  {	
        
       
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName(" SMITH", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestValidInput10()  {	
      
      
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName(" JOHN", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestValidInput11()  {	
       
       
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName("JOHN ", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }

    @Test
    void searchPatientByNameTestValidInput12()  {	
       
      
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = listOfUsers.searchPatientByName("SMITH, JOHN", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void addPatient() {
       
        ArrayList<Patient> listOfPat = listOfUsers.getAllPatientsList();
        
        long newHealthCardNum = 1111144444;
        for (int i = 0; i < listOfPat.size(); i++) {
        	if (listOfPat.get(i).getHealthCardNum() == newHealthCardNum) {
        		newHealthCardNum = -1; // patient already exists
        		break;
        	}
        }
        
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        
        if (newHealthCardNum != -1) {
        	Patient patient1 = new Patient("TEST", "MAN", "5334 YONGE ST", 1112224444, 1111144444, 11222012);
             
            
             comparator1.add(patient1);
             try {
            	 listOfUsers.addPatient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
     		} catch (Exception e) {
     			e.printStackTrace();
     		}

             
             ArrayList<Patient> result = null;
			try {
				result = listOfUsers.searchPatientByName("MAN", "LastName");
			} catch (Exception e) {
				e.printStackTrace();
			}

             assertEquals(comparator1.toString(), result.toString());
        }
        else {
        	try {
        		assertThrows(Exception.class, () -> listOfUsers.addPatient("TEST", "MAN", "5334 YONGE ST", 1112224444, 1111144444, 11222012));
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        }
       
    }
   
    @Test
    void addPatientInvalid() { // negative health card num
       
        long newHealthCardNum = -555;
      
        assertThrows(Exception.class, () -> listOfUsers.addPatient("another", "test", "123 fake street", 647, newHealthCardNum, 20221010));
       
    }
    
    @Test
    void addPatientInvalid2() { // negative phone number
        assertThrows(Exception.class, () -> listOfUsers.addPatient("New", "Test", "123 Fake Street", -416, 123789, 11222012));
    }

    @Test
    void modifyPatientInfo() {
       
       
        JTextField fname = new JTextField();
        fname.setText("jo");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("");
        address.setText("");
        ArrayList<Patient> originalList = listOfUsers.getAllPatientsList();
        try {
        	listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
            ArrayList<Patient> newList = listOfUsers.getAllPatientsList();
            assertEquals("JO", newList.get(0).getFirstName());
            
            //back to normal
            fname.setText("Smith");
            listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
        }
        catch (Exception e) {
        	
        }
        
    }
    
    @Test
    void modifyPatientInfoInvalid() { // negative phoneNum
       
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("-555555");
        address.setText("");
        ArrayList<Patient> originalList = listOfUsers.getAllPatientsList();
        
        assertThrows(Exception.class, () -> listOfUsers.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
       

    }
    
}
