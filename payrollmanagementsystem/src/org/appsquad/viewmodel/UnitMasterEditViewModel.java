package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.UnitMasterBean;
import org.appsquad.service.UnitMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

public class UnitMasterEditViewModel {

	Session session = null;
	private String userName;
	
	public UnitMasterBean unitMasterBean = new UnitMasterBean();
	public UnitMasterBean baseDaysTypeBean = new UnitMasterBean();
	public UnitMasterBean wagesTypeBean = new UnitMasterBean();
	public UnitMasterBean sundayTypeBean = new UnitMasterBean();
	
	private ArrayList<UnitMasterBean> baseDaysList = new ArrayList<UnitMasterBean>();
	private ArrayList<UnitMasterBean> wagesTypeBeanList = new ArrayList<UnitMasterBean>();
	private ArrayList<UnitMasterBean> sunDaySelecttionBnLst = new ArrayList<UnitMasterBean>();
	
	
	
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
						@ExecutionArgParam("bean") UnitMasterBean bean,
						@ExecutionArgParam("basedaysList") ArrayList<UnitMasterBean> pBaseDaysList,
						@ExecutionArgParam("wagesList") ArrayList<UnitMasterBean> pwagesTypeBeanList, 
						@ExecutionArgParam("sundaylist") ArrayList<UnitMasterBean> psunDaySelecttionBnLst) throws Exception{
		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		
		System.out.println("unit Id " + bean.getUnitId());
		
		System.out.println("Base day type " + bean.getBaseDaysType());
		
		System.out.println("Wages Type " + bean.getWagesType());
		
		System.out.println("Sun day sel " + bean.getSundaySelection());
		
		System.out.println("Working hour " + bean.getWorkingHour());
		
		
		unitMasterBean = bean;
		baseDaysList = pBaseDaysList;
		wagesTypeBeanList = pwagesTypeBeanList;
		sunDaySelecttionBnLst = psunDaySelecttionBnLst;
		//System.out.println("11 " + baseDaysList.size());
		//System.out.println("11 " + wagesTypeBeanList.size());
		System.out.println("11 " + sunDaySelecttionBnLst.size());
		
	}

	@Command
	@NotifyChange("*")
	public void onSelectBasedaysType(){
		unitMasterBean.setBaseDaysTypeId(baseDaysTypeBean.getBaseDaysTypeId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectWages(){
		unitMasterBean.setWagesTypeId(wagesTypeBean.getWagesTypeId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSundaySelect(){
		unitMasterBean.setSundaySelectionId(sundayTypeBean.getSundaySelectionId());
	}
	
	@Command
	@NotifyChange("*")
	public void onClickUpdate(){
		int i = 0;
		i= UnitMasterService.upDateUnit(unitMasterBean);
		if(i>0){
			Messagebox.show("Updated Successfully", "Information", Messagebox.OK,Messagebox.INFORMATION);
		}else {
			Messagebox.show("Updated Successfully!", "Information", Messagebox.OK,Messagebox.INFORMATION);
		}
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


	public UnitMasterBean getUnitMasterBean() {
		return unitMasterBean;
	}


	public void setUnitMasterBean(UnitMasterBean unitMasterBean) {
		this.unitMasterBean = unitMasterBean;
	}


	public UnitMasterBean getBaseDaysTypeBean() {
		return baseDaysTypeBean;
	}


	public void setBaseDaysTypeBean(UnitMasterBean baseDaysTypeBean) {
		this.baseDaysTypeBean = baseDaysTypeBean;
	}


	public UnitMasterBean getWagesTypeBean() {
		return wagesTypeBean;
	}


	public void setWagesTypeBean(UnitMasterBean wagesTypeBean) {
		this.wagesTypeBean = wagesTypeBean;
	}


	public UnitMasterBean getSundayTypeBean() {
		return sundayTypeBean;
	}


	public void setSundayTypeBean(UnitMasterBean sundayTypeBean) {
		this.sundayTypeBean = sundayTypeBean;
	}


	public ArrayList<UnitMasterBean> getBaseDaysList() {
		return baseDaysList;
	}


	public void setBaseDaysList(ArrayList<UnitMasterBean> baseDaysList) {
		this.baseDaysList = baseDaysList;
	}


	public ArrayList<UnitMasterBean> getWagesTypeBeanList() {
		return wagesTypeBeanList;
	}


	public void setWagesTypeBeanList(ArrayList<UnitMasterBean> wagesTypeBeanList) {
		this.wagesTypeBeanList = wagesTypeBeanList;
	}


	public ArrayList<UnitMasterBean> getSunDaySelecttionBnLst() {
		return sunDaySelecttionBnLst;
	}


	public void setSunDaySelecttionBnLst(
			ArrayList<UnitMasterBean> sunDaySelecttionBnLst) {
		this.sunDaySelecttionBnLst = sunDaySelecttionBnLst;
	}
	
}
