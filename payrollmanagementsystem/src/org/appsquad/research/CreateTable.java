package org.appsquad.research;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreateTable {

	/** The resulting PDF file. */
    public static final String RESULT = "C:\\Users\\somnathd\\Desktop\\first_table.pdf";

    /**
     * Main method.
     * 
     * @param args
     *            no arguments needed
     * @throws DocumentException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, DocumentException {
        new CreateTable().createPdf(RESULT);
    }
    
    /**
     * Creates a PDF with information about the movies
     * 
     * @param filename
     *            the name of the PDF file that will be created.
     * @throws DocumentException
     * @throws IOException
     */
    public void createPdf(String filename) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        createCustomTables(document);
        // step 5
        document.close();
    }

    public static void createCustomTables(Document document) throws DocumentException {
        String[] data = { "1", "2", "3", "4", "5", "6", "7", "8" };
        PdfPTable table = null;
        int arrayLength = data.length;
     //   int mod3 = data.length % 3;
        int mod9 = data.length % 9;
      //  int iterCounter = (mod3 == 0) ? arrayLength : arrayLength + (3 - mod3);
        int iterCounter = (mod9 == 0) ? arrayLength : arrayLength + (9 - mod9);
        
        System.out.println(iterCounter);

        for (int i = 0; i <= iterCounter; i++) {
            String string = (arrayLength > i) ? data[i] : "";
           // if (i % 3 == 0) {
             if (i % 9 == 0) {	
              //  table = new PdfPTable(3);
            	table = new PdfPTable(9);
                document.newPage();
            }
            PdfPCell cell = new PdfPCell(new Phrase(string));
            table.addCell(cell);
            document.add(table);
        }
        return;
    }

}
