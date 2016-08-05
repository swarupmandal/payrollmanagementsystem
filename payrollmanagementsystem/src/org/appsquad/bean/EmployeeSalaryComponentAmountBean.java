package org.appsquad.bean;

public class EmployeeSalaryComponentAmountBean {
	
	private String componentName;
	private String componentType;
	private double componentAmount;
	private int componentTypeId;
	private Integer totalWorkingDays;
	private Double totalOtAdd;
	private Double totalLeaveDeduction;
	
	private int empId;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return componentType+"\t"+componentName+"\t"+componentAmount;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public double getComponentAmount() {
		return componentAmount;
	}
	public void setComponentAmount(double componentAmount) {
		this.componentAmount = componentAmount;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getComponentTypeId() {
		return componentTypeId;
	}
	public void setComponentTypeId(int componentTypeId) {
		this.componentTypeId = componentTypeId;
	}
	public Integer getTotalWorkingDays() {
		return totalWorkingDays;
	}
	public void setTotalWorkingDays(Integer totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}
	public Double getTotalOtAdd() {
		return totalOtAdd;
	}
	public void setTotalOtAdd(Double totalOtAdd) {
		this.totalOtAdd = totalOtAdd;
	}
	public Double getTotalLeaveDeduction() {
		return totalLeaveDeduction;
	}
	public void setTotalLeaveDeduction(Double totalLeaveDeduction) {
		this.totalLeaveDeduction = totalLeaveDeduction;
	}
	
	

}
