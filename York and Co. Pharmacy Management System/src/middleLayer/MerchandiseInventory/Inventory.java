package middleLayer.MerchandiseInventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import databaseDAO.*;
import databaseDAO.MerchandiseData.MerchandiseDAO;
import databaseDAO.MerchandiseData.MerchandiseRoot;

public class Inventory{
    private static Inventory singletonInstance = null;

    ArrayList<Merchandise> list = new ArrayList<Merchandise>();

    private MerchandiseRoot _merDAO; // Dependency Injection Principle
	
	private Inventory() {  //constructor of all singleton classes should be private
		
		try {
			_merDAO = new MerchandiseDAO();
			list = _merDAO.getListOfMerchandise();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// singleton classes must have this method
    public static Inventory getInstance(){
        if (singletonInstance == null)
            singletonInstance = new Inventory();
        return singletonInstance;
    }

	public void set_merDAO(MerchandiseRoot _merDAO) {
		this._merDAO = _merDAO;
		list =_merDAO.getListOfMerchandise();
	}

	// can also be called "displayAllMedication" (can be another name for it)
    public ArrayList<Merchandise> getMerchandise(){
    	list =_merDAO.getListOfMerchandise();
    	return list;
    }
    
    // can also be called "displayOnlyOTCMedication" for guest/home/main screen where only OTC medication information should be displayed for easy access...
    public ArrayList<Merchandise> getOnlyOTCMerchandise(){ //... for users like patients who only have access to OTCs
    	list =_merDAO.getListOfMerchandise();
    	ArrayList<Merchandise> allOTCOnlyMedication = new ArrayList<Merchandise>();
    	
        for (int i = 0; i < list.size(); i ++){
        	if (list.get(i).isOTC == true) { // only new condition different from above method
        		allOTCOnlyMedication.add(list.get(i));
        	}
        }
        
        return allOTCOnlyMedication;
    }
    
    // organizes and returns inventory sorted alphabetically
    public ArrayList<Merchandise> displayAlphabetically(ArrayList<Merchandise> listToSortAlphabetically){
    	
    	ArrayList<Merchandise> displayResult = new ArrayList<Merchandise>();
    	ArrayList<Merchandise> copyOfList = new ArrayList<Merchandise>(listToSortAlphabetically); // so that original list doesn't get modified when removing elements below
    	ArrayList<String> onlyMedicationNames = new ArrayList<String>();
    	
        for (int i = 0; i < listToSortAlphabetically.size(); i ++) { // ArrayList "onlyMedicationNames" only stores names of all medication
        	onlyMedicationNames.add(listToSortAlphabetically.get(i).name);
        }
        
        Collections.sort(onlyMedicationNames); // sort names of medication

        for (int i = 0; i < onlyMedicationNames.size(); i++) { // iterate through sorted medication names and obtain all medication details
        	
        	for (int j = 0; j < copyOfList.size(); j++) { // iterate through inventory to find any medications with same name from outer loop
        		
        		if (onlyMedicationNames.get(i).equals(copyOfList.get(j).name)) {
        			Merchandise m = new Merchandise(copyOfList.get(j));
        			displayResult.add(m);
        			
                    copyOfList.remove(j); // if a match to sorted name, remove from list because already added to output (don't want to repeat same medication)
                    j = copyOfList.size(); // end this inner for loop so can start iterating again using next element in onlyMedicationNames list
        		} 	
            }
        }
        
        return displayResult;
    }
    
    // organizes and returns inventory sorted by quantity (lowest to highest)
    public ArrayList<Merchandise> displayByQuantity(ArrayList<Merchandise> listToSortByQuantity){
    	
    	ArrayList<Merchandise> displayResult = new ArrayList<Merchandise>();
    	ArrayList<Merchandise> copyOfList = new ArrayList<Merchandise>(listToSortByQuantity); // so that original list doesn't get modified
    	ArrayList<Integer> onlyMedicationQuantities = new ArrayList<Integer>();
    	
        for (int i = 0; i < listToSortByQuantity.size(); i ++) { // ArrayList "onlyMedicationQuantities" only stores quantities of all medication
        	onlyMedicationQuantities.add(listToSortByQuantity.get(i).quantity);
        }
        
        Collections.sort(onlyMedicationQuantities); // sort quantities of medication

        for (int i = 0; i < onlyMedicationQuantities.size(); i++) { // iterate through sorted medication quantities and obtain all medication details
        	
        	for (int j = 0; j < copyOfList.size(); j++) { // iterate through inventory to find any medications with same quantity from outer loop
        		
        		if (onlyMedicationQuantities.get(i) == copyOfList.get(j).quantity) {
        			Merchandise m = new Merchandise(copyOfList.get(j));
        			displayResult.add(m);
                    
                    copyOfList.remove(j); // if a match to sorted quantity, remove from list because already added to output (don't want to repeat same medication)
                    j = copyOfList.size(); // end this inner for loop so can start iterating again using next element in onlyMedicationQuantities list
        		} 	
            }
        }
       
        return displayResult;
    }
    
    // organizes and returns inventory sorted by quantity (lowest to highest)
    public ArrayList<Merchandise> displayByPrice(ArrayList<Merchandise> listToSortByPrice){
    	
    	ArrayList<Merchandise> displayResult = new ArrayList<Merchandise>();
    	ArrayList<Merchandise> copyOfList = new ArrayList<Merchandise>(listToSortByPrice); // so that original list doesn't get modified
    	ArrayList<Double> onlyMedicationPrices = new ArrayList<Double>();
    	
        for (int i = 0; i < listToSortByPrice.size(); i ++) { // ArrayList "onlyMedicationPrices" only stores prices of all medication
        	onlyMedicationPrices.add(listToSortByPrice.get(i).price);
        }
        
        Collections.sort(onlyMedicationPrices); // sort prices of medication

        for (int i = 0; i < onlyMedicationPrices.size(); i++) { // iterate through sorted medication prices and obtain all medication details
        	
        	for (int j = 0; j < copyOfList.size(); j++) { // iterate through inventory to find any medications with same price from outer loop
        		
        		if (onlyMedicationPrices.get(i) == copyOfList.get(j).price) {
        			Merchandise m = new Merchandise(copyOfList.get(j));
        			displayResult.add(m);
                    
                    copyOfList.remove(j); // if a match to sorted quantity, remove from list because already added to output (don't want to repeat same medication)
                    j = copyOfList.size(); // end this inner for loop so can start iterating again using next element in onlyMedicationPrices list
        		} 	
            }
        }
        
        return displayResult;
    }

    // increase quantity of medication already existing in inventory (if exists)
    public boolean increaseQuantity(int medicationID, int increasedQuantity) throws Exception{
    	
    	if (increasedQuantity < 0) {
			throw new Exception("Quantity to increase by must be a non-negative number!");
		}
    	
    	boolean medicationIncreased = false;
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	if (specificMedication == null) {
    		return medicationIncreased;
    	}
    	
    	specificMedication.quantity += increasedQuantity;
    	medicationIncreased = true;
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
        return medicationIncreased;
    }

    // decrease quantity of medication already existing in inventory, if possible (if medication exists)
    public boolean[] decreaseQuantity(int medicationID, int decreasedQuantity) throws Exception{
    	
    	if (decreasedQuantity < 0) {
			throw new Exception("Quantity to decrease by must be a non-negative number!");
		}
    	
    	boolean medicationDecreased = false;
    	boolean enoughQuantityToDecrease = true;
    	boolean itemLowInStock = false;
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null || decreasedQuantity < 0) { // if medID does not exist in inventory, can't do anything
    		// no change because want initial boolean values as above
    	}
    	
    	else { // otherwise it exists, then can potentially decrease
	        int potentialNewQuantity = specificMedication.quantity - decreasedQuantity; // check to see new quantity if decreased
			if (potentialNewQuantity < 0) {
	    		enoughQuantityToDecrease = false; // decrease will not occur if new quantity results in being less than 0
	    	}
	    	else { // otherwise, decrease can occur
	    		specificMedication.quantity -= decreasedQuantity;
	    		medicationDecreased = true;
	    	}
	    	
	    	// variable that keeps track if item is low in stock
	        if(specificMedication.quantity < 3){
	            itemLowInStock = true;
	        }
    	}
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	boolean[] booleanArray = {medicationDecreased, enoughQuantityToDecrease, itemLowInStock}; // return values easy for front end
    	return booleanArray;
    }
    
    // delete a medication from inventory (if exists)
    public boolean delete(int medicationID){
    	
    	boolean medicationRemoved = false;
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if medID does not exist in inventory, can't do anything
    		return medicationRemoved; // return false
    	}
    	
//    	list.remove(specificMedication);
    	specificMedication.setIsValid(false);
    	medicationRemoved = true; // remove successful
    	
    	// modify database accordingly
    	_merDAO.deleteMedicationInDatabase(medicationID);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
        
        return medicationRemoved;
    }
    
    // add a new medication to inventory (if it already doesn't exist)
    public boolean addToInventory(Merchandise m) throws Exception{
    	
    	if (m.getPrice() < 0) {
			throw new Exception("Price must be a non-negative number!");
		}
    	if (m.getQuantity() < 0) {
			throw new Exception("Quantity must be a non-negative number!");
		}
    	boolean medicationAlreadyExists = false;
    	boolean medicationAdded = false;
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name.equals(m.name) && list.get(i).type == m.type && list.get(i).form == m.form && list.get(i).isOTC == m.isOTC){
            	medicationAlreadyExists = true; // if same medication already exists, can't add it again bc duplicates
            }
        }
        
        if (medicationAlreadyExists == false) { // otherwise, can add
        	list.add(m);
        	medicationAdded = true;
        	
        	  // modify database accordingly
        	_merDAO.addMedicationToDatabase(m);
        	
        	//once database is updated, also updated this class's list variable by reading from database
        	list = _merDAO.getListOfMerchandise();
        }
      
        return medicationAdded;
    }
    
    // finds and returns a medication with medID == medicationID
    public Merchandise searchMerchandiseWithID(int medicationID){
    	Merchandise foundMWithID = null; // if no medication with this ID exists, returns NULL
    	this.updateFromDatabase();
    	for (int i = 0; i < list.size(); i ++){
    		if (list.get(i).medicationID == medicationID){
    			foundMWithID = list.get(i);
    		}
    	}
    	
    	return foundMWithID;
    }
    
    
    // OCP NOT followed for below 3 modification methods because as a group, we have decided that there are no other modifications to the medication that can be added in the future
    // modifies the name of the medication but first makes sure the same medication doesn't already exist in the system
    public boolean modifyMedicationName(int medicationID, String newName) throws Exception {
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID); // find medication with medID = medicationID
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		return false;
    	}
    	
    	// otherwise, can potentially update name but first must check to see if an identical medication with name newNAME already exists --> if does, can't make modification
    	boolean medicationAlreadyExists = false;
        for (int i = 0; i < list.size(); i ++){ //notice using newNAME
            if (list.get(i).name.equals(newName) && list.get(i).type == specificMedication.type && list.get(i).form == specificMedication.form && list.get(i).isOTC == specificMedication.isOTC){
            	medicationAlreadyExists = true;
            }
        }
        
        if (medicationAlreadyExists == true) { // if medication already exists then throw exception to front end that can't update
        	throw new Exception();
        }
        
        // otherwise, medication does not already exist and so can update its name
    	specificMedication.setName(newName);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	return true;
    }
    
    // modifies the price of the medication
    public boolean modifyMedicationPrice(int medicationID, double newPrice) throws Exception {
    	
    	if (newPrice < 0) {
			throw new Exception("Price must be a non-negative number!");
		}
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		return false;
    	}
    	
    	// otherwise, update the medication's price
    	specificMedication.setPrice(newPrice);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	return true;
    }
    
    // modifies the description of the medication
    public boolean modifyMedicationDescription(int medicationID, String newDescription) {
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		return false;
    	}
    	
    	// otherwise, update the medication's description
    	specificMedication.setDescription(newDescription);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	return true;
    }
    
    // good design to put in a method; required for front end use
    public void updateFromDatabase() {
    	this.list = _merDAO.getListOfMerchandise();
    }

}
    
