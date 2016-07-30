package org.appsquad.bean;

public class EmployeeDesignationMaster {

	private String empDesignation;
	private int empdesignationId;
	
	private DesignationBean designationBean; 
	
	
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
	public int getEmpdesignationId() {
		return empdesignationId;
	}
	public void setEmpdesignationId(int empdesignationId) {
		this.empdesignationId = empdesignationId;
	}
	public DesignationBean getDesignationBean() {
		return designationBean;
	}
	public void setDesignationBean(DesignationBean designationBean) {
		this.designationBean = designationBean;
	}
	
	
	
}
