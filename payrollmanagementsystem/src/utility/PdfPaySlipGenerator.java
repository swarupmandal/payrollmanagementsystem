package utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.appsquad.bean.RunPayRollBean;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;



public class PdfPaySlipGenerator {

		private String filePath;
		private Document document = null;
		private PdfWriter writer = null;
		
		
		RunPayRollBean runPayRollBean = new RunPayRollBean();
		ArrayList<RunPayRollBean> runPayRollBeanList = new ArrayList<RunPayRollBean>();
		
		public void getDetails(String data,String path, ArrayList<RunPayRollBean> runPayRollBeanList,RunPayRollBean bean) throws Exception, DocumentException{
			
			filePath = path+"pay.pdf";
			System.out.println("My file path :: "+filePath);
			document = new Document(PageSize.A4, 2, 2, 60, 40);
			document.setMargins(-40, -60, 2, 2);
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 59, 788));
			document.open();
			
			//createPdfHeader(bean);
			createPdfHeader(data);
			document.close();
		}
		
		/*void createPdfHeader(RunPayRollBean runPayRollBean) throws Exception{
			
			Paragraph companyName = new Paragraph("BDA DETECTIVE AGENCY");
			companyName.getFont().setStyle(Font.BOLDITALIC);
			companyName.setAlignment(Paragraph.ALIGN_CENTER);
			companyName.getFont().setSize(9f);
			document.add(companyName);
		}*/
		
		void createPdfHeader(String mybuilder) throws Exception{
			
			Paragraph companyName = new Paragraph(mybuilder);
			companyName.getFont().setStyle(Font.BOLDITALIC);
			companyName.setAlignment(Paragraph.ALIGN_CENTER);
			companyName.getFont().setSize(9f);
			document.add(companyName);
		}
		
		
		
	
}
