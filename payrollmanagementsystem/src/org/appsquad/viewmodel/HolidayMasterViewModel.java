package org.appsquad.viewmodel;


import java.util.ArrayList;

import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.HolidayMasterGeneralHolidayBean;
import org.appsquad.bean.HolidayMasterWeekDayBean;
import org.appsquad.bean.Week;
import org.appsquad.dao.HolidayMasterDao;
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

public class HolidayMasterViewModel {
  
	private ArrayList<Week> weekList = new ArrayList<Week>();
	private ArrayList<HolidayMasterWeekDayBean> weekDayList = new ArrayList<HolidayMasterWeekDayBean>();
	private ArrayList<HolidayMasterBean> holidayMasterBeanList = new ArrayList<HolidayMasterBean>();
	private ArrayList<HolidayMasterGeneralHolidayBean> generalHoliDayBeanList = new ArrayList<HolidayMasterGeneralHolidayBean>();
	
	HolidayMasterBean holidayMasterBean = new HolidayMasterBean();
	HolidayMasterWeekDayBean dayBean = new HolidayMasterWeekDayBean();
	HolidayMasterGeneralHolidayBean generalHolidayBean = new HolidayMasterGeneralHolidayBean();
	Session session = null;
	String userName;
	
	
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
		
//		weekList.add(new Week(false, "1st week", "1"));
		
		holidayMasterBeanList = HolidayMasterService.loadWeeklyHolidayMasterData();
		generalHoliDayBeanList = HolidayMasterService.loadGenerealHoliDayList();
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
		for(Week week : weekList){
			if(week.isWeekChecked()){
				System.out.println("Week checked ID:: "+week.getWeekId()+" week nAme :: "+week.getWeekName());
				weekIdList.add(week.getWeekId());	
			}
		}
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
			
			if(str.length()>0){
				
				HolidayMasterService.saveHolidayMasterData(str,holidayMasterBean,userName);
				
				holidayMasterBeanList = HolidayMasterService.loadWeeklyHolidayMasterData();
				}else{
					
				Messagebox.show("Week Not Selected ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
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
	public void deleteWeekMasterData(@BindingParam("bean") HolidayMasterBean bean){
		HolidayMasterService.deleteHolidayMasterData(bean);
		holidayMasterBeanList = HolidayMasterService.loadWeeklyHolidayMasterData();
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSaveGeneralHday(){
		if(holidayMasterBean.getLeaveYrId()>0){
			HolidayMasterService.saveGeneralHoliDayMasterData(generalHolidayBean, generalHolidayBean.getGeneralHolidayDate(), 
															  generalHolidayBean.getGeneralHolidayName(), userName, holidayMasterBean.getLeaveYrId());
			generalHoliDayBeanList = HolidayMasterService.loadGenerealHoliDayList();
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onClickDeleteGeneralHoliday(@BindingParam("bean") HolidayMasterGeneralHolidayBean bean){
	
		HolidayMasterService.deleteGeneralHoliDayMasterData(bean);
		generalHoliDayBeanList = HolidayMasterService.loadGenerealHoliDayList();
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

	
	
	
}
