package databaseDAO.UserData;

import java.util.ArrayList;

import middleLayer.Users.*;

public class UserStub implements UserRoot {
	public ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	public ArrayList<Patient> patientList = new ArrayList<Patient>();
	
	public UserStub() {

		User owner = new Owner(1111, 1111);
		User pharm = new Pharmacist(1234,1234);
		User pat = new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222);

		allUsernamesAndPasswordsList.add(owner);
		allUsernamesAndPasswordsList.add(pharm);
		allUsernamesAndPasswordsList.add(pat);
		patientList.add((Patient) pat);
	}
	@Override
	public ArrayList<User> getListOfUsernamesAndPasswords(){
		return allUsernamesAndPasswordsList;
	}

	@Override
	public ArrayList<Patient> getListOfAllPatients() {
		return patientList;
	}

	@Override
	public void addPatientToDatabase(Patient newPatient) throws Exception {
		allUsernamesAndPasswordsList.add(newPatient);
		patientList.add(newPatient);
	}

	@Override
	public void updatePatientInDatabase(long IDOfModifiedPatient, Patient actualPatientObject) {
		
		for (Patient p : patientList) {
			if (p.getHealthCardNum() == IDOfModifiedPatient) {
				p = actualPatientObject;
				break;
			}
		}

	}

}
