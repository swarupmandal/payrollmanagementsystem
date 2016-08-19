package org.appsquad.bean;

import java.util.ArrayList;

public class PayrollExistBean {

	private String lvYr2, salMonth2;
	private int monthID2,companyId2,unitId2,unitDesignationId2;
	
	private String empName2, empCode2, empDes2;
	private Float presentDay2;
	private Double netSalary2,wages2;
	private int empId2;
	
	private ArrayList<EmployeeSalaryComponentAmountBean> componentAmountBeanList = new ArrayList<EmployeeSalaryComponentAmountBean>();
	
	
	public String getLvYr2() {
		return lvYr2;
	}
	public void setLvYr2(String lvYr2) {
		this.lvYr2 = lvYr2;
	}
	public int getMonthID2() {
		return monthID2;
	}
	public void setMonthID2(int monthID2) {
		this.monthID2 = monthID2;
	}
	public int getCompanyId2() {
		return companyId2;
	}
	public void setCompanyId2(int companyId2) {
		this.companyId2 = companyId2;
	}
	public int getUnitId2() {
		return unitId2;
	}
	public void setUnitId2(int unitId2) {
		this.unitId2 = unitId2;
	}
	public int getUnitDesignationId2() {
		return unitDesignationId2;
	}
	public void setUnitDesignationId2(int unitDesignationId2) {
		this.unitDesignationId2 = unitDesignationId2;
	}
	public String getEmpName2() {
		return empName2;
	}
	public void setEmpName2(String empName2) {
		this.empName2 = empName2;
	}
	public String getEmpCode2() {
		return empCode2;
	}
	public void setEmpCode2(String empCode2) {
		this.empCode2 = empCode2;
	}
	public String getEmpDes2() {
		return empDes2;
	}
	public void setEmpDes2(String empDes2) {
		this.empDes2 = empDes2;
	}
	public Float getPresentDay2() {
		return presentDay2;
	}
	public void setPresentDay2(Float presentDay2) {
		this.presentDay2 = presentDay2;
	}
	public Double getNetSalary2() {
		return netSalary2;
	}
	public void setNetSalary2(Double netSalary2) {
		this.netSalary2 = netSalary2;
	}
	public int getEmpId2() {
		return empId2;
	}
	public void setEmpId2(int empId2) {
		this.empId2 = empId2;
	}
	public String getSalMonth2() {
		return salMonth2;
	}
	public void setSalMonth2(String salMonth2) {
		this.salMonth2 = salMonth2;
	}
	public Double getWages2() {
		return wages2;
	}
	public void setWages2(Double wages2) {
		this.wages2 = wages2;
	}
	public ArrayList<EmployeeSalaryComponentAmountBean> getComponentAmountBeanList() {
		return componentAmountBeanList;
	}
	public void setComponentAmountBeanList(
			ArrayList<EmployeeSalaryComponentAmountBean> componentAmountBeanList) {
		this.componentAmountBeanList = componentAmountBeanList;
	}
	
	
}
