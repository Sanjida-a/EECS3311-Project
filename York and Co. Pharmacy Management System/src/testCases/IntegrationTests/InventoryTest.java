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
    static String pass = "hello123";  // TA please change this according to your mySQL password in order for the tests to work

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
       // val.decreaseQuantity(1,1);
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
//        assertEquals("[ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
//                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
//                ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n" +
//                ", ID: 6, Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false, Description: null\n" +
//                ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
//                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
//                "]",val.displayAlphabetically(val.getMerchandise()).toString());
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
//        Inventory val = Inventory.getInstance();
//        assertEquals("[ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
//                ", ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
//                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
//                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
//                ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n" +
//                ", ID: 6, Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false, Description: null\n" +
//                "]",val.displayByQuantity(val.getMerchandise()).toString());
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

//        Inventory val = Inventory.getInstance();
//        assertEquals("[ID: 1, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
//              //  ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
//                ", ID: 3, Name: ADVIL, Quantity: 10, Price: 5.0, Type: COLD, Form: TABLET, isOTC: true, Description: null\n" +
//                ", ID: 4, Name: TYLENOL, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: true, Description: null\n" +
//                ", ID: 5, Name: PILL1, Quantity: 10, Price: 5.0, Type: COLD, Form: LIQUID, isOTC: false, Description: null\n" +
//                ", ID: 6, Name: PILL2, Quantity: 10, Price: 5.0, Type: FEVER, Form: TABLET, isOTC: false, Description: null\n" +
//                ", ID: 2, Name: TYLENOL, Quantity: 5, Price: 8.0, Type: FEVER, Form: TABLET, isOTC: true, Description: null\n" +
//                "]",val.displayByPrice(val.getMerchandise()).toString());
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
        
//        assertEquals(true, val.increaseQuantity(2,10))
        
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
//        assertEquals(true, val.increaseQuantity(2,10))
    }
//    @Test
//    void addTo(){
//        try {
//            superDAO.setPassword(pass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Inventory val = Inventory.getInstance();
//        ArrayList<Merchandise> originalList = val.getMerchandise();
//        System.out.println(originalList.size());
//        Merchandise m = new Merchandise(originalList.size()+1, "Buckleys",  5,  3.00, MERCHANDISE_TYPE.COLD, MERCHANDISE_FORM.LIQUID, true, null, true);
//        val.addToInventory(m);
//        System.out.println(originalList.size());
//        ArrayList<Merchandise> newList = val.getMerchandise();
//        assertEquals(originalList.size(), newList.size());
//    }
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

//        Inventory val = Inventory.getInstance();
//        Merchandise m = new Merchandise(7, "ASPIRIN", 10, 15.0, MERCHANDISE_TYPE.FEVER, MERCHANDISE_FORM.TABLET, true, null, true);
//        assertEquals(true, val.addToInventory(m));
//        assertEquals(true, val.delete(7));
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
//        ArrayList<Merchandise> newList = val.getMerchandise();
//        if (originalList.get(0).getName().equals("Buckleys")) {
//            assertEquals(false, val.modifyMedicationName(1, "Buckleys"));
//        }
//        else {
//             assertEquals(true, val.modifyMedicationName(1, "Buckleys"));
//        }

//        assertEquals("Buckleys", newList.get(0).getName());
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