package databaseDAO.UserData;

import java.sql.*;
import java.util.ArrayList;

import databaseDAO.superDAO;
import middleLayer.Users.*;

public class UserDAO extends superDAO implements UserRoot {
	
	private ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	private ArrayList<Patient> patientList = new ArrayList<Patient>();
	
	public UserDAO() throws Exception {
		try {
			con = DriverManager.getConnection(url, user, password);
			con.close();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	//reads all rows of usernames and passwords from database and puts it into arrayList
	public ArrayList<User> getListOfUsernamesAndPasswords() throws Exception {
		try {
			allUsernamesAndPasswordsList = new ArrayList<User>(); //need to empty current list first so it gets overriden
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM AllUsernamesAndPasswords;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			long username;
			int password;
			String userType;
			User user;
			
			while (result.next()) { 
				username =  result.getLong("usernameID");
				password = result.getInt("passwordID") ;
				userType = result.getString("userType") ;
				
				if (userType.equalsIgnoreCase("Owner")) {
					user = new Owner(username, password);
				}
				else if (userType.equalsIgnoreCase("Pharmacist")) {
					user = new Pharmacist(username, password);
				}
				else { // case where user is a Patient
					user = new Patient(username, password);
				}				
				allUsernamesAndPasswordsList.add(user);
			}			
			con.close();
		}
		catch (Exception e) {
			throw e;
		}		
		return allUsernamesAndPasswordsList;
	}
	
	//reads all rows of patients from database and puts it into arrayList
	public ArrayList<Patient> getListOfAllPatients() throws Exception {
		try {
			patientList = new ArrayList<Patient>(); //need to empty current list first so it gets overriden
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM Patient;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int dateOfBirth;
			String firstName, lastName, address;
			long phoneNumber, healthCardNumber;
			
			Patient patient;
			
			while (result.next()) { 
				firstName =  result.getString("firstName").toUpperCase();
				lastName =  result.getString("lastName").toUpperCase();
				address =  result.getString("Address").toUpperCase();
				phoneNumber =  result.getLong("phoneNumber");
				healthCardNumber =  result.getLong("healthCardNumber");
				dateOfBirth =  result.getInt("dateOfBirth");
				
				patient = new Patient(firstName, lastName, address, phoneNumber, healthCardNumber, dateOfBirth);
				
				
				patientList.add(patient);
			}			
			con.close();
		}
		catch (Exception e) {
			throw e;
		}		
		return patientList;
	}
	
	//add new patient row to database table (invoked whenever a new patient has been added to the system)
	public void addPatientToDatabase(Patient newPatient) throws Exception {
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			String queryAddToUserAndPassTable = "INSERT INTO AllUsernamesAndPasswords VALUES ('"+newPatient.username+"','"+newPatient.password+"','"+"Patient"+"');";
			PreparedStatement statement1 = con.prepareStatement(queryAddToUserAndPassTable);
			int newRow1 = statement1.executeUpdate(queryAddToUserAndPassTable);
			
			String queryAddToPatientTable = "INSERT INTO Patient VALUES ('"+newPatient.getFirstName()+"','"+newPatient.getLastName()+"','"+newPatient.getAddress()+"','"+newPatient.getPhoneNum()+"','"+newPatient.getHealthCardNum()+"','"+newPatient.getDateOfBirth()+"');";
			PreparedStatement statement2 = con.prepareStatement(queryAddToPatientTable);
			int newRow2 = statement2.executeUpdate(queryAddToPatientTable);
			
			con.close();
		}
		catch (SQLException e) { // exception is thrown if a health card number is entered that already exists in the system because HCNum has to be unique for every person
			throw e;
		}
		
	}
	
	// whenever a specific/individual patient from the list of patients is updated (ex. firstname/lastname/phoneNo/address changed), this method makes sure that that row of patient in the database is updated accordingly
	public void updatePatientInDatabase(long IDOfModifiedPatient, Patient actualPatientObject){
		try {
			con = DriverManager.getConnection(url, user, password);
			
			String updatePatientQuery = "UPDATE Patient SET firstName = ?, lastName = ?, Address = ?, phoneNumber = ?, dateOfBirth = ? WHERE healthCardNumber = ?";
			PreparedStatement statement = con.prepareStatement(updatePatientQuery);

			statement.setString(1, actualPatientObject.getFirstName());
			statement.setString(2, actualPatientObject.getLastName());
			statement.setString(3, actualPatientObject.getAddress());
			statement.setLong(4, actualPatientObject.getPhoneNum());
			statement.setInt(5, actualPatientObject.getDateOfBirth());
			statement.setLong(6, actualPatientObject.getHealthCardNum());
			
			statement.executeUpdate();
		
			con.close();
		}
		catch (Exception e) {
			// exception not expected because not updating primary key healthCardNum
		}
	}
}
