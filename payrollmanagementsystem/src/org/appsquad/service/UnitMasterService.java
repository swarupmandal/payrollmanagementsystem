package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.BloodGroupDao;
import org.appsquad.dao.UnitMasterDao;
import org.zkoss.zul.Messagebox;

public class UnitMasterService {

	public static boolean isValid(UnitMasterBean unitMasterBean){
			if(unitMasterBean.getCompanyId() != 0){
				if(unitMasterBean.getUnitName() != null){
					return true;
				}else{
					Messagebox.show("Unit Name Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
					return false;
				}
			}else{
				Messagebox.show("Company Name Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
				return false;
			}
	}
	
	public static void insertUnitMasterData(UnitMasterBean unitMasterBean){
		if(isValid(unitMasterBean)){
			UnitMasterDao.insertBloodGroupData(unitMasterBean);
		}
	}
	
	public static void updateUnitMasterData(UnitMasterBean unitMasterBean){
		if(isValid(unitMasterBean)){
			UnitMasterDao.updateUnitMasterData(unitMasterBean);
		}
	}
	
	public static void deleteUnitMasterData(UnitMasterBean unitMasterBean){
		UnitMasterDao.deleteUnitMasterData(unitMasterBean);
	}
	
	public static void loadAllDataOfUnitMaster(ArrayList<UnitMasterBean> unitMasterBeanList){
		UnitMasterDao.onLoad(unitMasterBeanList);
	}
	
	public static void clearScreen(UnitMasterBean unitMasterBean){
		unitMasterBean.setCompanyId(0);
		unitMasterBean.setUnitName(null);
		unitMasterBean.setUnitId(0);
	}
	
}
