package org.appsquad.sql;

public class BankAccountMasterSql {

	public static final String saveBankMasterQuery = "INSERT INTO pms_bank_master(bank_name, created_by, updated_by)VALUES (?, ?, ?)";
	
	public static final String loadBankMasterData = "SELECT id, bank_name FROM pms_bank_master ";
	
}
