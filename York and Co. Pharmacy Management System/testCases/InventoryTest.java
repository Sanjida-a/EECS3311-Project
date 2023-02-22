import org.junit.jupiter.api.Test;
import middleLayer.Inventory;
import middleLayer.MERCHANDISE_TYPE;
import middleLayer.MERCHANDISE_FORM;
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
    void decreaseQuantity() {
    }

    @Test
    void delete() {
    }

    @Test
    void addToInventory() {
    }

    @Test
    void getMerchandise() {
    }
}