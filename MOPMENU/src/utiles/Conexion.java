package utiles;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

	public Conexion() { }//172.20.7.100   "+ip.getHostAddress()+"

	public Connection getConnection() throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
	    String url = "jdbc:sybase:Tds:"+ip.getHostAddress()+":2638?ServiceName=micros"; //"+ip.getHostAddress()+"
		//String url = "jdbc:sybase:Tds:172.20.97.100:2638?ServiceName=micros";
		String driver = "com.sybase.jdbc3.jdbc.SybDriver";
		String userName = "dba";
		String password = "micros";
		Class.forName(driver).newInstance();
		
		return DriverManager.getConnection(url, userName, password);		

	}
		public Connection conexionBksrv4() throws Exception {
		String url = "jdbc:sqlserver://172.31.1.14:1433;databaseName=micros";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String userName ="Aplicaciones";// "mobile";
		String password =".4pl1c4c10n3s";// ".M0b1l3$.";
		Class.forName(driver).newInstance();
		
		return DriverManager.getConnection(url, userName, password);
	}
		
		
		public Connection getConnection80() throws Exception {
			InetAddress ip = InetAddress.getLocalHost();
		    String url = "jdbc:sybase:Tds:172.31.1.80:2638?ServiceName=micros";
			//String url = "jdbc:sybase:Tds:172.20.136.100:2638?ServiceName=micros";
			String driver = "com.sybase.jdbc3.jdbc.SybDriver";
			String userName = "dba";
			String password = "micros";
			Class.forName(driver).newInstance();
			
			return DriverManager.getConnection(url, userName, password);		
		}
	
	
}

