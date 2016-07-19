package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.dao.BloodGroupDao;
import org.zkoss.zul.Messagebox;

public class BloodGroupService {

	public static boolean isValid(BloodGroupBean bloodGroupBean){
		if(bloodGroupBean.getBloodGroupName()!=null && bloodGroupBean.getBloodGroupName().trim().length()>0){
			return true;
		}else {
			Messagebox.show("Enter Blood Group Name","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
			return false;
		}
	}
	
	public static void clearData(BloodGroupBean bloodGroupBean){
		bloodGroupBean.setBloodGroupName(null);
		bloodGroupBean.setBloodGroupId(0);
	}
	
	public static void loadAllDataOfBloodGroup(ArrayList<BloodGroupBean> bloodGroupBeanList){
		BloodGroupDao.onLoad(bloodGroupBeanList);
	}
	
	public static void insertBloodGroupData(BloodGroupBean bloodGroupBean){	
		if(isValid(bloodGroupBean)){
			BloodGroupDao.insertBloodGroupData(bloodGroupBean);
		}
	}
	
	public static void updateBloodGroupData(BloodGroupBean bloodGroupBean){
		if(isValid(bloodGroupBean))
			BloodGroupDao.updateBloodGroupData(bloodGroupBean);
	}
	
	public static void deleteBloodGroupData(BloodGroupBean bloodGroupBean){
			BloodGroupDao.deleteBloodGroupData(bloodGroupBean);
	}
}
