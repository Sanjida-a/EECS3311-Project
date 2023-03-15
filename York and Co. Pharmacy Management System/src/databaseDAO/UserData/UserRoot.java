package databaseDAO.UserData;

import java.util.ArrayList;

import middleLayer.Users.*;

public interface UserRoot {
	public ArrayList<User> getListOfUsernamesAndPasswords() throws Exception;
	public ArrayList<Patient> getListOfAllPatients() throws Exception;
	public void addPatientToDatabase(Patient newPatient) throws Exception;
	public void updatePatientInDatabase(long IDOfModifiedPatient, Patient actualPatientObject);
}
