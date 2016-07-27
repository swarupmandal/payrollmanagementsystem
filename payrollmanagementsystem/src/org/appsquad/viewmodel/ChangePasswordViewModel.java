package org.appsquad.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import org.appsquad.dao.ChangePasswordDao;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.SqlQuery;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import utility.Util1;

public class ChangePasswordViewModel {
	Session session = null;

	private String userId;
	
	private String password;
	
	@Wire("#changepasswordWindow")
	private Window changepasswordWindow ;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view)
			throws Exception {

		Selectors.wireComponents(view, this, false);
	
		session = Sessions.getCurrent();
		
		userId = (String) session.getAttribute("userId");
	}
	
	@Command
	@NotifyChange("*")
	public void onClickChangePassword(){
		Window window = (Window) Executions.createComponents("/WEB-INF/view/changepassword.zul", null, null);
		window.doModal();
	}

	@Command
	@NotifyChange("*")
	public void onClickChange(){
	     ChangePasswordDao.changePassword(userId, password);
	     changepasswordWindow.detach();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
