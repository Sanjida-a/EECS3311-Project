package testCases.Unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import middleLayer.MerchandiseInventory.MERCHANDISE_FORM;
import middleLayer.MerchandiseInventory.MERCHANDISE_TYPE;
import middleLayer.MerchandiseInventory.Merchandise;
import middleLayer.MerchandiseInventory.SortInventory;

public class SortInventoryUnitTest {
	private static SortInventory val = new SortInventory();
	@Test
    void displayAlphabeticallyTest(){
  

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
}
