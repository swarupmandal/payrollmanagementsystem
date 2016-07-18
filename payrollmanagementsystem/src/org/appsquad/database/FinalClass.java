package org.appsquad.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import org.zkoss.zhtml.Object;

import utility.Util1;

public class FinalClass {
	
	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		String x1="10";
		String x2="20";
		PreparedStatement preparedStatement =Util1.createQuery(connection, SqlInt1.SQL1, Arrays.asList(x1,x2));
		preparedStatement.executeQuery();
		
	}
}
