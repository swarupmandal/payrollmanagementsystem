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
	
	/**-------------------------------------------------------BloodGroup Master---------------------------------------------------------------------------**/
	
	public static final String onLoadBloodGroupQuery = "select * from pms_bloodgroup_master where is_delete = 'N' ";
	public static final String insertBloodGroupQuery = "insert into pms_bloodgroup_master (bloodgroup_name,created_by) values(?,?)";
	public static final String updateBloodGroupQuery = "update pms_bloodgroup_master set bloodgroup_name = ?,updated_by=? where bloodgroup_id = ? ";
	public static final String deleteBloodGroupQuery = "update pms_bloodgroup_master set is_delete = 'Y' where bloodgroup_id = ? ";
	
}
