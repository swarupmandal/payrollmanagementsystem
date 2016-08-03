package org.appsquad.viewmodel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.DesignationBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.bean.UnitDesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.rules.Rules;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.HolidayMasterService;
import org.appsquad.service.RunPayRollService;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.itextpdf.text.DocumentException;

import utility.LeaveCalculation;
import utility.OtCalculation;
import utility.PdfPaySlipGenerator;

public class RunPayrollViewModel {
	
	RunPayRollBean runPayRollBean = new RunPayRollBean();
	
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	private UnitMasterBean unitMasterBean = new UnitMasterBean();
	private MonthMasterBean monthMasterBean = new MonthMasterBean();
	private EmployeeSalaryComponentAmountBean amountBean = new EmployeeSalaryComponentAmountBean();
	private HolidayMasterBean holidayMasterBean =new HolidayMasterBean();
	private UnitDesignationBean unitDesignationBean = new UnitDesignationBean();
	
	
	private ArrayList<RunPayRollBean> runPayRollBeanList = new ArrayList<RunPayRollBean>();
	private ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	private ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	private ArrayList<MonthMasterBean> monthList = new ArrayList<MonthMasterBean>();
	private ArrayList<UnitDesignationBean> unitDesignationBeanList = new ArrayList<UnitDesignationBean>();
	
	
	
	Session session = null;
	String userName;
	String date;
	int month;
	int day,year;
	int second, minute, hour;
	String currentDate;
	String currentTime;
	
	public boolean salaryAdjustmentVisibility = true;
	public boolean salaryComponentVisibility  = false;
	
	public boolean nextButtonVisibility = false;
	public boolean prevButtonVisibility = false;
	public boolean calculateButtonVisibility = false;
	
	public boolean allChecked = false;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		System.out.println("R U N P A Y R O L L");
		dateFormat();
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
	//	EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
		RunPayRollDao.loadMonthList(monthList);
		loadLeaveYrDate();
		
		runPayRollBean.setLeaveYrId(holidayMasterBean.getLeaveYrId());
		Calendar now = Calendar.getInstance();
	    
	    int monthId = now.get(Calendar.MONTH) + 1;
	    for(MonthMasterBean monthbean: monthList){
	    	if(monthbean.getMonthId()==monthId){
	    		monthMasterBean.setMonthName(monthbean.getMonthName());
	    	}
	    }
		
	}

	
	
	
	@Command
	@NotifyChange("*")
	public void onSelectMonth(){
		//System.out.println("MONTH " + monthMasterBean.getMonthName() + " - ID - " + monthMasterBean.getMonthId());
		runPayRollBean.setSelectedMonthId(monthMasterBean.getMonthId());
		runPayRollBean.setMonthName(monthMasterBean.getMonthName());
	}
	@Command
	@NotifyChange("*")
	public void onSelectCompany(){
		//System.out.println("MONTH " + companyMasterBean.getCompanyName() + " - ID - " + companyMasterBean.getCompanyId());
		unitMasterBean.setUnitName(null);
		unitDesignationBean.setUnitDesignation(null);
		runPayRollBeanList.clear();
		calculateButtonVisibility = false;
		nextButtonVisibility = false;
		unitMasterBeanList = EmployeeMasterService.loadUnitBeanListWrtCompany(companyMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnit(){
		
		runPayRollBean.setSelectedUnitId(unitMasterBean.getUnitId());
		
		/*if(monthMasterBean.getMonthName()!=null && unitMasterBean.getUnitName()!=null && unitMasterBean.getUnitId()>0){
			RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		}
		if(runPayRollBeanList.size()>0){
			nextButtonVisibility = true;
		}else{
			nextButtonVisibility = false;
		}*/
		
		//runPayRollBean.setTotalNumberOfDayseveryMonth(RunPayRollService.totnoOfDaysInMonth(month, year));
		
		unitDesignationBeanList = EmployeeMasterService.loadUnitDesignation(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		
		/*runPayRollBean.setTotalNumberOfDayseveryMonth(RunPayRollService.totnoOfDaysInMonth(monthMasterBean.getMonthId(), year));
		
		//System.out.println("NO OF DAYS MONTH VIEW MODEL " + runPayRollBean.getTotalNumberOfDayseveryMonth());
		
		int s = sunDayCheck();
		System.out.println("TOTtal no of sun deed " + s);
		
		int t = satDayCheck();
		System.out.println("TOTtal no of sat deed " + t);
		
		int u = monDayCheck();
		System.out.println("TOTtal no of mon deed " + u);
		
		int v = tuesDayCheck();
		System.out.println("TOTtal no of mon deed " + v);
		
		int w = generalHolidaysCheck();
		System.out.println("TOTtal no of general deed " + w);
		
		runPayRollBean.totalNumberOfHolidays = s+t+u+v+w;
		
		System.out.println("Total Number Of Holidays " + runPayRollBean.totalNumberOfHolidays);
		
		int x = sundayDateCheck();
		System.out.println("Total Number of sunday check " + x);
		
		runPayRollBean.totalNumberOfHolidays = runPayRollBean.totalNumberOfHolidays - x;
		
		runPayRollBean.setTotalNumberOfWorkingDaysEveryMonth(runPayRollBean.getTotalNumberOfDayseveryMonth() - runPayRollBean.totalNumberOfHolidays);*/
		//System.out.println("TOTAL WORKING DAYS " + runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth());
		
		/********************************************LOAD EMPLOYEE DETAILS*******************************************************************************/
		
		/*if(monthMasterBean.getMonthName()!=null && unitMasterBean.getUnitName()!=null && unitMasterBean.getUnitId()>0){
			//RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
			runPayRollBean.setMonthName(monthMasterBean.getMonthName());
			runPayRollBean.setYear(String.valueOf(year));
			RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth());
		}
		if(runPayRollBeanList.size()>0){
			nextButtonVisibility = true;
		}else{
			nextButtonVisibility = false;
		}*/
		
		//runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sunDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		//runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		//RunPayRollService.countHoliDaysPerMonth(runPayRollBean, month, year);
		
		
	}
	
	
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitDesignation(){
		
		runPayRollBean.setSelectedCurrentYr(year);
		
		//System.out.println("Select cccc " + unitDesignationBean.getUnitDesignationId());
		
		System.out.println("SELECTED MONTH ID >>> >> > " + runPayRollBean.getSelectedMonthId());
		System.out.println("SELECTED UNIT ID >>> >> > " + runPayRollBean.getSelectedUnitId());
		System.out.println("CURRENT YR >>> >> >  " + year);
		
		if(monthMasterBean.getMonthName()!=null && companyMasterBean.getCompanyId()>0 && unitMasterBean.getUnitId()>0 && unitDesignationBean.getUnitDesignationId()>0){
			
			runPayRollBean.setMonthName(monthMasterBean.getMonthName());
			runPayRollBean.setYear(String.valueOf(year));
			
			RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), 
					unitMasterBean.getUnitId(), runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth(), 
					unitDesignationBean.getUnitDesignationId());
			runPayRollBean.setBaseDays( RunPayRollDao.getBaseDays(runPayRollBean.getSelectedMonthId(), runPayRollBean.getSelectedUnitId(), runPayRollBean.getSelectedCurrentYr()));
		}
		if(runPayRollBeanList.size()>0){
			nextButtonVisibility = true;
			calculateButtonVisibility = true;
		}else{
			nextButtonVisibility = false;
			calculateButtonVisibility = false;
		}	
	}
	
	
	
	@Command
	@NotifyChange
	public void onClickAdjment(@BindingParam("bean") RunPayRollBean bean){
		Map<String, Object> parenMap = new HashMap<String, Object>();
		parenMap.put("parentList", bean);
		parenMap.put("companyName", companyMasterBean.getCompanyName());
		parenMap.put("unitName", unitMasterBean.getUnitName());
		parenMap.put("salaryMonth", monthMasterBean.getMonthName());
		parenMap.put("Year", year);
		
		
		Window window = (Window) Executions.createComponents("/WEB-INF/view/employeePaymentDetails.zul", null, parenMap);
		window.doModal();
	}
	
	@Command
	@NotifyChange("*")
	public void onclickConfirm() throws DocumentException, Exception{
		
		StringBuilder stringBuilder = new StringBuilder();
		for(RunPayRollBean rBean: runPayRollBeanList){
			
			if(rBean.isChecked()){
				
				ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				ArrayList<EmployeeSalaryComponentAmountBean> deductList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				
				
				System.out.println("COMPANY " + companyMasterBean.getCompanyName());
				//System.out.println("MMMMMMMMMM " +rBean.otSalary);
				//System.out.println("deddc  " + rBean.leaveDeduction);
				//System.out.println("NETs >>> >> > " + rBean.getNetSalary());
				//System.out.println("Workingssss " + rBean.getTotalNumberOfDayseveryMonth());
				//System.out.println("Working days " + rBean.getTotalNumberOfWorkingDaysEveryMonth());
				
				/*stringBuilder.append("ID : " +rBean.getEmpId() +" : "+ rBean.getEmpName() + " : " + companyMasterBean.getCompanyName() + unitMasterBean.getUnitId() +" : " + monthMasterBean.getMonthName() +" : " + year + " : "+ rBean.getTotalNumberOfWorkingDaysEveryMonth()  +"\n");
				
				 for(EmployeeSalaryComponentAmountBean sBean : rBean.getComponentAmountBeanList()){
					 
					 if(sBean.getComponentType().equalsIgnoreCase("EARNING")){
						 earnList.add(sBean);
					 }if(sBean.getComponentType().equalsIgnoreCase("DEDUCTION")){
						 deductList.add(sBean);
					 }
			     }
				 
				 stringBuilder.append("EARNINGS "+ "\n");
				 for(EmployeeSalaryComponentAmountBean sBean : earnList){
					 stringBuilder.append("Components : "+ sBean.getComponentName() + " : " + sBean.getComponentAmount() + " : " +sBean.getComponentType() +"\n");
				 }
				 
				 stringBuilder.append(" "+ "\n");
				 stringBuilder.append("DEDUCTIONS "+ "\n");
				 
				 for(EmployeeSalaryComponentAmountBean sBean : deductList){
					 stringBuilder.append("Components : "+ sBean.getComponentName() + " : " + sBean.getComponentAmount() + " : " +sBean.getComponentType() +"\n");
					 }
				 stringBuilder.append("--------------------------------------------" +"\n");*/
				
				String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
				//System.out.println("pdf_path >>> >> > " + pdfPath);
				PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
				runPayRollBean.setMonthName(monthMasterBean.getMonthName());
				runPayRollBean.setYear(String.valueOf(year));
			    paySlipGenerator.getDetails(stringBuilder.toString(),pdfPath, runPayRollBeanList, runPayRollBean, companyMasterBean.getCompanyName(), unitMasterBean.getUnitName());
			}
			
			//System.out.println(">>> >> > " + stringBuilder.toString());
		}
		/*String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		System.out.println("pdf_path >>> >> > " + pdfPath);
		PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
	    paySlipGenerator.getDetails(stringBuilder.toString(),pdfPath, runPayRollBeanList, runPayRollBean);*/
	
	}
	
	@Command
	@NotifyChange("*")
	public void onclickGenerateSheet() throws Exception{
		String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		runPayRollBean.setComapnyName(companyMasterBean.getCompanyName());
		runPayRollBean.setUnitName(unitMasterBean.getUnitName());
		PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
	    paySlipGenerator.getSheetDetails(pdfPath, runPayRollBeanList, runPayRollBean);

	}
	
	@Command
	@NotifyChange("*")
	public void onCheckAll(){
		
		if(runPayRollBeanList.size()>0){
			if(allChecked){
				for(RunPayRollBean bean : runPayRollBeanList){
					bean.setChecked(true);	
				}
			}else{
				for(RunPayRollBean bean : runPayRollBeanList){
					bean.setChecked(false);	
				}
			}
			
		}
		
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickNext(){
		salaryAdjustmentVisibility = false;
		salaryComponentVisibility = true;
		nextButtonVisibility = false;
		prevButtonVisibility = true;
	}
	
	@Command
	@NotifyChange("*")
	public void onClickPrevious(){
		salaryAdjustmentVisibility = true;
		salaryComponentVisibility = false;
		nextButtonVisibility = true;
		prevButtonVisibility = false;
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClcikOtandLeaveSave(@BindingParam("bean") RunPayRollBean bean){
		
		//System.out.println("Total Salary " + bean.getTotalSalary());
		//System.out.println("Total Deduction " + bean.getTotalDeduction());
		//System.out.println("Net Salary " + bean.getNetSalary());
		//System.out.println("OT Days " + bean.getOtDaysF());
		//System.out.println("OT Hours " + bean.getOtHoursF());
		//System.out.println("Leave Days " + bean.getLeaveDaysF());
		//System.out.println("Leave Hours " + bean.getLeaveHoursF());
		
		double hourPerDay = RunPayRollService.hoursPerDay(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean);
		
		/******************************************OT CALCULATION**********************************************************************/
		
		if(bean.getOtHoursF() == null){
			bean.setOtHoursF(0.0);
		}
		if(bean.getOtDaysF() == null){
			bean.setOtDaysF(0);
		}
		
		
		bean.setTotalOtHoursF(bean.getOtHoursF()+(bean.getOtDaysF()*hourPerDay));
		
		
		
		//double otSalary = 0;
		//bean.otSalary = (bean.getTotalSalary()/(runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth()*hourPerDay))*bean.getTotalOtHoursF();
		
		if(bean.getTotalOtHoursF()>0.0){
			
			bean.otSalary = (bean.getNetSalary()/(runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth()*hourPerDay))*bean.getTotalOtHoursF();
			
			//System.out.println(" >>> >> >" + bean.otSalary);
			
			NumberFormat formatter = new DecimalFormat("#0.00");  
			double d = Double.parseDouble(formatter.format(bean.otSalary));
			System.out.println(" >>> >> >" + bean.otSalary);
			
			//String decimalformat = new DecimalFormat("#.##").format(bean.otSalary);
			//double d = Double.parseDouble(decimalformat);
			bean.otSalary = d;
			
			bean.setNetSalary(bean.getNetSalary()+bean.otSalary);
		
		}
		
		
		
		
		/************************************LEAVE CALCULATION*************************************************/
		
		
		if(bean.getLeaveHoursF() == null){
			bean.setLeaveHoursF(0.0);
		}if(bean.getLeaveDaysF() == null){
			bean.setLeaveDaysF(0);
		}
		bean.setTotalLeaveHoursF(bean.getLeaveHoursF()+(bean.getLeaveDaysF()*hourPerDay));
		
		if(bean.getTotalLeaveHoursF()>0.0){
			
			//bean.leaveDeduction = (bean.getTotalSalary()/(runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth()*hourPerDay))*bean.getTotalLeaveHoursF();

			bean.leaveDeduction = (bean.getNetSalary()/(runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth()*hourPerDay))*bean.getTotalLeaveHoursF();
			
			System.out.println("Leave deduction >>> >> > " + bean.leaveDeduction);
			
			NumberFormat formatter1 = new DecimalFormat("#0.00");  
			double d2 = Double.parseDouble(formatter1.format(bean.leaveDeduction));
			
			//String decimalformat2 = new DecimalFormat("#.##").format(bean.leaveDeduction);
			//double d2 = Double.parseDouble(decimalformat2);
			bean.leaveDeduction = d2;
			
			bean.setNetSalary(bean.getNetSalary()-bean.leaveDeduction);
			
			bean.setTotalNumberOfWorkingDaysEveryMonth(runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth()-bean.getLeaveDaysF());
			
			System.out.println("AFter leave day >>> >> > " + bean.getTotalNumberOfDayseveryMonth());
			
			
		}
		
		
		
	}
	
	@Command
	@NotifyChange("*")
	public void onChangePresentDays(@BindingParam("bean") RunPayRollBean bean){
		Integer otDays = 0 ;
		int basedays = RunPayRollDao.getBaseDays(runPayRollBean.getSelectedMonthId(),
				runPayRollBean.getSelectedUnitId(), runPayRollBean.getSelectedCurrentYr());
		
		if(basedays < bean.getPresentDay() ){
			otDays = bean.getPresentDay() - basedays ;
		}
			bean.setOtHoursF(otDays.doubleValue());
	}
	
	
	@Command
	@NotifyChange("*")
	public void onChangeOt(@BindingParam("bean")RunPayRollBean bean){
		Messagebox.show("Ot changed!");
	}
	
	@Command
	@NotifyChange("*")
	public void onClickCalculate(){
		int baseDays;
		baseDays = RunPayRollDao.getBaseDays(runPayRollBean.getSelectedMonthId(), runPayRollBean.getSelectedUnitId(), runPayRollBean.getSelectedCurrentYr());
		
		if(companyMasterBean.getCompanyId()==36 || companyMasterBean.getCompanyId() == 37){
			
			for(RunPayRollBean bean : runPayRollBeanList){
				
				if(bean.getPresentDay()!=null){
					bean.setBaseDays(baseDays);
					double grossTotal=0.0,deduction=0.0;
					ArrayList<EmployeeSalaryComponentAmountBean> earningList = new ArrayList<EmployeeSalaryComponentAmountBean>();
					ArrayList<EmployeeSalaryComponentAmountBean> deductionList = new ArrayList<EmployeeSalaryComponentAmountBean>();
					
					//if(bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN") || bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN SUPERVISOR") || bean.getEmpDesignation().equalsIgnoreCase("GUN MAN")){
						for(EmployeeSalaryComponentAmountBean salBean : bean.getComponentAmountBeanList()){
							
							if(salBean.getComponentTypeId()==1){
								if( ! salBean.getComponentName().equalsIgnoreCase("WAGES"))
								earningList.add(salBean);
							}else{
								deductionList.add(salBean);
							}
								
						}
						for(EmployeeSalaryComponentAmountBean earn: earningList){
							
							if(earn.getComponentName().equalsIgnoreCase("BASIC")){
								earn.setComponentAmount(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()));
							}
							if(earn.getComponentName().equalsIgnoreCase("HRA")){
								
								if(bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN") || bean.getEmpDesignation().equalsIgnoreCase("EX-MAN SUPERVISOR") 
										|| bean.getEmpDesignation().equalsIgnoreCase("GUN MAN")){
									earn.setComponentAmount(bean.getWages()*0.15);
								}else{
									earn.setComponentAmount(bean.getWages()*0.05);
								}	
							}
							
							if(earn.getComponentName().equalsIgnoreCase("CONVEYENCE")){
								earn.setComponentAmount(20*bean.getPresentDay());
							}
							if(earn.getComponentName().equalsIgnoreCase("WASHING ALLOWANCES")){
								earn.setComponentAmount(5*bean.getPresentDay());
							}
							if(!bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN") || !bean.getEmpDesignation().equalsIgnoreCase("EX-MAN SUPERVISOR") || !bean.getEmpDesignation().equalsIgnoreCase("GUN MAN")
						                              || !bean.getEmpDesignation().equalsIgnoreCase("TOKEN KEEPER CUM DRIVER") || !bean.getEmpDesignation().equalsIgnoreCase("FACTORY DRIVER")
						                              || !bean.getEmpDesignation().equalsIgnoreCase("COMPUTER OPERATOR") ||  !bean.getEmpDesignation().equalsIgnoreCase("CIVILIAN GUARD")){
								if(earn.getComponentName().equalsIgnoreCase("ALLOWANCES")){
									earn.setComponentAmount(50*bean.getPresentDay());
								}
							}
							if(bean.getEmpDesignation().equalsIgnoreCase("GUN MAN")){
								if(earn.getComponentName().equalsIgnoreCase("WEAPON ALLOWANCES")){
									earn.setComponentAmount(50*bean.getPresentDay());
								}
							}
							
							if(bean.getSelectedMonthId()==10){
								if(earn.getComponentName().equalsIgnoreCase("BONUS")){
									earn.setComponentAmount(bean.getWages()*0.0833);
								}
							}
							if(bean.getComapnyName().equalsIgnoreCase("EX-MAN ALLOWANCES")){
								earn.setComponentAmount((earn.getComponentAmount()*bean.getPresentDay())/bean.getBaseDays());
							}
							if(!earn.getComponentName().equalsIgnoreCase("BASIC") && !earn.getComponentName().equalsIgnoreCase("HRA") 
									&& !earn.getComponentName().equalsIgnoreCase("CONVEYENCE")
									&& !earn.getComponentName().equalsIgnoreCase("WASHING ALLOWANCES") && !earn.getComponentName().equalsIgnoreCase("ALLOWANCES")
									&& !earn.getComponentName().equalsIgnoreCase("WEAPON ALLOWANCES") && !earn.getComponentName().equalsIgnoreCase("BONUS") 
									&& !bean.getComapnyName().equalsIgnoreCase("EX-MAN ALLOWANCES")){
								earn.setComponentAmount((earn.getComponentAmount()*bean.getPresentDay())/bean.getBaseDays());
							}
							grossTotal += earn.getComponentAmount();
						}
						if(bean.getOtHoursF()!=null)
							
							bean.otSalary = Rules.getOtSalary(bean.getWages(), bean.getBaseDays(), bean.getOtHoursF());
							System.out.println("OT S A L >>> >> > " + bean.otSalary);		
							grossTotal = grossTotal+bean.otSalary;
							System.out.println("Earnigs: "+grossTotal);
					
							for(EmployeeSalaryComponentAmountBean deduct: deductionList){

								if(deduct.getComponentName().equalsIgnoreCase("PF")){
									deduct.setComponentAmount(Rules.getPf(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay())));
								}
								if(deduct.getComponentName().equalsIgnoreCase("PROF TAX")){
									deduct.setComponentAmount(Rules.getPtAmount(grossTotal));
								}
								
								if(deduct.getComponentName().equalsIgnoreCase("ESI")){
									if(grossTotal <= 15000.00){
										for(EmployeeSalaryComponentAmountBean escb: earningList){
											if(escb.getComponentName().equalsIgnoreCase("WASHING")){
												deduct.setComponentAmount(Rules.getEsi(grossTotal, escb.getComponentAmount()));
											}else{
												deduct.setComponentAmount(Rules.getEsi(grossTotal, escb.getComponentAmount()));
											}
										}
									}
								}
								System.out.println("Deduction: "+deduct.getComponentName()+" Amount: "+deduct.getComponentAmount());
								deduction += deduct.getComponentAmount();
							
						
							}
					
				
				
				}
			}
			
			
		}else {
		
		for(RunPayRollBean bean : runPayRollBeanList ){
			
			if(bean.getPresentDay()!=null){
						
				bean.setBaseDays(baseDays);
				System.out.println("Base days: "+bean.getBaseDays()+" Present :: "+bean.getPresentDay() + " WAGES  "+bean.getWages());
				double grossTotal=0.0,deduction=0.0;
				ArrayList<EmployeeSalaryComponentAmountBean> earningList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				ArrayList<EmployeeSalaryComponentAmountBean> deductionList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				for (EmployeeSalaryComponentAmountBean salBean : bean.getComponentAmountBeanList()) {
					
					if(salBean.getComponentTypeId()==1){
						if( ! salBean.getComponentName().equalsIgnoreCase("WAGES"))
						earningList.add(salBean);
					}else{
						deductionList.add(salBean);
					}
					
					/*if(salBean.getComponentName().equalsIgnoreCase("BASIC")){
						salBean.setComponentAmount(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()));
					}
					if(salBean.getComponentName().equalsIgnoreCase("HRA")){
						salBean.setComponentAmount(Rules.getHra(salBean.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
					}
					if(salBean.getComponentName().equalsIgnoreCase("PF")){
						salBean.setComponentAmount(Rules.getPf(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay())));
					}
					
					if(salBean.getComponentName().equalsIgnoreCase("SPECIAL WORK ALLOWANCES")){
						
						salBean.setComponentAmount(Rules.getSpecialAllowance(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()), 
								bean.getBaseDays(), bean.getPresentDay()));	
					}
					
					if (!salBean.getComponentName().equalsIgnoreCase("BASIC") && !salBean.getComponentName().equalsIgnoreCase("HRA") && 
							!salBean.getComponentName().equalsIgnoreCase("PF") && !salBean.getComponentName().equalsIgnoreCase("ESI") &&
							!salBean.getComponentName().equalsIgnoreCase("WAGES") && !salBean.getComponentName().equalsIgnoreCase("SPECIAL WORK ALLOWANCES")) {
						salBean.setComponentAmount(Rules.getGeneral(salBean.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
						
						//System.out.println("sal >>> >> > " + salBean.getComponentName());
					}
					grossTotal += salBean.getComponentAmount();
					if(salBean.getComponentName().equalsIgnoreCase("ESI")){
						
						salBean.setComponentAmount(Rules.getGeneral(salBean.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
					
					}*/
				}
				
				for (EmployeeSalaryComponentAmountBean salBean : bean.getComponentAmountBeanList()) {
					System.out.println("Com: "+salBean.getComponentName()+" Amount : "+salBean.getComponentAmount());
				}
				for(EmployeeSalaryComponentAmountBean earn: earningList){
					
					if(earn.getComponentName().equalsIgnoreCase("BASIC")){
						earn.setComponentAmount(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()));
					}
					if(earn.getComponentName().equalsIgnoreCase("HRA")){
						earn.setComponentAmount(Rules.getHra(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
					}
					
					if(earn.getComponentName().equalsIgnoreCase("SPECIAL WORK ALLOWANCES")){
						
						earn.setComponentAmount(Rules.getSpecialAllowance(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()), 
								bean.getBaseDays(), bean.getPresentDay()));	
					}
					
					if (!earn.getComponentName().equalsIgnoreCase("BASIC") && !earn.getComponentName().equalsIgnoreCase("HRA") && 
							!earn.getComponentName().equalsIgnoreCase("WAGES") && !earn.getComponentName().equalsIgnoreCase("SPECIAL WORK ALLOWANCES")) {
						earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
						
					}
					
					System.out.println("Earning: "+earn.getComponentName()+" Amount: "+earn.getComponentAmount());
					grossTotal += earn.getComponentAmount();
				}
				
				
				if(bean.getOtHoursF()!=null)
				
				bean.otSalary = Rules.getOtSalary(bean.getWages(), bean.getBaseDays(), bean.getOtHoursF());
				System.out.println("OT S A L >>> >> > " + bean.otSalary);		
				grossTotal = grossTotal+bean.otSalary;
				System.out.println("Earnigs: "+grossTotal);
				
				for(EmployeeSalaryComponentAmountBean deduct: deductionList){
					if(deduct.getComponentName().equalsIgnoreCase("PF")){
						deduct.setComponentAmount(Rules.getPf(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay())));
					}
					if(deduct.getComponentName().equalsIgnoreCase("PROF TAX")){
						deduct.setComponentAmount(Rules.getPtAmount(grossTotal));
					}
					
					if(deduct.getComponentName().equalsIgnoreCase("ESI")){
						if(grossTotal <= 15000.00){
							for(EmployeeSalaryComponentAmountBean escb: earningList){
								if(escb.getComponentName().equalsIgnoreCase("WASHING")){
									deduct.setComponentAmount(Rules.getEsi(grossTotal, escb.getComponentAmount()));
								}else{
									deduct.setComponentAmount(Rules.getEsi(grossTotal, escb.getComponentAmount()));
								}
							}
						}
					}
					System.out.println("Deduction: "+deduct.getComponentName()+" Amount: "+deduct.getComponentAmount());
					deduction += deduct.getComponentAmount();
				}
				System.out.println("Gross:: "+grossTotal+" deduction : "+deduction);
				System.out.println("----------------------------------------------------------------------------------------------------------");
				
			}
			
		}
	}
	
	}
	
	/**************************** initial functions  **********************************************************************************/
	
	public void dateFormat(){
		GregorianCalendar date = new GregorianCalendar();
		  day = date.get(Calendar.DAY_OF_MONTH);
	      month = date.get(Calendar.MONTH)+1;
	      year = date.get(Calendar.YEAR);
	      
	      second = date.get(Calendar.SECOND);
	      minute = date.get(Calendar.MINUTE);
	      hour = date.get(Calendar.HOUR);
	       
	      currentDate =day+"-"+month+"-"+year;
	      currentTime = hour+" : "+minute+" : "+second;
	      
	      System.out.println("MON T " + month);
	      System.out.println("Current Date " +currentDate );
	      System.out.println("current date "+ currentDate +" - " + currentTime);
	      
	}

	
	public void loadLeaveYrDate(){
		HolidayMasterService.loadLeaveYr(holidayMasterBean);
		
	}
	
	public int sunDayCheck(){
		int noOfSundayallocated;
		runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sunDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfSundayallocated = runPayRollBean.getSunDayCountPerMonth();
		//System.out.println("no OF SUNDAY " + noOfSundayallocated);
		if(noOfSundayallocated==5){
			
			//runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sundayDeduction(runPayRollBean, month, year));
			
			runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sundayDeduction(runPayRollBean, monthMasterBean.getMonthId(), year));
			
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getSunDayCountPerMonth());
			//System.out.println("Sunday Count 5 aMonth >>> >> > " + runPayRollBean.getSunDayCountPerMonth());
		}if(noOfSundayallocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfSundayallocated);
			//System.out.println("SSSSUNDAY COUNT 4 >>> >> > " +runPayRollBean.getTotalNumberOfHolidayseveryMonth());
		}
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
		
	}
	
	public int satDayCheck(){
		int noOfSatDayAllocated;
		runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfSatDayAllocated = runPayRollBean.getSatDayCountPerMonth();
		//System.out.println("no OF SATDAY " + noOfSatDayAllocated);
		if(noOfSatDayAllocated ==5){
			//runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satdayDeduction(runPayRollBean, month, year));
			
			runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satdayDeduction(runPayRollBean, monthMasterBean.getMonthId(), year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getSatDayCountPerMonth());
			//System.out.println("SadDay Count 5 aMonth >>> >> > " + runPayRollBean.getSatDayCountPerMonth());
			
		}if(noOfSatDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfSatDayAllocated);
			//System.out.println("SSSSSATDAY COUNT 4 >>> >> > " + runPayRollBean.getSatDayCountPerMonth());
		}
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
	}
	
	public int monDayCheck(){
		int noOfMonDayAllocated;
		runPayRollBean.setMonDayCountPerMonth(RunPayRollService.monDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfMonDayAllocated = runPayRollBean.getMonDayCountPerMonth();
		//System.out.println("no OF MONDAY " + noOfMonDayAllocated);
		if(noOfMonDayAllocated == 5){
			//runPayRollBean.setMonDayCountPerMonth(RunPayRollService.monDayDeduction(runPayRollBean, month, year));
			
			runPayRollBean.setMonDayCountPerMonth(RunPayRollService.monDayDeduction(runPayRollBean, monthMasterBean.getMonthId(), year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getMonDayCountPerMonth());
			//System.out.println("MonDay Count 5 aMonth >>> >> > " + runPayRollBean.getMonDayCountPerMonth());
		}if(noOfMonDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfMonDayAllocated);
			//System.out.println("SSSSMONDAY COUNT 4 >>> >> > " + runPayRollBean.getMonDayCountPerMonth());
		}
		
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
	}
	
	public int tuesDayCheck(){
		int noOfTuesDayAllocated;
		runPayRollBean.setTuesDayCountPerMonth(RunPayRollService.monDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfTuesDayAllocated= runPayRollBean.getTuesDayCountPerMonth();
		//System.out.println("no OF TUESDAY " + noOfTuesDayAllocated);
		if(noOfTuesDayAllocated == 5){
			//runPayRollBean.setTuesDayCountPerMonth(RunPayRollService.tuesDayDeduction(runPayRollBean, month, year));
			
			runPayRollBean.setTuesDayCountPerMonth(RunPayRollService.tuesDayDeduction(runPayRollBean, monthMasterBean.getMonthId(), year));
			
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getTuesDayCountPerMonth());
			//System.out.println("TuesDay Count 5 aMonth >>> >> > " + runPayRollBean.getTuesDayCountPerMonth());
		}if(noOfTuesDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfTuesDayAllocated);
			//System.out.println("SSSSTUESDAY COUNT 4 >>> >> > " + runPayRollBean.getTuesDayCountPerMonth());
		}
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
	}
	
	
	
	
	public int sundayDateCheck(){
		int noOfDateMatchs = 0;
		
		runPayRollBean.setSunDayCountPerMonth(RunPayRollService.sunDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		
		noOfDateMatchs = RunPayRollService.loadholiDayListPerMonth(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean, monthMasterBean.getMonthId(), year, runPayRollBean.getSunDayCountPerMonth());
		//noOfDateMatchs = RunPayRollService.loadholiDayListPerMonth(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean, month, year, runPayRollBean.getSunDayCountPerMonth());
		
		return noOfDateMatchs;
	}
	
	
	
	
	public int generalHolidaysCheck(){
		int noOfHolidays;
		//noOfHolidays = RunPayRollService.generalHolidayDeduction(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean, month);
		
		noOfHolidays = RunPayRollService.generalHolidayDeduction(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean, monthMasterBean.getMonthId());
		
		return noOfHolidays;
		
		
	}
	
	
	
	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public RunPayRollBean getRunPayRollBean() {
		return runPayRollBean;
	}

	public void setRunPayRollBean(RunPayRollBean runPayRollBean) {
		this.runPayRollBean = runPayRollBean;
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

	public ArrayList<MonthMasterBean> getMonthList() {
		return monthList;
	}

	public void setMonthList(ArrayList<MonthMasterBean> monthList) {
		this.monthList = monthList;
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

	public MonthMasterBean getMonthMasterBean() {
		return monthMasterBean;
	}

	public void setMonthMasterBean(MonthMasterBean monthMasterBean) {
		this.monthMasterBean = monthMasterBean;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public ArrayList<RunPayRollBean> getRunPayRollBeanList() {
		return runPayRollBeanList;
	}

	public void setRunPayRollBeanList(ArrayList<RunPayRollBean> runPayRollBeanList) {
		this.runPayRollBeanList = runPayRollBeanList;
	}

	public EmployeeSalaryComponentAmountBean getAmountBean() {
		return amountBean;
	}

	public void setAmountBean(EmployeeSalaryComponentAmountBean amountBean) {
		this.amountBean = amountBean;
	}

	public boolean isSalaryAdjustmentVisibility() {
		return salaryAdjustmentVisibility;
	}

	public void setSalaryAdjustmentVisibility(boolean salaryAdjustmentVisibility) {
		this.salaryAdjustmentVisibility = salaryAdjustmentVisibility;
	}

	public boolean isSalaryComponentVisibility() {
		return salaryComponentVisibility;
	}

	public void setSalaryComponentVisibility(boolean salaryComponentVisibility) {
		this.salaryComponentVisibility = salaryComponentVisibility;
	}

	public boolean isNextButtonVisibility() {
		return nextButtonVisibility;
	}

	public void setNextButtonVisibility(boolean nextButtonVisibility) {
		this.nextButtonVisibility = nextButtonVisibility;
	}

	public boolean isPrevButtonVisibility() {
		return prevButtonVisibility;
	}

	public void setPrevButtonVisibility(boolean prevButtonVisibility) {
		this.prevButtonVisibility = prevButtonVisibility;
	}

	public boolean isAllChecked() {
		return allChecked;
	}

	public void setAllChecked(boolean allChecked) {
		this.allChecked = allChecked;
	}

	public HolidayMasterBean getHolidayMasterBean() {
		return holidayMasterBean;
	}

	public void setHolidayMasterBean(HolidayMasterBean holidayMasterBean) {
		this.holidayMasterBean = holidayMasterBean;
	}

	public UnitDesignationBean getUnitDesignationBean() {
		return unitDesignationBean;
	}

	public void setUnitDesignationBean(UnitDesignationBean unitDesignationBean) {
		this.unitDesignationBean = unitDesignationBean;
	}

	public ArrayList<UnitDesignationBean> getUnitDesignationBeanList() {
		return unitDesignationBeanList;
	}

	public void setUnitDesignationBeanList(
			ArrayList<UnitDesignationBean> unitDesignationBeanList) {
		this.unitDesignationBeanList = unitDesignationBeanList;
	}




	public boolean isCalculateButtonVisibility() {
		return calculateButtonVisibility;
	}




	public void setCalculateButtonVisibility(boolean calculateButtonVisibility) {
		this.calculateButtonVisibility = calculateButtonVisibility;
	}

	

	
	
}
