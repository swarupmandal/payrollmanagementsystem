package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.BankAccountBean;
import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.DesignationBean;
import org.appsquad.bean.EmployeeMasterBean;
import org.appsquad.bean.PaymentModeMasterBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.EmployeeDao;
import org.zkoss.zul.Flash;
import org.zkoss.zul.Messagebox;

public class EmployeeMasterService {

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
	
	
	
	public static boolean isValid(CompanyMasterBean companyMasterBean, UnitMasterBean unitMasterBean, String userName){
		
		if(companyMasterBean.getCompanyName() != null && companyMasterBean.getCompanyName().trim().length()>0){

			if(unitMasterBean.getUnitName() != null && unitMasterBean.getUnitName().trim().length()>0){
				
				return true;
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
		if((bean.isPfCheckValue() && bean.getUan() != null) || (bean.isEsiCheckValue() && bean.getEsi() != null)){
			return true;
		}
		
		boolean falg = false;
		
		if(bean.isPfCheckValue() || bean.isEsiCheckValue()){
			if(bean.isPfCheckValue()){
				if(bean.getUan() != null){
					falg = true;
				}
			}
			if(bean.isEsiCheckValue()){
				if(bean.getEsi() != null){
					falg = true;
				}
			}
		}
		return falg;
	}
	
	
	
	
	
	public static void clearData(){
		
	}
	
}


    
	

