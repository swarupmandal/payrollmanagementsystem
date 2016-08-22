package org.appsquad.dao;

import java.beans.beancontext.BeanContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.print.attribute.standard.Sides;

import org.apache.log4j.Logger;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.EmployeePaymentDetailsBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.bean.SheetBean;
import org.appsquad.database.DbConnection;
import org.appsquad.research.DayCalculate;
import org.appsquad.service.RunPayRollService;
import org.appsquad.sql.RunPayRollSql;
import org.zkoss.zul.Messagebox;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import utility.CheckSunday;
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
	
	
	public static ArrayList<SheetBean> loadSheetTypeList(){
		ArrayList<SheetBean> sheetTypeList = new ArrayList<SheetBean>();
		if(sheetTypeList.size()>0){
			sheetTypeList.clear();
			}
			try {
				Connection connection = DbConnection.createConnection();
				sql_connection:{
					
					try {
						
						sql_block:{
						
							PreparedStatement preparedStatement = null;
							try {
							
								preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadSheetTypeQuery, null);
								ResultSet resultSet = preparedStatement.executeQuery();
								
								while (resultSet.next()) {
									SheetBean bean = new SheetBean();
									bean.setSheetType(resultSet.getString("sheet_type"));
									bean.setSheetTypeId(resultSet.getInt("salary_sheet_type_id"));
									sheetTypeList.add(bean);
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
		 return sheetTypeList;
		}
	
	
	public static ArrayList<ComponentMasterBean> loadComponentList(ComponentMasterBean componentMasterBean){
		ArrayList<ComponentMasterBean> componentList = new ArrayList<ComponentMasterBean>();
		if(componentList.size()>0){
			componentList.clear();
			}
			try {
				Connection connection = DbConnection.createConnection();
				sql_connection:{
					try {
						
						sql_block:{
						
							PreparedStatement preparedStatement = null;
							try {
							
								preparedStatement = Util1.createQuery(connection, RunPayRollSql.loadComponentQuery, 
										Arrays.asList(componentMasterBean.getCompanyId(), componentMasterBean.getUnitId() ));
								ResultSet resultSet = preparedStatement.executeQuery();
								
								while (resultSet.next()) {
									ComponentMasterBean bean = new ComponentMasterBean();
									bean.setComponentName(resultSet.getString("component_name"));
									bean.setCheckVal(true);
									bean.setComponentId(resultSet.getInt("component_id"));
									componentList.add(bean);
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
		 return componentList;
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
							//System.out.println("LOAD EMP DE " + preparedStatement);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								i=i+1;
								
								RunPayRollBean bean = new RunPayRollBean();
								
								bean.setEmpcount(i);
								bean.setEmpId(resultSet.getInt("employee_id"));
								bean.setEmpDesignationId(resultSet.getInt("emp_designation_id"));
								bean.setComponentAmountBeanList(loadComponentAmountDetails(connection,bean.getEmpId(), bean));
								bean.setEmpCode(resultSet.getString("employee_code"));
								bean.setEmpName(resultSet.getString("employee_name"));
								bean.setEmpPf(resultSet.getString("pf_number"));
								bean.setEmpUan(resultSet.getString("uan_number"));
								bean.setEmpEsi(resultSet.getString("esi"));
								bean.setEmpDesignation(resultSet.getString("emp_designation"));
								bean.setWages(getEmpWages(bean.getEmpId(), connection));
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
	
	
	public static ArrayList<EmployeeSalaryComponentAmountBean> loadComponentAmountDetails(Connection connection ,int empid, RunPayRollBean salBean){
		
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
			}finally{/*
				if(connection != null){
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			*/}
			
		
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
							//System.out.println("PREPared statemt sat day " +preparedStatement);
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
	
	
	public static int getBaseDays(int monthId, int unitId, int year, int sunSelId){
		int baseDays = 0,baseDayType=0;
		try {
			SQL:{
					Connection connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = Util1.createQuery(connection, RunPayRollSql.getUnitDayTypeQuery,
								Arrays.asList(unitId));
						
						//System.out.println("BASE DUAS CAL " + preparedStatement);
						
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
			e.printStackTrace();
		}
		
		if(baseDayType == 2){
			// calculate month day and minus 4 in baseDays
			
			//if(sunSelId == 2)
			if((unitId == 39 || unitId == 55 || unitId == 54) || (sunSelId == 2)){ // this line can be replaced by --> if(sunSelId == 2){
				//System.out.println("--Special base for religh court---y:"+year+"m:"+monthId);
				int sunday = CheckSunday.countWeekendDays(year, monthId);
				if(sunday==5){
					baseDays = 26;
				}else{
					baseDays = DayCalculate.getDaysOfMonth(year, monthId, 1);
					baseDays = baseDays-4;
				}
			}else{
				//System.out.println("--Special unit---");
				baseDays = DayCalculate.getDaysOfMonth(year, monthId, 1);
				baseDays = baseDays-4;
			}
			
		}else if(baseDayType == 3) {
			
			baseDays = 26;
		}else{
			
			baseDays = DayCalculate.getDaysOfMonth(year, monthId, 1);
		}
		
		return baseDays;
	}
	
	public static double getEmpWages(int empId,Connection connection ){
		double wages = 0;
		//Connection connection = null;
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
			/*if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
		
		return wages;
	}
	
	
	public static void saveSalSheet(ArrayList<RunPayRollBean> runPayRollBeanList, RunPayRollBean pdfBean){
		try {
			
			Connection connection = DbConnection.createConnection();
			connection.setAutoCommit(false);
			
			try {
				PreparedStatement preparedStatement = null;
				//PreparedStatement preparedStatement = Util1.createQuery(connection, RunPayRollSql.empInsertSalStore, null);
				for(RunPayRollBean bean : runPayRollBeanList){
				    preparedStatement = Util1.createQuery(connection, RunPayRollSql.empInsertSalStore, null);
					preparedStatement.setInt(1, bean.getEmpId());
					preparedStatement.setString(2, bean.getEmpCode());
					preparedStatement.setInt(3, bean.getSelectedCompanyId());
					preparedStatement.setInt(4, bean.getSelectedUnitId());
					preparedStatement.setInt(5, bean.getSelectedUnitDesignationId());
					preparedStatement.setInt(6, bean.getEmpDesignationId());
					preparedStatement.setFloat(7, bean.getPresentDay());
					preparedStatement.setInt(8, bean.getBaseDays());
					preparedStatement.setDouble(9, bean.getOtHoursF());
					preparedStatement.setString(10, bean.getEmpPf());
					preparedStatement.setString(11, bean.getEmpEsi());
					preparedStatement.setString(12, bean.getEmpUan());
					preparedStatement.setString(13, bean.getCurrentDate());
					preparedStatement.setInt(14, bean.getSelectedMonthId());
					preparedStatement.setString(15, bean.getMonthName());
					preparedStatement.setString(16, bean.getYear());
					preparedStatement.setDouble(17, bean.getWages());
					preparedStatement.setDouble(18, bean.getHoliDayAmount());
					preparedStatement.setDouble(19, bean.getOtSalary());
					preparedStatement.setDouble(20, bean.getTotalSalary());
					preparedStatement.setDouble(21, bean.getTotalDeduction());
					preparedStatement.setDouble(22, bean.getNetSalary());
					preparedStatement.setString(23, bean.getUserName());
					preparedStatement.setString(24, bean.getUserName());
					preparedStatement.setString(25, bean.getEmpName());
					int count = preparedStatement.executeUpdate();
					connection.commit();
					if(count>0){
						preparedStatement = Util1.createQuery(connection, RunPayRollSql.empInsertSalDetailsStore, null);
						bean.getComponentAmountBeanList().addAll(bean.getEarningCompList());
						bean.getComponentAmountBeanList().addAll(bean.getDeductionCompList());
						
						//System.out.println("comp list size:: "+bean.getComponentAmountBeanList().size());
						for(EmployeeSalaryComponentAmountBean salBean : bean.getComponentAmountBeanList()){
							preparedStatement.setInt(1, salBean.getComponentId());
							preparedStatement.setString(2, salBean.getComponentName());
							preparedStatement.setInt(3, salBean.getComponentTypeId());
							preparedStatement.setInt(4, bean.getEmpId());
							preparedStatement.setString(5, bean.getEmpCode());
							preparedStatement.setString(6, bean.getMonthName());
							preparedStatement.setString(7, bean.getYear());
							preparedStatement.setDouble(8, salBean.getComponentAmount());
							preparedStatement.addBatch();
						}
						int[] countArray = preparedStatement.executeBatch();
						connection.commit();
						boolean insert = false;
						for(Integer c : countArray){
							insert = true;
						}
						if(insert){
							//System.out.println("Components amount saved!!");
							Messagebox.show("Saved Successfully " ,"Information", Messagebox.OK, Messagebox.INFORMATION);
						}
					}
				}
				
			} catch(Exception e){
				if(e.getMessage().contains("duplicate")){
					System.out.println("Update data!");
					updateSalSheet(runPayRollBeanList);
				}
			}
			finally{
				if(connection!=null){
					connection.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void updateSalSheet(ArrayList<RunPayRollBean> runPayRollBeanList){
		try {
			
			Connection connection = DbConnection.createConnection();
			connection.setAutoCommit(false);
			
			try {
				PreparedStatement preparedStatement = null;
				for(RunPayRollBean bean : runPayRollBeanList){
				    preparedStatement = Util1.createQuery(connection, RunPayRollSql.empUpdateSalStore, null);
				    preparedStatement.setFloat(1, bean.getPresentDay());
				    preparedStatement.setInt(2, bean.getBaseDays());
					preparedStatement.setDouble(3, bean.getOtHoursF());
					preparedStatement.setString(4, bean.getEmpPf());
					preparedStatement.setString(5, bean.getEmpEsi());
					preparedStatement.setString(6, bean.getEmpUan());
					preparedStatement.setString(7, bean.getCurrentDate());
					
					preparedStatement.setDouble(8, bean.getWages());
					preparedStatement.setDouble(9, bean.getHoliDayAmount());
					preparedStatement.setDouble(10, bean.getOtSalary());
					preparedStatement.setDouble(11, bean.getTotalSalary());
					preparedStatement.setDouble(12, bean.getTotalDeduction());
					preparedStatement.setDouble(13, bean.getNetSalary());
					
					preparedStatement.setString(14, bean.getUserName());
					preparedStatement.setString(15, bean.getEmpName());
					
				    preparedStatement.setInt(16, bean.getEmpId());
					preparedStatement.setString(17, bean.getEmpCode());
					preparedStatement.setString(18, bean.getMonthName());
					preparedStatement.setString(19, bean.getYear());
					
					int count = preparedStatement.executeUpdate();
					connection.commit();
					if(count>0){
						
						preparedStatement = Util1.createQuery(connection, RunPayRollSql.empUpdateSalDetailsStore, null);
						bean.getComponentAmountBeanList().addAll(bean.getEarningCompList());
						bean.getComponentAmountBeanList().addAll(bean.getDeductionCompList());
						
						//System.out.println("comp list size:: "+bean.getComponentAmountBeanList().size());
						for(EmployeeSalaryComponentAmountBean salBean : bean.getComponentAmountBeanList()){
							preparedStatement.setDouble(1, salBean.getComponentAmount());
							preparedStatement.setInt(2, bean.getEmpId());
							preparedStatement.setString(3, bean.getEmpCode());
							preparedStatement.setString(4, bean.getMonthName());
							preparedStatement.setString(5, bean.getYear());
							preparedStatement.setInt(6, salBean.getComponentId());
							preparedStatement.addBatch();
						}
						int[] countArray = preparedStatement.executeBatch();
						connection.commit();
						boolean insert = false;
						for(Integer c : countArray){
							insert = true;
						}
						if(insert){
							//System.out.println("Components amount updated!!");
							Messagebox.show("Updated Successfully " ,"Information", Messagebox.OK, Messagebox.INFORMATION);
						}
					}
				}
				
			} catch(Exception e){
				e.printStackTrace();
			}
			finally{
				if(connection!=null){
					connection.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int fetchWagesType(int coId, int unId){
		int wagesTypeId = 0;
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, RunPayRollSql.wagesTypeSelQry, Arrays.asList(unId, coId));
				
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					wagesTypeId = resultSet.getInt("wages_type_id");
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
			}
		} catch (Exception e) {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return wagesTypeId;
	}
	
}
