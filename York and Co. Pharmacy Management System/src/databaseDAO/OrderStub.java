package databaseDAO;

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

import middleLayer.Merchandise;
import middleLayer.Order;
import middleLayer.Patient;

public class OrderStub implements OrderRoot {
	public ArrayList<Order> orderList; 
	
	public OrderStub() {
		Order order1 = new Order(1, 1, 1, 10, 10.99);
		Order order2 = new Order(2, 2, 2, 11, 9.99);
		Order order3 = new Order(3, 3, 3, 12, 8.99);
		Order order4 = new Order(4, 4, 4, 13, 7.99);
		orderList.add(order1);
		orderList.add(order2);
		orderList.add(order2);
		orderList.add(order2);
	}
	/*@Override
	public void saveToOrder(int _patientID, int _medicationId, int _qty, int _refill) throws Exception {
		// TODO Auto-generated method stub
		
		Patient getPat = patResult ( _patientID);		
		if(getPat == null) {
			throw new Exception("Non-existent patient");
		}
		Merchandise getMer = merResult(_medicationId);		
		if(getMer == null) {
			throw new Exception("Non-existent medication");
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
			if (_isPres == true) {
				addNewPres (_patientID, _medicationId, _refill);
			}
			con.close();		
	} catch (SQLException e1) {
		e1.printStackTrace();
		throw e1;
	}		
	}

	@Override
	public void addNewPres(int _patientID, int _medicationId, int _numOfRefills) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void refillSave(int _patientID, int _medicationId, int _qty) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int numOfRefill(int _patientID, int _medicationId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Merchandise merResult(int _medicationId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient patResult(int _patientID) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Patient> patients = new ArrayList<Patient>();
		Patient patient = new Patient("Smith", "John", "5324 yonge St", 1112223333, 1111122222, 11111222);
		try {		
			/*con = DriverManager.getConnection(url, user, password);
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
			return new Patient(firstName, lastName, address, phoneNum, healthCardNum, dateOfBirth);	*/	
		/*}
		catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}*/

	@Override
	public Boolean checkPatMed(int _patientID, int _medicationId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Order> getListOfAllOrders() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveToOrder(int _patientID, int _medicationId, int _qty, int _refill) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewPres(int _patientID, int _medicationId, int _numOfRefills) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refillSave(int _patientID, int _medicationId, int _qty) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int numOfRefill(int _patientID, int _medicationId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Merchandise merResult(int _medicationId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient patResult(int _patientID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
