package org.appsquad.research;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.appsquad.bean.RunPayRollBean;
import org.appsquad.pdfhandler.RoundRectangle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
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

public class TableHeader {

	public static final String DEST = "C:\\Users\\somnathd\\Desktop\\table_column.pdf";
	
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
	
	
	
	public static void main(String[] args) throws IOException, DocumentException {
		 File file = new File(DEST);
	     file.getParentFile().mkdirs();
	    // TableHeader tableHeader = new TableHeader();
	    //tableHeader.createPdf(DEST);
	     Document  document = new Document();
	     PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
	     document.open();
	   
	     PdfPTable mainTable = new PdfPTable(4);
	     PdfPCell cell;
	     String[] data = {"1","2","3","4","5","6","7","8","9","10"};
		    
	     System.out.println("Length:"+data.length);
	     for(String str  : data ){
	    	 PdfPTable innertable = new PdfPTable(1);
	    	 cell = new PdfPCell(new Phrase(str));
	    	 cell.setBorder(Rectangle.NO_BORDER);
	    	 innertable.addCell(cell);
	    	 mainTable.addCell(innertable);
	    	 /*System.out.println("Starts loop i = "+i);
	    	  cell = new PdfPCell(new Phrase(data[i]));
		    	 PdfPTable table = new PdfPTable(1);
		    	 table.addCell(cell);
		    	
	    	 if(i+1 <= data.length-1 ){
	    		 cell = new PdfPCell( new Phrase(data[i+1]));
	    		 table.addCell(cell);
	    	 }else{
	    		 cell = new PdfPCell(new Phrase(" "));
	    		 table.addCell(cell);
	    	 }
	    	
	    	mainTable.addCell(table);
	    	 System.out.println("* * * * * * * * * * *After adding to table Ends loop i * * * * * * * * *");*/
	     }
	     document.add(mainTable);
	     document.close();
	}
	
	public static float addHeaderTable(Document document, String day, int page)
	        throws DocumentException {
	        PdfPTable header = new PdfPTable(3);
	        header.setWidthPercentage(100);
	        header.getDefaultCell().setBackgroundColor(BaseColor.BLACK);
	        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
	        Phrase p = new Phrase("Foobar Film Festival", font);
	        header.addCell(p);
	        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	        p = new Phrase(day.toString(), font);
	        header.addCell(p);
	        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	        p = new Phrase(String.format("page %d", page), font);
	        header.addCell(p);
	        document.add(header);
	        return header.getTotalHeight();
	    }
	
	public void createPdf(String filename) throws IOException, DocumentException {
        RunPayRollBean bean = new RunPayRollBean();
        bean.setComapnyName("ABCD");
        bean.setUnitName("UNIT");
        bean.setUnitDesignation("UNIT DESG");
        bean.setYear("2016");
        bean.setMonthName("JANUARY");
        bean.setCurrentDate("28-01-2016");
        
		HeaderTable event = new HeaderTable(bean);
		   // step 1
	    Document  document = new Document(PageSize.LEGAL.rotate(), 65f,5f, 28f + event.getTableHeight(), 5f);
	        // step 2
	    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
	        writer.setPageEvent(event);
	     // step 3
        document.open();
        // step 4
        ArrayList<Integer> intList = new ArrayList<Integer>();
        intList.add(2);   intList.add(4);   intList.add(3);   intList.add(7);   intList.add(9);
        intList.add(5);   intList.add(6);   intList.add(1);   intList.add(8);   intList.add(10);
        intList.add(12);   intList.add(24);   intList.add(23);   intList.add(27);   intList.add(19);
        intList.add(25);   intList.add(16);   intList.add(11);   intList.add(18);   intList.add(100);
        
    	for(Integer count : intList){
    		PdfPTable table = new PdfPTable(1);
    		Font font = new Font(Font.getFamily("HELVETICA"), 78, Font.BOLD);
			PdfPCell cell = new PdfPCell(new Phrase("Count "+count, font));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
    		document.add(table);
           
    	}
            
        // step 5
        document.close();
    }

}
