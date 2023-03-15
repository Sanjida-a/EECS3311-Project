package testCases.IntegrationTests;

import middleLayer.*;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;

import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListOfOrdersTest {

    static String pass = "hello123";  // TA please change this according to your mySQL password in order for the tests to work
	
    @Test
    void updateOrderListFromDatabase() {
    }

    
    @Test 
    void addOrderToDatabaseTest() throws Exception {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
        
        if (MedIDWithQuantityMoreThan1 != -1) {
        	 Order O = new Order(medList.get(MedIDWithQuantityMoreThan1).getMedicationID(), 1111122222, 1);
             Prescription p = new Prescription(medList.get(MedIDWithQuantityMoreThan1).getMedicationID(), 1111122222, 2);
             val.addOrderToDatabase(O,p);
             ArrayList<Order> newList = val.getListofAllOrders();
             assertEquals(originalList.size()+1, newList.size());
        }

       
    }
    
    @Test
    void orderConstructorTestInvalid() throws Exception {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        ListOfOrders val = ListOfOrders.getInstance();
        ArrayList<Order> originalList = val.getListofAllOrders();
        
        assertThrows(Exception.class, () -> new Order(100, 1111122222, 1)); // medID doesn't exist

       
    }
    
    @Test
    void orderConstructorTestInvalid2() throws Exception {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}

        
        assertThrows(Exception.class, () -> new Order(1, 7777, 1)); // patientID/healthcard doesn't exist

       
    }
    
    @Test
    void orderConstructorTestInvalid3() throws Exception {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}

        
        assertThrows(Exception.class, () -> new Order(1, 1111122222, -5)); // negative quantity

       
    }

    @Test
    void addRefillInvalid() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        
        if (indexForOTC != -1) {
        Order O = new Order(medList.get(indexForOTC).getMedicationID(), 1111122222, 1);
        
       
        //prescription doesn't exist because medication is OTC
        assertThrows(Exception.class,  () -> val.addRefillToDatabase(O));
        ArrayList<Order> newList = val.getListofAllOrders();
        }
    }




}