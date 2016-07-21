package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.BankAccountMasterBean;
import org.appsquad.dao.BankAccountMasterDao;
import org.appsquad.dao.DesignationMasterDao;
import org.appsquad.service.BankAccountMasterService;
import org.appsquad.service.DesignationMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class BankAccountMasterViewModel {
	
	BankAccountMasterBean bankAccountMasterBean = new BankAccountMasterBean();
	ArrayList<BankAccountMasterBean> bankAccountMasterBeanList = new ArrayList<BankAccountMasterBean>();
	
	Session session = null;
	String userName;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		
		bankAccountMasterBeanList = BankAccountMasterDao.onLoad();
		//bankAccountMasterBeanList = BankAccountMasterService.loadAllDataOfBnakMaster();
				
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
		bankAccountMasterBean.setUserName(userName);
		BankAccountMasterService.insertBankMasterData(bankAccountMasterBean);
		BankAccountMasterService.clearScreen(bankAccountMasterBean);
		bankAccountMasterBeanList = BankAccountMasterDao.onLoad();
		//bankAccountMasterBeanList = BankAccountMasterService.loadAllDataOfBnakMaster();
	}

	public BankAccountMasterBean getBankAccountMasterBean() {
		return bankAccountMasterBean;
	}

	public void setBankAccountMasterBean(BankAccountMasterBean bankAccountMasterBean) {
		this.bankAccountMasterBean = bankAccountMasterBean;
	}

	public ArrayList<BankAccountMasterBean> getBankAccountMasterBeanList() {
		return bankAccountMasterBeanList;
	}

	public void setBankAccountMasterBeanList(
			ArrayList<BankAccountMasterBean> bankAccountMasterBeanList) {
		this.bankAccountMasterBeanList = bankAccountMasterBeanList;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

}
