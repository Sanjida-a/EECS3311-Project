package testCases.IntegrationTests;


import databaseDAO.superDAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import middleLayer.*;
import middleLayer.Users.*;

import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;



class OwnerTest {
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello@123456");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	//**NOTE** AddPatient method tested in PharmacistTest.java
	
//    @Test
//    void getOwnerUserTest() {
//		
//    	Owner subject1 = new Owner(0, 0);
//    	Owner subject2 = subject1.getOwnerUser();
//    	assertEquals(subject1, subject2);
//    }
//    
//    @Test 
//    void constructorTest() {
//		
//    	Owner subject1 = new Owner(0, 0);
//    	assertEquals(subject1.username, 0);
//    	assertEquals(0, subject1.password);
//    }
//    
//    @Test
//    void setOwnerUserTest() {
//		
//    	Owner subject1 = new Owner(0, 0);
//    	Owner subject2 = new Owner(1, 1);
//    	subject1.setOwnerUser(subject2);
//    	assertEquals(subject1.username, subject2.username);
//    	assertEquals(subject1.password, subject2.password);
//    }

    
    

    

}