package databaseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import middleLayer.MERCHANDISE_FORM;
import middleLayer.MERCHANDISE_TYPE;
import middleLayer.Merchandise;


public class MerchandiseDAO {
	
	Connection con;
	private String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	private String user = "root";
	private String password = "hello123"; //make sure to change password based on your password for MySQL

	private ArrayList<Merchandise> allInventory = new ArrayList<Merchandise>();
	
	public MerchandiseDAO() throws Exception {
		try {
			Merchandise mer1 = new Merchandise( "ADVIL",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, true);
			Merchandise mer2 = new Merchandise( "TYLENOL",  5,  8.00, MERCHANDISE_TYPE.FEVER , MERCHANDISE_FORM.TABLET, true);
			Merchandise mer3 = new Merchandise( "ADVIL",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.TABLET, true);
			Merchandise mer4 = new Merchandise( "TYLENOL",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, true);
			Merchandise mer5 = new Merchandise( "PILL1",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, false);
			Merchandise mer6 = new Merchandise( "PILL2",  10,  5.00, MERCHANDISE_TYPE.FEVER , MERCHANDISE_FORM.TABLET, false);

			allInventory.add(mer1);
			allInventory.add(mer2);
			allInventory.add(mer3);
			allInventory.add(mer4);
			allInventory.add(mer5);
			allInventory.add(mer6);
			
			con = DriverManager.getConnection(url, user, password);
			con.close();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<Merchandise> getListOfMerchandise() {
		return allInventory;
	
	}
	

}
