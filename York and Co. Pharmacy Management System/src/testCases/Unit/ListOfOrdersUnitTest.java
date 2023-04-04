package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.MerchandiseInventory.MERCHANDISE_FORM;
import middleLayer.MerchandiseInventory.MERCHANDISE_TYPE;
import middleLayer.MerchandiseInventory.Merchandise;
import middleLayer.Orders.*;
import presentation.USER;

import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.OrderData.OrderStub;
import databaseDAO.UserData.UserStub;

class ListOfOrdersUnitTest {
	public static ListOfOrders orders;
	public static OrderStub orderStub;
	public static MerchandiseStub merStub;
	public static UserStub userStub;
	
	@BeforeEach
	public void before() {
		try {
			
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
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;

		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		
		assertEquals("Quantity Bought Must Be Positive (at least 1)!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddOrderToDatabase3() {
		Order newOrder = new Order(5, 10, 1111122222, 5, 10.00, false);  
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Medication doesn't exist!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddOrderToDatabase4() {
		Order newOrder = new Order(5, 1, 1111122229, 5, 10.00, false);  
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Patient doesn't exist!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddOrderToDatabase5() {
		Order newOrder = new Order(5, 4, 1111122222, 5, 10.00, false);  
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Not an OTC! Use the \"Give Refill for a prescription\" button", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddOrderToDatabase6() {
		Order newOrder = new Order(5, 1, 1111122222, 5, 10.00, false);  
		String errorString = null;
		merStub.allInventoryStub.get(0).setQuantity(0);
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {

			
			errorString = e.getMessage();
		}

		assertEquals("Check quantity in stock for medication! Not enough!", errorString);
		
		merStub.allInventoryStub.get(0).setQuantity(3);
		errorString = null;
		try {
			orders.addOrderToDatabase(newOrder);

		} catch (Exception e) {

			
			errorString = e.getMessage();
		}
		assertEquals("Check quantity in stock for medication! Not enough!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddPresOrderToDb1() {
		Prescription pres = new Prescription(5, 3, 1111122222, 0);
		String errorString = null;
		ArrayList<Prescription> before = orders.getListofAllPres();
		ArrayList<Prescription> after;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Refills must be positive (at least 1)!", errorString);
		after = orders.getListofAllPres();
		assertEquals(before, after);

	}
	
	@Test
	void testAddPresOrderToDb2() {
		Prescription pres = new Prescription(5, 10, 1111122222, 5);
		String errorString = null;
		ArrayList<Prescription> before = orders.getListofAllPres();
		ArrayList<Prescription> after;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {

			errorString = e.getMessage();
			
		}
		assertEquals("Medication doesn't exist!", errorString);
		after = orders.getListofAllPres();
		assertEquals(before, after);
	}
	
	@Test
	void testAddPresOrderToDb3() {
		Prescription pres = new Prescription(5, 1, 1111122222, 5);
		String errorString = null;
		ArrayList<Prescription> before = orders.getListofAllPres();
		ArrayList<Prescription> after;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {

			errorString = e.getMessage();
			
		}
		assertEquals("Not an Rx! You can only add prescription forms for Rx Medications", errorString);
		after = orders.getListofAllPres();
		assertEquals(before, after);
	}
	
	@Test
	void testAddPresOrderToDb4() {
		Prescription pres = new Prescription(5, 3, 1111155555, 5);
		String errorString = null;
		ArrayList<Prescription> before = orders.getListofAllPres();
		ArrayList<Prescription> after;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {

			errorString = e.getMessage();
			
		}
		assertEquals("Patient doesn't exist!", errorString);
		after = orders.getListofAllPres();
		assertEquals(before, after);
	}
	
	@Test
	void testAddPresOrderToDb5() {
		Prescription pres = new Prescription(3, 6, 1111122222, 5);
		Merchandise merc4 = new Merchandise(6, "test", 7, 5.00, MERCHANDISE_TYPE.SINUS, MERCHANDISE_FORM.LIQUID, false, null, true);
		Inventory inv = Inventory.getInstance(merStub);
		
		try {
			inv.addToInventory(merc4);
			orders.addPresFormToDb(pres);
		} catch (Exception e) {

			fail();
		}
		assertEquals(pres, orders.getListofAllPres().get(2));
	}
	
	@Test
	void testAddPresOrderToDb6() {
		Prescription pres = new Prescription(3, 3, 2222233333L, 2);

		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {

			fail();
		}
		assertEquals(pres, orders.getListofAllPres().get(2));
		
	}
	
	@Test
	void testAddRefillToDatabase1() {
		Order order = new Order(5, 3, 1111122222, 1, 20.00, true);
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			fail();
		}
		assertEquals(order, orderStub.orderList.get(4));
	}
	
	@Test
	void testAddRefillToDatabase2() {
		Order order = new Order(5, 10, 1111122222, 1, 20.00, true);
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Medication doesn't exist!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddRefillToDatabase3() {
		Order order = new Order(5, 3, 1111122225, 1, 20.00, true);
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Patient doesn't exist!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddRefillToDatabase4() {
		Order order = new Order(5, 3, 1111122222, -1, 20.00, true);
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Quantity Bought Must Be Positive (at least 1)!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddRefillToDatabase5() {
		Order order = new Order(5, 3, 1111122222, 5, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			fail(e.getMessage());
		}
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("0 refills left!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase6() {
		Order order = new Order(5, 3, 1111122222, 11, 20.00, true);
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Not enough refills! Only have " + 5 +  " refills left!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testAddRefillToDatabase7() {
		Order order = new Order(5, 3, 1111122222, 11, 20.00, true);
		String errorString = null;
		ArrayList<Order> before = orders.getListofAllOrders();
		ArrayList<Order> after;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Not enough refills! Only have " + 5 +  " refills left!", errorString);
		after = orders.getListofAllOrders();
		assertEquals(before, after);
	}
	
	@Test
	void testSpecificPatientOrderHistory1() {
		ArrayList<Order> expected = orderStub.orderList;
		try {
			assertEquals(expected, orders.specificPatientOrderHistory(1111122222));
		} catch (Exception e) {

			fail();
		}
	}
	
	@Test
	void testSpecificPatientOrderHistory2() {
		String errorString = null;
		try {
			orders.specificPatientOrderHistory(111112222);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Please enter a valid 10-digit health card number", errorString);
	}
	@Test
	void testSpecificPatientOrderHistory3() {
		String errorString = null;
		try {
			orders.specificPatientOrderHistory(11111222222L);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Please enter a valid 10-digit health card number", errorString);
	}
	@Test
	void testSpecificPatientOrderHistory4() {
		String errorString = null;
		try {
			orders.specificPatientOrderHistory(1111122223);
		} catch (Exception e) {

			errorString = e.getMessage();
		}
		assertEquals("Patient doesn't exist!", errorString);
	}
	
	@Test
	void testOutputOrderHistoryDetails1() {
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);   
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		ArrayList<Order> expectedOrders = new ArrayList<Order>();
		
		expectedOrders.add(order1);
		expectedOrders.add(order2);
		expectedOrders.add(order3);
		expectedOrders.add(order4);

		ArrayList<String> expectedString = new ArrayList<String>();
		expectedString.add("ORDER HISTORY OF PATIENT WITH HEALTH CARD NUMBER " + 1111122222 + "\n\n");
		
		int orderNum = 1;
		Merchandise associatedMedication = null;
		for (Order o : expectedOrders) {
			String oneFullOrder = "";
			oneFullOrder += "ORDER #" + orderNum + "\n";
			oneFullOrder += "Medication ID: " + o.getMedicationID() + "\nQuantity bought: " + o.getQuantityBought() + "\nTotal price: " + o.getTotalPriceOfOrder(); 
			associatedMedication = orderStub.medications.get(o.getMedicationID()-1);
			String OTCorRx = "Rx";
			if (associatedMedication.getisOTC()) {
				OTCorRx = "OTC";
			}
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\nOTCorRx: " + OTCorRx + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			expectedString.add(oneFullOrder);
			orderNum++;
		}
		try {
			assertEquals(expectedString, orders.outputOrderHistoryDetails(1111122222, USER.OWNER));
			assertEquals(expectedString, orders.outputOrderHistoryDetails(1111122222, USER.PHARMACIST));
		} catch (Exception e) {

			fail();
		}
		
	}
	
	@Test
	void testOutputOrderHistoryDetails2() {
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);   
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		ArrayList<Order> expectedOrders = new ArrayList<Order>();
		
		expectedOrders.add(order1);
		expectedOrders.add(order2);
		expectedOrders.add(order3);
		expectedOrders.add(order4);

		ArrayList<String> expectedString = new ArrayList<String>();
		expectedString.add("YOUR ORDERS:\n\n");
		
		int orderNum = 1;
		Merchandise associatedMedication = null;
		for (Order o : expectedOrders) {
			String oneFullOrder = "";
			oneFullOrder += "ORDER #" + orderNum + "\n";
			oneFullOrder += "Medication ID: " + o.getMedicationID() + "\nQuantity bought: " + o.getQuantityBought() + "\nTotal price: " + o.getTotalPriceOfOrder(); 
			associatedMedication = orderStub.medications.get(o.getMedicationID()-1);
			String OTCorRx = "Rx";
			if (associatedMedication.getisOTC()) {
				OTCorRx = "OTC";
			}
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\nOTCorRx: " + OTCorRx + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			expectedString.add(oneFullOrder);
			orderNum++;
		}
		try {
			assertEquals(expectedString, orders.outputOrderHistoryDetails(1111122222, USER.PATIENT));
		} catch (Exception e) {

			fail();
		}
		
	}
	@Test
	void testOutputOrderHistoryDetails3() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Patient with health card number " + 2222233333L + " has not made any orders.");
		try {
			assertEquals(expected, orders.outputOrderHistoryDetails(2222233333L, USER.OWNER));
		} catch (Exception e) {

			fail();
		}
	}
	@Test
	void testOutputOrderHistoryDetails4() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Patient with health card number " + 2222233333L + " has not made any orders.");
		try {
			assertEquals(expected, orders.outputOrderHistoryDetails(2222233333L, USER.PHARMACIST));
		} catch (Exception e) {

			fail();
		}
	}
	
	@Test
	void testOutputOrderHistoryDetails5() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("You have not made any orders.");
		try {
			assertEquals(expected, orders.outputOrderHistoryDetails(2222233333L, USER.PATIENT));
		} catch (Exception e) {

			fail();
		}
	}
	@Test
	void testSpecificPatientPres1() {
		ArrayList<Prescription> expected = new ArrayList<Prescription>();
		Prescription pres1 = new Prescription(1, 3, 1111122222, 10);
		Prescription pres2 = new Prescription(1, 4, 1111122222, 20);
		expected.add(pres1);
		expected.add(pres2);
		try {
			assertEquals(expected, orders.specificPatientPres(1111122222));
		} catch (Exception e) {

			fail();
		}
	}
	
	@Test 
	void testSpecificPatientPres2(){
		String result = null;
		try {
			orders.specificPatientPres(111112222);
		} catch (Exception e) {

			result = e.getMessage();
		}
		assertEquals( "Please enter a valid 10-digit health card number", result);
	}
	
	@Test 
	void testSpecificPatientPres3(){
		String result = null;
		try {
			orders.specificPatientPres(11111222222L);
		} catch (Exception e) {

			result = e.getMessage();
		}
		assertEquals( "Please enter a valid 10-digit health card number", result);
	}
	
	@Test 
	void testSpecificPatientPres4(){
		String result = null;
		try {
			orders.specificPatientPres(1111122223);
		} catch (Exception e) {

			result = e.getMessage();
		}
		assertEquals( "Patient doesn't exist!", result);
	}
	
	@Test
	void testSpecificPatientPres5() {
		ArrayList<Prescription> expected = new ArrayList<Prescription>();
		ArrayList<Prescription> result = null;
		try {
			result = orders.specificPatientPres(2222233333L);
		} catch (Exception e) {

			fail();
		}
		assertEquals(expected, result);
		
		
	}
	
	@Test
	void testOutputPresRefill1() {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> expected = new ArrayList<String>();
		ArrayList<Prescription> prescriptionList = new ArrayList<Prescription>();
		Prescription pres1 = new Prescription(1, 3, 1111122222, 10);
		Prescription pres2 = new Prescription(1, 4, 1111122222, 20);
		prescriptionList.add(pres1);
		prescriptionList.add(pres2);
		int presNum = 1;
		Merchandise associatedMedication = null;
		Inventory inv = Inventory.getInstance(merStub);
		expected.add("PRESCRIPTION FORM HISTORY OF PATIENT WITH HEALTH CARD NUMBER " + 1111122222 + "\n\n");
		for (Prescription p : prescriptionList) {
			String oneFullOrder = "";
			oneFullOrder += "PRESCRIPTION FORM #" + presNum + "\n";
			oneFullOrder += "Medication ID: " + p.getMedicationID() + "\n" + "Number of Refills Left: " + orderStub.numOfRemainingRefills(1111122222, p.getMedicationID()); 
			associatedMedication = inv.searchAllValidAndInvalidMerchandiseWithID(p.getMedicationID());
			
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			expected.add(oneFullOrder);
			presNum++;
		}
		try {
			result = orders.outputPresRefill(1111122222, USER.OWNER);
		} catch (Exception e) {

			fail();
		}
		assertEquals(expected, result);
		try {
			result = orders.outputPresRefill(1111122222, USER.PHARMACIST);
		} catch (Exception e) {

			fail();
		}
		assertEquals(expected, result);
	}
	
	@Test
	void testOutputPresRefill2() {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> expected = new ArrayList<String>();
		ArrayList<Prescription> prescriptionList = new ArrayList<Prescription>();
		Prescription pres1 = new Prescription(1, 3, 1111122222, 10);
		Prescription pres2 = new Prescription(1, 4, 1111122222, 20);
		prescriptionList.add(pres1);
		prescriptionList.add(pres2);
		int presNum = 1;
		Merchandise associatedMedication = null;
		Inventory inv = Inventory.getInstance(merStub);
		expected.add("YOUR PRESCRIPTION FORMS:\n\n");
		for (Prescription p : prescriptionList) {
			String oneFullOrder = "";
			oneFullOrder += "PRESCRIPTION FORM #" + presNum + "\n";
			oneFullOrder += "Medication ID: " + p.getMedicationID() + "\n" + "Number of Refills Left: " + orderStub.numOfRemainingRefills(1111122222, p.getMedicationID()); 
			associatedMedication = inv.searchAllValidAndInvalidMerchandiseWithID(p.getMedicationID());
			
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			expected.add(oneFullOrder);
			presNum++;
		}
		try {
			result = orders.outputPresRefill(1111122222, USER.PATIENT);
		} catch (Exception e) {

			fail();
		}
		assertEquals(expected, result);

	}
	
	@Test
	void testOutputPresRefill3() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Patient with health card number " + 2222233333L + " does not have any prescription forms in the system.");
		ArrayList<String> result = new ArrayList<String>();
		try {
			result = orders.outputPresRefill(2222233333L, USER.OWNER);
		} catch (Exception e) {

			fail();
		}
		assertEquals(expected, result);
		try {
			result = orders.outputPresRefill(2222233333L, USER.PHARMACIST);
		} catch (Exception e) {

			fail();
		}
		assertEquals(expected, result);
	}
	@Test
	void testOutputPresRefill4() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("You have not entered any prescription forms into the system.");
		ArrayList<String> result = new ArrayList<String>();
		try {
			result = orders.outputPresRefill(2222233333L, USER.PATIENT);
		} catch (Exception e) {

			fail();
		}
		assertEquals(expected, result);

	}
	
	@Test 
	void testSpecificPatientMoneySpent1() {
		try {
			assertEquals(70.0, orders.specificPatientMoneySpent(1111122222), 0.001);
		} catch (Exception e) {

			fail();
		}
	}
	@Test 
	void testSpecificPatientMoneySpent2() {
		try {
			assertEquals(0.0, orders.specificPatientMoneySpent(2222233333L), 0.001);
		} catch (Exception e) {

			fail();
		}
	}
	
	


}
