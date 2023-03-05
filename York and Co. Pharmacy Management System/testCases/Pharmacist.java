import org.junit.jupiter.api.Test;
import middleLayer.Pharmacist;
import static org.junit.jupiter.api.Assertions.*;
import middleLayer.MERCHANDISE_TYPE;
import middleLayer.ListOfUsers;
class PharmacistTest {
    private middleLayer.Patient patient1;
    @Test
    void getPharmacistUser() {
    }

    @Test
    void setPharmacistUser() {
    }

    @Test
    void addPatient() {
        patient1 = new middleLayer.Patient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
        ListOfUsers val = ListOfUsers.getInstance();
        val.addPatientToList(patient1);
        assertEquals("Test",patient1.getFirstName());
        assertEquals("Man",patient1.getLastName());
        assertEquals("5334 yonge St",patient1.getAddress());
        assertEquals(1112224444,patient1.getPhoneNum());
        assertEquals(1111144444,patient1.getHealthCardNum());
        assertEquals(11222012,patient1.getDateOfBirth());
    }

    @Test
    void searchOTCMedicineByName() {
        Pharmacist val = new Pharmacist(1234567890, 12345678);
        assertEquals("[Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true\n" +
                ", Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true\n" +
                "]", val.searchOTCMedicineByName ("ADVIL").toString());
    }

    @Test
    void searchOTCMedicineByType() {
        Pharmacist val = new Pharmacist(1234567890, 12345678);
        assertEquals("[Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true\n" +
                ", Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false\n" +
                "]", val.searchOTCMedicineByType (MERCHANDISE_TYPE.FEVER).toString());

    }
}