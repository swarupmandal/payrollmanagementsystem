package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.*;

import utility.Util1;

public class CompanyDao {

	public static ArrayList<CompanyMasterBean> loadCompanyList(){
		ArrayList<CompanyMasterBean> companyMasterBeanList = new ArrayList<CompanyMasterBean>();
		Connection connection = DbConnection.createConnection();
		try {
			SQL:{
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
					    preparedStatement =	Util1.createQuery(connection, CompanyMasterSqlQuery.loadCompanyListQuery, null);
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							int companyId = resultSet.getInt("company_id");
							String companyName = resultSet.getString("company_name");
							companyMasterBeanList.add(new CompanyMasterBean(companyName, companyId));
						}
					} finally {
						if(preparedStatement != null){
							preparedStatement.close();
						}
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
		return companyMasterBeanList;
	}
}
