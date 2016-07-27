package org.appsquad.sql;

public class CompanyMasterSqlQuery {
	public static final String loadCompanyListQuery = "select company_name,company_id from pms_company_master where is_delete = 'N' ";
	
	public static final String editCompanyListQuery = "select * from vw_company_data where company_id = ? ";
}
