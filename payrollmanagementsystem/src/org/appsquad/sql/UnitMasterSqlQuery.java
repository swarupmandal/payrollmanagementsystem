package org.appsquad.sql;

public class UnitMasterSqlQuery {
	public static final String onLoadUnitMasterQuery = "select pum.unit_name,pum.unit_id,pcm.company_name,pcm.company_id from "
			+ "pms_unit_master pum "
			+ "join pms_company_master pcm "
			+ "on pcm.company_id = pum.company_id where pum.is_delete = 'N' ";
	public static final String insertUnitMasterQuery = "insert into pms_unit_master (unit_name,unit_address,company_id,created_by) values(?,?,?,?)";
	public static final String updateUnitMasterQuery = "update pms_unit_master set unit_name = ?,unit_address=?, updated_by = ? where unit_id = ? ";
	public static final String deleteUnitMasterQuery = "update pms_unit_master set is_delete = 'Y', updated_by = ? where unit_id = ?  ";
}
