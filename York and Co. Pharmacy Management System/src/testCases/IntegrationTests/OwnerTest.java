package testCases.IntegrationTests;


import org.junit.jupiter.api.Test;

import middleLayer.*;

import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

/* to be tested:
 * public Owner(int username, int password)	- sets its fields with parameters
 * public Owner getOwnerUser()				- returns the reference of itself
 * public void setOwnerUser(Owner ownerUser)
 * public ArrayList<Patient> searchPatientByName (String patientName, String typeOfSearch)
 * 
 */

class OwnerTest {

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
       assertEquals("[First name: Smith, Last name: John, Address: 5324 Yonge St, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", o.searchPatientByName("Smith", "FirstName").toString());
    }

    @Test
    void searchFNameFalse(){ //false
        Owner o = new Owner(0, 0);
        assertEquals("[]", o.searchPatientByName("John", "FirstName").toString());
    }

	@Test
	void searchLName(){
		Owner o = new Owner(0, 0);
		assertEquals("[First name: Smith, Last name: John, Address: 5324 Yonge St, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", o.searchPatientByName("John", "LastName").toString());
	}

	@Test
	void searchLNameFalse(){ //false
		Owner o = new Owner(0, 0);
		assertEquals("[]", o.searchPatientByName("Smith", "LastName").toString());
	}
//    @Test
//    void searchOTCMedicineByName() {
//        Owner val = new Owner(1111111111, 11111111);
//        assertEquals("[Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false\n" +
//                "]", val.searchOTCMedicineByName ("PILL1").toString());
//    }
//
//
//    @Test
//    void searchOTCMedicineByType() {
//        Owner val = new Owner(1111111111, 11111111);
//        assertEquals("[Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true\n" +
//                ", Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true\n" +
//                ", Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true\n" +
//                ", Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false\n" +
//                "]", val.searchOTCMedicineByType (MERCHANDISE_TYPE.COLD).toString());
//    }
    
    @Test
    void searchPatientByNameTestValidInput1()  {	//dependency to the actual database cannot be removed. thus, cannot use stub data
    	Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("SMITH", "FirstName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput2(){
    	Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("JOHN", "LastName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput3() {
    	Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("SMITH JOHN", "FullName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput4() {
    	Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("", "FirstName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertNotEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestInvalidInput1() {
    	Owner subject1 = new Owner(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName(null, "FirstName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));
    	assertNotEquals(comparator1.toString(), result.toString());
    }
    

    

}