package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EsiReportBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.UnitDesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.EsiReportService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class EsiReportViewModel {

	Session session = null;
	private String userId;
	
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	private UnitMasterBean unitMasterBean = new UnitMasterBean();
	EsiReportBean esiReportBean = new EsiReportBean();
	MonthMasterBean monthBean = new MonthMasterBean();
	public UnitDesignationBean unitDesignationBean = new UnitDesignationBean();
	
	
	ArrayList<String> lvYrList = new ArrayList<String>();
	private ArrayList<MonthMasterBean> monthList = new ArrayList<MonthMasterBean>();
	private ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	private ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	public ArrayList<UnitDesignationBean> unitDesignationBeanList = new ArrayList<UnitDesignationBean>();
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception {

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userId = (String) session.getAttribute("userId");
		
		lvYrList = EsiReportService.getLvYr();
		RunPayRollDao.loadMonthList(monthList);
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
		
		
	}

	@Command
	@NotifyChange
	public void onSelectYr(){
		
	}
	
	
	@Command
	@NotifyChange
	public void onSelectMonth(){
		esiReportBean.setSalaryMonth(monthBean.getMonthName());
		esiReportBean.setSalaryMonthId(monthBean.getMonthId());
		
	}
	
	@Command
	@NotifyChange
	public void onSelectCompany(){
		esiReportBean.setCompanyId(companyMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange
	public void onSelectUnit(){
		esiReportBean.setUnitId(unitMasterBean.getUnitId());
		unitDesignationBeanList = EmployeeMasterService.loadUnitDesignation(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		
		for(UnitDesignationBean bean : unitDesignationBeanList){
			System.out.println(bean.getUnitDesignation());
		}
	}
	
	@Command
	@NotifyChange
	public void onSelectUnitDesignation(){
		esiReportBean.setUnitDesignationId(unitDesignationBean.getUnitDesignationId());
		System.out.println("--- " + esiReportBean.getUnitDesignationId());
	}
	
	public ArrayList<String> getLvYrList() {
		return lvYrList;
	}

	public void setLvYrList(ArrayList<String> lvYrList) {
		this.lvYrList = lvYrList;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public EsiReportBean getEsiReportBean() {
		return esiReportBean;
	}

	public void setEsiReportBean(EsiReportBean esiReportBean) {
		this.esiReportBean = esiReportBean;
	}

	public MonthMasterBean getMonthBean() {
		return monthBean;
	}

	public void setMonthBean(MonthMasterBean monthBean) {
		this.monthBean = monthBean;
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

	public UnitDesignationBean getUnitDesignationBean() {
		return unitDesignationBean;
	}

	public void setUnitDesignationBean(UnitDesignationBean unitDesignationBean) {
		this.unitDesignationBean = unitDesignationBean;
	}

	public ArrayList<UnitDesignationBean> getUnitDesignationBeanList() {
		return unitDesignationBeanList;
	}

	public void setUnitDesignationBeanList(
			ArrayList<UnitDesignationBean> unitDesignationBeanList) {
		this.unitDesignationBeanList = unitDesignationBeanList;
	}
	
}
