package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EmployeeMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.EmployeeDao;
import org.zkoss.zul.Messagebox;

public class EmployeeMasterService {

	public static void loadCompanyBeanList(ArrayList<CompanyMasterBean> compBeanList){
		EmployeeDao.loadCompanyList(compBeanList);
	}
	
	public static void loadUnitBeanList(ArrayList<UnitMasterBean> beanList){
		EmployeeDao.loadUnitList(beanList);
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
	
	public static void clearData(){
		
	}
	
}


    
	

