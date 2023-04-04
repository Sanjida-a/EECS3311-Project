package testCases.IntegrationTests;


import databaseDAO.superDAO;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Users.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import presentation.USER;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
	
	private static Connection con;
	
	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("hello123");// TA please change this according to your mySQL password in order for the tests to work
			con = superDAO.getCon();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
    void searchMedicineByNameForAdmin() {
        
		Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        ArrayList<Merchandise> answer = new ArrayList<Merchandise>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Medications Where isValid = 1 AND medName = 'ADVIL';";
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
				answer.add(m);
			}
	
		} catch (Exception e) {
		}
		
		assertEquals(answer.toString(), o.searchMedicineByName("ADVIL", USER.OWNER).toString());
    }
	
    @Test
    void searchMedicineByNameNotFound() { //assumes a medication with name "CompletelyRandomNoName" will never exist in inventory
        
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        assertEquals("[]", o.searchMedicineByName("CompletelyRandomNoName", USER.OWNER).toString());
    }
    
    @Test
    void searchMedicineByNameForPatient() {
        
		Patient o = new Patient(0, 0);	//User is abstract so it has to be created using its subclasses
        ArrayList<Merchandise> answer = new ArrayList<Merchandise>();
		
		try { // difference from admin test is that isOTC = 1 as well (guests/patients only have access to OTC medicine)
			String queryGetAllRows = "SELECT * FROM Medications Where isValid = 1 AND isOTC = 1 and medName = 'ADVIL';";
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
				answer.add(m);
			}
	
		} catch (Exception e) {
		}
		
		assertEquals(answer.toString(), o.searchMedicineByName("ADVIL", USER.PATIENT).toString());
    }
    
    @Test
    void searchMedicineByTypeForAdmin() {
        
    	Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        ArrayList<Merchandise> answer = new ArrayList<Merchandise>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Medications Where isValid = 1 AND medType = 'COLD';";
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
				answer.add(m);
			}
	
		} catch (Exception e) {
		}
		
		assertEquals(answer.toString(), o.searchMedicineByType(MERCHANDISE_TYPE.COLD, USER.OWNER).toString());
		
    }
    
    @Test
    void searchMedicineByTypeForPatient() {
        
    	Patient o = new Patient(0, 0);	//User is abstract so it has to be created using its subclasses
        ArrayList<Merchandise> answer = new ArrayList<Merchandise>();
		
		try {
			String queryGetAllRows = "SELECT * FROM Medications Where isValid = 1 AND isOTC = 1 AND medType = 'COLD';";
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
				answer.add(m);
			}
	
		} catch (Exception e) {
		}
		
		assertEquals(answer.toString(), o.searchMedicineByType(MERCHANDISE_TYPE.COLD, USER.PATIENT).toString());
		
    }

}