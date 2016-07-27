package org.appsquad.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.dao.CompanyDao;
import org.appsquad.database.DbConnection;
import org.appsquad.service.CompanyService;
import org.appsquad.sql.SqlQuery;
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

import utility.Util1;

public class CompanyEditViewModel {

	CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	
	StateMasterBean stateMasterBean = new StateMasterBean();
	
	ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	
	ArrayList<StateMasterBean> stateBeanList = new ArrayList<StateMasterBean>();
	
	Session session = null;

	private String userId;

	private Connection connection = null;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			  @ExecutionArgParam("parentBean") Integer companyId)
			throws Exception {

		Selectors.wireComponents(view, this, false);
	
		session = Sessions.getCurrent();
		
		userId = (String) session.getAttribute("userId");
		
		if(companyId != null){
			companyMasterBean = fetchcompanyData(companyId);
		}
		companyMasterBean.setUserId(userId);
		fetchStateNameList();
	}
	
	public void fetchStateNameList(){
		if(stateBeanList.size()>0){
			stateBeanList.clear();
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
					bean.setStateName(resultSet.getString("state_name"));
					bean.setStateId(resultSet.getInt("id"));
					System.out.println(bean.getStateId());
					stateBeanList.add(bean);
					
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(preparedStatement !=null){
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
	public void onSelectStateName(){
		System.out.println("stateName selected");
		System.out.println("State Name " + stateMasterBean.getStateName());
		System.out.println("state Id " + stateMasterBean.getStateId());
		companyMasterBean.setCompanyStateId(stateMasterBean.getStateId());
		
	}
	

	@Command
	@NotifyChange("*")
	public void onclickUpdate(){
		CompanyDao.updateCompanyInfo(companyMasterBean);
	}
	
	public CompanyMasterBean fetchcompanyData(int companyId){
		return CompanyService.loadCompany(companyId);
	}

	public CompanyMasterBean getCompanyMasterBean() {
		return companyMasterBean;
	}

	public void setCompanyMasterBean(CompanyMasterBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}

	public StateMasterBean getStateMasterBean() {
		return stateMasterBean;
	}

	public void setStateMasterBean(StateMasterBean stateMasterBean) {
		this.stateMasterBean = stateMasterBean;
	}

	public ArrayList<CompanyMasterBean> getCompanyBeanList() {
		return companyBeanList;
	}

	public void setCompanyBeanList(ArrayList<CompanyMasterBean> companyBeanList) {
		this.companyBeanList = companyBeanList;
	}

	public ArrayList<StateMasterBean> getStateBeanList() {
		return stateBeanList;
	}

	public void setStateBeanList(ArrayList<StateMasterBean> stateBeanList) {
		this.stateBeanList = stateBeanList;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
