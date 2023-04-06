package databaseDAO.UserData;

import java.sql.SQLException;
import java.util.ArrayList;

import middleLayer.Users.*;

public class UserStub implements UserRoot {
	public ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	public ArrayList<Patient> patientList = new ArrayList<Patient>();
	
	public UserStub() {

		User owner = new Owner(1111, 1111);
		User pharm = new Pharmacist(1234,1234);
		User pat1 = new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 19990101);
		User pat2 = new Patient("JANE", "DOE", "5000 YONGE ST", 2223334444L, 2222233333L, 19990202);

		allUsernamesAndPasswordsList.add(owner);
		allUsernamesAndPasswordsList.add(pharm);
		allUsernamesAndPasswordsList.add(pat1);
		allUsernamesAndPasswordsList.add(pat2);
		patientList.add((Patient) pat1);
		patientList.add((Patient) pat2);
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
		for (Patient p : this.patientList) {
			if(p.getHealthCardNum() == newPatient.getHealthCardNum()) {
				throw new SQLException();	//ensures no duplicated entry
			}
		}
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
