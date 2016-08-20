package org.appsquad.viewmodel;

import java.sql.Connection;
import java.util.ArrayList;

import org.appsquad.bean.BankAccountBean;
import org.appsquad.bean.BloodGroupBean;
import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.DesignationBean;
import org.appsquad.bean.DesignationMasterBean;
import org.appsquad.bean.EmployeeMasterBean;
import org.appsquad.bean.PaymentModeMasterBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.DesignationMasterDao;
import org.appsquad.service.EmployeeMasterService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

public class EmployeeEditViewModel {

	public EmployeeMasterBean employeeMasterBean = new EmployeeMasterBean();
	public ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	public ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	public ArrayList<EmployeeMasterBean> employeeMasterBeanList = new ArrayList<EmployeeMasterBean>();
	private ArrayList<StateMasterBean> stateMasterBeanList = new ArrayList<StateMasterBean>();
	private ArrayList<BloodGroupBean> bloodGroupBeanList = new ArrayList<BloodGroupBean>();
	private ArrayList<DesignationBean> designationBeanList = new ArrayList<DesignationBean>();
	private ArrayList<PaymentModeMasterBean> paymentModeMasterBeanList = new ArrayList<PaymentModeMasterBean>();
	private ArrayList<BankAccountBean> bankAccountBeanList = new ArrayList<BankAccountBean>();
	ArrayList<ComponentMasterBean> componentMasterBeanList = new ArrayList<ComponentMasterBean>();
	private ArrayList<DesignationMasterBean> empDesignationList = new ArrayList<DesignationMasterBean>();
	
	
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
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("parentBean")Integer empId)throws Exception{
		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		userName =  (String) session.getAttribute("userId");
	
		if(empId != null){
			System.out.println("employee data loading. . . "+empId);
			loadEmployeeInfo(empId);
		}
		EmployeeMasterService.loadStateBeanList(stateMasterBeanList);
		EmployeeMasterService.loadBloodGroupList(bloodGroupBeanList);
		EmployeeMasterService.loadDesignationList(designationBeanList);
		EmployeeMasterService.loadpaymentmodeList(paymentModeMasterBeanList);
		EmployeeMasterService.loadBankList(bankAccountBeanList);
		empDesignationList = DesignationMasterDao.onLoadEmpDesignationList();
	    //componentMasterBeanList = EmployeeMasterService.loadComponentDetatils(employeeMasterBean.getCompanyId(), employeeMasterBean.getUnitId());

	}
	
	@Command
	@NotifyChange("*")
	public void onCheckPf(){
		
		if(employeeMasterBean.isPfCheckValue()==true){
			employeeMasterBean.setUanFieldDisabled(false);
			}else if (employeeMasterBean.isPfCheckValue()==false) {
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
	public void oClickUpdate(){
		EmployeeMasterService.updateEmployeeInfo(employeeMasterBean);
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
	
	public void loadEmployeeInfo(Integer empId){
		employeeMasterBean = EmployeeMasterService.fetchEmployeeInfo(empId);
		employeeMasterBean.setUserId(userName);
		employeeMasterBean.setEmployeeid(empId);
	}
	
	@Command
	@NotifyChange("*")
	public void onClickInactive(){
		int i =0;
		i = EmployeeMasterService.onInActiveEmp(employeeMasterBean.getEmployeeid());
		if(i>0){
			Messagebox.show("Employee Inactivate Successfully", "Information", Messagebox.OK, Messagebox.INFORMATION );
		}
		
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

	public ArrayList<StateMasterBean> getStateMasterBeanList() {
		return stateMasterBeanList;
	}

	public void setStateMasterBeanList(
			ArrayList<StateMasterBean> stateMasterBeanList) {
		this.stateMasterBeanList = stateMasterBeanList;
	}

	public ArrayList<BloodGroupBean> getBloodGroupBeanList() {
		return bloodGroupBeanList;
	}

	public void setBloodGroupBeanList(ArrayList<BloodGroupBean> bloodGroupBeanList) {
		this.bloodGroupBeanList = bloodGroupBeanList;
	}

	public ArrayList<DesignationBean> getDesignationBeanList() {
		return designationBeanList;
	}

	public void setDesignationBeanList(
			ArrayList<DesignationBean> designationBeanList) {
		this.designationBeanList = designationBeanList;
	}

	public ArrayList<PaymentModeMasterBean> getPaymentModeMasterBeanList() {
		return paymentModeMasterBeanList;
	}

	public void setPaymentModeMasterBeanList(
			ArrayList<PaymentModeMasterBean> paymentModeMasterBeanList) {
		this.paymentModeMasterBeanList = paymentModeMasterBeanList;
	}

	public ArrayList<BankAccountBean> getBankAccountBeanList() {
		return bankAccountBeanList;
	}

	public void setBankAccountBeanList(
			ArrayList<BankAccountBean> bankAccountBeanList) {
		this.bankAccountBeanList = bankAccountBeanList;
	}

	public ArrayList<ComponentMasterBean> getComponentMasterBeanList() {
		return componentMasterBeanList;
	}

	public void setComponentMasterBeanList(
			ArrayList<ComponentMasterBean> componentMasterBeanList) {
		this.componentMasterBeanList = componentMasterBeanList;
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

	public StateMasterBean getStateMasterBean() {
		return stateMasterBean;
	}

	public void setStateMasterBean(StateMasterBean stateMasterBean) {
		this.stateMasterBean = stateMasterBean;
	}

	public BloodGroupBean getBloodGroupBean() {
		return bloodGroupBean;
	}

	public void setBloodGroupBean(BloodGroupBean bloodGroupBean) {
		this.bloodGroupBean = bloodGroupBean;
	}

	public DesignationBean getDesignationBean() {
		return designationBean;
	}

	public void setDesignationBean(DesignationBean designationBean) {
		this.designationBean = designationBean;
	}

	public PaymentModeMasterBean getPaymentModeMasterBean() {
		return paymentModeMasterBean;
	}

	public void setPaymentModeMasterBean(PaymentModeMasterBean paymentModeMasterBean) {
		this.paymentModeMasterBean = paymentModeMasterBean;
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

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public ArrayList<DesignationMasterBean> getEmpDesignationList() {
		return empDesignationList;
	}

	public void setEmpDesignationList(
			ArrayList<DesignationMasterBean> empDesignationList) {
		this.empDesignationList = empDesignationList;
	}

	public DesignationMasterBean getDesignationMasterBean() {
		return designationMasterBean;
	}

	public void setDesignationMasterBean(DesignationMasterBean designationMasterBean) {
		this.designationMasterBean = designationMasterBean;
	}
	
	
}
