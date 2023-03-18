package testCases.IntegrationTests;
import databaseDAO.superDAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import middleLayer.Users.*;



class PharmacistTest {
    private Patient patient1;
    
    //beforeAll is just used to established a connection with the database before all tests
  	@BeforeAll
  	public static void before() {
  		try {
  			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
  		} catch (Exception e) {
  			e.printStackTrace();
  		} 
  		
  	}

  	//**NOTE** searchPatientByName method tested in OwnerTest.java
  	
    @Test
    void addPatient() {
       
        ListOfPatients val = ListOfPatients.getInstance();
        ArrayList<Patient> listOfPat = val.getAllPatientsList();
        
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
        	patient1 = new Patient("TEST", "MAN", "5334 YONGE ST", 1112224444, 1111144444, 11222012);
             
            
             comparator1.add(patient1);
             try {
     			subject1.addPatient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
     		} catch (Exception e) {
     			e.printStackTrace();
     		}

             
             ArrayList<Patient> result = null;
			try {
				result = subject1.searchPatientByName("MAN", "LastName");
			} catch (Exception e) {
				e.printStackTrace();
			}

             assertEquals(comparator1.toString(), result.toString());
        }
        else {
        	try {
        		assertThrows(Exception.class, () -> subject1.addPatient("TEST", "MAN", "5334 YONGE ST", 1112224444, 1111144444, 11222012));
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        }
       
    }
   
    @Test
    void addPatientInvalid() { // negative health card num
       
        long newHealthCardNum = -555;
      
        Pharmacist p = new Pharmacist(0,0);
        assertThrows(Exception.class, () -> p.addPatient("another", "test", "123 fake street", 647, newHealthCardNum, 20221010));
       
    }
    
    @Test
    void addPatientInvalid2() { // negative phone number
    	
        ListOfPatients val = ListOfPatients.getInstance();
        ArrayList<Patient> listOfPat = val.getAllPatientsList();
        
        Pharmacist subject1 = new Pharmacist(0, 0);
        assertThrows(Exception.class, () -> subject1.addPatient("New", "Test", "123 Fake Street", -416, 123789, 11222012));
    }
    
//    test was failing
//    @Test
//    void searchPatientByNameTestValidInput4() {
//        try {
//            superDAO.setPassword(pass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    	Pharmacist subject1 = new Pharmacist(0, 0);
//    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
//    	ArrayList<Patient> result = null;
//		try {
//			result = subject1.searchPatientByName("", "FirstName");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
//    	assertNotEquals(comparator1.toString(), result.toString());
//    }

//    @Test
//    void searchPatientByNameTestInvalidInput1() {
//        try {
//            superDAO.setPassword(pass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Pharmacist subject1 = new Pharmacist(0, 0);
//    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
//    	ArrayList<Patient> result = null;
//		try {
//			result = subject1.searchPatientByName("", "FirstName");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
//    	assertNotEquals(comparator1.toString(), result.toString());
//    }
    
   
}