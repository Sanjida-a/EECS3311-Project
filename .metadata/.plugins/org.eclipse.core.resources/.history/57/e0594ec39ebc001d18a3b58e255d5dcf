package databaseDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import middleLayer.Order;

import middleLayer.MERCHANDISE_FORM;
import middleLayer.MERCHANDISE_TYPE;
import middleLayer.Merchandise;
import middleLayer.Owner;
import middleLayer.Patient;
import middleLayer.Pharmacist;
import middleLayer.User;

public class OrderDAO implements OrderRoot{
	
	Connection con;
	private String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	private String user = "root";

	private String password = "Motp1104#"; //make sure to change password based on your password for MySQL


	private ArrayList<Order> orderList = new ArrayList<Order>();

//	private ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	
	public OrderDAO() throws Exception {
		try {
			con = DriverManager.getConnection(url, user, password);			
			con.close();			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void saveToOrder(int _patientID, int _medicationId, int _qty,  int _refill) throws Exception {
		try {		
			con = DriverManager.getConnection(url, user, password);
			Patient getPat = patResult ( _patientID);		
			if(getPat == null) {
				throw new Exception("Non-existent patient");
			}
			Merchandise getMer = merResult(_medicationId);		
			if(getMer == null) {
				throw new Exception("Non-existent medication");
			}
			if (getMer.getQuantity() <= 0 || getMer.getisValid() == false) {
				throw new Exception("Check inventory!");
			}
			double price = getMer.getPrice();			
			price = price * _qty;
			boolean _isPres = getMer.getisOTC();
			String preparedStatement = " insert into Orders ( medicationID , patientID , quantityBought , priceAtPurchase, isPrescription )"
					+ " values ( ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(preparedStatement);
			stmt.setInt(1, _medicationId);
			stmt.setInt(2, _patientID);
			stmt.setInt(3, _qty);
			stmt.setDouble(4, price); 
			stmt.setBoolean(5, _isPres);			
				stmt.execute(); 
				if (_isPres == false) {
					addNewPres (_patientID, _medicationId, _refill);
				}
				con.close();		
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}		
	}
	
	public void addNewPres (int _patientID, int _medicationId, int _numOfRefills) throws Exception{
		try {
			con = DriverManager.getConnection(url, user, password);		
			String preparedStatement = " insert into Prescriptions ( medicationID , patientID , numOfRefills )"
					+ " values ( ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(preparedStatement);
			stmt.setInt(1, _medicationId);
			stmt.setInt(2, _patientID);
			stmt.setInt(3, _numOfRefills);
				stmt.execute(); // execute the preparedstatement
				con.close();		
		}	
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		
	}
	
	public void refillSave(int _patientID, int _medicationId, int _qty) throws Exception{
		try {		
			con = DriverManager.getConnection(url, user, password);	
			if (!checkPatMed(_patientID, _medicationId)) {
				throw new Exception ("No record found!");
			}
			Patient getPat = patResult ( _patientID);	
			if(getPat == null) {
				throw new Exception("Non-existent patient!");
			}
			Merchandise getMer = merResult(_medicationId);
			if(getMer == null) {
				throw new Exception("Non-existent medication!");
			}
			if (getMer.getQuantity() <= 0 || getMer.getisValid() == false) {
				throw new Exception("Check inventory!");
			}
			// check number of refills left
			int refillLeft = numOfRefill(_patientID,_medicationId ) ;
			if (refillLeft <= 0) {
				throw new Exception("No more refill left!");
			}
			double price = getMer.getPrice();
			String preparedStatement = " insert into Orders ( medicationID , patientID , quantityBought , priceAtPurchase, isPrescription )"
					+ " values ( ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(preparedStatement);
			stmt.setInt(1, _medicationId);
			stmt.setInt(2, _patientID);
			stmt.setInt(3, _qty);
			stmt.setDouble(4, price); 
			boolean _isPres = getMer.getisOTC();
			stmt.setBoolean(5, _isPres);
				stmt.execute(); // execute the preparedstatement
				con.close();		
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}
	
	public int numOfRefill (int _patientID, int _medicationId) throws SQLException {
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			String queryOrderStatement = "SELECT SUM(quantityBought) AS totalBought FROM Orders WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;
			ResultSet numberOfOrder = statement.executeQuery(queryOrderStatement);														
			int numOfOrder = 0;
			if(numberOfOrder.next()) {
				numOfOrder= numberOfOrder.getInt("totalBought");
			}
			numberOfOrder.close();
						
			String queryPresStatement = "SELECT SUM(numOfRefills) AS totalRefills  FROM Prescriptions WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;					
			ResultSet numberOfRefill = statement.executeQuery(queryPresStatement);								
			int numOfRefill = 0;
			if(numberOfRefill.next()) {
				numOfRefill= numberOfRefill.getInt("totalRefills");
			}
			numberOfRefill.close();
			int refillLeft = numOfRefill - numOfOrder;
			return refillLeft;
		
		}
			catch (SQLException e1) {
				e1.printStackTrace();
				throw e1;
			}
		

		
	}
	
	public Merchandise merResult (int _medicationId) throws SQLException{
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			String queryMedStatement = "SELECT * FROM Medications where medicationID = " + _medicationId; 
			ResultSet medResult = statement.executeQuery(queryMedStatement);	
			if(!medResult.next()) {
				return null;
			}			
				int medicationID = medResult.getInt("medicationID");
			    String name = medResult.getString("medName");
			    int quantity = medResult.getInt("quantity");
			    double price = medResult.getDouble("price");
			    MERCHANDISE_TYPE type = MERCHANDISE_TYPE.valueOf(medResult.getString("medType"));
			    MERCHANDISE_FORM form = MERCHANDISE_FORM.valueOf(medResult.getString("medForm"));
			    boolean isOTC = medResult.getBoolean("isOTC");
			    String description = medResult.getString("medDescription");
			    boolean isValid = medResult.getBoolean("isValid");
			
			return new Merchandise(medicationID, name, quantity, price, type, form, isOTC, description, isValid);		
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}
	
	public Patient patResult (int _patientID) throws SQLException{
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			String queryPatientStatement = "SELECT * FROM Patient where healthCardNumber = " + _patientID; 
			ResultSet patientResult = statement.executeQuery(queryPatientStatement);		
			if(!patientResult.next()) {
				return null;
			}	
			String firstName = patientResult.getString("firstName");
			String lastName = patientResult.getString("lastName");
			String address = patientResult.getString("Address");
			int phoneNum =patientResult.getInt("phoneNumber");
			int healthCardNum =patientResult.getInt("healthCardNumber");
			int dateOfBirth =patientResult.getInt("dateOfBirth");			
			return new Patient(firstName, lastName, address, phoneNum, healthCardNum, dateOfBirth);		
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}
	
	public Boolean checkPatMed (int _patientID, int _medicationId)  throws SQLException{
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			String queryOrderStatement = "SELECT *  FROM Prescriptions WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;
			ResultSet totalRecord = statement.executeQuery(queryOrderStatement);	
			boolean isRecord = true;
			if (!totalRecord.next()) {
				isRecord = false;
			}
			return isRecord;
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}
	public ArrayList<Order> getListOfAllOrders() throws Exception {
		try {
			orderList = new ArrayList<Order>(); //need to empty current list first so new list overrides
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM Orders;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int orderNum, medicationID, patientID, quantityBought;
			double priceAtPurchase;
			
			Order order;
			
			while (result.next()) { 
				orderNum =  result.getInt("orderNum");
				medicationID =  result.getInt("medicationID");
				patientID =  result.getInt("patientID");
				quantityBought =  result.getInt("quantityBought");
				priceAtPurchase =  result.getDouble("priceAtPurchase");
				
				order = new Order(orderNum, medicationID, patientID, quantityBought, priceAtPurchase);
				
				
				orderList.add(order);
			}
			
			con.close();
		}
		catch (Exception e) {
			throw e; 
		}
		
		return orderList;
	}

	
	//	private Boolean checkOTC (int _medicationId) throws Exception{
//		try {		
//			con = DriverManager.getConnection(url, user, password);
//			Statement statement = con.createStatement();		
//			Merchandise med = merResult(_medicationId);
//			boolean isOTC  = true;
//			if (med == null) {
//				throw new Exception ("Non-existent medication!");
//			}
//			if (!med.getisOTC()) {
//				isOTC = false;
//			}
//			return isOTC;
//		}
//		catch (SQLException e1) {
//			e1.printStackTrace();
//			throw e1;
//		}		
//	}


}
	
