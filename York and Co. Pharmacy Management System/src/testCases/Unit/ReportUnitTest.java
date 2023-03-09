package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import middleLayer.Orders.*;
import databaseDAO.superDAO;
import databaseDAO.OrderData.OrderStub;

class ReportUnitTest {
	
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	//done
	@Test
	void testCalculateRevenue() {
		//fail("Not yet implemented");
		Report report = new Report();
		report.setOrderDAO(new OrderStub());
		try {
			assertEquals(70.0, report.calculateRevenue(), 0.001);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testCalculateProfit() {
		//fail("Not yet implemented");
		Report report = new Report();
		report.setOrderDAO(new OrderStub());
		try {
			assertEquals(21.0, report.calculateProfit(), 0.01);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testSeeSummaryOfSales() {
		//fail("Not yet implemented");
		Report report = new Report();
		OrderStub stub = new OrderStub();
		report.setOrderDAO(stub);
		ArrayList<String> expected = new ArrayList<String>();
		for(Order o : stub.orderList) {
			expected.add(o.toString());
		}
		assertEquals(expected, report.seeSummaryOfSales() );
	}

}
