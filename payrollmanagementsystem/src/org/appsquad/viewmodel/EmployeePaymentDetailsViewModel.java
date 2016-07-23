package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.EmployeePaymentDetailsBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.service.EmployeeMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class EmployeePaymentDetailsViewModel {

	Session session = null;
	String userName;
	private String companyName;
	private String unitName;
	private String salaryMonth;
	
	
	private RunPayRollBean payRollBean = new RunPayRollBean();
	private EmployeePaymentDetailsBean paymentDetailsBean = new EmployeePaymentDetailsBean();
	
	private ArrayList<EmployeePaymentDetailsBean> paymentDetailsBeanList = new ArrayList<EmployeePaymentDetailsBean>();
	
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			         @ExecutionArgParam("parentList") RunPayRollBean rpBean,
			         @ExecutionArgParam("companyName") String com,
			         @ExecutionArgParam("unitName") String unit,
			         @ExecutionArgParam("salaryMonth") String mon) throws Exception{
		
		payRollBean= rpBean;

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		companyName= com;
		unitName=unit;
		salaryMonth = mon;
		System.out.println("Emp Id " + payRollBean.getEmpId());
		System.out.println("Company Name " + companyName);
		System.out.println("Unit name " + unitName);
		System.out.println("Salary Month " + salaryMonth);
		
	}
	
}
