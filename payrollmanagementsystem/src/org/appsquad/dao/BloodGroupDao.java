package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.database.DbConnection;
import org.appsquad.service.BloodGroupService;
import org.appsquad.sql.SqlQuery;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class BloodGroupDao {

	public static void onLoad(ArrayList<BloodGroupBean> bloodGroupBeanList ){
		if(bloodGroupBeanList.size()>0){
			bloodGroupBeanList.clear();
		}
		Connection connection =null;
		try {		
			  sql:{	
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
			
				try {
					preparedStatement = Util1.createQuery(connection, SqlQuery.onLoadBloodGroupQuery, null);
					
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						int bloodGroupId = resultSet.getInt("bloodgroup_id");
						String bloodGroupName = resultSet.getString("bloodgroup_name");
						bloodGroupBeanList.add(new BloodGroupBean(bloodGroupName, bloodGroupId));
					}  
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}if(connection != null){
						connection.close();
					}
				}
			}	
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public static void insertBloodGroupData(BloodGroupBean bloodGroupBean){
		boolean isInserted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							SqlQuery.insertBloodGroupQuery, Arrays.asList(bloodGroupBean.getBloodGroupName().toUpperCase(),
									bloodGroupBean.getUserName()) );
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isInserted = true;	
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
			Messagebox.show("Blood Group Saved successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Blood Group Saving failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static void updateBloodGroupData(BloodGroupBean bloodGroupBean){
		Connection connection = null;
		boolean isUpdated = false;
		try {			
			sql:{
			connection = DbConnection.createConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.updateBloodGroupQuery, 
						Arrays.asList(bloodGroupBean.getBloodGroupName().toUpperCase(),bloodGroupBean.getUserName() , 
								bloodGroupBean.getBloodGroupId() ));

				int i = preparedStatement.executeUpdate();
				connection.commit();
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
			Messagebox.show("Blood group updated successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Blood group updating failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static void deleteBloodGroupData(BloodGroupBean bloodGroupBean){
		Connection connection = null;
		boolean isdeleted = false;
		try {			
			sql:{
			connection = DbConnection.createConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.deleteBloodGroupQuery, 
						Arrays.asList(bloodGroupBean.getBloodGroupId() ));

				int i = preparedStatement.executeUpdate();
				connection.commit();
				if(i>0){
					isdeleted = true;
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
		if( isdeleted){
			Messagebox.show("Blood group deleted successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Blood group deleting failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	
}
