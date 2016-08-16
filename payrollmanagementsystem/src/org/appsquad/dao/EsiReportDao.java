package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.appsquad.database.DbConnection;
import org.appsquad.sql.EsiReportSql;

import utility.Util1;

public class EsiReportDao {

	public static ArrayList<String> getLvYrList(){
		ArrayList<String> lvYrList = new ArrayList<String>(); 
		if(lvYrList.size()>0){
			lvYrList.clear();
		}
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, EsiReportSql.selectLvYrQuery, null);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					lvYrList.add(resultSet.getString("leave_yr"));
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return lvYrList;
	}
	
	
	
	
	
}
