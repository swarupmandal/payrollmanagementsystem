package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.EsiReportBean;
import org.appsquad.dao.EsiReportDao;

public class EsiReportService {

	public static ArrayList<String> getLvYr(){
		
		ArrayList<String> lvYrlist = new ArrayList<String>();
		lvYrlist = EsiReportDao.getLvYrList();
	
		return lvYrlist;
	}
	public static ArrayList<EsiReportBean> getUnitDesList(EsiReportBean bean){
		ArrayList<EsiReportBean> arrayList = new ArrayList<EsiReportBean>();
		arrayList = EsiReportDao.getUnitDesignationList(bean);
		System.out.println("");
		return arrayList;
	}
	
	public static ArrayList<EsiReportBean> getEsiReport(EsiReportBean prmBean){
		ArrayList<EsiReportBean> list = new ArrayList<EsiReportBean>();
		list = EsiReportDao.getEsiReport(prmBean);
		return list;
	}
	
}
