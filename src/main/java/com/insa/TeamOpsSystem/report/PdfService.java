package com.insa.TeamOpsSystem.report;

import com.insa.TeamOpsSystem.FTraffic.FTrafficRepository;
import com.insa.TeamOpsSystem.FTraffic.Ftraffics;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
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
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(out);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            // Add image
            String imagePath = "path/img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);

            // Add title
            Paragraph title1 = new Paragraph("INFORMATION NETWORK SECURITY ADMINISTRATION")
                    .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER);
            document.add(title1);

            Paragraph title2 = new Paragraph("DAILY TOTAL TRAFFIC MONITORING CHECKLIST")
                    .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);
            document.add(title2);

            // Add date
            Paragraph date = new Paragraph("Date: all ")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setUnderline(1.5f, -1);
            document.add(date);


            // Add table for traffic data
            Table trafficTable = new Table(new float[]{1, 3, 3, 3, 3});
            trafficTable.setWidth(UnitValue.createPercentValue(100));
            trafficTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            trafficTable.addCell("No");
            trafficTable.addCell("Site");
            trafficTable.addCell("Time");
            trafficTable.addCell("Total Traffic");
            trafficTable.addCell("Remark");
            List<Ftraffics> ftraffics = trafficRepository.findAll();
            // Add rows (example data)
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generatePdfByDateRAnge(LocalDate from, LocalDate to) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(out);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            // Add image
            String imagePath = "path/img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);

            // Add title
            Paragraph title1 = new Paragraph("INFORMATION NETWORK SECURITY ADMINISTRATION")
                    .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER);
            document.add(title1);

            Paragraph title2 = new Paragraph("DAILY TOTAL TRAFFIC MONITORING CHECKLIST")
                    .setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);
            document.add(title2);

            // Add date
            Paragraph date = new Paragraph("Date: " + from.toString() + " - " + to.toString())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setUnderline(1.5f, -1);
            document.add(date);


            // Add table for traffic data
            Table trafficTable = new Table(new float[]{1, 3, 3, 3, 3});
            trafficTable.setWidth(UnitValue.createPercentValue(100));
            trafficTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            trafficTable.addCell("No");
            trafficTable.addCell("Site");
            trafficTable.addCell("Time");
            trafficTable.addCell("Total Traffic");
            trafficTable.addCell("Remark");
            List<Ftraffics> ftraffics = trafficRepository.findAllByCreatedAtBetweenAndSitesDeletedIsFalse(from.atStartOfDay(), to.plusDays(1).atStartOfDay());
            int index = 1;  // Index counter

            // Add rows (example data)
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
