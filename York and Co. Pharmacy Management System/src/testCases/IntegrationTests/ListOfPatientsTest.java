package testCases.IntegrationTests;

import databaseDAO.superDAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import middleLayer.Users.*;

import org.junit.platform.commons.annotation.Testable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfPatientsTest {
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

    @Test
    void modifyPatientInfo() {
       
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
        try {
        	val.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
            ArrayList<Patient> newList = val.getAllPatientsList();
            assertEquals("JO", newList.get(0).getFirstName());
            
            //back to normal
            fname.setText("Smith");
            val.modifyPatientDetails(1111122222, fname, lname, phoneNum, address);
        }
        catch (Exception e) {
        	
        }
        
    }
    
    @Test
    void modifyPatientInfoInvalid() { // negative phoneNum
       
        ListOfPatients val = ListOfPatients.getInstance();
        JTextField fname = new JTextField();
        fname.setText("");
        JTextField lname = new JTextField();
        JTextField phoneNum = new JTextField();
        JTextField address = new JTextField();
        lname.setText("");
        phoneNum.setText("-555555");
        address.setText("");
        ArrayList<Patient> originalList = val.getAllPatientsList();
        
        assertThrows(Exception.class, () -> val.modifyPatientDetails(1111122222, fname, lname, phoneNum, address));
       

    }
    
}
