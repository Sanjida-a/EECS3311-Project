package testCases.IntegrationTests;

import databaseDAO.MerchandiseDAO;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import org.junit.platform.commons.annotation.Testable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private MerchandiseDAO _merDAO;

    @Test
    void getInstance() {
    }

    @Test
    void display() {
        Inventory val = Inventory.getInstance();
        assertEquals("[ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n" +
                ", ID: 6, Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false, Description: null\n" +
                "]",val.getMerchandise().toString());
    }

    @Test
    void onlyOTC(){
        Inventory val = Inventory.getInstance();
        assertEquals("[ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                "]",val.getOnlyOTCMerchandise().toString());
    }

    @Test
    void alphabetically(){
        Inventory val = Inventory.getInstance();
        assertEquals("[ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n" +
                ", ID: 6, Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false, Description: null\n" +
                ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                "]",val.displayAlphabetically(val.getMerchandise()).toString());
    }

    @Test
    void byQuantity(){
        Inventory val = Inventory.getInstance();
        assertEquals("[ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n" +
                ", ID: 6, Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false, Description: null\n" +
                "]",val.displayByQuantity(val.getMerchandise()).toString());
    }

    @Test
    void byPrice(){
        Inventory val = Inventory.getInstance();
        assertEquals("[ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
              //  ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n" +
                ", ID: 6, Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false, Description: null\n" +
                ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
                "]",val.displayByPrice(val.getMerchandise()).toString());
    }

    @Test
    void increaseQuantity(){
        Inventory val = Inventory.getInstance();
        assertEquals(true, val.increaseQuantity(2,10));
    }

    @Test
    void decreaseQuantity(){ //need condition
        Inventory val = Inventory.getInstance();
        boolean[] testCase = {false, true, true};
        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity(2,3)));
    }

    @Test
    void decreaseQuantity1(){ // by 7
        Inventory val = Inventory.getInstance();
        boolean[] testCase = {false, true, true};
        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity(2,7)));
    }

    @Test
    void addTo(){
        Inventory val = Inventory.getInstance();
        //Merchandise m = new Merchandise(7, "ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
        Merchandise m = new Merchandise(7, "Buckleys",  5,  3.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, null, true);
        assertEquals(true, val.addToInventory(m));
    }
    @Test
    void delete(){
        Inventory val = Inventory.getInstance();
        Merchandise m = new Merchandise(7, "ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
        assertEquals(true, val.addToInventory(m));
        assertEquals(true, val.delete(7));
    }
    @Test
    void searchByID(){
        Inventory val = Inventory.getInstance();
        Merchandise m = new Merchandise(5, "PILL1", 10, 5.0, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID,false, null, true);
        assertEquals(m.toString(), val.searchMerchandiseWithID(5).toString());
    }

    @Test
    void modifyPrice(){
        Inventory val = Inventory.getInstance();
        assertEquals(true, val.modifyMedicationPrice(5,19.0));
    }



//
//    @Test
//    void increaseQuantity() {
//        Inventory val = new Inventory();
//        assertTrue(true, val.increaseQuantity("ADVIL", 20, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
//    }
//    @Test
//    void increaseQuantityFalse() {
//        Inventory val = new Inventory();
//        assertEquals(false, val.increaseQuantity("PILL6", 20, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
//    }
//
//    @Test
//    void decreaseQuantity0() { //if quantity is equal
//        Inventory val = new Inventory();
//        boolean[] testCase = {true, true, true};
//        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
//    }
//    @Test
//    void decreaseQuantity1() { //if quantity is less
//        Inventory val = new Inventory();
//        boolean[] testCase = {true, true, false};
//        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 5, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
//    }
//    @Test
//    void decreaseQuantity2() { //if quantity is more
//        Inventory val = new Inventory();
//        boolean[] testCase = {false, false, false};
//        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 13, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
//    }
//    @Test
//    void decreaseQuantity3() { //if quantity hits 2
//        Inventory val = new Inventory();
//        boolean[] testCase = {true, true, true};
//        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 8, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
//    }
//    @Test
//    void delete() {
//        Inventory val = new Inventory();
//        assertEquals(true, val.delete("ADVIL", MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
//    }
//    @Test
//    void delete2() {
//        Inventory val = new Inventory();
//        assertEquals(false, val.delete("BUCKLEYS", MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
//    }
//
//    @Test
//    void addToInventory() {
//        Inventory val = new Inventory();
//        Merchandise m = new Merchandise("ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true);
//       assertEquals(true, val.addToInventory(m));
//    }
//
//    @Test
//    void addToInventory1() {
//        Inventory val = new Inventory();
//        Merchandise m = new Merchandise("ADVIL", 10, 5.0, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true);
//        assertEquals(false, val.addToInventory(m));
//
//    }
//
//    @Test
//    void getMerchandise() {
//    }
}