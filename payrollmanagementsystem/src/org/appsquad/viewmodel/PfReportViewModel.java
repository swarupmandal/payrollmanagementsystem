package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EsiReportBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.PfReportBean;
import org.appsquad.bean.UnitDesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.EmployeeDao;
import org.appsquad.dao.PfReportDao;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.EsiReportService;
import org.appsquad.service.PfReportService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class PfReportViewModel {

	Session session = null;
	private String userId;
	
	
	public CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	public UnitMasterBean unitMasterBean = new UnitMasterBean();
	public PfReportBean pfReportBean = new PfReportBean();
	public MonthMasterBean monthBean = new MonthMasterBean();
	public EsiReportBean unitDesBean = new EsiReportBean();
	public UnitDesignationBean UnitDesignationBean = new UnitDesignationBean();
	public PfReportBean pfParameterBean = new PfReportBean();
	
	
	ArrayList<String> lvYrList = new ArrayList<String>();
	private ArrayList<MonthMasterBean> monthList = new ArrayList<MonthMasterBean>();
	private ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	private ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	public ArrayList<UnitDesignationBean> unitDesignationList = new ArrayList<UnitDesignationBean>();
	private ArrayList<PfReportBean> pfReportBeanList = new ArrayList<PfReportBean>();
	
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception {

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userId = (String) session.getAttribute("userId");
		lvYrList = EsiReportService.getLvYr();
		
		
	}

	@Command
	@NotifyChange("*")
	public void onSelectYr(){
		pfParameterBean.setLvYr(pfReportBean.getLvYr());
		RunPayRollDao.loadMonthList(monthList);
	}
	
	
	@Command
	@NotifyChange("*")
	public void onSelectMonth(){
		pfReportBean.setSalaryMonth(monthBean.getMonthName());
		pfReportBean.setSalaryMonthId(monthBean.getMonthId());
		if(companyBeanList.size()>0){
			companyBeanList.clear();
		}
		pfParameterBean.setSalaryMonth(monthBean.getMonthName());
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectCompany(){
		pfReportBean.setCompanyId(companyMasterBean.getCompanyId());
		
		pfParameterBean.setCompanyId(companyMasterBean.getCompanyId());
		unitMasterBeanList = EmployeeMasterService.loadUnitBeanListWrtCompany(companyMasterBean.getCompanyId());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnit(){
		pfReportBean.setUnitId(unitMasterBean.getUnitId());
		
		pfParameterBean.setUnitId(unitMasterBean.getUnitId());
		unitDesignationList = EmployeeDao.loadUnitDesignationList(pfReportBean.getCompanyId(), pfReportBean.getUnitId());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitDesignation(){
		
		pfParameterBean.setUnitDesignationId(UnitDesignationBean.getUnitDesignationId());
		pfReportBeanList = PfReportDao.getEsiReport(pfParameterBean);
		
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickCSV(){
		PfReportService.printCSV(pfReportBeanList);
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



	public PfReportBean getPfReportBean() {
		return pfReportBean;
	}



	public void setPfReportBean(PfReportBean pfReportBean) {
		this.pfReportBean = pfReportBean;
	}



	public MonthMasterBean getMonthBean() {
		return monthBean;
	}



	public void setMonthBean(MonthMasterBean monthBean) {
		this.monthBean = monthBean;
	}



	public EsiReportBean getUnitDesBean() {
		return unitDesBean;
	}



	public void setUnitDesBean(EsiReportBean unitDesBean) {
		this.unitDesBean = unitDesBean;
	}



	public UnitDesignationBean getUnitDesignationBean() {
		return UnitDesignationBean;
	}



	public void setUnitDesignationBean(UnitDesignationBean unitDesignationBean) {
		UnitDesignationBean = unitDesignationBean;
	}



	public PfReportBean getPfParameterBean() {
		return pfParameterBean;
	}



	public void setPfParameterBean(PfReportBean pfParameterBean) {
		this.pfParameterBean = pfParameterBean;
	}



	public ArrayList<String> getLvYrList() {
		return lvYrList;
	}



	public void setLvYrList(ArrayList<String> lvYrList) {
		this.lvYrList = lvYrList;
	}



	public ArrayList<MonthMasterBean> getMonthList() {
		return monthList;
	}



	public void setMonthList(ArrayList<MonthMasterBean> monthList) {
		this.monthList = monthList;
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



	public ArrayList<UnitDesignationBean> getUnitDesignationList() {
		return unitDesignationList;
	}



	public void setUnitDesignationList(
			ArrayList<UnitDesignationBean> unitDesignationList) {
		this.unitDesignationList = unitDesignationList;
	}



	public ArrayList<PfReportBean> getPfReportBeanList() {
		return pfReportBeanList;
	}



	public void setPfReportBeanList(ArrayList<PfReportBean> pfReportBeanList) {
		this.pfReportBeanList = pfReportBeanList;
	}

	
	
}
