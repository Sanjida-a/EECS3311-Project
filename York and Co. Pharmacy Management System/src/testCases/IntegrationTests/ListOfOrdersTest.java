package testCases.IntegrationTests;

import middleLayer.*;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListOfOrdersTest {

	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello@123456");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
    @Test
    void updateOrderListFromDatabase() {
    }
    
    @Test 
    void addOrderToDatabaseTest() {
    	
        ListOfOrders val = ListOfOrders.getInstance();
        Inventory inv = Inventory.getInstance();
        ArrayList<Merchandise> medList = inv.getMerchandise();
        ArrayList<Order> originalList = val.getListofAllOrders();
        
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
                val.addOrderToDatabase(O);
                ArrayList<Order> newList = val.getListofAllOrders();
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
       
        ListOfOrders val = ListOfOrders.getInstance();
        ArrayList<Order> originalList = val.getListofAllOrders();
        
        Inventory inv = Inventory.getInstance();
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
		        assertThrows(Exception.class,  () -> val.addRefillToDatabase(O));
		        ArrayList<Order> newList = val.getListofAllOrders();
        	 }
        } catch (Exception e){
        	
        }
       
    }
    
    //might need to update if return type of method changes
    @Test
    void specificPatientOrderHistoryTest1() { 
    	ListOfOrders val = ListOfOrders.getInstance();
    	long patientOfInterestID = 1111122222; //Smith John always exists and always has at least 1 order (from our Sql script)
    	try {
    		ArrayList<Order> allOrders = val.getListofAllOrders();
    		ArrayList<Order> answer = new ArrayList<Order>();
    		
    		for (Order o : allOrders) {
    			if (o.getPatientID() == patientOfInterestID) {
    				answer.add(o);
    			}
    		}
    		
			ArrayList<Order> result = val.specificPatientOrderHistory(patientOfInterestID); 
			
			assertEquals(answer.size(), result.size());
			assertEquals(answer, result); //don't need toString; automatically does .toString for each element of arraylist
			
    	} catch (Exception e) {
		}
    }
    
    @Test
    void specificPatientOrderHistoryTest2() { //invalid patient id
    	ListOfOrders val = ListOfOrders.getInstance();
    	assertThrows(Exception.class, () -> val.specificPatientOrderHistory(1)); // patientID can't be of size 1
    }
    
    @Test
    void specificPatientMoneySpentTest1() {
    	ListOfOrders val = ListOfOrders.getInstance();
    	long patientOfInterestID = 1111122222; //Smith John always exists and always has at least 1 order (from our Sql script)
    	try {
    		ArrayList<Order> allPatientOrders = val.specificPatientOrderHistory(patientOfInterestID);
    		
    		double answer = 0;
    		for (Order o : allPatientOrders) {
    			answer += o.getTotalPriceOfOrder();
    		}
    		
			double result = val.specificPatientMoneySpent(patientOfInterestID); 
			
			assertEquals(answer, result);
			
    	} catch (Exception e) {
		}
    }
    
    @Test
    void specificPatientMoneySpentTest2() { //invalid patient id
    	ListOfOrders val = ListOfOrders.getInstance();
    	assertThrows(Exception.class, () -> val.specificPatientOrderHistory(1)); // patientID can't be of size 1
    }

    

}