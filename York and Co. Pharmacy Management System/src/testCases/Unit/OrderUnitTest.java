package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.UserData.UserStub;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Users.*;
import middleLayer.Orders.*;

class OrderUnitTest {
//done
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
	void testOrderIntIntInt() {
		//fail("Not yet implemented");
        Inventory inv = Inventory.getInstance();
        MerchandiseStub mStub = new MerchandiseStub();
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub uStub = new UserStub();
		inv.set_merDAO(mStub);
		patients.set_userDAO(uStub);
		assertThrows(Exception.class, () -> new Order(1, 1, -1));
		assertThrows(Exception.class, () -> new Order(5, 1, 1));
		assertThrows(Exception.class, () -> new Order(1, 0, 1));
	}


}
