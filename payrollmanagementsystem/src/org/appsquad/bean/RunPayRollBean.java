package org.appsquad.bean;

import java.util.ArrayList;

public class RunPayRollBean {
	
    private String empName;
    private int empId;
    private String empCode;
    private String comapnyName,UnitName,year,monthName;
    private String empPf;
    private String empEsi,empUan;
    private String empDesignation,unitDesignation;
    private int empcount;
    private double totalDeduction;
    private double netSalary;
    private double totalSalary;
    private Integer totalWorkingDays;
    private boolean checked = false;
    private int leaveYrId;
    private Integer sunDayCountPerMonth;
    private Integer monDayCountPerMonth;
    private Integer tuesDayCountPerMonth;
    private Integer wendsDayCountPerMonth;
    private Integer thursDayCountPerMonth;
    private Integer friDayCountPerMonth;
    private Integer satDayCountPerMonth;
    private int totalNumberOfDayseveryMonth;
    private int totalNumberOfHolidayseveryMonth;
    private int totalNumberOfWorkingDaysEveryMonth;
    public int totalNumberOfHolidays;
    
    private Integer otDaysF,presentDay,baseDay;
    private Double otHoursF;
    private Double totalOtHoursF;
    
    
    private Integer leaveDaysF;
    private Double leaveHoursF;
    private Double totalLeaveHoursF;
    
    public double otSalary = 0;
    public double leaveDeduction = 0;
    
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
	public Integer getTotalWorkingDays() {
		return totalWorkingDays;
	}
	public void setTotalWorkingDays(Integer totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getLeaveYrId() {
		return leaveYrId;
	}
	public void setLeaveYrId(int leaveYrId) {
		this.leaveYrId = leaveYrId;
	}
	public Integer getSunDayCountPerMonth() {
		return sunDayCountPerMonth;
	}
	public void setSunDayCountPerMonth(Integer sunDayCountPerMonth) {
		this.sunDayCountPerMonth = sunDayCountPerMonth;
	}
	public Integer getMonDayCountPerMonth() {
		return monDayCountPerMonth;
	}
	public void setMonDayCountPerMonth(Integer monDayCountPerMonth) {
		this.monDayCountPerMonth = monDayCountPerMonth;
	}
	public Integer getTuesDayCountPerMonth() {
		return tuesDayCountPerMonth;
	}
	public void setTuesDayCountPerMonth(Integer tuesDayCountPerMonth) {
		this.tuesDayCountPerMonth = tuesDayCountPerMonth;
	}
	public Integer getWendsDayCountPerMonth() {
		return wendsDayCountPerMonth;
	}
	public void setWendsDayCountPerMonth(Integer wendsDayCountPerMonth) {
		this.wendsDayCountPerMonth = wendsDayCountPerMonth;
	}
	public Integer getThursDayCountPerMonth() {
		return thursDayCountPerMonth;
	}
	public void setThursDayCountPerMonth(Integer thursDayCountPerMonth) {
		this.thursDayCountPerMonth = thursDayCountPerMonth;
	}
	public Integer getFriDayCountPerMonth() {
		return friDayCountPerMonth;
	}
	public void setFriDayCountPerMonth(Integer friDayCountPerMonth) {
		this.friDayCountPerMonth = friDayCountPerMonth;
	}
	public Integer getSatDayCountPerMonth() {
		return satDayCountPerMonth;
	}
	public void setSatDayCountPerMonth(Integer satDayCountPerMonth) {
		this.satDayCountPerMonth = satDayCountPerMonth;
	}
	public int getTotalNumberOfHolidayseveryMonth() {
		return totalNumberOfHolidayseveryMonth;
	}
	public void setTotalNumberOfHolidayseveryMonth(
			int totalNumberOfHolidayseveryMonth) {
		this.totalNumberOfHolidayseveryMonth = totalNumberOfHolidayseveryMonth;
	}
	public int getTotalNumberOfDayseveryMonth() {
		return totalNumberOfDayseveryMonth;
	}
	public void setTotalNumberOfDayseveryMonth(int totalNumberOfDayseveryMonth) {
		this.totalNumberOfDayseveryMonth = totalNumberOfDayseveryMonth;
	}
	public int getTotalNumberOfWorkingDaysEveryMonth() {
		return totalNumberOfWorkingDaysEveryMonth;
	}
	public void setTotalNumberOfWorkingDaysEveryMonth(
			int totalNumberOfWorkingDaysEveryMonth) {
		this.totalNumberOfWorkingDaysEveryMonth = totalNumberOfWorkingDaysEveryMonth;
	}
	public int getTotalNumberOfHolidays() {
		return totalNumberOfHolidays;
	}
	public void setTotalNumberOfHolidays(int totalNumberOfHolidays) {
		this.totalNumberOfHolidays = totalNumberOfHolidays;
	}
	public Integer getOtDaysF() {
		return otDaysF;
	}
	public void setOtDaysF(Integer otDaysF) {
		this.otDaysF = otDaysF;
	}
	public Double getOtHoursF() {
		return otHoursF;
	}
	public void setOtHoursF(Double otHoursF) {
		this.otHoursF = otHoursF;
	}
	public Double getTotalOtHoursF() {
		return totalOtHoursF;
	}
	public void setTotalOtHoursF(Double totalOtHoursF) {
		this.totalOtHoursF = totalOtHoursF;
	}
	public Integer getLeaveDaysF() {
		return leaveDaysF;
	}
	public void setLeaveDaysF(Integer leaveDaysF) {
		this.leaveDaysF = leaveDaysF;
	}
	public Double getLeaveHoursF() {
		return leaveHoursF;
	}
	public void setLeaveHoursF(Double leaveHoursF) {
		this.leaveHoursF = leaveHoursF;
	}
	public Double getTotalLeaveHoursF() {
		return totalLeaveHoursF;
	}
	public void setTotalLeaveHoursF(Double totalLeaveHoursF) {
		this.totalLeaveHoursF = totalLeaveHoursF;
	}
	public double getOtSalary() {
		return otSalary;
	}
	public void setOtSalary(double otSalary) {
		this.otSalary = otSalary;
	}
	public double getLeaveDeduction() {
		return leaveDeduction;
	}
	public void setLeaveDeduction(double leaveDeduction) {
		this.leaveDeduction = leaveDeduction;
	}
	public String getComapnyName() {
		return comapnyName;
	}
	public void setComapnyName(String comapnyName) {
		this.comapnyName = comapnyName;
	}
	public String getUnitName() {
		return UnitName;
	}
	public void setUnitName(String unitName) {
		UnitName = unitName;
	}
	public String getEmpUan() {
		return empUan;
	}
	public void setEmpUan(String empUan) {
		this.empUan = empUan;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getUnitDesignation() {
		return unitDesignation;
	}
	public void setUnitDesignation(String unitDesignation) {
		this.unitDesignation = unitDesignation;
	}
	public Integer getPresentDay() {
		return presentDay;
	}
	public void setPresentDay(Integer presentDay) {
		this.presentDay = presentDay;
	}
	public Integer getBaseDay() {
		return baseDay;
	}
	public void setBaseDay(Integer baseDay) {
		this.baseDay = baseDay;
	}
	
}
