package databaseDAO;

import java.sql.Connection;

public abstract class DAOConnection {
	protected Connection con;
	protected final String url = "jdbc:mysql://localhost:3306/3311Team8Project";
	protected final String user = "root";
	protected final String password = "Motp1104#"; //make sure to change password based on your password for MySQL

}
