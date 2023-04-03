package databaseDAO.OrderData;


import java.sql.SQLException;

import java.util.ArrayList;

import middleLayer.Orders.Order;
import middleLayer.Orders.Prescription;



public interface OrderRoot {
	public void addToOrderTable(Order o) throws Exception;	
	public void addToPrescriptionTable (Prescription p) throws Exception;
	public void addRefillToOrderTable(Order o) throws Exception;	
	public int numOfRemainingRefills (long _patientID, int _medicationId) throws SQLException;	
	public int checkIfExistsInPrescriptionTable (long _patientID, int _medicationId)  throws SQLException;
	public ArrayList<Order> getListOfAllOrders() throws Exception;
	public ArrayList<Prescription> getListOfAllPres() throws Exception;
	public void updateRefillsInExistingPresFormInDB(int presNum, int refillsNum ) throws Exception;
}
