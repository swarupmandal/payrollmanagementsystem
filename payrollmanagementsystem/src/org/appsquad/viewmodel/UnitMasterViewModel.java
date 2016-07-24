package org.appsquad.viewmodel;

import java.util.ArrayList;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.CompanyDao;
import org.appsquad.service.BloodGroupService;
import org.appsquad.service.UnitMasterService;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

public class UnitMasterViewModel {
	public UnitMasterBean unitMasterBean = new UnitMasterBean();

	Session session = null;
	
	private String userName;
	
	public ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	
	public ArrayList<CompanyMasterBean> companyMasterBeanList = new ArrayList<CompanyMasterBean>();
	
	public boolean saveDisability = false;
	
	public boolean updateDisability = true;
	
	private int companyId =0;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		userName = (String) session.getAttribute("userId");
		
		unitMasterBean.setUserName(userName);
		
		UnitMasterService.loadAllDataOfUnitMaster(unitMasterBeanList);
		
		companyMasterBeanList = CompanyDao.loadCompanyList();
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectCompanyName(){
		unitMasterBean.setCompanyName(companyMasterBean.getCompanyName());
		companyId = companyMasterBean.getCompanyId();
		unitMasterBean.setCompanyId(companyId);
	}
	
	@GlobalCommand
	@NotifyChange("*")
	public void globalReload(){
		UnitMasterService.loadAllDataOfUnitMaster(unitMasterBeanList);
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSave(){
		unitMasterBean.setCompanyId(companyId);
		UnitMasterService.insertUnitMasterData(unitMasterBean);
		UnitMasterService.clearScreen(unitMasterBean);
		UnitMasterService.loadAllDataOfUnitMaster(unitMasterBeanList);
	}
	
	@Command
	@NotifyChange("*")
	public void onClickEdit(@BindingParam("bean")UnitMasterBean bean){
		unitMasterBean.setCompanyId(bean.getCompanyId());
		unitMasterBean.setUnitAddress(bean.getUnitAddress());
		unitMasterBean.setUnitName(bean.getUnitName());
		unitMasterBean.setUnitId(bean.getUnitId());
	}
	
	@Command
	@NotifyChange("*")
	public void onClickUpdate(){
		UnitMasterService.updateUnitMasterData(unitMasterBean);
		UnitMasterService.clearScreen(unitMasterBean);
		UnitMasterService.loadAllDataOfUnitMaster(unitMasterBeanList);
		updateDisability = true;
		saveDisability = false;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange("*")
	public void onClickDelete(@BindingParam("bean")UnitMasterBean unitmasterbean){
		
		Messagebox.show(
				"Are you sure to delete?", "Confirm Dialog",
				Messagebox.OK | Messagebox.IGNORE | Messagebox.CANCEL,
				Messagebox.QUESTION, 
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							UnitMasterService.deleteUnitMasterData(unitmasterbean);
							 BindUtils.postGlobalCommand(null, null, "globalReload", null);
						} else if (evt.getName().equals("onIgnore")) {
							Messagebox.show("Ignore Deletion?", "Warning",
									Messagebox.OK, Messagebox.EXCLAMATION);
						} else {
							System.out.println("Save Canceled !");
						}
					}
				}
			);
	}
	
	public UnitMasterBean getUnitMasterBean() {
		return unitMasterBean;
	}

	public void setUnitMasterBean(UnitMasterBean unitMasterBean) {
		this.unitMasterBean = unitMasterBean;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<UnitMasterBean> getUnitMasterBeanList() {
		return unitMasterBeanList;
	}

	public void setUnitMasterBeanList(ArrayList<UnitMasterBean> unitMasterBeanList) {
		this.unitMasterBeanList = unitMasterBeanList;
	}

	public boolean isSaveDisability() {
		return saveDisability;
	}

	public void setSaveDisability(boolean saveDisability) {
		this.saveDisability = saveDisability;
	}

	public boolean isUpdateDisability() {
		return updateDisability;
	}

	public void setUpdateDisability(boolean updateDisability) {
		this.updateDisability = updateDisability;
	}

	public CompanyMasterBean getCompanyMasterBean() {
		return companyMasterBean;
	}

	public void setCompanyMasterBean(CompanyMasterBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}

	public ArrayList<CompanyMasterBean> getCompanyMasterBeanList() {
		return companyMasterBeanList;
	}

	public void setCompanyMasterBeanList(
			ArrayList<CompanyMasterBean> companyMasterBeanList) {
		this.companyMasterBeanList = companyMasterBeanList;
	}
}
