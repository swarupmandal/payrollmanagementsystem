package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import org.appsquad.database.DbConnection;
import org.appsquad.sql.SqlQuery;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class ChangePasswordDao {

	public static void changePassword(String userId, String password){
		boolean updated= false;
		try {		
			SQL:{
					Connection connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					try {
						preparedStatement = Util1.createQuery(connection, SqlQuery.updatePasswordQuery, 
								Arrays.asList(password,userId));
						int count = preparedStatement.executeUpdate();
						if(count >0 ){
							updated = true;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						if(connection!=null){
							connection.close();
						}
					}	
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(updated){
			Messagebox.show("Password changed successfully!","Successful information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			
		}
	}
}
