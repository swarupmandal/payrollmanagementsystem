package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.bean.DesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.ComponentPerUnitMasterDao;
import org.appsquad.dao.EmployeeDao;
import org.zkoss.zul.Messagebox;

public class ComponentPerUnitMasterService {
	
	public static ArrayList<UnitMasterBean> loadUnitBeanList(Integer id){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		list = ComponentPerUnitMasterDao.loadUnitList(id);
		return list;
	}
		
	public static ArrayList<ComponentPerUnitMasterBean> loadData(){
		ArrayList<ComponentPerUnitMasterBean> list = new ArrayList<ComponentPerUnitMasterBean>();
		list = ComponentPerUnitMasterDao.onLoadData();
		return list;
	}
	
	public static boolean isEmptyLocationField(ComponentPerUnitMasterBean bean){
		boolean flag = false;
		if(bean.getCompanyId()>0){
			if(bean.getUnitId()>0){
				   if(bean.getUnitDesignationId()>0){
					   return true; 
					   
				   }else {
					Messagebox.show("Select Designation", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
			}else {
				Messagebox.show("Select Unit", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			}	return false;
		}else {
			Messagebox.show("Select Company Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		
	}
	
	public static void saveComponentPerUnit(ArrayList<ComponentPerUnitMasterBean> list, Integer companyId, Integer unitId, String userName ,int designationId){
		ComponentPerUnitMasterDao.insertComponentPerUnit(list, companyId, unitId, userName, designationId);
		
		
	}
	public static void saveHourPerDesignation(int companyId, int unitId ,int designationId, double workingHour, String username){
		ComponentPerUnitMasterDao.saveHourPerDay(companyId, unitId, designationId, workingHour, username);
	}
	
	public static void saveBaseDaysPerUnit(int companyId, int unitId ,int baseDays, double workingHour, String userName){
		ComponentPerUnitMasterDao.saveBaseDayPerUnit(companyId, unitId, baseDays, workingHour, userName);
	}
	
	public static void saveComponentPerUnit2(ArrayList<ComponentPerUnitMasterBean> list, ComponentPerUnitMasterBean bean){
		
		ComponentPerUnitMasterDao.insertComponentPerUnit2(list, bean);
		
	}
	
	public static ArrayList<ComponentPerUnitMasterBean> onLoadExistingComponent(int companyId, int unitId, int unitDesId){
		ArrayList<ComponentPerUnitMasterBean> list = new ArrayList<ComponentPerUnitMasterBean>();
		list = ComponentPerUnitMasterDao.onSelectExistingDesignation(companyId, unitId, unitDesId);
		
		return list;
	}
	
	
}
