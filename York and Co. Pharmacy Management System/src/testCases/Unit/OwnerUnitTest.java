package testCases.Unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseStub;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Users.*;
import presentation.USER;

class OwnerUnitTest {
	
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
	void testOwner() {
	
		Owner subject1 = new Owner(0,0);
		assertEquals(subject1.username, 0);
		assertEquals(subject1.password, 0);
	}

	@Test
	void testGetOwnerUser() {
		
		Owner subject1 = new Owner(0,0);
		assertEquals(subject1.getOwnerUser(), subject1);
	}

	@Test
	void testSetOwnerUser() {
		
		Owner subject1 = new Owner(0,0);
		Owner subject2 = new Owner(1,1);
		subject1.setOwnerUser(subject2);
		assertEquals(subject1.username, subject2.username);
		assertEquals(subject1.password, subject2.password);
	}

	
	@Test
	void testSearchPatientByName1() {
		
		Owner subject1 = new Owner(0,0);
		Inventory inv = Inventory.getInstance();
		inv.set_merDAO(new MerchandiseStub());
    	List<Patient> comparator1 = new ArrayList<Patient>();
    	List<Patient> result = null;
		try {
			result = subject1.searchPatientByName("SMITH", "FirstName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
		
	}
	
	@Test
	void testSearchPatientByName2() {
		Owner subject1 = new Owner(0,0);
		Inventory inv = Inventory.getInstance();
		inv.set_merDAO(new MerchandiseStub());
		
    	List<Patient> comparator1 = new ArrayList<Patient>();
    	List<Patient> result = null;
		try {
			result = subject1.searchPatientByName("JOHN", "LastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
	}
	
	@Test
	void testSearchPatientByName3() {
		Owner subject1 = new Owner(0,0);
		Inventory inv = Inventory.getInstance();
		inv.set_merDAO(new MerchandiseStub());
		List<Patient> comparator1 = new ArrayList<Patient>();
    	List<Patient> result = null;
		try {
			result = subject1.searchPatientByName("SMITH JOHN", "FullName");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
	}
	
	@Test
	void testSearchPatientByName4() {
		Owner subject1 = new Owner(0,0);
		Inventory inv = Inventory.getInstance();
		inv.set_merDAO(new MerchandiseStub());
		assertThrows(Exception.class, () -> subject1.searchPatientByName("", "FirstName"));
		assertThrows(Exception.class, () -> subject1.searchPatientByName("", "LastName"));
		assertThrows(Exception.class, () -> subject1.searchPatientByName("", "FulllName"));
		
	}
	
	/* the methods below are derived from User, but tested here
	 * since User is abstract class and the method behave the same 
	 * throughout the subclasses
	 */


	@Test
	void testSearchMedicineByName() {
		
		Owner subject1 = new Owner(0,0);
		Inventory inv = Inventory.getInstance();
		inv.set_merDAO(new MerchandiseStub());
		ArrayList<Merchandise> result = new ArrayList<Merchandise>();
		ArrayList<Merchandise> expected = new ArrayList<Merchandise>();
		result = subject1.searchMedicineByName("pill3", USER.OWNER);
		expected.add(inv.getMerchandise().get(2));
		assertEquals(result, expected);
		result = subject1.searchMedicineByName("pill1", USER.PATIENT);
		expected.clear();
		expected.add(inv.getMerchandise().get(0));
		assertEquals(result, expected);
		result = subject1.searchMedicineByName("random name", USER.OWNER);
		assertTrue(result.isEmpty());

	}

	@Test
	void testSearchMedicineByType() {
		
		Owner subject1 = new Owner(0,0);
		Inventory inv = Inventory.getInstance();
		inv.set_merDAO(new MerchandiseStub());
		ArrayList<Merchandise> result = new ArrayList<Merchandise>();
		ArrayList<Merchandise> expected = new ArrayList<Merchandise>();
		MERCHANDISE_TYPE type = MERCHANDISE_TYPE.COLD;
		result = subject1.searchMedicineByType(type, USER.OWNER);
		expected.add(inv.getMerchandise().get(0));
		assertEquals(result, expected);
		type = MERCHANDISE_TYPE.FEVER;
		result = subject1.searchMedicineByType(type, USER.PATIENT);
		assertTrue(result.isEmpty());
	}

}
