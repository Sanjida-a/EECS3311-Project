package testCases.IntegrationTests;


import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseDAO;

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
    static String pass = "hello@123456";  // TA please change this according to your mySQL password in order for the tests to work

    @Test
    void getInstance() {
    }

    @Test
    void getOnlyOTCMerchandiseTest(){
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Inventory val = Inventory.getInstance();

        ArrayList<Merchandise> onlyOTC = val.getOnlyOTCMerchandise();
    
        int i = 0;
        for (i = 0; i < onlyOTC.size(); i++) {
            assertEquals(onlyOTC.get(i).getisOTC(), true);
        }
    }

    @Test
    void alphabetically(){
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.increaseQuantity(originalList.get(0).getMedicationID(),10);
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(originalList.get(0).getQuantity() + 10, newList.get(0).getQuantity());
        
        // back to normal
        val.decreaseQuantity(originalList.get(0).getMedicationID(), 10);
    }
    @Test
    void increaseQuantityInvalid(){
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.increaseQuantity(originalList.size() + 1,10);
        assertEquals(false, val.increaseQuantity(originalList.size() + 1,10));
    }

    @Test
    void decreaseQuantity(){ //need condition
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
      
        
        if (originalList.get(0).getQuantity() >= 1) {
        	val.decreaseQuantity(originalList.get(0).getMedicationID(),1);
        	ArrayList<Merchandise> newList = val.getMerchandise();
            assertEquals(originalList.get(0).getQuantity()-1, newList.get(0).getQuantity());
            
            //back to normal
            val.increaseQuantity(originalList.get(0).getMedicationID(), 1);
        }
        else {
        	val.decreaseQuantity(originalList.get(0).getMedicationID(),1);
        	ArrayList<Merchandise> newList = val.getMerchandise();
            assertEquals(originalList.get(0).getQuantity(), newList.get(0).getQuantity());
        }
        

        
        // back to normal
       
    }


    @Test
    void decreaseQuantityInvalid(){ // by 7
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.decreaseQuantity(originalList.get(0).getMedicationID(),originalList.get(0).getQuantity()+1);
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(originalList.get(0).getQuantity(), newList.get(0).getQuantity());

    }

    @Test
    void delete(){
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.delete(100);
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(originalList.size(), newList.size());
    }
    @Test
    void searchByID(){
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(originalList.get(0).toString(), val.searchMerchandiseWithID(1).toString());
    }

    @Test
    void searchByIDInvalid(){
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        assertEquals(null, val.searchMerchandiseWithID(originalList.size() + 1));
    }
    @Test
    void modifyPrice(){
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        double originalPrice = originalList.get(0).getPrice();
        
        val.modifyMedicationPrice(originalList.get(0).getMedicationID(), 10.00);
        ArrayList<Merchandise> newList = val.getMerchandise();
        assertEquals(10.00, newList.get(0).getPrice());
        
        // back to normal
       	val.modifyMedicationPrice(originalList.get(0).getMedicationID(), originalPrice);
       
    }

    @Test
    void modifyPriceInvalid(){
    	try {
			superDAO.setPassword(pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.modifyMedicationPrice(100, 100.00);
        ArrayList<Merchandise> newList = val.getMerchandise();
        
        for (int i = 0 ; i< originalList.size(); i++) {
        	 assertEquals(originalList.get(0).getPrice(), newList.get(0).getPrice());
        }
       
    }

    @Test
    void modifyDescription(){
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.modifyMedicationDescription(100, "howdy");
        ArrayList<Merchandise> newList = val.getMerchandise();
        
        for (int i = 0 ; i< originalList.size(); i++) {
        	assertEquals(originalList.get(i).getDescription(), newList.get(i).getDescription());
        }
        
    }

    @Test
    void modifyName() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        
        String originalName = originalList.get(0).getName();
        val.modifyMedicationName(originalList.get(0).getMedicationID(), "Buckleys");

        // back to normal
       	val.modifyMedicationName(originalList.get(0).getMedicationID(), originalName);
        
    }

    @Test
    void modifyNameInvalid() throws Exception {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Inventory val = Inventory.getInstance();
        ArrayList<Merchandise> originalList = val.getMerchandise();
        val.modifyMedicationName(100, "Buckleys");
        ArrayList<Merchandise> newList = val.getMerchandise();
        
        for (int i = 0 ; i< originalList.size(); i++) {
        	assertEquals(originalList.get(i).getName(), newList.get(i).getName());
        }
        
    }
}