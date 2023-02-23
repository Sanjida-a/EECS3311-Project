package middleLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

import databaseDAO.MerchandiseDAO;

public class Inventory{
    private static Inventory singletonInstance = null;

    ArrayList<Merchandise> list = new ArrayList<Merchandise>();
 
	private MerchandiseDAO _merDAO;
	
	public Inventory() {
		try {
			_merDAO = new MerchandiseDAO();
			list = _merDAO.getListOfMerchandise();
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
	}

    public static Inventory getInstance(){
        if (singletonInstance == null)
            singletonInstance = new Inventory();
        return singletonInstance;
    }

    public String display(){
    	String output = "";
        for (int i = 0; i < list.size(); i ++){
           output += list.get(i).medicationID+", ";
           output += list.get(i).name+", ";
           output += list.get(i).quantity+", ";
           output += list.get(i).price+", ";
           output += list.get(i).type+", ";
           output += list.get(i).form+", ";
           output += list.get(i).isOTC+" \n";
        }
        
        return output;
    }
    
    public String displayAlphabetically(){
    	
    	ArrayList<Merchandise> copyOfList = new ArrayList<Merchandise>(list); // so that original list doesn't get modified
    	ArrayList<String> onlyMedicationNames = new ArrayList<String>();
    	
    	String output = "";
    	
        for (int i = 0; i < list.size(); i ++) { // ArrayList "onlyMedicationNames" only stores names of all medication
        	onlyMedicationNames.add(list.get(i).name);
        }
        
        Collections.sort(onlyMedicationNames); // sort names of medication

        for (int i = 0; i < onlyMedicationNames.size(); i++) { // iterate through sorted medication names and obtain all medication details
        	
        	for (int j = 0; j < copyOfList.size(); j++) {
        		
        		if (onlyMedicationNames.get(i).equals(copyOfList.get(j).name)) {
        			output += list.get(i).medicationID+", ";
        			output += copyOfList.get(j).name+", ";
                    output += copyOfList.get(j).quantity+", ";
                    output += copyOfList.get(j).price+", ";
                    output += copyOfList.get(j).type+", ";
                    output += copyOfList.get(j).form+", ";
                    output += copyOfList.get(j).isOTC+" \n";
                    
                    copyOfList.remove(j); // if a match to sorted name, remove from list because already added to output (don't want to repeat same medication)
                    j = copyOfList.size(); // end this inner for loop so can start iterating again using next element in onlyMedicationNames list
        		} 	
            }
        }
        
        // just for testing without UI for now -- can delete below 2 lines later once UI implemented
        // System.out.println(onlyMedicationNames.toString() + "\n\n");
        System.out.println(output);
       
        return output;
    }
    
    public String displayByQuantity(){
    	ArrayList<Merchandise> copyOfList = new ArrayList<Merchandise>(list); // so that original list doesn't get modified
    	ArrayList<Integer> onlyMedicationQuantities = new ArrayList<Integer>();
    	
    	String output = "";
    	
        for (int i = 0; i < list.size(); i ++) { // ArrayList "onlyMedicationQuantities" only stores quantities of all medication
        	onlyMedicationQuantities.add(list.get(i).quantity);
        }
        
        Collections.sort(onlyMedicationQuantities); // sort quantities of medication

        for (int i = 0; i < onlyMedicationQuantities.size(); i++) { // iterate through sorted medication quantities and obtain all medication details
        	
        	for (int j = 0; j < copyOfList.size(); j++) {
        		
        		if (onlyMedicationQuantities.get(i) == copyOfList.get(j).quantity) {
        			output += list.get(i).medicationID+", ";
        			output += copyOfList.get(j).name+", ";
                    output += copyOfList.get(j).quantity+", ";
                    output += copyOfList.get(j).price+", ";
                    output += copyOfList.get(j).type+", ";
                    output += copyOfList.get(j).form+", ";
                    output += copyOfList.get(j).isOTC+" \n";
                    
                    copyOfList.remove(j); // if a match to sorted name, remove from list because already added to output (don't want to repeat same medication)
                    j = copyOfList.size(); // end this inner for loop so can start iterating again using next element in onlyMedicationQuantities list
        		} 	
            }
        }
        
        // just for testing without UI for now -- can delete below 2 lines later once UI implemented
        // System.out.println(onlyMedicationQuantities.toString() + "\n\n");
        System.out.println(output);
       
        return output;
    }
    
    public String displayByPrice(){
    	ArrayList<Merchandise> copyOfList = new ArrayList<Merchandise>(list); // so that original list doesn't get modified
    	ArrayList<Double> onlyMedicationPrices = new ArrayList<Double>();
    	
    	String output = "";
    	
        for (int i = 0; i < list.size(); i ++) { // ArrayList "onlyMedicationPrices" only stores prices of all medication
        	onlyMedicationPrices.add(list.get(i).price);
        }
        
        Collections.sort(onlyMedicationPrices); // sort prices of medication

        for (int i = 0; i < onlyMedicationPrices.size(); i++) { // iterate through sorted medication prices and obtain all medication details
        	
        	for (int j = 0; j < copyOfList.size(); j++) {
        		
        		if (onlyMedicationPrices.get(i) == copyOfList.get(j).price) {
        			output += list.get(i).medicationID+", ";
        			output += copyOfList.get(j).name+", ";
                    output += copyOfList.get(j).quantity+", ";
                    output += copyOfList.get(j).price+", ";
                    output += copyOfList.get(j).type+", ";
                    output += copyOfList.get(j).form+", ";
                    output += copyOfList.get(j).isOTC+" \n";
                    
                    copyOfList.remove(j); // if a match to sorted name, remove from list because already added to output (don't want to repeat same medication)
                    j = copyOfList.size(); // end this inner for loop so can start iterating again using next element in onlyMedicationPrices list
        		} 	
            }
        }
        
        // just for testing without UI for now -- can delete below 2 lines later once UI implemented
        // System.out.println(onlyMedicationQuantities.toString() + "\n\n");
        System.out.println(output);
       
        return output;
    }

    // increase quantity of medication already existing in inventory (if exists)
    public boolean increaseQuantity(String name, int quantity, MERCHANDISE_TYPE type, MERCHANDISE_FORM form, boolean OTC){
    	boolean medicationIncreased = false;
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name.equals(name) && list.get(i).type == type && list.get(i).form == form && list.get(i).isOTC == OTC){
                list.get(i).quantity += quantity;
                medicationIncreased = true;
            }
        }
        
        return medicationIncreased;
    }

    // decrease quantity of medication already existing in inventory, if possible (if medication exists)
    public boolean[] decreaseQuantity(String name, int quantity, MERCHANDISE_TYPE type, MERCHANDISE_FORM form, boolean OTC){
    	boolean medicationDecreased = false;
    	boolean enoughQuantityToDecrease = true;
    	boolean itemLowInStock = false;
    	for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name.equals(name) && list.get(i).type == type && list.get(i).form == form && list.get(i).isOTC == OTC){
                int potentialNewQuantity = list.get(i).quantity - quantity;
            	if (potentialNewQuantity < 0) {
            		enoughQuantityToDecrease = false;
            	}
            	else {
            		list.get(i).quantity -= quantity;
            		medicationDecreased = true;
            	}
            	
            	// variable that keeps track if item is low in stock
                if(list.get(i).quantity < 3){
                    itemLowInStock = true;
                }
            }
        }
    	
    	boolean[] booleanArray = {medicationDecreased, enoughQuantityToDecrease, itemLowInStock};
    	return booleanArray;
    }
    
    // delete a medication from inventory (if exists)
    public boolean delete(String name, MERCHANDISE_TYPE type, MERCHANDISE_FORM form, boolean OTC){
    	boolean medicationRemoved = false;
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name.equals(name) && list.get(i).type == type && list.get(i).form == form && list.get(i).isOTC == OTC){
                list.remove(i);
                medicationRemoved = true;
            }
        }
        
        return medicationRemoved;
    }
    
    // add a new medication to inventory (if it already doesn't exist)
    public boolean addToInventory(Merchandise m){
    	
    	boolean medicationAlreadyExists = false;
    	boolean medicationAdded = false;
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name.equals(m.name) && list.get(i).type == m.type && list.get(i).form == m.form && list.get(i).isOTC == m.isOTC){
            	medicationAlreadyExists = true;
            }
        }
        
        if (medicationAlreadyExists == false) {
        	list.add(m);
        	medicationAdded = true;
        }
        
        return medicationAdded;
    }
    
    public ArrayList<Merchandise> getMerchandise(){
    	return list;
    }
    
    public Merchandise searchMerchandiseWithID(int medicationID){
    	Merchandise foundMWithID = null;
    	
    	for (int i = 0; i < list.size(); i ++){
    		if (list.get(i).medicationID == medicationID){
    			foundMWithID = list.get(i);
    		}
    	}
    	
    	return foundMWithID;
    }

}
    
