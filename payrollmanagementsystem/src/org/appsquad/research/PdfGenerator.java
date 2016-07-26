package org.appsquad.research;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.appsquad.bean.RunPayRollBean;
import org.zkoss.zk.ui.Executions;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

	private static String filePath;
	private static Document document = null;
	private static PdfWriter writer = null;
	
	
	RunPayRollBean runPayRollBean = new RunPayRollBean();
	ArrayList<RunPayRollBean> runPayRollBeanList = new ArrayList<RunPayRollBean>();
	
	public static void getDetails(String data,String path, ArrayList<RunPayRollBean> runPayRollBeanList,RunPayRollBean bean) throws Exception, DocumentException{
		
		filePath = path+"pay.pdf";
		System.out.println("My file path :: "+filePath);
		document = new Document(PageSize.A4, 2, 2, 60, 40);
		document.setMargins(-40, -60, 2, 2);
		writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
		writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 59, 788));
		document.open();
		
		createPdfHeader(bean);
		//createPdfHeader(data);
		
		openPdf(filePath);
		document.close();
	}
	
	static void createPdfHeader(RunPayRollBean runPayRollBean) throws Exception{
		
		Paragraph companyName = new Paragraph("BDA DETECTIVE AGENCY");
		companyName.getFont().setStyle(Font.BOLDITALIC);
		companyName.setAlignment(Paragraph.ALIGN_CENTER);
		companyName.getFont().setSize(9f);
		document.add(companyName);
		
		String salaryMonthYear = "SALARY MONTH YEAR: ";
		Paragraph salaryParagraph = new Paragraph(salaryMonthYear);
		document.add(salaryParagraph);
	}
	
	static void openPdf(String fileName) throws IOException{
		if (Desktop.isDesktopSupported()) {
			try {
		        File myFile = new File(fileName );
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException ex) {
		       ex.printStackTrace();
		    }
		}
	}
	
	public static void main(String[] args) throws Exception {
		PdfGenerator.getDetails("Som", Executions.getCurrent().getDesktop().getWebApp().getRealPath("/"),
				new ArrayList<RunPayRollBean>(), new RunPayRollBean());
	}

}
