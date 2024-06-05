package com.insa.TeamOpsSystem.report;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;


@RestController
@RequiredArgsConstructor

@RequestMapping("/api/pdf")
@CrossOrigin(origins = {"http://localhost:3000", "http://10.10.10.112:8088"})
public class PdfController {
    private final PdfService pdfService;

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfReport() {
        ByteArrayInputStream bis = pdfService.generatePdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/{from}/{to}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfReportByDateRAnge(
            @PathVariable("from")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from
            , @PathVariable("to")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        ByteArrayInputStream bis = pdfService.generatePdfByDateRAnge(from,to);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
