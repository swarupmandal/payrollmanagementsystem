package org.appsquad.viewmodel;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Pack200.Unpacker;

import javax.swing.tree.ExpandVetoException;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.MonthMasterBean;
import org.appsquad.bean.PayrollExistBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.bean.SheetBean;
import org.appsquad.bean.UnitDesignationBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.dao.ComponentRemoveDao;
import org.appsquad.dao.EmployeeDao;
import org.appsquad.dao.RunPayRollDao;
import org.appsquad.research.DoubleFormattor;
import org.appsquad.rules.Rules;
import org.appsquad.service.EmployeeMasterService;
import org.appsquad.service.EsiReportService;
import org.appsquad.service.HolidayMasterService;
import org.appsquad.service.RunPayRollService;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import utility.PdfPaySlipGenerator;

import com.itextpdf.text.DocumentException;

public class RunPayrollViewModel {
	
	RunPayRollBean runPayRollBean = new RunPayRollBean();
	RunPayRollBean exRunPayRollBean = new RunPayRollBean();
	
	RunPayRollBean pdfSheetBean = new RunPayRollBean();
	
	private CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	private UnitMasterBean unitMasterBean = new UnitMasterBean();
	private MonthMasterBean monthMasterBean = new MonthMasterBean();
	private EmployeeSalaryComponentAmountBean amountBean = new EmployeeSalaryComponentAmountBean();
	private HolidayMasterBean holidayMasterBean =new HolidayMasterBean();
	private UnitDesignationBean unitDesignationBean = new UnitDesignationBean();
	private SheetBean sheetbean = new SheetBean();
	
	private ArrayList<RunPayRollBean> runPayRollBeanList = new ArrayList<RunPayRollBean>();
	private ArrayList<RunPayRollBean> pdfBeanList = new ArrayList<RunPayRollBean>();
	private ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	private ArrayList<UnitMasterBean> unitMasterBeanList = new ArrayList<UnitMasterBean>();
	private ArrayList<MonthMasterBean> monthList = new ArrayList<MonthMasterBean>();
	private static ArrayList<String> yearList = null;
	private ArrayList<UnitDesignationBean> unitDesignationBeanList = new ArrayList<UnitDesignationBean>();
	private ArrayList<SheetBean> sheetTypeBeanList = new ArrayList<SheetBean>();
	private ArrayList<RunPayRollBean> payrollExistBeanList = new ArrayList<RunPayRollBean>();
	
	
	private String selectedYear = null;
	
	private PayrollExistBean exPayrollBn = new PayrollExistBean();
	private CompanyMasterBean companyMasterBean2 = new CompanyMasterBean();
	private UnitMasterBean unitMasterBean2 = new UnitMasterBean();
	MonthMasterBean monthBean2 = new MonthMasterBean();
	public UnitDesignationBean unitDesignationBean2 = new UnitDesignationBean();
	
	ArrayList<String> lvYrList2 = new ArrayList<String>();
	private ArrayList<MonthMasterBean> monthList2 = new ArrayList<MonthMasterBean>();
	private ArrayList<CompanyMasterBean> companyBeanList2 = new ArrayList<CompanyMasterBean>();
	private ArrayList<UnitMasterBean> unitMasterBeanList2 = new ArrayList<UnitMasterBean>();
	public ArrayList<UnitDesignationBean> unitDesignationList2 = new ArrayList<UnitDesignationBean>();
	private ArrayList<PayrollExistBean> payrollExistBeanList2 = new ArrayList<PayrollExistBean>();
	//private ArrayList<E>
	
	
	Session session = null;
	String userName;
	String date;
	int month;
	int day,year;
	int second, minute, hour;
	String currentDate;
	String currentTime;
	int slNo = 0;
	
	public boolean salaryAdjustmentVisibility = true;
	public boolean salaryComponentVisibility  = false;
	public boolean upperComponentVisibility  = true;
	
	public boolean nextButtonVisibility = false;
	public boolean prevButtonVisibility = false;
	public boolean calculateButtonVisibility = false;
	
	public boolean allChecked = false;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		session = Sessions.getCurrent();
		userName = (String) session.getAttribute("userId");
		//System.out.println("R U N P A Y R O L L");
		dateFormat();
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
		//EmployeeMasterService.loadUnitBeanList(unitMasterBeanList);
		RunPayRollDao.loadMonthList(monthList);
		loadYearList();
		sheetTypeBeanList =  RunPayRollDao.loadSheetTypeList();
		loadLeaveYrDate();
		
		runPayRollBean.setLeaveYrId(holidayMasterBean.getLeaveYrId());
		Calendar now = Calendar.getInstance();
	    
		lvYrList2 = EsiReportService.getLvYr();
		
	    int monthId = now.get(Calendar.MONTH) + 1;
	    for(MonthMasterBean monthbean: monthList){
	    	if(monthbean.getMonthId()==monthId){
	    		//monthMasterBean.setMonthName(monthbean.getMonthName());
	    		runPayRollBean.setSelectedMonthId(monthMasterBean.getMonthId());
	    		runPayRollBean.setMonthName(monthMasterBean.getMonthName());
	    	}
	    }
		
	}

	public static void loadYearList(){
	//	yearList = RunPayRollService.loadYearList();
		yearList = EsiReportService.getLvYr();
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectYear(){
		System.out.println("Selected year:: "+exPayrollBn.getLvYr2());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectMonth(){
		
		runPayRollBean.setSelectedMonthId(monthMasterBean.getMonthId());
		runPayRollBean.setMonthName(monthMasterBean.getMonthName());
		
		unitMasterBean.setUnitName(null);
		unitDesignationBean.setUnitDesignation(null);
		calculateButtonVisibility = false;
		nextButtonVisibility = false;
		runPayRollBeanList.clear();
		companyMasterBean.setCompanyName(null);
		sheetbean.setSheetType(null);
		EmployeeMasterService.loadCompanyBeanList(companyBeanList);
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectCompany(){
		runPayRollBeanList.clear();
		unitMasterBean.setUnitName(null);
		unitDesignationBean.setUnitDesignation(null);
		sheetbean.setSheetType(null);
		runPayRollBeanList.clear();
		calculateButtonVisibility = false;
		nextButtonVisibility = false;
		
		unitMasterBeanList = EmployeeMasterService.loadUnitBeanListWrtCompany(companyMasterBean.getCompanyId());
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnit(){
		runPayRollBeanList.clear();
		unitDesignationBean.setUnitDesignation(null);
		sheetbean.setSheetType(null);
		
		runPayRollBean.setWagesTypeId(unitMasterBean.getWagesTypeId());
		runPayRollBean.setSunSelId(unitMasterBean.getSundaySelectionId());
		runPayRollBean.setSelectedUnitId(unitMasterBean.getUnitId());
		runPayRollBean.setSelectedCompanyId(companyMasterBean.getCompanyId());
		
		/*if(monthMasterBean.getMonthName()!=null && unitMasterBean.getUnitName()!=null && unitMasterBean.getUnitId()>0){
			RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		}
		if(runPayRollBeanList.size()>0){
			nextButtonVisibility = true;
		}else{
			nextButtonVisibility = false;
		}*/
		
		//runPayRollBean.setTotalNumberOfDayseveryMonth(RunPayRollService.totnoOfDaysInMonth(month, year));
		ComponentRemoveDao.addComponent(runPayRollBean.getSelectedUnitId()); //reset all components as 'N' for selected unit 
		unitDesignationBeanList = EmployeeMasterService.loadUnitDesignation(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		
		/*runPayRollBean.setTotalNumberOfDayseveryMonth(RunPayRollService.totnoOfDaysInMonth(monthMasterBean.getMonthId(), year));
		
		////System.out.println("NO OF DAYS MONTH VIEW MODEL " + runPayRollBean.getTotalNumberOfDayseveryMonth());
		
		int s = sunDayCheck();
		//System.out.println("TOTtal no of sun deed " + s);
		
		int t = satDayCheck();
		//System.out.println("TOTtal no of sat deed " + t);
		
		int u = monDayCheck();
		//System.out.println("TOTtal no of mon deed " + u);
		
		int v = tuesDayCheck();
		//System.out.println("TOTtal no of mon deed " + v);
		
		int w = generalHolidaysCheck();
		//System.out.println("TOTtal no of general deed " + w);
		
		runPayRollBean.totalNumberOfHolidays = s+t+u+v+w;
		
		//System.out.println("Total Number Of Holidays " + runPayRollBean.totalNumberOfHolidays);
		
		int x = sundayDateCheck();
		//System.out.println("Total Number of sunday check " + x);
		
		runPayRollBean.totalNumberOfHolidays = runPayRollBean.totalNumberOfHolidays - x;
		
		runPayRollBean.setTotalNumberOfWorkingDaysEveryMonth(runPayRollBean.getTotalNumberOfDayseveryMonth() - runPayRollBean.totalNumberOfHolidays);*/
		////System.out.println("TOTAL WORKING DAYS " + runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth());
		
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
		
		sheetbean.setSheetType(null);
		runPayRollBeanList.clear();
		sheetTypeBeanList =  RunPayRollDao.loadSheetTypeList();
		runPayRollBean.setSelectedCurrentYr(year);
		
		//int watypeId = RunPayRollService.fetchWagesTypeId(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId());
		//runPayRollBean.setWagesTypeId(watypeId);
		//System.out.println("0-------------------------------------------------------------------- wa type id " + runPayRollBean.getWagesTypeId());
		
		
		//System.out.println("Select cccc " + unitDesignationBean.getUnitDesignationId());
		
		//System.out.println("SELECTED MONTH ID >>> >> > " + runPayRollBean.getSelectedMonthId());
		//System.out.println("SELECTED UNIT ID >>> >> > " + runPayRollBean.getSelectedUnitId());
		//System.out.println("CURRENT YR >>> >> >  " + year);
		
		/*if(monthMasterBean.getMonthName()!=null && companyMasterBean.getCompanyId()>0 && unitMasterBean.getUnitId()>0 && unitDesignationBean.getUnitDesignationId()>0){
			
			runPayRollBean.setMonthName(monthMasterBean.getMonthName());
			runPayRollBean.setYear(String.valueOf(year));
			pdfBeanList.clear();
			RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), 
					unitMasterBean.getUnitId(), runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth(), 
					unitDesignationBean.getUnitDesignationId());
			for(RunPayRollBean runPayRollBean : runPayRollBeanList){
				runPayRollBean.setSpecialTime(0.0);
				runPayRollBean.setTotalSalary(0.0);
				runPayRollBean.setTotalDeduction(0.0);
				runPayRollBean.setNetSalary(0.0);
				runPayRollBean.setSelectedCompanyId(companyMasterBean.getCompanyId());
				runPayRollBean.setSelectedUnitId(unitMasterBean.getUnitId());
				runPayRollBean.setSelectedCurrentYr(year);
				runPayRollBean.setMonthName(monthMasterBean.getMonthName());
				runPayRollBean.setUnitDesignation(unitDesignationBean.getUnitDesignation());
				runPayRollBean.setSelectedUnitDesignationId(unitDesignationBean.getUnitDesignationId());
				runPayRollBean.setBaseDays( RunPayRollDao.getBaseDays(runPayRollBean.getSelectedMonthId(), 
						runPayRollBean.getSelectedUnitId(), runPayRollBean.getSelectedCurrentYr()));
			}
			
		}
		if(runPayRollBeanList.size()>0){
			//nextButtonVisibility = true;
			calculateButtonVisibility = true;
		}else{
			nextButtonVisibility = false;
			calculateButtonVisibility = false;
		}*/	
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectType(){
		
		if(runPayRollBeanList.size()>0){
			runPayRollBeanList.clear();
		}
		runPayRollBean.setSheetType(sheetbean.getSheetType());
		
		Map<String, Object> parenMap = new HashMap<String, Object>();
		ComponentMasterBean componentMasterBean = new ComponentMasterBean();
		componentMasterBean.setUnitId(unitMasterBean.getUnitId());
		componentMasterBean.setCompanyId(companyMasterBean.getCompanyId());
		parenMap.put("parent", componentMasterBean);
		
		Window window = (Window) Executions.createComponents("/WEB-INF/view/sheetComponents.zul", null, parenMap);
		window.doModal();
	}
	
	
	
	/**
	 * func using to return list with selected component from sheet type
	 * swarup 
	 */
	
	@Command
	@NotifyChange("*")
	public void onClickLoad(){
		//System.out.println("Year: "+year);
		//System.out.println("Month:: "+monthMasterBean.getMonthName());
		//System.out.println("Company:: "+companyMasterBean.getCompanyId()+" "+companyMasterBean.getCompanyName());
		//System.out.println("Unit:: "+unitMasterBean.getUnitId()+" "+unitMasterBean.getUnitName());
		//System.out.println("Unit desg :: "+unitDesignationBean.getUnitDesignationId()+" "+unitDesignationBean.getUnitDesignation());
		//System.out.println("Sheet type Id: "+sheetbean.getSheetTypeId()+" "+sheetbean.getSheetType());
		if(exPayrollBn.getLvYr2()!=null){
			if(monthMasterBean.getMonthName()!=null){
				if(companyMasterBean.getCompanyId()>0){
					if(unitMasterBean.getUnitId()>0){
						if(unitDesignationBean.getUnitDesignationId()!=null){
							if(sheetbean.getSheetTypeId()>0){
								if( monthMasterBean.getMonthName()!=null && companyMasterBean.getCompanyId()>0 
										&& unitMasterBean.getUnitId()>0 && unitDesignationBean.getUnitDesignationId()>0){

									runPayRollBean.setMonthName(monthMasterBean.getMonthName());
									runPayRollBean.setYear(String.valueOf(year));
									pdfBeanList.clear();
									RunPayRollService.loadEmpDetails(runPayRollBeanList,companyMasterBean.getCompanyId(), 
											unitMasterBean.getUnitId(), runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth(), 
											unitDesignationBean.getUnitDesignationId());
									for(RunPayRollBean runPayRollBean : runPayRollBeanList){
										runPayRollBean.setSheetType(sheetbean.getSheetType());
										runPayRollBean.setSpecialTime(0.0);
										runPayRollBean.setTotalSalary(0.0);
										runPayRollBean.setTotalDeduction(0.0);
										runPayRollBean.setNetSalary(0.0);
										runPayRollBean.setSelectedCompanyId(companyMasterBean.getCompanyId());
										runPayRollBean.setSelectedUnitId(unitMasterBean.getUnitId());
										runPayRollBean.setSelectedCurrentYr(year);
										runPayRollBean.setMonthName(monthMasterBean.getMonthName());
										runPayRollBean.setUnitDesignation(unitDesignationBean.getUnitDesignation());
										runPayRollBean.setSelectedUnitDesignationId(unitDesignationBean.getUnitDesignationId());
										runPayRollBean.setBaseDays( RunPayRollDao.getBaseDays(runPayRollBean.getSelectedMonthId(), 
												runPayRollBean.getSelectedUnitId(), runPayRollBean.getSelectedCurrentYr(), runPayRollBean.getSunSelId()));
									}

									int count = RunPayRollService.countSalStore(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), unitDesignationBean.getUnitDesignationId(), monthMasterBean.getMonthName(), String.valueOf(year));

									if(count>0){
										runPayRollBean.setSaveDisabled(true);
									}else {
										runPayRollBean.setSaveDisabled(false);
									}	
								}
								if(runPayRollBeanList.size()>0){
									//nextButtonVisibility = true;
									calculateButtonVisibility = true;
								}else{
									nextButtonVisibility = false;
									calculateButtonVisibility = false;
								}

							}else{
								Messagebox.show("SELECT SHEET TYPE", "Alert", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}else{
							Messagebox.show("Please select unit designation!", "Year required", Messagebox.OK, Messagebox.EXCLAMATION);
						}
					}else{
						Messagebox.show("Please select unit!", "Year required", Messagebox.OK, Messagebox.EXCLAMATION);
					}
				}else{
					Messagebox.show("Please select company!", "Company name required", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}else{
				Messagebox.show("Please select month!", "Month required", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}else{
			Messagebox.show("Please select year!", "Year required", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		// ADD COMPONENTS AGAIN
	//	ComponentRemoveDao.addComponent(unitMasterBean.getUnitId());
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
		boolean isChecked = false;
		for(RunPayRollBean rBean: pdfBeanList){
			if(rBean.isChecked()){
				isChecked = true;
			}
		}
		
		if(isChecked){
			String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
			PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
			pdfSheetBean.setComapnyName(companyMasterBean.getCompanyName());
			pdfSheetBean.setUnitName(unitMasterBean.getUnitName());
			pdfSheetBean.setCurrentDate(currentDate);
			pdfSheetBean.setMonthName(monthMasterBean.getMonthName());
			//pdfSheetBean.setYear(String.valueOf(year));
			pdfSheetBean.setYear(exPayrollBn.getLvYr2());
			pdfSheetBean.setUnitDesignation(unitDesignationBean.getUnitDesignation());
			paySlipGenerator.getSlipDetails(pdfPath, pdfBeanList, pdfSheetBean);
		}else{
			Messagebox.show("Please check at least one","Alert Information",Messagebox.OK,Messagebox.EXCLAMATION);
		}
		/*String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		//System.out.println("pdf_path >>> >> > " + pdfPath);
		PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
	    paySlipGenerator.getDetails(stringBuilder.toString(),pdfPath, runPayRollBeanList, runPayRollBean);*/
	
	}
	
	@Command
	@NotifyChange("*")
	public void onclickGenerateSheet() throws Exception{
		String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		pdfSheetBean.setComapnyName(companyMasterBean.getCompanyName());
		pdfSheetBean.setUnitName(unitMasterBean.getUnitName());
		pdfSheetBean.setCurrentDate(currentDate);
		pdfSheetBean.setMonthName(monthMasterBean.getMonthName());
		//pdfSheetBean.setYear(String.valueOf(year));
		pdfSheetBean.setYear(exPayrollBn.getLvYr2());
		pdfSheetBean.setUnitDesignation(unitDesignationBean.getUnitDesignation());
		pdfSheetBean.setSheetType(sheetbean.getSheetType());
		PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
		ArrayList<RunPayRollBean> selectedEmployeeList = new ArrayList<RunPayRollBean>();
	    boolean isChecked =  false;
	    for(RunPayRollBean payRollBean : pdfBeanList){
	    	if(payRollBean.isChecked()){
	    		selectedEmployeeList.add(payRollBean);
	    		isChecked = true;
	    	}
	    }
		if(isChecked){
			//System.out.println("* * * * * * * On Click generate sheet * * * *");
			 for(RunPayRollBean bean : selectedEmployeeList){
				 for(EmployeeSalaryComponentAmountBean comp : bean.getEarningCompList()){
					 //System.out.println(comp.toString());
				 }
			 }
			paySlipGenerator.getSheetDetails(pdfPath, selectedEmployeeList, pdfSheetBean);
		}else{
			Messagebox.show("Please check at least one!","Alert Information",Messagebox.OK,Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange("*")
	public void onCheckAll(){
		
		if(pdfBeanList.size()>0){
			if(allChecked){
				for(RunPayRollBean bean : pdfBeanList){
					bean.setChecked(true);	
				}
			}else{
				for(RunPayRollBean bean : pdfBeanList){
					bean.setChecked(false);	
				}
			}
			
		}
		
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickNext() throws DocumentException, Exception{
		salaryAdjustmentVisibility = false;
		salaryComponentVisibility = true;
		nextButtonVisibility = false;
		prevButtonVisibility = true;
		calculateButtonVisibility = false;
		upperComponentVisibility = false;
		//System.out.println("* * * * * * * On Click Next sheet earning and deduction components are::* * * *");
		 for(RunPayRollBean bean : pdfBeanList){
			 for(EmployeeSalaryComponentAmountBean comp : bean.getEarningCompList()){
				 //System.out.println(comp.toString());
			 }
			 for(EmployeeSalaryComponentAmountBean comp : bean.getDeductionCompList()){
				 //System.out.println(comp.toString());
			 }
		 }
		 //System.out.println("* * * * * * * end of earning and deduction components * * * *");
		/*String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		pdfSheetBean.setComapnyName(companyMasterBean.getCompanyName());
		pdfSheetBean.setUnitName(unitMasterBean.getUnitName());
		pdfSheetBean.setCurrentDate(currentDate);
		pdfSheetBean.setMonthName(monthMasterBean.getMonthName());
		pdfSheetBean.setYear(String.valueOf(year));
		pdfSheetBean.setUnitDesignation(unitDesignationBean.getUnitDesignation());
		PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
	    paySlipGenerator.getSheetDetails(pdfPath, pdfBeanList, pdfSheetBean);*/
	}
	
	@Command
	@NotifyChange("*")
	public void onClickPrevious(){
		
		Messagebox.show("If you go to previous page all calculations are reset.Are you sure to go previous page?", "Confirm Dialog", 
				Messagebox.OK |  Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
		    public void onEvent(Event evt) throws InterruptedException {
		        if (evt.getName().equals("onOK")) {
		          BindUtils.postGlobalCommand(null, null, "globalReload", null);
		        } else if (evt.getName().equals("onIgnore")) {
		           // Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		        } else {
		           
		        }
		    }
		});
	}
	
	@GlobalCommand
	@NotifyChange("*")
	public void globalReload(){
		salaryAdjustmentVisibility = true;
		salaryComponentVisibility = false;
		nextButtonVisibility = false;
		prevButtonVisibility = false;
		calculateButtonVisibility = true;
		upperComponentVisibility = true;
		slNo = 0;
		for(RunPayRollBean runPayRollBean : runPayRollBeanList){
			runPayRollBean.setTotalSalary(0.0);
			runPayRollBean.setTotalDeduction(0.0);
			runPayRollBean.setNetSalary(0.0);
			runPayRollBean.setEmpcount(0);
			allChecked=false;
		}
		pdfBeanList.clear();
	}
	
	
	
	@Command
	@NotifyChange("*")
	public void onChangePresentDays(@BindingParam("bean") RunPayRollBean bean){
		Integer otDays = 0 ;
		int basedays = RunPayRollDao.getBaseDays(runPayRollBean.getSelectedMonthId(),
				runPayRollBean.getSelectedUnitId(), runPayRollBean.getSelectedCurrentYr(), runPayRollBean.getSunSelId());
		
		/*if(bean.getPresentDay() == null){
			bean.setPresentDay(0.0f);
		}*/
		if(bean.getPresentDay() != null){
		if(basedays < bean.getPresentDay() ){
			otDays = (int) (bean.getPresentDay() - basedays) ;
		}
		}
			bean.setOtHoursF(otDays.doubleValue());
	}
	
	
	@Command
	@NotifyChange("*")
	public void onChangeOt(@BindingParam("bean")RunPayRollBean bean){
		//Messagebox.show("Ot changed!");
	}
	
	@Command
	@NotifyChange("*")
	public void onClickCalculate(){
		
		int baseDays,empcount=1;boolean isOtSheet = false;
		baseDays = RunPayRollDao.getBaseDays(runPayRollBean.getSelectedMonthId(), 
				runPayRollBean.getSelectedUnitId(), runPayRollBean.getSelectedCurrentYr(), runPayRollBean.getSunSelId());
		System.out.println("on click PDF TYPE " + sheetbean.getSheetType());	
		
		if(companyMasterBean.getCompanyId()==48 || companyMasterBean.getCompanyId() == 39){
		
			//System.out.println("inside I T C "+ companyMasterBean.getCompanyId());
			
			for(RunPayRollBean bean : runPayRollBeanList){
				
				if(bean.getPresentDay()!=null ){
				
					bean.setSelectedUnitId(runPayRollBean.getSelectedUnitId());
					RunPayRollBean pdfBean = new RunPayRollBean();
					pdfBean.setEmpcount(empcount);
					bean.setBaseDays(baseDays);
					double grossTotal=0.0,deduction=0.0;
					ArrayList<EmployeeSalaryComponentAmountBean> earningList = new ArrayList<EmployeeSalaryComponentAmountBean>();
					ArrayList<EmployeeSalaryComponentAmountBean> deductionList = new ArrayList<EmployeeSalaryComponentAmountBean>();
					
					//if(bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN") || bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN SUPERVISOR") || bean.getEmpDesignation().equalsIgnoreCase("GUN MAN")){
						for(EmployeeSalaryComponentAmountBean salBean : bean.getComponentAmountBeanList()){
							//GRATUITY = ( (WAGES*0.0481)/26 ) * PRESENT DAYS ON THREE MONTHS  FOR ITC LIMITED E LEAVE
							
							if(salBean.getComponentTypeId()==1){
								if( ! salBean.getComponentName().equalsIgnoreCase("WAGES"))
								earningList.add(salBean);
								if( salBean.getComponentName().equalsIgnoreCase("WAGES")){
									
									if(runPayRollBean.getWagesTypeId() == 1){
										bean.setWages(salBean.getComponentAmount()*baseDays);
									}else {
										bean.setWages(salBean.getComponentAmount());
									}
								}
							}else{
								deductionList.add(salBean);
							}		
						}
						//System.out.println("Emp name:: "+bean.getEmpName()+" Emp wages:: "+bean.getWages());
						for(EmployeeSalaryComponentAmountBean earn: earningList){
							
							System.out.println("final test 1 ----------------------------------------------------------- >>> >> > " + bean.getWages() + " - " + bean.getEmpName()
							          +" - " + bean.getEmpId() + " - " + bean.getEmpCode() + earn.getComponentName() + " - " + earn.getComponentAmount());
					
							
							if(earn.getComponentName().equalsIgnoreCase("BASIC")){
								earn.setComponentAmount( DoubleFormattor.setDoubleFormat(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay())) );
								bean.setBasic(earn.getComponentAmount());
								
							}
							if(earn.getComponentName().equalsIgnoreCase("HRA")){
								if(bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN GUARD")||bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE SECURITY GUARD")
										|| bean.getEmpDesignation().equalsIgnoreCase("EX-MAN SUPERVISOR") 
										|| bean.getEmpDesignation().equalsIgnoreCase("GUN MAN") || bean.getEmpDesignation().equalsIgnoreCase("SECURITY SUPERVISOR")){
									//earn.setComponentAmount( DoubleFormattor.setDoubleFormat( bean.getWages()*0.15));
									System.out.println("IF PART HRA CALCULATION STRTS . . . .");
									System.out.println("get basicccccccccccccccccccccccccccccccccccccccccccccccccccc " + bean.getBasic());
									earn.setComponentAmount(DoubleFormattor.setDoubleFormat(bean.getBasic()*0.15));
									System.out.println("get amounttttttttttttttttttttttttttttttttttttttttttttttttttt " + earn.getComponentAmount());
									System.out.println("HRA CALCULATION ENDS . . . .");
								}else{
									System.out.println("ELSE PART HRA CALCULATION STRTS . . . .");
									earn.setComponentAmount( DoubleFormattor.setDoubleFormat(bean.getBasic()*0.05) );
									System.out.println("HRA CALCULATION ENDS . . . .");
								}
							}
							
							if(earn.getComponentName().equalsIgnoreCase("CONVEYANCE")){
								System.out.println("CONVEYANCE CALCULATION STARTS . . . .");
								earn.setComponentAmount( DoubleFormattor.setDoubleFormat(20*bean.getPresentDay()) );

								//System.out.println("CON ITC >> " +earn.getComponentAmount());
								
								

								//System.out.println("CON ITC >> " +earn.getComponentAmount());
								//System.out.println("CONVEYANCE CALCULATION ENDS . . . .");

							}
							
							if(companyMasterBean.getCompanyId()==48){
								if(earn.getComponentName().equalsIgnoreCase("WASHING")){

									//System.out.println("WA SH ING " + bean.getPresentDay());

									//System.out.println("WASHING calculation for ralegh court " + bean.getPresentDay());

									earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
									
									//System.out.println("---------washing amount ------------------ for raleigh court  >>> >> > " + earn.getComponentAmount());
									
								}
							} else{
								if(earn.getComponentName().equalsIgnoreCase("WASHING")){

									//System.out.println("WA SH ING " + bean.getPresentDay());

									System.out.println("WASHING others " + bean.getPresentDay());

									if(bean.getOtHoursF()!=null && bean.getOtHoursF()>0.0){
										earn.setComponentAmount( DoubleFormattor.setDoubleFormat(5*bean.getOtHoursF()) );
									}else{
										earn.setComponentAmount( DoubleFormattor.setDoubleFormat(5*bean.getPresentDay()) );
									}
									
									////System.out.println("---------washing amount ------------------0 >>> >> > " + earn.getComponentAmount());
									
								}
							}
							
							
							if(bean.getSpecialTime() != null){
								if(earn.getComponentName().equalsIgnoreCase("SPECIAL WORK ALLOWANCES")){
									
									earn.setComponentAmount(Rules.getSpecialWorkAllowance(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()), 
											bean.getBaseDays(), bean.getSpecialTime()));	
								}
							}
							//System.out.println("emp dest >>> >> > " + bean.getEmpDesignation());
							if(bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE MAN GUARD") 
													  || bean.getEmpDesignation().equalsIgnoreCase("EX-MAN SUPERVISOR") 
													  || bean.getEmpDesignation().equalsIgnoreCase("GUN MAN")
						                             // || bean.getEmpDesignation().equalsIgnoreCase("TOKEN KEEPER CUM DRIVER") 
						                             // || bean.getEmpDesignation().equalsIgnoreCase("FACTORY DRIVER") 
						                              || bean.getEmpDesignation().equalsIgnoreCase("SECURITY SUPERVISOR")
						                              || bean.getEmpDesignation().equalsIgnoreCase("COMPUTER OPERATOR") 
						                              || !bean.getEmpDesignation().equalsIgnoreCase("CIVILIAN GUARD")
						                              && !bean.getEmpDesignation().equalsIgnoreCase("CARE TAKER")
						                              && !bean.getEmpDesignation().equalsIgnoreCase("FACTORY DRIVER") 
						                              && !bean.getEmpDesignation().equalsIgnoreCase("TOKEN KEEPER CUM DRIVER")){
								if(earn.getComponentName().equalsIgnoreCase("ALLOWANCE")){
									
									//System.out.println("FOR RLG COURT 100>>> >> > " + earn.getComponentAmount());
									
									earn.setComponentAmount( DoubleFormattor.setDoubleFormat(50*bean.getPresentDay()) );
									//System.out.println("ALL in ITCC " + earn.getComponentName());
									//System.out.println("ALL in ITC " + earn.getComponentAmount() );
									
								}
							}
							
							
							if(bean.getEmpDesignation().equalsIgnoreCase("GUN MAN")){
								if(earn.getComponentName().equalsIgnoreCase("WEAPON ALLOWANCES")){
									earn.setComponentAmount( DoubleFormattor.setDoubleFormat(50*bean.getPresentDay()) );
								}
							}
							
							if(bean.getSelectedMonthId()==10){
								if(earn.getComponentName().equalsIgnoreCase("BONUS")){
									earn.setComponentAmount( DoubleFormattor.setDoubleFormat(bean.getWages()*0.0833) );
								}
							}

							if(bean.getEmpDesignation().equalsIgnoreCase("EX-SERVICE SECURITY GUARD") && earn.getComponentName().equalsIgnoreCase("EX-MAN ALL")){
								earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
							}
							
							if(companyMasterBean.getCompanyId()==48){
								if(bean.getEmpDesignation().equalsIgnoreCase("CARE TAKER") && earn.getComponentName().equalsIgnoreCase("ALLOWANCE")){
									
									//System.out.println("FOR RLG COURT 1>>> >> > " + bean.getEmpDesignation());
									//System.out.println("FOR RLG COURT 2>>> >> > " + earn.getComponentName());
									//System.out.println("FOR RLG COURT 3>>> >> > " + earn.getComponentAmount());
									
									earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
									//System.out.println("FOR RLG COURT 4>>> >> > " + earn.getComponentAmount());
								}
							}
							
							//GRATUITY = ( (WAGES*0.0481)/26 ) * PRESENT DAYS ON THREE MONTHS  FOR ITC LIMITED E LEAVE
							if(earn.getComponentName().equalsIgnoreCase("GRATUITY")){
								
								earn.setComponentAmount(DoubleFormattor.setDoubleFormat(((bean.getWages()*0.0481)/26)*bean.getPresentDay()));
								
							}
							if(earn.getComponentName().equalsIgnoreCase("E LEAVE")){
								earn.setComponentAmount(DoubleFormattor.setDoubleFormat(((bean.getWages()*0.0481)/bean.getBaseDays())*bean.getPresentDay()));
								
							}
							
							/*if(earn.getComponentName().equalsIgnoreCase("EX-MAN ALLOWANCES")){
								earn.setComponentAmount((earn.getComponentAmount()*bean.getPresentDay())/bean.getBaseDays());
								}*/

							/*if(earn.getComponentName().equalsIgnoreCase("EX-MAN ALLOWANCES")){
								earn.setComponentAmount( DoubleFormattor.setDoubleFormat((earn.getComponentAmount()*bean.getPresentDay())/bean.getBaseDays()));

							}*/
							if(!earn.getComponentName().equalsIgnoreCase("BASIC") && !earn.getComponentName().equalsIgnoreCase("HRA") 
									&& !earn.getComponentName().equalsIgnoreCase("CONVEYANCE")
									&& !earn.getComponentName().equalsIgnoreCase("WASHING") && !earn.getComponentName().equalsIgnoreCase("ALLOWANCE")
									&& !earn.getComponentName().equalsIgnoreCase("WEAPON ALLOWANCES") 
									&& !earn.getComponentName().equalsIgnoreCase("BONUS") 
									&& !earn.getComponentName().equalsIgnoreCase("GRATUITY")
									&& !earn.getComponentName().equalsIgnoreCase("E LEAVE")){
								System.out.println("- - - -INSIDE ALL NOT - - - ");
								earn.setComponentAmount( DoubleFormattor.setDoubleFormat((earn.getComponentAmount()/bean.getBaseDays())*bean.getPresentDay()) );
							}
							//ITC FACtory driver
							if( bean.getEmpDesignation().equalsIgnoreCase("FACTORY DRIVER") && earn.getComponentName().equalsIgnoreCase("ALLOWANCE")){
								double factAllow = earn.getComponentAmount();
								System.out.println("FACTORY DRIVER CALCULATING ALLOWANCE :: "+factAllow);
								earn.setComponentAmount( Rules.getAllowanceForFActoryDriver(factAllow, baseDays, bean.getOtHoursF()) );
							}
							//ITC TOKEN KEEPER CUM DRIVER
							if( bean.getEmpDesignation().equalsIgnoreCase("TOKEN KEEPER CUM DRIVER") && earn.getComponentName().equalsIgnoreCase("ALLOWANCE")){
								double factAllow = earn.getComponentAmount();
								System.out.println("TOKEN KEEPER CUM DRIVER CALCULATING ALLOWANCE :: "+factAllow);
								earn.setComponentAmount( Rules.getAllowanceForFActoryDriver(factAllow, baseDays, bean.getOtHoursF()) );
							}
							grossTotal += earn.getComponentAmount();
						}
						
						if(bean.getHoliDayAmount()==null){
							bean.setHoliDayAmount(0.0);
						}
						
						if(bean.getHoliDayAmount()>0){
							bean.setBasic(bean.getBasic()+bean.getHoliDayAmount());
							grossTotal = grossTotal+bean.getHoliDayAmount();
							
							
						}
						
						if(bean.getOtHoursF()!=null){
							bean.otSalary = DoubleFormattor.setDoubleFormat( Rules.getOtSalary(bean.getWages(), bean.getBaseDays(), bean.getOtHoursF()) );
									
						}	
							grossTotal = grossTotal+bean.otSalary;
							
							for(EmployeeSalaryComponentAmountBean deduct: deductionList){

								if(deduct.getComponentName().equalsIgnoreCase("PF")){
									//deduct.setComponentAmount( DoubleFormattor.setDoubleFormat(Rules.getPf(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay())) ));
									deduct.setComponentAmount(Rules.getPf(bean.getBasic()));
								}
								if(deduct.getComponentName().equalsIgnoreCase("PROF TAX")){
									deduct.setComponentAmount( DoubleFormattor.setDoubleFormat(Rules.getPtAmount(grossTotal)) );
								}
								
								if(deduct.getComponentName().equalsIgnoreCase("ESI")){
									if((grossTotal/baseDays)>100 && grossTotal <= 15000.00){
										grossTotal = DoubleFormattor.setDoubleFormat(grossTotal);
										for(EmployeeSalaryComponentAmountBean escb: earningList){
											//if(escb.getComponentName().equalsIgnoreCase("WASHING")){
												if(escb.getComponentName().contains("WASHING")){
												
												deduct.setComponentAmount( DoubleFormattor.setDoubleFormatEsi(Rules.getEsi(grossTotal, escb.getComponentAmount()) ));
												break;
												
											}else{
												
												deduct.setComponentAmount( DoubleFormattor.setDoubleFormatEsi(Rules.getEsi(grossTotal, 0.0) ) );
											}
										}
									}
								}
								
								deduction += deduct.getComponentAmount();
							
						
							}
						bean.setTotalSalary( DoubleFormattor.setDoubleFormat(grossTotal));
						bean.setTotalDeduction( DoubleFormattor.setDoubleFormat(deduction) );
						bean.setNetSalary( DoubleFormattor.setDoubleFormat( (grossTotal-deduction)) );
						
						slNo = slNo+1;
						
						pdfBean.setEmpcount(slNo);
						pdfBean.setSheetType(sheetbean.getSheetType());
						pdfBean.setPresentDay(bean.getPresentDay());
						pdfBean.setWages(bean.getWages());
						pdfBean.setBasic(bean.getBasic()-bean.getHoliDayAmount());
						
						pdfBean.setComapnyName(bean.getComapnyName());
						pdfBean.setUnitName(bean.getUnitName());
						pdfBean.setUnitDesignation(bean.getUnitDesignation());
						pdfBean.setSelectedUnitId(bean.getSelectedUnitId());
						pdfBean.setMonthName(bean.getMonthName());
						pdfBean.setYear(bean.getYear());
						pdfBean.setCurrentDate(currentDate);
						pdfBean.setBaseDays(bean.getBaseDays());
						
						pdfBean.setPresentDay(bean.getPresentDay());
						pdfBean.setOtHoursF(bean.getOtHoursF());
						pdfBean.setOtSalary(bean.getOtSalary());
						
						pdfBean.setTotalSalary(bean.getTotalSalary() );
						pdfBean.setTotalDeduction(bean.getTotalDeduction());
						pdfBean.setNetSalary(bean.getNetSalary());
						pdfBean.setEmpName(bean.getEmpName());
						pdfBean.setEmpCode(bean.getEmpCode());
						pdfBean.setEmpDesignation(bean.getEmpDesignation());
						pdfBean.setEmpPf(bean.getEmpPf());
						pdfBean.setEmpEsi(bean.getEmpEsi());
						pdfBean.setEmpUan(bean.getEmpUan());
						pdfBean.setHoliDayAmount(DoubleFormattor.setDoubleFormat(bean.getHoliDayAmount()));
						
						pdfBean.setEarningCompList(earningList);
						pdfBean.setDeductionCompList(deductionList);
						pdfBeanList.add(pdfBean);
						empcount++;

				
				}
			}
		}else {
			//System.out.println("@ @ @ @ @ @ @  @ @ @ @ @ @ @ @  @ @ @ @ @ @ NOT ITC @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @  @ @");
			for(RunPayRollBean bean : runPayRollBeanList ){
				
				if(bean.getPresentDay()!=null ){
					bean.setSelectedUnitId(runPayRollBean.getSelectedUnitId());
					//System.out.println("Selected unit ID: "+bean.getSelectedUnitId());
					RunPayRollBean pdfBean = new RunPayRollBean();
					bean.setBaseDays(baseDays);
					
					double grossTotal=0.0,deduction=0.0;
					ArrayList<EmployeeSalaryComponentAmountBean> earningList = new ArrayList<EmployeeSalaryComponentAmountBean>();
					ArrayList<EmployeeSalaryComponentAmountBean> deductionList = new ArrayList<EmployeeSalaryComponentAmountBean>();
					for (EmployeeSalaryComponentAmountBean salBean : bean.getComponentAmountBeanList()) {
						
						/*System.out.println("final test 1 ----------------------------------------------------------- >>> >> > " + runPayRollBean.getEmpName()
								          +" - " + runPayRollBean.getEmpId() + " - " + runPayRollBean.getEmpCode() + salBean.getComponentName() + " - " + salBean.getComponentAmount());
						*/
						//Finding earning and deduction components
						if(salBean.getComponentTypeId()==1){
							if(salBean.getComponentName().equalsIgnoreCase("WAGES")){
								
								if(runPayRollBean.getWagesTypeId() == 1){
									bean.setWages(salBean.getComponentAmount()*baseDays);
									
								}else {
									
									bean.setWages(salBean.getComponentAmount());
								}
							}
							
							if( ! salBean.getComponentName().equalsIgnoreCase("WAGES"))//separating wages from earning components
							earningList.add(salBean);
						}else{
							deductionList.add(salBean);
						}
					}
					
					for (EmployeeSalaryComponentAmountBean salBean : bean.getEarningCompList()) {
						System.out.println("Earning Components after sepation: "+salBean.getComponentName()+" Amount : "+salBean.getComponentAmount());
					}
					for (EmployeeSalaryComponentAmountBean salBean : bean.getDeductionCompList()) {
						System.out.println("Deduction components after sepation: "+salBean.getComponentName()+" Amount : "+salBean.getComponentAmount());
					}
					
					for(EmployeeSalaryComponentAmountBean earn: earningList){
						
						if(earn.getComponentName().equalsIgnoreCase("BASIC")){
							//System.out.println("Basic calculation: ");
							earn.setComponentAmount(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()));
							bean.setBasic(earn.getComponentAmount());
						}
						if(earn.getComponentName().equalsIgnoreCase("HRA")){
							//System.out.println("HRA calculation: ");
							earn.setComponentAmount(Rules.getHra(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
						}
						if(earn.getComponentName().equalsIgnoreCase("WASH")){
							earn.getComponentAmount();
						}
						if(earn.getComponentName().equalsIgnoreCase("WASH") && (bean.getSelectedCompanyId()==49 || bean.getSelectedCompanyId()==58 || bean.getSelectedCompanyId()==57 || bean.getSelectedUnitId()==55)){//for ALPHA && HAHNIMANN
							
							double wash = earn.getComponentAmount();
							
							
							if(bean.getSelectedUnitId()==55){ // Wash calculating depends on presentDays for only new carbon barouni and for general sheet only
								
								earn.setComponentAmount(Rules.getOtSalary(wash, baseDays,  bean.getPresentDay()));
								
							}else {
								
							earn.setComponentAmount( Rules.getWashFORAlpha(wash, baseDays, bean.getPresentDay(), bean.getOtHoursF()) );
							}
							
						}
						/********************************************/
						
						if(bean.getOverTime() != null && earn.getComponentName().equalsIgnoreCase("ALLOWANCE")
								&&  (bean.getSelectedUnitId()!=38 && bean.getSelectedUnitId() != 40) 
								&& bean.getSelectedUnitId() !=51 && bean.getSelectedUnitId() !=55){//STC and ALPHA

							int overTime = bean.getOverTime().intValue();
							
							double initAllow = earn.getComponentAmount();
							
							double allowoverTime = Rules.getAllowances(initAllow, bean.getBaseDays(), overTime);
							
							earn.setComponentAmount(initAllow + allowoverTime);
							//System.out.println("11 ----------------------------0----------------------------------------- >>> >> > " + earn.getComponentAmount() );
						    //earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
							
							//earn.setComponentAmount(earn.getComponentAmount()+(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), overTime)));
							
							//System.out.println("With over time  Allowance NOT STC AND ALPHA --------------------------------->>> >> > " + earn.getComponentAmount());
							
							
							
						} // else{
						if(bean.getOverTime()==null && earn.getComponentName().equalsIgnoreCase("ALLOWANCE") 
								&&  (bean.getSelectedUnitId()!=38 && bean.getSelectedUnitId() != 40) && bean.getSelectedUnitId() !=51){
							
							bean.setOverTime(0.0);
							
							double initAllow = earn.getComponentAmount();
							
							int oT = bean.getOverTime().intValue();
							
							int ed = bean.getOtHoursF().intValue();
						
							Float presentDay = bean.getPresentDay() ;
							
							double allowoverTime = Rules.getAllowances(earn.getComponentAmount(),(int) (presentDay+ed), oT);
							
							earn.setComponentAmount(initAllow + allowoverTime);
							
							if(bean.getSelectedUnitId()==35 && oT ==0){ // when extra duty not given -- allwoance calculating based on present days and base days
								double allowance = Rules.getAllowances(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay());
								earn.setComponentAmount(allowance);
								
							}
							if(bean.getSelectedUnitId()==55){ // Allowance will be calculating on presents days only for general sheet for new carbon baroauni
								double allowance = Rules.getAllowances(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay());
								earn.setComponentAmount(allowance);
							}
							/*if(bean.getSelectedUnitId()==55){ // when extra duty given -- allowance calculating based on otFoursF or (Ed) for new carbon barouni
								//if(ed>0){
								double allowance = Rules.getAllowances(earn.getComponentAmount(), bean.getBaseDays(), ed);
								earn.setComponentAmount(allowance);
								//}
							}*/
							
						}
						
						
						/********************************************/
						
						if(bean.getSpecialTime() != null){
						    if(earn.getComponentName().equalsIgnoreCase("SPECIAL WORK ALLOWANCES")){
							  earn.setComponentAmount(Rules.getSpecialWorkAllowance(Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay()), 
							  bean.getBaseDays(), bean.getSpecialTime()));	
						     }
						}
						if (!earn.getComponentName().equalsIgnoreCase("BASIC") && !earn.getComponentName().equalsIgnoreCase("HRA") && 
								!earn.getComponentName().equalsIgnoreCase("WAGES") && !earn.getComponentName().equalsIgnoreCase("SPECIAL WORK ALLOWANCES")
								&& !earn.getComponentName().equalsIgnoreCase("LEAVE")) {
							
							
							
							//earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
							
							
							
							if(bean.getOverTime() == null && bean.getPresentDay()>0 ){
							//earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
								
							
							}
							if(bean.getPresentDay()==0 && bean.getOtHoursF()>0 && bean.getSelectedUnitId()!=38 && bean.getSelectedUnitId() != 40 && bean.getSelectedUnitId()!= 49 && bean.getSelectedUnitId()!= 55) {//General rule applicalbe
								
								earn.setComponentAmount(Rules.getGeneral(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
								
							}
							
							if(earn.getComponentName().equalsIgnoreCase("BONUS") && bean.getSelectedUnitId() == 55){
								double i  = (Rules.getBasic(bean.getWages(), bean.getBaseDays(), bean.getPresentDay())* 0.0833);
								earn.setComponentAmount(DoubleFormattor.setDoubleFormat(i));
								
								
							}
							
							
							if(bean.getSelectedUnitId()==47 && earn.getComponentName().equalsIgnoreCase("WASHING")){//NICCO WASHING CALCULATION
								double washingForNicco = earn.getComponentAmount();
								//System.out.println("- - -  - NICCO WASHING before calculation----"+washingForNicco);
								earn.setComponentAmount( Rules.getGeneral(washingForNicco, baseDays, bean.getPresentDay()) );
							}
							
							if(earn.getComponentName().equalsIgnoreCase("ALLOWANCE") && (bean.getSelectedUnitId()==51 || bean.getSelectedUnitId()==49 || bean.getSelectedUnitId()==55 )){//allwnce cal for hahnaimann and lily (present days + ot days)
								
								if(bean.getPresentDay() == null){
									bean.setPresentDay(0.0f);
								}if(bean.getOtHoursF() == null){
									bean.setOtHoursF(0.0);
								//}if(bean.getPresentDay() != null && bean.getOtHoursF() != null){
								}if(bean.getPresentDay() > 0 && bean.getOtHoursF() >0){
									
									if(bean.getSelectedUnitId()==55){
										earn.setComponentAmount(Rules.getAllowances(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
									}else {
										earn.setComponentAmount(Rules.getAllowances(earn.getComponentAmount(), bean.getBaseDays(), (float) (bean.getPresentDay()+bean.getOtHoursF())));
									}
									
									
								}if(bean.getPresentDay() >0 && bean.getOtHoursF() == 0){
									
									earn.setComponentAmount(Rules.getAllowances(earn.getComponentAmount(), bean.getBaseDays(), bean.getPresentDay()));
									
									
								}if(bean.getPresentDay() ==0 && bean.getOtHoursF() > 0){
									
									earn.setComponentAmount(Rules.getAllowancesOtOthers(earn.getComponentAmount(), bean.getBaseDays(), bean.getOtHoursF()));
									
									
								}
								
							}// }
							
							
						}
						
						
						grossTotal += earn.getComponentAmount();
					}
					if(bean.getHoliDayAmount()==null){
						bean.setHoliDayAmount(0.0);
					}
					if(bean.getHoliDayAmount()>0){
						bean.setBasic(bean.getBasic()+bean.getHoliDayAmount());
					  }
					
					if(bean.getOtHoursF()!=null){
						if(bean.getSelectedUnitId()== 53){//Farroe alloy OT with 30
							bean.otSalary = Rules.getOtSalary(bean.getWages(),30, bean.getOtHoursF());
						}else{
							bean.otSalary = Rules.getOtSalary(bean.getWages(), bean.getBaseDays(), bean.getOtHoursF());
						}
						
					  }
					grossTotal = grossTotal+bean.otSalary;
					
					
					if(bean.getOverTime() == null){
						bean.setOverTime(0.0);
					}
					
					if(bean.getOverTime() !=null){
						
						bean.setOverTimeSal(Rules.getOtSalary(bean.getWages(), bean.getBaseDays(), bean.getOverTime()));
						
					}
					grossTotal = grossTotal + bean.getOverTimeSal(); 
					
					for(EmployeeSalaryComponentAmountBean deduct: deductionList){
						if(deduct.getComponentName().equalsIgnoreCase("PF")){
							
							deduct.setComponentAmount(Rules.getPf(bean.getBasic()));
							
						}
						if(deduct.getComponentName().equalsIgnoreCase("PROF TAX")){
							deduct.setComponentAmount(Rules.getPtAmount(grossTotal));
						}
						
						if(deduct.getComponentName().equalsIgnoreCase("ESI")){
							if((bean.getWages()/30)>100 && grossTotal <= 15000.00){
								grossTotal = DoubleFormattor.setDoubleFormat(grossTotal);
								for(EmployeeSalaryComponentAmountBean escb: earningList){
									if(escb.getComponentName().equalsIgnoreCase("WASHING")){
										
										deduct.setComponentAmount( DoubleFormattor.setDoubleFormatEsi(Rules.getEsi(grossTotal, escb.getComponentAmount())) );
									}else{
										deduct.setComponentAmount( DoubleFormattor.setDoubleFormatEsi(Rules.getEsi(grossTotal, 0.0)) );
									}
								}
							}
						}
						
						deduction += deduct.getComponentAmount();
					}
					
					bean.setTotalSalary( DoubleFormattor.setDoubleFormat(grossTotal));
					bean.setTotalDeduction( DoubleFormattor.setDoubleFormat(deduction) );
					bean.setNetSalary( DoubleFormattor.setDoubleFormat( (grossTotal-deduction)) );
					
					//Setting new bean values for pdf generation
					
					slNo = slNo+1;
					
					pdfBean.setEmpcount(slNo);
					
					pdfBean.setSheetType(sheetbean.getSheetType());
					System.out.println("PDF TYPE " + sheetbean.getSheetType());
					pdfBean.setPresentDay(bean.getPresentDay());
					pdfBean.setWages(bean.getWages());
					pdfBean.setBasic(bean.getBasic());
					
					pdfBean.setComapnyName(bean.getComapnyName());
					pdfBean.setSelectedCompanyId(bean.getSelectedCompanyId());
					pdfBean.setSelectedUnitDesignationId(bean.getSelectedUnitDesignationId());
					pdfBean.setUnitName(bean.getUnitName());
					pdfBean.setUnitDesignation(bean.getUnitDesignation());
					pdfBean.setSelectedUnitId(bean.getSelectedUnitId());
					pdfBean.setMonthName(bean.getMonthName());
					pdfBean.setYear(String.valueOf(year));
					pdfBean.setCurrentDate(currentDate);
					
					pdfBean.setOtHoursF(bean.getOtHoursF());
					pdfBean.setOtSalary(bean.getOtSalary());
					pdfBean.setBaseDays(bean.getBaseDays());
					
					pdfBean.setTotalSalary(bean.getTotalSalary() );
					pdfBean.setTotalDeduction(bean.getTotalDeduction());
					pdfBean.setNetSalary(bean.getNetSalary());
					pdfBean.setUserName(userName);
					pdfBean.setEmpId(bean.getEmpId());
					pdfBean.setEmpName(bean.getEmpName());
					pdfBean.setEmpCode(bean.getEmpCode());
					pdfBean.setEmpDesignation(bean.getEmpDesignation());
					pdfBean.setEmpDesignationId(bean.getEmpDesignationId());
					pdfBean.setEmpPf(bean.getEmpPf());
					pdfBean.setEmpEsi(bean.getEmpEsi());
					pdfBean.setEmpUan(bean.getEmpUan());
					pdfBean.setHoliDayAmount(DoubleFormattor.setDoubleFormat(bean.getHoliDayAmount()));
					pdfBean.setOverTime(bean.getOverTime());
					
					pdfBean.setOverTimeSal(DoubleFormattor.setDoubleFormat(bean.getOverTimeSal()));
					
					pdfBean.setEarningCompList(earningList);
					pdfBean.setDeductionCompList(deductionList);
					pdfBeanList.add(pdfBean);
					
				}
			
		}
	}
		if(pdfBeanList.size() > 0 ){
			nextButtonVisibility = true;
			calculateButtonVisibility = false;
		}
		
		
		/**
		 * @author somnathdutta
		 * OT sheet population calculation
		 */
		/*ArrayList<RunPayRollBean> otSheetList = new ArrayList<RunPayRollBean>();
		if(companyMasterBean.getCompanyId()==39){
			for(RunPayRollBean calculateBean : pdfBeanList){
				if(calculateBean.getPresentDay() == 0 
					 && (calculateBean.getOtHoursF() != null)){
					double otSheetTotSal =0.0,eamt=0.0,hra=0.0,allowance=0.0,otSheetTotDed=0.0,otSheetNetSal=0.0;
					 for(EmployeeSalaryComponentAmountBean earn: calculateBean.getEarningCompList()){
						 if(earn.getComponentName().equalsIgnoreCase("HRA")){
							 hra = Rules.getHraForOt(calculateBean.getOtSalary());
							 earn.setComponentAmount(hra);
						 }
						 if(earn.getComponentName().equalsIgnoreCase("ALLOWANCE")){
							 allowance = Rules.getAllowanceForOt(calculateBean.getOtHoursF());
							 earn.setComponentAmount(allowance);
						 }
						eamt +=  earn.getComponentAmount();	 
					 }
					 otSheetTotSal = eamt + calculateBean.getOtSalary();
					 otSheetTotDed = DoubleFormattor.setDoubleFormatEsi( Rules.getEsi(otSheetTotSal, 0.0) ) ;
					 for(EmployeeSalaryComponentAmountBean ded : calculateBean.getDeductionCompList()){
						if(ded.getComponentName().equalsIgnoreCase("ESI")){
							 ded.setComponentAmount(otSheetTotDed);
						 }
					 }
					 otSheetNetSal = otSheetTotSal - otSheetTotDed ;
					 calculateBean.setTotalSalary(otSheetTotSal);
					 calculateBean.setTotalDeduction(otSheetTotDed);
					 calculateBean.setNetSalary(otSheetNetSal);
					 otSheetList.add(calculateBean);
				 }
				}
			}
		
		if(otSheetList.size() > 0){
			isOtSheet = true;

			//System.out.println("Ot sheet size: "+otSheetList.size());
		}

			System.out.println("Ot sheet size: "+otSheetList.size());
		}*/

		/* * * * * Otsheet calculation ends here* * * * * * * * * * * * */
		
	}
	
	
	/**************************** initial functions  **********************************************************************************/
	@Command
	@NotifyChange("*")
	public void onClcikOtandLeaveSave(@BindingParam("bean") RunPayRollBean bean){
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
			
			////System.out.println(" >>> >> >" + bean.otSalary);
			
			NumberFormat formatter = new DecimalFormat("#0.00");  
			double d = Double.parseDouble(formatter.format(bean.otSalary));
			//System.out.println(" >>> >> >" + bean.otSalary);
			
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
			
			//System.out.println("Leave deduction >>> >> > " + bean.leaveDeduction);
			
			NumberFormat formatter1 = new DecimalFormat("#0.00");  
			double d2 = Double.parseDouble(formatter1.format(bean.leaveDeduction));
			
			//String decimalformat2 = new DecimalFormat("#.##").format(bean.leaveDeduction);
			//double d2 = Double.parseDouble(decimalformat2);
			bean.leaveDeduction = d2;
			
			bean.setNetSalary(bean.getNetSalary()-bean.leaveDeduction);
			
			bean.setTotalNumberOfWorkingDaysEveryMonth(runPayRollBean.getTotalNumberOfWorkingDaysEveryMonth()-bean.getLeaveDaysF());
			
			//System.out.println("AFter leave day >>> >> > " + bean.getTotalNumberOfDayseveryMonth());	
		}	
	}
	
	
	
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
		////System.out.println("no OF SATDAY " + noOfSatDayAllocated);
		if(noOfSatDayAllocated ==5){
			//runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satdayDeduction(runPayRollBean, month, year));
			
			runPayRollBean.setSatDayCountPerMonth(RunPayRollService.satdayDeduction(runPayRollBean, monthMasterBean.getMonthId(), year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getSatDayCountPerMonth());
			////System.out.println("SadDay Count 5 aMonth >>> >> > " + runPayRollBean.getSatDayCountPerMonth());
			
		}if(noOfSatDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfSatDayAllocated);
			////System.out.println("SSSSSATDAY COUNT 4 >>> >> > " + runPayRollBean.getSatDayCountPerMonth());
		}
		return runPayRollBean.getTotalNumberOfHolidayseveryMonth();
	}
	
	public int monDayCheck(){
		int noOfMonDayAllocated;
		runPayRollBean.setMonDayCountPerMonth(RunPayRollService.monDayCount(companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), runPayRollBean));
		noOfMonDayAllocated = runPayRollBean.getMonDayCountPerMonth();
		////System.out.println("no OF MONDAY " + noOfMonDayAllocated);
		if(noOfMonDayAllocated == 5){
			//runPayRollBean.setMonDayCountPerMonth(RunPayRollService.monDayDeduction(runPayRollBean, month, year));
			
			runPayRollBean.setMonDayCountPerMonth(RunPayRollService.monDayDeduction(runPayRollBean, monthMasterBean.getMonthId(), year));
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(runPayRollBean.getMonDayCountPerMonth());
			////System.out.println("MonDay Count 5 aMonth >>> >> > " + runPayRollBean.getMonDayCountPerMonth());
		}if(noOfMonDayAllocated <= 4){
			runPayRollBean.setTotalNumberOfHolidayseveryMonth(noOfMonDayAllocated);
			////System.out.println("SSSSMONDAY COUNT 4 >>> >> > " + runPayRollBean.getMonDayCountPerMonth());
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
	
	@Command
	@NotifyChange("*")
	public void onclickSaveSalSheet(){
		
		/*pdfSheetBean.setComapnyName(companyMasterBean.getCompanyName());
		pdfSheetBean.setUnitName(unitMasterBean.getUnitName());
		pdfSheetBean.setCurrentDate(currentDate);
		pdfSheetBean.setMonthName(monthMasterBean.getMonthName());
		pdfSheetBean.setYear(String.valueOf(year));*/
		pdfSheetBean.setUnitDesignation(unitDesignationBean.getUnitDesignation());
		
		ArrayList<RunPayRollBean> selectedEmployeeList = new ArrayList<RunPayRollBean>();
	    boolean isChecked =  false;
	    for(RunPayRollBean payRollBean : pdfBeanList){
	    	if(payRollBean.isChecked()){
	    		selectedEmployeeList.add(payRollBean);
	    		isChecked = true;
	    	}
	    }
		if(isChecked){
			//System.out.println("Clik generate sheet Prest day : "+pdfSheetBean.getPresentDay()+" bean.getBasic():"+pdfSheetBean.getBasic()+" bean.getOtSalary(): "+pdfSheetBean.getOtSalary()+" bean.getOtHoursF()::"+pdfSheetBean.getOtHoursF());
			//paySlipGenerator.getSheetDetails(selectedEmployeeList, pdfSheetBean);
			RunPayRollService.saveEmpSalStore(selectedEmployeeList, pdfSheetBean, companyMasterBean.getCompanyId(), unitMasterBean.getUnitId(), unitDesignationBean.getUnitDesignationId(), monthMasterBean.getMonthName(), String.valueOf(year));
		}else{
			Messagebox.show("Please check at least one!","Alert Information",Messagebox.OK,Messagebox.EXCLAMATION);
		}
		

	
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectYr2(){
		
		RunPayRollDao.loadMonthList(monthList2);
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectMonth2(){
		exPayrollBn.setMonthID2(monthBean2.getMonthId());
		exPayrollBn.setSalMonth2(monthBean2.getMonthName());
		exRunPayRollBean.setMonthName(monthBean2.getMonthName());
		
		EmployeeMasterService.loadCompanyBeanList(companyBeanList2);
		
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectCompany2(){
		exPayrollBn.setCompanyId2(companyMasterBean2.getCompanyId());
		exRunPayRollBean.setSelectedCompanyId(companyMasterBean.getCompanyId());
		unitMasterBeanList2 = EmployeeMasterService.loadUnitBeanListWrtCompany(exPayrollBn.getCompanyId2());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnit2(){
		exPayrollBn.setUnitId2(unitMasterBean2.getUnitId());
		exRunPayRollBean.setSelectedUnitId(unitMasterBean2.getUnitId());
		unitDesignationList2 = EmployeeDao.loadUnitDesignationList(exPayrollBn.getCompanyId2(), exPayrollBn.getUnitId2());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onSelectUnitDesignation2(){
		exPayrollBn.setUnitDesignationId2(unitDesignationBean2.getUnitDesignationId());
		exRunPayRollBean.setSelectedUnitDesignationId(unitDesignationBean2.getUnitDesignationId());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickLoad2(){
		
		//payrollExistBeanList2 = RunPayRollService.loadEmpSalStore(exPayrollBn);
		payrollExistBeanList = RunPayRollService.loadEmpSalStore(exPayrollBn);
		
	}
	
	@Command
	@NotifyChange("*")
	public void onClickDetails2(@BindingParam("bean") RunPayRollBean exBean){
		
		/*for(EmployeeSalaryComponentAmountBean bb : payExBean.getComponentAmountBeanList()){
			System.out.println("bb - " + bb.getComponentName() +" - "+ bb.getComponentTypeId());
			System.out.println("aa - " + payExBean.getNetSalary2());
		}*/
		
		Map<String, Object> parenMap = new HashMap<String, Object>();
		parenMap.put("parentList", exBean);
		parenMap.put("companyName", companyMasterBean2.getCompanyName());
		parenMap.put("unitName", unitMasterBean2.getUnitName());
		parenMap.put("salaryMonth", monthBean2.getMonthName());
		parenMap.put("Year", exPayrollBn.getLvYr2());
		
		
		Window window = (Window) Executions.createComponents("/WEB-INF/view/existingPaymentDetails.zul", null, parenMap);
		window.doModal();
		
		
	}
	
	@Command
	@NotifyChange("*")
	public void genExSalSheet() throws DocumentException, Exception{

		String pdfPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
		
		pdfSheetBean.setSheetType(sheetbean.getSheetType());
		PdfPaySlipGenerator paySlipGenerator = new PdfPaySlipGenerator();
		
			if(payrollExistBeanList2.size()>0){
	    	paySlipGenerator.getExSheetDetails(pdfPath, payrollExistBeanList2, exPayrollBn);
			}else {
				Messagebox.show("No Sheet ","Alert",Messagebox.OK, Messagebox.EXCLAMATION);
			}
	
	}
	
	@Command
	@NotifyChange("*")
	public void onClickRemove(){
		if(exPayrollBn.getCompanyId2()>0 && exPayrollBn.getUnitId2()>0 && exPayrollBn.getUnitDesignationId2()>0 && exPayrollBn.getSalMonth2() != null && exPayrollBn.getLvYr2() !=null){
		int salStoreCount = RunPayRollService.removeSalStore(exPayrollBn.getCompanyId2(), exPayrollBn.getUnitId2(), exPayrollBn.getUnitDesignationId2(), exPayrollBn.getSalMonth2(), exPayrollBn.getLvYr2());
		int salStrDetlCount = RunPayRollService.removeSalStrDetails(exPayrollBn.getCompanyId2(), exPayrollBn.getUnitId2(), exPayrollBn.getUnitDesignationId2(), exPayrollBn.getSalMonth2(), exPayrollBn.getLvYr2());
		
		if(salStoreCount>0 && salStrDetlCount>0 ){
			Messagebox.show("Remove Successfully ", "Information", Messagebox.OK, Messagebox.INFORMATION);
		}
		}
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




	public ArrayList<RunPayRollBean> getPdfBeanList() {
		return pdfBeanList;
	}




	public void setPdfBeanList(ArrayList<RunPayRollBean> pdfBeanList) {
		this.pdfBeanList = pdfBeanList;
	}




	public RunPayRollBean getPdfSheetBean() {
		return pdfSheetBean;
	}




	public void setPdfSheetBean(RunPayRollBean pdfSheetBean) {
		this.pdfSheetBean = pdfSheetBean;
	}




	public int getSlNo() {
		return slNo;
	}




	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}




	public boolean isUpperComponentVisibility() {
		return upperComponentVisibility;
	}




	public void setUpperComponentVisibility(boolean upperComponentVisibility) {
		this.upperComponentVisibility = upperComponentVisibility;
	}

	public ArrayList<SheetBean> getSheetTypeBeanList() {
		return sheetTypeBeanList;
	}

	public void setSheetTypeBeanList(ArrayList<SheetBean> sheetTypeBeanList) {
		this.sheetTypeBeanList = sheetTypeBeanList;
	}

	public SheetBean getSheetbean() {
		return sheetbean;
	}

	public void setSheetbean(SheetBean sheetbean) {
		this.sheetbean = sheetbean;
	}

	public CompanyMasterBean getCompanyMasterBean2() {
		return companyMasterBean2;
	}

	public void setCompanyMasterBean2(CompanyMasterBean companyMasterBean2) {
		this.companyMasterBean2 = companyMasterBean2;
	}

	public UnitMasterBean getUnitMasterBean2() {
		return unitMasterBean2;
	}

	public void setUnitMasterBean2(UnitMasterBean unitMasterBean2) {
		this.unitMasterBean2 = unitMasterBean2;
	}

	public MonthMasterBean getMonthBean2() {
		return monthBean2;
	}

	public void setMonthBean2(MonthMasterBean monthBean2) {
		this.monthBean2 = monthBean2;
	}

	public UnitDesignationBean getUnitDesignationBean2() {
		return unitDesignationBean2;
	}

	public void setUnitDesignationBean2(UnitDesignationBean unitDesignationBean2) {
		this.unitDesignationBean2 = unitDesignationBean2;
	}

	public ArrayList<String> getLvYrList2() {
		return lvYrList2;
	}

	public void setLvYrList2(ArrayList<String> lvYrList2) {
		this.lvYrList2 = lvYrList2;
	}

	public ArrayList<MonthMasterBean> getMonthList2() {
		return monthList2;
	}

	public void setMonthList2(ArrayList<MonthMasterBean> monthList2) {
		this.monthList2 = monthList2;
	}

	public ArrayList<CompanyMasterBean> getCompanyBeanList2() {
		return companyBeanList2;
	}

	public void setCompanyBeanList2(ArrayList<CompanyMasterBean> companyBeanList2) {
		this.companyBeanList2 = companyBeanList2;
	}

	public ArrayList<UnitMasterBean> getUnitMasterBeanList2() {
		return unitMasterBeanList2;
	}

	public void setUnitMasterBeanList2(ArrayList<UnitMasterBean> unitMasterBeanList2) {
		this.unitMasterBeanList2 = unitMasterBeanList2;
	}

	public ArrayList<UnitDesignationBean> getUnitDesignationList2() {
		return unitDesignationList2;
	}

	public void setUnitDesignationList2(
			ArrayList<UnitDesignationBean> unitDesignationList2) {
		this.unitDesignationList2 = unitDesignationList2;
	}

	public PayrollExistBean getExPayrollBn() {
		return exPayrollBn;
	}

	public void setExPayrollBn(PayrollExistBean exPayrollBn) {
		this.exPayrollBn = exPayrollBn;
	}

	public ArrayList<PayrollExistBean> getPayrollExistBeanList2() {
		return payrollExistBeanList2;
	}

	public void setPayrollExistBeanList2(
			ArrayList<PayrollExistBean> payrollExistBeanList2) {
		this.payrollExistBeanList2 = payrollExistBeanList2;
	}

	public ArrayList<RunPayRollBean> getPayrollExistBeanList() {
		return payrollExistBeanList;
	}

	public void setPayrollExistBeanList(
			ArrayList<RunPayRollBean> payrollExistBeanList) {
		this.payrollExistBeanList = payrollExistBeanList;
	}

	public RunPayRollBean getExRunPayRollBean() {
		return exRunPayRollBean;
	}

	public void setExRunPayRollBean(RunPayRollBean exRunPayRollBean) {
		this.exRunPayRollBean = exRunPayRollBean;
	}

	public ArrayList<String> getYearList() {
		return yearList;
	}

	public void setYearList(ArrayList<String> yearList) {
		this.yearList = yearList;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	
	

	
	
}
