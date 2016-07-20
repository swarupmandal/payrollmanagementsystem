package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.ComponentPerUnitMasterDao;
import org.appsquad.dao.EmployeeDao;

public class ComponentPerUnitMasterService {
	
	public static ArrayList<UnitMasterBean> loadUnitBeanList(Integer id){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		list = ComponentPerUnitMasterDao.loadUnitList(id);
		return list;
	}
		
}
