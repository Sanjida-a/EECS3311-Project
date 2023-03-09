package testCases.IntegrationTests;


import middleLayer.MERCHANDISE_TYPE;
import middleLayer.Merchandise;
import middleLayer.Owner;
import middleLayer.User;
import org.junit.jupiter.api.Test;
import presentation.USER;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void searchMedicineByName() {
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        assertEquals("[ID: 1, Name: ADVIL, Quantity: 6, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n, ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n]",
                o.searchMedicineByName("ADVIL", USER.OWNER).toString());
    }
    @Test
    void searchMedicineByNameNotFound() {
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        assertEquals("[]", o.searchMedicineByName("Buckleys", USER.OWNER).toString());
    }
    @Test
    void searchMedicineByType() {
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        assertEquals("[ID: 1, Name: ADVIL, Quantity: 6, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n, ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n, ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
                        ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n]",
                o.searchMedicineByType(MERCHANDISE_TYPE.COLD, USER.OWNER).toString());
    }

    @Test
    void searchMedicineByTypeNotFound() {
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        assertEquals("[]", o.searchMedicineByName("fever", USER.OWNER).toString());
    }
}