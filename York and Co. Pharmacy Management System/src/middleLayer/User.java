package middleLayer;

import java.util.ArrayList;

public abstract class User {

	public int username;
	public int password;
	
	public void setUsername(int username) {
		this.username = username;
	}
	
	public void setPassword(int password) {
		this.password = password;
	}
	
	public int getUsername() {
		return this.username;
	}
	
	public int getPassword() {
		return this.password;
	}
	
	// abstract method to be implemented by Owner, Pharmacist and Patient (not implemented here because implementation for patient is different)
	public abstract ArrayList<Merchandise> searchOTCMedicineByName (String name);
	
	// abstract method to be implemented by Owner, Pharmacist and Patient (not implemented here because implementation for patient is different)
	public abstract ArrayList<Merchandise> searchOTCMedicineByType (MERCHANDISE_TYPE type);
	
}
