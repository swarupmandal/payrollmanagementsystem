package utility;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.RunPayRollBean;
import org.appsquad.pdfhandler.BorderEvent;
import org.appsquad.pdfhandler.Rotate;
import org.appsquad.research.DoubleFormattor;
import org.zkoss.zul.impl.Padding;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;



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
		
		RunPayRollBean runPayRollBean = new RunPayRollBean();
		ArrayList<RunPayRollBean> runPayRollBeanList = new ArrayList<RunPayRollBean>();
		
		public void getDetails(String data,String path, ArrayList<RunPayRollBean> runPayRollBeanList,
				RunPayRollBean bean, String company, String unit) throws Exception, DocumentException{
			filePath = path+"pay.pdf";
			System.out.println("My file path :: "+filePath);
			unitname = unit;
			companyName = company;
			System.out.println("COMPA " + companyName);
			/*document = new Document(PageSize.A4, 2, 2, 60, 40);
			document.setMargins(-40, -60, 2, 2);
			
			Rectangle pagesize = new Rectangle(216f, 720f);
			
			document = new Document(PageSize.A4, 2, 2, 60, 40);
			
		    document = new Document(PageSize.A4.rotate());
			//document.setMargins(40,40, 60, 10);
			//document.setMargins(20, 20, 20, 20);*/
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
		}
		
		
		public void generatePaySlip(ArrayList<RunPayRollBean> runPayRollBeanList
				,RunPayRollBean bean){
			
	        try {
	        	PdfPTable table = new PdfPTable(3);
	        	table.addCell( createLabelCell() );
 	            table.addCell( createLabelCell() );
 	            table.addCell( createLabelCell());
 	           for(int i=0;i<3;i++){
 	        	   for(RunPayRollBean payRollBean : runPayRollBeanList){
 	        		   if(payRollBean.isChecked()){
 	        			  table.addCell(createTableWithTwoColumn(document, payRollBean));
 	        			  PdfPCell cell;
 	      			   	  cell = new PdfPCell(new Phrase());
 	      	              document.add(table);
 	        		   }
 	        	   }
 	        	  table.setSpacingBefore(35f);
 	           }
 	           table.setWidthPercentage(100);
 	           //table.setTableEvent(new BorderEvent());
 	           table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
 	          
 	          table.setSpacingAfter(40f);
 	            document.add(table);
	           
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        } finally {
	            document.close();
	        }
		}
		
		
		public void generateSheet(ArrayList<RunPayRollBean> runPayRollBeanList
				,RunPayRollBean bean) throws Exception{
			document.add(createTableForLogo(document, bean));
			//document.add(createTableForHeader(document, bean));
			MNC:
			for(RunPayRollBean runPayRollBean : runPayRollBeanList){
				if(runPayRollBean.isChecked()){
					document.add(createTableForSheet(document, runPayRollBean));
				}else{
					document.add(createTableForSheet(document, runPayRollBean));
				}
			}
		}
		
		public static PdfPTable createTableForSheet(Document document, RunPayRollBean bean) throws Exception{
		//	PdfPTable table = new PdfPTable(4);
			float[] columnWidths = {70, 30,225, 75,70};
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
			float[] columnWidths = {30, 30,70};
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
			PdfPTable table;
			if(earnList.size()>0){
				if(bean.otSalary>0.0){
					table = new PdfPTable(earnList.size()+2);
				}else{
					table = new PdfPTable(earnList.size()+1);
				}
				
				for(EmployeeSalaryComponentAmountBean empAllowance : earnList){	
					table.addCell(createLabelCell(empAllowance.getComponentName()));
				}
				
				if(bean.getOtSalary()>0.0){
					table.addCell(createLabelCell("OT earnings"));
					table.addCell(createLabelCell("Salary Total"));
				}else{
					table.addCell(createLabelCell("Salary Total"));
				}
				
				Double total = 0.0;
				for(EmployeeSalaryComponentAmountBean empAllowance : earnList){	
					table.addCell(createValueCell( String.valueOf( DoubleFormattor.setDoubleFormat(empAllowance.getComponentAmount()) ) ));
					total += empAllowance.getComponentAmount();
				}
				if(bean.getOtSalary()>0.0){
					table.addCell(createValueCell(String.valueOf(bean.otSalary)));
					table.addCell(createValueCell(String.valueOf(total+bean.otSalary)));
				}else{
					table.addCell(createValueCell(String.valueOf(total)));
				}
				
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
				deducTable.addCell(createValueCell( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getTotalDeduction()))));
				deducTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				PdfPTable netsalTable = new PdfPTable(2);
				netsalTable.addCell(createLabelCellLeftUnderLine("NET SALARY :"));
				netsalTable.addCell(createValueCellRightFont( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getNetSalary()))) );
				netsalTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				mainDeductionTable.addCell( deducTable );
				mainDeductionTable.addCell( netsalTable );
				mainDeductionTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				return mainDeductionTable;
			}else{
				mainDeductionTable = new PdfPTable(1);
				
				PdfPTable deducTable = new PdfPTable(1);
				
				deducTable.addCell(createLabelCell("TOT. DED."));
				deducTable.addCell(createValueCell("0.00") );
				deducTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				PdfPTable netsalTable = new PdfPTable(2);
				netsalTable.addCell(createLabelCellLeftUnderLine("NET SALARY :"));
				netsalTable.addCell(createValueCellRightFont( String.valueOf( DoubleFormattor.setDoubleFormat( bean.getNetSalary()))) );
				netsalTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				mainDeductionTable.addCell( deducTable );
				mainDeductionTable.addCell( netsalTable );
				mainDeductionTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
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
			table.addCell(createLabelCell(""));
			table.addCell(createTableForBDA(document, bean));	
			table.addCell(createTableForUnit(document, bean));	
			table.addCell(createTableForEmployee(document, bean));	
			table.addCell(createTableForEarnings(document, bean));	
			table.addCell(createTableForDeductions(document, bean));
			table.addCell(createTableForNetSalary(document, bean));
			//table.setTableEvent(new BorderEvent());
			table.setWidthPercentage(5);
			return table;
		}
		
		public static PdfPTable createTableWithSingleColumn(Document document, RunPayRollBean bean )
				throws DocumentException {
		   PdfPTable table = new PdfPTable(1);
		   
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCell("WAGES"));
			table.addCell(createValueCell( String.valueOf(bean.getWages())));
			
			table.addCell(createLabelCell("PRESENT"));
			if(bean.getPresentDay()!=null){
				table.addCell(createValueCell(bean.getPresentDay().toString()));
			}else{
				table.addCell(createValueCell("00.00"));
			}
			
			//table.setTableEvent(new BorderEvent());
			table.setWidthPercentage(5);
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
			document = new Document(PageSize.LEGAL.rotate(),35f,5f,5f,5f);
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			generateSheet(runPayRollBeanList , bean);
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
			table.setWidthPercentage(100);
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("BLACKBOY DETECTIVE \nAGENCY PVT. LTD."));
			table.addCell(createLabelCellRight(bean.getMonthName()+" "+bean.getYear()));
			//table.setTableEvent(new BorderEvent());
			table.setSpacingBefore(5);
			return table;
		}
		
		public static PdfPTable createTableForEmployee(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("Name"));
			table.addCell(createLabelCellRight("PF : "+bean.getEmpPf()+"\n"+"ESI : "+bean.getEmpEsi()));
			table.addCell(createValueCellLeft(bean.getEmpName()+"\n"+bean.getEmpDesignation()));
			table.addCell(createValueCellRight(""));
			//table.setTableEvent(new BorderEvent());
			return table;
		}
		
		
		public static PdfPTable createTableForUnit(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("Unit".trim()));
			
			table.addCell(createValueCellLeft(bean.getComapnyName()));
			table.addCell(createValueCellLeft(bean.getUnitName()));
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			//table.setTableEvent(new BorderEvent());
			return table;
		}
		
		
		public static PdfPTable createTableForEarnings(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("Allowance".trim()));
			table.addCell(createLabelCellRight("Amount(Rs.)".trim()));
			double total = 0.0;
				for(EmployeeSalaryComponentAmountBean  index : bean.getComponentAmountBeanList()){
					if(index.getComponentType().equalsIgnoreCase("EARNING")){
						table.addCell(createValueCellLeft(index.getComponentName()));
						table.addCell(createValueCellRight(String.valueOf(index.getComponentAmount())));
						total += index.getComponentAmount();
					}
				}
				if(bean.getOtSalary()>0.0){
					table.addCell(createValueCellLeft("OT earnings"));
					table.addCell(createValueCellRight(String.valueOf(bean.otSalary)));
				}
				table.addCell(createValueCellLeft("Total earnings"));
				table.addCell(createValueCellRight(String.valueOf(total+bean.otSalary)));
				
				Paragraph netSalary = new Paragraph("", new Font(Font.getFamily("VERDANA"), 14f));
				document.add(netSalary);
				//table.setTableEvent(new BorderEvent());
			return table;
		}
		
		
		public static PdfPTable createTableForDeductions(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("Deductions".trim()));
			table.addCell(createLabelCellRight("Amount(Rs.)".trim()));
			double total = 0.0;
				for(EmployeeSalaryComponentAmountBean  index : bean.getComponentAmountBeanList()){
					if(index.getComponentType().equalsIgnoreCase("DEDUCTION")){
						table.addCell(createValueCellLeft(index.getComponentName()));
						table.addCell(createValueCellRight(String.valueOf(index.getComponentAmount())));
						total += index.getComponentAmount();
					}
				}
				if(bean.getLeaveDeduction() >0.0){
					table.addCell(createValueCellLeft("Leave deductions"));
					table.addCell(createValueCellRight(String.valueOf(bean.getLeaveDeduction())));
				}
				table.addCell(createValueCellLeft("Total deductions"));
				table.addCell(createValueCellRight(String.valueOf(total+bean.leaveDeduction)));
				//table.setTableEvent(new BorderEvent());
			return table;
		}
		
	
		public static PdfPTable createTableForNetSalary(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("Net Salary".trim()));
			
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
			table.addCell(createLabelCellRight(String.valueOf(formatter.format(netsalary))));
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			PdfPCell cell2;

			cell2 = new PdfPCell(new Phrase());
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
			cell.setPadding(25);
		
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
