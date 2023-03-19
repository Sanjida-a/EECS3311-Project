package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import middleLayer.Orders.*;
import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.OrderData.OrderStub;
import databaseDAO.UserData.UserStub;

class ReportUnitTest {
	private static Report report;
	private static OrderStub orderStub;
	private static MerchandiseStub merStub;
	private static UserStub userStub;
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		
		try {
			superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderStub = new OrderStub();
		merStub = new MerchandiseStub();
		userStub = new UserStub();
		report = new Report(orderStub, merStub, userStub);
			

		
	}
	
	//done
	@Test
	void testCalculateRevenue() {
		

		try {
			assertEquals(70.0, report.calculateRevenue(), 0.001);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Test
	void testCalculateProfit() {
		

		try {
			assertEquals(21.0, report.calculateProfit(), 0.01);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	@Test
	void testSeeSummaryOfSales() {
	

		ArrayList<String> expected = new ArrayList<String>();
		for(Order o : orderStub.orderList) {
			expected.add(o.toString());
		}
		assertEquals(expected, report.seeSummaryOfSales() );
	}

}
