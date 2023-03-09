package testCases.IntegrationTests;
import middleLayer.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void calculateRevenue() throws Exception {
        Report r = new Report();
        assertEquals(5.0, r.calculateRevenue());
    }

    @Test
    void calculateProfit() throws Exception {
        Report r = new Report();
        assertEquals(1.5, r.calculateProfit());

    }

    @Test
    void seeSummaryOfSales() {
        Report r = new Report();
       // System.out.println(r.seeSummaryOfSales());
        assertEquals("Order number: 1 Medication ID: 1 Quantity bought: 2 Total price: 5.00\n", r.seeSummaryOfSales());
    }
}