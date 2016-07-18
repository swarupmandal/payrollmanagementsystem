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
		
		onLoad();
		System.out.println("Size:: "+bloodGroupBeanList.size());
	}
	
	public void onLoad(){
		if(bloodGroupBeanList.size()>0){
			bloodGroupBeanList.clear();
		}
		try {		
			  sql:{	
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
			
				try {
					preparedStatement = Util1.createQuery(connection, SqlQuery.onLoadBloodGroupQuery, null);
					
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						int bloodGroupId = resultSet.getInt("bloodgroup_id");
						String bloodGroupName = resultSet.getString("bloodgroup_name");
						bloodGroupBeanList.add(new BloodGroupBean(bloodGroupName, bloodGroupId));
					}
				    
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}if(connection != null){
						connection.close();
					}
				}
			}	
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
	
		if( BloodGroupService.isValid(bloodGroupBean) ){
			if(BloodGroupDao.insertBloodGroupData(bloodGroupBean, userName)){
				Messagebox.show("Saved Succesfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
				onLoad();
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
		updateData();
	}
	
	public void updateData(){
		
		if(fieldValidation()){
		try {			
			sql:{
			connection = DbConnection.createConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.updateBloodGroupQuery, 
						Arrays.asList(bloodGroupBean.getBloodGroupName().toUpperCase(),userName , bloodGroupBean.getBloodGroupId() ));
				
				int i = preparedStatement.executeUpdate();
				connection.commit();
				if(i>0){
					Messagebox.show("Updated successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
					clearScreen();
					updateDisability = true;
					saveDisability = true;
				}else {
					Messagebox.show("Updation Failed", "Information", Messagebox.OK, Messagebox.INFORMATION);
				}
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
			}finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}if(connection != null){
					connection.setAutoCommit(true);
					connection.close();
				}
			}	
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		onLoad();
		
	}
	
	public boolean fieldValidation(){
		if(bloodGroupBean.getBloodGroupName()!=null && bloodGroupBean.getBloodGroupName().trim().length()>0){
			return true;
		}else {
			Messagebox.show("Enter Blood Group Name","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
			return false;
		}
	}
	
	public void clearScreen(){
		bloodGroupBean.setBloodGroupName(null);
		bloodGroupBean.setBloodGroupId(0);
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
