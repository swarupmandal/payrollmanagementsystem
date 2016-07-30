package org.appsquad.sql;

public class ComponentMasterSql {

	public static final String loadCompoNentTypeQuery = "SELECT component_type_id, component_type FROM pms_component_type_master where is_delete = 'N' ";
	
	public static final String insertCompontQuery = "INSERT INTO pms_component_master(component_name, component_type_id,created_by, updatetd_by) VALUES (?, ?, ?, ?) ";
	
	public static final String loadComponentDetailsQuery =  "SELECT pcm.component_id, pcm.component_name, pctm.component_type "
															+ " FROM pms_component_master pcm, pms_component_type_master pctm "
															+ " WHERE pcm.component_type_id = pctm.component_type_id";
	
}
