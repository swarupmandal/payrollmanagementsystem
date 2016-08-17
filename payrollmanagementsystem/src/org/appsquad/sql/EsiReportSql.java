package org.appsquad.sql;

public class EsiReportSql {

	public static final String selectLvYrQuery = "select distinct leave_yr from pms_emp_sal_store where is_delete = 'N'";
	
	public static final String loadUnitDesignationQuery = "SELECT DISTINCT id, designation from vw_unit_designation where company_id = ? and unit_id = ? ";
	
	public static final String loadEsiReportQuery = "select amount,wages, esi_num, presenet_days, emp_name from  vw_esi_data where salary_month = ? and leave_yr = ? "
											        + " and company_id = ? and unit_id = ? and unit_desiganatoin_id = ?";
	
}
