package testCases.IntegrationTests;
import databaseDAO.superDAO;
import middleLayer.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {
    static String pass = "hello123";
    @Test
    void calculateRevenue() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        assertEquals(5.0, r.calculateRevenue());
    }

    @Test
    void calculateProfit() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        assertEquals(1.5, r.calculateProfit());

    }

    @Test
    void seeSummaryOfSales() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        assertEquals("Order number: 1 Medication ID: 1 Quantity bought: 1 Total price: 5.000000\n", r.seeSummaryOfSales());
    }
}