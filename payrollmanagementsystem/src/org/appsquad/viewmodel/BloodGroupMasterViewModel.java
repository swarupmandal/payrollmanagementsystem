package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.service.BloodGroupService;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

public class BloodGroupMasterViewModel {

	public BloodGroupBean bloodGroupBean = new BloodGroupBean();

	Session session = null;
	
	private String userName;
	
	public ArrayList<BloodGroupBean> bloodGroupBeanList = new ArrayList<BloodGroupBean>();
	
	public boolean saveDisability = false;
	
	public boolean updateDisability = true;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		
		bloodGroupBean.setUserName(userName);
		
		BloodGroupService.loadAllDataOfBloodGroup(bloodGroupBeanList);
		
	}
	
	@GlobalCommand
	@NotifyChange("*")
	public void globalReload(){
		BloodGroupService.loadAllDataOfBloodGroup(bloodGroupBeanList);
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
		BloodGroupService.insertBloodGroupData(bloodGroupBean);
		BloodGroupService.clearData(bloodGroupBean);
		BloodGroupService.loadAllDataOfBloodGroup(bloodGroupBeanList);
	}
	
	@Command
	@NotifyChange("*")
	public void onClickEdit(@BindingParam("bean")BloodGroupBean bean){
		
		bloodGroupBean.setBloodGroupName(bean.getBloodGroupName());
		bloodGroupBean.setBloodGroupId(bean.getBloodGroupId());
		saveDisability = true;
		updateDisability = false;
	}
	
	@Command
	@NotifyChange("*")
	public void onClickUpdate(){
		BloodGroupService.updateBloodGroupData(bloodGroupBean);
		BloodGroupService.clearData(bloodGroupBean);
		BloodGroupService.loadAllDataOfBloodGroup(bloodGroupBeanList);
		updateDisability = true;
		saveDisability = false;
	}
	
	@Command
	@NotifyChange("*")
	public void onClickDelete(@BindingParam("bean")BloodGroupBean bloodgroupbean){
		
		Messagebox.show(
				"Are you sure to delete?", "Confirm Dialog",
				Messagebox.OK | Messagebox.IGNORE | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							BloodGroupService.deleteBloodGroupData(bloodgroupbean);
							 BindUtils.postGlobalCommand(null, null, "globalReload", null);
						} else if (evt.getName().equals("onIgnore")) {
							Messagebox.show("Ignore Deletion?", "Warning",
									Messagebox.OK, Messagebox.EXCLAMATION);
						} else {
							System.out.println("Save Canceled !");
						}
					}
				}
			);
	}

	public BloodGroupBean getBloodGroupBean() {
		return bloodGroupBean;
	}

	public void setBloodGroupBean(BloodGroupBean bloodGroupBean) {
		this.bloodGroupBean = bloodGroupBean;
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

	public ArrayList<BloodGroupBean> getBloodGroupBeanList() {
		return bloodGroupBeanList;
	}

	public void setBloodGroupBeanList(ArrayList<BloodGroupBean> bloodGroupBeanList) {
		this.bloodGroupBeanList = bloodGroupBeanList;
	}

	public boolean isSaveDisability() {
		return saveDisability;
	}

	public void setSaveDisability(boolean saveDisability) {
		this.saveDisability = saveDisability;
	}

	public boolean isUpdateDisability() {
		return updateDisability;
	}

	public void setUpdateDisability(boolean updateDisability) {
		this.updateDisability = updateDisability;
	}
}
