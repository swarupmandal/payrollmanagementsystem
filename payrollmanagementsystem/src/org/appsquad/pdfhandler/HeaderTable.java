package org.appsquad.pdfhandler;

import org.appsquad.bean.RunPayRollBean;

import utility.PdfPaySlipGenerator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderTable extends PdfPageEventHelper {
    protected PdfPTable table;
    protected float tableHeight;
    public HeaderTable(RunPayRollBean bean) throws DocumentException {
        table = new PdfPTable(1);
        table.setTotalWidth(523);
        table.setLockedWidth(true);
        PdfPTable nxtTable = new PdfPTable(2);
		nxtTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		nxtTable.setWidthPercentage(60);
		nxtTable.addCell("SALARY SHEET");
		nxtTable.addCell(bean.getComapnyName()+"\n"+bean.getUnitName()+"\n"+
				bean.getMonthName()+"      "+bean.getYear()+"     "+bean.getCurrentDate()+"\n"+
				bean.getUnitDesignation());
		
		PdfPTable logoTable = new PdfPTable(2);
		logoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
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
        
        logoTable.addCell("BLACKBOY DETECTIVE AGENCY PVT LTD.");
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
    
    public void onEndPage(PdfWriter writer, Document document) {
        table.writeSelectedRows(0, -1,
                document.left(),
                document.top() + ((document.topMargin() + tableHeight) / 2),
                writer.getDirectContent());
    }
}
