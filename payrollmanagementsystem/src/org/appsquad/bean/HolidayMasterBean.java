package org.appsquad.bean;

import java.sql.Date;

public class HolidayMasterBean {

	private int leaveYrId;
	
	private Date leavYrStartDate;
	private Date leavYrEndDate;
	
	
	private int weeklyHoiDayMasterId;
	private int weeklyHoliDayId;
	private String weeklyHoliDayName;
	private String week;
	private Integer weekDayCount;
	
	
	public int getLeaveYrId() {
		return leaveYrId;
	}
	public void setLeaveYrId(int leaveYrId) {
		this.leaveYrId = leaveYrId;
	}
	public Date getLeavYrStartDate() {
		return leavYrStartDate;
	}
	public void setLeavYrStartDate(Date leavYrStartDate) {
		this.leavYrStartDate = leavYrStartDate;
	}
	
	public int getWeeklyHoliDayId() {
		return weeklyHoliDayId;
	}
	public void setWeeklyHoliDayId(int weeklyHoliDayId) {
		this.weeklyHoliDayId = weeklyHoliDayId;
	}
	public String getWeeklyHoliDayName() {
		return weeklyHoliDayName;
	}
	public void setWeeklyHoliDayName(String weeklyHoliDayName) {
		this.weeklyHoliDayName = weeklyHoliDayName;
	}
	public Date getLeavYrEndDate() {
		return leavYrEndDate;
	}
	public void setLeavYrEndDate(Date leavYrEndDate) {
		this.leavYrEndDate = leavYrEndDate;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getWeeklyHoiDayMasterId() {
		return weeklyHoiDayMasterId;
	}
	public void setWeeklyHoiDayMasterId(int weeklyHoiDayMasterId) {
		this.weeklyHoiDayMasterId = weeklyHoiDayMasterId;
	}
	public Integer getWeekDayCount() {
		return weekDayCount;
	}
	public void setWeekDayCount(Integer weekDayCount) {
		this.weekDayCount = weekDayCount;
	}
	
}
