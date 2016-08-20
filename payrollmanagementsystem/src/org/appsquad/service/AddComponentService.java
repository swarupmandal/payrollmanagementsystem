package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.dao.AddComponentDao;
import org.appsquad.dao.ComponentPerUnitMasterDao;


public class AddComponentService {

	public static ArrayList<ComponentPerUnitMasterBean> loadData(int compId, int unId, int undesId){
		ArrayList<ComponentPerUnitMasterBean> list = new ArrayList<ComponentPerUnitMasterBean>();
		list = AddComponentDao.onLoadData(compId, unId, undesId);
		return list;
	}
	
	public static ArrayList<Integer> loadEmpIdList(int compId, int unId, int undesId){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list = AddComponentDao.loadEmpIdList(compId, unId, undesId);
		return list;
	}
	
	public static void saveNewComponent(ArrayList<ComponentPerUnitMasterBean> list, ComponentPerUnitMasterBean bean , ArrayList<Integer> empIdList){
		AddComponentDao.insertNewComponent(list, bean, empIdList);
		
	}
	
	
}
