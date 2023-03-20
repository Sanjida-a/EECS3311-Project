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

	public OrderDAO() throws Exception {
		try {
			con = DriverManager.getConnection(url, user, password);			
			con.close();			
		} catch (Exception e) {
			throw e;
		}
	}
	
	//reads all rows of orders from database and puts it into arrayList
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
				priceAtPurchase =  result.getDouble("priceAtPurchase");
				isPrescription =  result.getBoolean("isPrescription");
				
				order = new Order(orderNum, medicationID, patientID, quantityBought, priceAtPurchase, isPrescription);
				
				orderList.add(order);  // to create Order object and put in a list
			}
			
			con.close();
		}
		catch (Exception e) {
			throw e; 
		}
		
		return orderList;
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
	
	//add new prescription row to database table (invoked whenever a new prescription has been added)
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
	
	//add prescription refill row to database table (invoked whenever a refill has been done with "give refill" button)
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
	
	//checking how many refills left for prescriptions
	public int numOfRefill (long _patientID, int _medicationId) throws SQLException {
		try {		
			con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			String queryOrderStatement = "SELECT SUM(quantityBought) AS totalBought FROM Orders WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;
			ResultSet numberOfOrder = statement.executeQuery(queryOrderStatement);														
			int numOfOrder = 0;
			if(numberOfOrder.next()) {
				numOfOrder= numberOfOrder.getInt("totalBought");     // to get total number of orders for a given patient and a given medication
			}
			numberOfOrder.close();   
						
			String queryPresStatement = "SELECT SUM(numOfRefills) AS totalRefills  FROM Prescriptions WHERE medicationID= " + _medicationId +" AND" + " patientID=" + _patientID;					
			ResultSet numberOfRefill = statement.executeQuery(queryPresStatement);								
			int numOfRefill = 0;
			if(numberOfRefill.next()) {
				numOfRefill= numberOfRefill.getInt("totalRefills");     // to get total refills from prescriptions for a given patient and a given medication
			}
			numberOfRefill.close();
			int refillLeft = numOfRefill - numOfOrder;     // to get total number of refills left for a given patient and a given medication
			return refillLeft;
		
		}
			catch (SQLException e1) {
				e1.printStackTrace();
				throw e1;
			}
	}
	
	//to check whether a record exists in prescription table before giving a refill
	public Boolean checkIfExistsInPrescriptionTable(long _patientID, int _medicationId)  throws SQLException{
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


}
	
