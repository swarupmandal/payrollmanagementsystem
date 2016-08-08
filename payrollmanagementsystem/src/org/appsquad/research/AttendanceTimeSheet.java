package org.appsquad.research;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class AttendanceTimeSheet {
    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
    public static Font subSecFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLUE);
    public static Font tableCellFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    public static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
    public static Font smallFontofnames = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
    public static Font smallFontofcomp = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
    public static Font smallFontofstopped = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
    public static Font smallFontofGPS = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
    public static Font smallWhiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
    public static Font smallFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    public static Font smallHeadersFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
    public static Font FontForAllBold = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);

    public static Font headerBold = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.BOLD);
    public static Font headerBoldData = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.BOLD);

    public class Rotate extends PdfPageEventHelper {
        protected PdfNumber rotation = PdfPage.PORTRAIT;

        public void setRotation(PdfNumber rotation) {
            this.rotation = rotation;
        }

        public void onEndPage(PdfWriter writer, Document document) {
            //writer.addPageDictEntry(PdfName.ROTATE, rotation);
        }
    }

    public static void main(String[] args) throws IOException, DocumentException {
        new AttendanceTimeSheet().createPdf();
    }

    public void createPdf() throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Rectangle pagesize = new Rectangle(842f, 595f);
        Document document = new Document(pagesize, 10f, 10f, 10f, 10f);
        try {
            OutputStream file = new FileOutputStream(new File("E:\\ExamplePDF\\AttSheet+" + new Date().getTime() + ".pdf"));
            PdfWriter writer = PdfWriter.getInstance(document, file);
            Rotate rotation = new Rotate();
            writer.setPageEvent(rotation);
            rotation.setRotation(PdfPage.SEASCAPE);
            document.open();
            Paragraph paraRepName = new Paragraph();
            paraRepName.add(new Paragraph("SHEET", catFont));
            paraRepName.setSpacingAfter(04);
            document.add(paraRepName);
            int pageNo = 1;
            PdfPTable headeTable = headerTable_Main();
            document.add(headeTable);           
            PdfPTable data_HeadeTable = headerTable_Data();
            document.add(data_HeadeTable);
            for (int rows = 1; rows <= 10; rows++) {
                //Here will dynamic data some List
                PdfPTable rdTable = rawDataTable();
                document.add(rdTable);
            }
            document.newPage();
            pageNo = pageNo + 1;
            Paragraph balnkParaSummary = new Paragraph();
            document.add(balnkParaSummary);
            document.close();
            System.out.println("Pdf created successfully..");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private static PdfPTable headerTable_Main() throws DocumentException {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new int[] { 20, 40, 10, 10, 10, 10, 10 });
        PdfPCell cell = new PdfPCell();
        for (int tblrow = 1; tblrow <= 3; tblrow++) {
            String cellHead1 = "";
            String cellHead2 = "";
            String cellHead3 = "";
            String cellHead4 = "";
            String cellHead5 = "";
            String cellHead6 = "";
            String cellHead7 = "";
            if (tblrow == 1) {
                cellHead1 = "Project : ACE";
                cellHead2 = "";
                cellHead3 = "Legend:";
                cellHead4 = "AB = Absent";
                cellHead5 = "R = Rest Day";
                cellHead6 = "O = Off Day";
                cellHead7 = "H = Holi Day";
            } else if (tblrow == 2) {
                cellHead1 = "CLIENT : DOPMO";
                cellHead2 = "";
                cellHead3 = "";
                cellHead4 = "AL = Annual Leave";
                cellHead5 = "EL = Emg Leave";
                cellHead6 = "L1 = ";
                cellHead7 = "L2 = ";
            } else {
                cellHead1 = "Period : ";
                cellHead2 = "";
                cellHead3 = "";
                cellHead4 = "";
                cellHead5 = "";
                cellHead6 = "";
                cellHead7 = "";
            }

            for (int i = 1; i <= 7; i++) {
                if (i == 1) {
                    cell = new PdfPCell(new Phrase(cellHead1, headerBold));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else if (i == 2) {
                    cell = new PdfPCell(new Phrase(cellHead2, headerBold));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else if (i == 3) {
                    cell = new PdfPCell(new Phrase(cellHead3, headerBold));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else if (i == 4) {
                    cell = new PdfPCell(new Phrase(cellHead4, headerBold));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else if (i == 5) {
                    cell = new PdfPCell(new Phrase(cellHead5, headerBold));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else if (i == 6) {
                    cell = new PdfPCell(new Phrase(cellHead6, headerBold));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else {
                    cell = new PdfPCell(new Phrase(cellHead7, headerBold));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                }
                if (i <= 3 || tblrow == 3) {
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0);
                    cell.setBorderWidthTop(0);
                    cell.setBorderWidthBottom(0);
                    cell.setBorderColorBottom(BaseColor.WHITE);
                    cell.setBorderColorLeft(BaseColor.WHITE);
                    cell.setBorderColorRight(BaseColor.WHITE);
                    cell.setBorderColorTop(BaseColor.WHITE);
                }
                cell.setBackgroundColor(BaseColor.WHITE);
                cell.setFixedHeight(8);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setUseAscender(true);
                table.addCell(cell);
            }
        }

        return table;
    }

    private static PdfPTable headerTable_Data() throws DocumentException {
        PdfPTable table = new PdfPTable(33);
        table.setWidthPercentage(100);
        table.setWidths(new int[] { 10, 10, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02,
                02, 02, 02, 02, 02 });
        table.setSpacingBefore(05.0f);  
        PdfPCell cell = new PdfPCell();
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        for (int tblRow = 1; tblRow <= 2; tblRow++) {
            currentDate = new Date();
            cal = Calendar.getInstance();
            cal.setTime(currentDate);
            int dateNo = 1;
            int dateNos = 1;
            for (int i = 1; i <= 33; i++) {
                cal.set(Calendar.DATE, dateNos);
                if (i == 1) {
                    if (tblRow != 1) {
                        cell = new PdfPCell(new Phrase("Name", smallHeadersFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                } else if (i == 2) {
                    if (tblRow != 1) {
                        cell = new PdfPCell(new Phrase("Role", smallHeadersFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                } else {
                    if (tblRow == 1) {
                        if (i >= 3) {
                            cell = new PdfPCell(new Phrase(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US), headerBold));
                            if(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) == "Fri"){
                                cell.setBackgroundColor(BaseColor.BLUE);
                            }else{
                                cell.setBackgroundColor(BaseColor.WHITE);
                            }
                            dateNos = dateNos + 1;
                        }
                    } else {
                        cell = new PdfPCell(new Phrase("" + dateNo, headerBold));
                        if(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) == "Fri"){
                            cell.setBackgroundColor(BaseColor.BLUE);
                        }else{
                            cell.setBackgroundColor(BaseColor.WHITE);
                        }
                        dateNo = dateNo + 1;
                        dateNos = dateNos + 1;
                    }

                }
                cell.setFixedHeight(15);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setUseAscender(true);
                table.addCell(cell);

            }
        }

        return table;
    }

    private static PdfPTable rawDataTable() throws DocumentException {

        PdfPTable table = new PdfPTable(33);
        table.setWidthPercentage(100);
        table.setWidths(new int[] { 10, 10, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02, 02,
                02, 02, 02, 02, 02 });
        table.setSpacingBefore(0.0f);
        String head1;
        String head2;
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        for (int j = 1; j <= 2; j++) {
            currentDate = new Date();
            cal = Calendar.getInstance();
            cal.setTime(currentDate);
            if (j == 1) {
                head1 = "Rajinikanth Developer";
                head2 = "Designation ";
            } else {
                head1 = "";
                head2 = "";
            }
            PdfPCell cell = new PdfPCell();
            int dateNos = 1;
            for (int i = 1; i <= 33; i++) {
                cal.set(Calendar.DATE, dateNos);

                if (i == 1) {
                    if (j == 2) {
                        cell = new PdfPCell(new Phrase("10:12", headerBoldData));
                        dateNos = dateNos + 1;
                    } else {
                        cell = new PdfPCell(new Phrase(head1, headerBoldData));
                    }
                } else if (i == 2) {
                    if (j == 2) {
                        cell = new PdfPCell(new Phrase("10:13", headerBoldData));
                        dateNos = dateNos + 1;
                    } else {
                        cell = new PdfPCell(new Phrase(head2, headerBoldData));
                    }
                } else {
                    if (j == 1) {
                        cell = new PdfPCell(new Phrase("10:11", headerBoldData));
                        dateNos = dateNos + 1;

                    } else {
                        cell = new PdfPCell(new Phrase("08:59", headerBoldData));
                        dateNos = dateNos + 1;
                    }

                }
                if (j == 1 && i <= 2) {
                    cell.setRowspan(2);
                }
                cell.setFixedHeight(15);
                if(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) == "Fri"){
                    cell.setBackgroundColor(BaseColor.BLUE);
                }else{
                    cell.setBackgroundColor(BaseColor.WHITE);
                }
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setUseAscender(true);
                table.addCell(cell);

            }

        }

        return table;
    }
}