package org.appsquad.viewmodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.service.EmployeeMasterService;
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
import org.zkoss.zul.Window;

public class RunPayrollViewModel {
	
	RunPayRollBean runPayRollBean = new RunPayRollBean();
	
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	private UnitMasterBean unitMasterBean = new UnitMasterBean();
	private MonthMasterBean monthMasterBean = new MonthMasterBean();
	private EmployeeSalaryComponentAmountBean amountBean = new EmployeeSalaryComponentAmountBean();
	
	
	
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
	}

	public void dateFormat(){
		GregorianCalendar date = new GregorianCalendar();
		  day = date.get(Calendar.DAY_OF_MONTH);
	      month = date.get(Calendar.MONTH);
	      year = date.get(Calendar.YEAR);
	      
	      second = date.get(Calendar.SECOND);
	      minute = date.get(Calendar.MINUTE);
	      hour = date.get(Calendar.HOUR);
	       
	      currentDate =day+"-"+month+"-"+year;
	      currentTime = hour+" : "+minute+" : "+second;
	      //runPayRollBean.setEmpName("SWARUP");
	      //runPayRollBean.setEmpCode("ASDF/1234");
	      System.out.println("current date "+ currentDate +" - " + currentTime);
	      
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
	public void onclickConfirm(){
		/*for(RunPayRollBean rBean: runPayRollBeanList){
			String comp = null;
			double cAmount = 0;
			String cType = null;
			
			ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
			ArrayList<EmployeeSalaryComponentAmountBean> deductList = new ArrayList<EmployeeSalaryComponentAmountBean>();
			
			System.out.println("ID :: " +rBean.getEmpId() +" :: "+ rBean.getEmpName());
			 for(EmployeeSalaryComponentAmountBean sBean : rBean.getComponentAmountBeanList()){
				  
				 comp = sBean.getComponentName();
				 cAmount = sBean.getComponentAmount();
				 cType = sBean.getComponentType();
				 
				 if(cType.equalsIgnoreCase("EARNING")){
					 
					 earnList.add(sBean);
				 }if(cType.equalsIgnoreCase("DEDUCTION")){
					 
					 deductList.add(sBean);
				 }
				 
			 }
			 System.out.println("EARNINGS ");
			 for(EmployeeSalaryComponentAmountBean sBean : earnList){
				 comp = sBean.getComponentName();
				 cAmount = sBean.getComponentAmount();
				 cType = sBean.getComponentType();
				 System.out.println("Components :: "+ comp + " :: " + cAmount + " :: " +cType);
			 }
			 System.out.println();
			 System.out.println("DEDUCTIONS ");
			 for(EmployeeSalaryComponentAmountBean sBean : deductList){
				 comp = sBean.getComponentName();
				 cAmount = sBean.getComponentAmount();
				 cType = sBean.getComponentType();
			     System.out.println("Components :: "+ comp + " :: " + cAmount + " :: " +cType);
			 }
			 
			 System.out.println("------------------------------------------------------------------------");
			
		}*/
		
		
		
		
		
		
		
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

	
	
}
