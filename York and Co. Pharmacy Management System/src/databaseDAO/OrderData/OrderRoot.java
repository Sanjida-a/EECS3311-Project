package databaseDAO.OrderData;


import java.sql.SQLException;

import java.util.ArrayList;

import middleLayer.Orders.Order;
import middleLayer.Orders.Prescription;



public interface OrderRoot {
	public void addToOrderTable(Order o) throws Exception;	
	public void addToPrescriptionTable (Prescription p) throws Exception;
	public void addRefillToOrderTable(Order o) throws Exception;	
	public int numOfRefill (long _patientID, int _medicationId) throws SQLException;	
	public Boolean checkIfExistsInPrescriptionTable (long _patientID, int _medicationId)  throws SQLException;
	public ArrayList<Order> getListOfAllOrders() throws Exception;
}
