package org.appsquad.bean;

import java.sql.Date;
import java.util.ArrayList;

public class EmployeeMasterBean {

	private String companyName,userId;
	private int companyId;
	private String UnitName;
	private int unitId;
	private String employeeCode;
	private Integer employeeid;
	private String employeeName;
	private String gender;
	private int genderId;
	private String empEmail;
	private String empPhone;
	private Date empDob;
	private String empDobValue;
	
	private String empAddress;
	private String empCity;
	private String empState;
	private int empStateId;
	private String pinCode;
	private String empBloodGroup;
	private int empBloodGroupId;
	private String empPan;
	private String empMaritalStatus;
	
	private Date empDoj;
	private String empDesignation;
	private int empDesignationId;
	private String empLocation;
	private double salary;
	private String paymentMode;
	private int paymentModeId;
	private String empBankAccount;
	private int empBankId;
	private String empAccountNo;
	private String ifscCode;
	private Date incrementDate;
	private String incrementDateValue;
	private Date registrationDate;
	private String registrationDateValue;
	private Date lastWorkingDate;
	private String lastWorkingDateValue;
	
	private boolean checkSelect = false;
	private String componentName;
	private Integer componentId;
	private String componentType;
	private Integer componentTypeId;
	
	private String unitDesignation;
	private Integer unitDesignationId;
	
	private BloodGroupBean bloodGroupBean;
	
	
	private boolean pfCheckValue = false;
	private String pfNumber = "0";
	private boolean pfFielddisabled = true;
	private String uan;
	private boolean uanFieldDisabled = true;
	
	private boolean esiCheckValue = false;
	private String esi;
	private boolean esiFieldDisabled = true;
	
	private boolean div1Visblity = false;
	private boolean div2Visblity = false;
	private ComponentMasterBean componentMasterBean = new ComponentMasterBean();
	private ArrayList<ComponentMasterBean> componentMasterBeanList = new ArrayList<ComponentMasterBean>();
	
	
	public EmployeeMasterBean(){
		super();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getUnitName() {
		return UnitName;
	}

	public void setUnitName(String unitName) {
		UnitName = unitName;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public Date getEmpDob() {
		return empDob;
	}

	public void setEmpDob(Date empDob) {
		this.empDob = empDob;
	}

	public String getEmpDobValue() {
		return empDobValue;
	}

	public void setEmpDobValue(String empDobValue) {
		this.empDobValue = empDobValue;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	

	public String getEmpState() {
		return empState;
	}

	public void setEmpState(String empState) {
		this.empState = empState;
	}

	public int getEmpStateId() {
		return empStateId;
	}

	public void setEmpStateId(int empStateId) {
		this.empStateId = empStateId;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getEmpBloodGroup() {
		return empBloodGroup;
	}

	public void setEmpBloodGroup(String empBloodGroup) {
		this.empBloodGroup = empBloodGroup;
	}

	public int getEmpBloodGroupId() {
		return empBloodGroupId;
	}

	public void setEmpBloodGroupId(int empBloodGroupId) {
		this.empBloodGroupId = empBloodGroupId;
	}

	public String getEmpPan() {
		return empPan;
	}

	public void setEmpPan(String empPan) {
		this.empPan = empPan;
	}

	public String getEmpMaritalStatus() {
		return empMaritalStatus;
	}

	public void setEmpMaritalStatus(String empMaritalStatus) {
		this.empMaritalStatus = empMaritalStatus;
	}

	public Date getEmpDoj() {
		return empDoj;
	}

	public void setEmpDoj(Date empDoj) {
		this.empDoj = empDoj;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	

	public String getEmpBankAccount() {
		return empBankAccount;
	}

	public void setEmpBankAccount(String empBankAccount) {
		this.empBankAccount = empBankAccount;
	}

	

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public Date getIncrementDate() {
		return incrementDate;
	}

	public void setIncrementDate(Date incrementDate) {
		this.incrementDate = incrementDate;
	}

	public String getIncrementDateValue() {
		return incrementDateValue;
	}

	public void setIncrementDateValue(String incrementDateValue) {
		this.incrementDateValue = incrementDateValue;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getRegistrationDateValue() {
		return registrationDateValue;
	}

	public void setRegistrationDateValue(String registrationDateValue) {
		this.registrationDateValue = registrationDateValue;
	}

	public Date getLastWorkingDate() {
		return lastWorkingDate;
	}

	public void setLastWorkingDate(Date lastWorkingDate) {
		this.lastWorkingDate = lastWorkingDate;
	}

	public String getLastWorkingDateValue() {
		return lastWorkingDateValue;
	}

	public void setLastWorkingDateValue(String lastWorkingDateValue) {
		this.lastWorkingDateValue = lastWorkingDateValue;
	}

	public boolean isPfCheckValue() {
		return pfCheckValue;
	}

	public void setPfCheckValue(boolean pfCheckValue) {
		this.pfCheckValue = pfCheckValue;
	}

	public String getUan() {
		return uan;
	}

	public void setUan(String uan) {
		this.uan = uan;
	}

	public boolean isUanFieldDisabled() {
		return uanFieldDisabled;
	}

	public void setUanFieldDisabled(boolean uanFieldDisabled) {
		this.uanFieldDisabled = uanFieldDisabled;
	}

	public boolean isEsiCheckValue() {
		return esiCheckValue;
	}

	public void setEsiCheckValue(boolean esiCheckValue) {
		this.esiCheckValue = esiCheckValue;
	}

	public String getEsi() {
		return esi;
	}

	public void setEsi(String esi) {
		this.esi = esi;
	}

	public boolean isEsiFieldDisabled() {
		return esiFieldDisabled;
	}

	public void setEsiFieldDisabled(boolean esiFieldDisabled) {
		this.esiFieldDisabled = esiFieldDisabled;
	}







	public String getEmpPhone() {
		return empPhone;
	}







	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}







	public String getEmpCity() {
		return empCity;
	}







	public void setEmpCity(String empCity) {
		this.empCity = empCity;
	}







	public int getEmpDesignationId() {
		return empDesignationId;
	}







	public void setEmpDesignationId(int empDesignationId) {
		this.empDesignationId = empDesignationId;
	}







	public String getEmpLocation() {
		return empLocation;
	}







	public void setEmpLocation(String empLocation) {
		this.empLocation = empLocation;
	}







	public int getPaymentModeId() {
		return paymentModeId;
	}







	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}



	public int getEmpBankId() {
		return empBankId;
	}







	public void setEmpBankId(int empBankId) {
		this.empBankId = empBankId;
	}







	public String getEmpAccountNo() {
		return empAccountNo;
	}







	public void setEmpAccountNo(String empAccountNo) {
		this.empAccountNo = empAccountNo;
	}







	public boolean isCheckSelect() {
		return checkSelect;
	}







	public void setCheckSelect(boolean checkSelect) {
		this.checkSelect = checkSelect;
	}







	public String getComponentName() {
		return componentName;
	}







	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}







	public Integer getComponentId() {
		return componentId;
	}







	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}







	public String getComponentType() {
		return componentType;
	}







	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}







	public Integer getComponentTypeId() {
		return componentTypeId;
	}







	public void setComponentTypeId(Integer componentTypeId) {
		this.componentTypeId = componentTypeId;
	}







	public Integer getEmployeeid() {
		return employeeid;
	}







	public void setEmployeeid(Integer employeeid) {
		this.employeeid = employeeid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ArrayList<ComponentMasterBean> getComponentMasterBeanList() {
		return componentMasterBeanList;
	}

	public void setComponentMasterBeanList(
			ArrayList<ComponentMasterBean> componentMasterBeanList) {
		this.componentMasterBeanList = componentMasterBeanList;
	}

	public String getUnitDesignation() {
		return unitDesignation;
	}

	public void setUnitDesignation(String unitDesignation) {
		this.unitDesignation = unitDesignation;
	}

	public Integer getUnitDesignationId() {
		return unitDesignationId;
	}

	public void setUnitDesignationId(Integer unitDesignationId) {
		this.unitDesignationId = unitDesignationId;
	}

	public BloodGroupBean getBloodGroupBean() {
		return bloodGroupBean;
	}

	public void setBloodGroupBean(BloodGroupBean bloodGroupBean) {
		this.bloodGroupBean = bloodGroupBean;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public boolean isPfFielddisabled() {
		return pfFielddisabled;
	}

	public void setPfFielddisabled(boolean pfFielddisabled) {
		this.pfFielddisabled = pfFielddisabled;
	}

	public boolean isDiv1Visblity() {
		return div1Visblity;
	}

	public void setDiv1Visblity(boolean div1Visblity) {
		this.div1Visblity = div1Visblity;
	}

	public boolean isDiv2Visblity() {
		return div2Visblity;
	}

	public void setDiv2Visblity(boolean div2Visblity) {
		this.div2Visblity = div2Visblity;
	}

	public ComponentMasterBean getComponentMasterBean() {
		return componentMasterBean;
	}

	public void setComponentMasterBean(ComponentMasterBean componentMasterBean) {
		this.componentMasterBean = componentMasterBean;
	}
	
	
	
	
	
	
}
