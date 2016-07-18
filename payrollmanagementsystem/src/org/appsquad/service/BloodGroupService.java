package org.appsquad.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.dao.BloodGroupDao;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.SqlQuery;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class BloodGroupService {

	public static boolean isValid(BloodGroupBean bloodGroupBean,String userName){
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
	
	public static boolean insertBloodGroupData(BloodGroupBean bloodGroupBean, String userName){
		if(BloodGroupDao.insertBloodGroupData(bloodGroupBean, userName)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean updateBloodGroupData(BloodGroupBean bloodGroupBean, String userName){
		if(BloodGroupDao.updateBloodGroupData(bloodGroupBean, userName)){
			return true;
		}else{
			return false;
		}
	}
}
