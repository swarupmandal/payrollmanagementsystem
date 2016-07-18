package org.appsquad.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.StateMasterBean;
import org.appsquad.database.DbConnection;
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

public class StateMasterViewModel {
	
	StateMasterBean stateMasterBean = new StateMasterBean();;
	
	ArrayList<StateMasterBean> stateBeanList = new ArrayList<StateMasterBean>();
	
	private Connection connection = null;
	
	Session session = null;
	
	private String userName;

	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		
		stateMasterBean.setUserName(userName);
		onLoad();
		
	}
	
	public void onLoad(){
		if(stateBeanList.size()>0){
			stateBeanList.clear();
		}
		
		
		if(stateMasterBean.getStateName() != null && stateMasterBean.getStateId()>0){
			stateMasterBean.isSaveButtonDesabled();
			stateMasterBean.setUpdtateButtonDesabled(false);
		}else {

			stateMasterBean.isUpdtateButtonDesabled();
			stateMasterBean.setSaveButtonDesabled(false);
		}
		
		try {
			
		  sql:{	
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
		
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.onLoadStateQuery, null);
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					StateMasterBean bean = new StateMasterBean();
					bean.setStateId(resultSet.getInt("id"));
					bean.setStateName(resultSet.getString("state_name"));
					
					stateBeanList.add(bean);
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
		
				if(fieldValidation()){
			
			try {
				
				sql:{
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							SqlQuery.insertStateQuery, Arrays.asList(stateMasterBean.getStateName().toUpperCase(),stateMasterBean.getUserName()));
					int i = preparedStatement.executeUpdate();
					if(i>0){
						Messagebox.show("Saved Succesfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
						clearScreen();
					}else {
						Messagebox.show("Saving failed!", "Information", Messagebox.OK, Messagebox.ERROR);
						
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
			onLoad();
	}
	
	public boolean fieldValidation(){
		if(stateMasterBean.getStateName()!=null && stateMasterBean.getStateName().trim().length()>0){
			return true;
		}else {
			Messagebox.show("Enter State Name");
			return false;
		}
	}
	
	
	
	@Command
	@NotifyChange("*")
	public void onClickUpdate(){
		
		if(fieldValidation()){

		try {
			
			sql:{
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.updateStateQuery, Arrays.asList(stateMasterBean.getStateName().toUpperCase(),stateMasterBean.getUserName() , stateMasterBean.getStateId() ));
				
				int i = preparedStatement.executeUpdate();
				if(i>0){
					Messagebox.show("Updated successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
					clearScreen();
					
				}else {
					Messagebox.show("Updation Failed", "Information", Messagebox.OK, Messagebox.INFORMATION);
					
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
		onLoad();
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickDelete(@BindingParam("bean") StateMasterBean bean){
		
		try {
			
			sql:{
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.deleteStateQuery, Arrays.asList(stateMasterBean.getUserName(), bean.getStateId()));
			
				int i = preparedStatement.executeUpdate();
				if(i>0){
					Messagebox.show("Deleted successfully");
					clearScreen();
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
		onLoad();
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickEdit(@BindingParam("bean") StateMasterBean bean){
		
		stateMasterBean.setStateName(bean.getStateName());
		stateMasterBean.setStateId(bean.getStateId());
		
		if(stateMasterBean.getStateName() != null && stateMasterBean.getStateId()>0){
			
			
			stateMasterBean.setUpdtateButtonDesabled(false);
			stateMasterBean.setSaveButtonDesabled(true);
			
		}else {

			
			
			stateMasterBean.setSaveButtonDesabled(false);
			stateMasterBean.setUpdtateButtonDesabled(true);
		}
	}
	
	public void clearScreen(){
		stateMasterBean.setStateName(null);
		stateMasterBean.setStateId(0);
	}
	
	public StateMasterBean getStateMasterBean() {
		return stateMasterBean;
	}

	public void setStateMasterBean(StateMasterBean stateMasterBean) {
		this.stateMasterBean = stateMasterBean;
	}

	public ArrayList<StateMasterBean> getStateBeanList() {
		return stateBeanList;
	}

	public void setStateBeanList(ArrayList<StateMasterBean> stateBeanList) {
		this.stateBeanList = stateBeanList;
	}

}
