package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import middleLayer.Orders.*;

import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.OrderData.OrderStub;
import databaseDAO.UserData.UserStub;

class ReportUnitTest {
	private static Report report;
	private static OrderStub orderStub;
	private static MerchandiseStub merStub;
	private static UserStub userStub;
	
	@BeforeEach
	public void before() {
		orderStub = new OrderStub();
		merStub = new MerchandiseStub();
		userStub = new UserStub();
		report = new Report(orderStub, merStub, userStub);
	}
	

	@Test
	void testCalculateRevenue() {
		try {
			assertEquals(70.0, report.calculateRevenue(), 0.001);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testCalculateProfit() {
		try {
			assertEquals(21.0, report.calculateProfit(), 0.01);
		} catch (Exception e) {
			fail();
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
