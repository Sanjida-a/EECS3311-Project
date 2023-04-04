package testCases.IntegrationTests;

import middleLayer.*;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;
import middleLayer.Users.ListOfUsers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    @Test 
    void addOrderToDatabaseTest() { //OTC Order
    	
    	ArrayList<Merchandise> originalOTCList = inv.getOnlyOTCMerchandise();
    	ArrayList<Order> originalOrdersList = listOfOrders.getListofAllOrders();
    	 
    	Merchandise OTCMed = null;
    	int originalInStock = -1;
        double originalPrice = -1;
    	
        if (originalOTCList.size() > 0) { //if OTC exists in inventory
    		OTCMed = originalOTCList.get(0);
    		originalInStock = OTCMed.getQuantity();
    		originalPrice = OTCMed.getPrice();
    		
    		try {
    			inv.increaseQuantity(OTCMed.getMedicationID(), 2); //ensures enough quantity to make order (increase quantity by 2)
    			
        		Order o = new Order(OTCMed.getMedicationID(), 1111122222, 2); //(decrease quantity by 2)

                listOfOrders.addOrderToDatabase(o);
        	} catch(Exception e) { // no exception expected
            	fail();
            }
    		
    		int orderNum = -1, medID = -1, quantityBought = -1;
	        long patID = -1;
			double totalPrice = -1;
			boolean isPrescription = true;
			
			// sql query to confirm order details are right
	        try {
	            String queryGetAllRows = "SELECT * FROM Orders WHERE orderNum IN (Select max(orderNum) from Orders);";   // to get newest row/order from Orders table
	    		Statement statement = con.createStatement();
	    		ResultSet result = statement.executeQuery(queryGetAllRows);
	    		
	    		
	    		while (result.next()) { 
	    			orderNum =  result.getInt("orderNum");
	    			medID =  result.getInt("medicationID");
					patID =  result.getLong("patientID");
	    			quantityBought = result.getInt("quantityBought");
	    			totalPrice =  result.getDouble("priceAtPurchase");
	    			isPrescription =  result.getBoolean("isPrescription");
	    		}
	        } catch(Exception e) { // no exception expected
	        	fail();
	        }
	        
	        assertEquals(OTCMed.getMedicationID(), medID);
	        assertEquals(1111122222, patID);
	        assertEquals(2, quantityBought);
	        assertEquals(originalPrice*2, totalPrice);
	        assertEquals(false, isPrescription);
	    
	        ArrayList<Order> newOrdersList = listOfOrders.getListofAllOrders();
	        assertEquals(originalOrdersList.size()+1, newOrdersList.size()); // number of orders should increase
	        
	        ArrayList<Merchandise> newMedList = inv.getOnlyOTCMerchandise();
	        int newQuantity = newMedList.get(0).getQuantity();
	        assertEquals(originalInStock, newQuantity); // new quantity of medication should be same as original because did +2 -2
	        
	        //back to normal
	        try {
	            String queryDelete = "DELETE FROM Orders WHERE orderNum = ?";   // to get newest row/order from Orders table
	    		
	    		PreparedStatement pstmt = con.prepareStatement(queryDelete);
	    		pstmt.setInt(1, orderNum);
	    		pstmt.executeUpdate();
	    
	        } catch(Exception e) { // no exception expected
	        	fail();
	        }
    	}
       
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid1() {  //test "Patient doesn't exist!"
        String expectedMsg = "Patient doesn't exist!";
        String resultMsg = null;   
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        try {
    		Order o = new Order(3, 5, 1); // patientId = 5 is impossible; medID also doesn't matter because first exception thrown regards patient
            listOfOrders.addOrderToDatabase(o);
           
        } catch(Exception e) { // exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size()); // size of orders table in DB shouldn't change
        assertEquals(expectedMsg, resultMsg); 
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid2() {  //test "Medication doesn't exist!"      
        String expectedMsg = "Medication doesn't exist!";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        try {
    		Order o = new Order(500, 1111122222, 1); // medID = 500 is impossible (assuming too large)
            listOfOrders.addOrderToDatabase(o);
           
        } catch(Exception e) { // exception expected
        	resultMsg = e.getMessage();
        }
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size()); // size of orders table in DB shouldn't change
        assertEquals(expectedMsg, resultMsg);
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid3() {  //test "Quantity Bought Must Be Positive (at least 1)!"
        String expectedMsg = "Quantity Bought Must Be Positive (at least 1)!";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        ArrayList<Merchandise> medList = inv.getOnlyOTCMerchandise();
        
        if (medList.size() > 0) { // confirms there's at least 1 OTC medication in inventory
	        try {
	    		Order o = new Order(medList.get(0).getMedicationID(), 1111122222, -1); // can't order negative quantity
	            listOfOrders.addOrderToDatabase(o);
	        } catch(Exception e) { // exception expected
	        	resultMsg = e.getMessage();
	        }
	        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
	        assertEquals(originalList.size(), newList.size()); // size of orders table in DB shouldn't change
	        assertEquals(expectedMsg, resultMsg);
        }
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid4() {  //test "Not an OTC! Use the \"Give Refill for a prescription\" button"
        String expectedMsg = "Not an OTC! Use the \"Give Refill for a prescription\" button";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        ArrayList<Merchandise> medList = inv.getMerchandise();
        
        int medIDRxMed = -1;
        for (Merchandise m: medList) {
        	if (m.getisOTC() == false) { // found an Rx medication in inventory
        		medIDRxMed = m.getMedicationID();
        		break;
        	}
        }
        
        if (medIDRxMed != -1) {
	        try {
	    		Order o = new Order(medIDRxMed, 1111122222, 1);
	            listOfOrders.addOrderToDatabase(o);
	           
	        } catch(Exception e) { // exception expected
	        	resultMsg = e.getMessage();
	        }
	        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
	        assertEquals(originalList.size(), newList.size()); // size of orders table in DB shouldn't change
	        assertEquals(expectedMsg, resultMsg);
        }
    }
    
    @Test 
    void addOrderToDatabaseTestInvalid5() {  //test "Check quantity in stock for medication! Not enough!"
        String expectedMsg = "Check quantity in stock for medication! Not enough!";
        String resultMsg = null;
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        ArrayList<Merchandise> medList = inv.getOnlyOTCMerchandise();
        
        if (medList.size() > 0) {
        	Merchandise chosenM = medList.get(0);
	        try {
	    		Order o = new Order(chosenM.getMedicationID(), 1111122222, chosenM.getQuantity() + 100);
	            listOfOrders.addOrderToDatabase(o);
	           
	        } catch(Exception e) { // exception expected
	        	resultMsg = e.getMessage();
	        }
	        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
	        assertEquals(originalList.size(), newList.size()); // size of orders table in DB shouldn't change
	        assertEquals(expectedMsg, resultMsg);
        }
    }
    
    @Test 
    void addPresFormToDbTestValid() {  
    	ArrayList<Merchandise> medList = inv.getMerchandise();
    	ArrayList<Prescription> originalSmithListPres = new ArrayList<Prescription>();
    	try {
    		originalSmithListPres = listOfOrders.specificPatientPres(1111122222); // Smith John's prescription forms
    	} catch(Exception e) {
    		// no exception expected
    		fail();
    	}
    	
        Merchandise chosenM = null;
        for (Merchandise m: medList) {
        	if (m.getisOTC() == false) { // found Rx med
        		chosenM = m;
        		break;
        	}
        }
        
        if (chosenM != null) { // if an Rx exists in system, can only add prescription then
        	
        	int originalRefills = -1;
        	for (Prescription pres : originalSmithListPres) {
        		if (pres.getMedicationID() == chosenM.getMedicationID()) {
        			originalRefills = pres.getOriginalNumOfRefills(); // if comes here, a prescription with same medID and patID already exists
        		}
        	}
        	
        	// add prescription
        	try {
	        	Prescription p = new Prescription(chosenM.getMedicationID(), 1111122222, 10);
	        	listOfOrders.addPresFormToDb(p);
        	} catch (Exception e) {
        		// no exception expected
        		fail();
        	}
        	
        	// get new list of Smith's prescription forms
        	ArrayList<Prescription> newSmithListPres = new ArrayList<Prescription>();
        	try {
	        	newSmithListPres = listOfOrders.specificPatientPres(1111122222);
        	} catch (Exception e) {
        		// no exception expected
        		fail();
        	}
        	
        	// sql query to confirm pres form details are right
        	int newNumOfRefills = -1;
	        try {
	        	String query = "SELECT numOfRefills FROM prescriptions where patientID = 1111122222 and medicationID = " + chosenM.getMedicationID() + ";";
	    		Statement statement = con.createStatement();
	    		ResultSet result = statement.executeQuery(query);
	    		
	    		
	    		while (result.next()) { 
	    			newNumOfRefills =  result.getInt("numOfRefills");
	    		}
	        	
	        } catch(Exception e) { // no exception expected
	        	fail();
	        }
	        
        	if (originalRefills == -1) { //added new row in prescription form table
        		assertEquals(originalSmithListPres.size()+1, newSmithListPres.size());
        		assertEquals(10, newNumOfRefills);
        		
        		// back to normal by deleting row
        		try {
        			String queryDelete = "DELETE FROM Prescriptions WHERE patientID = 1111122222 and medicationID = " + chosenM.getMedicationID() + ";";   // to get newest row/order from Orders table
    	    		PreparedStatement pstmt = con.prepareStatement(queryDelete);
    	    		pstmt.executeUpdate();
     	        	
     	        } catch(Exception e) { // no exception expected
     	        	fail();
     	        }
        	}
        	
        	
        	else { //added quantity of prescription to existing row in prescription form table
        		assertEquals(originalSmithListPres.size(), newSmithListPres.size());
        		assertEquals(originalRefills + 10, newNumOfRefills);
        		
        		// back to normal by updating numOfRefills to original value
        		try {
        			String queryUpdate = "UPDATE Prescriptions SET numOfRefills = " + originalRefills + " WHERE patientID = 1111122222 and medicationID = " + chosenM.getMedicationID() + ";";   // to get newest row/order from Orders table
    	    		PreparedStatement pstmt = con.prepareStatement(queryUpdate);
    	    		pstmt.executeUpdate();
     	        	
     	        } catch(Exception e) { // no exception expected
     	        	fail();
     	        }
        	}

        }
    }
    
    @Test 
    void addPresFormToDbTestInvalid1() {  //test "Not an Rx! You can only add prescription forms for Rx Medications"
        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres(); 
        String expectedMsg = "Not an Rx! You can only add prescription forms for Rx Medications";
        String resultMsg = null;
        
        ArrayList<Merchandise> medOTCList = inv.getOnlyOTCMerchandise();
    
        if (medOTCList.size() > 0) { // need at least one OTC medication in inventory
	        try {
	        	Prescription p = new Prescription(medOTCList.get(0).getMedicationID(), 1111122222, 1);
	                listOfOrders.addPresFormToDb(p);
	           
	        } catch(Exception e) { // exception expected
	        	resultMsg = e.getMessage();
	        }
	        ArrayList<Prescription> newListPres = listOfOrders.getListofAllPres(); 
	        assertEquals(originalListPres.size(), newListPres.size()); // size of prescription forms table in DB shouldn't change
	        assertEquals(expectedMsg, resultMsg); 
        }
    }
    
    @Test 
    void addPresFormToDbTestInvalid2() {  //test "Refills must be positive (at least 1)!"
        String expectedMsg = "Refills must be positive (at least 1)!";
        String resultMsg = null;
        ArrayList<Prescription> originalListPres = listOfOrders.getListofAllPres(); 
        
        ArrayList<Merchandise> medList = inv.getMerchandise();
        int medIDOfRx = -1;
        for (Merchandise m : medList) {
        	if (m.getisOTC() == false)
        		medIDOfRx = m.getMedicationID();
        }
        
        if (medIDOfRx != -1) { // need at least 1 Rx in inventory
	        try {
	        	Prescription p = new Prescription(medIDOfRx, 1111122222, -1);
	                listOfOrders.addPresFormToDb(p);
	           
	        } catch(Exception e) { // no exception expected
	        	resultMsg = e.getMessage();
	        } 
	        ArrayList<Prescription> newListPres = listOfOrders.getListofAllPres(); 
	        assertEquals(originalListPres.size(), newListPres.size());
	        assertEquals(expectedMsg, resultMsg);
        }
    }
    
    @Test
    void addRefillValidTest() { // Rx medication
    	ArrayList<Order> originalOrderList = listOfOrders.getListofAllOrders();
    	ArrayList<Merchandise> originalMercList = inv.getMerchandise();
    	
    	Merchandise chosenMed = inv.searchMerchandiseWithID(7); // medID = 7 is our test Rx medication
    	double originalPrice = chosenMed.getPrice();
    	
    	try {
    		inv.increaseQuantity(chosenMed.getMedicationID(), 2); //increases quantity by 2
	       	Order o = new Order(chosenMed.getMedicationID(), 1111122222, 2);  //decreases quantity by 2
	   	    listOfOrders.addRefillToDatabase(o);
	   	} 
    	catch (Exception e){ // exception not expected
    		fail();
    	}
	   
    	ArrayList<Order> newOrderList = listOfOrders.getListofAllOrders();
    	ArrayList<Merchandise> newMercList = inv.getMerchandise();
    	assertEquals(originalOrderList.size()+1, newOrderList.size()); // number of orders should increase
    	assertEquals(originalMercList, newMercList); // merch list should be same before and after because increased AND decreased quantity by 2
    	
    	// sql query to confirm order details are right
    	int orderNum = -1, medID = -1, quantityBought = -1;
    	long patID = -1;
    	double totalPrice = -1;
    	boolean isPrescription = false;
        try {
            String queryGetAllRows = "SELECT * FROM Orders WHERE orderNum IN (Select max(orderNum) from Orders);";   // to get newest row/order from Orders table
    		Statement statement = con.createStatement();
    		ResultSet result = statement.executeQuery(queryGetAllRows);
    		
    		
    		while (result.next()) { 
    			orderNum =  result.getInt("orderNum");
    			medID =  result.getInt("medicationID");
				patID =  result.getLong("patientID");
    			quantityBought = result.getInt("quantityBought");
    			totalPrice =  result.getDouble("priceAtPurchase");
    			isPrescription =  result.getBoolean("isPrescription");
    		}
        } catch(Exception e) { // no exception expected
        	fail();
        }
        
        assertEquals(chosenMed.getMedicationID(), medID);
        assertEquals(1111122222, patID);
        assertEquals(2, quantityBought);
        assertEquals(originalPrice*2, totalPrice);
        assertEquals(true, isPrescription);
        
        //back to normal
        try {
            String queryDelete = "DELETE FROM Orders WHERE orderNum = ?";
    		
    		PreparedStatement pstmt = con.prepareStatement(queryDelete);
    		pstmt.setInt(1, orderNum);
    		pstmt.executeUpdate();
    
        } catch(Exception e) { // no exception expected
        	fail();
        }
        
        //back to normal
        try {
        	String queryUpdate = "UPDATE Prescriptions SET numOfRefills = " + 100 + " WHERE prescriptionNum = " + 1 + ";";
    		PreparedStatement pstmt = con.prepareStatement(queryUpdate);
    		pstmt.executeUpdate();
    
        } catch(Exception e) { // no exception expected
        	fail();
        }
    }
    
    @Test
	void addRefillInvalidTest1() { // not an Rx medication
  	
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
      
    	if (indexForOTC != -1) { // means at least one OTC exists in inventory
      	try {
        	Order O = new Order(medList.get(indexForOTC).getMedicationID(), 1111122222, 1);
	        listOfOrders.addRefillToDatabase(O); 
      	} 
        catch (Exception e){ // exception expected because can't add a refill order for an OTC medication
        	resultMsg = e.getMessage();
        }
      	
        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        assertEquals(originalList.size(), newList.size());
        assertEquals(expectedMsg, resultMsg);
      }
    
     
    }
    
    @Test
    void addRefillInvalidTest2() { // "No record found of prescription under Patient with Health Card Number: " + o.getPatientID() + ". Add prescription form into system first."
	  	
    	// deleting test prescription in prescription table
    	try {
            String queryDelete = "DELETE FROM Prescriptions WHERE prescriptionNum = 1";
    		
    		PreparedStatement pstmt = con.prepareStatement(queryDelete);
    		pstmt.executeUpdate();
        } catch(Exception e) { // no exception expected
        	fail();
        }
    	
    	ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
	  	
	  	String expectedMsg = "No record found of prescription under Patient with Health Card Number: " + 1111122222 + ". Add prescription form into system first.";
	  	String resultMsg = null;  
	  	try {
	  		Order O = new Order(7, 1111122222, 5);
	  		listOfOrders.addRefillToDatabase(O);
	  	}
	  	catch (Exception e){ // exception expected because can't add a refill order for an OTC medication
	  		resultMsg = e.getMessage();
	  	}  
	  	ArrayList<Order> newList = listOfOrders.getListofAllOrders();
	  	assertEquals(originalList.size(), newList.size());
	  	assertEquals(expectedMsg, resultMsg);    
	  	
	  	// back to normal (adding prescription form again)
	  	try {
            String queryInsert = "INSERT INTO Prescriptions VALUES (1, 7, 1111122222, 100)";
    		PreparedStatement pstmt = con.prepareStatement(queryInsert);
    		pstmt.executeUpdate();
        } catch(Exception e) { // no exception expected
        	fail();
        }
	  	
    }
    
    @Test
    void addRefillInvalidTest3() { // "0 refills left!"
    	
    	try {
			inv.increaseQuantity(7, 100); //keeps medication quantity the same before and after
			Order boughtAllRefillsOrder = new Order(7, 1111122222, 100);  // buying all 100 (original numOfRefills on prescriptionForm) to mimic 0 refills left
			listOfOrders.addRefillToDatabase(boughtAllRefillsOrder);
    	} catch (Exception e) {
    		// not expected
    		fail();
		}
 
    	ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
	  	
    	String expectedMsg = "0 refills left!";;
	  	String resultMsg = null;  
	  	
    	//actual test order
    	try {
    		Order o = new Order(7, 1111122222, 5);
			listOfOrders.addRefillToDatabase(o);
		} catch (Exception e1) {
			resultMsg = e1.getMessage();
		}
    	
    	assertEquals(expectedMsg, resultMsg);
    	ArrayList<Order> newList = listOfOrders.getListofAllOrders();
    	assertEquals(originalList, newList);
    	
    	// back to normal (delete fake order row added at beginning)
    	try {
	    	String queryGetAllRows = "SELECT max(orderNum) FROM Orders;";   // to get newest row/order from Orders table
	 		Statement statement = con.createStatement();
	 		ResultSet result = statement.executeQuery(queryGetAllRows);
	 		
	 		int maxOrderNum = -1;
	 		while (result.next()) { 
	 			maxOrderNum =  result.getInt("max(orderNum)");
	 			
	 		}
	 		
	 		String queryDelete = "DELETE FROM Orders WHERE orderNum = ?";
    		
    		PreparedStatement pstmt = con.prepareStatement(queryDelete);
    		pstmt.setInt(1, maxOrderNum);
    		pstmt.executeUpdate();
    		
    	} catch(Exception e) { //not expected
    		fail();
    	}
     
    }
    
    @Test
    void addRefillInvalidTest4() { // "Not enough refills! Only have " + refillLeft +  " refills left!"
    	
    	ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
    	
    	String expectedMsg = "Not enough refills! Only have " + 100 +  " refills left!";
    	String resultMsg = null;
    	
    	try {
    		Order o = new Order(7, 1111122222, 200); //only have 100 refills in total, so trying to buy 200
			listOfOrders.addRefillToDatabase(o);
		} catch (Exception e1) {
			resultMsg = e1.getMessage();
		}
    	
    	assertEquals(expectedMsg, resultMsg);
    	ArrayList<Order> newList = listOfOrders.getListofAllOrders();
    	assertEquals(originalList, newList);
    	
    }
    
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
    	
    	assertThrows(NegativeInputException.class, () -> listOfOrders.specificPatientOrderHistory(-5)); // patientID can't be negative
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
			
		} catch (Exception e) { // no exception expected
			fail();
		}
    	
		assertEquals(answer, resultMoney);
    }
    
    @Test
    void specificPatientPresInvalidTest1() { // test "Please enter a positive health card number"
    	
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
    void specificPatientPresInvalidTest2() { // test "Please enter a valid 10-digit health card number"
    	
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

}