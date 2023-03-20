package testCases.Unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaseDAO.*;
import databaseDAO.MerchandiseData.MerchandiseStub;
import middleLayer.MerchandiseInventory.*;


public class InventoryUnitTest {
	private static Inventory val;
	private static MerchandiseStub mStub;
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeAll
	public static void before() {
		try {
			//superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
			mStub = new MerchandiseStub();
			val = Inventory.getInstance(mStub);
			val.set_merDAO(mStub);

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@BeforeEach
	public void beforeEach() {
		val.set_merDAO(new MerchandiseStub());
	}
	
	@Test
    void getInstanceTest() {
        Inventory subject1 = Inventory.getInstance();
        Inventory subject2 = Inventory.getInstance();
        assertTrue(subject1 == subject2);	//compares the address of subject1 and subject2
        									//should be same since Inventory is Singleton
    }



    @Test
    void getOnlyOTCMerchandiseTest(){
       // Inventory val = Inventory.getInstance();
    	//MerchandiseStub mStub = new MerchandiseStub();
       // val.set_merDAO(new MerchandiseStub());
        ArrayList<Merchandise> subject1 = val.getOnlyOTCMerchandise();
		ArrayList<Merchandise> comparator1 = new ArrayList<Merchandise>();
		comparator1.add(mStub.getListOfMerchandise().get(0));
		comparator1.add(mStub.getListOfMerchandise().get(1));
		for(int i = 0; i < subject1.size(); i ++) {
			assertEquals(subject1.get(i), comparator1.get(i));
		}
    }

    @Test
    void displayAlphabeticallyTest(){
  
    	//Inventory inv = Inventory.getInstance();
    	ArrayList<Merchandise> subject1 = new ArrayList<Merchandise>();
    	ArrayList<Merchandise> comparator1 = new ArrayList<Merchandise>();
    	Merchandise merc1 = new Merchandise(1, "A", 10, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc2 = new Merchandise(2, "B", 10, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc3 = new Merchandise(3, "C", 10, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc4 = new Merchandise(4, "D", 10, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	
    	subject1.add(merc2);
    	subject1.add(merc3);
    	subject1.add(merc1);
    	subject1.add(merc4);
    	subject1 = val.displayAlphabetically(subject1);
    	comparator1.add(merc1);
    	comparator1.add(merc2);
       	comparator1.add(merc3);
       	comparator1.add(merc4);
       	
       	assertEquals(subject1.toString(), comparator1.toString());
    	
    	
    }

    @Test
    void displayByQuantityTest(){
        //Inventory inv = Inventory.getInstance();
       // MerchandiseStub mStub = new MerchandiseStub();
        //val.set_merDAO(mStub);
        ArrayList<Merchandise> subject1 = new ArrayList<Merchandise>();
    	ArrayList<Merchandise> comparator1 = new ArrayList<Merchandise>();
    	Merchandise merc1 = new Merchandise(1, "A", 10, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc2 = new Merchandise(2, "B", 11, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc3 = new Merchandise(3, "C", 12, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc4 = new Merchandise(4, "D", 13, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	
    	subject1.add(merc2);
    	subject1.add(merc3);
    	subject1.add(merc1);
    	subject1.add(merc4);
    	subject1 = val.displayAlphabetically(subject1);
    	comparator1.add(merc1);
    	comparator1.add(merc2);
       	comparator1.add(merc3);
       	comparator1.add(merc4);
       	assertEquals(subject1.toString(), comparator1.toString());
        
    }

    @Test
    void displayByPriceTest(){
       // Inventory val = Inventory.getInstance();
       // MerchandiseStub mStub = new MerchandiseStub();
       // val.set_merDAO(mStub);
        ArrayList<Merchandise> subject1 = new ArrayList<Merchandise>();
    	ArrayList<Merchandise> comparator1 = new ArrayList<Merchandise>();
    	Merchandise merc1 = new Merchandise(1, "A", 10, 10, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc2 = new Merchandise(2, "B", 10, 11, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc3 = new Merchandise(3, "C", 10, 12, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	Merchandise merc4 = new Merchandise(4, "D", 10, 13, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
    	
    	subject1.add(merc2);
    	subject1.add(merc3);
    	subject1.add(merc1);
    	subject1.add(merc4);
    	subject1 = val.displayAlphabetically(subject1);
    	comparator1.add(merc1);
    	comparator1.add(merc2);
       	comparator1.add(merc3);
       	comparator1.add(merc4);
       	assertEquals(subject1.toString(), comparator1.toString());
        
    }

    @Test
    void increaseQuantityTest1(){
       // Inventory val = Inventory.getInstance();
      //  MerchandiseStub mStub = new MerchandiseStub();
      //  val.set_merDAO(mStub);
        
        try {
        	 assertTrue(val.increaseQuantity(1,10));
             assertFalse(val.increaseQuantity(5, 10));
             assertEquals(val.getMerchandise().get(0).toString(), new Merchandise(1, "pill1", 20, 2.0, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true).toString() );
        }
        catch (Exception e) {
        	
        }
        
    }


    @Test
    void decreaseQuantityTest1(){ //need condition

       // Inventory val = Inventory.getInstance();
      //  MerchandiseStub mStub = new MerchandiseStub();
      //  val.set_merDAO(mStub);
        boolean[] result = new boolean[3];
        boolean[] expected = {false, false, false};
        
        try {
        	result = val.decreaseQuantity(1, 11);
            
            assertArrayEquals(expected, result);
            expected = new boolean[]{true, true, false};
            result = val.decreaseQuantity(2, 2);
            
            assertArrayEquals(expected, result);
            expected = new boolean[] {true, true, true};
            result = val.decreaseQuantity(3, 6);
            
            assertArrayEquals(expected, result);
            expected = new boolean[] {false, true, false};
            result = val.decreaseQuantity(5, 1);
            
            assertArrayEquals(expected, result);
            result = val.decreaseQuantity(4, -1);
            
            assertArrayEquals(expected, result);
        }
        catch (Exception e) {
        	
        }
        
        
    }

    @Test
    void decreaseQuantityTest2(){ // by 7
       // Inventory val = Inventory.getInstance();
      //  MerchandiseStub mStub = new MerchandiseStub();
      //  val.set_merDAO(mStub);
        boolean[] result = new boolean[3];
        boolean[] expected;
        expected = new boolean[] {false, true, false};
        
        try {
	        result = val.decreaseQuantity(5, 1);
	        assertArrayEquals(expected, result);
	        result = val.decreaseQuantity(4, -1);
	        assertArrayEquals(expected, result);
        }
        catch (Exception e) {
        	
        }
    }

    @Test
    void deleteTest(){
       // Inventory val = Inventory.getInstance();
      //  MerchandiseStub mStub = new MerchandiseStub();
       // val.set_merDAO(mStub);
        assertEquals(true, val.delete(1));
        assertEquals(false, val.delete(5));
    }

    @Test
    void addToInventoryTest1(){
      //  Inventory val = Inventory.getInstance();
     //   MerchandiseStub mStub = new MerchandiseStub();
     //   val.set_merDAO(mStub);
        Merchandise m = new Merchandise(5, "ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
        try {
	        assertEquals(true, val.addToInventory(m));
	        assertEquals(false, val.addToInventory(m));
        }
        catch (Exception e) {
        	
        }
    }
    
    @Test
    void addToInventoryTest2() {
    //	Inventory val = Inventory.getInstance();
     //   MerchandiseStub mStub = new MerchandiseStub();
    //    val.set_merDAO(mStub);
        Merchandise m1 = new Merchandise(5, "ASPIRIN", -10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
        Merchandise m2 = new Merchandise(6, "CEPACOL", 10, -15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
       
        assertThrows(Exception.class, () -> val.addToInventory(m1));
        assertThrows(Exception.class, () -> val.addToInventory(m2));
  
    }

    @Test
    void searchMerchandiseWithIDTest(){
      //  Inventory val = Inventory.getInstance();
       // MerchandiseStub mStub = new MerchandiseStub();
      //  val.set_merDAO(mStub);
        Merchandise m = new Merchandise(1, "pill1", 10, 2.0, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, "", true);
        assertEquals(m.toString(), val.searchMerchandiseWithID(1).toString());
        assertTrue(null == val.searchMerchandiseWithID(6));
    }
    
    @Test
    void modifyMedicationNameTest() {
       // Inventory val = Inventory.getInstance();
      //  MerchandiseStub mStub = new MerchandiseStub();
      //  val.set_merDAO(mStub);
        try {
        	assertFalse(val.modifyMedicationName(5, "subject1"));

        	Merchandise merc = val.getMerchandise().get(1);
        	merc.setName("subject2");
        	merc.setPrice(5.99);
        	merc.setType(MERCHANDISE_TYPE.COLD);
        	merc.setForm(MERCHANDISE_FORM.LIQUID);
     
        }catch(Exception ex) {
        	
        }
        
        assertThrows(Exception.class, () -> val.modifyMedicationName(1, "subject2"));
        try {
			assertTrue(val.modifyMedicationName(1, "subject1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertEquals(val.getMerchandise().get(0).getName(), "subject1");
        
    }

    @Test
    void modifyMedicationPrice(){
     //   Inventory val = Inventory.getInstance();
     //   MerchandiseStub mStub = new MerchandiseStub();
     //   val.set_merDAO(mStub);
        
        try {
	        assertFalse(val.modifyMedicationPrice(6, 11));
	        val.modifyMedicationPrice(1, 10.00);
	        assertEquals(10.00, val.getMerchandise().get(0).getPrice(), 0.001 );
        }
        catch (Exception e) {
        	
        }
    }
    
    @Test
    void modifyMedicationDescription() {
     //   Inventory val = Inventory.getInstance();
     //   MerchandiseStub mStub = new MerchandiseStub();
     //   val.set_merDAO(mStub);
    	assertFalse(val.modifyMedicationDescription(5, "description"));
    	assertTrue(val.modifyMedicationDescription(1, "description"));
    	assertEquals(val.getMerchandise().get(0).getDescription(), "description");
    }
}
