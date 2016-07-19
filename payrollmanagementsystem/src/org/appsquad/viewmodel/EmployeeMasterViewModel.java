package org.appsquad.viewmodel;

import java.sql.Connection;
import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.EmployeeMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.service.EmployeeMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

public class EmployeeMasterViewModel {
	
	public EmployeeMasterBean employeeMasterBean = new EmployeeMasterBean();
	public ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	public ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	public ArrayList<EmployeeMasterBean> employeeMasterBeanList = new ArrayList<EmployeeMasterBean>();
	public CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	public UnitMasterBean unitMasterBean = new UnitMasterBean();
	public static final int maxEmpId =0;

	private Connection connection = null;
	private Session session = null;
	private String userName;
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view)throws Exception{
		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		userName =  (String) session.getAttribute("userId");
		
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
	}
	
	
	
	@Command
	@NotifyChange("*")
	public void saveEmpInfo(){
		if(EmployeeMasterService.isValid(companyMasterBean, unitMasterBean, userName)){
		
			if(EmployeeMasterService.insertEmployeeInformation(employeeMasterBean, userName)){
				
				Messagebox.show("Saved successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
			}
			
			
		}
		
	}

	@Command
	@NotifyChange("*")
	public void onSelectCompanyName(){
		System.out.println("selected company name >>> >> > " + companyMasterBean.getCompanyName());
		System.out.println("Selected company ID >>> >> > " + companyMasterBean.getCompanyId());
		employeeMasterBean.setCompanyId(companyMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitName(){
		System.out.println("selected unit name >>> >> > " + unitMasterBean.getUnitName());
		System.out.println("Selected unit id >>> >> > " + unitMasterBean.getUnitId());
		employeeMasterBean.setUnitId(unitMasterBean.getUnitId());
	}
	


	public EmployeeMasterBean getEmployeeMasterBean() {
		return employeeMasterBean;
	}



	public void setEmployeeMasterBean(EmployeeMasterBean employeeMasterBean) {
		this.employeeMasterBean = employeeMasterBean;
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



	public ArrayList<EmployeeMasterBean> getEmployeeMasterBeanList() {
		return employeeMasterBeanList;
	}



	public void setEmployeeMasterBeanList(
			ArrayList<EmployeeMasterBean> employeeMasterBeanList) {
		this.employeeMasterBeanList = employeeMasterBeanList;
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



	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
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
	
}
