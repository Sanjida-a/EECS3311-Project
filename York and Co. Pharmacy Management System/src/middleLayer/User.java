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
	
	public abstract ArrayList<Merchandise> searchOTCMedicineByName (String name);
	
	public abstract ArrayList<Merchandise> searchOTCMedicineByType (MERCHANDISE_TYPE type);
	
}
