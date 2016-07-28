package org.appsquad.service;

import java.sql.Date;
import java.util.ArrayList;

import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.HolidayMasterGeneralHolidayBean;
import org.appsquad.bean.HolidayMasterWeekDayBean;
import org.appsquad.dao.HolidayMasterDao;
import org.zkoss.zul.Messagebox;

public class HolidayMasterService {

	
	public static void loadLeaveYr(HolidayMasterBean bean){
		HolidayMasterDao.loadLeaveYr(bean);
	}
	
	public static void saveHourPerDay(HolidayMasterBean bean, String userName, int companyId, int unitId){
		
		HolidayMasterDao.saveHourPerDay(bean, userName, companyId, unitId);
		
	}
	
	
	
	public static Boolean dateIsEmpty(HolidayMasterBean bean) {
		System.out.println(bean.getLeavYrStartDate());
		System.out.println(bean.getLeavYrEndDate());
		if (bean.getLeavYrStartDate()!=null) {
			if (bean.getLeavYrEndDate()!= null) {
				if(bean.getLeavYrEndDate().after(bean.getLeavYrStartDate()))
				{
					return true;
				}else {
					Messagebox.show("End planning date must be after start planning date!!", "Information", Messagebox.OK,Messagebox.EXCLAMATION);
					bean.setLeavYrEndDate(null);
					return false;
				}
			} else {
				Messagebox.show("End Date Required!!", "Information", Messagebox.OK,Messagebox.EXCLAMATION);
				return false;
			}
		} else {
			Messagebox.show("Start Date Required!!", "Information", Messagebox.OK,Messagebox.EXCLAMATION);
			return false;
		}
	}
	
	public static boolean generalFieldIsEmpty(HolidayMasterGeneralHolidayBean bean){
		if(bean.getGeneralHolidayDate()!=null){
			if(bean.getGeneralHolidayName()!=null){
				return true;
				
			}else {
			
				Messagebox.show("Enter Holidayname", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}else {
			Messagebox.show("Enter Holidayname", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
	}
	
	
	public static int saveLeaveYear(HolidayMasterBean bean, String userName){
		int id = 0;
		if(dateIsEmpty(bean)){
		id = HolidayMasterDao.saveLeaveYr(bean,userName);
		
		}
		return id;
	}
	
	public static ArrayList<HolidayMasterWeekDayBean> loadWeekLyHoliDayList(HolidayMasterWeekDayBean bean){
		ArrayList<HolidayMasterWeekDayBean> list = new ArrayList<HolidayMasterWeekDayBean>();
		if(bean.getWeeklyHoliDayName()!=null){
			list = HolidayMasterDao.loadWeekDayData();
		}
		return list;
	}
	
	public static ArrayList<HolidayMasterBean> loadWeeklyHolidayMasterData(int leaveYrId, int companyId, int unitId){
		ArrayList<HolidayMasterBean> list = new ArrayList<HolidayMasterBean>();
		list = HolidayMasterDao.LoadWeekDayMasterData(leaveYrId, companyId, unitId);
		return list;
		
	}
	
	
	public static void saveHolidayMasterData(int noOfDays, String week, HolidayMasterBean bean, String userName, int companyId, int unitId){
		HolidayMasterDao.saveWeekLeaveMasterData(noOfDays ,week, bean, userName, companyId, unitId);
	}
	
	public static void deleteHolidayMasterData(HolidayMasterBean bean){
		HolidayMasterDao.deleteWeekDayMasterData(bean);
	}
	
	public static void saveGeneralHoliDayMasterData(HolidayMasterGeneralHolidayBean bean,Date date, String holiDayName, String userName, int leaveYrId, int companyId, int unitId){
		if(generalFieldIsEmpty(bean)){
		
			HolidayMasterDao.saveGeneralHoliDayMasterData(bean ,date, holiDayName, userName, leaveYrId, companyId, unitId);
		}
	}
	
	public static ArrayList<HolidayMasterGeneralHolidayBean> loadGenerealHoliDayList(int leaveYrId, int companyId, int unitId){
		ArrayList<HolidayMasterGeneralHolidayBean> list = new ArrayList<HolidayMasterGeneralHolidayBean>();
		list = HolidayMasterDao.loadGenerealHoliDayMasterData(leaveYrId, companyId, unitId);
		return list;
	}
	
	public static void deleteGeneralHoliDayMasterData(HolidayMasterGeneralHolidayBean bean){
		HolidayMasterDao.deleteGeneralHoliDayMasterData(bean);
	}
	
	
	
}
