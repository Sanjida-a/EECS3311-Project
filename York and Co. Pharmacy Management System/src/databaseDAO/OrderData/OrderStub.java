package databaseDAO.OrderData;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*(int orderNum,int medicationID, int patientID, int quantityBought, double priceAtPurchase )
 * 
 * 
 */
import java.util.ArrayList;


import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.UserData.UserStub;
import middleLayer.MerchandiseInventory.Merchandise;
import middleLayer.Orders.*;
import middleLayer.Users.Patient;

public class OrderStub implements OrderRoot {
	public ArrayList<Order> orderList; 
	public ArrayList<Prescription> prescriptionList;
    public MerchandiseStub _merStub = new MerchandiseStub();
    public UserStub _userStub = new UserStub();
	public ArrayList<Patient> patients ;
    public ArrayList<Merchandise> medications;    

	public OrderStub() {
		medications = _merStub.getListOfMerchandise();
		patients = _userStub.getListOfAllPatients();
		orderList = new ArrayList<Order>();
		prescriptionList = new ArrayList<Prescription>();
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);   
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		orderList.add(order1);
		orderList.add(order2);
		orderList.add(order3);
		orderList.add(order4);		
		Prescription pres1 = new Prescription(1, 3, 1111122222, 10);
		Prescription pres2 = new Prescription(1, 4, 1111122222, 20);
		prescriptionList.add(pres1);
		prescriptionList.add(pres2);
    }
	
	@Override
	public ArrayList<Order> getListOfAllOrders() throws Exception {
		return orderList;
	}
	@Override
	public void addToOrderTable(Order o) throws Exception {
		for(Order order : orderList) {
			if(order.equals(o)) {
				throw new SQLException();
			}
		}
		orderList.add(o);
	}
	@Override
	public void addToPrescriptionTable(Prescription p) throws Exception {
		prescriptionList.add(p);
	}
	@Override
	public void addRefillToOrderTable(Order o) throws Exception {
		orderList.add(o);
	}
	
	@Override
	public int numOfRefill(long _patientID, int _medicationId)  {
		int sumQuantityBought = 0;
		for (Order o: orderList) {
			if ((o.getMedicationID() == _medicationId) && (o.getPatientID() == _patientID)) {
				sumQuantityBought += o.getQuantityBought();
			}
		}
		
		int numOfOriginalRefills = 0;
		for (Prescription p: prescriptionList) {
			if ((p.getMedicationID() == _medicationId) && (p.getPatientID() == _patientID)) {
				numOfOriginalRefills += p.getOriginalNumOfRefills();
			}
		}
		
		int refillsLeft = numOfOriginalRefills - sumQuantityBought;
		return refillsLeft;

	}
	
	@Override
	public Boolean checkIfExistsInPrescriptionTable(long _patientID, int _medicationId) throws SQLException {		
		Boolean existsInPrescriptionList = false;
		for (Prescription p: prescriptionList) {
			if ((p.getMedicationID() == _medicationId) && (p.getPatientID() == _patientID)) {
				existsInPrescriptionList = true;
				break;
			}
		}
		
		return existsInPrescriptionList;
		
	}

	@Override
	public ArrayList<Prescription> getListOfAllPres() throws Exception {
		return prescriptionList;
	}	
}
