package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.PayrollExistBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.RunPayRollSql;

import utility.Util1;

public class PayRollExistDao {

	/**
	 * load emp Data for existing salary details
	 * 
	 */
	
	public static ArrayList<PayrollExistBean> loadEmpSalDetails2(PayrollExistBean pxBean){
		ArrayList<PayrollExistBean> list = new ArrayList<PayrollExistBean>();
		
		Connection connection = null;
		try {
			PreparedStatement preparedStatement = null;
			connection = DbConnection.createConnection();
			try {
				preparedStatement = Util1.createQuery(connection, RunPayRollSql.empSalStoreSelectQuery, Arrays.asList(pxBean.getCompanyId2(), 
						pxBean.getUnitId2(), pxBean.getUnitDesignationId2(), pxBean.getSalMonth2(), pxBean.getLvYr2()));
				
				
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					PayrollExistBean bean = new PayrollExistBean();
					bean.setEmpId2(resultSet.getInt("employee_id"));
					bean.setEmpCode2(resultSet.getString("employee_code"));
					bean.setEmpDes2(resultSet.getString("emp_designation"));
					bean.setEmpName2(resultSet.getString("emp_name"));
					bean.setPresentDay2(resultSet.getFloat("presenet_days"));
					bean.setNetSalary2(resultSet.getDouble("net_salary"));
					bean.setWages2(resultSet.getDouble("wages"));
					bean.setComponentAmountBeanList(loadCompoAmtDtls(connection, bean.getEmpId2(),pxBean.getSalMonth2(), pxBean.getLvYr2() ,bean));
					
					list.add(bean);
					
				}
				
			} finally {
				if(preparedStatement != null){
					preparedStatement.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
		
	}
	
	public static ArrayList<EmployeeSalaryComponentAmountBean> loadCompoAmtDtls(Connection connection, int empId, String month, String year, PayrollExistBean pexBean){
		
		ArrayList<EmployeeSalaryComponentAmountBean> list = new ArrayList<EmployeeSalaryComponentAmountBean>();
		if(list.size()>0){
			list.clear();
		}
		try {
			PreparedStatement preparedStatement = null;
		try {
			
			ResultSet resultSet = null;
			
			preparedStatement = Util1.createQuery(connection, RunPayRollSql.empSalStoreCompoDetlQuery, 
					Arrays.asList(empId , month, year));
			
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				EmployeeSalaryComponentAmountBean bean = new EmployeeSalaryComponentAmountBean();
				
				bean.setComponentId(resultSet.getInt("component_id"));
				bean.setComponentName(resultSet.getString("component_name"));
				bean.setComponentAmount(resultSet.getDouble("amount"));
				bean.setComponentTypeId(resultSet.getInt("component_type_id"));
				
				list.add(bean);
			}
			
		} finally{
			if(preparedStatement != null){
				preparedStatement.close();
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; 
	}
	
/*public static ArrayList<EmployeeSalaryComponentAmountBean> loadComponentAmountDetails(Connection connection ,int empid, PayrollExistBean salBean){
		
		ArrayList<EmployeeSalaryComponentAmountBean> list = new ArrayList<EmployeeSalaryComponentAmountBean>();
		if(list.size()>0){
			list.clear();
		}
		double earning = 0;
		double deductions = 0;
		double netSal = 0;
		
		//Connection connection = DbConnection.createConnection();
			try {
				
				SQL:{
				      PreparedStatement preparedStatement = null;
				      preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadEmpcomponentSalaryDetails, Arrays.asList(empid));
				      
				      ResultSet resultSet = preparedStatement.executeQuery();
					  while (resultSet.next()) {
						  EmployeeSalaryComponentAmountBean bean = new EmployeeSalaryComponentAmountBean();
						    bean.setComponentId(resultSet.getInt("component_id"));
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
	}*/
	
	
	
	
}
