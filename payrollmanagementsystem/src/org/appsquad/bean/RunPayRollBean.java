package org.appsquad.bean;

import java.util.ArrayList;

public class RunPayRollBean {
	
    private String empName;
    private int empId;
    private String empCode;
    private String empPf;
    private String empEsi;
    private String empDesignation;
    private int empcount;
    private double totalDeduction;
    private double netSalary;
    private double totalSalary;
    private ArrayList<EmployeeSalaryComponentAmountBean> componentAmountBeanList = new ArrayList<EmployeeSalaryComponentAmountBean>();
    
    
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpPf() {
		return empPf;
	}
	public void setEmpPf(String empPf) {
		this.empPf = empPf;
	}
	public String getEmpEsi() {
		return empEsi;
	}
	public void setEmpEsi(String empEsi) {
		this.empEsi = empEsi;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
	public int getEmpcount() {
		return empcount;
	}
	public void setEmpcount(int empcount) {
		this.empcount = empcount;
	}
	public double getTotalDeduction() {
		return totalDeduction;
	}
	public void setTotalDeduction(double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
	public double getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}
	public double getTotalSalary() {
		return totalSalary;
	}
	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}
	public ArrayList<EmployeeSalaryComponentAmountBean> getComponentAmountBeanList() {
		return componentAmountBeanList;
	}
	public void setComponentAmountBeanList(
			ArrayList<EmployeeSalaryComponentAmountBean> componentAmountBeanList) {
		this.componentAmountBeanList = componentAmountBeanList;
	}
	
	

}
