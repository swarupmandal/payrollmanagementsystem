package org.appsquad.sql;

public class UnitMasterSqlQuery {
	public static final String onLoadUnitMasterQuery = " select pum.unit_name,pum.unit_id,pcm.company_name,pcm.company_id, "
														+ " pum.base_days_type_id, pum.working_hour, pum.wages_type_id, pum.sunday_sel_id, "
														+ " pbdtm.days_type, pwt.wages_type, psc.sunday_select "
														+ " from "
														+ " pms_unit_master pum "
														+ " join pms_company_master pcm "
														+ " on pcm.company_id = pum.company_id "
														+ " join pms_base_days_type_master pbdtm "
														+ " on pum.base_days_type_id = pbdtm.id "
														+ " join pms_wages_type pwt "
														+ " on pum.wages_type_id = pwt.id "
														+ " join pms_sunday_selection psc "
														+ " on pum.sunday_sel_id = psc.id "
														+ " where pum.is_delete = 'N' ";
	
	public static final String onLoadUnitQuery = "select * from vw_unit_details";
	
	public static final String insertUnitMasterQuery = "insert into pms_unit_master (unit_name,unit_address,company_id,created_by, base_days_type_id, working_hour, wages_type_id, sunday_sel_id) values(?,?,?,?,?,?,?,?)";
	
	public static final String updateUnitMasterQuery = "update pms_unit_master set unit_name = ?,unit_address=?, updated_by = ? where unit_id = ? ";
	
	public static final String deleteUnitMasterQuery = "update pms_unit_master set is_delete = 'Y', updated_by = ? where unit_id = ?  ";
	
	public static final String loadDayTypeQuery = "select id, days_type from pms_base_days_type_master ";
	
	public static final String loadWagesTypeQuery = "select id, wages_type from pms_wages_type";
	
	public static final String loadSundaySelectionQuery = "select id, sunday_select from pms_sunday_selection";
	
	public static final String upDateUnitQuery = "update pms_unit_master set unit_name = ?, base_days_type_id = ?, working_hour = ?, wages_type_id = ?, sunday_sel_id=  ? where unit_id = ? ";
	
}
