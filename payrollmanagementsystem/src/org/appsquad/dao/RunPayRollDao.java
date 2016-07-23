package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.RunPayRollSql;

import utility.Util1;

public class RunPayRollDao {

	public static void loadMonthList(ArrayList<MonthMasterBean> monthList){


		if(monthList.size()>0){
			monthList.clear();
		}
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadMonthQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								MonthMasterBean bean = new MonthMasterBean();
								bean.setMonthName(resultSet.getString("month"));
								bean.setMonthId(resultSet.getInt("month_id"));
								monthList.add(bean);
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
	
	public static void loadEmpDetails(ArrayList<RunPayRollBean> beanList, int companyId, int unitId){
		
		if(beanList.size()>0){
			beanList.clear();
		}
		int i=0;
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadEmpDetailsQuery, Arrays.asList(companyId,unitId));
							
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								i=i+1;
								
								RunPayRollBean bean = new RunPayRollBean();
								
								bean.setEmpcount(i);
								bean.setEmpId(resultSet.getInt("employee_id"));
								bean.setEmpCode(resultSet.getString("employee_code"));
								bean.setEmpName(resultSet.getString("employee_name"));
								bean.setEmpPf(resultSet.getString("uan"));
								bean.setEmpEsi(resultSet.getString("esi"));
								bean.setEmpDesignation(resultSet.getString("designation"));
								
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
