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
	
    @Test
    void updateOrderListFromDatabase() {
    }
    
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
           	 Order O = new Order(medList.get(MedIDWithQuantityMoreThan1).getMedicationID(), 1111122222, 1);
                Prescription p = new Prescription(medList.get(MedIDWithQuantityMoreThan1).getMedicationID(), 1111122222, 2);
//                val.addOrderToDatabase(O,p);
                listOfOrders.addOrderToDatabase(O);
                ArrayList<Order> newList = listOfOrders.getListofAllOrders();
                assertEquals(originalList.size()+1, newList.size());
           }
        } catch(Exception e) {
        	
        }
    
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
    void addRefillInvalid() {
       
        ArrayList<Order> originalList = listOfOrders.getListofAllOrders();
        
        ArrayList<Merchandise> medList = inv.getMerchandise();

        
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
        	        
        	       
		        //prescription doesn't exist because medication is OTC
		        assertThrows(Exception.class,  () -> listOfOrders.addRefillToDatabase(O));
		        ArrayList<Order> newList = listOfOrders.getListofAllOrders();
        	 }
        } catch (Exception e){
        	
        }
       
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
			
		} catch (Exception e) {
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
    void specificPatientOrderHistoryTest2() { //invalid patient id
    	
    	assertThrows(Exception.class, () -> listOfOrders.specificPatientOrderHistory(1)); // patientID can't be of size 1
    }
    
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
    void specificPatientMoneySpentTest1() {
    	
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
    void specificPatientMoneySpentTest2() { //invalid patient id
    	assertThrows(Exception.class, () -> listOfOrders.specificPatientOrderHistory(1)); // patientID can't be of size 1
    }

    

}