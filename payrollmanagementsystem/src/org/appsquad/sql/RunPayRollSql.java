package org.appsquad.sql;

public class RunPayRollSql {
	
	public static final String loadMonthQuery = " SELECT month_id, month FROM pms_month_master ";
	
	
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
	
	public static final String loadEmpSalaryDetails = " SELECT pesc.component_name, pesc.component_type_id, " +
													  " pesc.component_amount, pctm.component_type " +
													  " FROM pms_employee_salary_components pesc, " +
													  " pms_component_type_master pctm where " + 
													  " pesc.component_type_id = pctm.component_type_id " +
													  " and employee_id = ? ";

	
	
	

}
