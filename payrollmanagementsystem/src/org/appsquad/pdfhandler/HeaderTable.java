package org.appsquad.pdfhandler;

import org.appsquad.bean.RunPayRollBean;

import utility.PdfPaySlipGenerator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderTable extends PdfPageEventHelper {
    protected PdfPTable table;
    protected float tableHeight;
    public HeaderTable(RunPayRollBean bean) throws DocumentException {
    	float[] columnWidths = {8,  16};
		 table = new PdfPTable(columnWidths);Font font;
		
    	//table = new PdfPTable(1);
        table.setTotalWidth(930);
        //table.setLockedWidth(true);
        PdfPTable nxtTable = new PdfPTable(2);
		nxtTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		nxtTable.setWidthPercentage(60);
		
		font = new Font(Font.getFamily("HELVETICA"), 14, Font.BOLD);
		PdfPCell cell = new PdfPCell(new Phrase("SALARY SHEET", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		nxtTable.addCell(cell);
		
		font = new Font(Font.getFamily("HELVETICA"), 11, Font.NORMAL);
		cell = new PdfPCell(new Phrase(bean.getComapnyName()+"\n"+bean.getUnitName()+"\n"+
				bean.getMonthName()+"      "+bean.getYear()+"     "+bean.getCurrentDate()+"\n"+
				bean.getUnitDesignation(), font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		nxtTable.addCell(cell);
		
		/*nxtTable.addCell(bean.getComapnyName()+"\n"+bean.getUnitName()+"\n"+
				bean.getMonthName()+"      "+bean.getYear()+"     "+bean.getCurrentDate()+"\n"+
				bean.getUnitDesignation());*/
		
		PdfPTable logoTable = new PdfPTable(4);
		logoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCellEvent roundRectangle = new RoundRectangle();
		Font white = new Font();
        font = new Font(Font.getFamily("HELVETICA"), 23, Font.BOLD);
        white.setColor(BaseColor.WHITE); 
		cell = new PdfPCell(new Phrase(" BDA" , font));
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorderColor(BaseColor.GRAY);
        cell.setBorderWidth(2f);
        cell.setPaddingBottom(7f);
        cell.setPaddingLeft(5f);
    	cell.setCellEvent(roundRectangle);
		logoTable.addCell(cell);
        
		cell = new PdfPCell(new Phrase());
	    cell.setBorder(Rectangle.NO_BORDER);
	    logoTable.addCell(cell); 
	        
	    cell = new PdfPCell(new Phrase());
		cell.setBorder(Rectangle.NO_BORDER);
		logoTable.addCell(cell); 
		    
		cell = new PdfPCell(new Phrase());
		cell.setBorder(Rectangle.NO_BORDER);
		logoTable.addCell(cell); 
        
        font = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
		cell = new PdfPCell(new Phrase("BLACKBOY DETECTIVE AGENCY PVT LTD.", font));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(4);
		cell.setBorder(Rectangle.NO_BORDER);
        logoTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase());
        cell.setBorder(Rectangle.NO_BORDER);
        logoTable.addCell(cell);
       
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.addCell(logoTable);
        table.addCell(nxtTable);
        table.setWidthPercentage(100);
        tableHeight = table.getTotalHeight();
    }
    
    public float getTableHeight() {
        return tableHeight;
    }
    int c=1;
    public void onEndPage(PdfWriter writer, Document document) {
    	System.out.println("document.left() - >"+document.left());
    	System.out.println("document.top() - >"+document.top());
    	System.out.println("document.topMargin() - >"+document.topMargin());
    	System.out.println("tableHeight - >"+tableHeight);
    	PdfContentByte cb = writer.getDirectContent();
    	Font ffont = new Font(Font.FontFamily.TIMES_ROMAN,19, Font.BOLDITALIC);
    	Phrase footer = new Phrase("Page no."+c,ffont);
    	table.writeSelectedRows(0, -1,
                document.left(),
                document.top() + ((document.topMargin() + tableHeight) / 2),
                writer.getDirectContent());
    	ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom()+20 , 0);
    	c++;
    }
}
