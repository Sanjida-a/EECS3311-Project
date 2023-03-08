package Unit;

import middleLayer.Pharmacist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PharmacistTest {

    @Test
    void getPharmacistUser() {
    }

    @Test
    void setPharmacistUser() {
    }

    @Test
    void addPatient() throws Exception {
        Pharmacist p = new Pharmacist(1234567890, 12345678);
        p.addPatient("John", "Doe", "12 Dreamville rd", 1357924680, 217896421, 20021010);
        assertEquals("[First name: John, Last name: Doe, Address: 12 Dreamville rd, Phone Number: 1357924680, Health Card: 217896421, Date of birth: 20021010\n]", p.searchPatientByName("John", "FirstName").toString());
    }

    @Test
    void searchPatientByName() {
        Pharmacist p = new Pharmacist(1234567890, 12345678);
        assertEquals("[First name: Smith, Last name: John, Address: 5324 Yonge St, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", p.searchPatientByName("Smith", "FirstName").toString());
    }
}