package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDev {

	private static final String SERVER = "127.0.0.1";
	private static final String DB = "snake";
	private static final String USER = "root";
	private static final String PASSWORD = "mvth";
	private static Connection connection = null;
		
	/**
	 * Disable instance from this class
	 * 
	 */
	private ConexaoDev(){}
	
	/**
	 * Create connection to the database
	 * 
	 * @return
	 */
	public static Connection getConexao() {

		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DB, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}
