package org.appsquad.sql;

public class ComponentPerUnitMasterSql {
	
	public static final String loadComponentBeanListQuery = "SELECT pcm.component_id, pcm.component_name, pctm.component_type "
															+ " FROM pms_component_master pcm, pms_component_type_master pctm "
															+ " WHERE pcm.component_type_id = pctm.component_type_id ";
	
	public static final String loadUnitQuery = "select unit_name, unit_id from unit_master where company_id = ? ";
	

}
