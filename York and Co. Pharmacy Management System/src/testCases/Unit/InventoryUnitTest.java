package testCases.Unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaseDAO.*;
import databaseDAO.MerchandiseData.MerchandiseStub;
import middleLayer.NegativeInputException;
import middleLayer.MerchandiseInventory.*;


public class InventoryUnitTest {
	private static Inventory val;
	private static MerchandiseStub mStub;
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeEach
	public void before() {
		try {
			//superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
			mStub = new MerchandiseStub();
			val = Inventory.getInstance(mStub);
			val.set_merDAO(mStub);

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	@Test
    void getInstanceTest() {
        Inventory subject1 = Inventory.getInstance();
        Inventory subject2 = Inventory.getInstance();
        assertTrue(subject1 == subject2);	//compares the address of subject1 and subject2
        									//should be same since Inventory is Singleton
    }
	
	@Test 
	void searchAllValidAndInvalidMerchandiseWithIDTest1() {
		Merchandise expected = new Merchandise(1, "pill1", 10, 2.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
		
		assertEquals(expected, val.searchAllValidAndInvalidMerchandiseWithID(1));
	}
	
	@Test
	void searchAllValidAndInvalidMerchandiseWithIDTest2() {
		Merchandise expected = new Merchandise(5, "pill5", 6, 3.00, MERCHANDISE_TYPE.SINUS, MERCHANDISE_FORM.LIQUID, true, null, false);
		assertEquals(expected, val.searchAllValidAndInvalidMerchandiseWithID(5));
	}
	
	@Test
	void searchAllValidAndInvalidMerchandiseWithIDTest3() {
		assertEquals(null, val.searchAllValidAndInvalidMerchandiseWithID(99));
	}



    @Test
    void getOnlyOTCMerchandiseTest(){
        ArrayList<Merchandise> subject1 = val.getOnlyOTCMerchandise();
		ArrayList<Merchandise> comparator1 = new ArrayList<Merchandise>();
		comparator1.add(mStub.getListOfMerchandise().get(0));
		comparator1.add(mStub.getListOfMerchandise().get(1));

		assertEquals(subject1, comparator1);
    }

    

    @Test
    void increaseQuantityTest1(){

    	try {
			val.increaseQuantity(1,10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
        assertEquals(20,val.getMerchandise().get(0).getQuantity());
    	
    }
    
    @Test
    void increaseQuantityTest2() {
    	String expected = "Quantity to increase by must be a non-negative number!";
    	String result = null;
    	try {
			val.increaseQuantity(1, -1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals(expected, result);
    }
    
    @Test
    void increaseQuantityTest3() {
    	String expected = "Increase unsuccessful. No such medication currently exists in the inventory. See current inventory";
    	String result = null;
    	try {
			val.increaseQuantity(6, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals(expected, result);
    }


    @Test
    void decreaseQuantityTest1(){ 

    	try {
			val.decreaseQuantity(1, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
    	assertEquals(0, val.getValidAndInvalidMerchandise().get(0).getQuantity());
        
        
    }

    @Test
    void decreaseQuantityTest2(){ 

    	assertThrows(NegativeInputException.class, () -> val.decreaseQuantity(1, -1));
    }
    @Test
    void decreaseQuantityTest3(){
    	String result = null;
    	try {
			val.decreaseQuantity(10, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals("Decrease unsuccessful. No such medication currently exists in the inventory. See inventory", result);
    }
    @Test
    void decreaseQuantityTest4(){
    	String result = null;
    	try {
			val.decreaseQuantity(1, 11);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals("Decrease unsuccessful. There is not enough quantity of the medication to decrease by " + 11 + ". See inventory", result);
    }
    @Test
    void decreaseQuantityTest5(){
    	try {
			assertTrue(val.decreaseQuantity(1, 8));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
    }
    

    @Test
    void deleteTest1(){
    	try {
			val.delete(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
    	assertFalse(val.getValidAndInvalidMerchandise().get(0).getisValid());
    }
    @Test
    void deleteTest2(){
    	String result = null;
    	try {
			val.delete(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals("Remove unsuccessful. No such medication currently exists in the inventory. See current inventory", result);
    }

    @Test
    void addToInventoryTest1(){
    	Merchandise m1 = new Merchandise(6, "ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
    	try {
			val.addToInventory(m1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
    	
    	assertEquals(m1, val.getValidAndInvalidMerchandise().get(5));
    }
    
    @Test
    void addToInventoryTest2() {
        Merchandise m1 = new Merchandise(6, "ASPIRIN", 10, -15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
        String result = null;
        try {
			val.addToInventory(m1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
        assertEquals("Price must be a non-negative number!", result);
  
    }
    @Test
    void addToInventoryTest3() {
        Merchandise m1 = new Merchandise(6, "ASPIRIN", -10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
        String result = null;
        try {
			val.addToInventory(m1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
        assertEquals("Quantity must be a non-negative number!", result);
  
    }
    @Test
    void addToInventoryTest4() {
        Merchandise m1 = new Merchandise(6, "ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
        String result = null;
        try {
			val.addToInventory(m1);
			val.addToInventory(m1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
        assertEquals("Add unsuccessful. The medication (same name, type, form and OTC/Rx) already exists in the inventory. See current inventory", result);
  
    }
    @Test
    void addToInventoryTest5() {
    	try {
			val.delete(2);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			fail();
		}

    	Merchandise merc5 = new Merchandise(5, "pill5", 11, 5.00, MERCHANDISE_TYPE.SINUS, MERCHANDISE_FORM.LIQUID, true, null, true);

        try {
			val.addToInventory(merc5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
        assertTrue(val.getValidAndInvalidMerchandise().get(4).isValid());
        assertEquals(11,val.getValidAndInvalidMerchandise().get(4).getQuantity());
        assertEquals(5.00, val.getValidAndInvalidMerchandise().get(4).getPrice(), 0.001);
  
    }
    @Test
    void isMedAddedBeforeTest1() {
    	mStub.allInventoryStub.get(0).setIsValid(false);
    	Merchandise merc1 = new Merchandise(1, "pill1", 10, 2.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	assertEquals(1, val.isMedAddedBefore(merc1));
    }
    @Test
    void isMedAddedBeforeTest2() {
    	
    	Merchandise merc1 = new Merchandise(1, "pill1", 10, 2.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	assertEquals(1, val.isMedAddedBefore(merc1));
    }
    @Test
    void isMedAddedBeforeTest3() {
    	
    	Merchandise merc1 = new Merchandise(6, "pill6", 10, 2.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	assertEquals(-1, val.isMedAddedBefore(merc1));
    }
    @Test
    
    void searchMerchandiseWithIDTest1(){

        Merchandise m = new Merchandise(1, "pill1", 10, 2.0, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
        assertEquals(m.toString(), val.searchMerchandiseWithID(1).toString());

    }
    
    @Test
    void searchMerchandiseWithIDTest2() {
        assertTrue(null == val.searchMerchandiseWithID(6));
    }
    
    @Test
    void modifyMedicationNameTest1() {
    	try {
			val.modifyMedicationName(1, "new pill");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
    	assertEquals("new pill", val.getValidAndInvalidMerchandise().get(0).getName());
        
    }
    
    @Test
    void modifyMedicationNameTest2() {
    	String result = null;
    	try {
			val.modifyMedicationName(7, "new pill");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals("Modification unsuccessful. No such medication currently exists in the inventory. See current inventory", result);
        
    }
    @Test
    void modifyMedicationNameTest3() {
    	Merchandise merc1 = new Merchandise(6, "new pill", 10, 2.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	try {
			val.addToInventory(merc1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			fail();
		}
    	String result = null;
    	try {
			val.modifyMedicationName(6, "pill1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals("Modification unsuccessful. The medication (same name, type, form and OTC/Rx) already exists in the inventory.", result);
        
    }

    @Test
    void modifyMedicationPrice1(){
    	try {
			val.modifyMedicationPrice(1, 20.00);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
    	assertEquals(20.00, val.getMerchandise().get(0).getPrice(), 0.001);
    }
    
    @Test
    void modifyMedicationPrice2(){
    	assertThrows(NegativeInputException.class , () -> val.modifyMedicationPrice(1, -10.00));
    }
    
    @Test
    void modifyMedicationPrice3(){
    	String result = null;
    	try {
			val.modifyMedicationPrice(10, 10.00);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals("Modification unsuccessful. No such medication currently exists in the inventory.", result);
    }
    
    @Test
    void modifyMedicationDescription1() {
    	try {
			val.modifyMedicationDescription(1, "test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
    	assertEquals("test", val.getValidAndInvalidMerchandise().get(0).getDescription());
    }
    @Test
    void modifyMedicationDescription2() {
    	String result = null;
    	try {
			val.modifyMedicationDescription(10, "test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
    	assertEquals("Modification unsuccessful. No such medication currently exists in the inventory.", result);
    }
    

}
