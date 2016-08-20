package org.appsquad.viewmodel;

import java.sql.Connection;
import java.util.ArrayList;

import org.appsquad.bean.AddComponentBean;
import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.service.AddComponentService;
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

public class AddComponentViewModel {
	
	
	private Connection connection = null;
	private Session session = null;
	private String userName;
	Integer companyId, unitId, unitDesId;
	
	public AddComponentBean addComponentBean = new AddComponentBean();
	public ComponentPerUnitMasterBean perUnitMasterBean = new ComponentPerUnitMasterBean();
	
	
	private ArrayList<ComponentPerUnitMasterBean> componentPerUnitMasterBeanList = new ArrayList<ComponentPerUnitMasterBean>();
	private ArrayList<Integer> empIdList = new ArrayList<Integer>();
	
	
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("companyId")Integer compId,
			@ExecutionArgParam("unitId")Integer unId,
			@ExecutionArgParam("unitDes")Integer desId)throws Exception{
		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		userName =  (String) session.getAttribute("userId");
		
		companyId = compId; 
		unitId = unId; 
		unitDesId = desId;
		
		perUnitMasterBean.setUserName(userName);
		perUnitMasterBean.setCompanyId(companyId);
		perUnitMasterBean.setUnitId(unitId);
		perUnitMasterBean.setUnitDesignationId(unitDesId);
		
		componentPerUnitMasterBeanList = AddComponentService.loadData(companyId, unitId, unitDesId);
		empIdList = AddComponentService.loadEmpIdList(companyId, unitId, unitDesId);
		System.out.println("EMP ID LIST " + empIdList);
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
		AddComponentService.saveNewComponent(componentPerUnitMasterBeanList, perUnitMasterBean, empIdList);
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getUnitDesId() {
		return unitDesId;
	}

	public void setUnitDesId(Integer unitDesId) {
		this.unitDesId = unitDesId;
	}

	public ComponentPerUnitMasterBean getPerUnitMasterBean() {
		return perUnitMasterBean;
	}

	public void setPerUnitMasterBean(ComponentPerUnitMasterBean perUnitMasterBean) {
		this.perUnitMasterBean = perUnitMasterBean;
	}

	public ArrayList<ComponentPerUnitMasterBean> getComponentPerUnitMasterBeanList() {
		return componentPerUnitMasterBeanList;
	}

	public void setComponentPerUnitMasterBeanList(
			ArrayList<ComponentPerUnitMasterBean> componentPerUnitMasterBeanList) {
		this.componentPerUnitMasterBeanList = componentPerUnitMasterBeanList;
	}

}
