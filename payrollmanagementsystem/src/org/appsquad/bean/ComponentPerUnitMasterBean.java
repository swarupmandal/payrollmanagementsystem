package org.appsquad.bean;

public class ComponentPerUnitMasterBean {
	
	
	private int companyId;
	private int unitId;
	
	private String componet;
	private int componentId;
	
	private String componentType;
	private int componentTypeId;
	
	private boolean checkVal = false;
	
	private Integer count;
	
	
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getComponet() {
		return componet;
	}
	public void setComponet(String componet) {
		this.componet = componet;
	}
	public int getComponentId() {
		return componentId;
	}
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public int getComponentTypeId() {
		return componentTypeId;
	}
	public void setComponentTypeId(int componentTypeId) {
		this.componentTypeId = componentTypeId;
	}
	public boolean isCheckVal() {
		return checkVal;
	}
	public void setCheckVal(boolean checkVal) {
		this.checkVal = checkVal;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
