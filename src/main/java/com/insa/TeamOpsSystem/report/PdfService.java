package com.insa.TeamOpsSystem.report;

import com.insa.TeamOpsSystem.FTraffic.FTrafficRepository;
import com.insa.TeamOpsSystem.FTraffic.Ftraffics;
import com.insa.TeamOpsSystem.exceptions.AlreadyExistException;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PdfService {
    private final FTrafficRepository trafficRepository;

    public ByteArrayInputStream generatePdf() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(out);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            // Add image
            String imagePath = "\\\\10.10.10.112\\home\\img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);

            // Add title
//            Paragraph title1 = new Paragraph("INFORMATION NETWORK SECURITY ADMINISTRATION")
//                    .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER);
//            document.add(title1);
//
//            Paragraph title2 = new Paragraph("DAILY TOTAL TRAFFIC MONITORING CHECKLIST")
//                    .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);
//            document.add(title2);

            // Add date (initially for "Date: All")
            Paragraph date = new Paragraph("Date: All")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setUnderline(1.5f, -1); // This will add an underline with a thickness of 1.5 points

            document.add(date);

            // Add table for traffic data
            Table trafficTable = new Table(new float[]{1, 3, 3, 3, 3});
            trafficTable.setWidth(UnitValue.createPercentValue(100));
            trafficTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            trafficTable.addCell("No");
            trafficTable.addCell("Site");
            trafficTable.addCell("Time");
            trafficTable.addCell("Total Time Traffic");
            trafficTable.addCell("Remark");

            // Fetch all traffic data (example data)
            List<Ftraffics> ftraffics = trafficRepository.findAll();
            // Add rows
            for (Ftraffics traffics : ftraffics) {
                trafficTable.addCell(traffics.getId() != null ? traffics.getId().toString() : "");
                trafficTable.addCell(traffics.getSites() != null ? traffics.getSites().getName() : "");
                trafficTable.addCell(traffics.getTrafficTimeName() != null ? traffics.getTrafficTimeName() : "");
                trafficTable.addCell(traffics.getTimeValues() != null ? traffics.getTimeValues() : "");
                trafficTable.addCell(traffics.getRemark() != null ? traffics.getRemark() : "");
            }

            // Add table to document
            Div tableDiv = new Div();
            tableDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableDiv.add(trafficTable);
            document.add(tableDiv);

            // Close document
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new AlreadyExistException(e.getMessage());
        }
    }

    public ByteArrayInputStream generatePdfByDateRange(LocalDate from, LocalDate to) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(out);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            // Add image
            String imagePath = "\\\\10.10.10.112\\home\\img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);

            // Add title
//            Paragraph title1 = new Paragraph("INFORMATION NETWORK SECURITY ADMINISTRATION")
//                    .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER);
//            document.add(title1);
//
//            Paragraph title2 = new Paragraph("DAILY TOTAL TRAFFIC MONITORING CHECKLIST")
//                    .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);
//            document.add(title2);

            // Add date (for specified date range)
            Paragraph date = new Paragraph("Date: " + from.toString() + " - " + to.toString())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setUnderline(1.5f, -1); // This will add an underline with a thickness of 1.5 points

            document.add(date);

            // Add table for traffic data
            Table trafficTable = new Table(new float[]{1, 3, 3, 3, 3});
            trafficTable.setWidth(UnitValue.createPercentValue(100));
            trafficTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            trafficTable.addCell("No");
            trafficTable.addCell("Site");
            trafficTable.addCell("Time");
            trafficTable.addCell("Total Time Traffic");
            trafficTable.addCell("Remark");

            // Fetch traffic data within specified date range
            List<Ftraffics> ftraffics = trafficRepository.findAllByCreatedAtBetweenAndSitesDeletedIsFalse(from.atStartOfDay(), to.plusDays(1).atStartOfDay());
            int index = 1;  // Index counter

            // Add rows
            for (Ftraffics traffics : ftraffics) {
                trafficTable.addCell(String.valueOf(index++));
                trafficTable.addCell(traffics.getSites() != null ? traffics.getSites().getName() : "");
                trafficTable.addCell(traffics.getTrafficTimeName() != null ? traffics.getTrafficTimeName() : "");
                trafficTable.addCell(traffics.getTimeValues() != null ? traffics.getTimeValues() : "");
                trafficTable.addCell(traffics.getRemark() != null ? traffics.getRemark() : "");
            }

            // Add table to document
            Div tableDiv = new Div();
            tableDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableDiv.add(trafficTable);
            document.add(tableDiv);

            // Close document
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new AlreadyExistException(e.getMessage());
        }
    }
    public ByteArrayInputStream generatePdfByTrafficTimeName(String trafficTimeName) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(out);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            // Add image
            String imagePath = "\\\\10.10.10.112\\home\\img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);
            Paragraph date = new Paragraph(trafficTimeName+" Reports")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setUnderline(1.5f, -1);
            document.add(date);

            // Add table for traffic data
            Table trafficTable = new Table(new float[]{1, 3, 3, 3, 3});
            trafficTable.setWidth(UnitValue.createPercentValue(100));
            trafficTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            trafficTable.addCell("No");
            trafficTable.addCell("Site");
            trafficTable.addCell("Time");
            trafficTable.addCell("Total Time Traffic");
            trafficTable.addCell("Remark");

             List<Ftraffics> ftraffics = trafficRepository.findAllByTrafficTimeName( trafficTimeName);
            int index = 1;  // Index counter

            // Add rows
            for (Ftraffics traffics : ftraffics) {
                trafficTable.addCell(String.valueOf(index++));
                trafficTable.addCell(traffics.getSites() != null ? traffics.getSites().getName() : "");
                trafficTable.addCell(traffics.getTrafficTimeName() != null ? traffics.getTrafficTimeName() : "");
                trafficTable.addCell(traffics.getTimeValues() != null ? traffics.getTimeValues() : "");
                trafficTable.addCell(traffics.getRemark() != null ? traffics.getRemark() : "");
            }

            // Add table to document
            Div tableDiv = new Div();
            tableDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableDiv.add(trafficTable);
            document.add(tableDiv);

            // Close document
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new AlreadyExistException(e.getMessage());
        }
    }

}
