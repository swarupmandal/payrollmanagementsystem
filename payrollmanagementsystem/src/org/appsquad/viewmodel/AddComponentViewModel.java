package org.appsquad.viewmodel;

import java.sql.Connection;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class AddComponentViewModel {
	
	
	private Connection connection = null;
	private Session session = null;
	private String userName;
	Integer companyId, unitId, unitDesId;
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("companyId")Integer compId,
			@ExecutionArgParam("unitId")Integer unId,
			@ExecutionArgParam("unitDes")Integer desId)throws Exception{
		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		userName =  (String) session.getAttribute("userId");
		
		companyId = compId;
		unitId = unId;
		unitDesId = desId;
		
		
	}

}
