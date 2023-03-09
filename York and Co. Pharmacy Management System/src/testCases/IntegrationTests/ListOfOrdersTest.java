package testCases.IntegrationTests;

import middleLayer.*;
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
    void getListOfAllOrders(){
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
//        ListOfOrders L = ListOfOrders.getInstance();
//        Order O = new Order(1, 1111122222, 1);
//        Prescription p = new Prescription(1, 1111122222, 2);
//        L.addOrderToDatabase(O,p);
//        assertEquals("[]", L.getListofAllOrders().toString());
        ArrayList<Order> OrderTest = new ArrayList<Order>();
        ListOfOrders call = ListOfOrders.getInstance();
        Order val = new Order(1, 1, 1111122222, 2,5, false);
        OrderTest.add(val);
        assertEquals(OrderTest.toString(), call.getListofAllOrders().toString());

    }



    @Test
    void calculateRevenue() {
    }
}