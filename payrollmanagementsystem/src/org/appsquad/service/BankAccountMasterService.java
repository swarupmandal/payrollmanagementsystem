package org.appsquad.service;

import java.util.ArrayList;
import org.appsquad.bean.BankAccountMasterBean;
import org.appsquad.dao.BankAccountMasterDao;
import org.zkoss.zul.Messagebox;

public class BankAccountMasterService {
	
	public static boolean isValid(BankAccountMasterBean bean){
		if(bean.getBankName() != null){
			return true;
			
		}else{
			Messagebox.show("Bank Name Required!","Alert",Messagebox.OK,Messagebox.EXCLAMATION);
			return false;
		}
	}	

	public static void clearScreen(BankAccountMasterBean bean){
		bean.setBankName(null);
		bean.setBankId(0);
		
	}
	
	public static void insertBankMasterData(BankAccountMasterBean bean){
		if(isValid(bean)){
			BankAccountMasterDao.insertBankData(bean);
			
		}
	}
	
	public static ArrayList<BankAccountMasterBean> loadAllDataOfBnakMaster(){
		ArrayList<BankAccountMasterBean> list  = new ArrayList<BankAccountMasterBean>();
		list = BankAccountMasterDao.onLoad();
		return list;
	}


}
