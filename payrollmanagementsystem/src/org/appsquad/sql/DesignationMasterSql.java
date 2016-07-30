package org.appsquad.sql;

public class DesignationMasterSql {

	public static final String saveDesignationQuery = "INSERT INTO pms_designation_master(designation, created_by, updated_by)VALUES (?, ?, ?)";
	
	public static final String loadDesignationMasterData = "SELECT id, designation FROM pms_designation_master ";
	
	public static final String saveEmpDesignationQuery = "INSERT INTO pms_emp_designation_master(emp_designation, created_by, updated_by)VALUES (?, ?, ?) "; 
	
	public static final String loadEmpDesignationMasterData = "SELECT id, emp_designation FROM pms_emp_designation_master ";
	
}
