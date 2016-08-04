package org.appsquad.pdfhandler;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderTable extends PdfPageEventHelper {
    protected PdfPTable table;
    protected float tableHeight;
    public HeaderTable() {
        table = new PdfPTable(1);
        table.setTotalWidth(523);
        table.setLockedWidth(true);
        table.addCell("Header row 1");
        table.addCell("Header row 2");
        table.addCell("Header row 3");
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
