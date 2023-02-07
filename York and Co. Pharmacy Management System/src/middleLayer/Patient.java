package middleLayer;

import java.util.ArrayList;

public class Patient extends User {
	
	private String firstName;
	private String lastName;
	private String address;
	private int phoneNum;
	private int healthCardNum;
	private int dateOfBirth;
	//private ArrayList<Order> ordersMade; Order class not made yet
	
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

	// don't want this to alter username and pass right?
//	public void setHealthCardNum(int healthCardNum) {
//		this.healthCardNum = healthCardNum;
//	}

	public int getDateOfBirth() {
		return dateOfBirth;
	}

//	public void setDateOfBirth(int dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
	
	
}
