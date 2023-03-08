package databaseDAO;


import java.sql.SQLException;

import java.util.ArrayList;


import middleLayer.Merchandise;
import middleLayer.Order;
import middleLayer.Patient;
import middleLayer.Prescription;

public interface OrderRoot {
	public void addToOrderTable(Order o) throws Exception;	
	public void addToPrescriptionTable (Prescription p) throws Exception;
	public void addRefillToOrderTable(Order o) throws Exception;	
	public int numOfRefill (int _patientID, int _medicationId) throws SQLException;	
//	public Merchandise merResult (int _medicationId) throws SQLException;	
//	public Patient patResult (int _patientID) throws SQLException;
	public Boolean checkIfExistsInPrescriptionTable (int _patientID, int _medicationId)  throws SQLException;
	public ArrayList<Order> getListOfAllOrders() throws Exception;
}
