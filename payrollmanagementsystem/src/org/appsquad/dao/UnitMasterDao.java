package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.UnitMasterSqlQuery;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class UnitMasterDao {

	public static void onLoad(ArrayList<UnitMasterBean> unitMasterBeanList ){
		if(unitMasterBeanList.size()>0){
			unitMasterBeanList.clear();
		}
		Connection connection =null;
		try {		
			  sql:{	
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
			
				try {
					preparedStatement = Util1.createQuery(connection, UnitMasterSqlQuery.onLoadUnitMasterQuery, null);
					System.out.println("u ni t " + preparedStatement);
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						int unitId = resultSet.getInt("unit_id");
						int companyId = resultSet.getInt("company_id");
						String unitName = resultSet.getString("unit_name");
						String company_Name = resultSet.getString("company_name");
						unitMasterBeanList.add(new UnitMasterBean(unitId, companyId, unitName, company_Name));
					}  
				} finally{
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
	}
	
	public static void insertBloodGroupData(UnitMasterBean unitMasterBean){
		boolean isInserted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							UnitMasterSqlQuery.insertUnitMasterQuery, Arrays.asList(unitMasterBean.getUnitName().toUpperCase(),unitMasterBean.getUnitAddress(),
									unitMasterBean.getCompanyId(),unitMasterBean.getUserName()) );
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isInserted = true;	
					}					
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("Already Exsist","ERROR",Messagebox.OK,Messagebox.ERROR );
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		if( isInserted){
			Messagebox.show("Unit Saved successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Unit Saving failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static void updateUnitMasterData(UnitMasterBean unitMasterBean){
		boolean isUpdated = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							UnitMasterSqlQuery.updateUnitMasterQuery, Arrays.asList(unitMasterBean.getUnitName(),unitMasterBean.getUnitAddress(),
									unitMasterBean.getUserName(),unitMasterBean.getUnitId()) );
					
					System.out.println("UPDATE >>> >> > " + preparedStatement);
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isUpdated = true;
						
					}					
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		if( isUpdated){
			Messagebox.show("Unit updated successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Unit updating failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static void deleteUnitMasterData(UnitMasterBean unitMasterBean){
		boolean isDeleted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							UnitMasterSqlQuery.deleteUnitMasterQuery, Arrays.asList(unitMasterBean.getUserName(),unitMasterBean.getUnitId() ));
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isDeleted = true;	
					}					
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		if( isDeleted){
			Messagebox.show("Unit isDeleted successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Unit deletion failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
}
