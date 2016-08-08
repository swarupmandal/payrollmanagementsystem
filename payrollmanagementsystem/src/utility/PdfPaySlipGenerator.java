package utility;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.log4j.Logger;
import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.pdfhandler.DownloadPdf;
import org.appsquad.pdfhandler.HeaderTable;
import org.appsquad.pdfhandler.Rotate;
import org.appsquad.pdfhandler.RoundRectangle;
import org.appsquad.research.DoubleFormattor;
import org.appsquad.rules.Rules;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfPaySlipGenerator {

		private String filePath;
		private Document document = null;
		private PdfWriter writer = null;
		private static String tab = "\t\t\t\t\t\t\t\t";
		private String companyName;
		private String unitname;
		private String pdfMonth;
		protected PdfNumber rotation = PdfPage.LANDSCAPE;
		Rotate event = new Rotate();
		
		private static Font catFont = new Font(Font.getFamily("TIMES NEW ROMAN"), 22, Font.BOLD);
		private static Font headfont = new Font(Font.getFamily("TIMES NEW ROMAN"), 13, Font.NORMAL);
		final static Logger logger=Logger.getLogger(PdfPaySlipGenerator.class);
		
		//RunPayRollBean runPayRollBean = new RunPayRollBean();
		//ArrayList<RunPayRollBean> runPayRollBeanList = new ArrayList<RunPayRollBean>();
		
		/*public void getDetails(String data,String path, ArrayList<RunPayRollBean> runPayRollBeanList,
				RunPayRollBean bean, String company, String unit) throws Exception, DocumentException{
			filePath = path+"pay.pdf";
			System.out.println("My file path :: "+filePath);
			unitname = unit;
			companyName = company;
			System.out.println("COMPA " + companyName);
			document = new Document(PageSize.A4, 2, 2, 60, 40);
			document.setMargins(-40, -60, 2, 2);
			
			Rectangle pagesize = new Rectangle(216f, 720f);
			
			document = new Document(PageSize.A4, 2, 2, 60, 40);
			
		    document = new Document(PageSize.A4.rotate());
			//document.setMargins(40,40, 60, 10);
			//document.setMargins(20, 20, 20, 20);
			document = new Document(PageSize.LETTER.rotate());
			//document = new Document(PageSize.LEGAL, 2, 2, 60, 40);	
			document.setMargins(10,4, 10, 5);
			document.setMarginMirroring(true);
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			//writer.setPageEvent(event);
			writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 559, 788));
			document.open();
			
			for(RunPayRollBean rollBean : runPayRollBeanList){
				rollBean.setComapnyName(company);
				rollBean.setUnitName(unit);
			}
			//createPdfHeader(runPayRollBeanList);
			//createPdfHeader(data);
			generatePaySlip(runPayRollBeanList, bean);
			openPdf(filePath);
			document.close();
		}*/
		
		public void getSlipDetails(String path, ArrayList<RunPayRollBean> runPayRollBeanList,
				RunPayRollBean bean) throws Exception, DocumentException{
			filePath = path+"paySlip.pdf";
			System.out.println("My file path :: "+filePath);
			
			document = new Document(PageSize.LEGAL.rotate(),30f,5f,25f,5f);
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			generatePaySlip(runPayRollBeanList, bean);
		//	openPdf(filePath);
			DownloadPdf.download(filePath, "paySlip.pdf");
			document.close();
			/*document = new Document(PageSize.A4, 2, 2, 60, 40);
			document.setMargins(-40, -60, 2, 2);
			
			Rectangle pagesize = new Rectangle(216f, 720f);
			
			document = new Document(PageSize.A4, 2, 2, 60, 40);
			
		    document = new Document(PageSize.A4.rotate());
			//document.setMargins(40,40, 60, 10);
			//document.setMargins(20, 20, 20, 20);*/
			
			//document = new Document(PageSize.LEGAL, 2, 2, 60, 40);	
			
			//writer.setPageEvent(event);
			
			//createPdfHeader(runPayRollBeanList);
			//createPdfHeader(data);
		}
		
		
		public void generatePaySlip(ArrayList<RunPayRollBean> runPayRollBeanList
				,RunPayRollBean bean){
	        try {
	        	PdfPTable table =null ;
	        	ArrayList<RunPayRollBean> selectedBeanList = new ArrayList<RunPayRollBean>();
	        	for(RunPayRollBean payRollBean:runPayRollBeanList){
	        		if(payRollBean.isChecked()){
	        			selectedBeanList.add(payRollBean);
	        		}
	        	}
	        	if(selectedBeanList.size() > 4){
	        		//System.out.println("LIST SIZE>4 "+selectedBeanList.size());
	        		table = new PdfPTable(9);
	        		float[] widths = {9, 0.2f ,9 ,0.2f ,9 ,0.2f ,9 ,0.2f , 9};
	        		table.setWidths(widths);
	        		table.setWidthPercentage(100);
	        		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        	}
	        	if(selectedBeanList.size() == 1){
	        	//	System.out.println("LIST SIZE = 1 "+selectedBeanList.size());
	        		table = new PdfPTable(2);
	        		float[] widths = {9, 37f};
	        		table.setWidths(widths);
	        		table.setWidthPercentage(100);
	        		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        	}
	        	if(selectedBeanList.size() == 2 ){
	        		//System.out.println("LIST SIZE = 2 "+selectedBeanList.size());
	        		table = new PdfPTable(4);
	        		float[] widths = {9, 0.2f, 9, 28f};
	        		table.setWidths(widths);
	        		table.setWidthPercentage(100);
	        		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        	}
	        	if(selectedBeanList.size() == 3 ){
	        		//System.out.println("LIST SIZE = 3 "+selectedBeanList.size());
	        		table = new PdfPTable(6);
	        		float[] widths = {9, 0.2f , 9 , 0.2f, 9, 18f};
	        		table.setWidths(widths);
	        		table.setWidthPercentage(100);
	        		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        	}
	        	if(selectedBeanList.size() == 4 ){
	        		//System.out.println("LIST SIZE = 4 "+selectedBeanList.size());
	        		table = new PdfPTable(8);
	        		float[] widths = {9, 0.2f , 9 , 0.2f, 9, 0.2f, 9, 10f};
	        		table.setWidths(widths);
	        		table.setWidthPercentage(100);
	        		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        	}
	        	/*else{
	        		float[] widths = new float[runPayRollBeanList.size() + (runPayRollBeanList.size()-1)];
	        		for(int i=0 ; i<widths.length ; i++){
	        			if(i%2 == 0){
	        				widths[i] = 9;
	        			}else{
	        				widths[i] = 0.2f;
	        			}
	        		}
	        		table = new PdfPTable(runPayRollBeanList.size()+(runPayRollBeanList.size()-1));
	        		table.setWidths(widths);
	        		table.setWidthPercentage(100);
	        		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        	}*/
	        	
	        //	table.addCell( createLabelCell() );
 	          //  table.addCell( createLabelCell() );
 	           // table.addCell( createLabelCell());
 	           // table.addCell( createLabelCell() );
	           // table.addCell( createLabelCell());
 	         //  for(int i=0;i<3;i++){
	        	
	        	PdfPTable mainTable = new PdfPTable(9);
	        	float[] widths = {9, 0.2f ,9 ,0.2f ,9 ,0.2f ,9 ,0.2f , 9};
	        	mainTable.setWidths(widths);
	        	mainTable.setWidthPercentage(100);
	        	//mainTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        	
	        	int i =1,size = selectedBeanList.size();
	        	System.out.println("Before loop i: "+i);
 	        	  for(RunPayRollBean payRollBean : selectedBeanList){
 	        		  	//logger.info("At start of loop Counter->"+i+" size->"+size);
 	        		    PdfPTable innertable = new PdfPTable(1);
     				 	innertable.setWidthPercentage(20f);//added
     				 	innertable.setHorizontalAlignment(Element.ALIGN_LEFT);//added
     				 	
     				    innertable.addCell(createTableForBDA(document, bean));	
         				innertable.addCell(createTableForUnit(document, bean));	
         				innertable.addCell(createTableForEmployee(document, payRollBean));	
         				innertable.addCell(createTableForSalary(document , payRollBean));
         				innertable.addCell(createTableForEarnings(document, payRollBean));	
         				innertable.addCell(createTableForDeductions(document, payRollBean));
         				innertable.addCell(createTableForNetSalary(document, payRollBean));
         				//mainTable.addCell(innertable);
 	        		 
 	        		    
        						//innertable.setWidthPercentage(100);
 	 	        				
 	 	        			//	table.setSpacingBefore(35f);
 	 	        			//	table.setSpacingAfter(35f);
 	 		      	          if(i<6){
 	 		      	        	table.addCell(innertable);
 	 	        				table.addCell(new Phrase(""));
 	 		      	        	//document.add(table);
 	 		      	          }
 	 	        				
 	 		      	          
 	 	        		if(i>5){
 	 	        			//logger.info("---Create  new table -- - with column::"+size);
 	 	        			document.newPage();
 	 	        			table.addCell(innertable);
	 	        			table.addCell(new Phrase(""));
 	 	        			//document.add(table);
 	 	        		}
 	 	        		i++;size--;
 	 	        		logger.info("After adding to table counter::"+i+" remaing beans:: "+size);  
 	        	  }	
 	        	  document.add(table);
 	        	// document.add(mainTable);			//   }
 	        			   /*if(i>4)	{
 	        				  System.out.println(i+" *** bean Name----->(inside else) "+payRollBean.getEmpName());
 	        				  
 	        				    PdfPTable innertable = new PdfPTable(1);
	        				    innertable.addCell(createTableForBDA(document, bean));	
	 	        				innertable.addCell(createTableForUnit(document, bean));	
	 	        				innertable.addCell(createTableForEmployee(document, payRollBean));	
	 	        				innertable.addCell(createTableForSalary(document , payRollBean));
	 	        				innertable.addCell(createTableForEarnings(document, payRollBean));	
	 	        				innertable.addCell(createTableForDeductions(document, payRollBean));
	 	        				innertable.addCell(createTableForNetSalary(document, payRollBean));
	 	        				innertable.setWidthPercentage(100);
	 	        			   
	 	        				table.addCell(innertable);
	 	        				table.addCell(new Phrase(""));
	 		      	            document.add(table);
	 		      	            i++;
 	        			   } */
 	        		   //}
 	        	//  }  
 	        		 // table.addCell(new Phrase(""));
 	        		// table.setSpacingBefore(30f);
 	        		
 	        	   
 	        //	 document.add(table);
 	        	  /*table.setSpacingBefore(35f);*/
 	        //   }
 	           /*table.setWidthPercentage(100);*/
 	           //table.setTableEvent(new BorderEvent());
 	           /*table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);*/
 	          
 	         /* table.setSpacingAfter(40f);*/
 	          //  document.add(table);
	           
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        } finally {
	            document.close();
	        }
		}
		
		
		public void generateSheet(ArrayList<RunPayRollBean> runPayRollBeanList
				,RunPayRollBean bean) throws Exception{
		//	document.add(createTableForLogo(document, bean));
		//	System.out.println("Tot sal Gsheet ::"+bean.getTotalSalary()+" Tot net : "+bean.getNetSalary());
			double  hra = 0.0,allowance = 0.0,conveyance=0.0,totOt = 0.0, totBasic = 0.0, totSalTot = 0.0, 
					totExtraDuty=0.0,totOvertime=0.0,totProf =0.0,totPf=0.0,totEsi =0.0,totNetSal = 0.0,
					totDed = 0.0,totOvertimeSal=0.0,totOtSal=0.0; 
			int totPresnt = 0,earnSize = 0 ,dedSize = 0, beanCount=0;boolean otSheet = false;
			ArrayList<RunPayRollBean> otSheetList = new ArrayList<RunPayRollBean>();
			double otSheetTotDed =0.0,otSheetNetSal =0.0;
			for(RunPayRollBean payRollBean : runPayRollBeanList){
				double otSheetTotSal =0.0,eamt=0.0;
				if(payRollBean.getPresentDay() == 0 && payRollBean.getBasic() == 0.0 && 
						payRollBean.getOtSalary() > 0.0 && payRollBean.getOtHoursF() > 0.0){
					otSheet = true;
			
					 for(EmployeeSalaryComponentAmountBean earn: payRollBean.getEarningCompList()){
						 if(earn.getComponentName().equalsIgnoreCase("HRA")){
							 hra = Rules.getHraForOt(payRollBean.getOtSalary());
							 earn.setComponentAmount(hra);
						 }
						 if(earn.getComponentName().equalsIgnoreCase("ALLOWANCE")){
							 allowance = Rules.getAllowanceForOt(payRollBean.getOtHoursF());
							 earn.setComponentAmount(allowance);
						 }
						 if(earn.getComponentName().equalsIgnoreCase("CONVEYANCE")){
							 conveyance = Rules.getConveyenceForOt(payRollBean.getOtHoursF());
							 earn.setComponentAmount(conveyance);
						 }
						eamt +=  earn.getComponentAmount();	 
					 }
					 otSheetTotSal = eamt + payRollBean.getOtSalary();
					 otSheetTotDed = DoubleFormattor.setDoubleFormatEsi( Rules.getEsi(otSheetTotSal, 0.0) ) ;
					 for(EmployeeSalaryComponentAmountBean ded : payRollBean.getDeductionCompList()){
						if(ded.getComponentName().equalsIgnoreCase("ESI")){
							 ded.setComponentAmount(otSheetTotDed);
						 }
					 }
					 otSheetNetSal = otSheetTotSal - otSheetTotDed ;
					 payRollBean.setTotalSalary(otSheetTotSal);
					 payRollBean.setTotalDeduction(otSheetTotDed);
					 payRollBean.setNetSalary(otSheetNetSal);
					 beanCount++;
					 otSheetList.add(payRollBean);
				}
			}
			
		
			LinkedHashSet<String> ernList = new LinkedHashSet<String>();
			LinkedHashSet<String> dedctList = new LinkedHashSet<String>();
			Map<String, Double> earnMap = new HashMap<String, Double>();
			Map<String, Double> deductMap = new HashMap<String, Double>();
			
			if(!otSheet){
				for(RunPayRollBean rollBean : runPayRollBeanList){
					totPresnt += rollBean.getPresentDay();
					earnSize += rollBean.getEarningCompList().size();
					dedSize += rollBean.getDeductionCompList().size();
					totSalTot += rollBean.getTotalSalary();
					totNetSal += rollBean.getNetSalary();
					totDed += rollBean.getTotalDeduction();
					totExtraDuty += rollBean.getOtHoursF();
					totOt += rollBean.getOtHoursF();
					totOtSal += rollBean.getOtSalary();
					totOvertime += rollBean.getOverTime();
					totOvertimeSal+=rollBean.getOverTimeSal();
					String earnName = null;
					for(EmployeeSalaryComponentAmountBean earnBean : rollBean.getEarningCompList()){
						if(!earnBean.getComponentName().equalsIgnoreCase("BASIC")){
							earnName = earnBean.getComponentName();
							if(earnMap.containsKey(earnName)){
								double earnAmount = earnMap.get(earnName);
								earnMap.put(earnName, earnAmount + earnBean.getComponentAmount());
							}else{
								earnMap.put(earnName, earnBean.getComponentAmount());
							}
						}
						
					}
					String deductName = null;
					for(EmployeeSalaryComponentAmountBean deductBean : rollBean.getDeductionCompList()){
						deductName = deductBean.getComponentName();
							if(deductMap.containsKey(deductName)){
								double deductAmount = deductMap.get(deductName);
								deductMap.put(deductName, deductAmount + deductBean.getComponentAmount());
							}else{
								deductMap.put(deductName, deductBean.getComponentAmount());
							}
					}
					
				//	earnMap = AddDuplicate.findTotalAmount(rollBean.getEarningCompList());
				//	deductMap = AddDuplicate.findTotalAmount(rollBean.getDeductionCompList());
					for(EmployeeSalaryComponentAmountBean basic : rollBean.getEarningCompList()){
						if(!basic.getComponentName().equalsIgnoreCase("BASIC")){
							ernList.add(basic.getComponentName());
						}
					}
					for(EmployeeSalaryComponentAmountBean basic : rollBean.getDeductionCompList()){
						dedctList.add(basic.getComponentName());
						
					}
					
					for(EmployeeSalaryComponentAmountBean basic : rollBean.getEarningCompList()){
						if(basic.getComponentName().equalsIgnoreCase("BASIC")){
							totBasic += basic.getComponentAmount();
						}
					}
				}
			}else{
				///*********IF OTSHEET ************/
				for(RunPayRollBean rollBean : otSheetList){
					totPresnt += rollBean.getPresentDay();
					earnSize += rollBean.getEarningCompList().size();
					dedSize += rollBean.getDeductionCompList().size();
					totSalTot += rollBean.getTotalSalary();
					totNetSal += rollBean.getNetSalary();
					totDed += rollBean.getTotalDeduction();
					totOt += rollBean.getOtHoursF();
					totOtSal += rollBean.getOtSalary();
					totExtraDuty += rollBean.getOtHoursF();
					totOvertime += rollBean.getOverTime();
					totOvertimeSal+=rollBean.getOverTimeSal();
					String earnName = null;
					for(EmployeeSalaryComponentAmountBean earnBean : rollBean.getEarningCompList()){
						if(!earnBean.getComponentName().equalsIgnoreCase("BASIC")){
							earnName = earnBean.getComponentName();
							if(earnMap.containsKey(earnName)){
								double earnAmount = earnMap.get(earnName);
								earnMap.put(earnName, earnAmount + earnBean.getComponentAmount());
							}else{
								earnMap.put(earnName, earnBean.getComponentAmount());
							}
						}
					}
					
					
					String deductName = null;
					for(EmployeeSalaryComponentAmountBean deductBean : rollBean.getDeductionCompList()){
						deductName = deductBean.getComponentName();
							if(deductMap.containsKey(deductName)){
								double deductAmount = deductMap.get(deductName);
								deductMap.put(deductName, deductAmount + deductBean.getComponentAmount());
							}else{
								deductMap.put(deductName, deductBean.getComponentAmount());
							}
					}
					
				//	earnMap = AddDuplicate.findTotalAmount(rollBean.getEarningCompList());
				//	deductMap = AddDuplicate.findTotalAmount(rollBean.getDeductionCompList());
					for(EmployeeSalaryComponentAmountBean basic : rollBean.getEarningCompList()){
						if(!basic.getComponentName().equalsIgnoreCase("BASIC")){
							ernList.add(basic.getComponentName());
							
						}
					}
					for(EmployeeSalaryComponentAmountBean basic : rollBean.getDeductionCompList()){
						dedctList.add(basic.getComponentName());
						
					}
					
					for(EmployeeSalaryComponentAmountBean basic : rollBean.getEarningCompList()){
						if(basic.getComponentName().equalsIgnoreCase("BASIC")){
							totBasic += basic.getComponentAmount();
						}
					}
			
				}
			}
			
			//document.add(createTableForHeader(document, bean));
			System.out.println("Tot sal ::"+totSalTot+" Tot net : "+totNetSal);
			System.out.println("earn map :: "+earnMap);
			System.out.println("deduct map :: "+deductMap);
			System.out.println("totOvertime:: "+totOvertime);
			System.out.println("****************totOvertime *******************"+totOvertime);
			System.out.println("**************** totOt *******************"+totOt);
			
			float[] columnWidths = {60, 30, 50, 45, 500, 500};
			PdfPTable bottomTable = new PdfPTable(columnWidths);
			bottomTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell ;
			Font font ;
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			cell = new PdfPCell( new Phrase("PRESENT\n"+totPresnt,font) );
			//cell.setBorder(Rectangle.NO_BORDER);
			bottomTable.addCell(cell);
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			if(totOt > 0.0){
				cell = new PdfPCell( new Phrase("E.D.\n"+totExtraDuty,font) );
				bottomTable.addCell(cell);
			}else{
				cell = new PdfPCell( new Phrase("E.D.\n"+0.0,font) );
				bottomTable.addCell(cell);
			}
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			if(totOvertime > 0.0){
				System.out.println("otym>0");
				cell = new PdfPCell( new Phrase("Ex.Duty.\n"+totOvertime,font) );
				bottomTable.addCell(cell);
			}else{
				System.out.println("else otym>0");
				cell = new PdfPCell( new Phrase("Ex.Duty.\n"+0.0,font) );
				bottomTable.addCell(cell);
			}
			
			//cell.setBorder(Rectangle.NO_BORDER);
			//bottomTable.addCell(cell);
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			cell = new PdfPCell( new Phrase("BASIC\n"+totBasic,font) );
			//cell.setBorder(Rectangle.NO_BORDER);
			bottomTable.addCell(cell);
			
			//ernList.add("Tot Sal.");
			PdfPTable earnTable = null;
			if(totOvertimeSal > 0.0){
				earnTable = new PdfPTable(ernList.size()+2);
			}else{
				earnTable = new PdfPTable(ernList.size()+1);
			}
			//PdfPTable earnTable = new PdfPTable(ernList.size()+1);
			for(String e : ernList){
				System.out.println("For loop e : "+e);
				font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
				if(earnMap.containsKey(e)){
					cell = new PdfPCell( new Phrase(e+"\n"+String.valueOf( DoubleFormattor.setDoubleFormat(earnMap.get(e)) ),font) );
				}
				cell.setBorder(Rectangle.NO_BORDER);
				earnTable.addCell(cell);
			}
			
			if(totOvertimeSal>0.0){
				cell = new PdfPCell( new Phrase("Ex.Duty\n"+String.valueOf( DoubleFormattor.setDoubleFormat(totOvertimeSal) ),font));
				cell.setBorder(Rectangle.NO_BORDER);
				earnTable.addCell(cell);
				earnTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			}
			
			if(totOvertimeSal>0.0){
				cell = new PdfPCell( new Phrase("TOT.SALARY\n"+String.valueOf(totSalTot-totOtSal),font));
				cell.setBorder(Rectangle.NO_BORDER);
				earnTable.addCell(cell);
				earnTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			}else{
				cell = new PdfPCell( new Phrase("TOT.SALARY\n"+String.valueOf(totSalTot),font));
				cell.setBorder(Rectangle.NO_BORDER);
				earnTable.addCell(cell);
				earnTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			}
			
			
			dedctList.add("TOT.DED");
			//dedctList.add("TOT.NET SALARY");
			System.out.println("DEDUCTION LIST SIZE :: "+dedctList.size() );
			PdfPTable dedTable = new PdfPTable(dedctList.size()+1);
			//if(dedctList.size()>0){
				for(String d : dedctList){
					System.out.println("deduct  list : "+d);
					font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
					if(deductMap.containsKey(d)){
						cell = new PdfPCell( new Phrase(d+"\n"+ String.valueOf( deductMap.get(d)) ,font) );
						cell.setBorder(Rectangle.NO_BORDER);
						dedTable.addCell(cell);
					}
					if(d.equalsIgnoreCase("TOT.DED")){
						cell = new PdfPCell( new Phrase(d+"\n"+ String.valueOf(totDed) ,font) );
						cell.setBorder(Rectangle.NO_BORDER);
						dedTable.addCell(cell);
					}
					//cell.setBorder(Rectangle.NO_BORDER);
					//dedTable.addCell(cell);
				}
				
				if(totOvertimeSal>0.0){
					cell = new PdfPCell( new Phrase("TOT.NET SALARY\n"+String.valueOf(totNetSal-totOtSal),font));
					cell.setBorder(Rectangle.NO_BORDER);
					dedTable.addCell(cell);
					dedTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				}else{
					cell = new PdfPCell( new Phrase("TOT.NET SALARY\n"+String.valueOf(totNetSal),font));
					cell.setBorder(Rectangle.NO_BORDER);
					dedTable.addCell(cell);
					dedTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				}
				
			//}
			/*if(dedctList.size() == 1){
				font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
				cell = new PdfPCell( new Phrase("TOT.DED\n"+ String.valueOf( totDed) ,font) );
				dedTable.addCell(cell);
				
				font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
				cell = new PdfPCell( new Phrase("TOT.NET SALARY\n"+ String.valueOf( totNetSal) ,font) );
				dedTable.addCell(cell);
			}*/
			
			
			
			bottomTable.addCell(earnTable);
			bottomTable.addCell(dedTable);
			
			bottomTable.setWidthPercentage(100f);
			int count=0;
			
			/******  FOR OTSHEET *********/
			if(otSheet){
				System.out.println("* * * * * OT SHEET PRINTING CREATING TABLES * * * * * ");
				for(RunPayRollBean runPayRollBean : otSheetList){
					document.add(createTableForSheet(document, runPayRollBean));
				}
				document.add(bottomTable);
			}else{/**********************FOR NORMAL *****************/
				System.out.println("* * * * * NORMAL SHEET PRINTING CREATING TABLES * * * * * ");
				for(RunPayRollBean runPayRollBean : runPayRollBeanList){
					for(EmployeeSalaryComponentAmountBean earn : runPayRollBean.getEarningCompList()){
					System.out.println("Comp name: "+earn.getComponentName()+" Amnt: "+earn.getComponentAmount());
					}
					System.out.println("Tot sal:: "+runPayRollBean.getTotalSalary()+" Net sal:: "+runPayRollBean.getNetSalary());
					document.add(createTableForSheet(document, runPayRollBean));
				}
				document.add(bottomTable);
			}
			
			//document.add(bottomTable);
		}
		
		public static PdfPTable createTableForSheet(Document document, RunPayRollBean bean) throws Exception{
		//	PdfPTable table = new PdfPTable(4);
			float[] columnWidths = {80, 42,225, 75,70};
	        PdfPTable table = new PdfPTable(columnWidths);
	        table.setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.addCell(createLabelCell(""));
			table.addCell(createLabelCell(""));
			table.addCell(createLabelCell(""));
			table.addCell(createLabelCell(""));
			table.addCell(createLabelCell(""));
			
			table.addCell( createTableForEmployeeOnSheet(document, bean) );
			table.addCell(createTableWithSingleColumn(document, bean));
			table.addCell( createTableForEarningOnSheet(document, bean) );
			table.addCell( createTableForDeductionOnSheet(document, bean) );
			table.addCell( createTableForSignatureSheet(document, bean) );
			//table.setWidthPercentage(100);
			 table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			 table.setWidthPercentage(100);
		     table.setSpacingBefore(0f);
		     table.setSpacingAfter(0f);
			//table.setTableEvent(new BorderEvent());
			 table.setTotalWidth(100);
			 
			
			return table;
		}
		
		public static PdfPTable createTableForLogo(Document document, RunPayRollBean bean) throws DocumentException{
		
			System.out.println("Tot sal ::"+bean.getTotalSalary()+" Tot net : "+bean.getNetSalary());
			float[] columnWidths = {8,  16};
			PdfPTable table = new PdfPTable(columnWidths);
			/*table.setWidths(columnWidths);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			
			PdfPCell cell ;
			cell = new PdfPCell( new Phrase("BDA"));
			cell.setRowspan(3);
			table.addCell(cell);
			
			cell = new PdfPCell( new Phrase("SALARY SHEET"));
			cell.setRowspan(3);
			table.addCell(cell);
			
			cell = new PdfPCell( new Phrase("COMPANY"));
			table.addCell(cell);
			
			cell = new PdfPCell( new Phrase("UNIT"));
			table.addCell(cell);
			
			cell = new PdfPCell( new Phrase("DATE"));
			table.addCell(cell);
			
			cell = new PdfPCell( new Phrase("BLACK BOY DETECTIVE AGENCY PVT LTD."));
			table.addCell(cell);
			
			cell = new PdfPCell( new Phrase("UNIT DESIGNATION"));
			table.addCell(cell);*/
			PdfPTable nxtTable = new PdfPTable(2);
			nxtTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			nxtTable.setWidthPercentage(60);
			nxtTable.addCell(createLabelCellBold("SALARY SHEET"));
			nxtTable.addCell(createLabelCellRightFont(bean.getComapnyName()+"\n"+bean.getUnitName()+"\n"+
					bean.getMonthName()+"      "+bean.getYear()+"     "+bean.getCurrentDate()+"\n"+
					bean.getUnitDesignation()));
			
			PdfPTable logoTable = new PdfPTable(2);
			logoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			//logoTable.setWidthPercentage(40);
	        Font white = new Font();
	        Font font = new Font(Font.getFamily("HELVETICA"), 23, Font.BOLD);
	        white.setColor(BaseColor.WHITE); 
			PdfPCell cell = new PdfPCell(new Phrase(" BDA" , font));
	        cell.setBackgroundColor(BaseColor.WHITE);
	        cell.setBorderColor(BaseColor.GRAY);
	        cell.setBorderWidth(3f);
	        cell.setPaddingBottom(3f);
	        logoTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase());
	        cell.setBorder(Rectangle.NO_BORDER);
	        logoTable.addCell(cell);
	        
	        
	        logoTable.addCell(createValueCellBoldLeft("BLACKBOY DETECTIVE AGENCY PVT LTD."));
	        
	        
	        cell = new PdfPCell(new Phrase());
	        cell.setBorder(Rectangle.NO_BORDER);
	        logoTable.addCell(cell);
	       // logoTable.setTotalWidth(10);
	        
	        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        table.addCell(logoTable);
	        table.addCell(nxtTable);
	        table.setWidthPercentage(100);
	        /*PdfContentByte canvas = writer.getDirectContent();
	        table.writeSelectedRows(0, -1, document.right() - 90, document.top(), canvas);*/
	        return table;
		}
		
		public static PdfPTable createTableForHeader(Document document, RunPayRollBean bean )
				throws DocumentException {
		    String pattern = "dd/MM/yyyy";
			String dateInString =new SimpleDateFormat(pattern).format(new Date());
			PdfPTable table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(20);
			table.addCell(createLabelCellLeftBold("BLACKBOY DETECTIVE AGENCY PVT. LTD.\n"));
			/*table.addCell(createLabelCellBold("SALARY SHEET"));
			table.addCell(createLabelCellRightFont(bean.getComapnyName()+"\n"+bean.getUnitName()+"\n"+
								bean.getMonthName()+"      "+bean.getYear()+"     "+dateInString+"\n"+
								bean.getUnitDesignation()));*/
			table.getDefaultCell().setBorder(0);
			table.setTotalWidth(90);
			return table;
		}
		
		public static PdfPTable createTableForEmployeeOnSheet(Document document, RunPayRollBean bean )
				throws DocumentException {
			
			/*float[] columnWidths = {30, 15, 40};
			PdfPTable table2 = new PdfPTable(columnWidths);
			table2.setSpacingAfter(0f);
			table2.setSpacingBefore(0f);
		//	table2.addCell(createLabelCellLeftUnderLine(bean.getEmpName()));
		//	table2.addCell(createLabelCellRight(" "));
		//	table2.addCell(createLabelCellRight(" "));
			table2.addCell(createValueCellBold(bean.getEmpCode()));
			
			if (bean.getEmpUan() != null) {
				table2.addCell(createValueCell("ESI " + bean.getEmpEsi() + "\nPF "
						+ bean.getEmpPf() + "\nUAN " + bean.getEmpUan()));
				table2.addCell(createValueCell("ESI"));
				table2.addCell(createValueCell(bean.getEmpEsi()));
				table2.addCell(createValueCell(" "));
				table2.addCell(createValueCell("PF"));
				table2.addCell(createValueCell(bean.getEmpPf()));
				table2.addCell(createValueCell(" "));
				table2.addCell(createValueCell("UAN"));
				table2.addCell(createValueCell(bean.getEmpUan()));
			//	table2.addCell(createValueCellLeft(bean.getEmpDesignation()));
			//	table2.addCell(createLabelCell());
			} else {
				table2.addCell(createValueCell("ESI " + bean.getEmpEsi() + "\nPF "
						+ bean.getEmpPf() + "\nUAN " + 0));
				//table2.addCell(createValueCellLeft(bean.getEmpDesignation()));
				//table2.addCell(createLabelCell());
			}
			
			PdfPTable tabledesg = new PdfPTable(1);
			tabledesg.addCell( createLabelCellLeft(bean.getEmpDesignation()) );
			tabledesg.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			PdfPTable table = new PdfPTable(1);
			table.addCell(createLabelCellLeftUnderLine(bean.getEmpName()));
			table.getDefaultCell().setPaddingTop(0);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.addCell(table2);
			table.addCell(tabledesg);*/
			float[] columnWidths = {40, 30,70};
			PdfPTable table = new PdfPTable(columnWidths);
			PdfPCell cell ;
			Font font ;
			font = new Font(Font.getFamily("HELVETICA"), 10, Font.BOLD);
			cell = new PdfPCell( new Phrase(bean.getEmpName(),font) );
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(bean.getEmpCode(),font));
			cell.setRowspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			cell = new PdfPCell(new Phrase("PF",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell) ;
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			cell = new PdfPCell(new Phrase(bean.getEmpPf(),font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell) ;
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			cell = new PdfPCell(new Phrase("ESI",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell) ;
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			cell = new PdfPCell(new Phrase(bean.getEmpEsi(),font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell) ;
		
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			cell = new PdfPCell(new Phrase("UAN",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell) ;
			
			font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			cell = new PdfPCell(new Phrase(bean.getEmpUan(),font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell) ;
			
			font = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);
			cell =  new PdfPCell(new Phrase(bean.getEmpDesignation(),font));
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			return table;
		}
		
		
		public static PdfPTable createTableForEarningOnSheet(Document document, RunPayRollBean bean){				
			ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
			/*for(EmployeeSalaryComponentAmountBean empAllowance : bean.getComponentAmountBeanList()){	
				if(empAllowance.getComponentType().equalsIgnoreCase("EARNING")){
					earnList.add(empAllowance);
				}
			}*/
			
			earnList = bean.getEarningCompList();
			PdfPTable table;boolean overTime=false, otGiven = false,holiGiven =false,bothGiven =false,bothNotGiven = false;
			if(earnList.size()>0){
				
				if(bean.getOtSalary() > 0.0 && bean.getHoliDayAmount() == 0.0){
					otGiven = true;holiGiven =false;bothGiven =false;bothNotGiven = false;
				}
				if(bean.getOtSalary() == 0.0 && bean.getHoliDayAmount() > 0.0){
					holiGiven = true;otGiven =false;bothGiven =false;bothNotGiven = false;
				}
				if(bean.getOtSalary() > 0.0 && bean.getHoliDayAmount() > 0.0){
					bothGiven = true;holiGiven =false;otGiven =false;bothNotGiven = false;
				}
				if(bean.getOtSalary() == 0.0 && bean.getHoliDayAmount() == 0.0){
					bothNotGiven = true;holiGiven =false;otGiven =false;bothGiven = false;
				}
				if(bean.getOverTime()>0.0){
					overTime= true;
				}
				System.out.println("Only ot :: "+otGiven+" Only holi ::"+holiGiven+" Both ::"+bothGiven+" Both not ::"+bothNotGiven);
				if(bean.otSalary>0.0){
					if(bean.getHoliDayAmount() > 0.0){
						//System.out.println("- - Ot given+holi - - -");
						table = new PdfPTable(earnList.size()+3);
					}else{
						//System.out.println("- - Ot given only - - -");
						if(bean.getPresentDay()>0 && bean.getBasic()==0 && bean.getOverTimeSal()==0.0){
							table = new PdfPTable(earnList.size()+1);
						}else{
							table = new PdfPTable(earnList.size()+2);
						}
						
					}
				}else if(bean.getHoliDayAmount() > 0.0){
					//System.out.println("- - Holi given - - -");
					table = new PdfPTable(earnList.size()+2);
				}else{
					//System.out.println("- - neither Ot nor holi given - - -");
					table = new PdfPTable(earnList.size()+1);
				}
				
				for(EmployeeSalaryComponentAmountBean empAllowance : earnList){	
					table.addCell(createLabelCell(empAllowance.getComponentName()));
				}
				
				//if only ot given
				if(otGiven){
					if(bean.getPresentDay()>0 && bean.getBasic()==0  && bean.getOverTimeSal()==0.0){
						//table.addCell(createLabelCell("E.D."));
						//table.addCell(createLabelCell("Ex.Duty."));
						table.addCell(createLabelCell("Salary Total"));
					}else if(bean.getPresentDay()>0 && bean.getBasic()==0 && bean.getOverTimeSal()>0.0){
						table.addCell(createLabelCell("Ex.Duty"));
						table.addCell(createLabelCell("Salary Total"));
					}
					else{
						table.addCell(createLabelCell("E.D"));
						table.addCell(createLabelCell("Salary Total"));
					}
				}
				if(holiGiven){
					table.addCell(createLabelCell("HOLIDAY"));
					table.addCell(createLabelCell("Salary Total"));
				}
				if(bothGiven){
					table.addCell(createLabelCell("HOLIDAY"));
					table.addCell(createLabelCell("E.D."));
					table.addCell(createLabelCell("Salary Total"));
				}
				if(bothNotGiven){
					table.addCell(createLabelCell("Salary Total"));
				}
				/***************** create cell when ot given and holiday given or not given *****************************//*
				if(otGiven){
					if(bean.getHoliDayAmount() > 0.0){
						table.addCell(createLabelCell("HOLIDAY"));
						table.addCell(createLabelCell("EX.DUTY"));
						table.addCell(createLabelCell("Salary Total"));
					}else{
						table.addCell(createLabelCell("EX.DUTY"));
						table.addCell(createLabelCell("Salary Total"));
					}
				}else{
					table.addCell(createLabelCell("Salary Total"));
				}
				*//***************** create cell when holiday given and ot given or not given *****************************//*
				if(bean.getHoliDayAmount() > 0.0){
					if(bean.getOtSalary() > 0.0){
						table.addCell(createLabelCell("HOLIDAY"));
						table.addCell(createLabelCell("EX.DUTY"));
						table.addCell(createLabelCell("Salary Total"));
					}else{
						table.addCell(createLabelCell("HOLIDAY"));
						table.addCell(createLabelCell("Salary Total"));
					}
				}else{
					table.addCell(createLabelCell("Salary Total"));
				}*/
				
				Double total = 0.0;
				for(EmployeeSalaryComponentAmountBean empAllowance : earnList){	
					table.addCell(createValueCell( String.valueOf( DoubleFormattor.setDoubleFormat(empAllowance.getComponentAmount()) ) ));
					total += empAllowance.getComponentAmount();
				}
				
				if(otGiven){
					if(bean.getPresentDay()>0 && bean.getBasic()==0 && bean.getOverTimeSal()==0.0){
						double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
						table.addCell(createValueCellBold(String.valueOf(totSal-bean.getOtSalary())));
					}else if(bean.getPresentDay()>0 && bean.getBasic()==0 && bean.getOverTimeSal()>0.0){
						//double otSal = DoubleFormattor.setDoubleFormat(bean.getOtSalary());
						double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary()-bean.getOtSalary());
						double overSal = DoubleFormattor.setDoubleFormat(bean.getOverTimeSal());
						//table.addCell(createValueCell(  String.valueOf(otSal)));
						table.addCell(createValueCell(  String.valueOf(overSal)));
						table.addCell(createValueCellBold(String.valueOf(totSal)));
					}else{
						double otSal = DoubleFormattor.setDoubleFormat(bean.getOtSalary());
						double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
						table.addCell(createValueCell(  String.valueOf(otSal)));
						table.addCell(createValueCellBold(String.valueOf(totSal)));
					}	
				}
				if(holiGiven){
					double holiSal = DoubleFormattor.setDoubleFormat(bean.getHoliDayAmount());
					double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
					table.addCell(createValueCell(  String.valueOf(holiSal)));
					table.addCell(createValueCellBold(String.valueOf(totSal)));
				}
				if(bothGiven){
					double holiSal = DoubleFormattor.setDoubleFormat(bean.getHoliDayAmount());
					double otSal = DoubleFormattor.setDoubleFormat(bean.getOtSalary());
					double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
					table.addCell(createValueCell(  String.valueOf(holiSal)));
					table.addCell(createValueCell(  String.valueOf(otSal)));
					table.addCell(createValueCellBold(String.valueOf(totSal)));
				}
				if(bothNotGiven){
					double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
					table.addCell(createValueCellBold(String.valueOf(totSal)));
				}
				/******************* Create value cell if otgiven and holi not sure **********************************************/
				/*if(bean.getOtSalary() > 0.0){
					if(bean.getHoliDayAmount() > 0.0){
						double holiSal = DoubleFormattor.setDoubleFormat(bean.getHoliDayAmount());
						double otSal = DoubleFormattor.setDoubleFormat(bean.getOtSalary());
						double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
						table.addCell(createValueCell(  String.valueOf(holiSal)));
						table.addCell(createValueCell(  String.valueOf(otSal)));
						table.addCell(createValueCellBold(String.valueOf(totSal)));
					}else{
						double otSal = DoubleFormattor.setDoubleFormat(bean.getOtSalary());
						double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
						table.addCell(createValueCell(  String.valueOf(otSal)));
						table.addCell(createValueCellBold(String.valueOf(totSal)));
					}
				}else{
					double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
					table.addCell(createValueCellBold(String.valueOf(totSal)));
				}
				
				*//******************* Create value cell if holi given and ot not sure **********************************************//*
				if(bean.getHoliDayAmount() > 0.0){
					if(bean.getOtSalary() > 0.0){
						double holiSal = DoubleFormattor.setDoubleFormat(bean.getHoliDayAmount());
						double otSal = DoubleFormattor.setDoubleFormat(bean.getOtSalary());
						double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
						table.addCell(createValueCell(  String.valueOf(holiSal)));
						table.addCell(createValueCell(  String.valueOf(otSal)));
						table.addCell(createValueCellBold(String.valueOf(totSal)));
					}else{
						double holiSal = DoubleFormattor.setDoubleFormat(bean.getHoliDayAmount());
						double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
						table.addCell(createValueCell(  String.valueOf(holiSal)));
						table.addCell(createValueCellBold(String.valueOf(totSal)));
					}
				}else{
					double totSal = DoubleFormattor.setDoubleFormat(bean.getTotalSalary());
					table.addCell(createValueCellBold(String.valueOf(totSal)));
				}*/
				
				//table.setWidthPercentage(60);
				table.setTotalWidth(100f);
				return table;
			}else{
				PdfPTable table1 = new PdfPTable(1);
				/*PdfPCell cell;
				cell = new PdfPCell(new Phrase());*/
				
				table1.addCell(createLabelCell("") );
				table1.addCell(createValueCell("") );
				table1.setTotalWidth(100f);	
				//table.setWidthPercentage(60);
				return table1;
			}
			
		}
		
		public static PdfPTable createTableForDeductionOnSheet(Document document, RunPayRollBean bean) throws DocumentException{				
			PdfPTable mainDeductionTable ;
			
			System.out.println("bean.getOverTime() :"+bean.getOverTime()+" getOverTimeSal:: "+bean.getOverTimeSal());
			
			if(bean.getDeductionCompList().size() > 0){
				mainDeductionTable = new PdfPTable(1);
				
				PdfPTable deducTable = new PdfPTable(bean.getDeductionCompList().size()+1);
				for(EmployeeSalaryComponentAmountBean deduct : bean.getDeductionCompList()){
					deducTable.addCell( createLabelCell(deduct.getComponentName()) );
				}
				deducTable.addCell(createLabelCell("TOT. DED."));
				for(EmployeeSalaryComponentAmountBean deduct : bean.getDeductionCompList()){
					deducTable.addCell( createValueCell(String.valueOf( DoubleFormattor.setDoubleFormat(deduct.getComponentAmount())) ) );
				}
				deducTable.addCell(createValueCellBold( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getTotalDeduction()))));
				deducTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				PdfPTable netsalTable = new PdfPTable(2);
				PdfPCell cell;Font font;
				
				font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
				cell = new PdfPCell(new Phrase("NET SALARY : ",font));
				cell.setHorizontalAlignment(Element.ALIGN_TOP);
				cell.setBorder(Rectangle.NO_BORDER);
				netsalTable.addCell(cell);
				System.out.println("bean.getTotalSalary() :"+bean.getTotalSalary()+" getOtSalary():: "+bean.getOtSalary());
				//netsalTable.addCell(createLabelCellLeftUnderLine("NET SALARY :"));
				if(bean.getBasic()==0 && bean.getPresentDay()>0 && bean.getOtSalary()>0.0 && bean.getOverTimeSal()==0.0){
					System.out.println("Otsal :"+bean.getOtSalary()+" otsal:: "+bean.otSalary);
						cell = new PdfPCell(new Phrase( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getTotalSalary()-bean.getOtSalary())) ,font));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(Rectangle.NO_BORDER);
						netsalTable.addCell(cell);
				}else if(bean.getBasic()==0 && bean.getPresentDay()>0 && bean.getOverTimeSal()>0.0){
					cell = new PdfPCell(new Phrase( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getTotalSalary()-bean.getOtSalary())) ,font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorder(Rectangle.NO_BORDER);
					netsalTable.addCell(cell);
				}else{
					cell = new PdfPCell(new Phrase( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getNetSalary())) ,font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorder(Rectangle.NO_BORDER);
					netsalTable.addCell(cell);
				}
				
				
				//netsalTable.addCell(createValueCellRightFont( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getNetSalary()))) );
				netsalTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				mainDeductionTable.addCell( deducTable );
				mainDeductionTable.addCell( netsalTable );
				mainDeductionTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				return mainDeductionTable;
			}else{
				mainDeductionTable = new PdfPTable(1);
				
				PdfPTable deducTable = new PdfPTable(1);
				
				deducTable.addCell(createLabelCell("TOT. DED."));
				deducTable.addCell(createValueCellBold("0.00") );
				deducTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				PdfPTable netsalTable = new PdfPTable(2);
				PdfPCell cell;Font font;
				font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
				cell = new PdfPCell(new Phrase("NET SALARY : ",font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_TOP);
				netsalTable.addCell(cell);
				
				if(bean.getOverTime() > 0.0){
					System.out.println("Otsal :"+bean.getOtSalary()+" otsal:: "+bean.otSalary);
						cell = new PdfPCell(new Phrase( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getTotalSalary()-bean.getOtSalary())) ,font));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(Rectangle.NO_BORDER);
						netsalTable.addCell(cell);
						mainDeductionTable.addCell( deducTable );
						mainDeductionTable.addCell( netsalTable );
						mainDeductionTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				}else{
					cell = new PdfPCell(new Phrase( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getNetSalary())) ,font));
					cell.setHorizontalAlignment(Element.ALIGN_TOP);
					cell.setBorder(Rectangle.NO_BORDER);
					netsalTable.addCell(cell);
					
			//		netsalTable.addCell(createLabelCellLeftUnderLine("NET SALARY :"));
				//	netsalTable.addCell(createValueCellRightFont( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getNetSalary()))) );
					netsalTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
					
					mainDeductionTable.addCell( deducTable );
					mainDeductionTable.addCell( netsalTable );
					mainDeductionTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				}
				
				
				return mainDeductionTable;
			}
			
			/*ArrayList<EmployeeSalaryComponentAmountBean> deductionList = new ArrayList<EmployeeSalaryComponentAmountBean>();
			for(EmployeeSalaryComponentAmountBean empAllowance : bean.getComponentAmountBeanList()){	
				if(empAllowance.getComponentType().equalsIgnoreCase("DEDUCTION")){
					deductionList.add(empAllowance);
				}
			}	
			deductionList = bean.getDeductionCompList();
			PdfPTable table;
			System.out.println("deduction list size:: "+deductionList.size());
			if(deductionList.size()>0){
				if(bean.leaveDeduction>0.0){
					table = new PdfPTable(deductionList.size()+2);
				}else{
					table = new PdfPTable(deductionList.size()+1);
				}
				table = new PdfPTable(deductionList.size()+1);
				
				Double total=0.0;
				for(EmployeeSalaryComponentAmountBean empAllowance : deductionList){	
					table.addCell(createLabelCell(empAllowance.getComponentName()));
				}
				if(bean.getLeaveDeduction()>0.0){
					table.addCell(createLabelCell("Deductions"));
					table.addCell(createLabelCell("Total deduction"));
				}else{
					table.addCell(createLabelCell("Total deduction"));
				}
				
				//table.addCell(createLabelCell("Total deduction"));
				for(EmployeeSalaryComponentAmountBean empAllowance : deductionList){	
					table.addCell(createValueCell( String.valueOf(empAllowance.getComponentAmount()) ));
					total += empAllowance.getComponentAmount();
				}
				
				if(bean.getLeaveDeduction()>0.0){
					table.addCell(createValueCell(String.valueOf(bean.getLeaveDeduction())));
					//table.addCell(createValueCell(String.valueOf(total+bean.getLeaveDeduction())));
					table.addCell(createValueCell(String.valueOf(DoubleFormattor.setDoubleFormat(bean.getTotalDeduction()))));
				}else{
					table.addCell(createValueCell(String.valueOf(DoubleFormattor.setDoubleFormat(bean.getTotalDeduction()))));
				}
				
				if(bean.getLeaveDeduction()>0.0){
					PdfPTable netSalaryTable = new PdfPTable(2);
					netSalaryTable.addCell(createTableForNetSalary(document, bean));
					table.addCell(createLabelCellLeftBoldFont("Net Salary:"));
					for(int i=0;i<deductionList.size();i++){
						table.addCell(createLabelCell());
					}
				}else{
					PdfPTable netSalaryTable = new PdfPTable(2);
					netSalaryTable.addCell(createTableForNetSalary(document, bean));
					table.addCell(createLabelCellLeftBoldFont("Net Salary:"));
					for(int i=0;i<deductionList.size()-1;i++){
						table.addCell(createLabelCell());
					}
				}
				
				
				ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				ArrayList<EmployeeSalaryComponentAmountBean> deductList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				
				for(EmployeeSalaryComponentAmountBean escbean : bean.getComponentAmountBeanList()){
					if(escbean.getComponentType().equalsIgnoreCase("EARNING")){
						 earnList.add(escbean);
					}if(escbean.getComponentType().equalsIgnoreCase("DEDUCTION")){
						 deductList.add(escbean);
					 }
				}	
				
				Double totalearning = 0.0;
				for(EmployeeSalaryComponentAmountBean earn : earnList){
					totalearning += earn.getComponentAmount();
				}
				
				Double totaldeduction = 0.0;
				for(EmployeeSalaryComponentAmountBean deduct : deductList){
					totaldeduction += deduct.getComponentAmount();
				}
				
				Double netsalary = (totalearning+bean.otSalary) - (totaldeduction+bean.leaveDeduction) ;
				NumberFormat formatter = new DecimalFormat("#0.00");     
				//System.out.println(formatter.format(netsalary));
				//table.addCell(createLabelCellRight(String.valueOf(formatter.format(netsalary))));
				PdfPTable netSalaryTable = new PdfPTable(2);
				netSalaryTable.addCell(createTableNetSalary(document, bean));
				table.addCell(netSalaryTable);
				//table.addCell(createValueCellRightFont(String.valueOf(formatter.format(bean.getNetSalary()))));
				return table;
			}else{
				PdfPTable table1 = new PdfPTable(2);
				
				table1.addCell(createLabelCellRight("ESI "));
				table1.addCell(createLabelCellRight("Total Deduction "));
				
				ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				ArrayList<EmployeeSalaryComponentAmountBean> deductList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				
				for(EmployeeSalaryComponentAmountBean escbean : bean.getComponentAmountBeanList()){
					if(escbean.getComponentType().equalsIgnoreCase("EARNING")){
						 earnList.add(escbean);
					}if(escbean.getComponentType().equalsIgnoreCase("DEDUCTION")){
						 deductList.add(escbean);
					 }
				}	
				
				Double totalearning = 0.0;
				for(EmployeeSalaryComponentAmountBean earn : earnList){
					totalearning += earn.getComponentAmount();
				}
				
				Double totaldeduction = 0.0;
				for(EmployeeSalaryComponentAmountBean deduct : deductList){
					totaldeduction += deduct.getComponentAmount();
				}
				
				Double netsalary = (totalearning+bean.otSalary) - (totaldeduction+bean.leaveDeduction) ;
				NumberFormat formatter = new DecimalFormat("#0.00"); 
				table1.addCell(createValueCellRight("0.00"));
				table1.addCell(createValueCellRight("0.00"));
				table1.addCell(createLabelCellLeftBoldFont("Net Salary:"));
				table1.addCell(createValueCellRightFont(String.valueOf(formatter.format(bean.getNetSalary()))));
				return table1;
			}*/
			
		}
		
		
		
		public static PdfPTable createTableForSignatureSheet(Document document, RunPayRollBean bean )
				throws DocumentException {
		   PdfPTable table = new PdfPTable(1);
		   
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			//table.addCell(createLabelCell("ACCOUNT NUMBER"));
			table.addCell(createValueCell(""));
			table.setWidthPercentage(5);
			return table;
		}
		
		public static PdfPTable createTableWithTwoColumn(Document document, RunPayRollBean bean )
				throws DocumentException {
		   PdfPTable table = new PdfPTable(1);
		   
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			//table.addCell(createLabelCell(""));
			table.addCell(createTableForBDA(document, bean));	
			table.addCell(createTableForUnit(document, bean));	
			table.addCell(createTableForEmployee(document, bean));	
			table.addCell(createTableForSalary(document , bean));
			table.addCell(createTableForEarnings(document, bean));	
			table.addCell(createTableForDeductions(document, bean));
			table.addCell(createTableForNetSalary(document, bean));
			//table.setTableEvent(new BorderEvent());
			
			table.setWidthPercentage(10);
			return table;
		}
		
		public static PdfPTable createTableWithSingleColumn(Document document, RunPayRollBean bean )
				throws DocumentException {
		   PdfPTable table = new PdfPTable(2);
		   
			PdfPCell cell;Font font;
			
			font = new Font(Font.getFamily("HELVETICA"), 7, Font.NORMAL);
			cell = new PdfPCell(new Phrase("WAGES",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase( String.valueOf(bean.getWages()),font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("PRESENT",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase( String.valueOf(bean.getPresentDay()),font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			if(bean.getOtHoursF() > 0.0){
				cell = new PdfPCell(new Phrase("E.D.",font));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase( String.valueOf(bean.getOtHoursF()),font));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}
			
			if(bean.getOverTime() > 0.0){
				cell = new PdfPCell(new Phrase("Ex.Duty.",font));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase( String.valueOf(bean.getOverTime()),font));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}

			/*cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCell("WAGES"));
			table.addCell(createValueCell( String.valueOf(bean.getWages())));
			
			table.addCell(createLabelCell("PRESENT"));
			if(bean.getPresentDay()!=null){
				table.addCell(createValueCell(bean.getPresentDay().toString()));
			}else{
				table.addCell(createValueCell("00.00"));
			}
			*/
			//table.setTableEvent(new BorderEvent());
			//table.setWidthPercentage(5);
			return table;
		}
		
		public static PdfPTable createTableNetSalary(Document document, RunPayRollBean bean )
				throws DocumentException {
			System.out.println("Net salary table creating . . . . ");
			 PdfPTable table = new PdfPTable(1);
		  
			table.addCell(createLabelCell("NET SALARY : "));
			table.addCell(createLabelCell( String.valueOf(bean.getNetSalary())));
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setWidthPercentage(5);
		
			return table;
		}
		
		public static PdfPTable createTableEmpName(Document document, RunPayRollBean bean )
				throws DocumentException {
		   PdfPTable table = new PdfPTable(1);
		   
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeftUnderLine(bean.getEmpName()));
			table.getDefaultCell().setBorder(0);
			table.setWidthPercentage(5);
			return table;
		}
		
		public static PdfPTable createTableEmpDesg(Document document, RunPayRollBean bean )
				throws DocumentException {
		   PdfPTable table = new PdfPTable(1);
		   
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft(bean.getEmpDesignation()));
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setWidthPercentage(5);
			return table;
		}
		
		
		void createPdfHeader(ArrayList<RunPayRollBean> runPayRollBeanList) throws Exception{
			
			Paragraph p = new Paragraph();
			p.add(new Paragraph("BLACKBOY DETECTIVE AGENCY", catFont));
			p.setAlignment(Element.ALIGN_CENTER);
			String line = "--------------------------------------------------------------------------------------------------------------------------------";
			p.add(new Paragraph(line));
			document.add(p);
			String space1 = String.format("%170s", "");
			String spaces = String.format("%150s", "");
			String space2 = String.format("%120s", "");
			String space3 = String.format("%60s", "");
			String space4 = String.format("%80s", "");
			//String text = "\nSALARY MONTH YEAR: JULY 2016\nCompany Name :ABCD"+space4
				//	+ "\t\tUnit Name: UNITPQRS";

		//	Paragraph para = new Paragraph(text, new Font(Font.getFamily("HELVETICA"), 16f));
		//	document.add(para);
			
		//	addEmptyLine(para, 2);
			/*Paragraph pa = new Paragraph(line);
			pa.setAlignment(Element.ALIGN_CENTER);
			document.add(pa);
		    int i=1;*/
			for(RunPayRollBean bean : runPayRollBeanList){
				
				//System.out.println("OOOOOOOO" +bean.leaveDeduction);
				
				/*
				Paragraph companyInfo = new Paragraph();
				p.add(new Paragraph("BLACKBOY DETECTIVE AGENCY", catFont));
				p.setAlignment(Element.ALIGN_CENTER);
				p.add(new Paragraph(line));
				document.add(companyInfo);
				
				String text = "\nSALARY MONTH YEAR : "  +pdfMonth+space3  
							  + "\t\nCompany Name : "+companyName+space3
						      + "\t\nUnit Name: "+ unitname;

				Paragraph para = new Paragraph(text, new Font(Font.getFamily("HELVETICA"), 16f));
				document.add(para);
				
				addEmptyLine(para, 2);
				
				String empDetails = "\nEmployee Details : -" + space3
						+"\t\t\nEmployee Name : "+bean.getEmpName()+space3
						+"\t\t\nDesignation : "+bean.getEmpDesignation()+ space3
						+"\t\t\nPF NO : "+bean.getEmpPf()+ space3+"\nESI :"+bean.getEmpEsi();
						
				
				Paragraph details = new Paragraph(empDetails, new Font(Font.getFamily("VERDANA"), 15f));
				document.add(details);	
				Paragraph pa1 = new Paragraph(line);
				pa1.setAlignment(Element.ALIGN_CENTER);
				document.add(pa1);
				String earningDetails = "",deductionDetails="";
				
				Paragraph earningDetailsP = new Paragraph(earningDetails, new Font(Font.getFamily("VERDANA"), 14f));
				document.add(earningDetailsP);	*/
				
				if(bean.isChecked()){
					document.add(createTableForBDA(document, bean));
					
					document.add(createTableForUnit(document, bean));
					
					document.add(createTableForEmployee(document, bean));
					
					document.add(createTableForEarnings(document, bean));
					
					document.add(createTableForDeductions(document, bean));
					
					document.add(createTableForNetSalary(document, bean));
					
					Paragraph pa = new Paragraph(line);
					pa.setAlignment(Element.ALIGN_CENTER);
					document.add(pa);
					//event.setOrientation(PdfPage.LANDSCAPE);
					document.newPage();
				}
				
				 /*if (writer.getVerticalPosition(true) - table.getRowHeight(0) - table.getRowHeight(1) < document.bottom()) {
		                document.newPage();
		            }*/
				/*ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				ArrayList<EmployeeSalaryComponentAmountBean> deductList = new ArrayList<EmployeeSalaryComponentAmountBean>();
				
				for(EmployeeSalaryComponentAmountBean escbean : bean.getComponentAmountBeanList()){
					//earningDetails += "\t\t\n"+bean.getComponentName()+": "+space3+bean.getComponentAmount();
					if(escbean.getComponentType().equalsIgnoreCase("EARNING")){
						 earnList.add(escbean);
					}if(escbean.getComponentType().equalsIgnoreCase("DEDUCTION")){
						 deductList.add(escbean);
					 }
				}	
				earningDetails = "\nEARNING DETAILS : -" + space3;
				Double totalearning = 0.0;
				for(EmployeeSalaryComponentAmountBean earn : earnList){
					earningDetails += "\t\t\n"+earn.getComponentName()+": "+space4+earn.getComponentAmount();
					totalearning += earn.getComponentAmount();
				}
				earningDetails += "\t\t\nOT EARNINGS : "+space4+bean.otSalary;
				earningDetails += "\t\t\nTOTAL EARNINGS : "+space4+(totalearning+bean.otSalary);
				
				Paragraph earningDetailsP = new Paragraph(earningDetails, new Font(Font.getFamily("VERDANA"), 14f));
				document.add(earningDetailsP);	
				Paragraph pa2 = new Paragraph(line);
				pa2.setAlignment(Element.ALIGN_CENTER);
				document.add(pa2);
				
				deductionDetails = "\nDEDUCTION DETAILS : -" + space3;
				Double totaldeduction = 0.0;
				for(EmployeeSalaryComponentAmountBean deduct : deductList){
					deductionDetails += "\t\t\n"+deduct.getComponentName()+": "+space4+deduct.getComponentAmount();
					totaldeduction += deduct.getComponentAmount();
				}
				deductionDetails += "\t\t\nLeave DEDUCTIONS : "+space4+ bean.leaveDeduction;
				deductionDetails += "\t\t\nTOTAL DEDUCTIONS : "+space4+(totaldeduction+bean.leaveDeduction);
				
				Paragraph deduction = new Paragraph(deductionDetails, new Font(Font.getFamily("VERDANA"), 14f));
				document.add(deduction);	
				pa2.setAlignment(Element.ALIGN_CENTER);
				document.add(pa2);
				Double netsalary = (totalearning+bean.otSalary) - (totaldeduction+bean.leaveDeduction) ;
				
				pa2.setAlignment(Element.ALIGN_CENTER);
				document.add(pa2);
				String netSal = space3+"\nNET SALARY : "+netsalary;
				Paragraph netSalary = new Paragraph(netSal, new Font(Font.getFamily("VERDANA"), 14f));
				document.add(netSalary);	
				//Paragraph pa2 = new Paragraph(line);
				pa2.setAlignment(Element.ALIGN_CENTER);
				document.add(pa2);
				pa2.setAlignment(Element.ALIGN_CENTER);
				document.add(pa2);
				addEmptyLine(para, 2);*/
			}
		}
		/*
		void createPdfHeader(String mybuilder) throws Exception{
			
			Paragraph companyName = new Paragraph(mybuilder);
			companyName.getFont().setStyle(Font.BOLDITALIC);
			companyName.setAlignment(Paragraph.ALIGN_CENTER);
			companyName.getFont().setSize(9f);
			document.add(companyName);
			
		}*/
		
		public void getSheetDetails(String path, ArrayList<RunPayRollBean> runPayRollBeanList
				,RunPayRollBean bean	) throws Exception, DocumentException{
			filePath = path+"salarysheet.pdf";
			System.out.println("My file path :: "+filePath);
			//document = new Document(PageSize.LEGAL.rotate(),35f,5f,5f,5f);
			//writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			HeaderTable event = new HeaderTable(bean);

			//document = new Document(PageSize.LEGAL.rotate(),65f,5f,5f,5f);
			//writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			
			// HeaderTable event = new HeaderTable(document,bean);

		        // step 1
		      document = new Document(PageSize.LEGAL.rotate(), 65f,5f, 25f + event.getTableHeight(), 5f);
		        // step 2
		    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
		        writer.setPageEvent(event);
		     // step 3
			
			document.open();
			generateSheet(runPayRollBeanList , bean);
			//DownloadPdf.download(filePath,"salarysheet.pdf");
			openPdf(filePath);
			document.close();
		//	Rectangle pageSize = new Rectangle(516f ,1256f );
		//	document = new Document(PageSize.A4_LANDSCAPE, 2, 2, 60, 40);
		//	document = new Document(PageSize._11X17.rotate());
			
			//document = new Document(pageSize, 0f, 0f, 0f, 0f);
		    //document.setMargins(15, 15, 20, 10);
			//document.setMargins(0, 5, 10, 20);
		//	document.setMargins(10,60, 5,80);
		//	document.setMarginMirroring(true);
			
		//	writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 59, 788));
		//	writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 36, 36, 36));
		
			
			/*PdfPTable table = new PdfPTable(1);

	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);

	        // first row
	        PdfPCell cell = new PdfPCell(new Phrase("DATATATATe"));
	        cell.setColspan(10);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setPadding(5.0f);
	        cell.setBackgroundColor(new BaseColor(140, 221, 8));
	        table.addCell(cell);
			
			table.setWidthPercentage(100f);
			document.add(table);*/
			
		}
		
		
		
		void openPdf(String fileName) throws IOException{
			if (Desktop.isDesktopSupported()) {
				try {
			        File myFile = new File(fileName );
			        Desktop.getDesktop().open(myFile);
			    } catch (IOException ex) {
			       ex.printStackTrace();
			    }
			}
		}
		
		private static void addEmptyLine(Paragraph paragraph, int number) {
			for (int i = 0; i < number; i++) {
				paragraph.add(new Paragraph(" "));
			}
		}
		
		public static PdfPTable createTableForBDA(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setWidths(new int[]{19,3});
			PdfPCell cell;
			Font font = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
			
			PdfPCellEvent roundRectangle = new RoundRectangle();
			cell = new PdfPCell(new Phrase("BLACKBOY DETECTIVE\n AGENCY PVT. LTD.",font));
			cell.setRowspan(3);
			cell.setCellEvent(roundRectangle);
			cell.setPadding(2f);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
		    font = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);
		    cell =  new PdfPCell( new Phrase(bean.getMonthName().substring(0,3),font) ); 
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			font = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);
			Paragraph year = new Paragraph(bean.getYear(),font);
			year.setAlignment(Element.ALIGN_RIGHT);
			cell = new PdfPCell( new Phrase(year) );
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			table.addCell(new Phrase(""));
			
			//cell = new PdfPCell(new Phrase());
			//table.addCell(createLabelCellLeft("BLACKBOY DETECTIVE \nAGENCY PVT. LTD."));
			//table.addCell(createLabelCellRight(bean.getMonthName()+" "+bean.getYear()));
			//table.setTableEvent(new BorderEvent());
			//table.setSpacingBefore(5);
			return table;
		}
		
		public static PdfPTable createTableForEmployee(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			PdfPCell cell ; Font font;
			font = new Font(Font.getFamily("HELVETICA"), 14, Font.BOLD);
			cell = new PdfPCell(new Phrase("Name",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			/*cell = new PdfPCell( new Phrase());
			cell.setRowspan(2);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);*/
			
			cell = new PdfPCell(new Phrase(bean.getEmpName()));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(bean.getEmpCode()));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(bean.getEmpDesignation()));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			if(bean.getEmpPf() != null){
				cell = new PdfPCell(new Phrase("PF : "+bean.getEmpPf()));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}else{
				cell = new PdfPCell(new Phrase("PF : "));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}
			
			if(bean.getEmpEsi() != null){
				cell = new PdfPCell(new Phrase("ESI : "+bean.getEmpEsi()));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}else{
				cell = new PdfPCell(new Phrase("ESI : "));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}
			
			if(bean.getEmpUan() != null){
				cell = new PdfPCell(new Phrase("UAN : "+bean.getEmpUan()));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}else{
				cell = new PdfPCell(new Phrase("UAN : "+0));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}
			
			/*cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("Name"));
			table.addCell(createLabelCellRight("PF : "+bean.getEmpPf()+"\n"+"ESI : "+bean.getEmpEsi()));
			table.addCell(createValueCellLeft(bean.getEmpName()+"\n"+bean.getEmpDesignation()));
			table.addCell(createValueCellRight(""));*/
			//table.setTableEvent(new BorderEvent());
			return table;
		}
		
		public static PdfPTable createTableForSalary(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			PdfPCell cell;Font font;
			font = new Font(Font.getFamily("HELVETICA"), 14, Font.BOLD);
			cell = new PdfPCell(new Phrase("Salary",font));
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Wages"));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Phrase( String.valueOf(bean.getWages())));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Salary"));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Present"));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bean.getPresentDay())));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bean.getBasic())));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Ex.Duty"));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(bean.getOtHoursF())));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			if(bean.getOtSalary() > 0.0 ){
				cell = new PdfPCell(new Phrase(String.valueOf(bean.getOtSalary())));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}else{
				cell = new PdfPCell(new Phrase("0.00"));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}
			return table;
		}
		
		
		public static PdfPTable createTableForUnit(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(1);
			PdfPCell cell;Font font;
			font = new Font(Font.getFamily("HELVETICA"), 14, Font.BOLD);
			cell = new PdfPCell(new Phrase("Unit",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(bean.getComapnyName()));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(bean.getUnitName()));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			//table.setWidthPercentage(100);
			
			//cell = new PdfPCell(new Phrase("Unit"));
			//cell.setColspan(3);
			//table.addCell(createLabelCellLeft("Unit"));
			
			//cell = new PdfPCell( new Phrase(bean.getComapnyName()) ); 
			//table.addCell(createValueCellLeft(bean.getComapnyName()));
			//table.addCell(createLabelCellLeft("Unit".trim()));
			
			//table.addCell(createValueCellLeft(bean.getComapnyName()));
			//table.addCell(createValueCellLeft(bean.getUnitName()));
			//table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			//table.setTableEvent(new BorderEvent());
			return table;
		}
		
		
		public static PdfPTable createTableForEarnings(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell cell;Font font ;boolean otGiven =false, holiDayGiven = false, bothGiven = false,bothNotGiven = false;
			if(bean.getOtSalary() > 0.0 && bean.getHoliDayAmount() == 0.0){
				otGiven = true; holiDayGiven = false; bothGiven = false; bothNotGiven = false;
			}
			if(bean.getHoliDayAmount() > 0.0 && bean.getOtSalary() == 0.0){
				otGiven = false; holiDayGiven = true; bothGiven = false; bothNotGiven = false;
			}
			if(bean.getHoliDayAmount() > 0.0 && bean.getOtSalary() > 0.0){
				otGiven = false; holiDayGiven = false; bothGiven = true; bothNotGiven = false;
			}
			if(bean.getHoliDayAmount() == 0.0 && bean.getOtSalary() == 0.0){
				otGiven = false; holiDayGiven = false; bothGiven = false; bothNotGiven = true;
			}
			
			font = new Font(Font.getFamily("HELVETICA"), 14, Font.BOLD);
			cell = new PdfPCell(new Phrase("Components",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			font = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
			cell =  new PdfPCell( new Phrase( "Amount(Rs.)",font) );
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//table.addCell(createLabelCellLeft("Allowance".trim()));
			//table.addCell(createLabelCellRight("Amount(Rs.)".trim()));
			double total = 0.0;
			for(EmployeeSalaryComponentAmountBean  index : bean.getEarningCompList()){
				//if(index.getComponentType().equalsIgnoreCase("EARNING")){
					table.addCell(createValueCellLeft(index.getComponentName()));
					table.addCell(createValueCellRight(String.valueOf(index.getComponentAmount())));
					total += index.getComponentAmount();
				//}
			}
			/*if(bean.getOtSalary()>0.0){
				table.addCell(createValueCellLeft("Extra duty"));
				table.addCell(createValueCellRight(String.valueOf(bean.otSalary)));
			}*/
			if(otGiven){
				table.addCell(createValueCellLeft("Extra duty"));
				table.addCell(createValueCellRight(String.valueOf(bean.otSalary)));
			}
			if(holiDayGiven){
				table.addCell(createValueCellLeft("Holiday"));
				table.addCell(createValueCellRight(String.valueOf(bean.getHoliDayAmount())));
			}
			if(bothGiven){
				table.addCell(createValueCellLeft("Extra duty"));
				table.addCell(createValueCellRight(String.valueOf(bean.otSalary)));
				table.addCell(createValueCellLeft("Holiday"));
				table.addCell(createValueCellRight(String.valueOf(bean.getHoliDayAmount())));
			}
			table.addCell(createValueCellLeft("Total"));
			table.addCell(createValueCellRight(String.valueOf(bean.getTotalSalary())));
				
				//Paragraph netSalary = new Paragraph("", new Font(Font.getFamily("VERDANA"), 14f));
				//document.add(netSalary);
				//table.setTableEvent(new BorderEvent());
			return table;
		}
		
		
		public static PdfPTable createTableForDeductions(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell cell;Font font ;
			font = new Font(Font.getFamily("HELVETICA"), 14, Font.BOLD);
			cell = new PdfPCell(new Phrase("Deduction",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			font = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
			cell =  new PdfPCell( new Phrase("Amount(Rs.)",font) );
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			//table.addCell(createLabelCellLeft("Deductions".trim()));
			//table.addCell(createLabelCellRight("Amount(Rs.)".trim()));
			double total = 0.0;
				for(EmployeeSalaryComponentAmountBean  index : bean.getDeductionCompList()){
					//if(index.getComponentType().equalsIgnoreCase("DEDUCTION")){
						table.addCell(createValueCellLeft(index.getComponentName()));
						table.addCell(createValueCellRight(String.valueOf(index.getComponentAmount())));
						total += index.getComponentAmount();
					//}
				}
				if(bean.getLeaveDeduction() >0.0){
					table.addCell(createValueCellLeft("Leave deductions"));
					table.addCell(createValueCellRight(String.valueOf(bean.getLeaveDeduction())));
				}
				table.addCell(createValueCellLeft("Total deductions"));
				//table.addCell(createValueCellRight(String.valueOf(total+bean.leaveDeduction)));
				table.addCell(createValueCellRight(String.valueOf(bean.getTotalDeduction())));
				//table.setTableEvent(new BorderEvent());
			return table;
		}
		
	
		public static PdfPTable createTableForNetSalary(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell cell;Font font ;
			font = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
			cell = new PdfPCell(new Phrase("NET SALARY",font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			
			cell =  new PdfPCell( new Phrase( String.valueOf(bean.getNetSalary()),font) );
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			//table.addCell(createLabelCellLeft("Net Salary".trim()));
			
		/*	ArrayList<EmployeeSalaryComponentAmountBean> earnList = new ArrayList<EmployeeSalaryComponentAmountBean>();
			ArrayList<EmployeeSalaryComponentAmountBean> deductList = new ArrayList<EmployeeSalaryComponentAmountBean>();
			
			for(EmployeeSalaryComponentAmountBean escbean : bean.getComponentAmountBeanList()){
				if(escbean.getComponentType().equalsIgnoreCase("EARNING")){
					 earnList.add(escbean);
				}if(escbean.getComponentType().equalsIgnoreCase("DEDUCTION")){
					 deductList.add(escbean);
				 }
			}	
			
			Double totalearning = 0.0;
			for(EmployeeSalaryComponentAmountBean earn : earnList){
				totalearning += earn.getComponentAmount();
			}
			
			Double totaldeduction = 0.0;
			for(EmployeeSalaryComponentAmountBean deduct : deductList){
				totaldeduction += deduct.getComponentAmount();
			}
			
			Double netsalary = (totalearning+bean.otSalary) - (totaldeduction+bean.leaveDeduction) ;
			NumberFormat formatter = new DecimalFormat("#0.00");     
			//System.out.println(formatter.format(netsalary));
			table.addCell(createLabelCellRight(String.valueOf(formatter.format(bean.getNetSalary()))));
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			PdfPCell cell2;

			cell2 = new PdfPCell(new Phrase());*/
			return table;
		}
		
		private static PdfPCell createLabelCell(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			//cell.setColspan(2);
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createLabelCellBold(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			//cell.setColspan(2);
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createLabelCell() {
			PdfPCell cell = new PdfPCell(new Phrase(""));
			cell.setPaddingLeft(25);
		
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createLabelCellLeft(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			right.setAlignment(Element.ALIGN_TOP);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createLabelCellLeftBold(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 11, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createLabelCellLeftBoldFont(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createLabelCellLeftUnderLine(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 9, Font.UNDERLINE);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			right.setAlignment(Element.ALIGN_TOP);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		
		private static PdfPCell createLabelCellRight(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createLabelCellRightFont(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 11, Font.NORMAL);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}

		private static PdfPCell createValueCell(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		

		private static PdfPCell createValueCellBold(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createValueCellBoldLeft(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 10, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createValueCellRight(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createValueCellRightFont(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 10, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
		
		private static PdfPCell createValueCellLeft(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;
		}
}
