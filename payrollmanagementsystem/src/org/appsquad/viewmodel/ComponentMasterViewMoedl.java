package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.service.ComponentMasterService;
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

public class ComponentMasterViewMoedl {

	
	
	ArrayList<ComponentMasterBean> componetTypeList = new ArrayList<ComponentMasterBean>();
	
	ArrayList<ComponentMasterBean> componentMasterBeanList = new ArrayList<ComponentMasterBean>();
	
	ComponentMasterBean componentMasterBean = new ComponentMasterBean();
	
	Session session = null;
	private String userName;
	public String componentName;
	
	public static Logger logger = Logger.getLogger(ComponentMasterViewMoedl.class);
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		
		componetTypeList= ComponentMasterService.loadComponents();
		ComponentMasterService.loadComponentDetails(componentMasterBeanList);
		
	}

	
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
		componentMasterBean.setComponentName(componentName);
		 if(ComponentMasterService.isEmptyFieldCheck(componentMasterBean)){
			
			if(ComponentMasterService.insertComponentDetails(componentMasterBean, userName)){
				 logger.info("1 name ::"+componentMasterBean.getComponentName());
				
		    	componentName=null;
		    	ComponentMasterService.loadComponentDetails(componentMasterBeanList);
		    	Messagebox.show("Saved successfully", "InforMation", Messagebox.OK, Messagebox.INFORMATION);
		    }
		}
	}

	public ComponentMasterBean getComponentMasterBean() {
		return componentMasterBean;
	}

	public void setComponentMasterBean(ComponentMasterBean componentMasterBean) {
		this.componentMasterBean = componentMasterBean;
	}

	public ArrayList<ComponentMasterBean> getComponentMasterBeanList() {
		return componentMasterBeanList;
	}

	public void setComponentMasterBeanList(
			ArrayList<ComponentMasterBean> componentMasterBeanList) {
		this.componentMasterBeanList = componentMasterBeanList;
	}

	public ArrayList<ComponentMasterBean> getComponetTypeList() {
		return componetTypeList;
	}

	public void setComponetTypeList(ArrayList<ComponentMasterBean> componetTypeList) {
		this.componetTypeList = componetTypeList;
	}



	public String getComponentName() {
		return componentName;
	}



	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	
}
