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
	private String password = "hello123"; //make sure to change password based on your password for MySQL

	private ArrayList<Merchandise> allInventory = new ArrayList<Merchandise>();
	
	public MerchandiseDAO() throws Exception { //does constructor even need anything? MAYBE CALL GETLISTOFMERCHANDISE on initialization?
		try {
//			Merchandise mer1 = new Merchandise( "ADVIL",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, true);
//			Merchandise mer2 = new Merchandise( "TYLENOL",  5,  8.00, MERCHANDISE_TYPE.FEVER , MERCHANDISE_FORM.TABLET, true);
//			Merchandise mer3 = new Merchandise( "ADVIL",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.TABLET, true);
//			Merchandise mer4 = new Merchandise( "TYLENOL",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, true);
//			Merchandise mer5 = new Merchandise( "PILL1",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, false);
//			Merchandise mer6 = new Merchandise( "PILL2",  10,  5.00, MERCHANDISE_TYPE.FEVER , MERCHANDISE_FORM.TABLET, false);
//
//			allInventory.add(mer1);
//			allInventory.add(mer2);
//			allInventory.add(mer3);
//			allInventory.add(mer4);
//			allInventory.add(mer5);
//			allInventory.add(mer6);
//			
			con = DriverManager.getConnection(url, user, password);
			con.close();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<Merchandise> getListOfMerchandise() { //reads all values from database
		try {
			allInventory = new ArrayList<Merchandise>(); //need to empty current list first so new list overrides
			
			con = DriverManager.getConnection(url, user, password);
			
			String queryGetAllRows = "SELECT * FROM Medications;";
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
		    
		    Merchandise m;
		    // String description; MAYBE don't put in database because so long?
			
			while (result.next()) { 
				
				medicationID =  result.getInt("medicationID");
				name = result.getString("medName") ;
				quantity =  result.getInt("quantity");
				price =  result.getDouble("price");
				type = MERCHANDISE_TYPE.valueOf(result.getString("medType").toUpperCase());
				form = MERCHANDISE_FORM.valueOf(result.getString("medForm").toUpperCase());
				isOTC = result.getBoolean("isOTC");
				description = result.getString("medDescription") ;
				
				m = new Merchandise(medicationID, name, quantity, price, type, form, isOTC, description);
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
	
	// whenever a specific/individual medication from the inventory is updated (ex. name/price/quantity changed), this method makes sure that row of medication in the database is updated accordingly
	public void updateMedicationInDatabase(int medIDOfModifiedMedication, Merchandise actualMedicationObject) {
		try {
			con = DriverManager.getConnection(url, user, password);
			
			String updateMedicationQuery = "UPDATE Medications SET medName = ?, quantity = ?, price = ?, medType = ?, medForm = ?, isOTC = ?, medDescription = ? WHERE medicationID = ?";
			PreparedStatement statement = con.prepareStatement(updateMedicationQuery);

			statement.setString(1, actualMedicationObject.getName());
			statement.setInt(2, actualMedicationObject.getQuantity());
			statement.setDouble(3, actualMedicationObject.getPrice());
			statement.setString(4, actualMedicationObject.getType().toString()); //check if works
			statement.setString(5, actualMedicationObject.getForm().toString()); //check if works
			statement.setBoolean(6, actualMedicationObject.getisOTC());
			statement.setString(7, actualMedicationObject.getDescription());
			statement.setInt(8, medIDOfModifiedMedication);
			
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
			
			// MINH YOU WILL HAVE TO ALTER THIS BASED ON THE COLUMN YOU ARE ADDING TO THE DATABASE TABLE SO THAT WE DON'T ACTUALLY DELETE IT
			String deleteMedicationQuery = "DELETE FROM Medications WHERE medicationID = ?";
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
	
	public void addMedicationToDatabase(Merchandise newMedication) {
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			int isOTCBooleanIntValue = 0; // using .getisOTC() in the SQL statement line below wasn't working since it was returning boolean and not int
			if (newMedication.getisOTC() == true) {
				isOTCBooleanIntValue = 1;
			}
	
			String queryAddToMedicationTable = "INSERT INTO Medications VALUES ('"+newMedication.getMedicationID()+"','"+newMedication.getName()+"','"+newMedication.getQuantity()+"','"+newMedication.getPrice()+"','"+newMedication.getType()+"','"+newMedication.getForm()+"','"+isOTCBooleanIntValue+"','"+newMedication.getDescription()+"');";
			PreparedStatement statement2 = con.prepareStatement(queryAddToMedicationTable);
			int newRow2 = statement2.executeUpdate(queryAddToMedicationTable);
			
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
//			throw e;
		}
		
	}
		
		
	

}
