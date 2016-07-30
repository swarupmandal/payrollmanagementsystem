package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.dao.ComponentMasterDao;
import org.appsquad.dao.ComponentPerUnitMasterDao;
import org.zkoss.zul.Messagebox;

public class ComponentMasterService {

	public static ArrayList<ComponentMasterBean> loadComponents(){
		ArrayList<ComponentMasterBean> list  =ComponentMasterDao.loadComponentName();
		return list;
	}
	
	public static boolean insertComponentDetails(ComponentMasterBean bean, String userName){
		if(ComponentMasterDao.saveComponentDetails(bean, userName)){
			bean.setComponentName(null);
			bean.setComponentTypeId(null);
			bean.setComponentType(null);
			
			return true;
		}else {
			return false;
		}
		
	}
	
	public static boolean isEmptyFieldCheck(ComponentMasterBean bean){
			if(bean.getComponentName() !=null){
				 if(bean.getComponentTypeId() >0){
						return true;
					}else {
					  Messagebox.show("Select Componet Type", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
					  return false;
					}
				}else{
			  Messagebox.show("Select Componet", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
	}
	
	public static void loadComponentDetails(ArrayList<ComponentMasterBean> beanList){
		ComponentMasterDao.onloadComponentDetails(beanList);
		
	}
	
}
