package org.appsquad.viewmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.HolidayMasterService;
import org.appsquad.service.RunPayRollService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import com.itextpdf.text.DocumentException;

import utility.PdfPaySlipGenerator;

public class RunPayrollViewModel {
	
	RunPayRollBean runPayRollBean = new RunPayRollBean();
	
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	private UnitMasterBean unitMasterBean = new UnitMasterBean();
	private MonthMasterBean monthMasterBean = new MonthMasterBean();
	private EmployeeSalaryComponentAmountBean amountBean = new EmployeeSalaryComponentAmountBean();
	private HolidayMasterBean holidayMasterBean =new HolidayMasterBean();
	
	
	private ArrayList<RunPayRollBean> runPayRollBeanList = new ArrayList<RunPayRollBean>();
	private ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	private ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	private ArrayList<MonthMasterBean> monthList = new ArrayList<MonthMasterBean>();
	
	
	
	Session session = null;
	String userName;
	String date;
	int day, month,year;
	int second, minute, hour;
	String currentDate;
	String currentTime;
	
	public boolean salaryAdjustmentVisibility = true;
	public boolean salaryComponentVisibility  = false;
	
	public boolean nextButtonVisibility = false;
	public boolean prevButtonVisibility = false;
	
	public boolean allChecked = false;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		System.out.println("R U N P A Y R O L L");
		dateFormat();
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
		RunPayRollDao.loadMonthList(monthList);
		loadLeaveYrDate();
		
		runPayRollBean.setLeaveYrId(holidayMasterBean.getLeaveYrId());
		
		
	}

	public void dateFormat(){
		GregorianCalendar date = new GregorianCalendar();
		  day = date.get(Calendar.DAY_OF_MONTH);
	      month = date.get(Calendar.MONTH)+1;
	      year = date.get(Calendar.YEAR);
	      
	      second = date.get(Calendar.SECOND);
	      minute = date.get(Calendar.MINUTE);
	      hour = date.get(Calendar.HOUR);
	       
	      currentDate =day+"-"+month+"-"+year;
	      currentTime = hour+" : "+minute+" : "+second;
	      
	      System.out.println("MON T " + month);
	      System.out.println("Current Date " +currentDate );
	      System.out.println("current date "+ currentDate +" - " + currentTime);
	      
	}

	
	public void loadLeaveYrDate(){
		HolidayMasterService.loadLeaveYr(holidayMasterBean);
		
	}
	
	
	@Command
	@NotifyChange("*")
	public void onSelectMonth(){
		//System.out.println("MONTH " + monthMasterBean.getMonthName() + " - ID - " + monthMasterBean.getMonthId());
	}
	@Command
	@NotifyChange("*")
	public void onSelectCompany(){
		//System.out.println("MONTH " + companyMasterBean.getCompanyName() + " - ID - " + companyMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnit(){
		
		if(monthMasterBean.getMonthName()!=null && unitMasterBean.getUnitName()!=null && unitMasterBean.getUnitId()>0){
			RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		}
		if(runPayRollBeanList.size()>0){
			nextButtonVisibility = true;
		}else{
			nextButtonVisibility = false;
		}
		
		runPayRollBean.setTotalNumberOfDayseveryMonth(RunPayRollService.totnoOfDaysInMonth(month, year));
		
		System.out.println("NO OF DAYS MONTH VIEW MODEL " + runPayRollBean.getTotalNumberOfDayseveryMonth());
		
		int s = sunDayCheck();
		System.out.println("TOTtal no of sun deed " + s);
		
		int t = satDayCheck();
		System.out.println("TOTtal no of sat deed " + t);
		
		int u = monDayCheck();
		System.out.println("TOTtal no of mon deed " + u);
		
		int v = tuesDayCheck();
		System.out.println("TOTtal no of mon deed " + v);
		
		int w = generalHolidaysCheck();
		System.out.println("TOTtal no of general deed " + w);
		
		runPayRollBean.totalNumberOfHolidays = s+t+u+v+w;
		
		System.out.println("Total Number Of Holidays " + runPayRollBean.totalNumberOfHolidays);
		
		runPayRollBean.setTotalNumberOfWorkingDaysEveryMonth(runPayRollBean.getTotalNumberOfDayseveryMonth() - runPayRollBean.totalNumberOfHolidays);
		System.out.println("TOTAL WORKING DAYS " + runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth());
		
		
		//runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sunDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		//runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		//RunPayRollService.countHoliDaysPerMonth(runPayRollBean, month, year);
		
		
	}
	
	public int sunDayCheck(){
		int noOfSundayallocated;
		runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sunDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfSundayallocated = runPayRollBean.getSunDayCountPerMonth();
		System.out.println("no OF SUNDAY " + noOfSundayallocated);
		if(noOfSundayallocated==5){
			runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sundayDeduction(runPayRollBean, month, year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getSunDayCountPerMonth());
			System.out.println("Sunday Count 5 aMonth >>> >> > " + runPayRollBean.getSunDayCountPerMonth());
		}if(noOfSundayallocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfSundayallocated);
			System.out.println("SSSSUNDAY COUNT 4 >>> >> > " +runPayRollBean.getTotalNumberOfHolidayseveryMonth());
		}
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
		
	}
	
	public int satDayCheck(){
		int noOfSatDayAllocated;
		runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfSatDayAllocated = runPayRollBean.getSatDayCountPerMonth();
		System.out.println("no OF SATDAY " + noOfSatDayAllocated);
		if(noOfSatDayAllocated ==5){
			runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satdayDeduction(runPayRollBean, month, year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getSatDayCountPerMonth());
			System.out.println("SadDay Count 5 aMonth >>> >> > " + runPayRollBean.getSatDayCountPerMonth());
			
		}if(noOfSatDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfSatDayAllocated);
			System.out.println("SSSSSATDAY COUNT 4 >>> >> > " + runPayRollBean.getSatDayCountPerMonth());
		}
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
	}
	
	public int monDayCheck(){
		int noOfMonDayAllocated;
		runPayRollBean.setMonDayCountPerMonth(RunPayRollService.satDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfMonDayAllocated = runPayRollBean.getMonDayCountPerMonth();
		System.out.println("no OF MONDAY " + noOfMonDayAllocated);
		if(noOfMonDayAllocated == 5){
			runPayRollBean.setMonDayCountPerMonth(RunPayRollService.monDayDeduction(runPayRollBean, month, year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getMonDayCountPerMonth());
			System.out.println("MonDay Count 5 aMonth >>> >> > " + runPayRollBean.getMonDayCountPerMonth());
		}if(noOfMonDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfMonDayAllocated);
			System.out.println("SSSSMONDAY COUNT 4 >>> >> > " + runPayRollBean.getMonDayCountPerMonth());
		}
		
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
	}
	
	public int tuesDayCheck(){
		int noOfTuesDayAllocated;
		runPayRollBean.setTuesDayCountPerMonth(RunPayRollService.monDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfTuesDayAllocated= runPayRollBean.getTuesDayCountPerMonth();
		System.out.println("no OF TUESDAY " + noOfTuesDayAllocated);
		if(noOfTuesDayAllocated == 5){
			runPayRollBean.setTuesDayCountPerMonth(RunPayRollService.tuesDayDeduction(runPayRollBean, month, year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getTuesDayCountPerMonth());
			System.out.println("TuesDay Count 5 aMonth >>> >> > " + runPayRollBean.getTuesDayCountPerMonth());
		}if(noOfTuesDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfTuesDayAllocated);
			System.out.println("SSSSTUESDAY COUNT 4 >>> >> > " + runPayRollBean.getTuesDayCountPerMonth());
		}
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
	}
	
	
	
	
	
	public int generalHolidaysCheck(){
		int noOfHolidays;
		noOfHolidays = RunPayRollService.generalHolidayDeduction(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean, month);
		
		return noOfHolidays;
		
		
	}
	
	
	
	
	
	
	@Command
	@NotifyChange
	public void onClickAdjment(@BindingParam("bean") RunPayRollBean bean){
		Map<String, Object> parenMap = new HashMap<String, Object>();
		parenMap.put("parentList", bean);
		parenMap.put("companyName", companyMasterBean.getCompanyName());
		parenMap.put("unitName", unitMasterBean.getUnitName());
		parenMap.put("salaryMonth", monthMasterBean.getMonthName());
		parenMap.put("Year", year);
		
		
		Window window = (Window) Executions.createComponents("/WEB-INF/view/employeePaymentDetails.zul", null, parenMap);
		window.doModal();
	}
	
	@Command
	@NotifyChange("*")
	public void onclickConfirm() throws DocumentException, Exception{
		
		StringBuilder stringBuilder = new StringBuilder();
		for(RunPayRollBean rBean: runPayRollBeanList){
			
			if(rBean.isChecked()){
				
				ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				ArrayList<EmployeeSalaryComponentAmountBean> deductList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				
				
				stringBuilder.append("ID : " +rBean.getEmpId() +" : "+ rBean.getEmpName() +"\n");
				
				 for(EmployeeSalaryComponentAmountBean sBean : rBean.getComponentAmountBeanList()){
					 
					 if(sBean.getComponentType().equalsIgnoreCase("EARNING")){
						 earnList.add(sBean);
					 }if(sBean.getComponentType().equalsIgnoreCase("DEDUCTION")){
						 deductList.add(sBean);
					 }
			     }
				 
				 stringBuilder.append("EARNINGS "+ "\n");
				 for(EmployeeSalaryComponentAmountBean sBean : earnList){
					 stringBuilder.append("Components : "+ sBean.getComponentName() + " : " + sBean.getComponentAmount() + " : " +sBean.getComponentType() +"\n");
				 }
				 
				 stringBuilder.append(" "+ "\n");
				 stringBuilder.append("DEDUCTIONS "+ "\n");
				 
				 for(EmployeeSalaryComponentAmountBean sBean : deductList){
					 stringBuilder.append("Components : "+ sBean.getComponentName() + " : " + sBean.getComponentAmount() + " : " +sBean.getComponentType() +"\n");
					 }
				 stringBuilder.append("--------------------------------------------" +"\n");
				
				String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
				System.out.println("pdf_path >>> >> > " + pdfPath);
				PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
			    paySlipGenerator.getDetails(stringBuilder.toString(),pdfPath, runPayRollBeanList, runPayRollBean);
			}
			
			
		}
		/*String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		System.out.println("pdf_path >>> >> > " + pdfPath);
		PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
	    paySlipGenerator.getDetails(stringBuilder.toString(),pdfPath, runPayRollBeanList, runPayRollBean);*/
	
	}
	
	@Command
	@NotifyChange("*")
	public void onCheckAll(){
		
		if(runPayRollBeanList.size()>0){
			if(allChecked){
				for(RunPayRollBean bean : runPayRollBeanList){
					bean.setChecked(true);	
				}
			}else{
				for(RunPayRollBean bean : runPayRollBeanList){
					bean.setChecked(false);	
				}
			}
			
		}
		
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickNext(){
		salaryAdjustmentVisibility = false;
		salaryComponentVisibility = true;
		nextButtonVisibility = false;
		prevButtonVisibility = true;
	}
	
	@Command
	@NotifyChange("*")
	public void onClickPrevious(){
		salaryAdjustmentVisibility = true;
		salaryComponentVisibility = false;
		nextButtonVisibility = true;
		prevButtonVisibility = false;
	}
	
	
	
	
	
	
	
	
	
	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public RunPayRollBean getRunPayRollBean() {
		return runPayRollBean;
	}

	public void setRunPayRollBean(RunPayRollBean runPayRollBean) {
		this.runPayRollBean = runPayRollBean;
	}

	public ArrayList<CompanyMasterBean> getCompanyBeanList() {
		return companyBeanList;
	}

	public void setCompanyBeanList(ArrayList<CompanyMasterBean> companyBeanList) {
		this.companyBeanList = companyBeanList;
	}

	public ArrayList<UnitMasterBean> getUnitMasterBeanList() {
		return unitMasterBeanList;
	}

	public void setUnitMasterBeanList(ArrayList<UnitMasterBean> unitMasterBeanList) {
		this.unitMasterBeanList = unitMasterBeanList;
	}

	public ArrayList<MonthMasterBean> getMonthList() {
		return monthList;
	}

	public void setMonthList(ArrayList<MonthMasterBean> monthList) {
		this.monthList = monthList;
	}

	public CompanyMasterBean getCompanyMasterBean() {
		return companyMasterBean;
	}

	public void setCompanyMasterBean(CompanyMasterBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}

	public UnitMasterBean getUnitMasterBean() {
		return unitMasterBean;
	}

	public void setUnitMasterBean(UnitMasterBean unitMasterBean) {
		this.unitMasterBean = unitMasterBean;
	}

	public MonthMasterBean getMonthMasterBean() {
		return monthMasterBean;
	}

	public void setMonthMasterBean(MonthMasterBean monthMasterBean) {
		this.monthMasterBean = monthMasterBean;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public ArrayList<RunPayRollBean> getRunPayRollBeanList() {
		return runPayRollBeanList;
	}

	public void setRunPayRollBeanList(ArrayList<RunPayRollBean> runPayRollBeanList) {
		this.runPayRollBeanList = runPayRollBeanList;
	}

	public EmployeeSalaryComponentAmountBean getAmountBean() {
		return amountBean;
	}

	public void setAmountBean(EmployeeSalaryComponentAmountBean amountBean) {
		this.amountBean = amountBean;
	}

	public boolean isSalaryAdjustmentVisibility() {
		return salaryAdjustmentVisibility;
	}

	public void setSalaryAdjustmentVisibility(boolean salaryAdjustmentVisibility) {
		this.salaryAdjustmentVisibility = salaryAdjustmentVisibility;
	}

	public boolean isSalaryComponentVisibility() {
		return salaryComponentVisibility;
	}

	public void setSalaryComponentVisibility(boolean salaryComponentVisibility) {
		this.salaryComponentVisibility = salaryComponentVisibility;
	}

	public boolean isNextButtonVisibility() {
		return nextButtonVisibility;
	}

	public void setNextButtonVisibility(boolean nextButtonVisibility) {
		this.nextButtonVisibility = nextButtonVisibility;
	}

	public boolean isPrevButtonVisibility() {
		return prevButtonVisibility;
	}

	public void setPrevButtonVisibility(boolean prevButtonVisibility) {
		this.prevButtonVisibility = prevButtonVisibility;
	}

	public boolean isAllChecked() {
		return allChecked;
	}

	public void setAllChecked(boolean allChecked) {
		this.allChecked = allChecked;
	}

	public HolidayMasterBean getHolidayMasterBean() {
		return holidayMasterBean;
	}

	public void setHolidayMasterBean(HolidayMasterBean holidayMasterBean) {
		this.holidayMasterBean = holidayMasterBean;
	}

	
	
}
