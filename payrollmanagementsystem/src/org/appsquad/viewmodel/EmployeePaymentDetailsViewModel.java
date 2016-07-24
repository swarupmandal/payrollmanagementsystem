package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.EmployeePaymentDetailsBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.RunPayRollService;
import org.appsquad.service.UnitMasterService;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import utility.LeaveCalculation;
import utility.OtCalculation;

public class EmployeePaymentDetailsViewModel {

	Session session = null;
	String userName;
	private String companyName;
	private String unitName;
	private String salaryMonth;
	private int year;
	
	private Integer totalWorkingDays;
	private Double workinghoursPerDay;
	
	private Double otHours;
	private Integer noOfLeaveDays;
	
	private Double otSalaryAdd;
	private Double leaveSalaryDeduct;
	
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
		
		salaryMonth = "SALARY FOR THE MONTH\n"+ mon +"-"+yr;
		
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


	@Command
	@NotifyChange("*")
	public void onClickSaveTotalWorkingDay(){
		
		if(RunPayRollService.totalWorkingDaysisNull(totalWorkingDays, workinghoursPerDay)){
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange("*")
	public void onClickSaveOtHours(){
		Messagebox.show(
				"Are you sure to calculate?", "Confirm Dialog",
				Messagebox.OK | Messagebox.IGNORE | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							if(RunPayRollService.totalOtHoursIsNullCheck(totalWorkingDays, workinghoursPerDay, otHours)){
								otSalaryAdd= OtCalculation.otAmount(payRollBean.getTotalSalary(), totalWorkingDays, workinghoursPerDay, otHours);
								if(otSalaryAdd>0){
									Messagebox.show("Over Time Amount " +otSalaryAdd, "Information", Messagebox.OK, Messagebox.INFORMATION);
								}
								System.out.println("O T >>> >> > " + otSalaryAdd);
							}	
						} else if (evt.getName().equals("onIgnore")) {
							Messagebox.show("Ignore Caculation?", "Warning",
									Messagebox.OK, Messagebox.EXCLAMATION);
						} else {
							System.out.println("Save Canceled !");
						}
					}
				}
			);
		
		
		/*if(RunPayRollService.totalOtHoursIsNullCheck(totalWorkingDays, workinghoursPerDay, otHours)){
			otSalaryAdd= OtCalculation.otAmount(payRollBean.getTotalSalary(), totalWorkingDays, workinghoursPerDay, otHours);
		}*/
		
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange("*")
	public void onClickSaveLeaveDays(){
		
		Messagebox.show(
				"Are you sure to calculate?", "Confirm Dialog",
				Messagebox.OK | Messagebox.IGNORE | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							if(RunPayRollService.totalLeaveIsNullCheck(totalWorkingDays, workinghoursPerDay, noOfLeaveDays)){
								leaveSalaryDeduct = LeaveCalculation.leaveAmount(payRollBean.getTotalSalary(), totalWorkingDays, workinghoursPerDay, noOfLeaveDays);
								if(leaveSalaryDeduct >0){
									Messagebox.show("Leave Deduction " + leaveSalaryDeduct, "Information", Messagebox.OK, Messagebox.INFORMATION);
								}
								System.out.println("Leave Salary Ded >>> >> > " + leaveSalaryDeduct);
							}
						} else if (evt.getName().equals("onIgnore")) {
							Messagebox.show("Ignore Calculation?", "Warning",
									Messagebox.OK, Messagebox.EXCLAMATION);
						} else {
							System.out.println("Save Canceled !");
						}
					}
				}
			);
		
		/*if(RunPayRollService.totalLeaveIsNullCheck(totalWorkingDays, workinghoursPerDay, noOfLeaveDays)){
			leaveSalaryDeduct = LeaveCalculation.leaveAmount(payRollBean.getTotalSalary(), totalWorkingDays, workinghoursPerDay, noOfLeaveDays);
		}*/
		
		
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


	public Integer getTotalWorkingDays() {
		return totalWorkingDays;
	}


	public void setTotalWorkingDays(Integer totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}


	public Double getWorkinghoursPerDay() {
		return workinghoursPerDay;
	}


	public void setWorkinghoursPerDay(Double workinghoursPerDay) {
		this.workinghoursPerDay = workinghoursPerDay;
	}


	public Double getOtHours() {
		return otHours;
	}


	public void setOtHours(Double otHours) {
		this.otHours = otHours;
	}


	public Integer getNoOfLeaveDays() {
		return noOfLeaveDays;
	}


	public void setNoOfLeaveDays(Integer noOfLeaveDays) {
		this.noOfLeaveDays = noOfLeaveDays;
	}


	public Double getOtSalaryAdd() {
		return otSalaryAdd;
	}


	public void setOtSalaryAdd(Double otSalaryAdd) {
		this.otSalaryAdd = otSalaryAdd;
	}


	public Double getLeaveSalaryDeduct() {
		return leaveSalaryDeduct;
	}


	public void setLeaveSalaryDeduct(Double leaveSalaryDeduct) {
		this.leaveSalaryDeduct = leaveSalaryDeduct;
	}



	
}
