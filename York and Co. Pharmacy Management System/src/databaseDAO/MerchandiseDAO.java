package databaseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import middleLayer.MERCHANDISE_FORM;
import middleLayer.MERCHANDISE_TYPE;
import middleLayer.Merchandise;
import middleLayer.Owner;
import middleLayer.Patient;
import middleLayer.Pharmacist;
import middleLayer.User;


public class MerchandiseDAO {
	
	Connection con;
	private String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	private String user = "root";
	private String password = "ALVINTA12"; //make sure to change password based on your password for MySQL

	private ArrayList<Merchandise> allInventory = new ArrayList<Merchandise>();
	
	public MerchandiseDAO() throws Exception {
		try {
			con = DriverManager.getConnection(url, user, password);
			con.close();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	//reads all rows of medication from database and puts it into arrayList
	public ArrayList<Merchandise> getListOfMerchandise() { 
		try {
			allInventory = new ArrayList<Merchandise>(); //need to empty current list first so it gets overriden
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM Medications Where isValid = 1;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(queryGetAllRows);
			int medicationID;
		    String name;
		    int quantity;
		    double price;
		    MERCHANDISE_TYPE type;
		    MERCHANDISE_FORM form;
		    boolean isOTC;
		    String description;
		    boolean isValid;
		    
		    Merchandise m;
			
			while (result.next()) { 
				
				medicationID =  result.getInt("medicationID");
				name = result.getString("medName") ;
				quantity =  result.getInt("quantity");
				price =  result.getDouble("price");
				type = MERCHANDISE_TYPE.valueOf(result.getString("medType").toUpperCase());
				form = MERCHANDISE_FORM.valueOf(result.getString("medForm").toUpperCase());
				isOTC = result.getBoolean("isOTC");
				description = result.getString("medDescription") ;
				isValid = result.getBoolean("isValid");
				
				m = new Merchandise(medicationID, name, quantity, price, type, form, isOTC, description, isValid);
				allInventory.add(m);
			}
			
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
//			throw e;
		}
		
		return allInventory;
	
	}
	
	// whenever a specific/individual medication from the inventory is updated (ex. name/price/quantity changed), this method makes sure that that row of medication in the database is updated accordingly
	public void updateMedicationInDatabase(int medIDOfModifiedMedication, Merchandise actualMedicationObject) {
		try {
			con = DriverManager.getConnection(url, user, password);
			
			String updateMedicationQuery = "UPDATE Medications SET medName = ?, quantity = ?, price = ?, medType = ?, medForm = ?, isOTC = ?, medDescription = ?, isValid = ? WHERE medicationID = ?";
			PreparedStatement statement = con.prepareStatement(updateMedicationQuery);

			statement.setString(1, actualMedicationObject.getName());
			statement.setInt(2, actualMedicationObject.getQuantity());
			statement.setDouble(3, actualMedicationObject.getPrice());
			statement.setString(4, actualMedicationObject.getType().toString());
			statement.setString(5, actualMedicationObject.getForm().toString());
			statement.setBoolean(6, actualMedicationObject.getisOTC());
			statement.setString(7, actualMedicationObject.getDescription());
			statement.setBoolean(8, actualMedicationObject.getisValid());
			statement.setInt(9, medIDOfModifiedMedication);
			
			statement.executeUpdate();
		
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
//			throw e;
		}
	}
	
	// delete medication row from database table (invoked whenever a medication has been deleted from inventory)
	public void deleteMedicationInDatabase(int medIDOfDeletedMedication) {
		try {
			con = DriverManager.getConnection(url, user, password);
			
//			String deleteMedicationQuery = "DELETE FROM Medications WHERE medicationID = ?"; // we are not explicitly deleting the row since it would cause errors in other database tables...
			String deleteMedicationQuery = "UPDATE Medications SET isValid = 0 WHERE medicationID = ?"; //... instead we are modifying the value of isValid for that medication
			PreparedStatement statement = con.prepareStatement(deleteMedicationQuery);

			statement.setInt(1, medIDOfDeletedMedication);
			
			statement.executeUpdate();
		
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
//				throw e;
		}
	}
	
	//add new medication row to database table (invoked whenever a new medication has been added to the inventory)
	public void addMedicationToDatabase(Merchandise newMedication) {
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			int isOTCBooleanIntValue = 0; // using .getisOTC() in the SQL statement line below wasn't working since it was returning boolean and not int
			int isValidBooleanIntValue = 0; // same as above
			if (newMedication.getisOTC() == true) {
				isOTCBooleanIntValue = 1;
			}
			if (newMedication.getisValid() == true) {
				isValidBooleanIntValue = 1;
			}
	
			String queryAddToMedicationTable = "INSERT INTO Medications VALUES ('"+newMedication.getMedicationID()+"','"+newMedication.getName()+"','"+newMedication.getQuantity()+"','"+newMedication.getPrice()+"','"+newMedication.getType()+"','"+newMedication.getForm()+"','"+isOTCBooleanIntValue+"','"+newMedication.getDescription()+"','"+isValidBooleanIntValue+"');";
			PreparedStatement statement2 = con.prepareStatement(queryAddToMedicationTable);
			int newRow2 = statement2.executeUpdate(queryAddToMedicationTable);
			
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
//			throw e;
		}
	}
		
	public void updateQuantPurchase (int merID, int quantBought) {
		try {
			con = DriverManager.getConnection(url, user, password);
			int quant = 0;
			String QuantQuery = "SELECT quantity FROM Medications WHERE medicationID = "+ merID;
			PreparedStatement statement = con.prepareStatement(QuantQuery);
			ResultSet setResult = statement.executeQuery(QuantQuery);
			while(setResult.next()) {
				quant = setResult.getInt("quantity");
			}
			int newQuant = quant - quantBought;
	
			String newQuantQuery = "UPDATE Medications SET quantity = ? WHERE medicationID = ?";
			PreparedStatement st = con.prepareStatement(newQuantQuery);
			st.setInt(1, newQuant);
			st.setInt(2, merID);
			st.execute();
			
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
//			throw e;
		}
	}
		
	
		
		
	

}
