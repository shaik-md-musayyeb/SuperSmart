package utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	public Properties loadPropertiesFile() throws IOException {
		Properties p = new Properties();
		InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream("jdbc.properties");
		p.load(in);
		in.close();
		return p;
		
	}
	public Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
		Properties p = null;
		p=loadPropertiesFile();
		String driver = p.getProperty("driver");
		String url = p.getProperty("url");
		Class.forName(driver);
		Connection conn = null;
		conn = DriverManager.getConnection(url);
//		if(conn != null)
//			System.out.println("\nconnection sucess");
		return conn;
	}
}

