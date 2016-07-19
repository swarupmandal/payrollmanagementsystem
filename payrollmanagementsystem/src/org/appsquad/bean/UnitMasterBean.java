package org.appsquad.bean;

public class UnitMasterBean {
	private int unitId,companyId;
	private String unitName,unitAddress,userName,companyName;
	
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

}
