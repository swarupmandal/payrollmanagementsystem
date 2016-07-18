package org.appsquad.bean;

public class StateMasterBean {

	private String stateName;
	
	private int stateId;
	
	private boolean saveButtonDesabled = false;
	
	private boolean updtateButtonDesabled = true;
	
	private String isExist;
	
	private String userName;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public boolean isSaveButtonDesabled() {
		return saveButtonDesabled;
	}

	public void setSaveButtonDesabled(boolean saveButtonDesabled) {
		this.saveButtonDesabled = saveButtonDesabled;
	}

	public boolean isUpdtateButtonDesabled() {
		return updtateButtonDesabled;
	}

	public void setUpdtateButtonDesabled(boolean updtateButtonDesabled) {
		this.updtateButtonDesabled = updtateButtonDesabled;
	}

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
