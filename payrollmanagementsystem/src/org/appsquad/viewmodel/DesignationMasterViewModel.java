package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.DesignationMasterBean;
import org.appsquad.bean.EmployeeDesignationMaster;
import org.appsquad.dao.DesignationMasterDao;
import org.appsquad.service.DesignationMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class DesignationMasterViewModel {

	DesignationMasterBean designationMasterBean = new DesignationMasterBean();
	
	//DesignationMasterBean unitDesignationMasterBean = new DesignationMasterBean();
	
	DesignationMasterBean empDesignationMasterBean = new DesignationMasterBean();
	
	
	ArrayList<DesignationMasterBean> designationMasterBeanList = new ArrayList<DesignationMasterBean>();
	ArrayList<DesignationMasterBean> empDesignationList= new ArrayList<DesignationMasterBean>();
	
	
	Session session = null;
	String userName;
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		designationMasterBean.setUserName(userName);
		
		designationMasterBeanList = DesignationMasterDao.onLoad();
		empDesignationList = DesignationMasterDao.onLoadEmpDesignationList();
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
		designationMasterBean.setUserName(userName);
		DesignationMasterService.insertDesignationMasterData(designationMasterBean);
		DesignationMasterService.clearScreen(designationMasterBean);
		designationMasterBeanList = DesignationMasterDao.onLoad();
	}

	@Command
	@NotifyChange("*")
	public void onClickSaveEmpDes(){
		
		DesignationMasterService.insertEmpDesignationData(empDesignationMasterBean, userName);
		DesignationMasterService.clearScreen(empDesignationMasterBean);
		empDesignationList = DesignationMasterDao.onLoadEmpDesignationList();
	}
	
	
	
	public DesignationMasterBean getDesignationMasterBean() {
		return designationMasterBean;
	}

	public void setDesignationMasterBean(DesignationMasterBean designationMasterBean) {
		this.designationMasterBean = designationMasterBean;
	}

	public ArrayList<DesignationMasterBean> getDesignationMasterBeanList() {
		return designationMasterBeanList;
	}

	public void setDesignationMasterBeanList(
			ArrayList<DesignationMasterBean> designationMasterBeanList) {
		this.designationMasterBeanList = designationMasterBeanList;
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

	public ArrayList<DesignationMasterBean> getEmpDesignationList() {
		return empDesignationList;
	}

	public void setEmpDesignationList(
			ArrayList<DesignationMasterBean> empDesignationList) {
		this.empDesignationList = empDesignationList;
	}
	
	public DesignationMasterBean getEmpDesignationMasterBean() {
		return empDesignationMasterBean;
	}

	public void setEmpDesignationMasterBean(
			DesignationMasterBean empDesignationMasterBean) {
		this.empDesignationMasterBean = empDesignationMasterBean;
	}

	
}
