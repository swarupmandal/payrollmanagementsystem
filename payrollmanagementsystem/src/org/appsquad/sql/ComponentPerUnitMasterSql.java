package org.appsquad.sql;

public class ComponentPerUnitMasterSql {
	
	public static final String loadComponentBeanListQuery = "SELECT pcm.component_id, pcm.component_name, pctm.component_type_id, pctm.component_type "
															+ " FROM pms_component_master pcm, pms_component_type_master pctm "
															+ " WHERE pcm.component_type_id = pctm.component_type_id ";
	
	public static final String loadUnitQuery = "select unit_name, unit_id from pms_unit_master where company_id = ? ";
	
	public static final String insertComponentPerUnitQuery = "INSERT INTO pms_component_master_per_unit(component_id, component_name, component_type_id, company_id, unit_id, created_by, updatetd_by, designation_id, amount) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?) "; 
	

}
