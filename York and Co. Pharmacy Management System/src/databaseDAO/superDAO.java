package databaseDAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class superDAO {
	
	protected static Connection con;
	protected static String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	protected static String user = "root";
	protected static String password;

	
	public static void setPassword(String userPass) throws Exception {
		try {
			password = userPass;
			con = DriverManager.getConnection(url, user, password); // tests if password is correct right away to avoid exception
			con.close();
		} catch (Exception e) {
			throw e; // exception thrown if connection fails (password is incorrect)
		}
	
	}
	
	public static Connection getCon() {
		try {
			con = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e) {
			// no exception expected because this method was created for Integration Tests and...
			// ...is only invoked once the password has been set correctly
		} 
		return con;
	}
	
}
