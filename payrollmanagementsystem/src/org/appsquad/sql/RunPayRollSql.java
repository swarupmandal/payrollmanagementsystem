package org.appsquad.sql;

public class RunPayRollSql {
	
	public static final String loadMonthQuery = " SELECT month_id, month FROM pms_month_master ";
	
	public static final String loadSheetTypeQuery = " SELECT salary_sheet_type_id,sheet_type FROM pms_salary_sheet_type where is_delete='N' ";
	
	public static final String loadComponentQuery = "  select distinct component_id,component_name,component_type_id "
				+ " from pms_employee_salary_components where company_id=?  and  unit_id =? ";
	
	public static final String loadEmpDetailsQuery = " select pem.employee_id, pem.employee_code, " +
														" pem.employee_name, " +
														" pdm.designation,peped.uan,peped.esi " +
														" from pms_employee_master pem, " + 
														" pms_employee_office_details ped, " + 
														" pms_designation_master pdm, " +
														" pms_employee_pf_esi_details peped " +
														" where pem.employee_id = ped.employee_id " +
														" and pdm.id = ped.emp_designation_id " +
														" and peped.employee_id = pem.employee_id " +
														" and pem.company_id = ? and pem.unit_id = ?";
	
	
	public static final String loadEmpDetailsQuery2 = " select employee_id, employee_code, employee_name, "
													  + " emp_designation, emp_designation_id,unit_designation, pf_number, uan_number, "
													  + " esi from vw_employee_information where company_id = ? and unit_id = ? "
													  + " and unit_designation_id = ? and is_delete = 'N' ";
	
	
	public static final String loadWagesQuery = "SELECT component_amount from pms_employee_salary_components where employee_id = ? and component_name = 'WAGES'";
	
	
	
	public static final String loadEmpSalaryDetails = " SELECT pesc.component_name, pesc.component_type_id, " +
													  " pesc.component_amount, pctm.component_type " +
													  " FROM pms_employee_salary_components pesc, " +
													  " pms_component_type_master pctm where " + 
													  " pesc.component_type_id = pctm.component_type_id " +
													  " and employee_id = ? ";

	public static final String loadEmpcomponentSalaryDetails = " SELECT pesc.component_id, pesc.component_name, pesc.component_type_id, " +
																	  " pesc.component_amount, pctm.component_type " +
																	  " FROM pms_employee_salary_components pesc, " +
																	  " pms_component_type_master pctm where " + 
																	  " pesc.component_type_id = pctm.component_type_id " +
																	  " and employee_id = ? and pesc.is_delete = 'N'";
														  	
	
	public static final String loadSundayCount = "select total_no_of_days  FROM pms_weekly_holiday_master where day_id = 1 and leave_year_id = ? and company_id = ? and unit_id = ?  ";
		
	public static final String loadMondayCount = "select total_no_of_days  FROM pms_weekly_holiday_master where day_id = 2 and leave_year_id = ? and company_id = ? and unit_id = ?  ";

	public static final String loadTuesdayCount = "select total_no_of_days  FROM pms_weekly_holiday_master where day_id = 3 and leave_year_id = ? and company_id = ? and unit_id = ?  ";

	public static final String loadWendsdayCount = "select total_no_of_days  FROM pms_weekly_holiday_master where day_id = 4 and leave_year_id = ? and company_id = ? and unit_id = ?  ";

	public static final String loadThursdayCount = "select total_no_of_days  FROM pms_weekly_holiday_master where day_id = 5 and leave_year_id = ? and company_id = ? and unit_id = ?  ";

	public static final String loadFridayCount = "select total_no_of_days  FROM pms_weekly_holiday_master where day_id = 6 and leave_year_id = ? and company_id = ? and unit_id = ?  ";

	public static final String loadSaturdayCount = "select total_no_of_days  FROM pms_weekly_holiday_master where day_id = 7 and leave_year_id = ? and company_id = ? and unit_id = ?  ";

	public static final String loadGeneralHoliDayCount = "select count(id) as holiday_count from pms_general_holiday_master where leave_year_id = ? and company_id = ? and unit_id = ? and month_id = ? ";
	
	public static final String loadGeneralHoliDayDates = "select (select extract(day from holiday_date)) as holiday_date from pms_general_holiday_master where leave_year_id = ? and company_id = ? and unit_id = ? and month_id = ? ";
	
	public static final String loadHoursPerDay = "select hour_per_day from pms_working_hours_per_unit where company_id = ? and unit_id = ? and leave_year_id = ? " ; 
	
	public static final String getUnitDayTypeQuery = "select base_days_type_id from pms_unit_master where unit_id = ?";
	
	public static final String empInsertSalStore = " INSERT INTO pms_emp_sal_store( " +
										            " employee_id, employee_code, company_id, " + 
										            " unit_id, unit_desiganatoin_id, emp_designation_id, presenet_days, " + 
										            " base_days, ot, pf_num, esi_num, uan_num, salary_sheet_generation_date, " + 
										            " salary_month_id, salary_month, leave_yr, wages, holiday_amount, " + 
										            " ot_salary, total_salary, total_deduction, net_salary, " + 
										            " created_by, updatetd_by, emp_name) " +
										            " VALUES (?, ?, ?, ?, " + 
										            " ?, ?, ?, ?, " + 
										            " ?, ?, ?, ?, ?, ?, " + 
										            " ?, ?, ?, ?, ?, " + 
										            " ?, ?, ?, ?, ?, " + 
										            " ?) ";

	public static final String empInsertSalDetailsStore = "INSERT INTO pms_emp_sal_store_comp_details( "
            										+" component_id, component_name, component_type_id, "
						            				+" emp_id, emp_code, month, year, amount) "
						            				+" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
	
	public static final String empUpdateSalStore = " UPDATE pms_emp_sal_store SET" +
									            " presenet_days=?,base_days=?, ot=?, pf_num=?, esi_num=?, uan_num=?, salary_sheet_generation_date=?,"+
									            " wages=?, holiday_amount=?,ot_salary=?, total_salary=?, total_deduction=?, net_salary=?,"+
									            " updatetd_by=?, emp_name=? WHERE employee_id=? and employee_code=? and salary_month=? and leave_yr = ?";

	public static final String empUpdateSalDetailsStore = "UPDATE pms_emp_sal_store_comp_details SET "
												+" amount=?  WHERE emp_id=? and emp_code=? and month=? "
												+" and year = ? and component_id=?";
	
	public static final String empSalStoreSelectQuery = "SELECT employee_id , " +
														"  employee_code , " +
														"  company_id , " +
														"  unit_id , " +
														"  unit_desiganatoin_id , " +
														"  emp_designation_id , " +
														"  presenet_days , " +
														"  pf_num , " +
														"  esi_num , " +
														"  uan_num , " +
														"  salary_month_id , " +
														"  salary_month , " +
														"  leave_yr , " +
														"  net_salary , " +
														"  is_delete , wages, " +
														"  emp_name, emp_designation, " +
														"  holiday_amount, ed_amt, total_salary, " +
														" total_deduction, ed, " +
														" company_name, unit_name, designation " +
														" FROM vw_emp_sal_store " +
														" WHERE company_id         = ? " +
														" AND unit_id              = ? " +
														" AND unit_desiganatoin_id = ? " +
														" AND salary_month      = ? " +
														" AND leave_yr             = ? ";
															
	
	public static final String empSalStoreCompoDetlQuery = "select component_id, component_type_id, component_name,emp_id, " +
														   " month, year, amount from pms_emp_sal_store_comp_details where emp_id = ? " +
														   " and month = ? and year = ?";
	
	public static final String wagesTypeSelQry = "select wages_type_id from pms_unit_master where unit_id = ? and company_id = ? ";
	
	
}
