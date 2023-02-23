import org.junit.jupiter.api.Test;
import middleLayer.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void getInstance() {
    }

    @Test
    void display() {
        Inventory val = new Inventory();
        assertEquals("ADVIL, 10, 5.0, COLD, LIQUID, true \n" +
                "TYLENOL, 5, 8.0, FEVER, TABLET, true \n" +
                "ADVIL, 10, 5.0, COLD, TABLET, true \n" +
                "TYLENOL, 10, 5.0, COLD, LIQUID, true \n" +
                "PILL1, 10, 5.0, COLD, LIQUID, false \n" +
                "PILL2, 10, 5.0, FEVER, TABLET, false \n",val.display());
    }

    @Test
    void increaseQuantity() {
        Inventory val = new Inventory();
        assertEquals(true, val.increaseQuantity("ADVIL", 20, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
    }
    @Test
    void increaseQuantityFalse() {
        Inventory val = new Inventory();
        assertEquals(false, val.increaseQuantity("PILL6", 20, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
    }

    @Test
    void decreaseQuantity0() { //if quantity is equal
        Inventory val = new Inventory();
        boolean[] testCase = {true, true, true};
        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
    }
    @Test
    void decreaseQuantity1() { //if quantity is less
        Inventory val = new Inventory();
        boolean[] testCase = {true, true, false};
        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 5, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
    }
    @Test
    void decreaseQuantity2() { //if quantity is more
        Inventory val = new Inventory();
        boolean[] testCase = {false, false, false};
        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 13, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
    }
    @Test
    void decreaseQuantity3() { //if quantity hits 2
        Inventory val = new Inventory();
        boolean[] testCase = {true, true, true};
        assertEquals(Arrays.toString(testCase), Arrays.toString(val.decreaseQuantity("ADVIL", 8, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true)));
    }
    @Test
    void delete() {
        Inventory val = new Inventory();
        assertEquals(true, val.delete("ADVIL", MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
    }
    @Test
    void delete2() {
        Inventory val = new Inventory();
        assertEquals(false, val.delete("BUCKLEYS", MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true));
    }

    @Test
    void addToInventory() {
        Inventory val = new Inventory();
        Merchandise m = new Merchandise("ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true);
       assertEquals(true, val.addToInventory(m));

    }

    @Test
    void addToInventory1() {
        Inventory val = new Inventory();
        Merchandise m = new Merchandise("ADVIL", 10, 5.0, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true);
        assertEquals(false, val.addToInventory(m));

    }

    @Test
    void getMerchandise() {
    }
}