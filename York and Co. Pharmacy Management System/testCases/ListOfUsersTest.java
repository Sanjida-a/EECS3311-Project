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

        patient2 = new Patient(null, null, null, 1112224444, 1111144444, 11222012);


    }

    @Test
    void getInstance(){

    }

    @Test
    void addPatientToListCheck() {
        patient1 = new Patient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
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

//    @Test
//    void getAllPatientUsersList() {
//        ListOfUsers val = new ListOfUsers();
//        val.getAllPatientUsersList();
//        assertEquals(null, val);
//    }
    @Test
    void getAllUsersListCheck() {
        ListOfUsers val = new ListOfUsers();

        assertEquals("[Owner, Pharmacist, Patient]",val.getAllUsersList().toString());
    }
//
//    @Test
//    void getAllUsersListCheck2(){
//        patient1 = new Patient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
//        ListOfUsers val = new ListOfUsers();
//        val.addPatientToList(patient1);
//        assertEquals("Owner, Pharmacist, Patient, Patient", val.getAllUsersList());
//    }

    @Test
    void getAllPatientUsersList() {
        ListOfUsers val = new ListOfUsers();
        assertEquals("[]",val.getAllPatientUsersList().toString());

    }
    @Test
    void getAllPatientUsersList2() {
        patient1 = new Patient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
        ListOfUsers val = new ListOfUsers();
        val.addPatientToList(patient1);
        assertEquals("[]",val.getAllPatientUsersList());

    }

//    @Test
//    void setAllPatientUsersList() {
//    }
//
//    @Test
//    void getAllUsersList() {
//    }


}