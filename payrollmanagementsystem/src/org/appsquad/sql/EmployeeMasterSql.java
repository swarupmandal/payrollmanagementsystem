package org.appsquad.sql;

public class EmployeeMasterSql {

	public static final String employeeInsertQuery= "insert into pms_employee_master (employee_code,employee_name,company_id,unit_id,employee_phone_number,employee_email,gender,"
													 + " created_by,updatetd_by) values(?,?,?,?,?,?,?,?,?)";
	
	public static final String loadCompanyListQuery = "select company_name, company_id from pms_company_master ";
	
	public static final String loadUnitListQuery = "select unit_name, unit_id from unit_master ";
	
	public static final String insertPersoalInformationQuery = "insert into pms_employee_personal_information (employee_id,employee_address,employee_city,emp_state_id,employee_pincode,emp_blood_groop_id,emp_pan,emp_marital_status) "
																+ "	values(?,?,?,?,?,?,?,?) ";
	
	
	
}
