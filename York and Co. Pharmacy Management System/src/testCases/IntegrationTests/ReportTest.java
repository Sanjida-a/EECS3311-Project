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
			// no exception expected
		}
    	
    }
	
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
			// no exception expected
		}

    }

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
			// no exception expected
		}
		
		assertEquals(answer, r.seeSummaryOfSales());

    }
    
    @Test
    void seeMedicationSalesTest() {
        
    	ArrayList<String> expected = new ArrayList<String>();
		try {
			String queryGetAllRows = "SELECT medicationID, sum(quantityBought) as totalBought, sum(priceAtPurchase) as totalPrice FROM Orders GROUP BY medicationID;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int medicationID, totalBought;
			double totalPrice;
			
			while (result.next()) { 
				medicationID =  result.getInt("medicationID");
				totalBought =  result.getInt("totalBought");
				totalPrice =  result.getDouble("totalPrice");
				
				expected.add("MedID: " + medicationID + " - " + "Sold: " + totalBought + ", $" + totalPrice + "\n");
			}
			
		} catch (Exception e) {
			// no exception expected
		}
		
		assertEquals(expected, r.seeMedicationSales());

    }
    
}