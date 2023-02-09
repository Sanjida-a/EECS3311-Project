package middleLayer;

import java.util.ArrayList;
import databaseDAO.MerchandiseDAO;

public class Inventory{
    private static Inventory singletonInstance = null;

    ArrayList<Merchandise> list = new ArrayList<Merchandise>();
 
    //Minh changed here
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
           output += list.get(i).name+", ";
           output += list.get(i).quantity+", ";
           output += list.get(i).price+", ";
           output += list.get(i).type+", ";
           output += list.get(i).form+", ";
           output += list.get(i).isOTC+" \n";
        }
        
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

}
    
