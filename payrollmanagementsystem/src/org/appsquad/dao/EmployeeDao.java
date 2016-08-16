package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.BankAccountBean;
import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.bean.DesignationBean;
import org.appsquad.bean.EmployeeMasterBean;
import org.appsquad.bean.PaymentModeMasterBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.bean.UnitDesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.ComponentMasterSql;
import org.appsquad.sql.ComponentPerUnitMasterSql;
import org.appsquad.sql.EmployeeMasterSql;
import org.zkoss.zhtml.Pre;
import org.zkoss.zul.Flash;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class EmployeeDao {
	
	public static EmployeeMasterBean getEmployeeInformation(Integer empId){
		EmployeeMasterBean employeeMasterBean = new EmployeeMasterBean();
		try {
			SQL:{
					Connection connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
				    try {
				    	preparedStatement = Util1.createQuery(connection, 
								EmployeeMasterSql.loadEmployeeInfoQuery,
								Arrays.asList(empId));
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							employeeMasterBean.setEmployeeCode(resultSet.getString("employee_code"));
							employeeMasterBean.setEmployeeName(resultSet.getString("employee_name"));
							employeeMasterBean.setEmpPhone(resultSet.getString("employee_phone_number"));
							employeeMasterBean.setEmpEmail(resultSet.getString("employee_email"));
							employeeMasterBean.setGender(resultSet.getString("gender"));
							employeeMasterBean.setEmpDob(resultSet.getDate("dob"));
							
							employeeMasterBean.setEmpAddress(resultSet.getString("employee_address"));
							employeeMasterBean.setEmpCity(resultSet.getString("employee_city"));
							employeeMasterBean.setEmpState(resultSet.getString("state_name"));
							employeeMasterBean.setEmpStateId(resultSet.getInt("emp_state_id"));
							employeeMasterBean.setPinCode(resultSet.getString("employee_pincode"));
							employeeMasterBean.setEmpBloodGroupId(resultSet.getInt("emp_blood_group_id"));
							employeeMasterBean.setEmpBloodGroup(resultSet.getString("bloodgroup_name"));
							employeeMasterBean.setEmpPan(resultSet.getString("emp_pan"));
							employeeMasterBean.setEmpMaritalStatus(resultSet.getString("emp_marital_status"));
							employeeMasterBean.setEmpDoj(resultSet.getDate("emp_joining_date"));
							employeeMasterBean.setEmpDesignation(resultSet.getString("emp_designation"));
							employeeMasterBean.setEmpLocation(resultSet.getString("emp_location"));
							employeeMasterBean.setPaymentMode(resultSet.getString("payment_mode"));
							employeeMasterBean.setEmpBankAccount(resultSet.getString("bank_name"));
							employeeMasterBean.setEmpAccountNo(resultSet.getString("emp_bank_account_number"));
							employeeMasterBean.setIfscCode(resultSet.getString("ifsc"));
							employeeMasterBean.setIncrementDate(resultSet.getDate("increment_date"));
							employeeMasterBean.setRegistrationDate(resultSet.getDate("registration_date"));
							employeeMasterBean.setLastWorkingDate(resultSet.getDate("last_working_date"));
							employeeMasterBean.setComponentMasterBeanList(fetchComponentList(empId, connection));
							employeeMasterBean.setUan(resultSet.getString("pf_number"));
							employeeMasterBean.setPfNumber(resultSet.getString("uan_number"));
							employeeMasterBean.setEsi(resultSet.getString("esi"));
						}
					} catch (Exception e) {
						Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
						e.printStackTrace();
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
						if(connection!=null){
							connection.close();
						}
					}
			}	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeMasterBean;
	}
	
	public static void upDateEmployee(EmployeeMasterBean employeeMasterBean){
		boolean masterUpdate=false,personalUpdate=false,officeUpdate=false,pfEsiUpdate=false;
		System.out.println("emp dob - "+employeeMasterBean.getEmpStateId());
		try {
			Connection connection = DbConnection.createConnection();
			SQL:{
				PreparedStatement preparedStatement = null;
				try {
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.updateEmployeeMasterQuery, 
								Arrays.asList(employeeMasterBean.getEmployeeName(),
										employeeMasterBean.getEmpPhone(),employeeMasterBean.getEmpEmail(),
										employeeMasterBean.getGender(),employeeMasterBean.getEmpStateId(),
										employeeMasterBean.getEmpDob(), employeeMasterBean.getUserId(),employeeMasterBean.getEmployeeid()));
						int count = preparedStatement.executeUpdate();
						if(count > 0){
							masterUpdate = true;
							System.out.println("Master data updated!");
						}
						
				} catch (Exception e) {
						// TODO: handle exception
					e.printStackTrace();
					Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
				}finally{
					if(preparedStatement!=null){
						preparedStatement.close();
					}
				}
			}
			
			if(masterUpdate){
				int countrow =0;
				SQL:{
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.getEmployeePersonalDataQuery, 
								Arrays.asList(employeeMasterBean.getEmployeeid() ));
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							countrow = resultSet.getInt(1);	
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
				}
				System.out.println("Getting emp id in personal data :"+countrow);
				if(countrow!=0){//if employee id exists then update
					SQL:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.updateEmployeePersonalQuery, 
									Arrays.asList(employeeMasterBean.getEmpAddress(),employeeMasterBean.getEmpCity(),
											employeeMasterBean.getEmpStateId(),employeeMasterBean.getPinCode(),
											employeeMasterBean.getEmpBloodGroupId(),employeeMasterBean.getEmpPan(),
											employeeMasterBean.getEmpMaritalStatus(),employeeMasterBean.getUserId(),employeeMasterBean.getEmployeeid()
											));
							int count = preparedStatement.executeUpdate();
							if(count > 0){
								personalUpdate = true;
								System.out.println("Personal data updated!");
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}
						}
					}
				}else{
					SQL:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.insertPersoalInformationQuery, 
									Arrays.asList(employeeMasterBean.getEmployeeid(),employeeMasterBean.getEmpAddress(),
											employeeMasterBean.getEmpCity(),employeeMasterBean.getEmpStateId(),
											employeeMasterBean.getPinCode(),employeeMasterBean.getEmpBloodGroupId(),employeeMasterBean.getEmpPan(),
											employeeMasterBean.getEmpMaritalStatus(),employeeMasterBean.getUserId(),employeeMasterBean.getUserId()
											));
							int count = preparedStatement.executeUpdate();
							if(count > 0){
								personalUpdate = true;
								System.out.println("Personal data inserted!");
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}
						}
					}
				}	
			}
			
			if(personalUpdate){
				int countrow =0;
				SQL:{
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.getEmployeeOfficeDataQuery, 
								Arrays.asList(employeeMasterBean.getEmployeeid() ));
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							countrow = resultSet.getInt(1);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
				}
				System.out.println("Official emp id in personal data :"+countrow);
				if(countrow!=0){
					SQL:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.updateEmployeeOfficeQuery, 
									Arrays.asList(employeeMasterBean.getEmpDoj(),employeeMasterBean.getEmpDesignation(),
											employeeMasterBean.getEmpLocation(),employeeMasterBean.getPaymentModeId(),
											employeeMasterBean.getEmpBankId(),employeeMasterBean.getEmpAccountNo(),
											employeeMasterBean.getIfscCode(),employeeMasterBean.getIncrementDate(),
											employeeMasterBean.getRegistrationDate(),employeeMasterBean.getLastWorkingDate(),
											employeeMasterBean.getEmpDesignationId(),
											employeeMasterBean.getUserId(),employeeMasterBean.getEmployeeid()
											));
							int count = preparedStatement.executeUpdate();
							if(count > 0){
								officeUpdate = true;
								System.out.println("Officail data updated!");
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}
						}
					}	
				}else{
					SQL:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.insertEmployeeOfficialDetails, 
									Arrays.asList(employeeMasterBean.getEmployeeid(),employeeMasterBean.getEmpDoj(),
											employeeMasterBean.getEmpDesignationId(),employeeMasterBean.getEmpLocation(),
											employeeMasterBean.getPaymentModeId(),employeeMasterBean.getEmpBankId(),
											employeeMasterBean.getEmpAccountNo(),employeeMasterBean.getIfscCode(),
											employeeMasterBean.getIncrementDate(),employeeMasterBean.getRegistrationDate(),
											employeeMasterBean.getLastWorkingDate(),employeeMasterBean.getUserId(),
											employeeMasterBean.getUserId()
											));
							int count = preparedStatement.executeUpdate();
							if(count > 0){
								officeUpdate = true;
								System.out.println("Officail data inserted!");
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}
						}
					}	
					
				}	
			}
			
			if(officeUpdate){
				int countrow =0;
				SQL:{
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.getEmployeeEsiDataQuery, 
								Arrays.asList(employeeMasterBean.getEmployeeid() ));
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							countrow = resultSet.getInt(1);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
				}
				System.out.println("Getting emp id in pfesi data :"+countrow);
				if(countrow!=0){
					SQL:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.updatePfEsiQuery, 
									Arrays.asList(employeeMasterBean.getUan(), employeeMasterBean.getPfNumber() ,employeeMasterBean.getEsi(),
											employeeMasterBean.getUserId(),employeeMasterBean.getEmployeeid()
											));
							int count = preparedStatement.executeUpdate();
							if(count > 0){
								pfEsiUpdate = true;
								System.out.println("PfEsi data updated!");
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}if(connection!=null){
								connection.close();
							}
						}
					}
				}else{
					SQL:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.empPfEsiInsertQuery, 
									Arrays.asList(employeeMasterBean.getEmployeeid(),employeeMasterBean.getUan(),
											employeeMasterBean.getEsi(),employeeMasterBean.getUserId(),employeeMasterBean.getUserId(), employeeMasterBean.getPfNumber()
											));
							int count = preparedStatement.executeUpdate();
							if(count > 0){
								pfEsiUpdate = true;
								System.out.println("PfEsi data inserted!");
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}if(connection!=null){
								connection.close();
							}
						}
					}	
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
		}
		if(pfEsiUpdate){
			Messagebox.show("Employee data updated successfully!","Successful Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Employee data updation failed!","Failed Information",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	
	public static ArrayList<ComponentMasterBean> fetchComponentList(Integer empId , Connection connection){
		ArrayList<ComponentMasterBean> componentMasterBeanList = new ArrayList<ComponentMasterBean>();
		try {
			SQL:{
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = Util1.createQuery(connection, 
								EmployeeMasterSql.fetchComponentsQuery, 
								Arrays.asList(empId));
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							ComponentMasterBean bean = new ComponentMasterBean();
							bean.setComponentId(resultSet.getInt("component_id"));
							bean.setComponentName(resultSet.getString("component_name"));
							bean.setComponentTypeId(resultSet.getInt("component_type_id"));
							bean.setComponentType(resultSet.getString("component_type"));
							bean.setComponentAmount(resultSet.getDouble("component_amount"));
							componentMasterBeanList.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return componentMasterBeanList;
	}
	
	public static ArrayList<EmployeeMasterBean> loadSavedEmployeeList(){
		ArrayList<EmployeeMasterBean> employeeMasterBeanList = new ArrayList<EmployeeMasterBean>();
		if(employeeMasterBeanList.size()>0){
			employeeMasterBeanList.clear();
		}
		try {
			SQL:{
					Connection connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						
						preparedStatement = Util1.createQuery(connection, 
								EmployeeMasterSql.loadSavedEmployeeQuery, null);
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							EmployeeMasterBean bean = new EmployeeMasterBean();
							bean.setEmployeeid(resultSet.getInt("employee_id"));
							bean.setEmployeeName(resultSet.getString("employee_name"));
							bean.setCompanyName(resultSet.getString("company_name"));
							bean.setCompanyId(resultSet.getInt("company_id"));
							bean.setUnitName(resultSet.getString("unit_name"));
							bean.setUnitId(resultSet.getInt("unit_id"));
							bean.setEmployeeCode(resultSet.getString("employee_code"));
							
							employeeMasterBeanList.add(bean);
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
					}finally{
						if(connection!=null){
							connection.close();
						}
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
		return employeeMasterBeanList;
	}
	
	public static ArrayList<EmployeeMasterBean> searchEmployeeFromList(String employeeCode){
		ArrayList<EmployeeMasterBean> employeeMasterBeanList = new ArrayList<EmployeeMasterBean>();
		if(employeeMasterBeanList.size()>0){
			employeeMasterBeanList.clear();
		}
		try {
			SQL:{
					Connection connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						preparedStatement = connection.prepareStatement(EmployeeMasterSql.searchEmployeeQuery);
						preparedStatement.setString(1, employeeCode+"%");
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							EmployeeMasterBean bean = new EmployeeMasterBean();
							bean.setEmployeeid(resultSet.getInt("employee_id"));
							bean.setCompanyName(resultSet.getString("company_name"));
							bean.setEmployeeName(resultSet.getString("employee_name"));
							bean.setCompanyId(resultSet.getInt("company_id"));
							bean.setUnitName(resultSet.getString("unit_name"));
							bean.setUnitId(resultSet.getInt("unit_id"));
							bean.setEmployeeCode(resultSet.getString("employee_code"));
							
							employeeMasterBeanList.add(bean);
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
					}finally{
						if(connection!=null){
							connection.close();
						}
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
		return employeeMasterBeanList;
	}
	
	public static ArrayList<EmployeeMasterBean> searchEmployeeFromCompanyList(EmployeeMasterBean employeeMasterBean){
		ArrayList<EmployeeMasterBean> employeeMasterBeanList = new ArrayList<EmployeeMasterBean>();
		if(employeeMasterBeanList.size()>0){
			employeeMasterBeanList.clear();
		}
		try {
			SQL:{
					Connection connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
						
						preparedStatement = Util1.createQuery(connection, 
								EmployeeMasterSql.loadSavedEmployeeFromCompanyQuery, 
								Arrays.asList(employeeMasterBean.getCompanyId(),
										employeeMasterBean.getUnitId()));
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							EmployeeMasterBean bean = new EmployeeMasterBean();
							bean.setEmployeeid(resultSet.getInt("employee_id"));
							bean.setCompanyName(resultSet.getString("company_name"));
							bean.setCompanyId(resultSet.getInt("company_id"));
							bean.setUnitName(resultSet.getString("unit_name"));
							bean.setUnitId(resultSet.getInt("unit_id"));
							bean.setEmployeeCode(resultSet.getString("employee_code"));
							
							employeeMasterBeanList.add(bean);
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
					}finally{
						if(connection!=null){
							connection.close();
						}
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
		return employeeMasterBeanList;
	}
	
	
	public static void loadCompanyList(ArrayList<CompanyMasterBean> companyBeanList){

		if(companyBeanList.size()>0){
			companyBeanList.clear();
		}
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadCompanyListQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								CompanyMasterBean bean = new CompanyMasterBean();
								bean.setCompanyName(resultSet.getString("company_name"));
								bean.setCompanyId(resultSet.getInt("company_id"));
								companyBeanList.add(bean);
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
	
	
	public static void loadUnitList(ArrayList<UnitMasterBean> unitBeanList){
		if(unitBeanList.size()>0){
			unitBeanList.clear();
		}

		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadUnitListQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					UnitMasterBean bean = new UnitMasterBean();
					bean.setUnitName(resultSet.getString("unit_name"));
					bean.setUnitId(resultSet.getInt("unit_id"));
					unitBeanList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<UnitMasterBean> loadUnitListWrtCompany(int companyId){
		ArrayList<UnitMasterBean> unitBeanList = new ArrayList<UnitMasterBean>();
		
		if(unitBeanList.size()>0){
			unitBeanList.clear();
		}

		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadUnitListWrtCompanyQuery, 
						Arrays.asList(companyId));
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					UnitMasterBean bean = new UnitMasterBean();
					bean.setUnitName(resultSet.getString("unit_name"));
					bean.setUnitId(resultSet.getInt("unit_id"));
					unitBeanList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unitBeanList;
	}
	
	
	public static void loadStateList(ArrayList<StateMasterBean> stateBeanList){
		if(stateBeanList.size()>0){
			stateBeanList.clear();
		}
		
		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadStateListQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					StateMasterBean bean = new StateMasterBean();
					bean.setStateName(resultSet.getString("state_name"));
					bean.setStateId(resultSet.getInt("id"));
					stateBeanList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
	}
	
	public static void onloadLoadBloodGroupList(ArrayList<BloodGroupBean> beanList){
		if(beanList.size()>0){
			beanList.clear();
		}

		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadBllodGroupListQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					BloodGroupBean bean = new BloodGroupBean();
					bean.setBloodGroupName(resultSet.getString("bloodgroup_name"));
					bean.setBloodGroupId(resultSet.getInt("bloodgroup_id"));
					beanList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDesignationList(ArrayList<DesignationBean> beanList){
		if(beanList.size()>0){
			beanList.clear();
		}
		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadEmployeeDesignationQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					DesignationBean bean = new DesignationBean();
					bean.setDesignation(resultSet.getString("designation"));
					bean.setDesignationId(resultSet.getInt("id"));
					beanList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void loadPaymentModeList(ArrayList<PaymentModeMasterBean> paymentBeanList){

		if(paymentBeanList.size()>0){
			paymentBeanList.clear();
		}
		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadEmployeePaymodeQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					PaymentModeMasterBean bean = new PaymentModeMasterBean();
					bean.setPaymentMode(resultSet.getString("payment_mode"));
					bean.setPaymentModeId(resultSet.getInt("id"));
					paymentBeanList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
	}
	
	public static void loadBankList(ArrayList<BankAccountBean> bankList){
		
		if(bankList.size()>0){
			bankList.clear();
		}
		try{	
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadEmployeeBankQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					BankAccountBean bean = new BankAccountBean();
					bean.setBankName(resultSet.getString("bank_name"));
					bean.setBankId(resultSet.getInt("id"));
					bankList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean insertEmployeeInfo(EmployeeMasterBean bean, String userName){
		boolean isInserted = false;
		try {
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.employeeInsertQuery, Arrays.asList(bean.getEmployeeCode(),bean.getEmployeeName(),bean.getCompanyId(),bean.getUnitId(),bean.getEmpPhone(),
									bean.getEmpEmail(),bean.getGender(), userName,userName));
				int i = preparedStatement.executeUpdate();
				if(i>0){
					isInserted = true;
					
				}
				
			} catch (Exception e) {
				connection.rollback();
				e.printStackTrace();
			}finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInserted;
		
	}
	
	
	public static int insertEmployeeInformation2(EmployeeMasterBean bean, String userName){
		
		boolean isInserted = false;
		int maxCompanyId = 0;
		
		try {
			
		Connection connection = DbConnection.createConnection();
		try {
			
			
			sql_info_insert_block:{
		
				PreparedStatement preparedStatement = null;
				
				try {

					preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.employeeInsertQuery, Arrays.asList(bean.getEmployeeCode(),bean.getEmployeeName(),bean.getCompanyId(),bean.getUnitId(),bean.getEmpPhone(),
										bean.getEmpEmail(),bean.getGender(), userName,userName, bean.getEmpDob(), bean.getUnitDesignationId()));
					
					System.out.println("Prep a b c >>> >> > " + preparedStatement);
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isInserted = true;
						
					}else {
						isInserted = false;
					}
						
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			} 	
			
			sql_select_max_emp_id:{
				
				if(isInserted){
					
					PreparedStatement preparedStatement = null;
					
					try {

						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.selectMaxEmployeeID, null);
						ResultSet resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							maxCompanyId = resultSet.getInt("employee_id");
						}
							
					} finally{
						if(preparedStatement != null){
							preparedStatement.close();
						}
						
					}
			
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			Messagebox.show("Already Exist ", "ERROR",Messagebox.OK,Messagebox.ERROR);
		}finally{
			if(connection != null){
				connection.close();
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return maxCompanyId;
	}
	
	
	public static boolean insertPersonalInfoDao(EmployeeMasterBean bean, int maxEmpId, String userName){
		int count = 0;
		boolean isInserted = false;
		try {
			Connection connection = DbConnection.createConnection();
			
				try {
					sql:{
					PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.insertPersoalInformationQuery, Arrays.asList(maxEmpId, bean.getEmpAddress(), bean.getEmpCity(), bean.getEmpStateId(),bean.getPinCode(), bean.getEmpBloodGroupId(),
									            bean.getEmpPan(), bean.getEmpMaritalStatus(), userName, userName));
							
							count = preparedStatement.executeUpdate();
							if(count>0){
								isInserted = true;
							}else {
								isInserted = false;
							}
							
						  } finally{
						  if(preparedStatement != null){
							  preparedStatement.close();
						  }
						}	
						
				      }
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(connection != null){
						connection.close();
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInserted;
	}
	
	
	public static boolean insertEmpOfficialDet(EmployeeMasterBean bean, int maxEmpId, String userName){
		
		int count = 0;
		boolean isInserted = false;
		try {
			Connection connection = DbConnection.createConnection();
			
				try {
					sql:{
					PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.insertEmployeeOfficialDetails, Arrays.asList(maxEmpId, bean.getEmpDoj(), bean.getEmpDesignationId(), bean.getEmpLocation(),bean.getPaymentModeId(), bean.getEmpBankId(),
									            bean.getEmpAccountNo(), bean.getIfscCode(),bean.getIncrementDate() , bean.getRegistrationDate(), bean.getLastWorkingDate(), userName, userName));
							
							count = preparedStatement.executeUpdate();
							if(count>0){
								isInserted = true;
							}else {
								isInserted = false;
							}
							
						  } finally{
						  if(preparedStatement != null){
							  preparedStatement.close();
						  }
						}	
						
				      }
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(connection != null){
						connection.close();
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInserted;
	}
	
	public static boolean pfEsiSave( EmployeeMasterBean bean, int empId, String userName){
		boolean isInserted = false;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				connection.setAutoCommit(false);
				sql:{
					PreparedStatement preparedStatement = null;
					try {
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.empPfEsiInsertQuery, Arrays.asList(empId, bean.getUan(),bean.getEsi(), userName, userName, bean.getPfNumber()));
						int i = preparedStatement.executeUpdate();
						connection.commit();
						if(i>0){
							isInserted = true;
						}else {
							isInserted = false;
						}
					}finally{
						if(preparedStatement != null){
							preparedStatement.close();
						}
					}
				}
				
			} catch (Exception e) {
				connection.rollback();
				e.printStackTrace();
		  }finally{
			  connection.setAutoCommit(true);
			  if(connection !=null){
				  connection.close();
			  }
		  }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInserted;
		
	}
	
    public static void onloadComponentDetails(ArrayList<ComponentMasterBean> beanList){
		
		if(beanList.size()>0){
			beanList.clear();
		}
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadComponentDetailsQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								ComponentMasterBean bean = new ComponentMasterBean();
								
								bean.setComponentName(resultSet.getString("component_name"));
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
	
    public static final ArrayList<ComponentMasterBean> loadComponentDetails(int companyId, int unitId, int unitdesignationId){
	ArrayList<ComponentMasterBean> list = new ArrayList<ComponentMasterBean>();
	if(list.size()>0){
		list.clear();
	}
	try {
		Connection connection = DbConnection.createConnection();
		sql_connection:{
			int count = 0;
			try {
				
				sql_block:{
				
					PreparedStatement preparedStatement = null;
					try {
					
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadComponentDetailsQuery, Arrays.asList(companyId, unitId, unitdesignationId));
						//System.out.println("preparedStatement ssss ssss >>> >> > " + preparedStatement);
						ResultSet resultSet = preparedStatement.executeQuery();
						
						while (resultSet.next()) {
							count = count+1;
							
							ComponentMasterBean bean = new ComponentMasterBean();
							
							bean.setCheckVal(true);
							bean.setCount(count);
							bean.setComponentName(resultSet.getString("component_name"));
							bean.setComponentId(resultSet.getInt("component_id"));
							bean.setComponentType(resultSet.getString("component_type"));
							bean.setComponentTypeId(resultSet.getInt("component_type_id"));
							bean.setComponentAmount(resultSet.getDouble("amount"));
							//bean.setCheckVal(false);
							//System.out.println("C C O U N T >>> >> > " + count);
							list.add(bean);
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
	
	return list;
}

    public static void insertComponentPerEmployee(ArrayList<ComponentMasterBean> list,int empId, Integer companyId, Integer unitId, String userName){
	boolean falg = false;
	int c = 0;
	
	try {
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				PreparedStatement preparedStatement = null;
				
				try {
					
					for(ComponentMasterBean bean : list){
					
					//if(bean.isCheckVal()==true && bean.getComponentAmount()>0){
			
					if(bean.isCheckVal()){	
					
					preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.insertComponentsPerEmpQuery, Arrays.asList(empId, bean.getComponentId(), bean.getComponentName(),bean.getComponentTypeId(),companyId,unitId,userName,userName, bean.getComponentAmount()));
					System.out.println("emp - sal - com p - " + preparedStatement); 
					c = preparedStatement.executeUpdate();
					
					}
					
				}
				
				
				if(c>0){
				
					Messagebox.show("Saved successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
				}else {
					
					Messagebox.show("Select Component \n and enter amount", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				
				
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			
		    }
		} catch (Exception e) {
			if(connection != null){
				connection.close();
			}
			e.printStackTrace();
			if(e.getMessage().contains("duplicate")){
			Messagebox.show("Already Exists ", "ERROR", Messagebox.OK, Messagebox.ERROR);
			}
			
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
}

    
    public static ArrayList<UnitDesignationBean> loadUnitDesignationList(int companyId, int unitId){

    	ArrayList<UnitDesignationBean> list = new ArrayList<UnitDesignationBean>();
    	
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadUnitDesignationQuery, Arrays.asList(companyId,unitId));
							
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								UnitDesignationBean bean = new UnitDesignationBean();
								bean.setUnitDesignation(resultSet.getString("designation"));
								bean.setUnitDesignationId(resultSet.getInt("id"));
								list.add(bean);
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
		System.out.println(list.size());
		return list;
	}

    
    
    public static int onClickInactive(int empId){
    	int i = 0;
    	
    	try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.empInactiveQuery, Arrays.asList(empId));
							
							i = preparedStatement.executeUpdate();
							
							
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
    	return i;
    }
    
    
	

}
