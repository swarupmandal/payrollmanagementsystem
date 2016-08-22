package org.appsquad.viewmodel;

import org.appsquad.bean.PayrollExistBean;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

public class ExistingPaymentDetailsViewModel {
	
	public PayrollExistBean payrollExBean = new PayrollExistBean();
	//EmployeeSalaryComponentAmountBean 
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("parentBean")PayrollExistBean existBean)throws Exception{
		Selectors.wireComponents(view, this, false);
		
		
		/*for(PayrollExistBean b : payrollExistBeanList2){
		for(EmployeeSalaryComponentAmountBean bb : b.getComponentAmountBeanList()){
			System.out.println("bb " + bb.getComponentName());
		}*/
	}
		
	

}
