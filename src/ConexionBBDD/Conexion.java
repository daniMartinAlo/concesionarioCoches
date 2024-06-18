package ConexionBBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	String driver = "com.mysql.jdbc.Driver";
	String database = "concesionariohito2";
	String host = "localhost";
	String url = "jdbc:mysql://"+host+"/"+database+"?useSSL=false&serverTimezone=UTC";
	String username = "root";	
	String pass = "campusfp";
	
	public Connection conectarMySQL() {
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = (Connection) DriverManager.getConnection(url, username, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
