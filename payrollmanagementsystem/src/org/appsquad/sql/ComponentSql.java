package org.appsquad.sql;

public class ComponentSql {

	public static final String removeComponentQuery = "UPDATE pms_employee_salary_components "
			+" SET is_delete='Y' WHERE unit_id = ? AND component_name in components";
}
