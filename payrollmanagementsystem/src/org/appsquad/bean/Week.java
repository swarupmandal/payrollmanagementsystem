package org.appsquad.bean;

public class Week {
	private boolean weekChecked = false;
	private String weekName ;
	private String weekId;
	private String selectedWeek;
	
	
	
	
	public Week() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Week(boolean weekChecked, String weekName, String weekId) {
		super();
		this.weekChecked = weekChecked;
		this.weekName = weekName;
		this.weekId = weekId;
	}
	public boolean isWeekChecked() {
		return weekChecked;
	}
	public void setWeekChecked(boolean weekChecked) {
		this.weekChecked = weekChecked;
	}
	
	
	public String getWeekName() {
		return weekName;
	}
	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}
	public String getWeekId() {
		return weekId;
	}
	public void setWeekId(String weekId) {
		this.weekId = weekId;
	}
	public String getSelectedWeek() {
		return selectedWeek;
	}
	public void setSelectedWeek(String selectedWeek) {
		this.selectedWeek = selectedWeek;
	}
	
	
	
}
