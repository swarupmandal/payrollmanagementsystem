package org.appsquad.sql;

public class EmployeeMasterSql {

	public static final String employeeInsertQuery= "insert into pms_employee_master (employee_code,employee_name,company_id,unit_id,employee_phone_number,employee_email,gender,"
													 + " created_by,updatetd_by) values(?,?,?,?,?,?,?,?,?)";
	
	public static final String loadCompanyListQuery = "select company_name, company_id from pms_company_master ";
	
	public static final String loadUnitListQuery = "select unit_name, unit_id from pms_unit_master ";
	
	public static final String insertPersoalInformationQuery = "insert into pms_employee_personal_information (employee_id,employee_address,employee_city,emp_state_id,employee_pincode,emp_blood_group_id,emp_pan,emp_marital_status,created_by,updatetd_by) "
																+ "	values(?,?,?,?,?,?,?,?,?,?) ";
	public static final String selectMaxEmployeeID = "select max(employee_id) as employee_id from pms_employee_master ";
	
	public static final String loadStateListQuery = "select state_name, id from pms_state_master where is_delete = 'N' ";
	
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
																
	public static final String empPfEsiInsertQuery = "INSERT INTO pms_employee_pf_esi_details(employee_id, uan, esi, created_by, updatetd_by)VALUES (?, ?, ?, ?, ?) ";
	
	public static final String loadComponentDetailsQuery =  " SELECT pcm.component_id, pcm.component_name, pcm.component_type_id, pcmp.company_id, pcmp.unit_id, pctm.component_type " +     
																 " FROM pms_component_master pcm, pms_component_master_per_unit pcmp, pms_component_type_master pctm " +
																 " where pcm.component_id = pcmp.component_id " +
																 " and pcmp.component_type_id = pctm.component_type_id" +
																 " and pcmp.company_id = ? and pcmp.unit_id =? " ;
	public static final String insertComponentsPerEmpQuery = "INSERT INTO pms_employee_salary_components(employee_id, component_id, component_name, component_type_id, company_id, unit_id, created_by, updatetd_by,component_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?) ";
	

	public  static final String loadSavedEmployeeQuery = "SELECT * FROM vw_employee_company_data ";
	
	public  static final String searchEmployeeQuery = "SELECT * FROM vw_employee_company_data where employee_code LIKE ?";
}
