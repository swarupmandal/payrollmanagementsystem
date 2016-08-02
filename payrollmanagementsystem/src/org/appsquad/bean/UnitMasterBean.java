package org.appsquad.bean;

public class UnitMasterBean {
	private int unitId,companyId, baseDaysTypeId;
	private String unitName,unitAddress,userName,companyName,baseDaysType;
	private boolean readOnly = true;
	private Double workingHour;
	
	public UnitMasterBean() {
		// TODO Auto-generated constructor stub
	}

	public UnitMasterBean(int unitId, int companyId, String unitName,
			String companyName) {
		super();
		this.unitId = unitId;
		this.companyId = companyId;
		this.unitName = unitName;
		this.companyName = companyName;
	}


	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
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

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public int getBaseDaysTypeId() {
		return baseDaysTypeId;
	}

	public void setBaseDaysTypeId(int baseDaysTypeId) {
		this.baseDaysTypeId = baseDaysTypeId;
	}

	public String getBaseDaysType() {
		return baseDaysType;
	}

	public void setBaseDaysType(String baseDaysType) {
		this.baseDaysType = baseDaysType;
	}

	public Double getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(Double workingHour) {
		this.workingHour = workingHour;
	}

	

}
