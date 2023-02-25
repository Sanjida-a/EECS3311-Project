package databaseDAO;

import java.sql.*;
import java.util.ArrayList;

import middleLayer.Owner;
import middleLayer.Patient;
import middleLayer.Pharmacist;
import middleLayer.User;

public class OrderDAO {
	
	Connection con;
	private String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	private String user = "root";
	private String password = "hello@123456"; //make sure to change password based on your password for MySQL
	
//	private ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	
	public OrderDAO() throws Exception {//what to put in constructor? don't think weven need to open a connection.
		try {

			con = DriverManager.getConnection(url, user, password);
			
			con.close();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void saveToOrder(int _patientID, int _medicationId, int _qty, double _price) {
		try {
			con = DriverManager.getConnection(url, user, password);
			String preparedStatement = " insert into Orders (orderNum , medicationID , patientID , quantityBought , priceAtPurchase )"
					+ " values (?, ?, ?, ?, ?)";
			int _orderNumber = 1;
			PreparedStatement stmt = con.prepareStatement(preparedStatement);
			stmt.setInt(1, _orderNumber);
			stmt.setInt(2, _medicationId);
			stmt.setInt(3, _patientID);
			stmt.setInt(4, _qty);
			stmt.setDouble(5, _price);

			
				stmt.execute(); // execute the preparedstatement
				con.close();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
//	public ArrayList<User> getListOfUsernamesAndPasswords() throws Exception {
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			
//			String queryGetAllRows = "SELECT * FROM AllUsernamesAndPasswords;";
//			Statement statement = con.createStatement();
//			ResultSet result = statement.executeQuery(queryGetAllRows);
//			int username, password;
//			String userType;
//			User user;
//			
//			while (result.next()) { 
//				username =  result.getInt("usernameID");
//				password = result.getInt("passwordID") ;
//				userType = result.getString("userType") ;
//				
//				if (userType.equalsIgnoreCase("Owner")) {
//					user = new Owner(username, password);
//				}
//				else if (userType.equalsIgnoreCase("Pharmacist")) {
//					user = new Pharmacist(username, password);
//				}
//				else { // case where user is a Patient
//					user = new Patient(username, password);
//				}
//				
//				allUsernamesAndPasswordsList.add(user);
//			}
//			
//			con.close();
//		}
//		catch (Exception e) {
//			throw e;
//		}
//		
//		return allUsernamesAndPasswordsList;
//	}
//	
//	public void addPatient(Patient newPatient) throws Exception {
//		
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			
//			String queryAddToUserAndPassTable = "INSERT INTO AllUsernamesAndPasswords VALUES ('"+newPatient.username+"','"+newPatient.password+"','"+"Patient"+"');";
//			PreparedStatement statement1 = con.prepareStatement(queryAddToUserAndPassTable);
//			int newRow1 = statement1.executeUpdate(queryAddToUserAndPassTable);
//			
//			String queryAddToPatientTable = "INSERT INTO Patient VALUES ('"+newPatient.getID()+"','"+newPatient.getFirstName()+"','"+newPatient.getLastName()+"','"+newPatient.getAddress()+"','"+newPatient.getPhoneNum()+"','"+newPatient.getHealthCardNum()+"','"+newPatient.getDateOfBirth()+"');";
//			PreparedStatement statement2 = con.prepareStatement(queryAddToPatientTable);
//			int newRow2 = statement2.executeUpdate(queryAddToPatientTable);
//			
//			con.close();
//		}
//		catch (Exception e) {
//			throw e;
//		}
//		
//	}
}
