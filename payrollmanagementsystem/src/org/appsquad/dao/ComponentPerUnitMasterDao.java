package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.ComponentMasterSql;
import org.appsquad.sql.ComponentPerUnitMasterSql;
import org.appsquad.sql.EmployeeMasterSql;
import org.zkoss.zul.Messagebox;

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
					Messagebox.show(e.getMessage());
					
					
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

	public static final ArrayList<ComponentPerUnitMasterBean> onLoadData(){
		ArrayList<ComponentPerUnitMasterBean> list = new ArrayList<ComponentPerUnitMasterBean>();
		if(list.size()>0){
			list.clear();
		}
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				int count = 0;
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.loadComponentBeanListQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								count = count+1;
								
								ComponentPerUnitMasterBean bean = new ComponentPerUnitMasterBean();
								bean.setCount(count);
								bean.setComponet(resultSet.getString("component_name"));
								bean.setComponentId(resultSet.getInt("component_id"));
								bean.setComponentType(resultSet.getString("component_type"));
								bean.setComponentTypeId(resultSet.getInt("component_type_id"));
								bean.setCheckVal(false);
								
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
	
	public static void insertComponentPerUnit(ArrayList<ComponentPerUnitMasterBean> list, Integer companyId, Integer unitId, String userName, int designationId){
		boolean falg = false;
		int c = 0;
		
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
					PreparedStatement preparedStatement = null;
					
					try {
						
						for(ComponentPerUnitMasterBean bean : list){
						
						if(bean.isCheckVal()==true){
						System.out.println("INSIDE CHECK---------------------------------- >>> >> > ");	
						preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.insertComponentPerUnitQuery, Arrays.asList(bean.getComponentId(), bean.getComponet(),bean.getComponentTypeId(),companyId,unitId,userName,userName, designationId));
						System.out.println("Batch Query >>> >> > " + preparedStatement);
						//preparedStatement.addBatch(); 
						c = preparedStatement.executeUpdate();
						
						}
						
					}
					
					int count[] = preparedStatement.executeBatch();
					int insertCount = 0;
					for(int i : count){
						insertCount +=i;
					}
					System.out.println("INSERT COUNT >>> >> > " + insertCount);
					if(c>0){
						Messagebox.show("Saved successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
					}else {
						Messagebox.show("Data Not Saved", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					
					
					} finally{
						if(preparedStatement != null){
							preparedStatement.close();
						}
					}
				
			    }
			} catch (Exception e) {
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
