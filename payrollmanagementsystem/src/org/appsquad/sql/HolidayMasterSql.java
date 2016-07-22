package org.appsquad.sql;

public class HolidayMasterSql {

	public static final String loadWeekDayQuery = "SELECT id, day FROM pms_day_master ";
	
	public static final String saveWeekLyHollyDayMasterData = "INSERT INTO pms_weekly_holiday_master(day, week, day_id, created_by, updated_by, leave_year_id) VALUES (?,?, ?, ?, ?, ?) ";

	public static final String loadWeeklyHollyDayMasterData = "SELECT day, id, week, day_id, leave_year_id FROM pms_weekly_holiday_master ";
	
	public static final String deleteWeekHoliDayMastetData = "delete from pms_weekly_holiday_master where id = ? ";
	
	public static final String saveGeneralHoliDayMasterData = "INSERT INTO pms_general_holiday_master(date, holiday_name, created_by, updated_by, leave_year_id) VALUES (?, ?, ?, ?, ?) ";
	
	public static final String loadGeneralHoliDayMasterData = "SELECT id, date, holiday_name, leave_year_id FROM pms_general_holiday_master ";
	
	public static final String deleteGeneralHoliDayMasterData = "DELETE FROM pms_general_holiday_master WHERE id = ? ";
	

}
