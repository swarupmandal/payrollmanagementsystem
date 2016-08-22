package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.PayrollExistBean;
import org.appsquad.bean.RunPayRollBean;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

public class ExistingPayroll {

	public PayrollExistBean payrollExistBean = new PayrollExistBean();
	public RunPayRollBean runPayRollBean = new RunPayRollBean();
	public EmployeeSalaryComponentAmountBean componentAmountBean = new EmployeeSalaryComponentAmountBean();
	
	
	private ArrayList<PayrollExistBean> payrollExistBeanList = new ArrayList<PayrollExistBean>();
	private ArrayList<RunPayRollBean> exPayrollBeanList = new ArrayList<RunPayRollBean>();
	
	private ArrayList<EmployeeSalaryComponentAmountBean>  componentBeanList = new ArrayList<EmployeeSalaryComponentAmountBean>();
	private ArrayList<EmployeeSalaryComponentAmountBean>  earningList = new ArrayList<EmployeeSalaryComponentAmountBean>();
	private ArrayList<EmployeeSalaryComponentAmountBean>  deductionList = new ArrayList<EmployeeSalaryComponentAmountBean>();
	
	
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("parentList")RunPayRollBean existBean)throws Exception{
		Selectors.wireComponents(view, this, false);
		
		runPayRollBean = existBean  ;
		//System.out.println("ntt data "+ existBean.getNetSalary2());
		componentBeanList = runPayRollBean.getComponentAmountBeanList();
		
		for(EmployeeSalaryComponentAmountBean bean : componentBeanList){
			if(bean.getComponentTypeId()==1){
				earningList.add(bean);
			}if (bean.getComponentTypeId() == 2) {
				deductionList.add(bean);
			}
		}
		
	}

	
	public PayrollExistBean getPayrollExistBean() {
		return payrollExistBean;
	}



	public void setPayrollExistBean(PayrollExistBean payrollExistBean) {
		this.payrollExistBean = payrollExistBean;
	}



	public EmployeeSalaryComponentAmountBean getComponentAmountBean() {
		return componentAmountBean;
	}



	public void setComponentAmountBean(
			EmployeeSalaryComponentAmountBean componentAmountBean) {
		this.componentAmountBean = componentAmountBean;
	}



	public ArrayList<PayrollExistBean> getPayrollExistBeanList() {
		return payrollExistBeanList;
	}



	public void setPayrollExistBeanList(
			ArrayList<PayrollExistBean> payrollExistBeanList) {
		this.payrollExistBeanList = payrollExistBeanList;
	}



	public ArrayList<EmployeeSalaryComponentAmountBean> getComponentBeanList() {
		return componentBeanList;
	}



	public void setComponentBeanList(
			ArrayList<EmployeeSalaryComponentAmountBean> componentBeanList) {
		this.componentBeanList = componentBeanList;
	}



	public ArrayList<EmployeeSalaryComponentAmountBean> getEarningList() {
		return earningList;
	}



	public void setEarningList(
			ArrayList<EmployeeSalaryComponentAmountBean> earningList) {
		this.earningList = earningList;
	}



	public ArrayList<EmployeeSalaryComponentAmountBean> getDeductionList() {
		return deductionList;
	}



	public void setDeductionList(
			ArrayList<EmployeeSalaryComponentAmountBean> deductionList) {
		this.deductionList = deductionList;
	}


	public RunPayRollBean getRunPayRollBean() {
		return runPayRollBean;
	}


	public void setRunPayRollBean(RunPayRollBean runPayRollBean) {
		this.runPayRollBean = runPayRollBean;
	}


	public ArrayList<RunPayRollBean> getExPayrollBeanList() {
		return exPayrollBeanList;
	}


	public void setExPayrollBeanList(ArrayList<RunPayRollBean> exPayrollBeanList) {
		this.exPayrollBeanList = exPayrollBeanList;
	}
	
}
