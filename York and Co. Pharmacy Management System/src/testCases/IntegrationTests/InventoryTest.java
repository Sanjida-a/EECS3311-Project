package testCases.IntegrationTests;


import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseDAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import middleLayer.MerchandiseInventory.*;

import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private MerchandiseDAO _merDAO;
    
    //beforeAll is just used to established a connection with the database before all tests
  	@BeforeAll
  	public static void before() {
  		try {
  			superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
  		} catch (Exception e) {
  			e.printStackTrace();
  		} 
  		
  	}

    @Test
    void getInstance() {
    }

    @Test
    void getOnlyOTCMerchandiseTest(){
       
        Inventory val = Inventory.getInstance();

        ArrayList<Merchandise> onlyOTC = val.getOnlyOTCMerchandise();
    
        int i = 0;
        for (i = 0; i < onlyOTC.size(); i++) {
            assertEquals(onlyOTC.get(i).getisOTC(), true);
        }
    }

    @Test
    void alphabetically(){
       
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> listOfMedicine = val.getMerchandise();


        ArrayList<String> namesOnly = new ArrayList<String>();
        for (int i = 0; i < listOfMedicine.size(); i++) {
            namesOnly.add(listOfMedicine.get(i).getName());
        }

        Collections.sort(namesOnly);

        ArrayList<Merchandise> sortedAlphaList = val.displayAlphabetically(listOfMedicine);
        for (int i = 0; i < listOfMedicine.size(); i++) {
            assertEquals(namesOnly.get(i), sortedAlphaList.get(i).getName());
        }

    }

    @Test
    void byQuantity(){
        
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> listOfMedicine = val.getMerchandise();


        ArrayList<Integer> quantitiesOnly = new ArrayList<Integer>();
        for (int i = 0; i < listOfMedicine.size(); i++) {
            quantitiesOnly.add(listOfMedicine.get(i).getQuantity());
        }

        Collections.sort(quantitiesOnly);

        ArrayList<Merchandise> sortedQuantityList = val.displayByQuantity(listOfMedicine);
        for (int i = 0; i < listOfMedicine.size(); i++) {
            assertEquals(quantitiesOnly.get(i), sortedQuantityList.get(i).getQuantity());
        }

    }

    @Test
    void byPrice(){
       
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> listOfMedicine = val.getMerchandise();


        ArrayList<Double> pricesOnly = new ArrayList<Double>();
        for (int i = 0; i < listOfMedicine.size(); i++) {
            pricesOnly.add(listOfMedicine.get(i).getPrice());
        }

        Collections.sort(pricesOnly);

        ArrayList<Merchandise> sortedPriceList = val.displayByPrice(listOfMedicine);
        for (int i = 0; i < listOfMedicine.size(); i++) {
            assertEquals(pricesOnly.get(i), sortedPriceList.get(i).getPrice());
        }


    }

    @Test
    void increaseQuantity(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        try {
			val.increaseQuantity(originalList.get(0).getMedicationID(),10);
			ArrayList<Merchandise> newList = val.getMerchandise();
	        assertEquals(originalList.get(0).getQuantity() + 10, newList.get(0).getQuantity());
	        
	        // back to normal
	        val.decreaseQuantity(originalList.get(0).getMedicationID(), 10);
		} catch (Exception e) {
			
		}
    }
    @Test
    void increaseQuantityInvalid(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        try {
        	val.increaseQuantity(originalList.size() + 1,10);
            assertEquals(false, val.increaseQuantity(originalList.size() + 1,10));
        }
        catch (Exception e) {
        	
        }
    }
    
    @Test
    void increaseQuantityInvalid2(){ // negative quantity
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        try {
        	assertThrows(Exception.class, () -> val.increaseQuantity(originalList.size(),-10));
        }
        catch (Exception e) {
        }
    }


    @Test
    void decreaseQuantity(){ //need condition
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
      
        
        if (originalList.get(0).getQuantity() >= 1) {
        	try {
        		val.decreaseQuantity(originalList.get(0).getMedicationID(),1);
            	ArrayList<Merchandise> newList = val.getMerchandise();
                assertEquals(originalList.get(0).getQuantity()-1, newList.get(0).getQuantity());
                
                //back to normal
                val.increaseQuantity(originalList.get(0).getMedicationID(), 1);
        	}
			catch (Exception e) {
			 	
			}
        }
        else {
        	try {
        		val.decreaseQuantity(originalList.get(0).getMedicationID(),1);
            	ArrayList<Merchandise> newList = val.getMerchandise();
                assertEquals(originalList.get(0).getQuantity(), newList.get(0).getQuantity());
        	}
            catch (Exception e) {
            	
            }
        }
    }


    @Test
    void decreaseQuantityInvalid(){ // by 7
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        try {
        	val.decreaseQuantity(originalList.get(0).getMedicationID(),originalList.get(0).getQuantity()+1);
            ArrayList<Merchandise> newList = val.getMerchandise();
            assertEquals(originalList.get(0).getQuantity(), newList.get(0).getQuantity());
        }
        catch (Exception e) {
        	
        }

    }
    
    @Test
    void decreaseQuantityInvalid2(){ // negative quantity
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        try {
        	assertThrows(Exception.class, () -> val.decreaseQuantity(originalList.get(0).getMedicationID(), -10));
        }
        catch (Exception e) {
        }

    }

    @Test
    void delete(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        Merchandise deletedM = originalList.get(originalList.size()-1);
        
        val.delete(originalList.size());
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(originalList.size() - 1, newList.size());
        
        // back to normal
       	deletedM.setIsValid(true);
       	try {
			_merDAO = new MerchandiseDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
       	_merDAO.updateMedicationInDatabase(deletedM.getMedicationID(), deletedM);


    }

    @Test
    void deleteInvalid(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.delete(100);
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(originalList.size(), newList.size());
    }
    @Test
    void searchByID(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(originalList.get(0).toString(), val.searchMerchandiseWithID(1).toString());
    }

    @Test
    void searchByIDInvalid(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        assertEquals(null, val.searchMerchandiseWithID(originalList.size() + 1));
    }
    @Test
    void modifyPrice(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        double originalPrice = originalList.get(0).getPrice();
        try { 
	        val.modifyMedicationPrice(originalList.get(0).getMedicationID(), 10.00);
	        ArrayList<Merchandise> newList = val.getMerchandise();
	        assertEquals(10.00, newList.get(0).getPrice());
	        
	        // back to normal
	       	val.modifyMedicationPrice(originalList.get(0).getMedicationID(), originalPrice);
        }
        catch (Exception e) {
        	
        }
       
    }

    @Test
    void modifyPriceInvalid(){
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        try {
	        val.modifyMedicationPrice(100, 100.00);
	        ArrayList<Merchandise> newList = val.getMerchandise();
	        
	        for (int i = 0 ; i< originalList.size(); i++) {
	        	 assertEquals(originalList.get(0).getPrice(), newList.get(0).getPrice());
	        }
        }
        catch (Exception e) {
        	
        }
       
    }
    
    @Test
    void modifyPriceInvalid2(){ // negative price
    	
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        try {
	        assertThrows(Exception.class, () -> val.modifyMedicationPrice(originalList.get(0).getMedicationID(), -10));
        }
        catch (Exception e) {
        }
       
    }

    @Test
    void modifyDescription(){
       
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        String originalDescription = originalList.get(0).getDescription();
        val.modifyMedicationDescription(originalList.get(0).getMedicationID(), "howdy");
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals("howdy", newList.get(0).getDescription());
        
     // back to normal
       	val.modifyMedicationDescription(originalList.get(0).getMedicationID(), originalDescription);
    }

    @Test
    void modifyDescriptionInvalid(){
        
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.modifyMedicationDescription(100, "howdy");
        ArrayList<Merchandise> newList = val.getMerchandise();
        
        for (int i = 0 ; i< originalList.size(); i++) {
        	assertEquals(originalList.get(i).getDescription(), newList.get(i).getDescription());
        }
        
    }

    @Test
    void modifyName() {
        
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        String originalName = originalList.get(0).getName();
        
        try {
        	 val.modifyMedicationName(originalList.get(0).getMedicationID(), "Buckleys");

             // back to normal
            val.modifyMedicationName(originalList.get(0).getMedicationID(), originalName);
        }
        catch (Exception e) {
        	
        }
    }

    @Test
    void modifyNameInvalid() {
       
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        try {
        	val.modifyMedicationName(100, "Buckleys");
            ArrayList<Merchandise> newList = val.getMerchandise();
            
            for (int i = 0 ; i< originalList.size(); i++) {
            	assertEquals(originalList.get(i).getName(), newList.get(i).getName());
            }
        }
        catch (Exception e) {
        }
    }
}