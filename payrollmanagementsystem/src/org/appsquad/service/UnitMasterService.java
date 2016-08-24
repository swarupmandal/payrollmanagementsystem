package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.BloodGroupDao;
import org.appsquad.dao.UnitMasterDao;
import org.zkoss.zul.Messagebox;

public class UnitMasterService {

	public static boolean isValid(UnitMasterBean unitMasterBean, int bdId, int wgsid, int sunId){
		
			if(unitMasterBean.getCompanyId() != 0){
				
				if(unitMasterBean.getUnitName() != null){
					
					if(bdId>0){
						
						if(unitMasterBean.getWorkingHour() != null){
						
							if(wgsid >0){
								
								if(sunId>0){
								
									return true;
						
								}else {
									Messagebox.show("Sunday Selection Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);	
									return false;
								}
								
							}else {
								Messagebox.show("Wages Type Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);	
								return false;
							}
						
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
	
	public static void insertUnitMasterData(UnitMasterBean unitMasterBean, UnitMasterBean baseDaysBean, UnitMasterBean wgsBean, UnitMasterBean sunBean){
		
		if(isValid(unitMasterBean , baseDaysBean.getBaseDaysTypeId(), wgsBean.getWagesTypeId(), sunBean.getSundaySelectionId())){
			UnitMasterDao.insertUnitMasterData(unitMasterBean, baseDaysBean.getBaseDaysTypeId() , wgsBean.getWagesTypeId(), sunBean.getSundaySelectionId());
		}
	}
	
	public static void updateUnitMasterData(UnitMasterBean unitMasterBean){
		int i=0;
		int j=0;
		int k=0;
		if(isValid(unitMasterBean, i, j, k)){
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
	
	public static ArrayList<UnitMasterBean> fetchWagesType(){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		list = UnitMasterDao.fetchWagesType();
		return list;
	}
	
	public static ArrayList<UnitMasterBean> sundaySelType(){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		list = UnitMasterDao.fetchSundaySelect();
		return list;
	}
	
	public static int upDateUnit(UnitMasterBean bean){
		int i = 0;
		i = UnitMasterDao.upDateUnit(bean);
		return i;
	}
	
	
}
