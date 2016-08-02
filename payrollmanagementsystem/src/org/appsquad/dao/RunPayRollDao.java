package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.print.attribute.standard.Sides;

import org.apache.log4j.Logger;
import org.appsquad.bean.EmployeePaymentDetailsBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.database.DbConnection;
import org.appsquad.service.RunPayRollService;
import org.appsquad.sql.RunPayRollSql;
import org.zkoss.zul.Messagebox;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

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
	
	
	
	public static void loadEmpDetails(ArrayList<RunPayRollBean> beanList, int companyId, int unitId, int workingDay, int unitDesignationId){
		
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
						
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadEmpDetailsQuery2, Arrays.asList(companyId,unitId,unitDesignationId));
							System.out.println("LOAD EMP DE " + preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								i=i+1;
								
								RunPayRollBean bean = new RunPayRollBean();
								
								bean.setEmpcount(i);
								bean.setEmpId(resultSet.getInt("employee_id"));
								bean.setComponentAmountBeanList(loadComponentAmountDetails(bean.getEmpId(), bean));
								bean.setEmpCode(resultSet.getString("employee_code"));
								bean.setEmpName(resultSet.getString("employee_name"));
								bean.setEmpPf(resultSet.getString("pf_number"));
								bean.setEmpUan(resultSet.getString("uan_number"));
								bean.setEmpEsi(resultSet.getString("esi"));
								bean.setEmpDesignation(resultSet.getString("emp_designation"));
								bean.setWages(getEmpWages(bean.getEmpId()));
								bean.setTotalNumberOfDayseveryMonth(workingDay);
								
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
							//System.out.println("PREPared statemt " +preparedStatement);
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
	
	public static Integer sunDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer sundayCount =0;
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadSundayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId));
							System.out.println("PREPared statemt 1" +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								sundayCount = resultSet.getInt("total_no_of_days");
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
		
		return sundayCount;
		
	}
	
	public static Integer satDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer satdayCount =0;
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadSaturdayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId));
							System.out.println("PREPared statemt sat day " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								satdayCount = resultSet.getInt("total_no_of_days");
								
							}
							System.out.println("SAT COUNT DAO " + satdayCount);
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
		
		return satdayCount;
		
	}
	
	public static Integer monDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer mondayCount =0;
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadMondayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId));
							System.out.println("PREPared statemt mon day " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								mondayCount = resultSet.getInt("total_no_of_days");
							}
							System.out.println("MON COUNT DAO " + mondayCount);
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
		return mondayCount;
	}
	
	public static Integer tuesDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer tuesdayCount =0;
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadTuesdayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId));
							System.out.println("PREPared statemt tue day " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								tuesdayCount = resultSet.getInt("total_no_of_days");
							}
							System.out.println("TUE COUNT DAO " + tuesdayCount);
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
		return tuesdayCount;
	}
	
	public static Integer wednesDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer wesnesdayCount =0;
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadWendsdayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId));
							System.out.println("PREPared statemt tue day " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								wesnesdayCount = resultSet.getInt("total_no_of_days");
							}
							System.out.println("WED COUNT DAO " + wesnesdayCount);
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
		return wesnesdayCount;
	}
	
	public static Integer thursDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer thursdayCount =0;
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadThursdayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId));
							System.out.println("PREPared statemt tue day " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								thursdayCount = resultSet.getInt("total_no_of_days");
							}
							System.out.println("THURS COUNT DAO " + thursdayCount);
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
		return thursdayCount;
	}
	
	public static Integer friDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer fridayCount =0;
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadThursdayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId));
							System.out.println("PREPared statemt tue day " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								fridayCount = resultSet.getInt("total_no_of_days");
							}
							System.out.println("FRI COUNT DAO " + fridayCount);
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
		return fridayCount;
	}
	
	public static Integer generalHoliDayCount(int companyId, int unitId, RunPayRollBean bean, int currentMonthId){

		Integer generalHolidayCount =0;
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadGeneralHoliDayCount, Arrays.asList(bean.getLeaveYrId(),companyId, unitId,currentMonthId));
							System.out.println("PREPared statemt General Holy day " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								generalHolidayCount = resultSet.getInt("holiday_count");
							}
							System.out.println("General Holiday COUNT DAO " + generalHolidayCount);
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
		return generalHolidayCount;	
		
	}
	
	public static Double loadHoursPerDay(int companyId, int unitId, RunPayRollBean bean){

		Double loadHoursPerDay = 0.0;
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadHoursPerDay, Arrays.asList(companyId, unitId, bean.getLeaveYrId()));
							System.out.println("PREPared statemt Hours " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								loadHoursPerDay = (double) resultSet.getInt("hour_per_day");
							}
							System.out.println("General HOur COUNT DAO " + loadHoursPerDay);
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
		return loadHoursPerDay;	
		
	}
	
	
	public static ArrayList<String> generalHoliDayDateList(int companyId, int unitId, RunPayRollBean bean, int currentMonthId){

		ArrayList<String> holidayDateList = new ArrayList<String>();
		
		
		if(holidayDateList.size()>0){
			holidayDateList.clear();
		}
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				try {
					sql_block:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadGeneralHoliDayDates, Arrays.asList(bean.getLeaveYrId(),companyId, unitId,currentMonthId));
							System.out.println("PREPared statemt General Holy day Dates " +preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								holidayDateList.add(resultSet.getString("holiday_date"));
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
		return holidayDateList;	
		
	}
	
	
	public static int getBaseDays(int monthId, int unitId, int year){
		int baseDays = 0,baseDayType=0;
		try {
			SQL:{
					Connection connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = Util1.createQuery(connection, RunPayRollSql.getUnitDayTypeQuery,
								Arrays.asList(unitId));
						
						System.out.println("BASE DUAS CAL " + preparedStatement);
						
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							baseDayType = resultSet.getInt("base_days_type_id");
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}if(connection!=null){
							connection.close();
						}
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(baseDayType == 2){
			// calculate month day and minus 4 in baseDays
			baseDays = RunPayRollService.totnoOfDaysInMonth(monthId, year);
			baseDays = baseDays-4;
		}else{
			// calculate month days in baseDayss
			baseDays = RunPayRollService.totnoOfDaysInMonth(monthId, year);
		}
		System.out.println("Calculated Base days: "+baseDays);
		return baseDays;
	}
	
	public static double getEmpWages(int empId ){
		double wages = 0;
		Connection connection = null;
		try {
			
			PreparedStatement preparedStatement = null;
			
			try {
				connection = DbConnection.createConnection();
				
				preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadWagesQuery, Arrays.asList(empId));
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					wages = resultSet.getDouble("component_amount");
				}
				
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
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
		
		return wages;
	}
	
	
}
