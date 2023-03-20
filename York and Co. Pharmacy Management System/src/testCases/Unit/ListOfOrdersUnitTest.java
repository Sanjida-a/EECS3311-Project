package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import middleLayer.Orders.*;
import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.OrderData.OrderStub;
import databaseDAO.UserData.UserStub;

class ListOfOrdersUnitTest {
	public static ListOfOrders orders;
	public static OrderStub orderStub;
	public static MerchandiseStub merStub;
	public static UserStub userStub;
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			//superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
			orderStub = new OrderStub();
			merStub = new MerchandiseStub();
			userStub = new UserStub();
			orders = ListOfOrders.getInstance(orderStub, merStub, userStub);
			orders.setOrderDAO(orderStub, merStub, userStub);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Test
	void testUpdateOrderListFromDatabase() {
		//orders = ListOfOrders.getInstance();
		//orders.setOrderDAO(new OrderStub());
		//orders.updateOrderListFromDatabase();
		ArrayList<Order> expected = new ArrayList<Order>();
		ArrayList<Order> result = new ArrayList<Order>();

		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);  
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		expected.add(order1);
		expected.add(order2);
		expected.add(order3);
		expected.add(order4);


		result = orders.getListofAllOrders();
		assertEquals(expected, result);

		
	}
	
	@Test
	void testAddOrderToDatabase1() {
		//orders = ListOfOrders.getInstance();
		//orders.setOrderDAO(new OrderStub());
		//orders.updateOrderListFromDatabase();
		ArrayList<Order> expected = new ArrayList<Order>();
		ArrayList<Order> result = new ArrayList<Order>();

		Order newOrder = new Order(5, 1, 1111122222, 5, 10.00, false);  
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);  
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		expected.add(order1);
		expected.add(order2);
		expected.add(order3);
		expected.add(order4);
		expected.add(newOrder);
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		result = orders.getListofAllOrders();
		assertEquals(expected, result);
	}
	
	@Test
	void testAddOrderToDatabase2() {

		Order newOrder = new Order(1, 1, 1111122222, 5, 10.00, true);  


		assertThrows(Exception.class, () -> orders.addOrderToDatabase(newOrder));
	}
	@Test
	void testAddOrderToDatabase3() {
		Order newOrder = new Order(5, 1, 1111122222, 5, 10.00, false);  

		orderStub.medications.get(0).setQuantity(0);
		//orders.setOrderDAO(orderStub);
		assertThrows(Exception.class, () -> orders.addOrderToDatabase(newOrder));
	}
	@Test
	void testAddOrderToDatabase4() {
		Order newOrder = new Order(5, 1, 1111122222, 5, 10.00, false);  

		orderStub.medications.get(0).setIsValid(false);
		//orders.setOrderDAO(orderStub);
		assertThrows(Exception.class, () -> orders.addOrderToDatabase(newOrder));
	}
	@Test
	void testAddOrderToDatabase5() {
		Order newOrder = new Order(5, 1, 1111122222, 5, 10.00, false);  

		orderStub.medications.get(0).setQuantity(3);
		//orders.setOrderDAO(orderStub);
		assertThrows(Exception.class, () -> orders.addOrderToDatabase(newOrder));
	}
	
	/*@Test
	void testAddPresOrderToDb1() {


		
	}*/


}
