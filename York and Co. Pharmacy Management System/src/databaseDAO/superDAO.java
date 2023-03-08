package databaseDAO;

//import static databaseDAO.superDAO.con;
//import static databaseDAO.superDAO.password;
//import static databaseDAO.superDAO.url;
//import static databaseDAO.superDAO.user;

import java.sql.Connection;
import java.sql.DriverManager;

public class superDAO {
	
	static Connection con;
	static String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	static String user = "root";
	static String password; //make sure to change password based on your password for MySQL
//	
//	public superDAO() {
//		
//	}
//	public superDAO(String userPass) {
//		password = userPass;
//	}
	
	public static void setPassword(String userPass) throws Exception {
		try {
			password = userPass;
			con = DriverManager.getConnection(url, user, password); // tests if password is correct right away to avoid exception
			con.close();
		} catch (Exception e) {
			throw e;
		}
	
	}
	
}
