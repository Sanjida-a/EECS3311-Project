package testCases.IntegrationTests;

import middleLayer.*;
import org.junit.jupiter.api.Test;

import databaseDAO.superDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListOfOrdersTest {
	
	static String pass = "user123";  // TA please change this according to your mySQL password in order for the tests to work
	
    @Test
    void updateOrderListFromDatabase() {
    }

    @Test
    void getListOfAllOrders() throws Exception {
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        ListOfOrders val = ListOfOrders.getInstance();
        ArrayList<Order> originalList = val.getListofAllOrders();
        Order O = new Order(1, 1111122222, 1);
        Prescription p = new Prescription(1, 1111122222, 2);
        val.addOrderToDatabase(O,p);
        ArrayList<Order> newList = val.getListofAllOrders();
        assertEquals(originalList.get(newList.size()-2), newList.get(newList.size()-2));
    }

    @Test
    void addRefill() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListOfOrders val = ListOfOrders.getInstance();
        ArrayList<Order> originalList = val.getListofAllOrders();
        Order O = new Order(1, 1111122222, 1);
        val.addRefillToDatabase(O);
        ArrayList<Order> newList = val.getListofAllOrders();
        System.out.println(newList);
    }




}