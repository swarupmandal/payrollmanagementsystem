package org.appsquad.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.CompanyDao;
import org.appsquad.dao.UnitMasterDao;
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
	
	public UnitMasterBean baseDaysTypeBean = new UnitMasterBean();
	
	private UnitMasterBean wagesTypeBean = new UnitMasterBean();
	
	private UnitMasterBean sundayTypeBean = new UnitMasterBean();

	Session session = null;
	
	private String userName;
	
	public ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	
	public ArrayList<CompanyMasterBean> companyMasterBeanList = new ArrayList<CompanyMasterBean>();
	
	public ArrayList<UnitMasterBean> baseDaysList = new ArrayList<UnitMasterBean>();
	
	public ArrayList<UnitMasterBean> wagesTypeBeanList = new ArrayList<UnitMasterBean>();
	
	public ArrayList<UnitMasterBean> sunDaySelecttionBnLst = new ArrayList<UnitMasterBean>();
	
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
		
		baseDaysList = UnitMasterDao.loadDayType();
		wagesTypeBeanList = UnitMasterService.fetchWagesType();
		sunDaySelecttionBnLst = UnitMasterService.sundaySelType();
		
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
		
		UnitMasterService.insertUnitMasterData(unitMasterBean, baseDaysTypeBean, wagesTypeBean, sundayTypeBean);
		
		UnitMasterService.clearScreen(unitMasterBean, baseDaysTypeBean);
		baseDaysList = UnitMasterDao.loadDayType();
		UnitMasterService.loadAllDataOfUnitMaster(unitMasterBeanList);
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickEdit(@BindingParam("bean")UnitMasterBean bean){
		/*unitMasterBean.setCompanyId(bean.getCompanyId());
		unitMasterBean.setUnitAddress(bean.getUnitAddress());
		unitMasterBean.setUnitName(bean.getUnitName());
		unitMasterBean.setUnitId(bean.getUnitId());*/
		System.out.println("unit id = "+bean.getUnitId());
		//bean.setReadOnly(false);
		Map<String, Object> parentMap = new HashMap<String, Object>();
		parentMap.put("parentList", unitMasterBean);
		
	}
	
	/*@Command
	@NotifyChange("*")
	public void onClickUpdate(){
		UnitMasterService.updateUnitMasterData(unitMasterBean);
		UnitMasterService.clearScreen(unitMasterBean);
		UnitMasterService.loadAllDataOfUnitMaster(unitMasterBeanList);
		updateDisability = true;
		saveDisability = false;
	}*/
	
	@Command
	@NotifyChange("*")
	public void onClickUpdate(@BindingParam("bean")UnitMasterBean bean){
		bean.setUserName(userName);
		//UnitMasterService.updateUnitMasterData(bean);
		//UnitMasterService.loadAllDataOfUnitMaster(unitMasterBeanList);
		bean.setReadOnly(false);
		/*
		Map<String, Object> parentMap = new HashMap<String, Object>();
		parentMap.put("bean", bean);
		parentMap.put("basedaysList ", baseDaysList);
		*/
		
	}

	@Command
	@NotifyChange("*")
	public void onSelectBasedaysType(){
		sundayTypeBean.setSundaySelection(null);
		sundayTypeBean.setSundaySelectionId(0);
		
		sunDaySelecttionBnLst = UnitMasterService.sundaySelType();
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
	
	@Command
	@NotifyChange("*")
	public void onSelectWages(){
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSundaySelect(){
		
		
		if(baseDaysTypeBean.getBaseDaysTypeId() != 2 && sundayTypeBean.getSundaySelectionId() == 2){
			Messagebox.show("Combination not matched ", "Alert", Messagebox.OK,Messagebox.EXCLAMATION);
			sundayTypeBean.setSundaySelection(null);
			sundayTypeBean.setSundaySelectionId(0);
			unitMasterBean.setSundaySelectionId(0);
			sunDaySelecttionBnLst = UnitMasterService.sundaySelType();
		}
		
		
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

	public UnitMasterBean getBaseDaysTypeBean() {
		return baseDaysTypeBean;
	}

	public void setBaseDaysTypeBean(UnitMasterBean baseDaysTypeBean) {
		this.baseDaysTypeBean = baseDaysTypeBean;
	}

	public ArrayList<UnitMasterBean> getBaseDaysList() {
		return baseDaysList;
	}

	public void setBaseDaysList(ArrayList<UnitMasterBean> baseDaysList) {
		this.baseDaysList = baseDaysList;
	}

	public UnitMasterBean getWagesTypeBean() {
		return wagesTypeBean;
	}

	public void setWagesTypeBean(UnitMasterBean wagesTypeBean) {
		this.wagesTypeBean = wagesTypeBean;
	}

	public UnitMasterBean getSundayTypeBean() {
		return sundayTypeBean;
	}

	public void setSundayTypeBean(UnitMasterBean sundayTypeBean) {
		this.sundayTypeBean = sundayTypeBean;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public ArrayList<UnitMasterBean> getWagesTypeBeanList() {
		return wagesTypeBeanList;
	}

	public void setWagesTypeBeanList(ArrayList<UnitMasterBean> wagesTypeBeanList) {
		this.wagesTypeBeanList = wagesTypeBeanList;
	}

	public ArrayList<UnitMasterBean> getSunDaySelecttionBnLst() {
		return sunDaySelecttionBnLst;
	}

	public void setSunDaySelecttionBnLst(
			ArrayList<UnitMasterBean> sunDaySelecttionBnLst) {
		this.sunDaySelecttionBnLst = sunDaySelecttionBnLst;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
