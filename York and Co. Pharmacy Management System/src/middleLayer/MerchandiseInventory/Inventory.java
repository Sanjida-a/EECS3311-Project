package middleLayer.MerchandiseInventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import databaseDAO.*;
import databaseDAO.MerchandiseData.MerchandiseDAO;
import databaseDAO.MerchandiseData.MerchandiseRoot;
import middleLayer.NegativeInputException;

public class Inventory{
    private static Inventory singletonInstance = null;

    ArrayList<Merchandise> list = new ArrayList<Merchandise>(); // only valid medication

    private MerchandiseRoot _merDAO; // Dependency Injection Principle
    
    SortInventory sortInv = new SortInventory(); // REFACTORING #2: Code Smell - Large Class
	
	private Inventory() {  //constructor of all singleton classes should be private
		
		try {
			_merDAO = new MerchandiseDAO();
			list = _merDAO.getListOfMerchandise();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Inventory(MerchandiseRoot dao) {
		this._merDAO = dao;
		this.list = this._merDAO.getListOfMerchandise();
		
	}

	// singleton classes must have this method
    public static Inventory getInstance(){
        if (singletonInstance == null)
            singletonInstance = new Inventory();
        return singletonInstance;
    }
    
    public static Inventory getInstance(MerchandiseRoot dao) {
        if (singletonInstance == null) {
            singletonInstance = new Inventory(dao);
        }
        return singletonInstance;
    }
    
    // dependency injection principle (database vs stub)
	public void set_merDAO(MerchandiseRoot _merDAO) {
		this._merDAO = _merDAO;
		list =_merDAO.getListOfMerchandise();
	}

	// can also be called "displayAllMedication" (can be another name for it) --> only VALID medication
    public ArrayList<Merchandise> getMerchandise(){
    	list =_merDAO.getListOfMerchandise();
    	return list;
    }
    
    //NOTE: method used in special few cases where invalid med are needed to be known as well
    public ArrayList<Merchandise> getValidAndInvalidMerchandise() { 
    	return _merDAO.getListOfValidAndInvalidMerchandise();
    }
    
    // finds from ALL VALID and INVALID list and returns a medication with medID == medicationID
    //NOTE: method used in special few cases where invalid med are needed to be known as well
    public Merchandise searchAllValidAndInvalidMerchandiseWithID(int medicationID){ 
    	Merchandise foundMWithID = null; // if no medication with this ID exists, returns NULL
    	
    	ArrayList<Merchandise> allValidAndInvalid = this.getValidAndInvalidMerchandise();
    	for (int i = 0; i < allValidAndInvalid.size(); i ++){
    		if (allValidAndInvalid.get(i).medicationID == medicationID){
    			foundMWithID = allValidAndInvalid.get(i);
    		}
    	}
    	
    	return foundMWithID;
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
    
    // REFACTORING #2: CODE SMELL - Large Class - moved 3 methods below related to displaying/sorting inventory to new class called "SortInventory"
    // organizes and returns inventory sorted alphabetically
    public ArrayList<Merchandise> displayAlphabetically(ArrayList<Merchandise> listToSortAlphabetically){
    	
    	return sortInv.displayAlphabetically(listToSortAlphabetically);
    }
    
    // organizes and returns inventory sorted by quantity (lowest to highest)
    public ArrayList<Merchandise> displayByQuantity(ArrayList<Merchandise> listToSortByQuantity){
    	
    	return sortInv.displayByQuantity(listToSortByQuantity);
    }
    
    // organizes and returns inventory sorted by quantity (lowest to highest)
    public ArrayList<Merchandise> displayByPrice(ArrayList<Merchandise> listToSortByPrice){
    	
    	return sortInv.displayByPrice(listToSortByPrice);
    }
    
    // finds from VALID list and returns a medication with medID == medicationID
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
    
    // increase quantity of medication already existing in inventory (if exists)
    public void increaseQuantity(int medicationID, int increasedQuantity) throws Exception{ // update from Itr2: turned boolean return value to void --> throw exceptions instead of using boolean value to track whether an exception occurred
    	
    	if (increasedQuantity < 0) {
			throw new NegativeInputException("Quantity to increase by must be a non-negative number!");
		}
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	if (specificMedication == null) {
    		throw new Exception("Increase unsuccessful. No such medication currently exists in the inventory. See current inventory");
    	}
    	
    	// if medicationID exists, can increase its quantity
    	specificMedication.quantity += increasedQuantity;
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    }
    
    // decrease quantity of medication already existing in inventory, if possible (if medication exists)
    // boolean return value is just to keep track of whether quantity of medication that just decreased quantity is <= 3 so can give admin restocking reminder/pop-up
    public boolean decreaseQuantity(int medicationID, int decreasedQuantity) throws Exception { // update from Itr2: turned boolean[] return value to void --> throw exceptions instead of using boolean values to track whether an exception occurred
    	
    	if (decreasedQuantity < 0) {
			throw new NegativeInputException("Quantity to decrease by must be a non-negative number!");
		}

    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if medID does not exist in inventory, can't do anything
    		throw new Exception("Decrease unsuccessful. No such medication currently exists in the inventory. See inventory");
    	}
    	
    	// if no exception: medication exists, then can potentially decrease
        int potentialNewQuantity = specificMedication.quantity - decreasedQuantity; // check to see new quantity if decreased
		
        if (potentialNewQuantity < 0) {
    		throw new Exception("Decrease unsuccessful. There is not enough quantity of the medication to decrease by " + decreasedQuantity + ". See inventory");
    	}
        
    	// if no exception: decrease can occur
		specificMedication.quantity -= decreasedQuantity;
	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	// once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    	if (specificMedication.getQuantity() <= 3) {
    		return true;
    	}
    	return false;
    }
    
    // delete a medication from inventory (if exists)
    public void delete(int medicationID) throws Exception{ // update from Itr2: turned boolean return value to void --> throw exceptions instead of using boolean value to track whether an exception occurred
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if medID does not exist in inventory, can't do anything
    		throw new Exception("Remove unsuccessful. No such medication currently exists in the inventory. See current inventory");
    	}
    	
    	// reaches here if a medication with such ID DOES exist in inventory
    	// so, deletion occurs
    	specificMedication.setIsValid(false);
    	
    	// modify database accordingly
    	_merDAO.deleteMedicationInDatabase(medicationID);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    }
    
    // add a new medication to inventory (if it already doesn't exist)
    public void addToInventory(Merchandise m) throws Exception { // update from Itr2: turned boolean return value to void --> throw exceptions instead of using boolean value to track whether an exception occurred
    	
    	if (m.getPrice() < 0) {
			throw new NegativeInputException("Price must be a non-negative number!");
		}
    	if (m.getQuantity() < 0) {
			throw new NegativeInputException("Quantity must be a non-negative number!");
		}

        for (int i = 0; i < list.size(); i ++){  // check in the list of valid medication
            if (list.get(i).name.equals(m.name) && list.get(i).type == m.type && list.get(i).form == m.form && list.get(i).isOTC == m.isOTC){
            	throw new Exception("Add unsuccessful. The medication (same name, type, form and OTC/Rx) already exists in the inventory. See current inventory");// if same medication already exists, can't add it again bc duplicates
            }
        }
        
        // if medication does not already exist in valid medication list (no exception thrown and code reaches here), check if it exists in invalid list (already deleted)
        int oldMedId = isMedAddedBefore(m);
    	
        if (oldMedId != -1) { // if medication IS found as invalid in all (invalid and valid) merchandise list, turn isValid for existing med to true (updating row not adding new row)
        	_merDAO.updateMedicationInDatabase(oldMedId, m);
    	}
        
    	else { // if medication IS NOT found in invalid list of medication, add new medication to table (add new row)
    		list.add(m);
        	_merDAO.addMedicationToDatabase(m);
    	}
        
        //once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    }
    
    // check to see if a medication is already in database as an INVALID (deleted) medication. If yes, set previously deleted medication to valid
	public int isMedAddedBefore(Merchandise m) {            
		int medID = -1;
		ArrayList<Merchandise> invalidAndValidList = new ArrayList<Merchandise>();
    	invalidAndValidList = _merDAO.getListOfValidAndInvalidMerchandise();
    	
    	for (int j = 0; j < invalidAndValidList.size(); j ++) {
    		 if (invalidAndValidList.get(j).name.equals(m.name) && invalidAndValidList.get(j).type == m.type && invalidAndValidList.get(j).form == m.form && invalidAndValidList.get(j).isOTC == m.isOTC){
    			 invalidAndValidList.get(j).setIsValid(true);
    			 medID = invalidAndValidList.get(j).getMedicationID();
    			 break;
             }
    	}
    	return medID; //returns -1 if medication doesn't exist as invalid medication
	}
    
    // NOTE: OCP NOT followed for below 3 modification methods because as a group, we have decided that there are no other modifications to the medication that can be added in the future
    // modifies the name of the medication but first makes sure the same medication doesn't already exist in the system
    public void modifyMedicationName(int medicationID, String newName) throws Exception {
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID); // find medication with medID = medicationID
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		throw new Exception("Modification unsuccessful. No such medication currently exists in the inventory. See current inventory");
    	}
    	
    	// otherwise, can potentially update name but first must check to see if an identical medication with name newNAME already exists --> if does, can't make modification
        for (int i = 0; i < list.size(); i ++){ //notice using newNAME
            if (list.get(i).name.equals(newName) && list.get(i).type == specificMedication.type && list.get(i).form == specificMedication.form && list.get(i).isOTC == specificMedication.isOTC){
            	throw new Exception("Modification unsuccessful. The medication (same name, type, form and OTC/Rx) already exists in the inventory.");
            }
        }
        
        // otherwise, medication does not already exist and so can update its name
    	specificMedication.setName(newName);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    }
    
    // modifies the price of the medication
    public void modifyMedicationPrice(int medicationID, double newPrice) throws Exception {
    	
    	if (newPrice < 0) {
			throw new NegativeInputException("Price must be a non-negative number!");
		}
    	
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		throw new Exception("Modification unsuccessful. No such medication currently exists in the inventory.");
    	}
    	
    	// otherwise, update the medication's price
    	specificMedication.setPrice(newPrice);
    	
    	// modify database accordingly
    	_merDAO.updateMedicationInDatabase(medicationID, specificMedication);
    	
    	//once database is updated, also updated this class's list variable by reading from database
    	list = _merDAO.getListOfMerchandise();
    	
    }
    
    // modifies the description of the medication
    public boolean modifyMedicationDescription(int medicationID, String newDescription) throws Exception {
    	Merchandise specificMedication = this.searchMerchandiseWithID(medicationID);
    	
    	if (specificMedication == null) { // if no medication with such ID exists, then can't update
    		throw new Exception("Modification unsuccessful. No such medication currently exists in the inventory.");
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
    
