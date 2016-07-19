package org.appsquad.bean;

public class BloodGroupBean {

	private String bloodGroupName;
	private int bloodGroupId;
	
	
	public BloodGroupBean(String bloodGroupName, int bloodGroupId) {
		super();
		this.bloodGroupName = bloodGroupName;
		this.bloodGroupId = bloodGroupId;
	}
	
	public BloodGroupBean() {
		super();
		
	}

	public String getBloodGroupName() {
		return bloodGroupName;
	}
	public void setBloodGroupName(String bloodGroupName) {
		this.bloodGroupName = bloodGroupName;
	}
	public int getBloodGroupId() {
		return bloodGroupId;
	}
	public void setBloodGroupId(int bloodGroupId) {
		this.bloodGroupId = bloodGroupId;
	}
	
	
}
