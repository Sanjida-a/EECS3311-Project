package middleLayer.MerchandiseInventory;

import java.util.ArrayList;
import java.util.Collections;

public class SortInventory { // this class was created to solve/fix for REFACTORING #2: Code Smell - Large Class
	
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
}
