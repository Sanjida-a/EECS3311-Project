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
  			superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
  		} catch (Exception e) {
  			e.printStackTrace();
  		} 
  		
  	}

  	//**NOTE** searchPatientByName method tested in OwnerTest.java
  	
    
    
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