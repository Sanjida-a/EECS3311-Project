package testCases.IntegrationTests;


import databaseDAO.superDAO;
import middleLayer.*;
import org.junit.jupiter.api.Test;
import presentation.USER;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    static String pass = "ALVINTA12";  // TA please change this according to your mySQL password in order for the tests to work
    @Test
    void searchMedicineByName() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        Inventory inv = Inventory.getInstance();
        ArrayList<Merchandise> listToSearchFrom = inv.getMerchandise();
        ArrayList<Merchandise> namesOnly = new ArrayList<Merchandise>();
        for (int i = 0; i < listToSearchFrom.size();i ++){
            if (listToSearchFrom.get(i).getName().equals("ADVIL")){
                namesOnly.add(listToSearchFrom.get(i));
            }
        }
            assertEquals(namesOnly.toString(), o.searchMedicineByName("ADVIL", USER.OWNER).toString());
    }
    @Test
    void searchMedicineByNameNotFound() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        Inventory inv = Inventory.getInstance();
        ArrayList<Merchandise> listToSearchFrom = inv.getMerchandise();
        ArrayList<Merchandise> namesOnly = new ArrayList<Merchandise>();
        for (int i = 0; i < listToSearchFrom.size();i ++){
            if (listToSearchFrom.get(i).getName().equals("Buckleys")){
                namesOnly.add(listToSearchFrom.get(i));
            }
        }
        assertEquals(namesOnly.toString(), o.searchMedicineByName("Buckleys", USER.OWNER).toString());
    }
    @Test
    void searchMedicineByType() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        Inventory inv = Inventory.getInstance();
        ArrayList<Merchandise> listToSearchFrom = inv.getMerchandise();
        ArrayList<Merchandise> namesOnly = new ArrayList<Merchandise>();
        for (int i = 0; i < listToSearchFrom.size();i ++){
            if (listToSearchFrom.get(i).getType() == MERCHANDISE_TYPE.COLD){
                namesOnly.add(listToSearchFrom.get(i));
            }
        }
        assertEquals(namesOnly.toString(), o.searchMedicineByType(MERCHANDISE_TYPE.COLD, USER.OWNER).toString());
    }

    @Test
    void searchMedicineByTypeNotFound() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        Inventory inv = Inventory.getInstance();
        ArrayList<Merchandise> listToSearchFrom = inv.getMerchandise();
        ArrayList<Merchandise> namesOnly = new ArrayList<Merchandise>();
        for (int i = 0; i < listToSearchFrom.size();i ++){
            if (listToSearchFrom.get(i).getType() == MERCHANDISE_TYPE.COLD){
                namesOnly.add(listToSearchFrom.get(i));
            }
        }
        assertEquals(namesOnly.toString(), o.searchMedicineByType(MERCHANDISE_TYPE.COLD, USER.OWNER).toString());
    }
}