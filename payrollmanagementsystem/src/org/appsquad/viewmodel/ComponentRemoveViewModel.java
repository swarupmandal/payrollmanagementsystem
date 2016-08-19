package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.service.ComponentRemoveService;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class ComponentRemoveViewModel {

	private ComponentMasterBean componentMasterBEAN = new ComponentMasterBean();
	private ArrayList<ComponentMasterBean> componentMasterBeanList = new ArrayList<ComponentMasterBean>();
	
	Session session = null;
	String userName;
	
	@Wire("#removeComponents")
	private Window removeComponents;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("parent")ComponentMasterBean componentMasterBean ) throws Exception{

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		if(componentMasterBean!=null){
			componentMasterBEAN = componentMasterBean;
		}
		//System.out.println("Company id:: "+componentMasterBEAN.getCompanyId()+" unit:: "+componentMasterBEAN.getUnitId());
		loadComponents();
		//System.out.println("Size: "+componentMasterBeanList.size());
	}

	public void loadComponents(){
		componentMasterBeanList = RunPayRollDao.loadComponentList(componentMasterBEAN);
	}

	@Command
	@NotifyChange("*")
	public void onClickRemove(){
		ComponentRemoveService.doRemoveComponents(componentMasterBeanList, componentMasterBEAN.getUnitId());
		removeComponents.detach();
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



	public ComponentMasterBean getComponentMasterBEAN() {
		return componentMasterBEAN;
	}



	public void setComponentMasterBEAN(ComponentMasterBean componentMasterBEAN) {
		this.componentMasterBEAN = componentMasterBEAN;
	}

	public ArrayList<ComponentMasterBean> getComponentMasterBeanList() {
		return componentMasterBeanList;
	}

	public void setComponentMasterBeanList(
			ArrayList<ComponentMasterBean> componentMasterBeanList) {
		this.componentMasterBeanList = componentMasterBeanList;
	}

	public Window getRemoveComponents() {
		return removeComponents;
	}

	public void setRemoveComponents(Window removeComponents) {
		this.removeComponents = removeComponents;
	}
}
