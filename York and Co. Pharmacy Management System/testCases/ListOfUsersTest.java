import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import middleLayer.AuthenticateUser;
import middleLayer.ListOfUsers;
import middleLayer.Patient;
import middleLayer.User;
import middleLayer.Pharmacist;
import middleLayer.Owner;


import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ListOfUsersTest {

    private Patient patient1;
    private Patient patient2;
    private ListOfUsers listofUsers;
    private User owner;
    private User pharma1;

    void setUp() {
        patient1 = new Patient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
        patient2 = new Patient(null, null, null, 1112224444, 1111144444, 11222012);
        owner = new Owner(1111111111, 11111111);
        pharma1 = new Pharmacist(1234567890,12345678);

    }

    @Test
    void getInstance(){

    }

    @Test
    void addPatientToListCheck() {

        ListOfUsers val = new ListOfUsers();
        val.addPatientToList(patient1);
        assertEquals("Test",patient1.getFirstName());
        assertEquals("Man",patient1.getLastName());
        assertEquals("5334 yonge St",patient1.getAddress());
        assertEquals(1112224444,patient1.getPhoneNum());
        assertEquals(1111144444,patient1.getHealthCardNum());
        assertEquals(11222012,patient1.getDateOfBirth());

    }

//    @Test(expected=NullPointerException.class)
//    void addPatientToListCheck2() {
//
//        ListOfUsers val = new ListOfUsers();
//        val.addPatientToList(patient1);
//        assertNotNull(patient2.getFirstName());
//        assertNotNull(patient2.getLastName());
//        assertNotNull(patient2.getAddress());
//
//
//    }

    @Test
    void getAllPatientUsersList() {
        ListOfUsers val = new ListOfUsers();
        val.getAllPatientUsersList();
        assertEquals(null, val);
    }
    @Test
    void getAllUsersListCheck() {
        ListOfUsers val = new ListOfUsers();
        val.getAllUsersList();
        assertEquals(null, val);
    }

    @Test
    void getAllUsersListCheck2(){
        ListOfUsers val = new ListOfUsers();
        val.addPatientToList(patient1);
        assertEquals(patient1, val);
    }

//    @Test
//    void getInstance() {
//    }
//
//    @Test
//    void addPatientToList() {
//    }
//
//    @Test
//    void getAllPatientUsersList() {
//    }
//
//    @Test
//    void setAllPatientUsersList() {
//    }
//
//    @Test
//    void getAllUsersList() {
//    }


}