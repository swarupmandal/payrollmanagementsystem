package org.appsquad.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.dao.BloodGroupDao;
import org.appsquad.database.DbConnection;
import org.appsquad.service.BloodGroupService;
import org.appsquad.sql.SqlQuery;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class BloodGroupMasterViewModel {

	private Connection connection = null;
	
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
		
		BloodGroupService.loadAllDataOfBloodGroup(bloodGroupBeanList);
		//System.out.println("Size:: "+bloodGroupBeanList.size());
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
	
		if( BloodGroupService.isValid(bloodGroupBean,userName) ){
			if(BloodGroupService.insertBloodGroupData(bloodGroupBean, userName)){
				Messagebox.show("Saved Succesfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
				BloodGroupService.clearData(bloodGroupBean);
				BloodGroupService.loadAllDataOfBloodGroup(bloodGroupBeanList);
			}
		}
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
		if( BloodGroupService.isValid(bloodGroupBean,userName) ){
			if(BloodGroupService.updateBloodGroupData(bloodGroupBean, userName)){
				saveDisability = true;
				updateDisability = false;
				BloodGroupService.clearData(bloodGroupBean);
				Messagebox.show("Updated Succesfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
				BloodGroupService.loadAllDataOfBloodGroup(bloodGroupBeanList);
			}
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
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
