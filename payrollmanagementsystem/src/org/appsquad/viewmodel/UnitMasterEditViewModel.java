package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.UnitMasterBean;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

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
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) throws Exception{
		Selectors.wireComponents(view, this, false);
	
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		
		
		
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
