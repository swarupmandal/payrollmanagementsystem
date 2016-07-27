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
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.ComponentMasterSql;
import org.appsquad.sql.ComponentPerUnitMasterSql;
import org.appsquad.sql.EmployeeMasterSql;
import org.zkoss.zul.Flash;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class EmployeeDao {
	
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
										bean.getEmpEmail(),bean.getGender(), userName,userName));
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
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.empPfEsiInsertQuery, Arrays.asList(empId, bean.getUan(),bean.getEsi(), userName, userName));
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
	
public static final ArrayList<ComponentMasterBean> loadComponentDetails(int companyId, int unitId){
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
					
						preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.loadComponentDetailsQuery, Arrays.asList(companyId, unitId));
						System.out.println("preparedStatement >>> >> > " + preparedStatement);
						ResultSet resultSet = preparedStatement.executeQuery();
						
						while (resultSet.next()) {
							count = count+1;
							
							ComponentMasterBean bean = new ComponentMasterBean();
							bean.setCount(count);
							bean.setComponentName(resultSet.getString("component_name"));
							bean.setComponentId(resultSet.getInt("component_id"));
							bean.setComponentType(resultSet.getString("component_type"));
							bean.setComponentTypeId(resultSet.getInt("component_type_id"));
							bean.setCheckVal(false);
							System.out.println("C C O U N T >>> >> > " + count);
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
					
					if(bean.isCheckVal()==true && bean.getComponentAmount()>0){
			
					preparedStatement = Util1.createQuery(connection, EmployeeMasterSql.insertComponentsPerEmpQuery, Arrays.asList(empId, bean.getComponentId(), bean.getComponentName(),bean.getComponentTypeId(),companyId,unitId,userName,userName, bean.getComponentAmount()));
					
					//preparedStatement.addBatch(); 
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
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
}

	

}
