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
	
	public void saveToOrder(int _patientID, int _medicationId, int _qty, boolean isPres) throws Exception {
		try {
			
			
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			
			//check patient
			String queryPatientStatement = "SELECT * FROM Patient where healthCardNumber = " + _patientID; 
			ResultSet patientResult = statement.executeQuery(queryPatientStatement);
			
			if(!patientResult.next()) {
				throw new Exception("Non-existent patient");
			}
			patientResult.close();
			
			// get last order Id number
			
//			String queryOrderStatement = "SELECT  orderNum FROM Orders order by orderNum desc limit 1;"; 
//			ResultSet lastOrderResult = statement.executeQuery(queryOrderStatement);					
			
//			int lastOrderNumber = 1;
//			if(lastOrderResult.next()) {
//				int lastId= lastOrderResult.getInt("orderNum");
//				lastOrderNumber = lastId + 1;
//			}
//			lastOrderResult.close();
			
			String queryMedStatement = "SELECT * FROM Medications where medicationID = " + _medicationId; 
			ResultSet medResult = statement.executeQuery(queryMedStatement);					
			
			if(!medResult.next()) {
				throw new Exception("Non-existent medication");
			}
			double price = medResult.getDouble("price");
			
			medResult.close();
			
			price = price * _qty;
			String preparedStatement = " insert into Orders ( medicationID , patientID , quantityBought , priceAtPurchase, isPrescription )"
					+ " values ( ?, ?, ?, ?)";
//			int _orderNumber = lastOrderNumber;

			PreparedStatement stmt = con.prepareStatement(preparedStatement);
	//		stmt.setInt(1, _orderNumber);
			stmt.setInt(1, _medicationId);
			stmt.setInt(2, _patientID);
			stmt.setInt(3, _qty);
			stmt.setDouble(4, price);
			stmt.setBoolean(5, isPres);
				stmt.execute(); // execute the preparedstatement


				con.close();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		
	}
	
	public void addNewPres (int _patientID, int _medicationId, int _numOfRefills) throws Exception{
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			
			String preparedStatement = " insert into Prescriptions ( medicationID , patientID , numOfRefills )"
					+ " values ( ?, ?, ?)";
//			int _orderNumber = lastOrderNumber;

			PreparedStatement stmt = con.prepareStatement(preparedStatement);
	//		stmt.setInt(1, _orderNumber);
			stmt.setInt(1, _medicationId);
			stmt.setInt(2, _patientID);
			stmt.setInt(3, _numOfRefills);

				stmt.execute(); // execute the preparedstatement
				

				con.close();
			
		}
		
		catch (SQLException e1) {
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
