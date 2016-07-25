package org.appsquad.sql;

public class HolidayMasterSql {

	public static final String loadLeaveYear = "SELECT id, start_date, end_date FROM pms_leave_year_master where id =(select max(id) from pms_leave_year_master )";
	
	public static final String loadWeekDayQuery = "SELECT id, day FROM pms_day_master ";
	
	public static final String  saveWorkingHourPerDay = "INSERT INTO pms_working_hours_per_unit(company_id, unit_id, leave_year_id, hour_per_day, created_by,updated_by ) VALUES (?, ?, ?, ?, ?, ?) "; 
	
	public static final String saveWeekLyHollyDayMasterData = "INSERT INTO pms_weekly_holiday_master(day, week, day_id, created_by, updated_by, leave_year_id, company_id, unit_id) VALUES (?,?, ?, ?, ?, ?, ?, ?) ";

	public static final String loadWeeklyHollyDayMasterData = "SELECT day, id, week, day_id, leave_year_id FROM pms_weekly_holiday_master ";
	
	public static final String deleteWeekHoliDayMastetData = "delete from pms_weekly_holiday_master where id = ? ";
	
	public static final String saveGeneralHoliDayMasterData = "INSERT INTO pms_general_holiday_master(date, holiday_name, created_by, updated_by, leave_year_id, company_id, unit_id) VALUES (?, ?, ?, ?, ?, ?, ?) ";
	
	public static final String loadGeneralHoliDayMasterData = "SELECT id, date, holiday_name, leave_year_id FROM pms_general_holiday_master ";
	
	public static final String deleteGeneralHoliDayMasterData = "DELETE FROM pms_general_holiday_master WHERE id = ? ";
	

}
