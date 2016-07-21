package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.DesignationMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.DesignationMasterDao;
import org.appsquad.dao.UnitMasterDao;
import org.zkoss.zul.Messagebox;

public class DesignationMasterService {

	
	
	
	public static boolean isValid(DesignationMasterBean bean){
		if(bean.getDesignation() != null){
			return true;
			
		}else{
			Messagebox.show("Designation Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
			return false;
		}
	}	

	public static void clearScreen(DesignationMasterBean bean){
		bean.setDesignation(null);
		bean.setDesignationId(0);
		
	}
	
	public static void insertDesignationMasterData(DesignationMasterBean bean){
		if(isValid(bean)){
			DesignationMasterDao.insertDesignationData(bean);
			
		}
	}
	
	public static ArrayList<DesignationMasterBean> loadAllDataOfDesignationMaster(){
		ArrayList<DesignationMasterBean> list  = new ArrayList<DesignationMasterBean>();
		list = DesignationMasterDao.onLoad();
		return list;
	}
}
