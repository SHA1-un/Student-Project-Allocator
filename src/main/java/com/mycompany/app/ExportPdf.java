package com.mycompany.app;
import java.io.FileOutputStream;
import java.util.Date;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.stream.*;

public class ExportPdf {

private static final int NUMBER_STUDENTS = 30;

    /**
     * Builds the table of students and projects using the itext API and then
     * saves it to a pdf file.
     * @param students
     * @param projects
     * @return boolean
     */
    public static boolean exportPdf(String[] students, String[] projects) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("AllocationOutput.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory. TIMES_ROMAN, 12, BaseColor.BLACK);
            PdfPTable table = new PdfPTable(2);
            addTableHeader(table);

            for (int i = 0; i < NUMBER_STUDENTS; i++) {
                addRow(table, students[i], projects[i]);
            }
            document.add(table);
            document.close();
            return true;

        } catch (Exception e) {
          //System.out.println(e);
          return false;
        }
    }

    /**
     * Add a table header and bind every cell to the respective col.
     * @param table
     */
    private static void addTableHeader(PdfPTable table) {
    	    Stream.of("Student name", "Project name")
    	      .forEach(columnTitle -> {
    	        PdfPCell header = new PdfPCell();
    	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	        header.setBorderWidth(2);
    	        header.setPhrase(new Phrase(columnTitle));
    	        table.addCell(header);
    	    });
    }

    /**
     * Add the student and project name to the cells in the row .
     * @param table
     * @param student
     * @param projName
     */
    private static void addRow(PdfPTable table, String student, String projName) {
        table.addCell(student);
        table.addCell(projName);
    }
}
