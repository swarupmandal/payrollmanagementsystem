package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.print.attribute.standard.Sides;

import org.appsquad.bean.EmployeePaymentDetailsBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
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
								bean.setComponentAmountBeanList(loadComponentAmountDetails(bean.getEmpId(), bean));
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
	
	
	public static ArrayList<EmployeeSalaryComponentAmountBean> loadComponentAmountDetails(int empid, RunPayRollBean salBean){
		
		ArrayList<EmployeeSalaryComponentAmountBean> list = new ArrayList<EmployeeSalaryComponentAmountBean>();
		if(list.size()>0){
			list.clear();
		}
		double earning = 0;
		double deductions = 0;
		double netSal = 0;
		
		Connection connection = DbConnection.createConnection();
			try {
				
				SQL:{
				      PreparedStatement preparedStatement = null;
				      preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadEmpcomponentSalaryDetails, Arrays.asList(empid));
				      System.out.println(">>> >> > " + preparedStatement );
				      ResultSet resultSet = preparedStatement.executeQuery();
					  while (resultSet.next()) {
						  EmployeeSalaryComponentAmountBean bean = new EmployeeSalaryComponentAmountBean();
						  
						  	bean.setComponentName(resultSet.getString("component_name"));
							bean.setComponentAmount(resultSet.getDouble("component_amount"));
							bean.setComponentType(resultSet.getString("component_type"));
							bean.setComponentTypeId(resultSet.getInt("component_type_id"));
							
							if(bean.getComponentType().equalsIgnoreCase("EARNING")){
								earning = earning + bean.getComponentAmount(); 
							}
							if(bean.getComponentType().equalsIgnoreCase("DEDUCTION")){
								deductions = deductions+bean.getComponentAmount();
							}
							
							list.add(bean);
						  
					}
					
					netSal = earning - deductions;  
					salBean.setTotalSalary(earning);
					salBean.setTotalDeduction(deductions);
					salBean.setNetSalary(netSal);
				    
					   
					}
			} catch (SQLException e) {
				
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
			
		
		return list;
	}
	
	
	public static void loadEmpSalarayDetails(ArrayList<EmployeePaymentDetailsBean> beanList, int empId){
				
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
						
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadEmpSalaryDetails, Arrays.asList(empId));
							System.out.println("PREPared statemt " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								
								EmployeePaymentDetailsBean bean = new EmployeePaymentDetailsBean();
								
								bean.setComponentName(resultSet.getString("component_name"));
								bean.setComponentAmount(resultSet.getDouble("component_amount"));
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
