package org.appsquad.viewmodel;

import java.sql.Connection;
import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.service.ComponentMasterService;
import org.appsquad.service.ComponentPerUnitMasterService;
import org.appsquad.service.EmployeeMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.annotation.ComponentAnnotation;
import org.zkoss.zk.ui.select.Selectors;

public class ComponentMasterPerUnitViewModel {
	
	ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	ArrayList<ComponentPerUnitMasterBean> componentPerUnitMasterBeanList = new ArrayList<ComponentPerUnitMasterBean>();
	CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	UnitMasterBean unitMasterBean = new UnitMasterBean();
	ComponentPerUnitMasterBean componentPerUnitMasterBean = new ComponentPerUnitMasterBean();
	
	private Connection connection = null;
	Session session = null;
	private String userName;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		componentPerUnitMasterBeanList = ComponentPerUnitMasterService.loadData();
		
		//EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
		
		
	}

	@Command
	@NotifyChange("*")
	public void onSelectCompanyName(){
		componentPerUnitMasterBean.setCompanyId(companyMasterBean.getCompanyId());
		
		System.out.println(componentPerUnitMasterBean.getCompanyId());
		
		unitMasterBeanList = ComponentPerUnitMasterService.loadUnitBeanList(componentPerUnitMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitName(){
		componentPerUnitMasterBean.setUnitId(unitMasterBean.getUnitId());
		System.out.println(componentPerUnitMasterBean.getUnitId());
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickTab1(){
		System.out.println("TAB1 SELECTED");
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickTab2(){
		System.out.println("TAB2 SELECTED");
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
	if(ComponentPerUnitMasterService.isEmptyLocationField(componentPerUnitMasterBean)){
		
		ComponentPerUnitMasterService.saveComponentPerUnit(componentPerUnitMasterBeanList, componentPerUnitMasterBean.getCompanyId(), componentPerUnitMasterBean.getUnitId(), userName);
		System.out.println("SAVED " + companyMasterBean);
		
		
	    }
		
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

	public ArrayList<ComponentPerUnitMasterBean> getComponentPerUnitMasterBeanList() {
		return componentPerUnitMasterBeanList;
	}

	public void setComponentPerUnitMasterBeanList(
			ArrayList<ComponentPerUnitMasterBean> componentPerUnitMasterBeanList) {
		this.componentPerUnitMasterBeanList = componentPerUnitMasterBeanList;
	}

	public ComponentPerUnitMasterBean getComponentPerUnitMasterBean() {
		return componentPerUnitMasterBean;
	}

	public void setComponentPerUnitMasterBean(
			ComponentPerUnitMasterBean componentPerUnitMasterBean) {
		this.componentPerUnitMasterBean = componentPerUnitMasterBean;
	}

}
