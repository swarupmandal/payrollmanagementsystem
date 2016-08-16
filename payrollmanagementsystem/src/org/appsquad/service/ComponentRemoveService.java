package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.dao.ComponentRemoveDao;

public class ComponentRemoveService {
	
	public static void doRemoveComponents( ArrayList<ComponentMasterBean> componentList, int unitId ){
		ArrayList<String> componentsList = new ArrayList<String>();
		for(ComponentMasterBean bean : componentList){
			if( !bean.isCheckVal()){
				System.out.println("To be remove from sheet Component :"+bean.getComponentName());
				componentsList.add("'"+bean.getComponentName()+"'");
			}
		}
		String compBuilder = componentsList.toString();
		String fb = compBuilder.replace("[", "(");
		String bb = fb.replace("]", ")");
		String finalComponentList = bb.toString();
		
		System.out.println(" UNit:: "+unitId  +" Final : "+finalComponentList);
		ComponentRemoveDao.removeComponent(unitId, finalComponentList);
	}

}
