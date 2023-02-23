package middleLayer;

import java.util.ArrayList;

public class Owner extends User {
	
	private Inventory merListByOwner = Inventory.getInstance(); 
	
	// having only this constructor avoids having an owner without a username and password
	public Owner(int username, int password) {
		this.username = username;
		this.password = password;
	}
	
	// Minh moved 2 methods here from ListOfUsers.java class
	public Owner getOwnerUser() {
		return this;
	}

	public void setOwnerUser(Owner ownerUser) {
		this.username = ownerUser.username;
		this.password = ownerUser.password;
	}
	
	// implementation of inherited abstract method from User superclass
	public ArrayList<Merchandise> searchOTCMedicineByName (String name) {
		
		ArrayList<Merchandise> searchMedNameOwner = new ArrayList <Merchandise> ();
		
		for (Merchandise i : merListByOwner.getMerchandise()) {
			if (i.getName().compareTo(name) == 0) {
				searchMedNameOwner.add(i);
			}
		}
		
		return searchMedNameOwner;
	}
	
	// implementation of inherited abstract method from User superclass
	public ArrayList<Merchandise> searchOTCMedicineByType (MERCHANDISE_TYPE type) {
		
		ArrayList<Merchandise> searchMedTypeOwner = new ArrayList <Merchandise> ();
		
		for (Merchandise i : merListByOwner.getMerchandise()) {
			if (i.getType() == type) {
				searchMedTypeOwner.add(i);
			}
		}
		
		return searchMedTypeOwner;
	}
}
