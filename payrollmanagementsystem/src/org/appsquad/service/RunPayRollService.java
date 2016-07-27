package org.appsquad.service;

import java.util.ArrayList;
import java.util.Calendar;

import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.dao.RunPayRollDao;
import org.zkoss.zul.Messagebox;

import com.itextpdf.text.log.SysoCounter;

import utility.CheckFriDay;
import utility.CheckMonDayCount;
import utility.CheckSaturday;
import utility.CheckSunDayDates;
import utility.CheckSunday;
import utility.CheckThursDay;
import utility.CheckWednesDay;

public class RunPayRollService {
	
	
	public static void loadMonth(ArrayList<MonthMasterBean> monthList){
		RunPayRollDao.loadMonthList(monthList);
	}
	
	public static void loadEmpDetails(ArrayList<RunPayRollBean> beanList, int companyId, int unitId, int workingDay){
		RunPayRollDao.loadEmpDetails(beanList, companyId, unitId, workingDay);
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
	
	public static Integer sunDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer sundays;
		sundays = RunPayRollDao.sunDayCount(companyId, unitId, bean);
		return sundays;
	}
 
	public static Integer satDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer satdays;
		satdays = RunPayRollDao.satDayCount(companyId, unitId, bean);
		return satdays;
	}
	
	public static Integer monDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer mondays;
		mondays = RunPayRollDao.monDayCount(companyId, unitId, bean);
		return mondays;
	}
 
	public static Integer tuesDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer tuesdays;
		tuesdays = RunPayRollDao.tuesDayCount(companyId, unitId, bean);
		return tuesdays;
	}
	
	public static Integer wednesDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer wednesdays;
		wednesdays = RunPayRollDao.wednesDayCount(companyId, unitId, bean);
		return wednesdays;
	}
 
	public static Integer thursDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer thursdays;
		thursdays = RunPayRollDao.thursDayCount(companyId, unitId, bean);
		return thursdays;
	}
	
	public static Integer friDayCount(int companyId, int unitId, RunPayRollBean bean){
		Integer fridays;
		fridays = RunPayRollDao.friDayCount(companyId, unitId, bean);
		return fridays;
	}
	
	public static int totnoOfDaysInMonth(int currentMonth, int currentYr){
		int totDays;
		Calendar cal = Calendar.getInstance();
		totDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		return totDays;
	}
	
	
	
	public static int sundayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		
		int noOfSunDay = CheckSunday.countWeekendDays(currentYr, currentMonth);
		System.out.println("NUM of sUN Day " +noOfSunDay);
		
		return noOfSunDay;
	}
	
	public static int satdayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		
		int noOfSatDay = CheckSaturday.countWeekendDays(currentYr, currentMonth);
		System.out.println("NUM of saT Day " + noOfSatDay);
		
		return noOfSatDay;
		
	}
	
	public static int monDayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		
		int noOfMonDay = CheckMonDayCount.countWeekendDays(currentYr, currentMonth);
		System.out.println("NUM of moN Day " + noOfMonDay);
		
		return noOfMonDay;
	}
	
	public static int tuesDayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		
		int noOfTuesDay = CheckMonDayCount.countWeekendDays(currentYr, currentMonth);
		System.out.println("NUM of tueS Day " + noOfTuesDay);
		
		return noOfTuesDay;
	}
	
	public static int wednesDayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		
		int noOfWednesDay = CheckWednesDay.countWeekendDays(currentYr, currentMonth);
		System.out.println("NUM of tueS Day " + noOfWednesDay);
		
		return noOfWednesDay;
		
	}
	
	public static int thursDayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		
		int noOfThursDay = CheckThursDay.countWeekendDays(currentYr, currentMonth);
		System.out.println("NUM of thurS Day " + noOfThursDay);
		
		return noOfThursDay;
		
	}
	
	public static int friDayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		
		int noOfFriDay = CheckFriDay.countWeekendDays(currentYr, currentMonth);
		System.out.println("NUM of thurS Day " + noOfFriDay);
		
		return noOfFriDay;
		
	}
	
	
	public static int generalHolidayDeduction(int companyId, int unitId, RunPayRollBean bean, int currentMonthId){
		
		int noOfGeneralHoliday = RunPayRollDao.generalHoliDayCount(companyId, unitId, bean, currentMonthId);
		System.out.println("Num Of General Holidays "+ noOfGeneralHoliday);
		
		return noOfGeneralHoliday;
	}
	
	public static Double hoursPerDay(int companyId, int unitId, RunPayRollBean bean){
		Double hours = RunPayRollDao.loadHoursPerDay(companyId, unitId, bean);
		System.out.println("Hours service " + hours);
		
		return hours; 
		
	}
	
	public static ArrayList<String> loadholiDayListPerMonth(int companyId, int unitId, RunPayRollBean bean, int currentMonthId, int year){
		ArrayList<String> list = new ArrayList<String>();
		list = RunPayRollDao.generalHoliDayDateList(companyId, unitId, bean, currentMonthId);
		
		if(list.size()>0){
			ArrayList<Integer> list1 = new ArrayList<Integer>();
			int i = currentMonthId-1;
			list1 = CheckSunDayDates.checkSatDates(year, i);
			
		}
		if(list.size()>0){
			/*String str ="";
			if(list.size()!=0){
			StringBuilder builder = new StringBuilder();
			String temp = list.toString();
			String fb = temp.replace("[", "");
			String bb =  fb.replace("]", "");
			builder.append(bb);
			str = builder.toString();
			str = str.trim();
			System.out.println("STR " + str);*/
		}
		
		
		
		
		return list;
	}
	
	
	
	
 	public static int saatdayDeduction(RunPayRollBean bean, int currentMonth, int currentYr){
		int totalNumberOfDaysInaMonth = 0;
		int noOfSunDay;
		int totalNumOfWorkingDays;
		
		Calendar cal = Calendar.getInstance();
	    totalNumOfWorkingDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	    noOfSunDay = CheckSunday.countWeekendDays(currentYr, currentMonth);
	    totalNumOfWorkingDays = totalNumberOfDaysInaMonth-noOfSunDay;
		noOfSunDay = 0;
		
		return totalNumOfWorkingDays;
	}
	
    public static Integer countHoliDaysPerMonth(RunPayRollBean bean, int currentMonth, int currentYr){
    	
    	int totalNumberOfDaysInaMonth;
    	int noOfSunDay;
    	int noOfSatDay;
    	int totalNoOfWorkingDays;
    	
    	no_of_working_days_with_out_sunday:{
    		
    		Calendar cal = Calendar.getInstance();
    	    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    		totalNumberOfDaysInaMonth = days;
    		
    		noOfSunDay = CheckSunday.countWeekendDays(currentYr, currentMonth);
    		totalNoOfWorkingDays = totalNumberOfDaysInaMonth-noOfSunDay;
    		noOfSunDay = 0;
    	}
    	no_of_working_days_with_out_saturDay:{
    		
    		noOfSatDay = CheckSaturday.countWeekendDays(currentYr, currentMonth);
    		totalNoOfWorkingDays = totalNumberOfDaysInaMonth-noOfSatDay;
    		noOfSatDay = 0;
    		
    	}
    	
    	return totalNoOfWorkingDays;
    	
    }
	
}
