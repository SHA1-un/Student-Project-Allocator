package com.mycompany.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExportPdfTest {
    // Should export without any problem and not throw an exception.
    @Test
    public void testExportPdfOne () {
        String[] students = {"Karlee Rosario","Addisyn Mcconnell","Audrey Pitts",
        "Mike Bonilla","Theodore Mclean","Marlee Ortiz","Nathanael Miranda","Grady Weeks",
        "Makenna Avila","Kyler Berry","Carissa Haley","Elise Santana","Alannah Aguirre",
        "Kamren Ryan","Rhys Nash","Darion Hernandez","Raquel Mueller","Walker Ingram",
        "Marlon Pennington","Caylee Dunn","Averi Salazar","Nathaly Higgins",
        "Brayan Mckenzie","Jamari Mckenzie","Kinley Lam","Ashly Riddle","Jennifer Keller",
        "Jadiel Gaines","Alyssa Vazquez","Brenda Chambers"};
        String[] projects = {"Project1","Project2","Project3","Project4","Project5",
        "Project6","Project7","Project8","Project9","Project10","Project11","Project12",
        "Project13","Project14","Project15","Project16","Project17","Project18",
        "Project19","Project20","Project21","Project22","Project23","Project24",
        "Project25","Project26","Project27","Project28","Project29","Project30"};

        assertEquals(true, ExportPdf.exportPdf(students, projects));
    }
    // Must throw an exception and should be caught.
    @Test
    public void testExportPdfTwo () {
        String[] students = {"Karlee Rosario","Addisyn Mcconnell","Audrey Pitts",
        "Mike Bonilla","Theodore Mclean","Marlee Ortiz","Nathanael Miranda","Grady Weeks",
        "Makenna Avila","Kyler Berry","Carissa Haley","Elise Santana","Alannah Aguirre",
        "Kamren Ryan","Rhys Nash","Darion Hernandez","Raquel Mueller","Walker Ingram",
        "Marlon Pennington","Caylee Dunn","Averi Salazar","Nathaly Higgins",
        "Brayan Mckenzie","Jamari Mckenzie","Kinley Lam","Ashly Riddle","Jennifer Keller",
        "Jadiel Gaines","Alyssa Vazquez"};
        String[] projects = {"Project1","Project2","Project3","Project4","Project5",
        "Project6","Project7","Project8","Project9","Project10","Project11","Project12",
        "Project13","Project14","Project15","Project16","Project17","Project18",
        "Project19","Project20","Project21","Project22","Project23","Project24",
        "Project25","Project26","Project27","Project28","Project29","Project30"};

        assertEquals(false, ExportPdf.exportPdf(students, projects));
    }
}
