package testCases.IntegrationTests;
import databaseDAO.superDAO;
import middleLayer.*;
import middleLayer.Orders.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class ReportTest {
	
	private static Connection con;
	private static Report r;
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			con = superDAO.getCon();
			r = new Report();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
		
//    @Test OLD TEST WITHOUT QUERYING - BAD I BELIVE
//    void calculateRevenueTest() {
//        
//        Report r = new Report();
//        ListOfOrders listOfOrders = ListOfOrders.getInstance();
//        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
//		
//        double revenue = 0;
//		for ( Order e : allOrders) {
//			revenue = revenue + e.getTotalPriceOfOrder();
//		}
//	
//        try {
//			assertEquals(revenue, r.calculateRevenue());
//		} catch (Exception e1) {
//		}
//    }
	
	@Test
    void calculateRevenueTest() {
		
		double answer = 0;
		try {
			String queryGetAllRows = "SELECT SUM(priceAtPurchase) FROM Orders;";   // to get all rows from Orders table
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			
			while (result.next()) { 
				
				answer =  result.getDouble("sum(priceAtPurchase)");
				
			}
			
			assertEquals(answer, r.calculateRevenue());
			
		} catch (Exception e) {
		}
    	
    }

//    @Test OLD TEST WITHOUT QUERYING - BAD I BELIVE
//    void calculateProfitTest() {
//       
//        Report r = new Report();
//        
//        ListOfOrders listOfOrders = ListOfOrders.getInstance();
//        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
//		
//        double revenue = 0;
//		for ( Order e : allOrders) {
//			revenue = revenue + e.getTotalPriceOfOrder();
//		}
//		
//		double profit = 0.3*revenue;
//	
//        try {
//			assertEquals(profit, r.calculateProfit());
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//
//    }
	
	@Test
    void calculateProfitTest() {
		
		double answer = 0;
		try {
			String queryGetAllRows = "SELECT SUM(priceAtPurchase) FROM Orders;";   // to get all rows from Orders table
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			
			while (result.next()) { 
				
				answer =  result.getDouble("sum(priceAtPurchase)");
				
			}

			answer = 0.3*answer;
			assertEquals(answer, r.calculateProfit());
			
		} catch (Exception e) {
		}

    }

//    @Test  OLD TEST WITHOUT QUERYING - BAD I BELIVE
//    void seeSummaryOfSalesTest() {
//        
//        Report r = new Report();
//        
//        ListOfOrders listOfOrders = ListOfOrders.getInstance();
//        
//        ArrayList<Order> allOrders = listOfOrders.getListofAllOrders();
//        
//        ArrayList<String> summary = r.seeSummaryOfSales();
//		
//		for ( int i = 0; i < allOrders.size(); i++) {
//			assertEquals(allOrders.get(i).toString(), summary.get(i));
//		}
//
//    }
    

    @Test
    void seeSummaryOfSalesTest() {
        
    	ArrayList<String> answer = new ArrayList<String>();
		try {
			String queryGetAllRows = "SELECT * FROM Orders;";   // to get all rows from Orders table
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
				
				answer.add(order.toString());  // to create Order object and put in a list
			}
			
		} catch (Exception e) {
		}
		
		assertEquals(answer, r.seeSummaryOfSales());

    }
    
}