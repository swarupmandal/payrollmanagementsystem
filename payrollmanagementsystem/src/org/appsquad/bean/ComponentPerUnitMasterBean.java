package org.appsquad.bean;

public class ComponentPerUnitMasterBean {
	
	
	private int companyId;
	private int unitId;
	
	private String componet;
	private int componentId;
	
	private String componentType;
	private int componentTypeId;
	
	private double desCompoAmount;
	private Double workinghour;
	private double avd;
	
	
	private int unitDesignationId;
	private int baseDays;
	
	
	private boolean checkVal = false;
	
	private Integer count;
	public String userName;
	
	public int id;
	
	public boolean saveButtnVisibles = true;
	
	public boolean updateButtonVisible = false;
	
	
	
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
	
	public Double getWorkinghour() {
		return workinghour;
	}
	public void setWorkinghour(Double workinghour) {
		this.workinghour = workinghour;
	}
	
	public double getDesCompoAmount() {
		return desCompoAmount;
	}
	public void setDesCompoAmount(double desCompoAmount) {
		this.desCompoAmount = desCompoAmount;
	}
	public double getAvd() {
		return avd;
	}
	public void setAvd(double avd) {
		this.avd = avd;
	}
	public int getBaseDays() {
		return baseDays;
	}
	public void setBaseDays(int baseDays) {
		this.baseDays = baseDays;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUnitDesignationId() {
		return unitDesignationId;
	}
	public void setUnitDesignationId(int unitDesignationId) {
		this.unitDesignationId = unitDesignationId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isSaveButtnVisibles() {
		return saveButtnVisibles;
	}
	public void setSaveButtnVisibles(boolean saveButtnVisibles) {
		this.saveButtnVisibles = saveButtnVisibles;
	}
	public boolean isUpdateButtonVisible() {
		return updateButtonVisible;
	}
	public void setUpdateButtonVisible(boolean updateButtonVisible) {
		this.updateButtonVisible = updateButtonVisible;
	}

}
