package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EsiReportBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.UnitDesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.EmployeeDao;
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
	public EsiReportBean unitDesBean = new EsiReportBean();
	public UnitDesignationBean UnitDesignationBean = new UnitDesignationBean();
	public EsiReportBean esiParameterBean = new EsiReportBean();
	
	
	ArrayList<String> lvYrList = new ArrayList<String>();
	private ArrayList<MonthMasterBean> monthList = new ArrayList<MonthMasterBean>();
	private ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	private ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	public ArrayList<UnitDesignationBean> unitDesignationList = new ArrayList<UnitDesignationBean>();
	private ArrayList<EsiReportBean> esiReportBeanList = new ArrayList<EsiReportBean>();
	
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
		esiParameterBean.setLvYr(esiReportBean.getLvYr());
		RunPayRollDao.loadMonthList(monthList);
	}
	
	
	@Command
	@NotifyChange("*")
	public void onSelectMonth(){
		esiReportBean.setSalaryMonth(monthBean.getMonthName());
		esiReportBean.setSalaryMonthId(monthBean.getMonthId());
		if(companyBeanList.size()>0){
			companyBeanList.clear();
		}
		esiParameterBean.setSalaryMonth(monthBean.getMonthName());
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectCompany(){
		esiReportBean.setCompanyId(companyMasterBean.getCompanyId());
		
		esiParameterBean.setCompanyId(companyMasterBean.getCompanyId());
		unitMasterBeanList = EmployeeMasterService.loadUnitBeanListWrtCompany(companyMasterBean.getCompanyId());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnit(){
		esiReportBean.setUnitId(unitMasterBean.getUnitId());
		
		esiParameterBean.setUnitId(unitMasterBean.getUnitId());
		unitDesignationList = EmployeeDao.loadUnitDesignationList(esiReportBean.getCompanyId(), esiReportBean.getUnitId());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitDesignation(){
		
		esiParameterBean.setUnitDesignationId(UnitDesignationBean.getUnitDesignationId());
		esiReportBeanList = EsiReportService.getEsiReport(esiParameterBean);
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickCSV(){
		EsiReportService.printCSV(esiReportBeanList);
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

	/*public ArrayList<EsiReportBean> getUnitDesignationBeanList() {
		return unitDesignationBeanList;
	}

	public void setUnitDesignationBeanList(
			ArrayList<EsiReportBean> unitDesignationBeanList) {
		this.unitDesignationBeanList = unitDesignationBeanList;
	}*/

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

	public ArrayList<UnitDesignationBean> getUnitDesignationList() {
		return unitDesignationList;
	}

	public void setUnitDesignationList(
			ArrayList<UnitDesignationBean> unitDesignationList) {
		this.unitDesignationList = unitDesignationList;
	}

	public ArrayList<EsiReportBean> getEsiReportBeanList() {
		return esiReportBeanList;
	}

	public void setEsiReportBeanList(ArrayList<EsiReportBean> esiReportBeanList) {
		this.esiReportBeanList = esiReportBeanList;
	}

	public EsiReportBean getEsiParameterBean() {
		return esiParameterBean;
	}

	public void setEsiParameterBean(EsiReportBean esiParameterBean) {
		this.esiParameterBean = esiParameterBean;
	}

	/*public UnitMasterBean getUnitDesBean() {
		return unitDesBean;
	}

	public void setUnitDesBean(UnitMasterBean unitDesBean) {
		this.unitDesBean = unitDesBean;
	}*/
	
	
}
