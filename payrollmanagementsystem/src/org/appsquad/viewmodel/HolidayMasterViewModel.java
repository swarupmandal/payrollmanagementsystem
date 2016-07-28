package org.appsquad.viewmodel;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.HolidayMasterGeneralHolidayBean;
import org.appsquad.bean.HolidayMasterWeekDayBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.bean.Week;
import org.appsquad.dao.HolidayMasterDao;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.HolidayMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import utility.DateFormatter;

public class HolidayMasterViewModel {
  
	private ArrayList<Week> weekList = new ArrayList<Week>();
	private ArrayList<HolidayMasterWeekDayBean> weekDayList = new ArrayList<HolidayMasterWeekDayBean>();
	private ArrayList<HolidayMasterBean> holidayMasterBeanList = new ArrayList<HolidayMasterBean>();
	private ArrayList<HolidayMasterGeneralHolidayBean> generalHoliDayBeanList = new ArrayList<HolidayMasterGeneralHolidayBean>();
	public ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	public ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	
	HolidayMasterBean holidayMasterBean = new HolidayMasterBean();
	HolidayMasterWeekDayBean dayBean = new HolidayMasterWeekDayBean();
	HolidayMasterGeneralHolidayBean generalHolidayBean = new HolidayMasterGeneralHolidayBean();
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	private UnitMasterBean unitMasterBean = new UnitMasterBean();
	Session session = null;
	String userName;
	private String currentDateValue;
	private Date currentDate;
	private Date utilstartDate;
	private Date utilEndDate;
	
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		
		weekDayList = HolidayMasterDao.loadWeekDayData();
		
		weekList.add(new Week(false, "1st week", "1"));
		weekList.add(new Week(false, "2nd Week", "2"));
		weekList.add(new Week(false, "3rd week", "3"));
		weekList.add(new Week(false, "4th Week", "4"));
		weekList.add(new Week(false, "5th week", "5"));
		
		currentDate = DateFormatter.date();
		
		System.out.println("Current " + currentDate);
		
		
		
		loadLeaveYrDate();
		
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		
		EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
	}
	

	public void loadLeaveYrDate(){
		HolidayMasterService.loadLeaveYr(holidayMasterBean);
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectCompanyName(){
		
		System.out.println("selected company name >>> >> > " + companyMasterBean.getCompanyName());
		System.out.println("Selected company ID >>> >> > " + companyMasterBean.getCompanyId());
		//employeeMasterBean.setCompanyId(companyMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnit(){
		if(unitMasterBean.getUnitId()>0){
			
			holidayMasterBeanList = HolidayMasterService.loadWeeklyHolidayMasterData(holidayMasterBean.getLeaveYrId(), companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
			generalHoliDayBeanList = HolidayMasterService.loadGenerealHoliDayList(holidayMasterBean.getLeaveYrId(), companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
			
		}
		
		
	}
	
	
	
	
	
	@Command
	@NotifyChange("*")
	public void onClickSaveLeaveYr(){
		
		int id = HolidayMasterService.saveLeaveYear(holidayMasterBean, userName);
		if(id>0){
		holidayMasterBean.setLeaveYrId(id);
		Messagebox.show("Saved Succesfully ", "Information", Messagebox.OK,Messagebox.INFORMATION );
		}
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectDay(){
		holidayMasterBean.setWeeklyHoliDayId(dayBean.getWeeklyHoliDayId());
		holidayMasterBean.setWeeklyHoliDayName(dayBean.getWeeklyHoliDayName());
		
	}
	
	@Command
	@NotifyChange
	public void onClickSaveWeek(){
		ArrayList<String> weekIdList = new ArrayList<String>();
		int noOfDays;
		for(Week week : weekList){
			if(week.isWeekChecked()){
				System.out.println("Week checked ID:: "+week.getWeekId()+" week nAme :: "+week.getWeekName());
				weekIdList.add(week.getWeekId());	
			}
		}
		
		noOfDays = weekIdList.size();
		String str ="";
			if(weekIdList.size()!=0){
			StringBuilder builder = new StringBuilder();
			String temp = weekIdList.toString();
			String fb = temp.replace("[", "");
			String bb =  fb.replace("]", "");
			builder.append(bb);
			str = builder.toString();
			str = str.trim();
		}
		
		if(holidayMasterBean.getLeaveYrId()>0){
			
			if(dayBean.getWeeklyHoliDayName()!=null){
				
				if(companyMasterBean.getCompanyId()>0){
				
					if(unitMasterBean.getUnitId()>0){
					
						if(str.length()>0){
						
							HolidayMasterService.saveHolidayMasterData(noOfDays,str,holidayMasterBean,userName, companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
						
							holidayMasterBeanList = HolidayMasterService.loadWeeklyHolidayMasterData(holidayMasterBean.getLeaveYrId(), companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
						
					     }else{
					
					   Messagebox.show("Week Not Selected ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
					     
					  }
					
					}else {
						Messagebox.show("Select Unit ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
					}
			
				}else {
					Messagebox.show("Select Company Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			}
				
			}else {
				Messagebox.show("Select Day ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			
			}else {
			Messagebox.show("Select Leave Year ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		
	}

	@Command
	@NotifyChange("*")
	public void onOkHourPerDay(){
		if(holidayMasterBean.getLeaveYrId()>0){
			 
			if(companyMasterBean.getCompanyId()>0){
			    
				if(unitMasterBean.getUnitId()>0){
			    	
					if(holidayMasterBean.getHourPerDay() != null){
			    		
			    		HolidayMasterService.saveHourPerDay(holidayMasterBean, userName, companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
			    	 
			    	}else {
			    		 Messagebox.show("Eneter Working Hours Per Day ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			    	 }
			    }else {
			    	Messagebox.show("Select Unit ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);	
				}
			 }else {
				 Messagebox.show("Select Company Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);	
			}
		}else {
			Messagebox.show("First save Leave Year ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	
	@Command
	@NotifyChange("*")
	public void deleteWeekMasterData(@BindingParam("bean") HolidayMasterBean bean){
		//HolidayMasterService.deleteHolidayMasterData(bean);
		//holidayMasterBeanList = HolidayMasterService.loadWeeklyHolidayMasterData();
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSaveGeneralHday(){
		if(holidayMasterBean.getLeaveYrId()>0){
			HolidayMasterService.saveGeneralHoliDayMasterData(generalHolidayBean, generalHolidayBean.getGeneralHolidayDate(), 
															  generalHolidayBean.getGeneralHolidayName(), userName, holidayMasterBean.getLeaveYrId(), companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
			generalHoliDayBeanList = HolidayMasterService.loadGenerealHoliDayList(holidayMasterBean.getLeaveYrId(), companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onClickDeleteGeneralHoliday(@BindingParam("bean") HolidayMasterGeneralHolidayBean bean){
	
		HolidayMasterService.deleteGeneralHoliDayMasterData(bean);
		generalHoliDayBeanList = HolidayMasterService.loadGenerealHoliDayList(holidayMasterBean.getLeaveYrId(), companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
	}
	
	@Command
	@NotifyChange("*")
	public void onChangeDate(){
		
		java.sql.Date str = generalHolidayBean.getGeneralHolidayDate();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String dddd = df.format(str);
		
		String[]  parts= dddd.split("/");
		String parts1 = parts[0];
		String s = parts1;
		s = parts1.replaceFirst ("^0*", "");
		int i = Integer.parseInt(s);
		
		generalHolidayBean.setMonthId(i);
		//System.out.println("MMMMMMMMMMM MONTH ID >>> >> > " + generalHolidayBean.getMonthId());
		
		//System.out.println("MMM MM M " + s);
		
	}
	
	
	
	public ArrayList<Week> getWeekList() {
		return weekList;
	}

	public void setWeekList(ArrayList<Week> weekList) {
		this.weekList = weekList;
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

	public HolidayMasterBean getHolidayMasterBean() {
		return holidayMasterBean;
	}

	public void setHolidayMasterBean(HolidayMasterBean holidayMasterBean) {
		this.holidayMasterBean = holidayMasterBean;
	}

	public ArrayList<HolidayMasterWeekDayBean> getWeekDayList() {
		return weekDayList;
	}

	public void setWeekDayList(ArrayList<HolidayMasterWeekDayBean> weekDayList) {
		this.weekDayList = weekDayList;
	}

	public HolidayMasterWeekDayBean getDayBean() {
		return dayBean;
	}

	public void setDayBean(HolidayMasterWeekDayBean dayBean) {
		this.dayBean = dayBean;
	}

	public ArrayList<HolidayMasterBean> getHolidayMasterBeanList() {
		return holidayMasterBeanList;
	}

	public void setHolidayMasterBeanList(
			ArrayList<HolidayMasterBean> holidayMasterBeanList) {
		this.holidayMasterBeanList = holidayMasterBeanList;
	}

	public ArrayList<HolidayMasterGeneralHolidayBean> getGeneralHoliDayBeanList() {
		return generalHoliDayBeanList;
	}

	public void setGeneralHoliDayBeanList(
			ArrayList<HolidayMasterGeneralHolidayBean> generalHoliDayBeanList) {
		this.generalHoliDayBeanList = generalHoliDayBeanList;
	}

	public HolidayMasterGeneralHolidayBean getGeneralHolidayBean() {
		return generalHolidayBean;
	}

	public void setGeneralHolidayBean(
			HolidayMasterGeneralHolidayBean generalHolidayBean) {
		this.generalHolidayBean = generalHolidayBean;
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

	
	
	
}
