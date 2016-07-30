package org.appsquad.viewmodel;

import java.sql.Connection;
import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.bean.DesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.service.ComponentMasterService;
import org.appsquad.service.ComponentPerUnitMasterService;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.HolidayMasterService;
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
	private ArrayList<DesignationBean> designationBeanList = new ArrayList<DesignationBean>();
	
	CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	UnitMasterBean unitMasterBean = new UnitMasterBean();
	ComponentPerUnitMasterBean componentPerUnitMasterBean = new ComponentPerUnitMasterBean();
	private DesignationBean designationBean = new DesignationBean();
	
	
	private Connection connection = null;
	Session session = null;
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		componentPerUnitMasterBean.userName = (String) session.getAttribute("userId");
		
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		EmployeeMasterService.loadDesignationList(designationBeanList);
		componentPerUnitMasterBeanList = ComponentPerUnitMasterService.loadData();
		
		
		
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
	public void onSelectDesignation(){
		
		componentPerUnitMasterBean.setUnitDesignationId(designationBean.getDesignationId());
		System.out.println(componentPerUnitMasterBean.getUnitDesignationId());
		
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
	
	/*@Command
	@NotifyChange("*")
	public void onClickSave(){
	if(ComponentPerUnitMasterService.isEmptyLocationField(componentPerUnitMasterBean)){
		if(componentPerUnitMasterBean.getWorkinghour() != null){
			ComponentPerUnitMasterService.saveHourPerDesignation(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(),designationBean.getDesignationId(), componentPerUnitMasterBean.getWorkinghour(), userName);
		}
		if(componentPerUnitMasterBean.getBaseDays()>0){
			ComponentPerUnitMasterService.saveBaseDaysPerUnit(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(),componentPerUnitMasterBean.getBaseDays(), componentPerUnitMasterBean.getWorkinghour(), userName);
		}
		ComponentPerUnitMasterService.saveComponentPerUnit(componentPerUnitMasterBeanList, componentPerUnitMasterBean.getCompanyId(), componentPerUnitMasterBean.getUnitId(), userName, designationBean.getDesignationId());
		
	    }
		
	}*/
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
		
		if(ComponentPerUnitMasterService.isEmptyLocationField(componentPerUnitMasterBean)){
			
			ComponentPerUnitMasterService.saveComponentPerUnit2(getComponentPerUnitMasterBeanList(), componentPerUnitMasterBean);
			
			
			
			
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

	public ArrayList<DesignationBean> getDesignationBeanList() {
		return designationBeanList;
	}

	public void setDesignationBeanList(
			ArrayList<DesignationBean> designationBeanList) {
		this.designationBeanList = designationBeanList;
	}

	public DesignationBean getDesignationBean() {
		return designationBean;
	}

	public void setDesignationBean(DesignationBean designationBean) {
		this.designationBean = designationBean;
	}

}
