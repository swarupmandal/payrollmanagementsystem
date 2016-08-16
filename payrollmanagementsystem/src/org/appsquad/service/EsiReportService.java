package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.dao.EsiReportDao;

public class EsiReportService {

	public static ArrayList<String> getLvYr(){
		
		ArrayList<String> lvYrlist = new ArrayList<String>();
		lvYrlist = EsiReportDao.getLvYrList();
	
		return lvYrlist;
	}
}
