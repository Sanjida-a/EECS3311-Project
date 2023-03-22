package databaseDAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class superDAO {
	
	protected static Connection con;
	protected static String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	protected static String user = "root";
	protected static String password; //make sure to change password based on your password for MySQL

	
	public static void setPassword(String userPass) throws Exception {
		try {
			password = userPass;
			con = DriverManager.getConnection(url, user, password); // tests if password is correct right away to avoid exception
			con.close();
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	public static Connection getCon() {
		try {
			con = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return con;
	}
	
}
