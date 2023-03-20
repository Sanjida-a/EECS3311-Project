package testCases.IntegrationTests;
import databaseDAO.superDAO;
import middleLayer.*;
import middleLayer.Orders.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ReportTest {

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
    void calculateRevenue() {
        
        Report r = new Report();
        ListOfOrders listOfOrders = ListOfOrders.getInstance();
        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
		
        double revenue = 0;
		for ( Order e : allOrders) {
			revenue = revenue + e.getTotalPriceOfOrder();
		}
	
        try {
			assertEquals(revenue, r.calculateRevenue());
		} catch (Exception e1) {
		}
    }

    @Test
    void calculateProfit() {
       
        Report r = new Report();
        
        ListOfOrders listOfOrders = ListOfOrders.getInstance();
        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
		
        double revenue = 0;
		for ( Order e : allOrders) {
			revenue = revenue + e.getTotalPriceOfOrder();
		}
		
		double profit = 0.3*revenue;
	
        try {
			assertEquals(profit, r.calculateProfit());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

    }

    @Test
    void seeSummaryOfSales() {
        
        Report r = new Report();
        
        ListOfOrders listOfOrders = ListOfOrders.getInstance();
        
        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
        
        ArrayList<String> summary = r.seeSummaryOfSales();
		
		for ( int i = 0; i < allOrders.size(); i++) {
			assertEquals(allOrders.get(i).toString(), summary.get(i));
		}

    }
}