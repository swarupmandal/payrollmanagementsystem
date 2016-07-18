package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.database.DbConnection;
import org.appsquad.service.BloodGroupService;
import org.appsquad.sql.SqlQuery;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class BloodGroupDao {

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
					//	connection.setAutoCommit(true);
						connection.close();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isInserted;	
	}
}
