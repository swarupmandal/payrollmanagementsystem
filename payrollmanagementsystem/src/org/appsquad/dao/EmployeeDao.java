package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EmployeeMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.EmployeeMasterSql;

import utility.Util1;

public class EmployeeDao {
	
	public static void loadCompanyList(ArrayList<CompanyMasterBean> companyBeanList){

	try{	
		Connection connection = DbConnection.createConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadCompanyListQuery, null);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				CompanyMasterBean bean = new CompanyMasterBean();
				bean.setCompanyName(resultSet.getString("company_name"));
				bean.setCompanyId(resultSet.getInt("company_id"));
				companyBeanList.add(bean);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(preparedStatement != null){
				preparedStatement.close();
			}
			if(connection != null){
				connection.close();
			}
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}
	
	
	public static void loadUnitList(ArrayList<UnitMasterBean> unitBeanList){


		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadUnitListQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					UnitMasterBean bean = new UnitMasterBean();
					bean.setUnitName(resultSet.getString("unit_name"));
					bean.setUnitId(resultSet.getInt("unit_id"));
					unitBeanList.add(bean);
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
	}
	
	
	
	
	
	
	public static boolean insertEmployeeInfo(EmployeeMasterBean bean, String userName){
		boolean isInserted = false;
		try {
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.employeeInsertQuery, Arrays.asList(bean.getEmployeeCode(),bean.getEmployeeName(),bean.getCompanyId(),bean.getUnitId(),bean.getEmpPhone(),
									bean.getEmpEmail(),bean.getGender(), userName,userName));
				int i = preparedStatement.executeUpdate();
				if(i>0){
					isInserted = true;
					
					
				}
				
			} catch (Exception e) {
				connection.rollback();
				e.printStackTrace();
			}finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInserted;
		
	}

}
