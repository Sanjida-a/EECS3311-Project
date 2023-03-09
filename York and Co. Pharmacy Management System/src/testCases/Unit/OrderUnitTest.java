package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import middleLayer.Order;

import databaseDAO.MerchandiseStub;
import databaseDAO.UserStub;
import middleLayer.Inventory;
import middleLayer.ListOfPatients;

class OrderUnitTest {
//done
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
