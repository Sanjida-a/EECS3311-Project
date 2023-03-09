package testCases.IntegrationTests;

import databaseDAO.superDAO;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfPatientsTest {
    static String pass = "user123";  // TA please change this according to your mySQL password in order for the tests to work

    @Test
   void getPatientsList(){
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListOfPatients val = ListOfPatients.getInstance();
        ArrayList<Patient> originalList = val.getAllPatientsList();
        ArrayList<Patient> newList = val.getAllPatientsList();
        assertEquals(newList.get(0).toString(), val.searchPatientWithID(1111122222).toString());
    }
}
