package testCases.IntegrationTests;
import databaseDAO.superDAO;
import middleLayer.*;
import middleLayer.Orders.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ReportTest {

    static String pass = "hello123";  // TA please change this according to your mySQL password in order for the tests to work
    @Test
    void calculateRevenue() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        ListOfOrders listOfOrders = ListOfOrders.getInstance();
        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
		
        double revenue = 0;
		for ( Order e : allOrders) {
			revenue = revenue + e.getTotalPriceOfOrder();
		}
	
        assertEquals(revenue, r.calculateRevenue());
    }

    @Test
    void calculateProfit() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        
        ListOfOrders listOfOrders = ListOfOrders.getInstance();
        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
		
        double revenue = 0;
		for ( Order e : allOrders) {
			revenue = revenue + e.getTotalPriceOfOrder();
		}
		
		double profit = 0.3*revenue;
	
        assertEquals(profit, r.calculateProfit());

    }

    @Test
    void seeSummaryOfSales() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        
        ListOfOrders listOfOrders = ListOfOrders.getInstance();
        
        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
        
        ArrayList<String> summary = r.seeSummaryOfSales();
		
		for ( int i = 0; i < allOrders.size(); i++) {
			assertEquals(allOrders.get(i).toString(), summary.get(i));
		}
//        assertEquals("Order number: 1 Medication ID: 1 Quantity bought: 1 Total price: 5.000000\n", r.seeSummaryOfSales());
    }
}