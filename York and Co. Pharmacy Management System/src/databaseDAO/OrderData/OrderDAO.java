package databaseDAO.OrderData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import databaseDAO.superDAO;


import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;
import middleLayer.Users.*;

public class OrderDAO extends superDAO implements OrderRoot{

	private ArrayList<Order> orderList = new ArrayList<Order>();
	private ArrayList<Prescription> presList = new ArrayList<Prescription>();

	public OrderDAO() throws Exception {
		try {
			con = DriverManager.getConnection(url, user, password);			
			con.close();			
		} catch (Exception e) {
			throw e;
		}
	}
	
	//reads all rows of orders (both OTC and Rx refill orders) from database and puts it into arrayList
	public ArrayList<Order> getListOfAllOrders() throws Exception {
		try {
			orderList = new ArrayList<Order>(); //need to empty current list first so new list overrides
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM Orders;";   // to get all rows from Orders table
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int orderNum, medicationID, quantityBought;
			long patientID;
			double priceAtPurchase;
			boolean isPrescription;
			
			Order order;
			
			while (result.next()) { 
				orderNum =  result.getInt("orderNum");
				medicationID =  result.getInt("medicationID");
				patientID =  result.getLong("patientID");
				quantityBought =  result.getInt("quantityBought");
				priceAtPurchase =  result.getDouble("priceAtPurchase"); // actually refers to totalPriceOfOrder
				isPrescription =  result.getBoolean("isPrescription");
				
				order = new Order(orderNum, medicationID, patientID, quantityBought, priceAtPurchase, isPrescription);
				
				orderList.add(order);  // created Order object added to array list
			}
			
			con.close();
		}
		catch (Exception e) {
			throw e; 
		}
		
		return orderList;
	}
	
	//reads all rows of prescription FORMS from database and puts it into arrayList
	public ArrayList<Prescription> getListOfAllPres() throws Exception {
		try {
			presList = new ArrayList<Prescription>(); //need to empty current list first so new list overrides
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM Prescriptions;";   // to get all rows from prescriptions table
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int prescriptionNum, medicationID, numOfRefills;
			long patientID;
			
			Prescription pres;
			
			while (result.next()) { 
				prescriptionNum =  result.getInt("prescriptionNum");
				medicationID =  result.getInt("medicationID");
				patientID =  result.getLong("patientID");
				numOfRefills =  result.getInt("numOfRefills");
				
				pres = new Prescription(prescriptionNum, medicationID, patientID, numOfRefills);
				
				presList.add(pres);  // created Prescription (form) object added to array list
			}
			
			con.close();
		}
		catch (Exception e) {
			throw e; 
		}
		
		return presList;
	}
	
	//add new order row to database table (invoked whenever a new order has been added)
	public void addToOrderTable(Order o) throws Exception {
		try {		
			con = DriverManager.getConnection(url, user, password);
			
			String preparedStatement = " insert into Orders ( medicationID , patientID , quantityBought , priceAtPurchase, isPrescription )"
					+ " values ( ?, ?, ?, ?, ?)";                
			PreparedStatement stmt = con.prepareStatement(preparedStatement);
			stmt.setInt(1, o.getMedicationID());
			stmt.setLong(2, o.getPatientID());
			stmt.setInt(3, o.getQuantityBought());
			stmt.setDouble(4, o.getTotalPriceOfOrder()); 
			stmt.setBoolean(5, o.getIsPrescription());			
			stmt.execute(); 
			
			con.close();		
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}		
	}
	
	//add new prescription (FORM) row to database table (invoked whenever a new prescription FORM has been added)
	public void addToPrescriptionTable (Prescription p) throws Exception{
		try {
			con = DriverManager.getConnection(url, user, password);		
			String preparedStatement = " insert into Prescriptions ( medicationID , patientID , numOfRefills )"
					+ " values ( ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(preparedStatement);
			stmt.setInt(1, p.getMedicationID());
			stmt.setLong(2, p.getPatientID());
			stmt.setInt(3, p.getOriginalNumOfRefills());
				stmt.execute(); // execute the preparedstatement
				con.close();		
		}	
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		
	}
	
	//add Rx refill (aka Rx order) row to order table (invoked whenever a refill has been done with "Add refill for prescription" button)
	public void addRefillToOrderTable(Order o) throws Exception{
		try {		
			con = DriverManager.getConnection(url, user, password);	
			
			String preparedStatement = " insert into Orders ( medicationID , patientID , quantityBought , priceAtPurchase, isPrescription )"
					+ " values ( ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(preparedStatement);
			stmt.setInt(1, o.getMedicationID());
			stmt.setLong(2, o.getPatientID());
			stmt.setInt(3, o.getQuantityBought());
			stmt.setDouble(4, o.getTotalPriceOfOrder()); 
			boolean _isPres = o.getIsPrescription();
			stmt.setBoolean(5, _isPres);
				stmt.execute(); // execute the preparedstatement
				con.close();		
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}
	
	//calculating how many refills LEFT for prescriptions using prescription forms and order table
	public int numOfRemainingRefills (long _patientID, int _medicationId) throws SQLException {
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			String queryOrderStatement = "SELECT SUM(quantityBought) AS totalBought FROM Orders WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;
			ResultSet numberOfOrder = statement.executeQuery(queryOrderStatement);														
			int numOfOrder = 0;
			if(numberOfOrder.next()) {
				numOfOrder= numberOfOrder.getInt("totalBought");     // to get total number of quantity bought for a given patient and a given medication
			}
			numberOfOrder.close();   
						
			String queryPresStatement = "SELECT SUM(numOfRefills) AS totalRefills  FROM Prescriptions WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;					
			ResultSet numberOfRefill = statement.executeQuery(queryPresStatement);								
			int numOfRefill = 0;
			if(numberOfRefill.next()) {
				numOfRefill= numberOfRefill.getInt("totalRefills");     // to get total refills from prescriptions for a given patient and a given medication
			}
			numberOfRefill.close();
			int refillLeft = numOfRefill - numOfOrder;     // to get total number of refills left/remaining for a given patient and a given medication
			return refillLeft;
		
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}
	
	//to check whether a record exists in prescription table before giving a refill
	public int checkIfExistsInPrescriptionTable(long _patientID, int _medicationId)  throws SQLException{
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			String queryOrderStatement = "SELECT *  FROM Prescriptions WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;
			ResultSet totalRecord = statement.executeQuery(queryOrderStatement);	

			int isRecord = -1;

			while (totalRecord.next()) {
				isRecord =  totalRecord.getInt("prescriptionNum");
			}
			return isRecord; // returns prescriptionNum (primary key) of row in prescriptions table if prescription form record already exists; if not exists, returns -1
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}
	
	// if prescription form record already exists, simply adding new refills to original num of refills (NOT creating a new row in prescriptions table)
	public void updateRefillsInExistingPresFormInDB (int presNum, int refillsNum) throws Exception {
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			
			String queryPresStatement = "SELECT numOfRefills  FROM Prescriptions WHERE prescriptionNum= " + presNum ;					
			ResultSet numberOfOriginalRefill = statement.executeQuery(queryPresStatement);
			int originalRefill = 0;
			while (numberOfOriginalRefill.next()) {
				originalRefill = numberOfOriginalRefill.getInt("numOfRefills");						
			}

			int newRefill = originalRefill + refillsNum;
			String queryOrderStatement = "UPDATE Prescriptions SET numOfRefills = ? WHERE prescriptionNum = ?"; 
			PreparedStatement st = con.prepareStatement(queryOrderStatement);

			st.setInt(1, newRefill);
			st.setInt(2, presNum);
			
			st.executeUpdate();
			
			
			con.close();
			
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		
	}






}
	
