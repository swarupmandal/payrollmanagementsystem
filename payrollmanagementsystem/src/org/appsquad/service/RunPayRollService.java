package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.dao.RunPayRollDao;

public class RunPayRollService {
	
	public static void loadMonth(ArrayList<MonthMasterBean> monthList){
		RunPayRollDao.loadMonthList(monthList);
	}
	
	public static void loadEmpDetails(ArrayList<RunPayRollBean> beanList, int companyId, int unitId){
		RunPayRollDao.loadEmpDetails(beanList, companyId, unitId);
	}

}
