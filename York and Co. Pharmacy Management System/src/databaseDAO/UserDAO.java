package databaseDAO;

import java.sql.*;
import java.util.ArrayList;

import middleLayer.Owner;
import middleLayer.Patient;
import middleLayer.Pharmacist;
import middleLayer.User;

public class UserDAO {
	
	Connection con;
	private String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	private String user = "root";
	private String password = "hello123"; //make sure to change password based on your password for MySQL
	
	private ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	private ArrayList<Patient> patientList = new ArrayList<Patient>();
	
	public UserDAO() throws Exception {//what to put in constructor? don't think weven need to open a connection.
		try {
			//removed because added to database; establishing connection below to database instead
//			User owner = new Owner(1111111111, 11111111);
//			User pharma1 = new Pharmacist(1234567890,12345678);
//			User patient = new Patient("Smith", "John", "5324 yonge St", 1112223333, 1111122222, 11111222);
//			allUsernamesAndPasswordsList.add(owner);
//			allUsernamesAndPasswordsList.add(pharma1);
//			allUsernamesAndPasswordsList.add(patient);

			con = DriverManager.getConnection(url, user, password);
			
			//tested using few lines before to see if get connection to database - works
//			String query = "select * from AllUsernamesAndPasswords;";
//			Statement statement = con.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			String data = "";
//			while (result.next()) { 
//				String _ID = "ID: " + result.getString("usernameID") + "\t";
//				String _Name = "Name: " + result.getString("passwordID") ;
//				data += _ID + _Name + "\n";	
//			}
//			System.out.println(data);
			
			con.close();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<User> getListOfUsernamesAndPasswords() throws Exception {
		try {
			allUsernamesAndPasswordsList = new ArrayList<User>(); //need to empty current list first so new list overrides
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM AllUsernamesAndPasswords;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int username, password;
			String userType;
			User user;
			
			while (result.next()) { 
				username =  result.getInt("usernameID");
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
	
	public ArrayList<Patient> getListOfAllPatients() throws Exception {
		try {
			patientList = new ArrayList<Patient>(); //need to empty current list first so new list overrides
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM Patient;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int phoneNumber, healthCardNumber, dateOfBirth;
			String firstName, lastName, address;
			
			Patient patient;
			
			while (result.next()) { 
				firstName =  result.getString("firstName");
				lastName =  result.getString("lastName");
				address =  result.getString("Address");
				phoneNumber =  result.getInt("phoneNumber");
				healthCardNumber =  result.getInt("healthCardNumber");
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
	
	// update database by adding new patient
	public void addPatientToDatabase(Patient newPatient) throws Exception {
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			// TO DO FOR AIZA: have to make sure healthcard already doesn't exist bc two patients can't have same healthcard number
			
			String queryAddToUserAndPassTable = "INSERT INTO AllUsernamesAndPasswords VALUES ('"+newPatient.username+"','"+newPatient.password+"','"+"Patient"+"');";
			PreparedStatement statement1 = con.prepareStatement(queryAddToUserAndPassTable);
			int newRow1 = statement1.executeUpdate(queryAddToUserAndPassTable);
			
			String queryAddToPatientTable = "INSERT INTO Patient VALUES ('"+newPatient.getFirstName()+"','"+newPatient.getLastName()+"','"+newPatient.getAddress()+"','"+newPatient.getPhoneNum()+"','"+newPatient.getHealthCardNum()+"','"+newPatient.getDateOfBirth()+"');";
			PreparedStatement statement2 = con.prepareStatement(queryAddToPatientTable);
			int newRow2 = statement2.executeUpdate(queryAddToPatientTable);
			
			con.close();
		}
		catch (SQLException e) {
			throw e;
		}
		
	}
}
