package Unit;

import org.junit.jupiter.api.Test;
import middleLayer.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    void getOwnerUser() {
    }

    @Test
    void setOwnerUser() {
    }

    @Test
    void searchName(){
        Owner o = new Owner(1111111111, 11111111);
       assertEquals("[First name: Smith, Last name: John, Address: 5324 Yonge St, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", o.searchPatientByName("Smith", "FirstName").toString());
    }

    @Test
    void searchNameFalse(){ //false
        Owner o = new Owner(1111111111, 11111111);
        assertEquals("[]", o.searchPatientByName("John", "FirstName").toString());
    }

//    @Test
//    void searchOTCMedicineByName() {
//        Owner val = new Owner(1111111111, 11111111);
//        assertEquals("[Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false\n" +
//                "]", val.searchOTCMedicineByName ("PILL1").toString());
//    }
//
//
//    @Test
//    void searchOTCMedicineByType() {
//        Owner val = new Owner(1111111111, 11111111);
//        assertEquals("[Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true\n" +
//                ", Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true\n" +
//                ", Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true\n" +
//                ", Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false\n" +
//                "]", val.searchOTCMedicineByType (MERCHANDISE_TYPE.COLD).toString());
//    }
}