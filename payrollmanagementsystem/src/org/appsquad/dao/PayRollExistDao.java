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
	
	/*public static ArrayList<PayrollExistBean> loadEmpSalDetails2(PayrollExistBean pxBean){
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
					bean.setTotalSalary2(resultSet.getDouble("total_salary"));
					bean.setTotalDeduction2(resultSet.getDouble("total_deduction"));
					bean.setEd2(resultSet.getDouble("ed"));
					bean.setEdAmt(resultSet.getDouble("ed_amt"));
					bean.setHoliDayAmount(resultSet.getDouble("holiday_amount"));
					bean.setCompanyName2(resultSet.getString("company_name"));
					bean.setUnitName2(resultSet.getString("unit_name"));
					bean.setUnitDes2(resultSet.getString("designation"));
					bean.setLvYr2(resultSet.getString("leave_yr"));
					bean.setSalMonth2(resultSet.getString("salary_month"));
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
	}*/
	
	public static ArrayList<RunPayRollBean> loadEmpSalDetails2(PayrollExistBean pxBean){
		ArrayList<RunPayRollBean> list = new ArrayList<RunPayRollBean>();
		
		Connection connection = null;
		try {
			PreparedStatement preparedStatement = null;
			connection = DbConnection.createConnection();
			try {
				preparedStatement = Util1.createQuery(connection, RunPayRollSql.empSalStoreSelectQuery, Arrays.asList(pxBean.getCompanyId2(), 
						pxBean.getUnitId2(), pxBean.getUnitDesignationId2(), pxBean.getSalMonth2(), pxBean.getLvYr2()));
				
				
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					RunPayRollBean bean = new RunPayRollBean();
					
					bean.setEmpId(resultSet.getInt("employee_id"));
					bean.setEmpCode(resultSet.getString("employee_code"));
					bean.setEmpDesignation(resultSet.getString("emp_designation"));
					bean.setEmpName(resultSet.getString("emp_name"));
					bean.setPresentDay(resultSet.getFloat("presenet_days"));
					bean.setNetSalary(resultSet.getDouble("net_salary"));
					bean.setWages(resultSet.getDouble("wages"));
					bean.setTotalSalary(resultSet.getDouble("total_salary"));
					bean.setTotalDeduction(resultSet.getDouble("total_deduction"));
					bean.setOtHoursF(resultSet.getDouble("ed"));
					bean.setOtSalary(resultSet.getDouble("ed_amt"));
					bean.setHoliDayAmount(resultSet.getDouble("holiday_amount"));
					bean.setComapnyName(resultSet.getString("company_name"));
					bean.setUnitName(resultSet.getString("unit_name"));
					bean.setUnitDesignation(resultSet.getString("designation"));
					bean.setYear(resultSet.getString("leave_yr"));
					bean.setMonthName(resultSet.getString("salary_month"));
					bean.setComponentAmountBeanList(loadCompoAmtDtls(connection, bean.getEmpId(),pxBean.getSalMonth2(), pxBean.getLvYr2()));
					
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
	
	public static ArrayList<EmployeeSalaryComponentAmountBean> loadCompoAmtDtls(Connection connection, int empId, String month, String year){
		
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
	
	
	
	
	
	
}
