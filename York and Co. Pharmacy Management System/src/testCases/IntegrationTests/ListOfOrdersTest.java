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
			superDAO.setPassword("hello@123456");// TA please change this according to your mySQL password in order for the tests to work
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
        for (int i = 0; i < medList.size(); i++) {
        	if (medList.get(i).getQuantity() > 1 )  {
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
    
    @Test 
    void addOrderToDatabaseTestInvalid1() {  //test "Patient doesn't exist!"
        String expectedMsg = "Patient doesn't exist!";
        String resultMsg = null;   
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        try {
        		Order o = new Order(3, 5, 1);
                listOfOrders.addOrderToDatabase(o);
           
        } catch(Exception e) { // no exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid2() {  //test "Medication doesn't exist!"      
        String expectedMsg = "Medication doesn't exist!";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        try {
        		Order o = new Order(500, 1111122222, 1);
                listOfOrders.addOrderToDatabase(o);
           
        } catch(Exception e) { // no exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid3() {  //test "Quantity Bought Must Be Positive (at least 1)!"
        String expectedMsg = "Quantity Bought Must Be Positive (at least 1)!";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        try {
        		Order o = new Order(3, 1111122222, -1);
                listOfOrders.addOrderToDatabase(o);
           
        } catch(Exception e) { // no exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid4() {  //test "Not an OTC! Use the \"Give Refill for a prescription\" button"
        String expectedMsg = "Not an OTC! Use the \"Give Refill for a prescription\" button";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        try {
        		Order o = new Order(5, 1111122222, 1);
                listOfOrders.addOrderToDatabase(o);
           
        } catch(Exception e) { // no exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid5() {  //test "Check quantity in stock for medication! Not enough!"
        String expectedMsg = "Check quantity in stock for medication! Not enough!";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        try {
        		Order o = new Order(3, 1111122222, 100000000);
                listOfOrders.addOrderToDatabase(o);
           
        } catch(Exception e) { // no exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        assertEquals(expectedMsg, resultMsg);
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
    void addPresFormToDbTestValid() {  
    	ArrayList<Merchandise> medList = inv.getMerchandise();
        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres();        
        int MedIDWithQuantityMoreThan1 = -1;
        for (int i = 0; i < medList.size(); i++) {
        	if (medList.get(i).getQuantity() > 1 && medList.get(i).getisOTC() != true) {
        		MedIDWithQuantityMoreThan1 = i;
        		break;
        	}
        }
        int originalRefills = 0; int johnRefill = 0;
        for(Prescription pres : originalListPres ) {
        	originalRefills += pres.getOriginalNumOfRefills();
        	if (pres.getPatientID() == 1111122222 && pres.getMedicationID() == medList.get(MedIDWithQuantityMoreThan1).getMedicationID()) {
        		johnRefill = pres.getOriginalNumOfRefills();
        	}
        }       
        try {
        	if (MedIDWithQuantityMoreThan1 != -1) {
        		Prescription p = new Prescription(medList.get(MedIDWithQuantityMoreThan1).getMedicationID(), 1111122222, 10);
                listOfOrders.addPresFormToDb(p);
           }
        } catch(Exception e) { // no exception expected
        	fail();
        }
        
        ArrayList<Prescription> newList = listOfOrders.getListofAllPres();
        int newRefills =0; int johnUpdate = 0;
        for(Prescription prescript : newList ) {
        	newRefills += prescript.getOriginalNumOfRefills();
        	if (prescript.getPatientID() == 1111122222 && prescript.getMedicationID() == medList.get(MedIDWithQuantityMoreThan1).getMedicationID()) {
        		johnUpdate = prescript.getOriginalNumOfRefills();
        	}
        }
        int newSize = originalListPres.size()+1;
        if (newSize == newList.size()) {
        	assertEquals(10, newRefills- originalRefills);
        }
        else {
        	assertNotEquals(johnRefill, johnUpdate);
        }      
    }
    
    @Test 
    void addPresFormToDbTestValid1() {  //test "Not an Rx! You can only add prescription forms for Rx Medications"
        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres(); 
        String expectedMsg = "Not an Rx! You can only add prescription forms for Rx Medications";
        String resultMsg = null;
        try {
        	Prescription p = new Prescription(3, 1111122222, 1);
                listOfOrders.addPresFormToDb(p);
           
        } catch(Exception e) { // no exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Prescription> newListPres = listOfOrders.getListofAllPres(); 
        assertEquals(originalListPres.size(), newListPres.size());
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test 
    void addPresFormToDbTestValid2() {  //test "Refills must be positive (at least 1)!"
        String expectedMsg = "Refills must be positive (at least 1)!";
        String resultMsg = null;
        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres(); 
        try {
        	Prescription p = new Prescription(5, 1111122222, -1);
                listOfOrders.addPresFormToDb(p);
           
        } catch(Exception e) { // no exception expected
        	resultMsg = e.getMessage();
        } 
        ArrayList<Prescription> newListPres = listOfOrders.getListofAllPres(); 
        assertEquals(originalListPres.size(), newListPres.size());
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test
    void addRefillValid() { 
       
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
//        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres();
        ArrayList<Merchandise> medList = inv.getMerchandise();
        int indexForRx = -1;
        for (int i = 0; i < medList.size(); i++) {
        	if (medList.get(i).getName().equalsIgnoreCase("Test")) {
        		indexForRx = medList.get(i).getMedicationID();
        		break;
        	}
        }
        
        try {
        	 if (indexForRx != -1) {
        		
//        		listOfOrders.addPresFormToDb(new Prescription(medList.get(indexForRx).getMedicationID(), 1111122222, 50));
        		listOfOrders.addPresFormToDb(new Prescription(indexForRx, 1111122222, 10));
	        	Order O = new Order(indexForRx, 1111122222, 1);
        	     
		        listOfOrders.addRefillToDatabase(O);
        	 }
        } 
        catch (Exception e){ // exception expected because can't add a refill order for an OTC medication
        	fail();
        }
        
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size()+1, newList.size());
       
    }

    @Test
    void addRefillInvalid1() { // "0 refills left!"
       
    	ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
//        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres();
        ArrayList<Merchandise> medList = inv.getMerchandise();
        int indexForRx = -1;
        for (int i = 0; i < medList.size(); i++) {
        	if (medList.get(i).getName().equals("Test1")) {
        		indexForRx = medList.get(i).getMedicationID();
        		break;
        	}
        }

        String expectedMsg = "0 refills left!";
        String resultMsg = null;
        
       
        try {
        	if (indexForRx != -1) {
        	  {
        		 listOfOrders.addPresFormToDb(new Prescription(indexForRx, 1111122222, 1)); 
        		  Order O = new Order(indexForRx, 1111122222, 1);
		        listOfOrders.addRefillToDatabase(O);
		        Order O1 = new Order(indexForRx, 1111122222, 10);
		        listOfOrders.addRefillToDatabase(O1);
        	 }
        	}
        } 
        catch (Exception e){ // exception expected because can't add a refill order for an OTC medication
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size()+1, newList.size());
        assertEquals(expectedMsg, resultMsg);
       
    }
    
    @Test
    void addRefillInvalid2() { // "Not enough refills! Only have " + refillLeft +  " refills left!"
    	ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres();
        ArrayList<Merchandise> medList = inv.getMerchandise();
        int indexForRx = -1;
        for (int i = 0; i < medList.size(); i++) {
        	if (medList.get(i).getName().equals("Test2")) {
        		indexForRx = medList.get(i).getMedicationID();
        		break;
        	}
        }
        int refillLeft = 0;       
        String resultMsg = null;
        try {
        	if (indexForRx != -1) {
        	  {
        		 listOfOrders.addPresFormToDb(new Prescription(indexForRx, 1111122222, 1)); 
                  originalListPres = listOfOrders.getListofAllPres();
        		 for (int i = 0; i < originalListPres.size(); i++) {
 		        	if (originalListPres.get(i).getMedicationID() == indexForRx && originalListPres.get(i).getPatientID() == 1111122222 ) {
 		        		refillLeft = refillLeft + originalListPres.get(i).getOriginalNumOfRefills();
 		        	}
 		        }
        		  Order O = new Order(indexForRx, 1111122222, 100000000);
		        listOfOrders.addRefillToDatabase(O);		        		       
        	 }
        	}
        } 
        catch (Exception e){ // exception expected because can't add a refill order for an OTC medication
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        String expectedMsg = "Not enough refills! Only have " + refillLeft +  " refills left!";
        assertEquals(expectedMsg, resultMsg);
       
    }
    
    @Test
    void addRefillInvalid3() { // "No record found of prescription under Patient with Health Card Number: " + o.getPatientID() + ". Add prescription form into system first."
    	ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
    	ArrayList<Merchandise> medList = inv.getMerchandise();
        int indexForRx = -1;
        for (int i = 0; i < medList.size(); i++) {
        	if (medList.get(i).getName().equals("Test3")) {
        		indexForRx = medList.get(i).getMedicationID();
        		break;
        	}
        }
        String expectedMsg = "No record found of prescription under Patient with Health Card Number: " + 1111122222 + ". Add prescription form into system first.";
        String resultMsg = null;  
        try {
        	if (indexForRx != -1) {
        	  {
        		  Order O = new Order(indexForRx, 1111122222, 100000000);
		        listOfOrders.addRefillToDatabase(O);
        	 }
        	}
        } 
        catch (Exception e){ // exception expected because can't add a refill order for an OTC medication
        	resultMsg = e.getMessage();
        }  
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        assertEquals(expectedMsg, resultMsg);     
    }
    
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
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
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
    
    @Test
    void specificPatientPresInvalid1() { // test "Please enter a positive health card number"
    	
    	String expectedMsg = "Please enter a positive health card number";
    	
    	String resultMsg = null;
    	
    	try {
    		listOfOrders.specificPatientPres(-1111122222);
    	}
    	catch(Exception e) {
    		resultMsg = e.getMessage();
    	}
    	
    	assertEquals(expectedMsg, resultMsg);
    }
    
    @Test
    void specificPatientPresInvalid2() { // test "Please enter a valid 10-digit health card number"
    	
    	String expectedMsg = "Please enter a valid 10-digit health card number";
    	
    	String resultMsg = null;
    	
    	try {
    		listOfOrders.specificPatientPres(123456);
    	}
    	catch(Exception e) {
    		resultMsg = e.getMessage();
    	}
    	
    	assertEquals(expectedMsg, resultMsg);
    }
    
//    @Test
//    void specificPatientMoneySpentInvalid1() { //invalid patient id
//    	assertThrows(Exception.class, () -> listOfOrders.specificPatientOrderHistory(1)); // patientID can't be of size 1
//    }

    

}