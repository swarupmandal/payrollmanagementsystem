package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.dao.RunPayRollDao;
import org.zkoss.zul.Messagebox;

public class RunPayRollService {
	
	public static void loadMonth(ArrayList<MonthMasterBean> monthList){
		RunPayRollDao.loadMonthList(monthList);
	}
	
	public static void loadEmpDetails(ArrayList<RunPayRollBean> beanList, int companyId, int unitId){
		RunPayRollDao.loadEmpDetails(beanList, companyId, unitId);
	}

	public static boolean totalWorkingDaysisNull(Integer totalWorkingDays, Double workinghoursPerDay){
		if(totalWorkingDays != null){
			if(workinghoursPerDay != null){
				return true;
			}else {
				Messagebox.show("Enter Working Hours Per Day", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}else {
			Messagebox.show("Enter Total Working Days", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
	}
	
	public static boolean totalOtHoursIsNullCheck(Integer totalWorkingDays, Double workinghoursPerDay, Double otHours){
		if(totalWorkingDaysisNull(totalWorkingDays, workinghoursPerDay)){
			if(otHours != null){
					return true;
			}else {
			Messagebox.show("Enter Over Time Hours", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
			}
		}else {
			return false;
		}
	}
	
	public static boolean totalLeaveIsNullCheck(Integer totalWorkingDays, Double workinghoursPerDay, Integer days){
		if(totalWorkingDaysisNull(totalWorkingDays, workinghoursPerDay)){
			if(days !=null){
					return true;
			}else {
			Messagebox.show("Enter No of Leave Day", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
			}
		}else {
			return false;
		}
	}
	
	
	
	
}
