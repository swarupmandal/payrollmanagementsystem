package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.ComponentSql;
import org.appsquad.sql.RunPayRollSql;

import utility.Util1;

public class ComponentRemoveDao {

	public static void removeComponent(int unitId, String components){
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						String sql ="UPDATE pms_employee_salary_components "
									+" SET is_delete='Y' WHERE unit_id = ? AND component_name in"+components;
						try {
							preparedStatement = connection.prepareStatement(sql);
							preparedStatement.setInt(1, unitId);
							System.out.println(preparedStatement);
							int count = preparedStatement.executeUpdate();
							if(count>0){
								System.out.println("Components removed!");
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
	
	public static void addComponent(int unitId){
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						String sql ="UPDATE pms_employee_salary_components "
									+" SET is_delete='N' WHERE unit_id = ?";
						try {
							preparedStatement = connection.prepareStatement(sql);
							preparedStatement.setInt(1, unitId);
							System.out.println(preparedStatement);
							int count = preparedStatement.executeUpdate();
							if(count>0){
								System.out.println("Components Added!");
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
