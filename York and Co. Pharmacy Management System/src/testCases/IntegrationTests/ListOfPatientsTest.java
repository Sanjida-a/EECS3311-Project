package testCases.IntegrationTests;

import org.junit.jupiter.api.Test;
import middleLayer.*;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfPatientsTest {
    @Test
   void getPatientsList(){
        ArrayList<Patient> patientTest = new ArrayList<Patient>();
        ListOfPatients call = ListOfPatients.getInstance();
        Patient val = new Patient("Smith", "John", "5324 Yonge St", 1112223333,1111122222, 11111222);
       // Patient val1 = new Patient("Test", "Man", "5334 yonge St",  1112224444, 1111144444,  11222012);
        patientTest.add(val);
       // patientTest.add(val1);
        assertEquals(patientTest.toString(), call.getAllPatientsList().toString());
    }
}
