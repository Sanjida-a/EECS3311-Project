package testCases.IntegrationTests;

import databaseDAO.superDAO;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import middleLayer.Users.*;

import org.junit.platform.commons.annotation.Testable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfPatientsTest {
    static String pass = "hello@123456";  // TA please change this according to your mySQL password in order for the tests to work



    @Test
    void modifyPatientInfo() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListOfPatients val = ListOfPatients.getInstance();
        JTextField fname = new JTextField();
        fname.setText("jo");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("");
        address.setText("");
        ArrayList<Patient> originalList = val.getAllPatientsList();
        val.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
        ArrayList<Patient> newList = val.getAllPatientsList();
        assertEquals("jo", newList.get(0).getFirstName());
        
        //back to normal
        fname.setText("Smith");
        val.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);

    }
    
}
