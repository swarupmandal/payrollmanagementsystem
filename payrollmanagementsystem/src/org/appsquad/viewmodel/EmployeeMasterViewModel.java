package org.appsquad.viewmodel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.appsquad.bean.BankAccountBean;
import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.DesignationBean;
import org.appsquad.bean.DesignationMasterBean;
import org.appsquad.bean.EmployeeMasterBean;
import org.appsquad.bean.PaymentModeMasterBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.bean.UnitDesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.DesignationMasterDao;
import org.appsquad.dao.EmployeeDao;
import org.appsquad.service.ComponentPerUnitMasterService;
import org.appsquad.service.EmployeeMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.CookieParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class EmployeeMasterViewModel {
	
	public EmployeeMasterBean employeeMasterBean = new EmployeeMasterBean();
	public ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	public ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	public ArrayList<EmployeeMasterBean> employeeMasterBeanList = new ArrayList<EmployeeMasterBean>();
	private ArrayList<StateMasterBean> stateMasterBeanList = new ArrayList<StateMasterBean>();
	private ArrayList<BloodGroupBean> bloodGroupBeanList = new ArrayList<BloodGroupBean>();
	private ArrayList<DesignationBean> designationBeanList = new ArrayList<DesignationBean>();
	private ArrayList<PaymentModeMasterBean> paymentModeMasterBeanList = new ArrayList<PaymentModeMasterBean>();
	private ArrayList<BankAccountBean> bankAccountBeanList = new ArrayList<BankAccountBean>();
	private ArrayList<ComponentMasterBean> componentMasterBeanList = new ArrayList<ComponentMasterBean>();
	ArrayList<UnitDesignationBean> unitDesignationBeanList = new ArrayList<UnitDesignationBean>();
	ArrayList<DesignationMasterBean> empDesignationList = new ArrayList<DesignationMasterBean>();
	
	
	
	private UnitDesignationBean unitDesignationBean = new UnitDesignationBean();
	public CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	public UnitMasterBean unitMasterBean = new UnitMasterBean();
	private StateMasterBean stateMasterBean = new StateMasterBean();
	private BloodGroupBean bloodGroupBean = new BloodGroupBean();
	private DesignationBean designationBean = new DesignationBean();
	private PaymentModeMasterBean paymentModeMasterBean = new PaymentModeMasterBean();
	private BankAccountBean bankAccountBean = new BankAccountBean();
	private ComponentMasterBean componentMasterBean = new ComponentMasterBean();
	private DesignationMasterBean designationMasterBean = new DesignationMasterBean();
	private int maxEmpId =0;

	private Connection connection = null;
	private Session session = null;
	private String userName;
	
	public String employeeCode ;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view)throws Exception{
		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		userName =  (String) session.getAttribute("userId");
		
		loadExistingEmployeeData();
		
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
		EmployeeMasterService.loadStateBeanList(stateMasterBeanList);
		EmployeeMasterService.loadBloodGroupList(bloodGroupBeanList);
		EmployeeMasterService.loadDesignationList(designationBeanList);
		EmployeeMasterService.loadpaymentmodeList(paymentModeMasterBeanList);
		EmployeeMasterService.loadBankList(bankAccountBeanList);
		empDesignationList = DesignationMasterDao.onLoadEmpDesignationList();
		
		
	}
	
	/*
	 * @author Somnath
	 * @Functionality Searching
	 */
	@Command
	@NotifyChange("*")
	public void onChangeEmployeeCode(){
		if(employeeCode.length()>0)
		employeeMasterBeanList = EmployeeMasterService.searchSavedEmployeeData(employeeCode);
		else
			loadExistingEmployeeData();
	}
	
	@Command
	@NotifyChange("*")
	public void onClickClear(){
		employeeCode = null;
		employeeMasterBean.setCompanyName(null);
		employeeMasterBean.setUnitName(null);
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		unitMasterBeanList.clear();
		loadExistingEmployeeData();
	}
	
	
	/*
	 * @author: Swarup Mandal
	 * @Functionality:
	 * @Return:
	 */
	@Command
	@NotifyChange("*")
	public void saveEmpInfo(){
		if(EmployeeMasterService.isValid(employeeMasterBean,companyMasterBean, unitMasterBean, userName)){
		
		
			if(EmployeeMasterService.insertEmployeeInformation(employeeMasterBean, userName)){
				
				Messagebox.show("Saved successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
			}
			
			
		}
		
	}

	
	@Command
	@NotifyChange("*")
	public void saveEmpInfo2(){
		
		if(EmployeeMasterService.isValid(employeeMasterBean , companyMasterBean, unitMasterBean, userName)){
			
			maxEmpId = EmployeeMasterService.insertEmployeeInformation2(employeeMasterBean, userName);
			
			if(maxEmpId>0){
				
				Messagebox.show("Saved successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		
	}
	
	
	@Command
	@NotifyChange("*")
	public void onSelectCompanyName(){
		System.out.println("selected company name >>> >> > " + companyMasterBean.getCompanyName());
		System.out.println("Selected company ID >>> >> > " + companyMasterBean.getCompanyId());
		employeeMasterBean.setCompanyId(companyMasterBean.getCompanyId());
		unitMasterBeanList = ComponentPerUnitMasterService.loadUnitBeanList(employeeMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange("*")
	public void onClickTabExisting(){
		System.out.println("Tab 2 clicked!");
		employeeCode = null;
		employeeMasterBean.setCompanyName(null);
		employeeMasterBean.setUnitName(null);
		loadExistingEmployeeData();
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitName(){
		employeeMasterBean.setUnitId(unitMasterBean.getUnitId());
		employeeMasterBean.setCompanyId(companyMasterBean.getCompanyId());
		employeeMasterBean.setUnitDesignationId(unitDesignationBean.getUnitDesignationId());
		if(employeeMasterBean.getCompanyId()>0 && employeeMasterBean.getUnitId()>0){
			
			unitDesignationBeanList = EmployeeMasterService.loadUnitDesignation(employeeMasterBean.getCompanyId(), employeeMasterBean.getUnitId());
			
		}
		loadSearchedEmployeeFromCompany(employeeMasterBean);
		employeeMasterBean.setCompanyName(null);
		employeeMasterBean.setUnitName(null);
	}
	
	public void loadSearchedEmployeeFromCompany(EmployeeMasterBean employeeMasterBean){
		employeeMasterBeanList = EmployeeMasterService.searchEmployeeFromCompany(employeeMasterBean);
	}

	@Command
	@NotifyChange("*")
	public void insertPersonalInfo(){
		if(EmployeeMasterService.insertPersonelInfoService(employeeMasterBean, maxEmpId, userName)){
			
			Messagebox.show("Saved successfully \n proceed to next tab", "Information", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectStateName(){
		
		employeeMasterBean.setEmpStateId(stateMasterBean.getStateId());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectBooldGroup(){
		employeeMasterBean.setEmpBloodGroupId(bloodGroupBean.getBloodGroupId());
	}
	

	@Command
	@NotifyChange("*")
	public void onSelectDesignation(){
		
		employeeMasterBean.setEmpDesignationId(designationMasterBean.getDesignationId());
		
	}
	
	
	
	
	@Command
	@NotifyChange("*")
	public void onSelectPaymentMode(){
		
		employeeMasterBean.setPaymentModeId(paymentModeMasterBean.getPaymentModeId());
	}
	
	
	@Command
	@NotifyChange("*")
	public void onSelectBankAccount(){
		
		employeeMasterBean.setEmpBankId(bankAccountBean.getBankId());
	}
	
	@Command
	@NotifyChange("*")
	public void onClickOfficialDetails(){
		if(EmployeeDao.insertEmpOfficialDet(employeeMasterBean, maxEmpId, userName)){
			Messagebox.show("Saved successfully \n proceed to next tab", "Information", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onCheckPf(){
		
		if(employeeMasterBean.isPfCheckValue()==true){
			employeeMasterBean.setUanFieldDisabled(false);
			employeeMasterBean.setPfFielddisabled(false);
			}else if (employeeMasterBean.isPfCheckValue()==false) {
			employeeMasterBean.setPfFielddisabled(true);	
			employeeMasterBean.setUan(null);
			employeeMasterBean.setUanFieldDisabled(true);
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onCheckEsi(){
		if(employeeMasterBean.isEsiCheckValue()){
			employeeMasterBean.setEsiFieldDisabled(false);
		}else if (!employeeMasterBean.isEsiCheckValue()) {
			employeeMasterBean.setEsi(null);
			employeeMasterBean.setEsiFieldDisabled(true);
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onClickSaveComponents(){
		if(EmployeeMasterService.isEmptyLocationField(employeeMasterBean)){
			EmployeeDao.insertComponentPerEmployee(componentMasterBeanList, maxEmpId, employeeMasterBean.getCompanyId(), employeeMasterBean.getUnitId(), userName);
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onClickEdit(@BindingParam("bean")EmployeeMasterBean bean){
		System.out.println("on edit: "+bean.getEmployeeCode()+" "+bean.getEmployeeid());
		Map<String, Integer> parentMap = new HashMap<String, Integer>();
		parentMap.put("parentBean", bean.getEmployeeid());
		Window window = (Window) Executions.createComponents("/WEB-INF/view/employeeedit.zul", null, parentMap);
		window.doModal();
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickPfEsiSave(){
		if (EmployeeMasterService.isPfEsiFieldEmpty(employeeMasterBean)) {
			
			if(EmployeeMasterService.pfEsiInsert(employeeMasterBean, maxEmpId, userName)){
				
				Messagebox.show("Saved succesful", "Information", Messagebox.OK, Messagebox.INFORMATION);
			}else {
				Messagebox.show("Fill Details", "Information", Messagebox.OK, Messagebox.INFORMATION);
			}
		}else {
			Messagebox.show("Fill Details", "Information", Messagebox.OK, Messagebox.INFORMATION);
		}
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitDesignation(){
		
		System.out.println("unit designetionId " + unitDesignationBean.getUnitDesignationId() );
		
		employeeMasterBean.setUnitDesignationId(unitDesignationBean.getUnitDesignationId());
		
		System.out.println("" + employeeMasterBean.getUnitDesignationId());
		
		componentMasterBeanList = EmployeeMasterService.loadComponentDetatils(employeeMasterBean.getCompanyId(), employeeMasterBean.getUnitId(),unitDesignationBean.getUnitDesignationId() );
	
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickInActive(@BindingParam("bean")EmployeeMasterBean bean){
		
		
		
	}
	
	
	
	public void loadExistingEmployeeData(){
		employeeMasterBeanList = EmployeeMasterService.loadSavedEmployeeData();
	}
	
	public EmployeeMasterBean getEmployeeMasterBean() {
		return employeeMasterBean;
	}



	public void setEmployeeMasterBean(EmployeeMasterBean employeeMasterBean) {
		this.employeeMasterBean = employeeMasterBean;
	}



	public ArrayList<CompanyMasterBean> getCompanyBeanList() {
		return companyBeanList;
	}



	public void setCompanyBeanList(ArrayList<CompanyMasterBean> companyBeanList) {
		this.companyBeanList = companyBeanList;
	}



	public ArrayList<UnitMasterBean> getUnitMasterBeanList() {
		return unitMasterBeanList;
	}



	public void setUnitMasterBeanList(ArrayList<UnitMasterBean> unitMasterBeanList) {
		this.unitMasterBeanList = unitMasterBeanList;
	}



	public ArrayList<EmployeeMasterBean> getEmployeeMasterBeanList() {
		return employeeMasterBeanList;
	}



	public void setEmployeeMasterBeanList(
			ArrayList<EmployeeMasterBean> employeeMasterBeanList) {
		this.employeeMasterBeanList = employeeMasterBeanList;
	}



	public CompanyMasterBean getCompanyMasterBean() {
		return companyMasterBean;
	}



	public void setCompanyMasterBean(CompanyMasterBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}



	public UnitMasterBean getUnitMasterBean() {
		return unitMasterBean;
	}



	public void setUnitMasterBean(UnitMasterBean unitMasterBean) {
		this.unitMasterBean = unitMasterBean;
	}



	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
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



	public ArrayList<StateMasterBean> getStateMasterBeanList() {
		return stateMasterBeanList;
	}



	public void setStateMasterBeanList(
			ArrayList<StateMasterBean> stateMasterBeanList) {
		this.stateMasterBeanList = stateMasterBeanList;
	}



	public StateMasterBean getStateMasterBean() {
		return stateMasterBean;
	}



	public void setStateMasterBean(StateMasterBean stateMasterBean) {
		this.stateMasterBean = stateMasterBean;
	}



	public ArrayList<BloodGroupBean> getBloodGroupBeanList() {
		return bloodGroupBeanList;
	}



	public void setBloodGroupBeanList(ArrayList<BloodGroupBean> bloodGroupBeanList) {
		this.bloodGroupBeanList = bloodGroupBeanList;
	}



	public BloodGroupBean getBloodGroupBean() {
		return bloodGroupBean;
	}



	public void setBloodGroupBean(BloodGroupBean bloodGroupBean) {
		this.bloodGroupBean = bloodGroupBean;
	}



	public ArrayList<DesignationBean> getDesignationBeanList() {
		return designationBeanList;
	}



	public void setDesignationBeanList(
			ArrayList<DesignationBean> designationBeanList) {
		this.designationBeanList = designationBeanList;
	}



	public DesignationBean getDesignationBean() {
		return designationBean;
	}



	public void setDesignationBean(DesignationBean designationBean) {
		this.designationBean = designationBean;
	}



	public ArrayList<PaymentModeMasterBean> getPaymentModeMasterBeanList() {
		return paymentModeMasterBeanList;
	}



	public void setPaymentModeMasterBeanList(
			ArrayList<PaymentModeMasterBean> paymentModeMasterBeanList) {
		this.paymentModeMasterBeanList = paymentModeMasterBeanList;
	}



	public PaymentModeMasterBean getPaymentModeMasterBean() {
		return paymentModeMasterBean;
	}



	public void setPaymentModeMasterBean(PaymentModeMasterBean paymentModeMasterBean) {
		this.paymentModeMasterBean = paymentModeMasterBean;
	}



	public ArrayList<BankAccountBean> getBankAccountBeanList() {
		return bankAccountBeanList;
	}



	public void setBankAccountBeanList(
			ArrayList<BankAccountBean> bankAccountBeanList) {
		this.bankAccountBeanList = bankAccountBeanList;
	}



	public BankAccountBean getBankAccountBean() {
		return bankAccountBean;
	}



	public void setBankAccountBean(BankAccountBean bankAccountBean) {
		this.bankAccountBean = bankAccountBean;
	}



	



	public ComponentMasterBean getComponentMasterBean() {
		return componentMasterBean;
	}



	public void setComponentMasterBean(ComponentMasterBean componentMasterBean) {
		this.componentMasterBean = componentMasterBean;
	}



	public int getMaxEmpId() {
		return maxEmpId;
	}



	public void setMaxEmpId(int maxEmpId) {
		this.maxEmpId = maxEmpId;
	}



	public String getEmployeeCode() {
		return employeeCode;
	}



	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public ArrayList<UnitDesignationBean> getUnitDesignationBeanList() {
		return unitDesignationBeanList;
	}

	public void setUnitDesignationBeanList(
			ArrayList<UnitDesignationBean> unitDesignationBeanList) {
		this.unitDesignationBeanList = unitDesignationBeanList;
	}

	public UnitDesignationBean getUnitDesignationBean() {
		return unitDesignationBean;
	}

	public void setUnitDesignationBean(UnitDesignationBean unitDesignationBean) {
		this.unitDesignationBean = unitDesignationBean;
	}

	public ArrayList<ComponentMasterBean> getComponentMasterBeanList() {
		return componentMasterBeanList;
	}

	public void setComponentMasterBeanList(
			ArrayList<ComponentMasterBean> componentMasterBeanList) {
		this.componentMasterBeanList = componentMasterBeanList;
	}

	

	public DesignationMasterBean getDesignationMasterBean() {
		return designationMasterBean;
	}

	public void setDesignationMasterBean(DesignationMasterBean designationMasterBean) {
		this.designationMasterBean = designationMasterBean;
	}

	public void setEmpDesignationList(
			ArrayList<DesignationMasterBean> empDesignationList) {
		this.empDesignationList = empDesignationList;
	}

	public ArrayList<DesignationMasterBean> getEmpDesignationList() {
		return empDesignationList;
	}
	
}
