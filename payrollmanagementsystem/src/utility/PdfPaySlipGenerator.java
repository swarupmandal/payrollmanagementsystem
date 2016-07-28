package utility;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.appsquad.bean.EmployeeSalaryComponentAmountBean;
import org.appsquad.bean.RunPayRollBean;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class PdfPaySlipGenerator {

		private String filePath;
		private Document document = null;
		private PdfWriter writer = null;
		private static String tab = "\t\t\t\t\t\t\t\t";
		private String companyName;
		private String unitname;
		private String pdfMonth;
		
		
		
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
			document.setMargins(-40, -60, 2, 2);*/
			Rectangle pagesize = new Rectangle(216f, 720f);
			
			document = new Document(PageSize.A4_LANDSCAPE, 2, 2, 60, 40);
			
			document.setMargins(40,40, 60, 10);

			 document.setMarginMirroring(true);
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 59, 788));
			document.open();
			
			for(RunPayRollBean rollBean : runPayRollBeanList){
				rollBean.setComapnyName(company);
				rollBean.setUnitName(unit);
			}
			createPdfHeader(runPayRollBeanList);
			//createPdfHeader(data);
			
			openPdf(filePath);
			document.close();
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
			Paragraph pa = new Paragraph(line);
			pa.setAlignment(Element.ALIGN_CENTER);
			document.add(pa);
		    int i=1;
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
				
				document.add(createTableForBDA(document, bean));
				
				document.add(createTableForUnit(document, bean));
				
				document.add(createTableForEmployee(document, bean));
				
				document.add(createTableForEarnings(document, bean));
				
				/*Paragraph deductionDetailsP = new Paragraph(deductionDetails, new Font(Font.getFamily("VERDANA"), 14f));
				document.add(deductionDetailsP);*/
				
				
				document.add(createTableForDeductions(document, bean));
				
				document.add(createTableForNetSalary(document, bean));
				
				
				
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
			table.addCell(createLabelCellRight("JULY 2016"));
		
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
			return table;
		}
		
		
		public static PdfPTable createTableForUnit(Document document, RunPayRollBean bean )
				throws DocumentException {
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			PdfPCell cell;

			cell = new PdfPCell(new Phrase());
			table.addCell(createLabelCellLeft("Unit".trim()));
			
			table.addCell(createValueCellLeft("Company "+bean.getComapnyName()));
			table.addCell(createValueCellLeft("Unit "+bean.getUnitName()));
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
				addEmptyLine(netSalary, 2);
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
			table.addCell(createLabelCellRight(String.valueOf(netsalary)));
			
			return table;
		}
		
		private static PdfPCell createLabelCell(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			return cell;
		}
		
		private static PdfPCell createLabelCellLeft(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			return cell;
		}
		
		private static PdfPCell createLabelCellRight(String text) {
			
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.BOLD);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			return cell;
		}

		private static PdfPCell createValueCell(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			return cell;
		}
		
		private static PdfPCell createValueCellRight(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			return cell;
		}
		
		private static PdfPCell createValueCellLeft(String text) {
			Font font = new Font(Font.getFamily("HELVETICA"), 8, Font.NORMAL);
			Paragraph right = new Paragraph(text,font);
			right.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell(new Phrase(text, font));
			cell.addElement(right);
			return cell;
		}
}
