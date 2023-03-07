package databaseDAO;

import java.util.ArrayList;

import middleLayer.Patient;
import middleLayer.User;

public class UserStub implements UserRoot {
	public ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	public ArrayList<Patient> patientList = new ArrayList<Patient>();
	@Override
	public ArrayList<User> getListOfUsernamesAndPasswords() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Patient> getListOfAllPatients() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPatientToDatabase(Patient newPatient) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePatientInDatabase(int IDOfModifiedPatient, Patient actualPatientObject) {
		// TODO Auto-generated method stub

	}

}
