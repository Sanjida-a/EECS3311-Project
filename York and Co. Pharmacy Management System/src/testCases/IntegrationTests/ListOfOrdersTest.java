package testCases.IntegrationTests;

import middleLayer.*;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;
import middleLayer.Users.ListOfUsers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListOfOrdersTest {
	
	private static ListOfOrders listOfOrders;
	private static Inventory inv;
	private static Connection con;
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			con = superDAO.getCon();
			listOfOrders = ListOfOrders.getInstance();
			inv = Inventory.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
// MINH To Do / TODO: I think stated all tests (at least 20) you should add in comments in these 5 lines below; try to organize the tests in same manner as methods found in ListOfOrders.java class
// MINH To Do / TODO: I added one VALID test for addOrderToDatabase, so please add 5 INVALID tests for addOrderToDatabase, one for each exception thrown (so should have tests for addOrderToDatabaseInvalid1...addOrderToDatabaseInvalid5)
//	for addOrderToDatabase tests, don't need to check return value (i think it's a minor thing)
//	MINH To Do / TODO: add 1 VALID test and 4 INVALID tests (for the 4 exceptions) for addPresFormToDb
// MINH To Do / TODO: I already added one INVALID test for addRefillToDatabase, so please add 1 VALID TEST (maybe add a fake prescription form into MySQL for john smith guy) and the remaining 7 INVALID tests, one for each of the remaining exceptions
// MINH To Do / TODO: add 2 INVALID tests for specificPatientPres for the two exceptions (don't do a test for "patient doesn't exist" exception)
    @Test 
    void addOrderToDatabaseTest() {
    	
        ArrayList<Merchandise> medList = inv.getMerchandise();
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        int MedIDWithQuantityMoreThan1 = -1;
        for (int i = 0; i < originalList.size(); i++) {
        	if (medList.get(i).getQuantity() > 1) {
        		MedIDWithQuantityMoreThan1 = i;
        		break;
        	}
        }
        
        try {
        	if (MedIDWithQuantityMoreThan1 != -1) {
        		Order o = new Order(medList.get(MedIDWithQuantityMoreThan1).getMedicationID(), 1111122222, 1);
//                Prescription p = new Prescription(medList.get(MedIDWithQuantityMoreThan1).getMedicationID(), 1111122222, 2);
//                val.addOrderToDatabase(O,p);
                listOfOrders.addOrderToDatabase(o);
           }
        } catch(Exception e) { // no exception expected
        	fail();
        }
        
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size()+1, newList.size());
    }
    
    /*@Test
    void orderConstructorTestInvalid() {
    	
        ListOfOrders val = ListOfOrders.getInstance();
        ArrayList<Order> originalList = val.getListofAllOrders();
        
        assertThrows(Exception.class, () -> new Order(100, 1111122222, 1)); // medID doesn't exist

    }
    
    @Test
    void orderConstructorTestInvalid2() {
    	
        assertThrows(Exception.class, () -> new Order(1, 7777, 1)); // patientID/healthcard doesn't exist
    }
    
    @Test
    void orderConstructorTestInvalid3()  {
    
        assertThrows(Exception.class, () -> new Order(1, 1111122222, -5)); // negative quantity
    }*/

    @Test
    void addRefillInvalid4() { // not an Rx medication
       
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        ArrayList<Merchandise> medList = inv.getMerchandise();

        String expectedMsg = "Not an Rx! Use the \"Add OTC Order\" button";
        String resultMsg = null;
        
        int indexForOTC = -1;
        for (int i = 0; i < originalList.size(); i++) {
        	if (medList.get(i).getisOTC() == true) {
        		indexForOTC = i;
        		break;
        	}
        }
        try {
        	 if (indexForOTC != -1) {
	        	Order O = new Order(medList.get(indexForOTC).getMedicationID(), 1111122222, 1);
        	     
		        listOfOrders.addRefillToDatabase(O);
        	 }
        } 
        catch (Exception e){ // exception expected because can't add a refill order for an OTC medication
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
       
    }
    
    // aiza added tests below for the methods she added in Itr3
//    @Test OLD TEST WITHOUT QUERYING - BAD I BELIVE
//    void specificPatientOrderHistoryTest1() { 
//    	
//    	long patientOfInterestID = 1111122222; //Smith John always exists and always has at least 1 order (from our Sql script)
//    	try {
//    		ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
//    		ArrayList<Order> answer = new ArrayList<Order>();
//    		
//    		for (Order o : allOrders) {
//    			if (o.getPatientID() == patientOfInterestID) {
//    				answer.add(o);
//    			}
//    		}
//    		
//			ArrayList<Order> result = listOfOrders.specificPatientOrderHistory(patientOfInterestID); 
//			
//			assertEquals(answer.size(), result.size());
//			assertEquals(answer, result); //don't need toString; automatically does .toString for each element of arraylist
//			
//    	} catch (Exception e) {
//		}
//    }
    
    @Test
    void specificPatientOrderHistoryTest1() { 
    	
    	long patientOfInterestID = 1111122222; //Smith John always exists and always has at least 1 order (from our Sql script)
    	
    	ArrayList<Order> answer = new ArrayList<Order>();
		
    	try {
			String queryGetAllRows = "SELECT * FROM Orders WHERE patientID = 1111122222;";   // to get all rows from Orders table
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int orderNum, medicationID, quantityBought;
			long patientID;
			double priceAtPurchase;
			boolean isPrescription;
			
			Order order;
			
			while (result.next()) { 
				orderNum =  result.getInt("orderNum");
				medicationID =  result.getInt("medicationID");
				patientID =  result.getLong("patientID");
				quantityBought =  result.getInt("quantityBought");
				priceAtPurchase =  result.getDouble("priceAtPurchase");
				isPrescription =  result.getBoolean("isPrescription");
				
				order = new Order(orderNum, medicationID, patientID, quantityBought, priceAtPurchase, isPrescription);
				
				answer.add(order);  // to create Order object and put in a list
			}
			
		} catch (Exception e) { // no exception expected
			fail();
		}
    	
    	ArrayList<Order> result = new ArrayList<Order>();
		try {
			result = listOfOrders.specificPatientOrderHistory(patientOfInterestID);
		} catch (Exception e) {
		} 
		
		assertEquals(answer.size(), result.size());
		assertEquals(answer, result); //don't need toString; automatically does .toString for each element of arraylist
    	
    }
    
    @Test
    void specificPatientOrderHistoryInvalid1() { //negative health card number
    	
    	assertThrows(NegativeInputException.class, () -> listOfOrders.specificPatientOrderHistory(-5)); // patientID can't be of size 1
    }
    
    @Test
    void specificPatientOrderHistoryInvalid2() { // healthcard num not 10 digits long
    	
    	String expectedMsg = "Please enter a valid 10-digit health card number";
    	
    	String resultMsg = null;
    	
    	try {
    		listOfOrders.specificPatientOrderHistory(12345);
    	}
    	catch(Exception e) {
    		resultMsg = e.getMessage();
    	}
    	
    	assertEquals(expectedMsg, resultMsg);
    }
    
    // this test is risky bc what if they add a patient with this id into the system? then it fails our test
//    @Test
//    void specificPatientOrderHistoryInvalid3() { // patient doesn't exist (assuming ID 1111191
//    	
//    	String expectedMsg = "Patient doesn't exist!";
//    	
//    	String resultMsg = null;
//    	
//    	try {
//    		listOfOrders.specificPatientOrderHistory(1111191111); // assuming this patient doesn't exist in system
//    	}
//    	catch(Exception e) {
//    		resultMsg = e.getMessage();
//    	}
//    	
//    	assertEquals(expectedMsg, resultMsg);
//    }
//    
//    @Test OLD TEST WITHOUT QUERYING - BAD I BELIVE
//    void specificPatientMoneySpentTest1() {
//    	
//    	long patientOfInterestID = 1111122222; //Smith John always exists and always has at least 1 order (from our Sql script)
//    	try {
//    		ArrayList<Order> allPatientOrders = listOfOrders.specificPatientOrderHistory(patientOfInterestID);
//    		
//    		double answer = 0;
//    		for (Order o : allPatientOrders) {
//    			answer += o.getTotalPriceOfOrder();
//    		}
//    		
//			double result = listOfOrders.specificPatientMoneySpent(patientOfInterestID); 
//			
//			assertEquals(answer, result);
//			
//    	} catch (Exception e) {
//		}
//    }
    
    @Test
    void specificPatientMoneySpentTest1() { //don't need invalid tests because already tested exceptions in specificPatientOrderHistory tests
    	
    	long patientOfInterestID = 1111122222; //Smith John always exists and always has at least 1 order (from our Sql script)
    	
    	double answer = 0, resultMoney = 0;
    	try {
			String queryGetAllRows = "SELECT SUM(priceAtPurchase) FROM Orders WHERE patientID = 1111122222;";   // to get all rows from Orders table
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			
			while (result.next()) { 
				
				answer =  result.getDouble("sum(priceAtPurchase)");
				
			}
			
			resultMoney = listOfOrders.specificPatientMoneySpent(patientOfInterestID); 
			
		} catch (Exception e) {
		}
    	
		assertEquals(answer, resultMoney);
    }
    
//    @Test
//    void specificPatientMoneySpentInvalid1() { //invalid patient id
//    	assertThrows(Exception.class, () -> listOfOrders.specificPatientOrderHistory(1)); // patientID can't be of size 1
//    }

    

}