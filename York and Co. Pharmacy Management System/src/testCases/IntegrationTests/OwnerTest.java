package testCases.IntegrationTests;


import databaseDAO.superDAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import middleLayer.*;
import middleLayer.Users.*;

import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;



class OwnerTest {
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	//**NOTE** AddPatient method tested in PharmacistTest.java
	
    @Test
    void getOwnerUserTest() {
		
    	Owner subject1 = new Owner(0, 0);
    	Owner subject2 = subject1.getOwnerUser();
    	assertEquals(subject1, subject2);
    }
    
    @Test 
    void constructorTest() {
		
    	Owner subject1 = new Owner(0, 0);
    	assertEquals(subject1.username, 0);
    	assertEquals(0, subject1.password);
    }
    
    @Test
    void setOwnerUserTest() {
		
    	Owner subject1 = new Owner(0, 0);
    	Owner subject2 = new Owner(1, 1);
    	subject1.setOwnerUser(subject2);
    	assertEquals(subject1.username, subject2.username);
    	assertEquals(subject1.password, subject2.password);
    }

    @Test
    void searchFName(){
		
        Owner o = new Owner(0, 0);
        try {
		assertEquals("First name: SMITH, Last name: JOHN, Address: 5324 YONGE ST, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n", o.searchPatientByName("Smith", "FirstName").get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Test
    void searchFNameFalse(){ 
		
        Owner o = new Owner(0, 0);
        try {
			assertEquals("[]", o.searchPatientByName("NonExistentName", "FirstName").toString());
		} catch (Exception e) {
		}
    }

	@Test
	void searchLName(){
		
		Owner o = new Owner(0, 0);
		try {
			assertEquals("[First name: SMITH, Last name: JOHN, Address: 5324 YONGE ST, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", o.searchPatientByName("John", "LastName").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void searchLNameFalse(){ 
		
		Owner o = new Owner(0, 0);
		try {
			assertEquals("[]", o.searchPatientByName("NonExistentName", "LastName").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    
    @Test
    void searchPatientByNameTestValidInput1()  {
		
		Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName("SMITH", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput2(){
		
    	Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName("JOHN", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput3() {
		
    	Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName("SMITH JOHN", "FullName");
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
		
    	Owner subject1 = new Owner(0, 0);
    	
		try {
			assertThrows(Exception.class, () -> subject1.searchPatientByName("", "FirstName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTestValidInput5() {
		
    	Owner subject1 = new Owner(0, 0);
    	
		try {
			assertThrows(Exception.class, () -> subject1.searchPatientByName("", "LastName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTestValidInput6() {
		
		
    	Owner subject1 = new Owner(0, 0);
    	
		try {
			assertThrows(Exception.class, () -> subject1.searchPatientByName("", "FullName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    void searchPatientByNameTestValidInput13()  {	
        
        Owner subject1 = new Owner(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName("SMITH Man", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }


    @Test
    void searchPatientByNameTestValidInput8()  {	
        
        Owner subject1 = new Owner(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName("SMITH ", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput9()  {	
        
        Owner subject1 = new Owner(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName(" SMITH", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestValidInput10()  {	
      
        Owner subject1 = new Owner(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName(" JOHN", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestValidInput11()  {	
       
        Owner subject1 = new Owner(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName("JOHN ", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }

    @Test
    void searchPatientByNameTestValidInput12()  {	
       
        Owner subject1 = new Owner(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = null;
		try {
			result = subject1.searchPatientByName("SMITH, JOHN", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    

    

}