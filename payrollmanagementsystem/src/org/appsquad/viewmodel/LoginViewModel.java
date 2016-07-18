package org.appsquad.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.appsquad.bean.LoginBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.SqlQuery;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class LoginViewModel {
	
	LoginBean loginBean = new LoginBean();
	
	private Connection connection = null;
	
	Session session = null;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		System.out.println("Login Page loading with session::"+session);
		System.out.println("INIT LOAD");
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickLogin(){
		
		
		
		if(isFieldValidate()){
			
			try {
				 
				sql:{
				     connection = DbConnection.createConnection();
				     
				     
				     PreparedStatement preparedStatement = null;
				     ResultSet resultSet = null;
				     
				     try {
						preparedStatement = Util1.createQuery(connection, SqlQuery.loginQuery, 
								Arrays.asList(loginBean.getUserId(),loginBean.getPassword()));
						resultSet = preparedStatement.executeQuery();
						
						if(resultSet.next()){
							String userId = resultSet.getString("user_id");
							session.setAttribute("userId", userId);
							Executions.sendRedirect("/test.zul");
							
						}else {
							Messagebox.show("Illigal entry");
						}
					} catch (Exception e) {
						
						e.printStackTrace();
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}if(connection!=null){
							connection.close();
						}
					}
			}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
	public boolean isFieldValidate(){
		if(loginBean.getUserId()!=null){
			if(loginBean.getPassword()!=null){
				return true;
			}else{
				Messagebox.show("Enter Password");
				return false;
			}
		}else{
			Messagebox.show("Enter User Id");
			return false;
		}
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
