package org.appsquad.sql;

public class PfReportSql {

	public static final String loadPfReportQuery = "select amount,wages, pf_num, presenet_days, emp_name from  vw_pf_data where salary_month = ? and leave_yr = ? "
	        + " and company_id = ? and unit_id = ? and unit_desiganatoin_id = ?";
	
	public static final String loadPtReportQuery = "select amount,wages, presenet_days, emp_name from  vw_pt_data where salary_month = ? and leave_yr = ? "
	        + " and company_id = ? and unit_id = ? and unit_desiganatoin_id = ?";
	
	
}
