package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.CompanyMasterSqlQuery;
import org.appsquad.sql.ComponentMasterSql;
import org.appsquad.sql.EmployeeMasterSql;

import utility.Util1;

public class ComponentMasterDao {
	
	public static ArrayList<ComponentMasterBean> loadComponentName(){
		ArrayList<ComponentMasterBean> componetTypeList = new ArrayList<ComponentMasterBean>();
		if(componetTypeList.size()>0){
			componetTypeList.clear();
		}
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, ComponentMasterSql.loadCompoNentTypeQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								ComponentMasterBean bean = new ComponentMasterBean(); 
								bean.setComponentType(resultSet.getString("component_type"));
								bean.setComponentTypeId(resultSet.getInt("component_type_id"));
								componetTypeList.add(bean);
							}
						}finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(connection !=null){
						connection.close();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return componetTypeList;
	
	}
	
	
	public static boolean saveComponentDetails(ComponentMasterBean bean, String userName){	
		int count = 0;
		boolean isInserted = false;
		try {
			Connection connection = DbConnection.createConnection();
			
				try {
					sql:{
					PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, ComponentMasterSql.insertCompontQuery, Arrays.asList(bean.getComponentName().toUpperCase(), bean.getComponentTypeId(),userName, userName));
							
							count = preparedStatement.executeUpdate();
							if(count>0){
								isInserted = true;
							}else {
								isInserted = false;
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
						connection.close();
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInserted;
	
	}
	
	
	public static void addNewComponentColumnInPayrollTable(String columnName){	
		try {
			Connection connection = DbConnection.createConnection();
					sql:{
					PreparedStatement preparedStatement = null;
					String query =  ComponentMasterSql.addColumnQuery.replace("columnname", columnName);
						try {
							preparedStatement = Util1.createQuery(connection,query, null);
							int count = preparedStatement.executeUpdate();
							if(count>0){
								System.out.println("Column "+columnName+" added!");
							}
						  }finally{
						  if(preparedStatement != null){
							  preparedStatement.close();
						  }
						}	
				      }	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void onloadComponentDetails(ArrayList<ComponentMasterBean> beanList){
		
		if(beanList.size()>0){
			beanList.clear();
		}
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, ComponentMasterSql.loadComponentDetailsQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								ComponentMasterBean bean = new ComponentMasterBean();
								
								bean.setComponentName(resultSet.getString("component_name"));
								bean.setComponentType(resultSet.getString("component_type"));
								
								beanList.add(bean);
							}
						}finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(connection !=null){
						connection.close();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
		
	}
	
	

}
