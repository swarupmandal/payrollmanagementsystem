package org.appsquad.sql;

public class CompanyMasterSqlQuery {
	public static final String loadCompanyListQuery = "select company_name,company_id from pms_company_master where is_delete = 'N' ";
}
