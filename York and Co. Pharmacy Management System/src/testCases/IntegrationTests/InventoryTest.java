package testCases.IntegrationTests;


import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseDAO;
import databaseDAO.UserData.UserDAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import middleLayer.*;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Users.ListOfUsers;

import org.junit.platform.commons.annotation.Testable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
	
    private MerchandiseDAO _merDAO;
    private static Inventory inventoryInstance;
    private static Connection con;
    
    //beforeAll is just used to established a connection with the database before all tests
  	@BeforeAll
  	public static void before() {
  		try {

  			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
  			con = superDAO.getCon();
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
    void getMerchandiseTest() { //should only hold all valid medicine
    	ArrayList<Merchandise> allValidOnly = inventoryInstance.getMerchandise();
        
        int i = 0;
        for (i = 0; i < allValidOnly.size(); i++) {
            assertEquals(allValidOnly.get(i).getisValid(), true);
        }
    }
    
    @Test
    void getValidAndInvalidMerchandiseTest() {
    	ArrayList<Merchandise> allValidOnly = inventoryInstance.getMerchandise();
    	ArrayList<Merchandise> allValidAndInvalid = inventoryInstance.getValidAndInvalidMerchandise();
        
    	assertEquals(allValidAndInvalid.size()>= allValidOnly.size(), true);
    }

    @Test
    void alphabeticallyTest(){
       
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
    void byQuantityTest(){
        
        
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
    void byPriceTest(){
       
        
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
    void increaseQuantityTest(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) { // if originalList is empty, no medication to increase
	        try {
				inventoryInstance.increaseQuantity(originalList.get(0).getMedicationID(),10);
				
				ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
		        assertEquals(originalList.get(0).getQuantity() + 10, newList.get(0).getQuantity());
			} catch (Exception e) {
				fail();
			}
	        
	        // back to normal
	        try {
	        	inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(), 10);
	        }
	        catch (Exception e) {
	        	fail();
	        }
        }
    }
    
    @Test
    void increaseQuantityInvalidTest() throws Exception{ // negative quantity
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) {
        	assertThrows(NegativeInputException.class, () -> inventoryInstance.increaseQuantity(originalList.get(0).getMedicationID(),-10));
            
            ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
            assertEquals(originalList, newList); // all medications in DB should remain the same (no quantity should have changed)
        }
       
    }
    
    @Test
    void increaseQuantityInvalidTest2(){ //invalid because medID doesn't exist
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        String expectedMsg = "Increase unsuccessful. No such medication currently exists in the inventory. See current inventory";
        
        String resultMsg = null;
        try {
        	inventoryInstance.increaseQuantity(1000,10); //assuming 1000 medications not inventory
        }
        catch (Exception e) { // expecting an exception
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList, newList); // all medications in DB should remain the same (no quantity should have changed)
    }

    @Test
    void decreaseQuantityTest(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
      
        if (originalList.size() > 0) { // if originalList empty, no medication to decrease quantity of
	        
	        int originalQty = originalList.get(0).getQuantity();
	        
        	try {
        		inventoryInstance.increaseQuantity(originalList.get(0).getMedicationID(), 5);
        		
        		inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(),5); //going back to normal at the same time
        		
            	ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
                assertEquals(originalQty, newList.get(0).getQuantity());
        	}
			catch (Exception e) { //not expecting any exception
			 	fail();
			}
        }

    }
  
    @Test
    void decreaseQuantityInvalidTest1(){ // negative quantity
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) { 
			assertThrows(NegativeInputException.class, () -> inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(), -10));
			
			ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
		    assertEquals(originalList, newList); // all medications in DB should remain the same (no quantity should have changed)
        }
    }
    
    @Test
    void decreaseQuantityInvalidTest2(){ // medication Id doesn't exist
    	
    	ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        String expectedMsg = "Decrease unsuccessful. No such medication currently exists in the inventory. See inventory";
        
        String resultMsg = null;
        try {
        	inventoryInstance.decreaseQuantity(1000,10); //assuming 1000 medications not inventory
        }
        catch (Exception e) { // expecting an exception
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
        
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList, newList); // all medications in DB should remain the same (no quantity should have changed)
    }
    
    @Test
    void decreaseQuantityInvalidTest3(){ // not enough med quantity in stock
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 1) {
        
		    int decreaseQuantityBy = originalList.get(0).getQuantity()+1;
		    
		    String expectedMsg = "Decrease unsuccessful. There is not enough quantity of the medication to decrease by " + decreaseQuantityBy + ". See inventory";
		    
		    String resultMsg = null;
		    
		    // if originalList is empty, throws exception --> test still passes because nothing to decrease (ID doesn't exist)
		    try { 
		    	inventoryInstance.decreaseQuantity(originalList.get(0).getMedicationID(),decreaseQuantityBy);
		       
		    } catch (Exception e) { //exception expected to be thrown
		    	resultMsg = e.getMessage();
		    }
		    
		    assertEquals(expectedMsg, resultMsg);
		    
		    ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
		    assertEquals(originalList, newList); // all medications in DB should remain the same (no quantity should have changed)
        }
    }

    @Test
    void deleteTest(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) { // if originalList is empty, no medication to delete
	        Merchandise deletedM = originalList.get(originalList.size()-1);
	        
	        try {
	        	inventoryInstance.delete(deletedM.getMedicationID());
	        	ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
	 	        assertEquals(originalList.size() - 1, newList.size());
	        }
	        catch (Exception e) { // exception not expected
	        	fail();
	        }
	       
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
    void deleteInvalidTest(){ //medicationID doesn't exist (assuming 1000 ID too large)
    	ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
    	
        String expectedMsg = "Remove unsuccessful. No such medication currently exists in the inventory. See current inventory";
        String resultMsg = null;
        try {
        	inventoryInstance.delete(1000);
        }
        catch (Exception e) {
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
        
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList, newList); // all medications in DB should remain the same (no medication should be deleted)
    }
    

    @Test
    void addToInventoryTest() { // for already deleted medication
    	ArrayList<Merchandise> originaValidAndlInvalidList = inventoryInstance.getValidAndInvalidMerchandise();
    	
    	ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise(); // only valid list
    	
    	int indexOfInvalid = -1;
    	for (int i = 0; i < originaValidAndlInvalidList.size(); i++) {
    		if (originaValidAndlInvalidList.get(i).getisValid() == false) { //already deleted medication
    			indexOfInvalid = i;
    			
    			break;
    		}
    	}
    	
    	if (indexOfInvalid != -1) {

    		Merchandise foundM = originaValidAndlInvalidList.get(indexOfInvalid);
    		Merchandise m = new Merchandise(foundM.getName(), 50, 49.99, foundM.getType(), foundM.getForm(), foundM.getisOTC());

    		try {
				inventoryInstance.addToInventory(m);
			} catch (Exception e) {
				// no exception expected
				fail();
			}
    		
    		ArrayList<Merchandise> newList = inventoryInstance.getMerchandise(); // only valid list
    		
    		assertEquals(originalList.size()+1, newList.size());
    		
    		//back to normal
    		try {
				inventoryInstance.delete(foundM.getMedicationID());
			} catch (Exception e) {
				//no exception expected
				fail();
			}
    	}
    }
    
    @Test
    void addToInventoryInvalidTest(){ // negative price
    	ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
    	
	    String expectedMsg = "Price must be a non-negative number!";
	    String resultMsg = null;
	   
	    Merchandise m = new Merchandise("AddedForTest", 50, -50.99, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.TABLET, true);
	    	
	    try {
	    	inventoryInstance.addToInventory(m);
	    }
	    catch (NegativeInputException e) {
	    	resultMsg = e.getMessage();
	    }
	    catch (Exception e) {
	    	fail();
	    }
	    
	    assertEquals(expectedMsg, resultMsg);
	    
	    ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
	    assertEquals(originalList, newList); // all medications in DB should remain the same (no medication should be added)
	}
    
    @Test
    void addToInventoryInvalidTest2(){ // negative quantity
    	ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
    	
        String expectedMsg = "Quantity must be a non-negative number!";
        String resultMsg = null;
       
        Merchandise m = new Merchandise("AddedForTest", -50, 50.99, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.TABLET, true);
        	
        try {
        	inventoryInstance.addToInventory(m);
        }
        catch (NegativeInputException e) {
        	resultMsg = e.getMessage();
        }
        catch (Exception e) {
        	fail();
        }
        
        assertEquals(expectedMsg, resultMsg);
        
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList, newList); // all medications in DB should remain the same (no medication should be added)
    }
    
    @Test
    void searchByIDWithSQLQueryTest(){
    	
    	Merchandise m = null;
    	try {
    		String queryGetAllRows = "SELECT * FROM Medications WHERE medicationID = 1 AND isValid = 1;";
    		Statement statement = con.createStatement();
    		ResultSet result = statement.executeQuery(queryGetAllRows);
    		int medicationID;
    	    String name;
    	    int quantity;
    	    double price;
    	    MERCHANDISE_TYPE type;
    	    MERCHANDISE_FORM form;
    	    boolean isOTC;
    	    String description;
    	    boolean isValid;

    		while (result.next()) { 
    			
    			medicationID =  result.getInt("medicationID");
    			name = result.getString("medName") ;
    			quantity =  result.getInt("quantity");
    			price =  result.getDouble("price");
    			type = MERCHANDISE_TYPE.valueOf(result.getString("medType").toUpperCase());
    			form = MERCHANDISE_FORM.valueOf(result.getString("medForm").toUpperCase());
    			isOTC = result.getBoolean("isOTC");
    			description = result.getString("medDescription") ;
    			isValid = result.getBoolean("isValid");
    			
    			m = new Merchandise(medicationID, name, quantity, price, type, form, isOTC, description, isValid);
    		}
    		
    	} catch (Exception e) { //no exception expected
    	}
 
        if (m != null) {
			assertEquals(m.toString(), inventoryInstance.searchMerchandiseWithID(1).toString());
        }
        else {
			assertEquals(null, inventoryInstance.searchMerchandiseWithID(1));
        }
    }


    @Test
    void searchByIDInvalidTest(){ //medicationID doesn't exist
		assertEquals(null, inventoryInstance.searchMerchandiseWithID(1000));
    }
    
    @Test
    void modifyNameTest() {
        
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) {
        	String originalName = originalList.get(0).getName();
            
            try {
            	 inventoryInstance.modifyMedicationName(originalList.get(0).getMedicationID(), "RANDOMNONAME");
            	 
            	 ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
            	 assertEquals(newList.get(0).getName(), "RANDOMNONAME");
            	 
            	// back to normal
                 inventoryInstance.modifyMedicationName(originalList.get(0).getMedicationID(), originalName);
            }
            catch (Exception e) { // no exception expected
            	fail();
            }
           
        }
        
    }

    @Test
    void modifyNameInvalidTest() { //medicationID doesn't exist
    	 ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        String expectedMsg = "Modification unsuccessful. No such medication currently exists in the inventory. See current inventory";
        String resultMsg = null;
        
        try {
        	inventoryInstance.modifyMedicationName(1000, "RANDOMNONAME");
           
        }
        catch (Exception e) {
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
        
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList, newList); // all medications in DB should remain the same (no medication name should be changed)
    }
    
    
    @Test
    void modifyPriceTest(){
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) { // if originalList is empty, no medication to modify price for
	        double originalPrice = originalList.get(0).getPrice();
	        double newPrice = originalPrice + 100;
	        try { 
		        inventoryInstance.modifyMedicationPrice(originalList.get(0).getMedicationID(), newPrice);
		        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
		        assertEquals(newPrice, newList.get(0).getPrice());
		        
		        // back to normal
		       	inventoryInstance.modifyMedicationPrice(originalList.get(0).getMedicationID(), originalPrice);
	        }
	        catch (Exception e) { //no exception expected
	        	fail();
	        }
        }
       
    }
    
    
    @Test
    void modifyPriceInvalidTest1(){ // negative price
    	
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) {
        	assertThrows(NegativeInputException.class, () -> inventoryInstance.modifyMedicationPrice(originalList.get(0).getMedicationID(), -10));
    	    
    	    ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
            assertEquals(originalList, newList); // all medications in DB should remain the same (no medication price should be changed)
        }
	    
    }
    
    @Test
    void modifyPriceInvalidTest2(){ // medicationID doesn't exist (assuming 100 too large)
    	ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
    	
        String expectedMsg = "Modification unsuccessful. No such medication currently exists in the inventory.";
        String resultMsg = null;
        
        try {
	        inventoryInstance.modifyMedicationPrice(1000, 100.00);
        }
        catch (Exception e) {
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
        
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList, newList); // all medications in DB should remain the same (no medication price should be changed)
    }
 

    @Test
    void modifyDescriptionTest(){
       
        ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
        
        if (originalList.size() > 0) {
        	
        	try {
        		String originalDescription = originalList.get(0).getDescription();
                inventoryInstance.modifyMedicationDescription(originalList.get(0).getMedicationID(), "howdy");
                ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
                assertEquals("howdy", newList.get(0).getDescription());
                
                // back to normal
               	inventoryInstance.modifyMedicationDescription(originalList.get(0).getMedicationID(), originalDescription);
        	}
        	catch (Exception e) { //no exception expected
        		fail();
        	}
        	
        }
        
    }

    @Test
    void modifyDescriptionInvalidTest(){ //medicationID does not exist
    	ArrayList<Merchandise> originalList = inventoryInstance.getMerchandise();
    	 
        String expectedMsg = "Modification unsuccessful. No such medication currently exists in the inventory.";
        String resultMsg = null;
        
        try {
        	inventoryInstance.modifyMedicationDescription(1000, "howdy");
        }
        catch(Exception e) {
        	resultMsg = e.getMessage();
        }
        
        assertEquals(expectedMsg, resultMsg);
       
        ArrayList<Merchandise> newList = inventoryInstance.getMerchandise();
        assertEquals(originalList, newList); // all medications in DB should remain the same (no medication price should be changed)
    }

}