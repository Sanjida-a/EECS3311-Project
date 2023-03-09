package testCases.IntegrationTests;
import databaseDAO.superDAO;
import middleLayer.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    static String pass = "ALVINTA12";  // TA please change this according to your mySQL password in order for the tests to work
    @Test
    void calculateRevenue() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        ListOfOrders l = ListOfOrders.getInstance();
        ArrayList<Order> allOrders = l.getListofAllOrders();
        double val = 0.0;
        for (int i = 0; i < allOrders.size(); i++){
            val += allOrders.get(i).getTotalPriceOfOrder();
        }
    assertEquals(val, r.calculateRevenue());
    }

    @Test
    void calculateProfit() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        ListOfOrders l = ListOfOrders.getInstance();
        ArrayList<Order> allOrders = l.getListofAllOrders();
        double val = 0.0;
        for (int i = 0; i < allOrders.size(); i++){
            val += allOrders.get(i).getTotalPriceOfOrder();
        }
        assertEquals(val * 0.3, r.calculateProfit());

    }

    @Test
    void seeSummaryOfSales() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Report r = new Report();
        ListOfOrders l = ListOfOrders.getInstance();
        ArrayList<Order> allOrders = l.getListofAllOrders();
        ArrayList<String> allOrdersString = new ArrayList<>();

       // System.out.println(allOrders.get(0).toString());

        String c ="";
       for (int i = 0; i < allOrders.size(); i++){
           c += allOrders.get(i).toString();
       }
        assertEquals(c, r.seeSummaryOfSales());
    }
}