package com.insa.TeamOpsSystem.report;

import com.insa.TeamOpsSystem.CheckList.CheckList;
import com.insa.TeamOpsSystem.CheckList.CheckListRepository;
import com.insa.TeamOpsSystem.FTraffic.FTrafficRepository;
import com.insa.TeamOpsSystem.FTraffic.Ftraffics;
import com.insa.TeamOpsSystem.exceptions.AlreadyExistException;
import com.insa.TeamOpsSystem.request.Request;
import com.insa.TeamOpsSystem.request.RequestRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PdfService {
    private final FTrafficRepository trafficRepository;
    private final RequestRepository requestRepository;
    private final CheckListRepository checkListRepository;

    public ByteArrayInputStream generatePdf(String userName) {
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
//            trafficTable.addCell("CreatedBy");
//            trafficTable.addCell("CreatedAt");
            trafficTable.addCell("Remark");

            // Fetch all traffic data (example data)
            List<Ftraffics> ftraffics = trafficRepository.findAllBySitesDeletedIsFalseAndCreatedBy(userName);
            int index = 1;
            // Add rows

            for (Ftraffics traffics : ftraffics) {
                trafficTable.addCell(String.valueOf(index++));
                trafficTable.addCell(traffics.getSites() != null ? traffics.getSites().getName() : "");
                trafficTable.addCell(traffics.getTrafficTimeName() != null ? traffics.getTrafficTimeName() : "");
                trafficTable.addCell(traffics.getTimeValues() != null ? traffics.getTimeValues() : "");
//                trafficTable.addCell(traffics.getCreatedBy() != null ? traffics.getCreatedBy() : "");
//                trafficTable.addCell((Cell) (traffics.getCreatedAt() != null ? traffics.getCreatedAt() : ""));
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

    public ByteArrayInputStream generatePdfByDateRange(LocalDate from, LocalDate to, String token) {
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
//            trafficTable.addCell("CreatedBy");
//            trafficTable.addCell("CreatedAt");
            trafficTable.addCell("Remark");

            // Fetch traffic data within specified date range
            List<Ftraffics> ftraffics = trafficRepository.findAllByCreatedAtBetweenAndSitesDeletedIsFalseAndCreatedBy(from.atStartOfDay(), to.plusDays(1).atStartOfDay(), token);
            int index = 1;  // Index counter

            // Add rows
            for (Ftraffics traffics : ftraffics) {
                trafficTable.addCell(String.valueOf(index++));
                trafficTable.addCell(traffics.getSites() != null ? traffics.getSites().getName() : "");
                trafficTable.addCell(traffics.getTrafficTimeName() != null ? traffics.getTrafficTimeName() : "");
                trafficTable.addCell(traffics.getTimeValues() != null ? traffics.getTimeValues() : "");
//                trafficTable.addCell(traffics.getCreatedBy() != null ? traffics.getCreatedBy() : "");
//                trafficTable.addCell((Cell) (traffics.getCreatedAt() != null ? traffics.getCreatedAt() : ""));
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

            Paragraph date = new Paragraph(trafficTimeName + " Reports")
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

            List<Ftraffics> ftraffics = trafficRepository.findAllByTrafficTimeNameAndSitesDeletedIsFalse(trafficTimeName);
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

    public ByteArrayInputStream generatePdfReportByRequesters(String createdBy) {
        try {
            // Create a ByteArrayOutputStream to hold the PDF content
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


            // Add date (initially for "Date: All")
            Paragraph title = new Paragraph("Request:Reports")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setUnderline(1.5f, -1); // This will add an underline with a thickness of 1.5 points

            document.add(title);

            // Add table for traffic data
            Table requestTable = new Table(new float[]{1, 3, 3, 3, 3, 3, 3, 3, 3});
            requestTable.setWidth(UnitValue.createPercentValue(100));
            requestTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            requestTable.addCell("No");
            requestTable.addCell("Phone");
            requestTable.addCell("Email");
            requestTable.addCell("Requester");
            requestTable.addCell("Organization");
            requestTable.addCell("Categories");
            requestTable.addCell("Contact");
            requestTable.addCell("Description");
            requestTable.addCell("Priority");
            List<Request> requestIterable = requestRepository.findAllByCreatedBy(createdBy);
            // Add rows
            int index = 1;

            for (Request request : requestIterable) {
                requestTable.addCell(String.valueOf(index++));
                requestTable.addCell(request.getPhone() != null ? request.getPhone() : "");
                requestTable.addCell(request.getEmail() != null ? request.getEmail() : "");
                requestTable.addCell(request.getRequester() != null ? request.getRequester() : "");
                requestTable.addCell(request.getOrganization() != null ? request.getOrganization() : "");
                requestTable.addCell(request.getCategories() != null ? request.getCategories() : "");
                requestTable.addCell(request.getContact() != null ? request.getContact() : "");
                requestTable.addCell(request.getDescription() != null ? request.getDescription() : "");
                requestTable.addCell(request.getPriority() != null ? request.getPriority() : "");
            }

            // Add table to document
            Div tableDiv = new Div();
            tableDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableDiv.add(requestTable);
            document.add(tableDiv);

            // Close document
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new AlreadyExistException(e.getMessage());
        }
    }

    public ByteArrayInputStream generatePdfRequestsByDateRange(LocalDate from, LocalDate to) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(out);

            // Initialize PDF document
            Document document = new Document(new PdfDocument(writer));

            // Add image
            String imagePath = "\\\\10.10.10.112\\home\\img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);
            // Add date (for specified date range)
            Paragraph date = new Paragraph("Date: " + from.toString() + " - " + to.toString())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setUnderline(1.5f, -1); // This will add an underline with a thickness of 1.5 points

            document.add(date);
            // Add table for requests data
            Table requestTable = new Table(new float[]{1, 3, 3, 3, 3, 3, 3, 3, 3});
            requestTable.setWidth(UnitValue.createPercentValue(100));
            requestTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            requestTable.addCell("No");
            requestTable.addCell("Phone");
            requestTable.addCell("Email");
            requestTable.addCell("Requester");
            requestTable.addCell("Organization");
            requestTable.addCell("Categories");
            requestTable.addCell("Contact");
            requestTable.addCell("Description");
            requestTable.addCell("Priority");

            // Fetch requests data within specified date range

            List<Request> requests = requestRepository.findAllByCreatedAtBetween(from.atStartOfDay(), to.plusDays(1).atStartOfDay());
            int index = 1; // Reset index counter
// Add request data rows
            for (Request request : requests) {
                requestTable.addCell(String.valueOf(index++));
                requestTable.addCell(request.getPhone() != null ? request.getPhone() : "");
                requestTable.addCell(request.getEmail() != null ? request.getEmail() : "");
                requestTable.addCell(request.getRequester() != null ? request.getRequester() : "");
                requestTable.addCell(request.getOrganization() != null ? request.getOrganization() : "");
                requestTable.addCell(request.getCategories() != null ? request.getCategories() : "");
                requestTable.addCell(request.getContact() != null ? request.getContact() : "");
                requestTable.addCell(request.getDescription() != null ? request.getDescription() : "");
                requestTable.addCell(request.getPriority() != null ? request.getPriority() : "");
            }


            // Add table to document
            Div tableDiv = new Div();
            tableDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableDiv.add(requestTable);
            document.add(tableDiv);

            // Close document


            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new AlreadyExistException(e.getMessage());
        }
    }

    //Check List
    public ByteArrayInputStream generatePdfByCheckLists(String createdBy) {
        try {

            // Create a ByteArrayOutputStream to hold the PDF content
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add image to the document (optional)
            String imagePath = "\\\\10.10.10.112\\home\\img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);

            // Add title for the report
            Paragraph title = new Paragraph("Checklist Reports")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(16);
            document.add(title);

            // Add table headers for checklist reports
            Table checklistTable = new Table(new float[]{1, 3, 3, 3, 3, 3, 3, 3});
            checklistTable.setWidth(UnitValue.createPercentValue(100));
            checklistTable.setHorizontalAlignment(HorizontalAlignment.CENTER);


            checklistTable.addHeaderCell(new Cell().add(new Paragraph("No")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Sites")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Link Type")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Download")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Upload")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Created By")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Nbp Total")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Avg NBP")));

            List<CheckList> checkLists = checkListRepository.findAllByCreatedByAndSitesDeletedIsFalse(createdBy);
            int index = 1;
            for (CheckList checklist : checkLists) {
                checklistTable.addCell(String.valueOf(index++));
                checklistTable.addCell(checklist.getSites() != null ? checklist.getSites().getName() : "");
                checklistTable.addCell(checklist.getLinkType() != null ? checklist.getLinkType() : "");
                checklistTable.addCell(checklist.getDownload() != null ? checklist.getDownload() : "");
                checklistTable.addCell(checklist.getUpload() != null ? checklist.getUpload() : "");
                checklistTable.addCell(checklist.getCreatedBy() != null ? checklist.getCreatedBy() : "");
                checklistTable.addCell(String.valueOf(checklist.getNbpTotal()));
                checklistTable.addCell(checklist.getAvgNBP() != null ? checklist.getAvgNBP() : "");
            }
            // Add table to document
            Div tableDiv = new Div();
            tableDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableDiv.add(checklistTable);
            document.add(tableDiv);

            // Close the document
            document.close();

            // Return the PDF as a ByteArrayInputStream
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new AlreadyExistException(e.getMessage());
        }
    }

    public ByteArrayInputStream generatePdfChecklistByDateRange(LocalDate from, LocalDate to, String username) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(out);
            Document document = new Document(new PdfDocument(writer));

            // Add image to the document (optional)
            String imagePath = "\\\\10.10.10.112\\home\\img.png";
            Image img = new Image(ImageDataFactory.create(imagePath));
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);

            // Add date (for specified date range)
            Paragraph date = new Paragraph("Date: " + from.toString() + " - " + to.toString())
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setUnderline(1.5f, -1); // This will add an underline with a thickness of 1.5 points
            document.add(date);

            // Add table for checklist data
            Table checklistTable = new Table(new float[]{1, 3, 3, 3, 3, 3, 3, 3});
            checklistTable.setWidth(UnitValue.createPercentValue(100));
            checklistTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

            checklistTable.addHeaderCell(new Cell().add(new Paragraph("No")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Sites")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Link Type")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Download")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Upload")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Created By")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Nbp Total")));
            checklistTable.addHeaderCell(new Cell().add(new Paragraph("Avg NBP")));

            // Fetch checklist data within specified date range
            List<CheckList> checkLists = checkListRepository.findAllByCreatedAtBetweenAndCreatedByAndSitesDeletedIsFalse(from.atStartOfDay(), to.plusDays(1).atStartOfDay(), username);

            // Add rows to the table
            int index = 1;
            for (CheckList checklist : checkLists) {
                checklistTable.addCell(String.valueOf(index++));
                checklistTable.addCell(checklist.getSites() != null ? checklist.getSites().getName() : "");
                checklistTable.addCell(checklist.getLinkType() != null ? checklist.getLinkType() : "");
                checklistTable.addCell(checklist.getDownload() != null ? checklist.getDownload() : "");
                checklistTable.addCell(checklist.getUpload() != null ? checklist.getUpload() : "");
                checklistTable.addCell(checklist.getCreatedBy() != null ? checklist.getCreatedBy() : "");
                checklistTable.addCell(String.valueOf(checklist.getNbpTotal()));
                checklistTable.addCell(checklist.getAvgNBP() != null ? checklist.getAvgNBP() : "");
            }

            // Add table to document
            Div tableDiv = new Div();
            tableDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableDiv.add(checklistTable);
            document.add(tableDiv);

            // Close document

            document.close();
            // Return the PDF as a ByteArrayInputStream
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new AlreadyExistException(e.getMessage());
        }
    }
}
