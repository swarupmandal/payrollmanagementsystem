package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.ComponentMasterSql;
import org.appsquad.sql.ComponentPerUnitMasterSql;
import org.appsquad.sql.EmployeeMasterSql;

import utility.Util1;

public class ComponentPerUnitMasterDao {
	
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
	
	public static final ArrayList<UnitMasterBean> loadUnitList(Integer companyId){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		if(list.size()>0){
			list.clear();
		}
		try {


			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.loadUnitQuery, Arrays.asList(companyId));
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								UnitMasterBean bean = new UnitMasterBean(); 
								bean.setUnitName(resultSet.getString("unit_name"));
								bean.setUnitId(resultSet.getInt("unit_id"));
								list.add(bean);
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
		return list;
		
	}

	
	
}
