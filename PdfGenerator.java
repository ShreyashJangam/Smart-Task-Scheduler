package org.studyeasy;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PdfGenerator {

    public static void main(String[] args) {

        String filePath = "employee_report.pdf";

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Employee Report"));
            document.add(new Paragraph("----------------------"));

            document.add(new Paragraph("ID: 101"));
            document.add(new Paragraph("Name: Shreyash"));
            document.add(new Paragraph("Department: IT"));
            document.add(new Paragraph("Salary: 50000"));

            document.close();

            System.out.println("PDF Created Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}