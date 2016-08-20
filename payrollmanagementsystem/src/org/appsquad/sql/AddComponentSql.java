package org.appsquad.sql;

public class AddComponentSql {

	public static final String newComponentAddQry = "SELECT pcm.component_id, pcm.component_name, " +
														  "	pctm.component_type_id,pctm.component_type " + 
														  "	FROM pms_component_master pcm, pms_component_type_master pctm " + 
														  "	WHERE pcm.component_type_id = pctm.component_type_id " +
														  "	and pcm.component_id not in (SELECT pcmpu.component_id from " + 
														  "	pms_component_master_per_unit pcmpu  where pcmpu.company_id = ? " +
														  "	and pcmpu.unit_id = ? and pcmpu.designation_id = ?)";

	public static final String empIdLoadQuery = " select employee_id from pms_employee_master where company_id = ? "
											+ " and unit_id = ? and unit_designation_id = ? ";

	public static final String insertEmpCompoQuery = " INSERT INTO pms_employee_salary_components(employee_id, component_id, component_name, component_type_id, company_id, unit_id, component_amount) "
															 + "VALUES (?, ?, ?, ?, ?, ?, ?) ";
	

}
