package org.appsquad.sql;

public class ComponentPerUnitMasterSql {
	
	public static final String loadComponentBeanListQuery = "SELECT pcm.component_id, pcm.component_name, pctm.component_type_id, pctm.component_type "
															+ " FROM pms_component_master pcm, pms_component_type_master pctm "
															+ " WHERE pcm.component_type_id = pctm.component_type_id ";
	
	public static final String loadUnitQuery = "select unit_name, unit_id from pms_unit_master where company_id = ? ";
	
	public static final String insertComponentPerUnitQuery = "INSERT INTO pms_component_master_per_unit(component_id, component_name, component_type_id, company_id, unit_id, created_by, updatetd_by, designation_id, amount) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?) "; 
	
	public static final String saveWorkingHour = "INSERT INTO pms_working_hours_per_unit(company_id, unit_id, hour_per_day, created_by,updated_by, unit_designation_id,base_days) VALUES (?, ?,  ?, ?, ?, ?, ?) ";
	
	public static final String saveBaseDays = "INSERT INTO pms_base_days_per_unit(company_id, unit_id, base_days, created_by,updated_by, unit_designation_id) VALUES (?, ?,  ?, ?, ?, ?) ";
	
	public static final String ldExistingComponent = " SELECT pcmpu.id, pcmpu.component_id, pcmpu.component_name, " +
													  " pcmpu.component_type_id, pcmpu.company_id, " + 
												      " pcmpu.unit_id, pcmpu.created_date, pcmpu.update_date, pcmpu.created_by, pcmpu.updatetd_by, " +
												      " pcmpu.is_delete, pcmpu.designation_id, pcmpu.amount, pctm.component_type " +
												      " FROM pms_component_master_per_unit pcmpu, pms_component_type_master pctm " +
												      " where pcmpu.component_type_id = pctm.component_type_id " +
												      " and pcmpu.company_id = ? " +
												      " and pcmpu.unit_id = ? and pcmpu.designation_id = ? ";
	
	public static final String upDateExistingAmountQuery = "UPDATE pms_component_master_per_unit set amount = ? where id = ?";
	
	public static final String newComponentAddQry = "SELECT pcm.component_id, pcm.component_name, " +
													  "	pctm.component_type_id,pctm.component_type " + 
													  "	FROM pms_component_master pcm, pms_component_type_master pctm " + 
													  "	WHERE pcm.component_type_id = pctm.component_type_id " +
													  "	and pcm.component_id not in (SELECT pcmpu.component_id from " + 
													  "	pms_component_master_per_unit pcmpu  where pcmpu.company_id = ? " +
													  "	and pcmpu.unit_id = ? and pcmpu.designation_id = ?)";
	
	public static final String empIdLoadQuery = " select employee_id from pms_employee_master where company_id = ? "
												+ "unit_id = ? and unit_designation_id = ? ";
															
	
}
