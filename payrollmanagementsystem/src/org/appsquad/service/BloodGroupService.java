package org.appsquad.service;

import org.appsquad.bean.BloodGroupBean;
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
}
