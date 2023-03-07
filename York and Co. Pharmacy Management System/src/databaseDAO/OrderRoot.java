package databaseDAO;


import java.sql.SQLException;

import java.util.ArrayList;


import middleLayer.Merchandise;
import middleLayer.Order;
import middleLayer.Patient;

public interface OrderRoot {
	public void saveToOrder(int _patientID, int _medicationId, int _qty,  int _refill) throws Exception;	
	public void addNewPres (int _patientID, int _medicationId, int _numOfRefills) throws Exception;
	public void refillSave(int _patientID, int _medicationId, int _qty) throws Exception;	
	public int numOfRefill (int _patientID, int _medicationId) throws SQLException;	
	public Merchandise merResult (int _medicationId) throws SQLException;	
	public Patient patResult (int _patientID) throws SQLException;
	public Boolean checkPatMed (int _patientID, int _medicationId)  throws SQLException;
	public ArrayList<Order> getListOfAllOrders() throws Exception;
}
