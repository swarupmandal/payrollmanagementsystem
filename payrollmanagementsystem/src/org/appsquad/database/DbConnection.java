package org.appsquad.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

	public static Connection createConnection(){
		Connection connection = null;
		try {
			
			Class.forName("org.postgresql.Driver");
			
			connection = DriverManager.getConnection(DbCONSTANTS.JDBCURL, DbCONSTANTS.USERNAME,DbCONSTANTS.PASSWORD);			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		//System.out.println("Connection ::"+connection);
		return connection;
	}
	
	/*public static void main(String[] args) {
		createConnection();
	}*/
	
}
