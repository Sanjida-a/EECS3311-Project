package testCases.IntegrationTests;
import databaseDAO.superDAO;
import middleLayer.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {
<<<<<<< Updated upstream

    static String pass = "hello123";  // TA please change this according to your mySQL password in order for the tests to work
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
       // System.out.println(r.seeSummaryOfSales());
        assertEquals("Order number: 1 Medication ID: 1 Quantity bought: 2 Total price: 5.00\n", r.seeSummaryOfSales());
    }
}