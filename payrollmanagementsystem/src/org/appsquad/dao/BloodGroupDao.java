package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	
	public static boolean insertBloodGroupData(BloodGroupBean bloodGroupBean,String userName){
		boolean isInserted = false;
		try {
			sql:{
				Connection connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							SqlQuery.insertBloodGroupQuery, Arrays.asList(bloodGroupBean.getBloodGroupName().toUpperCase(),userName) );
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isInserted = true;	
					}					
				} catch (Exception e) {
					e.printStackTrace();
					connection.rollback();
				}finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}if(connection != null){
						connection.close();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isInserted;	
	}
	
	public static boolean updateBloodGroupData(BloodGroupBean bloodGroupBean,String userName){
		Connection connection = null;
		boolean isUpdated = false;
		try {			
			sql:{
			connection = DbConnection.createConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.updateBloodGroupQuery, 
						Arrays.asList(bloodGroupBean.getBloodGroupName().toUpperCase(),userName , bloodGroupBean.getBloodGroupId() ));

				int i = preparedStatement.executeUpdate();
				connection.commit();
				if(i>0){
					isUpdated = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
			}finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}if(connection != null){
					connection.setAutoCommit(true);
					connection.close();
				}
			}	
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	
}
