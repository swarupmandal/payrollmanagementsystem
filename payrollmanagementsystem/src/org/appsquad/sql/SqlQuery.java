package org.appsquad.sql;

public class SqlQuery {

	public static final String loginQuery = "Select * from pms_admin_users where user_id = ? and password = ? ";
	
	/**-------------------------------------------------------State Master---------------------------------------------------------------------------**/
	
	public static final String onLoadStateQuery = "select * from pms_state_master where is_delete = 'N' ";
	public static final String insertStateQuery = "insert into pms_state_master (state_name,created_by) values(?,?)";
	public static final String updateStateQuery = "update pms_state_master set state_name = ?, updated_by = ? where id = ? ";
	public static final String deleteStateQuery = "update pms_state_master set is_delete = 'Y', updated_by = ? where id = ?  ";
	
	
	/**-------------------------------------------------------Paymode Master---------------------------------------------------------------------------**/
	
	public static final String onLoadPaymentQuery = "select * from pms_payment_master where is_delete = 'N' ";
	public static final String insertPaymentQuery = "insert into pms_payment_master (payment_mode) values(?)";
	public static final String updatePaymentQuery = "update pms_payment_master set payment_mode = ? where id = ? ";
	public static final String deletePaymentQuery = "update pms_payment_master set is_delete = 'Y' where id = ? ";
	

	
	/**-------------------------------------------------------Paymode Master---------------------------------------------------------------------------**/
	
	
	public static final String insertCompanyMasterQuery = "insert into pms_company_master (company_name, address, city, pincode, state_id, company_email, company_ph_no, company_website, created_by, updatetd_by) "
														  + " values(?,?,?,?,?,?,?,?,?,?) ";
	
	public static final String getIdCompanyMasterQuery = "select max(company_id) as id from pms_company_master";
	
	public static final String insertCompanyContactInfo = "insert into pms_company_person_contact_info (contact_person_name, contact_person_email, contact_person_phone_no, company_id, created_by, updatetd_by) "
														  + " values(?,?,?,?,?,?) ";
	
	public static final String insertPfInfo = "insert into pms_company_pf_info (company_pf_number, company_pf_registration_date, company_pf_signatory_name,company_id, created_by, updatetd_by) "
			  								  + " values(?,?,?,?,?,?) ";
	
	public static final String insertEsiInfo = "insert into pms_company_esi_info (company_esi_number, company_esi_registration_date, company_esi_signatory_name, company_id, created_by, updatetd_by) "
			  								  + " values(?,?,?,?,?,?) ";
	
	public static final String insertPtInfo = "insert into pms_company_pt_info (company_pt_number, company_pt_registration_date, company_pt_signatory_name, company_id, created_by, updatetd_by) "
			  								  + " values(?,?,?,?,?,?) ";
	
	public static final String insertItInfo = "insert into pms_company_it_info (company_pan, company_tan, company_tan_cercle, company_id, created_by, updatetd_by) "
			  								  + " values(?,?,?,?,?,?) ";
	

	/**-------------------------------------------------------BloodGroup Master---------------------------------------------------------------------------**/
	
	public static final String onLoadBloodGroupQuery = "select * from pms_bloodgroup_master where is_delete = 'N' ";
	public static final String insertBloodGroupQuery = "insert into pms_bloodgroup_master (bloodgroup_name,created_by) values(?,?)";
	public static final String updateBloodGroupQuery = "update pms_bloodgroup_master set bloodgroup_name = ?,updated_by=? where bloodgroup_id = ? ";
	public static final String deleteBloodGroupQuery = "update pms_bloodgroup_master set is_delete = 'Y' where bloodgroup_id = ? ";

	
}
