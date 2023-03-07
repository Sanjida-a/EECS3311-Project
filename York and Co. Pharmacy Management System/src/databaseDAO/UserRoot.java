package databaseDAO;

import java.util.ArrayList;

import middleLayer.Patient;
import middleLayer.User;

public interface UserRoot {
	public ArrayList<User> getListOfUsernamesAndPasswords() throws Exception;
	public ArrayList<Patient> getListOfAllPatients() throws Exception;
	public void addPatientToDatabase(Patient newPatient) throws Exception;
	public void updatePatientInDatabase(int IDOfModifiedPatient, Patient actualPatientObject);
}
