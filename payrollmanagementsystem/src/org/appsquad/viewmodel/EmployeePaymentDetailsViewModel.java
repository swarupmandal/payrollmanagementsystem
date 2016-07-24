package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.EmployeePaymentDetailsBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
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
	private int year;
	
	private RunPayRollBean payRollBean = new RunPayRollBean();
	private EmployeePaymentDetailsBean paymentDetailsBean = new EmployeePaymentDetailsBean();
	
	private ArrayList<EmployeeSalaryComponentAmountBean> componentEarningAmountBeanList = new ArrayList<EmployeeSalaryComponentAmountBean>();
	private ArrayList<EmployeeSalaryComponentAmountBean> componentDeductionAmountBeanList = new ArrayList<EmployeeSalaryComponentAmountBean>();
	private ArrayList<EmployeePaymentDetailsBean> paymentDetailsBeanList = new ArrayList<EmployeePaymentDetailsBean>();
	
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			         @ExecutionArgParam("parentList") RunPayRollBean rpBean,
			         @ExecutionArgParam("companyName") String com,
			         @ExecutionArgParam("unitName") String unit,
			         @ExecutionArgParam("salaryMonth") String mon,
			         @ExecutionArgParam("Year") int yr) throws Exception{
		
		payRollBean= rpBean;

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		companyName= com;
		unitName=unit;	
		
		salaryMonth = "Salary for the month of \n"+ mon +"-"+yr;
		System.out.println("salary month " + salaryMonth);
		if(rpBean!=null){
			payRollBean = rpBean;
			ArrayList<EmployeeSalaryComponentAmountBean> tempList = new ArrayList<EmployeeSalaryComponentAmountBean>();
			tempList = payRollBean.getComponentAmountBeanList();
			for(EmployeeSalaryComponentAmountBean earning: tempList){
				if(earning.getComponentType().equalsIgnoreCase("Earning")){
					componentEarningAmountBeanList.add(earning);
				}
			}
			
			for(EmployeeSalaryComponentAmountBean earning: tempList){
				if(earning.getComponentType().equalsIgnoreCase("Deduction")){
					componentDeductionAmountBeanList.add(earning);
				}
			}
		}
		
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



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getUnitName() {
		return unitName;
	}



	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}



	public String getSalaryMonth() {
		return salaryMonth;
	}



	public void setSalaryMonth(String salaryMonth) {
		this.salaryMonth = salaryMonth;
	}



	public RunPayRollBean getPayRollBean() {
		return payRollBean;
	}



	public void setPayRollBean(RunPayRollBean payRollBean) {
		this.payRollBean = payRollBean;
	}



	public EmployeePaymentDetailsBean getPaymentDetailsBean() {
		return paymentDetailsBean;
	}



	public void setPaymentDetailsBean(EmployeePaymentDetailsBean paymentDetailsBean) {
		this.paymentDetailsBean = paymentDetailsBean;
	}



	public ArrayList<EmployeePaymentDetailsBean> getPaymentDetailsBeanList() {
		return paymentDetailsBeanList;
	}



	public void setPaymentDetailsBeanList(
			ArrayList<EmployeePaymentDetailsBean> paymentDetailsBeanList) {
		this.paymentDetailsBeanList = paymentDetailsBeanList;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public ArrayList<EmployeeSalaryComponentAmountBean> getComponentEarningAmountBeanList() {
		return componentEarningAmountBeanList;
	}



	public void setComponentEarningAmountBeanList(
			ArrayList<EmployeeSalaryComponentAmountBean> componentEarningAmountBeanList) {
		this.componentEarningAmountBeanList = componentEarningAmountBeanList;
	}



	public ArrayList<EmployeeSalaryComponentAmountBean> getComponentDeductionAmountBeanList() {
		return componentDeductionAmountBeanList;
	}



	public void setComponentDeductionAmountBeanList(
			ArrayList<EmployeeSalaryComponentAmountBean> componentDeductionAmountBeanList) {
		this.componentDeductionAmountBeanList = componentDeductionAmountBeanList;
	}



	
}
