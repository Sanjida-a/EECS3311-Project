package middleLayer;

import java.util.ArrayList;

public class Patient extends User {
	
	private String firstName;
	private String lastName;
	private String address;
	private int phoneNum;
	private int healthCardNum;
	private int dateOfBirth;
	private ArrayList<Order> ordersMade;  
	private Inventory merList = Inventory.getInstance(); 
	
	public Patient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNum = phoneNum;
		this.healthCardNum = healthCardNum;
		this.dateOfBirth = dateOfBirth;
		this.username = healthCardNum;
		this.password = dateOfBirth;
	}

	public Patient(int healthCardNum, int dateOfBirth) {
		this.healthCardNum = healthCardNum;
		this.dateOfBirth = dateOfBirth;
		this.username = healthCardNum;
		this.password = dateOfBirth;
	}
	
	// below methods are all getters/setters for class variables (except no setters for username/password/healthCardNumber/dateOfBirth...
	// ...because assuming a health card number and date of birth don't change for a client in a lifetime)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(int phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getHealthCardNum() {
		return healthCardNum;
	}

	public int getDateOfBirth() {
		return dateOfBirth;
	}

	// implementation of inherited abstract method from User superclass
	public ArrayList<Merchandise> searchOTCMedicineByName (String name) {
		
		ArrayList<Merchandise> searchMedName = new ArrayList <Merchandise> ();
		
		for (Merchandise i : merList.getMerchandise()) {
			if (i.getisOTC() == true && i.getName().compareTo(name) == 0) { // Patient can only search OVER-THE-COUNTER medications
				searchMedName.add(i);
			}
		}
		
		return searchMedName;
	}
	
	// implementation of inherited abstract method from User superclass
	public ArrayList<Merchandise> searchOTCMedicineByType (MERCHANDISE_TYPE type) {
		
		ArrayList<Merchandise> searchMedType = new ArrayList <Merchandise> ();
		
		for (Merchandise i : merList.getMerchandise()) {
			if (i.getisOTC() == true && i.getType() == type) { // Patient can only search OVER-THE-COUNTER medications
				searchMedType.add(i);
			}
		}
		
		return searchMedType;
	}

}
