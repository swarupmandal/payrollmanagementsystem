package org.appsquad.service;

import java.util.ArrayList;

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
import org.appsquad.dao.ComponentMasterDao;
import org.appsquad.dao.EmployeeDao;
import org.zkoss.zul.Flash;
import org.zkoss.zul.Messagebox;

public class EmployeeMasterService {

	public static EmployeeMasterBean fetchEmployeeInfo(Integer empId){
		return EmployeeDao.getEmployeeInformation(empId);
	}
	
	public static ArrayList<EmployeeMasterBean> loadSavedEmployeeData(){
		return EmployeeDao.loadSavedEmployeeList();
	}
	
	public static ArrayList<EmployeeMasterBean> searchSavedEmployeeData(String employeeCode){
		return EmployeeDao.searchEmployeeFromList(employeeCode);
	}
	
	public static ArrayList<EmployeeMasterBean> searchEmployeeFromCompany(EmployeeMasterBean employeeMasterBean){
		return EmployeeDao.searchEmployeeFromCompanyList(employeeMasterBean);
	}
	
	
	public static void loadCompanyBeanList(ArrayList<CompanyMasterBean> compBeanList){
		EmployeeDao.loadCompanyList(compBeanList);
	}
	
	public static void loadUnitBeanList(ArrayList<UnitMasterBean> beanList){
		EmployeeDao.loadUnitList(beanList);
	}
	
	public static void loadStateBeanList(ArrayList<StateMasterBean> beanList){
		EmployeeDao.loadStateList(beanList);
	}
	
	public static void loadBloodGroupList(ArrayList<BloodGroupBean> beanList){
		EmployeeDao.onloadLoadBloodGroupList(beanList);
	}
	
	public static void loadDesignationList(ArrayList<DesignationBean> beanList){
		EmployeeDao.loadDesignationList(beanList);
	}
	
	public static void loadpaymentmodeList(ArrayList<PaymentModeMasterBean> paymentBeanList){
		EmployeeDao.loadPaymentModeList(paymentBeanList);
	}
	
	public static void loadBankList(ArrayList<BankAccountBean> bankList){
		EmployeeDao.loadBankList(bankList);
	}
	
	public static ArrayList<ComponentMasterBean> loadComponentDetatils(int companyId, int unitId, int unitdesignationId){
		ArrayList<ComponentMasterBean> list = new ArrayList<ComponentMasterBean>();
		list = EmployeeDao.loadComponentDetails(companyId, unitId, unitdesignationId);
		return list;
	}
	
	
	
	public static void updateEmployeeInfo(EmployeeMasterBean employeeMasterBean){
		EmployeeDao.upDateEmployee(employeeMasterBean);
	}
	
	public static boolean isValid(EmployeeMasterBean bean,CompanyMasterBean companyMasterBean, UnitMasterBean unitMasterBean, String userName){
		
		if(companyMasterBean.getCompanyName() != null && companyMasterBean.getCompanyName().trim().length()>0){

			if(unitMasterBean.getUnitName() != null && unitMasterBean.getUnitName().trim().length()>0){
				
				if(bean.getEmployeeCode() != null){
					
					if(bean.getEmployeeName() != null){
						
						if(bean.getEmpPhone() != null){
							
							if(bean.getGender() != null){
								
								if(bean.getEmpDob() != null){
									
									//if(bean.getUnitDesignationId() != null){
									
										return true;
										
									/*}else {
										
										Messagebox.show("Select Unit Designation","Informtion", Messagebox.OK, Messagebox.EXCLAMATION);
										return false;
									}*/
									
									
									
									
								}else {
									Messagebox.show("Entetr Employee Date of Birth","Informtion", Messagebox.OK, Messagebox.EXCLAMATION);
									return false;
								}
								
							}else {
								Messagebox.show("Enter Gender", "Alert", Messagebox.OK, Messagebox.EXCLAMATION);
								return false;
							}
							
						}else {
							Messagebox.show("Enter Employee Phone Number", "Alert", Messagebox.OK, Messagebox.EXCLAMATION);
							return false;
						}
						
					}else {
						Messagebox.show("Enter Employee Name", "Alert", Messagebox.OK, Messagebox.EXCLAMATION);
						return false;
					}
					
				}else {
					
					Messagebox.show("Enter Employee Code", "Alert", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
					
				}
				
			}else {
				Messagebox.show("Select Unit", "Alert", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
			
		}else {
				Messagebox.show("Select Comapny", "Alert", Messagebox.OK, Messagebox.EXCLAMATION);
		  	return false;
		}
	}

	
	public static boolean insertEmployeeInformation(EmployeeMasterBean bean, String userName){
		if(EmployeeDao.insertEmployeeInfo(bean, userName)){
			return true;
		    }else {
			return false;
		}
	}
	
	
	public static int insertEmployeeInformation2(EmployeeMasterBean bean, String userName){
		int id = 0;
		id = EmployeeDao.insertEmployeeInformation2(bean, userName);
		return id;
	}
	
	
	public static boolean insertPersonelInfoService(EmployeeMasterBean bean, int maxEmpId, String userName){
		if(EmployeeDao.insertPersonalInfoDao(bean, maxEmpId, userName)){
			return true;
		}else {
			return false;
		}
	}
	
	
	public static boolean insertEmpOfficialDet(EmployeeMasterBean bean, int maxEmpId, String userName){
		if(EmployeeDao.insertEmpOfficialDet(bean, maxEmpId, userName)){
			return true;
		} else{
				return false;
			}
		}
	
	public static boolean isPfEsiFieldEmpty(EmployeeMasterBean bean){
		
		boolean flag = false;
		
		if(bean.isPfCheckValue() || bean.isEsiCheckValue()){
			if(bean.isPfCheckValue()){
				if(bean.getUan() != null){
					flag = true;
				}
			}
			if(bean.isEsiCheckValue()){
				if(bean.getEsi() != null){
					flag = true;
				}
			}
		}
		if(bean.isPfCheckValue() && bean.isEsiCheckValue()){
			if(bean.getUan() != null && bean.getEsi() != null){
				flag = true;
		    }else {
				flag = false;
			}
	  }
		
		return flag;
	}
	
	public static boolean pfEsiInsert(EmployeeMasterBean bean, int empId, String userName){
		if(EmployeeDao.pfEsiSave(bean, empId, userName)){
			return true;
		}else {
			return false;
		}
		
	}
	
	/*public static void loadComponentDetails(ArrayList<ComponentMasterBean> beanList){
		EmployeeDao.onloadComponentDetails(beanList);
		
	}*/
	
	public static ArrayList<UnitDesignationBean> loadUnitDesignation(int companyId, int unitId){
		ArrayList<UnitDesignationBean> list = new ArrayList<UnitDesignationBean>();
		list = EmployeeDao.loadUnitDesignationList(companyId, unitId);
		
		
		return list;
	}
	
	
	
	public static boolean isEmptyLocationField(EmployeeMasterBean bean){
		boolean flag = false;
		if(bean.getCompanyId()>0){
			if(bean.getUnitId()>0){
				return true;
			}else {
				Messagebox.show("Select Unit", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			}	return false;
			}else {
				Messagebox.show("Select Company Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
		}
		
	}
	
	public static void saveComponentsPerEmployee(ArrayList<ComponentMasterBean> list,int empId, Integer companyId, Integer unitId, String userName){
		EmployeeDao.insertComponentPerEmployee(list, empId, companyId, unitId, userName);
	}
	
	
	public static void clearData(){
		
	}
	
}


    
	

