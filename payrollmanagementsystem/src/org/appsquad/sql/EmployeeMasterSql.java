package org.appsquad.sql;

public class EmployeeMasterSql {

	public static final String employeeInsertQuery= "insert into pms_employee_master (employee_code,employee_name,company_id,unit_id,employee_phone_number,employee_email,gender,"
													 + " created_by,updatetd_by, dob, unit_designation_id) values(?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String loadCompanyListQuery = "select company_name, company_id from pms_company_master ";
	
	public static final String loadUnitListQuery = "select unit_name, unit_id from pms_unit_master ";
	
	public static final String loadUnitListWrtCompanyQuery = "select unit_name, unit_id from pms_unit_master where company_id = ?";
	
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
	
	public static final String loadComponentDetailsQuery =  " SELECT pcm.component_id, pcm.component_name, pcm.component_type_id, pcmp.company_id, pcmp.unit_id, pctm.component_type, pcmp.amount " +     
																 " FROM pms_component_master pcm, pms_component_master_per_unit pcmp, pms_component_type_master pctm " +
																 " where pcm.component_id = pcmp.component_id " +
																 " and pcmp.component_type_id = pctm.component_type_id" +
																 " and pcmp.company_id = ? and pcmp.unit_id =? and pcmp.designation_id = ? " ;
	public static final String insertComponentsPerEmpQuery = "INSERT INTO pms_employee_salary_components(employee_id, component_id, component_name, component_type_id, company_id, unit_id, created_by, updatetd_by,component_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?) ";
	

	public  static final String loadSavedEmployeeQuery = "SELECT * FROM vw_employee_information ";
	
	public  static final String loadSavedEmployeeFromCompanyQuery = "SELECT * FROM vw_employee_company_data where company_id = ? and unit_id = ?";
	
	public  static final String searchEmployeeQuery = "SELECT * FROM vw_employee_company_data where employee_code LIKE ?";
	
	public  static final String loadEmployeeInfoQuery = "SELECT * FROM vw_employee_information where employee_id = ?";
	
	public  static final String fetchComponentsQuery = "SELECT * FROM vw_salary_component_employee where employee_id = ?";
	
	public static final String updateEmployeeMasterQuery = "UPDATE pms_employee_master SET  employee_name=?, employee_phone_number=?,"
			+ " employee_email=?, gender=?,employee_state_id=?, dob=?,updatetd_by=? WHERE employee_id=? ";
	
	public static final String updateEmployeePersonalQuery = "UPDATE pms_employee_personal_information SET employee_address=?, employee_city=?, "
       +" emp_state_id=?, employee_pincode=?, emp_blood_group_id=?, emp_pan=?, "
       +" emp_marital_status=?, updatetd_by=? WHERE employee_id=?";

	public static final String getEmployeePersonalDataQuery = "select count(employee_id) AS employee_count from  pms_employee_personal_information "
			+ " where employee_id = ? ";
	
	public static final String updateEmployeeOfficeQuery = "UPDATE pms_employee_office_details "
									   		+" SET emp_joining_date=?, emp_designation=?, emp_location=?, "
									   		+" payment_mode_id=?, bank_account_id=?, emp_bank_account_number=?, "
									       +" ifsc=?, increment_date=?, registration_date=?, last_working_date=?, " 
									       +" emp_designation_id=?, updatetd_by=? WHERE employee_id=?";
	
	public static final String getEmployeeOfficeDataQuery = "select count(employee_id) AS employee_count from  pms_employee_office_details "
			+ " where employee_id = ? ";
	
	public static final String updatePfEsiQuery = "UPDATE pms_employee_pf_esi_details SET  uan=?, esi=?, updatetd_by=? WHERE employee_id=?";
	
	public static final String getEmployeeEsiDataQuery = "select count(employee_id) AS employee_count from  pms_employee_pf_esi_details "
			+ " where employee_id = ? ";

	
	public static final String loadUnitDesignationQuery = "SELECT DISTINCT id, designation from vw_unit_designation where company_id = ? and unit_id = ? ";
	
}
