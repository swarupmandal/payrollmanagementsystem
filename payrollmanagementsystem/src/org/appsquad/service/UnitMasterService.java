package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.BloodGroupDao;
import org.appsquad.dao.UnitMasterDao;
import org.zkoss.zul.Messagebox;

public class UnitMasterService {

	public static boolean isValid(UnitMasterBean unitMasterBean, int bdId){
		
			if(unitMasterBean.getCompanyId() != 0){
				
				if(unitMasterBean.getUnitName() != null){
					
					if(bdId>0){
						
						if(unitMasterBean.getWorkingHour() != null){
						
						return true;
						
						}else {
							Messagebox.show("Working Hour Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);	
							return false;
						}
					
					}else {
						Messagebox.show("Base Days Type Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);	
						return false;
					
					}
					
				}else{
					Messagebox.show("Unit Name Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
					return false;
				}
			}else{
				Messagebox.show("Company Name Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
				return false;
			}
	}
	
	public static void insertUnitMasterData(UnitMasterBean unitMasterBean, UnitMasterBean baseDaysBean){
		
		if(isValid(unitMasterBean , baseDaysBean.getBaseDaysTypeId())){
			UnitMasterDao.insertUnitMasterData(unitMasterBean, baseDaysBean.getBaseDaysTypeId());
		}
	}
	
	public static void updateUnitMasterData(UnitMasterBean unitMasterBean){
		int i=0;
		if(isValid(unitMasterBean, i)){
			UnitMasterDao.updateUnitMasterData(unitMasterBean);
		}
	}
	
	public static void deleteUnitMasterData(UnitMasterBean unitMasterBean){
		UnitMasterDao.deleteUnitMasterData(unitMasterBean);
	}
	
	public static void loadAllDataOfUnitMaster(ArrayList<UnitMasterBean> unitMasterBeanList){
		UnitMasterDao.onLoad(unitMasterBeanList);
	}
	
	public static void clearScreen(UnitMasterBean unitMasterBean, UnitMasterBean baseDaysBean){
		unitMasterBean.setCompanyId(0);
		unitMasterBean.setUnitName(null);
		unitMasterBean.setUnitId(0);
		
		unitMasterBean.setWorkingHour(null);
		
		baseDaysBean.setBaseDaysTypeId(0);
		baseDaysBean.setBaseDaysType(null);
		
		
	}
	
}
