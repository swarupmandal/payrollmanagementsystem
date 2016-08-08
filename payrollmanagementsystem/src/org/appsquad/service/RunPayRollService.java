package org.appsquad.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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
	
	public static void loadEmpDetails(ArrayList<RunPayRollBean> beanList, int companyId, int unitId, int workingDay, int unitDesignationId){
		RunPayRollDao.loadEmpDetails(beanList, companyId, unitId, workingDay, unitDesignationId);
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
	
	public static int loadholiDayListPerMonth(int companyId, int unitId, RunPayRollBean bean, int currentMonthId, int year, int sundayCount){
		int matchingdayCount =0;
		Set<Integer> setToReturnn = new HashSet<Integer>();
		ArrayList<String> generalholidaylist = new ArrayList<String>();
		ArrayList<Integer> sundayDateList = new ArrayList<Integer>();
		ArrayList<Integer> NewGeneralholidayDayList = new  ArrayList<Integer>();
		generalholidaylist = RunPayRollDao.generalHoliDayDateList(companyId, unitId, bean, currentMonthId);
		
		if(generalholidaylist.size()>0){
			
			int i = currentMonthId-1;
			sundayDateList = CheckSunDayDates.checkSatDates(year, i);
			
		}
		
		System.out.println("bean.getS " +sundayCount);
		if(generalholidaylist.size()>0 && sundayCount>0){
			
			
			for(String j : generalholidaylist){
				String part = j.replaceFirst ("^0*", "");
				int k = Integer.parseInt(part);
				
				NewGeneralholidayDayList.add(k);
			}
			
			
			NewGeneralholidayDayList.addAll(sundayDateList);
			
			setToReturnn = findDuplicates(NewGeneralholidayDayList);
			matchingdayCount=setToReturnn.size();
			
			
		}
		return matchingdayCount;
		
	}
	
	public static Set<Integer> findDuplicates(ArrayList<Integer> listContainingDuplicates) {
		   
		  final Set<Integer> setToReturn = new HashSet<Integer>();
		  final Set<Integer> set1 = new HashSet<Integer>();
		 
		  for (Integer yourInt : listContainingDuplicates) {
		   if (!set1.add(yourInt)) {
		    setToReturn.add(yourInt);
		   }
		  }
		
		  return setToReturn;
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

    public static final double getEmpWages(int empId){
    	double wages;
    	wages = RunPayRollService.getEmpWages(empId);
    	return wages;
    	
    }
    
    public static void saveEmpSalStore(ArrayList<RunPayRollBean> runPayRollBeanList, RunPayRollBean bean){
    	RunPayRollDao.saveSalSheet(runPayRollBeanList, bean);
    	
    }
    
}
