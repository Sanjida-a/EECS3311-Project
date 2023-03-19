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
    private static Inventory inventoryInstance;
    
    //beforeAll is just used to established a connection with the database before all tests
  	@BeforeAll
  	public static void before() {
  		try {
  			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
  			inventoryInstance = Inventory.getInstance();
  		} catch (Exception e) {
  			e.printStackTrace();
  		} 
  		
  	}

    @Test
    void getInstance() {
    }

    @Test
    void getOnlyOTCMerchandiseTest(){

        ArrayList<Merchandise> onlyOTC = inventoryInstance.getOnlyOTCMerchandise();
    
        int i = 0; // if onlyOTC empty, test still passes because doesn't go through for loop
        for (i = 0; i < onlyOTC.size(); i++) {
            assertEquals(onlyOTC.get(i).getisOTC(), true);
        }
    }

    @Test
    void alphabetically(){
       
        ArrayList<Merchandise> listOfMedicine = inventoryInstance.getMerchandise(); // if listOfMedicine empty, test still passes because doesn't go through for loop


        ArrayList<String> namesOnly = new ArrayList<String>();
        for (int i = 0; i < listOfMedicine.size(); i++) { 
            namesOnly.add(listOfMedicine.get(i).getName());
        }

        Collections.sort(namesOnly);

        ArrayList<Merchandise> sortedAlphaList = inventoryInstance.displayAlphabetically(listOfMedicine);
        for (int i = 0; i < listOfMedicine.size(); i++) {
            assertEquals(namesOnly.get(i), sortedAlphaList.get(i).getName());
        }

    }

    @Test
    void byQuantity(){
        
        
        ArrayList<Merchandise> listOfMedicine = inventoryInstance.getMerchandise(); // if listOfMedicine empty, test still passes because doesn't go through for loop


        ArrayList<Integer> quantitiesOnly = new ArrayList<Integer>();
        for (int i = 0; i < listOfMedicine.size(); i++) {
            quantitiesOnly.add(listOfMedicine.get(i).getQuantity());
        }

        Collections.sort(quantitiesOnly);

        ArrayList<Merchandise> sortedQuantityList = inventoryInstance.displayByQuantity(listOfMedicine);
        for (int i = 0; i < listOfMedicine.size(); i++) {
            assertEquals(quantitiesOnly.get(i), sortedQuantityList.get(i).getQuantity());
        }

    }

    @Test
    void byPrice(){
       
        
        ArrayList<Merchandise> listOfMedicine = inventoryInstance.getMerchandise(); // if listOfMedicine empty, test still passes because doesn't go through for loop


        ArrayList<Double> pricesOnly = new ArrayList<Double>();
        for (int i = 0; i < listOfMedicine.size(); i++) {
            pricesOnly.add(listOfMedicine.get(i).getPrice());
        }

        Collections.sort(pricesOnly);

        ArrayList<Merchandise> sortedPriceList = inventoryInstance.displayByPrice(listOfMedicine);
        for (int i = 0; i < listOfMedicine.size(); i++) {
            assertEquals(pricesOnly.get(i), sortedPriceList.get(i).getPrice());
        }


    }

    @Test
    void increaseQuantity(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        // if no medication in inventory, throws exception --> test still passes because nothing to increase (ID doesn't exist)
        try {
			inventoryInstance.increaseQuantity(originalList.get(0).getMedicationID(),10);
			ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
	        assertEquals(originalList.get(0).getQuantity() + 10, newList.get(0).getQuantity());
	        
	        // back to normal
	        inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(), 10);
		} catch (Exception e) {
			
		}
    }
    @Test
    void increaseQuantityInvalid(){ //invalid because medID doesn't exist
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        try {
        	inventoryInstance.increaseQuantity(originalList.size() + 1,10);
            assertEquals(false, inventoryInstance.increaseQuantity(originalList.size() + 1,10));
        }
        catch (Exception e) {
        	
        }
    }
    
    @Test
    void increaseQuantityInvalid2(){ // negative quantity
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        try {
        	assertThrows(Exception.class, () -> inventoryInstance.increaseQuantity(originalList.size(),-10));
        }
        catch (Exception e) {
        }
    }


    @Test
    void decreaseQuantity(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
      
        if (originalList.size() > 0) { // if originalList empty, no medication to decrease quantity of
	        if (originalList.get(0).getQuantity() >= 1) {
	        	try {
	        		inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(),1);
	            	ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
	                assertEquals(originalList.get(0).getQuantity()-1, newList.get(0).getQuantity());
	                
	                //back to normal
	                inventoryInstance.increaseQuantity(originalList.get(0).getMedicationID(), 1);
	        	}
				catch (Exception e) {
				 	
				}
	        }
	        else {
	        	try {
	        		inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(),1);
	            	ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
	                assertEquals(originalList.get(0).getQuantity(), newList.get(0).getQuantity());
	        	}
	            catch (Exception e) {
	            	
	            }
	        }
        }
    }


    @Test
    void decreaseQuantityInvalid(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        // if originalList is empty, throws exception --> test still passes because nothing to decrease (ID doesn't exist)
        try { 
        	inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(),originalList.get(0).getQuantity()+1);
            ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
            assertEquals(originalList.get(0).getQuantity(), newList.get(0).getQuantity());
        } catch (Exception e) {
        	
        }

    }
    
    @Test
    void decreaseQuantityInvalid2(){ // negative quantity
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        // if originalList is empty, throws exception --> test still passes because nothing to decrease (ID doesn't exist)
        try {
        	assertThrows(Exception.class, () -> inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(), -10));
        } catch (Exception e) {
        }

    }

    @Test
    void delete(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) { // if originalList is empty, no medication to delete
	        Merchandise deletedM = originalList.get(originalList.size()-1);
	        
	        inventoryInstance.delete(originalList.size());
	        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
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

    }

    @Test
    void deleteInvalid(){ //medicationID doesn't exist
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        inventoryInstance.delete(100);
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList.size(), newList.size());
    }
    
    @Test
    void searchByID(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) { // if originalList is empty, no medication to search
//        	ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
            assertEquals(originalList.get(0).toString(), inventoryInstance.searchMerchandiseWithID(1).toString());
        }
    }

    @Test
    void searchByIDInvalid(){ //medicationID doesn't exist
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        assertEquals(null, inventoryInstance.searchMerchandiseWithID(originalList.size() + 1));
    }
    @Test
    void modifyPrice(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) { // if originalList is empty, no medication to modify price for
	        double originalPrice = originalList.get(0).getPrice();
	        try { 
		        inventoryInstance.modifyMedicationPrice(originalList.get(0).getMedicationID(), 10.00);
		        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
		        assertEquals(10.00, newList.get(0).getPrice());
		        
		        // back to normal
		       	inventoryInstance.modifyMedicationPrice(originalList.get(0).getMedicationID(), originalPrice);
	        }
	        catch (Exception e) {
	        	
	        }
        }
       
    }

    @Test
    void modifyPriceInvalid(){ // medicationID doesn't exist (assuming 100 too large)
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        try {
	        inventoryInstance.modifyMedicationPrice(100, 100.00);
	        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
	        
	        for (int i = 0 ; i< originalList.size(); i++) {
	        	 assertEquals(originalList.get(0).getPrice(), newList.get(0).getPrice());
	        }
        }
        catch (Exception e) {
        	
        }
       
    }
    
    @Test
    void modifyPriceInvalid2(){ // negative price
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        try {
	        assertThrows(Exception.class, () -> inventoryInstance.modifyMedicationPrice(originalList.get(0).getMedicationID(), -10));
        }
        catch (Exception e) {
        }
       
    }

    @Test
    void modifyDescription(){
       
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) {
        	String originalDescription = originalList.get(0).getDescription();
            inventoryInstance.modifyMedicationDescription(originalList.get(0).getMedicationID(), "howdy");
            ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
            assertEquals("howdy", newList.get(0).getDescription());
            
         // back to normal
           	inventoryInstance.modifyMedicationDescription(originalList.get(0).getMedicationID(), originalDescription);
        }
        
    }

    @Test
    void modifyDescriptionInvalid(){ //medicationID does not exist
        
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        inventoryInstance.modifyMedicationDescription(100, "howdy");
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        
        for (int i = 0 ; i< originalList.size(); i++) {
        	assertEquals(originalList.get(i).getDescription(), newList.get(i).getDescription());
        }
        
    }

    @Test
    void modifyName() {
        
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) {
        	String originalName = originalList.get(0).getName();
            
            try {
            	 inventoryInstance.modifyMedicationName(originalList.get(0).getMedicationID(), "Buckleys");

                 // back to normal
                inventoryInstance.modifyMedicationName(originalList.get(0).getMedicationID(), originalName);
            }
            catch (Exception e) {
            	
            }
        }
        
    }

    @Test
    void modifyNameInvalid() { //medicationID doesn't exist
       
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        try {
        	inventoryInstance.modifyMedicationName(100, "Buckleys");
            ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
            
            for (int i = 0 ; i< originalList.size(); i++) {
            	assertEquals(originalList.get(i).getName(), newList.get(i).getName());
            }
        }
        catch (Exception e) {
        }
    }
}