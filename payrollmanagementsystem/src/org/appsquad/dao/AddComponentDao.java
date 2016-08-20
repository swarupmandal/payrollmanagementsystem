package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.AddComponentSql;
import org.appsquad.sql.ComponentPerUnitMasterSql;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class AddComponentDao {

	public static final ArrayList<ComponentPerUnitMasterBean> onLoadData(int compId, int unId, int undesId){
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
						
							preparedStatement = Util1.createQuery(connection, AddComponentSql.newComponentAddQry, Arrays.asList(compId, unId, undesId));
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
	
	public static ArrayList<Integer> loadEmpIdList(int compId, int unId, int undesId){
		ArrayList<Integer> list = new ArrayList<Integer>();
		if(list.size()>0){
			list.clear();
		}
		
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, AddComponentSql.empIdLoadQuery, Arrays.asList(compId, unId, undesId));
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					list.add(resultSet.getInt("employee_id"));
				}
				
			} finally {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			}
		} catch (Exception e) {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public static void insertNewComponent(ArrayList<ComponentPerUnitMasterBean> list, ComponentPerUnitMasterBean bean , ArrayList<Integer> empIdList){
		
	   ArrayList<ComponentPerUnitMasterBean> finalList = new ArrayList<ComponentPerUnitMasterBean>();
		 int i =0, j =0 ;
		 Connection connection = DbConnection.createConnection();
		 try {
		  connection.setAutoCommit(false);
		  	
		  sql_component:{
			PreparedStatement preparedStatement = null;
			try {
				for(ComponentPerUnitMasterBean bean1 : list){
				if(bean1.isCheckVal()){									
				finalList.add(bean1);
				preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.insertComponentPerUnitQuery, 
						Arrays.asList(bean1.getComponentId(), bean1.getComponet(),bean1.getComponentTypeId(),
								bean.getCompanyId(),bean.getUnitId(),bean.getUserName(),bean.getUserName(), 
								bean.getUnitDesignationId(), bean1.getDesCompoAmount()));
				
				i = preparedStatement.executeUpdate();
				}
			}
			} finally {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			}
		  }
		 
		  sql2:{
			  PreparedStatement preparedStatement = null;
			  try {
				  for(Integer id : empIdList){
					  
					  for(ComponentPerUnitMasterBean bn : finalList){
						  preparedStatement = Util1.createQuery(connection, AddComponentSql.insertEmpCompoQuery, Arrays.asList(id, bn.getComponentId(), bn.getComponet(),bn.getComponentTypeId(),
								  																bean.getCompanyId(), bean.getUnitId(), bn.getDesCompoAmount()));
						  
						  j = preparedStatement.executeUpdate();
						  
					  }
				  }
			} finally  {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			}
			  
		  }
		
	connection.commit();
	if(i>0){
		Messagebox.show("Saved SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
	}
	
	} catch (Exception e) {
		if(e.getMessage().contains("duplicate")){
		Messagebox.show("Already Exists","ERROR",Messagebox.OK,Messagebox.ERROR);
		try {
			connection.rollback();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		}
		e.printStackTrace();
		
	}finally{
		if(connection != null){
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				
				
				e.printStackTrace();
			}
		}
	}
	
}

	
	
	
}
