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
	

	public ArrayList<Merchandise> searchOTCMedicineByName (String name) {
		ArrayList<Merchandise> searchMedName = new ArrayList <Merchandise> ();
		for (Merchandise i : merList.getMerchandise()) {
			if (i.getisOTC() == true && i.getName().compareTo(name) == 0) {
				searchMedName.add(i);
			}
		}
		return searchMedName;
	}
	
	public ArrayList<Merchandise> searchOTCMedicineByType (MERCHANDISE_TYPE type) {
		ArrayList<Merchandise> searchMedType = new ArrayList <Merchandise> ();
		for (Merchandise i : merList.getMerchandise()) {
			if (i.getisOTC() == true && i.getType() == type) {
				searchMedType.add(i);
			}
		}
		return searchMedType;
	}
	
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Inventory merList = Inventory.getInstance();
		for (Merchandise i : merList.getMerchandise())
			System.out.println(i);
		Patient abc = new Patient("abc", "xyz" , "address" , 123456, 111111, 12121212);
		ArrayList<Merchandise> list = abc.searchOTCMedicineByName("Tylenol");
		System.out.println(list);
		ArrayList<Merchandise> listType = abc.searchOTCMedicineByType(MERCHANDISE_TYPE.FEVER);
		System.out.println(listType);

	}*/
	
	
}
