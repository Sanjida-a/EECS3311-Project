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
	
	public void saveToOrder(int _patientID, int _medicationId, int _qty, double _price) throws Exception {
		try {
			
			
			con = DriverManager.getConnection(url, user, password);
			
			
			//check patient
			String queryPatientStatement = "SELECT * FROM Patient where healthCardNumber = " + _patientID; 
			Statement statement = con.createStatement();
			ResultSet patientResult = statement.executeQuery(queryPatientStatement);
			
			if(!patientResult.next()) {
				throw new Exception("Non-existent patient");
			}
			
			String preparedStatement = " insert into Orders (orderNum , medicationID , patientID , quantityBought , priceAtPurchase )"
					+ " values (?, ?, ?, ?, ?)";
			int _orderNumber = 2;

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
			throw e1;
		}
		
		
		
	}
	
//	public ArrayList<Patient> getListOfPatient() throws Exception {
//		try {
//			con = DriverManager.getConnection(url, user, password);
//			
//			String queryGetAllRows = "SELECT * FROM Patient ;"; 
//			Statement statement = con.createStatement();
//			ResultSet result = statement.executeQuery(queryGetAllRows);
//			int ID;
//			int DOB;
//			Patient patient;
//			
//			while (result.next()) { 
//				ID =  result.getInt("healthCardNumber");
//				DOB = result.getInt("dateOfBirth") ;
//					patient = new Patient(ID, DOB);
//				
//				patientList.add(patient);
//			}
//			
//			con.close();
//		}
//		catch (Exception e) {
//			throw e;
//		}
//		
//		return patientList;
//	}
}
