package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.DesignationMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.DesignationMasterSql;
import org.appsquad.sql.UnitMasterSqlQuery;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class DesignationMasterDao {
	
	public static void insertDesignationData(DesignationMasterBean bean){
		boolean isInserted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							DesignationMasterSql.saveDesignationQuery, Arrays.asList(bean.getDesignation(), bean.getUserName(), bean.getUserName()) );
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
			Messagebox.show("Saved successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Saving failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	
	public static ArrayList<DesignationMasterBean> onLoad(){
		ArrayList<DesignationMasterBean> beanList = new ArrayList<DesignationMasterBean>();
		if(beanList.size()>0){
			beanList.clear();
		}
		Connection connection =null;
		try {		
			  sql:{	
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
			
				try {
					preparedStatement = Util1.createQuery(connection, DesignationMasterSql.loadDesignationMasterData, null);
					
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						DesignationMasterBean bean = new DesignationMasterBean();
						bean.setDesignation(resultSet.getString("designation").toUpperCase());
						bean.setDesignationId(resultSet.getInt("id"));
						beanList.add(bean);
						
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
		return beanList;
	}
	
	public static void insertEmpDesignationData(DesignationMasterBean bean, String userName){
		boolean isInserted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							DesignationMasterSql.saveEmpDesignationQuery, Arrays.asList(bean.getDesignation(), userName, userName) );
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
			Messagebox.show("Saved successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Saving failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static ArrayList<DesignationMasterBean> onLoadEmpDesignationList(){
		ArrayList<DesignationMasterBean> beanList = new ArrayList<DesignationMasterBean>();
		if(beanList.size()>0){
			beanList.clear();
		}
		Connection connection =null;
		try {		
			  sql:{	
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
			
				try {
					preparedStatement = Util1.createQuery(connection, DesignationMasterSql.loadEmpDesignationMasterData, null);
					
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						DesignationMasterBean bean = new DesignationMasterBean();
						bean.setDesignation(resultSet.getString("emp_designation").toUpperCase());
						bean.setDesignationId(resultSet.getInt("id"));
						beanList.add(bean);
						
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
		return beanList;
	}
	

}
