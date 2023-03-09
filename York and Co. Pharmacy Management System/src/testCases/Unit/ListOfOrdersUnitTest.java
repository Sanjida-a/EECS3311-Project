package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import middleLayer.ListOfOrders;
import middleLayer.Order;
import databaseDAO.OrderRoot;
import databaseDAO.OrderStub;
//done
class ListOfOrdersUnitTest {

	@Test
	void testUpdateOrderListFromDatabase() {
		//fail("Not yet implemented");
		ListOfOrders orders = ListOfOrders.getInstance();
		ArrayList<Order> expected = new ArrayList<Order>();
		ArrayList<Order> result = new ArrayList<Order>();
		OrderStub stub = new OrderStub();
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);   //does it match med and patient?
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		expected.add(order1);
		expected.add(order2);
		expected.add(order3);
		expected.add(order4);
		orders.setOrderDAO(stub);
		orders.updateOrderListFromDatabase();
		result = orders.getListofAllOrders();
		assertEquals(expected, result);
		/*for(int i = 0; i < result.size(); i ++) {
			assertEquals();
		}*/
		
	}

}
