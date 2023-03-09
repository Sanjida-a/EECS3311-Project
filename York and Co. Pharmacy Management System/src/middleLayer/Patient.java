package middleLayer;

import java.util.ArrayList;

public class Patient extends User {
//	private static int IDClassVar = 1; //deleting static instance, as this will generate wrong number on different computers
	
//	private int ID;                  // no need ID as healthCardNum is unique, can be used as ID
	private String firstName;
	private String lastName;
	private String address;
	private int phoneNum;
	private int healthCardNum;
	private int dateOfBirth;
	private ArrayList<Order> ordersMade; 
//	private Inventory merList = Inventory.getInstance(); 
	
	public Patient(String firstName, String lastName, String address, int phoneNum, int healthCardNum, int dateOfBirth) {
//		IDClassVar++;
		
//		this.ID = IDClassVar;
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
	public int getID() {             // to return healthCardNum as ID
		return healthCardNum;
	}
	
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
	
	public ArrayList<Order> getOrdersMade() {
		return ordersMade;
	}

	public void setOrdersMade(ArrayList<Order> ordersMade) {
		this.ordersMade = ordersMade;
	}
	
	// implementation of inherited abstract method from User superclass but notice that in this case only concerned with OTC medicine
//	public ArrayList<Merchandise> searchMedicineByName (String name) {
//		
//		ArrayList<Merchandise> searchMedName = new ArrayList <Merchandise> ();
//		
//		for (Merchandise i : merList.getMerchandise()) {
//			if (i.getisOTC() == true && i.getName().compareTo(name) == 0) { // Patient can only search OVER-THE-COUNTER medications
//				searchMedName.add(i);
//			}
//		}
//		
//		return searchMedName;
//	}
//	
//	// implementation of inherited abstract method from User superclass but notice that in this case only concerned with OTC medicine
//	public ArrayList<Merchandise> searchMedicineByType (MERCHANDISE_TYPE type) {
//		
//		ArrayList<Merchandise> searchMedType = new ArrayList <Merchandise> ();
//		
//		for (Merchandise i : merList.getMerchandise()) {
//			if (i.getisOTC() == true && i.getType() == type) { // Patient can only search OVER-THE-COUNTER medications
//				searchMedType.add(i);
//			}
//		}
//		
//		return searchMedType;
//	}

	@Override
	public String toString(){
		return "First name: " + firstName + ", Last name: " + lastName + ", Address: " + address + ", Phone Number: " + phoneNum + ", Health Card: " + healthCardNum + ", Date of birth: " + dateOfBirth + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.firstName == ((Patient)obj).firstName &&
		this.lastName == ((Patient)obj).lastName &&
		this.address == ((Patient)obj).address &&
		this.phoneNum == ((Patient)obj).phoneNum &&
		this.healthCardNum == ((Patient)obj).healthCardNum &&
		this.dateOfBirth == ((Patient)obj).dateOfBirth) {
			return true;
		}
		return false;
		
	}



}
