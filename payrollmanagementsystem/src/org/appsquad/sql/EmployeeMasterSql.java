package org.appsquad.sql;

public class EmployeeMasterSql {

	public static final String employeeInsertQuery= "insert into pms_employee_master (employee_code,employee_name,company_id,unit_id,employee_phone_number,employee_email,gender,"
													 + " created_by,updatetd_by) values(?,?,?,?,?,?,?,?,?)";
	
	public static final String loadCompanyListQuery = "select company_name, company_id from pms_company_master ";
	
	public static final String loadUnitListQuery = "select unit_name, unit_id from unit_master ";
	
	public static final String insertPersoalInformationQuery = "insert into pms_employee_personal_information (employee_id,employee_address,employee_city,emp_state_id,employee_pincode,emp_blood_group_id,emp_pan,emp_marital_status,created_by,updatetd_by) "
																+ "	values(?,?,?,?,?,?,?,?,?,?) ";
	public static final String selectMaxEmployeeID = "select max(employee_id) as employee_id from pms_employee_master ";
	
	public static final String loadStateListQuery = "select state_name, id from pms_state_master";
	
	public static final String loadBllodGroupListQuery = "select bloodgroup_id, bloodgroup_name from pms_bloodgroup_master";
	
	public static final String loadEmployeeDesignationQuery = "SELECT id, designation FROM pms_designation_master ";
	
	public static final String loadEmployeePaymodeQuery = "SELECT id, payment_mode FROM pms_payment_master ";
	
	public static final String loadEmployeeBankQuery = "SELECT id, bank_name FROM pms_bank_master ";
	
	public static final String insertEmployeeOfficialDetails = "INSERT " +
																		"INTO pms_employee_office_details " +
																		"  ( " +
																		"    employee_id, " +
																		"    emp_joining_date, " +
																		"    emp_designation_id, " +
																		"    emp_location, " +
																		"    payment_mode_id, " +
																		"    bank_account_id, " +
																		"    emp_bank_account_number, " +
																		"    ifsc, " +
																		"    increment_date, " +
																		"    registration_date, " +
																		"    last_working_date, " +
																		"    created_by, " +
																		"    updatetd_by " +
																		"  ) " +
																		"  VALUES " +
																		"  ( " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ?, " +
																		"    ? " +
																		"  )";
																
	
	
	
}
