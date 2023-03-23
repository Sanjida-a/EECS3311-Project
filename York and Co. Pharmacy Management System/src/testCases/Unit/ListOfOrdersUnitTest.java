package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
	@BeforeEach
	public void before() {
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
			System.out.println(e.getMessage());
			fail(e.getMessage());
		}
		result = orders.getListofAllOrders();
		assertEquals(expected, result);
	}
	
	@Test
	void testAddOrderToDatabase2() {

		Order newOrder = new Order(5, 1, 1111122222, -1, 10.00, true);  
		String errorString = null;

		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		
		assertEquals("Quantity Bought Must Be Positive!", errorString);
	}
	
	@Test
	void testAddOrderToDatabase3() {
		Order newOrder = new Order(5, 10, 1111122222, 5, 10.00, false);  
		String errorString = null;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Medication doesn't exist!", errorString);
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
	
	@Test
	void testAddPresOrderToDb1() {
		Prescription pres = new Prescription(5, 3, 1111122222, 0);
		String errorString = null;
		try {
			orders.addPresOrderToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Refills must be positive", errorString);
		
	}
	
	@Test
	void testAddPresOrderToDb2() {
		Prescription pres = new Prescription(5, 10, 1111122222, 5);
		String errorString = null;
		try {
			orders.addPresOrderToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
			
		}
		assertEquals("Medication doesn't exist!", errorString);
	}
	
	@Test
	void testAddPresOrderToDb3() {
		Prescription pres = new Prescription(5, 1, 1111122222, 5);
		String errorString = null;
		try {
			orders.addPresOrderToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
			
		}
		assertEquals("Not an Rx!", errorString);
	}
	
	@Test
	void testAddPresOrderToDb4() {
		Prescription pres = new Prescription(5, 3, 1111155555, 5);
		String errorString = null;
		try {
			orders.addPresOrderToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
			
		}
		assertEquals("Patient doesn't exist!", errorString);
	}
	
	@Test
	void testAddPresOrderToDb5() {
		Prescription pres = new Prescription(3, 3, 1111122222, 5);

		try {
			orders.addPresOrderToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(pres, orderStub.prescriptionList.get(2));
	}
	
	@Test
	void testAddRefillToDatabase1() {
		Order order = new Order(5, 3, 1111122222, 1, 20.00, true);
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(order, orderStub.orderList.get(4));
	}
	
	@Test
	void testAddRefillToDatabase2() {
		Order order = new Order(5, 10, 1111122222, 1, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("No record found of prescription!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase3() {
		Order order = new Order(5, 3, 1111122225, 1, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("No record found of prescription!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase4() {
		Order order = new Order(5, 3, 1111122222, -1, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Quantity Bought Must Be Positive!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase5() {
		Order order = new Order(5, 3, 1111122222, 8, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("No refills left!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase6() {
		Order order = new Order(5, 3, 1111122222, 11, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Only have " + 10 +  " refills left!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase7() {
		Order order = new Order(5, 3, 1111122222, 11, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Only have " + 10 +  " refills left!", errorString);
	}
	
	


}
