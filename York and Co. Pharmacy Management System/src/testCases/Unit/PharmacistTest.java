package testCases.Unit;
import org.junit.jupiter.api.Test;
import middleLayer.Pharmacist;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import middleLayer.ListOfPatients;


import middleLayer.Patient;

/*to be tested:
 * ArrayList<Patient> searchPatientByName (String patientName, String typeOfSearch)
 * void addPatient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth)
 * 
 */

class PharmacistTest {
    private middleLayer.Patient patient1;
    @Test
    void getPharmacistUser() {
    }

    @Test
    void setPharmacistUser() {
    }

    @Test
    void addPatient() throws Exception {
        Pharmacist p = new Pharmacist(1234567890, 12345678);
        p.addPatient("John", "Doe", "12 Dreamville rd", 1357924680, 217896421, 20021010);
        assertEquals("[First name: John, Last name: Doe, Address: 12 Dreamville rd, Phone Number: 1357924680, Health Card: 217896421, Date of birth: 20021010\n]", p.searchPatientByName("John", "FirstName").toString());
    }

    @Test
    void searchPatientByName() {
        Pharmacist p = new Pharmacist(1234567890, 12345678);
        assertEquals("[First name: Smith, Last name: John, Address: 5324 Yonge St, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", p.searchPatientByName("Smith", "FirstName").toString());
    }
    void addPatient() {
        patient1 = new middleLayer.Patient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
        ListOfPatients val = ListOfPatients.getInstance();
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        comparator1.add(patient1);
        try {
			subject1.addPatient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        ArrayList<Patient> result = subject1.searchPatientByName("MAN", "LastName");

        assertEquals(comparator1, result);
    }
    
    @Test
    void searchPatientByNameTestValidInput1()  {	//dependency to the actual database cannot be removed. thus, cannot use stub data
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("SMITH", "FirstName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1, result);
    }
    
    @Test
    void searchPatientByNameTestValidInput2(){
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("JOHN", "LastName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1, result);
    }
    
    @Test
    void searchPatientByNameTestValidInput3() {
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("SMITH JOHN", "FullName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1, result);
    }
    
    @Test
    void searchPatientByNameTestValidInput4() {
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("", "FirstName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));	
    	assertNotEquals(comparator1, result);
    }
    
    @Test
    void searchPatientByNameTestInvalidInput1() {
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName(null, "FirstName");
    	comparator1.add(new Patient("Smith", "John", "5324 Yonge St", 1112223333, 1111122222, 11111222));
    	assertNotEquals(comparator1, result);    	
    }

   
}