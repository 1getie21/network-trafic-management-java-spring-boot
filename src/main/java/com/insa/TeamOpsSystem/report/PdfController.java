package com.insa.TeamOpsSystem.report;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<InputStreamResource> generatePdf(
            @RequestParam ("userName") String username  ) {
        ByteArrayInputStream bis = pdfService.generatePdf(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=requests_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/{from}/{to}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfReportByDateRAnge(
            @PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam("userName") String username ) {

        ByteArrayInputStream bis = pdfService.generatePdfByDateRange(from, to,  username);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/{trafficTimeName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfReportByTrafficTimeName(
            @PathVariable("trafficTimeName") String trafficTimeName,
            @RequestParam ("userName") String username){
        ByteArrayInputStream bis = pdfService.generatePdfByTrafficTimeName(trafficTimeName, username);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/request/{from}/{to}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfRequestsByDateRange(
            @PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam ("userName") String username) {
        ByteArrayInputStream bis = pdfService.generatePdfRequestsByDateRange(from, to, username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=request.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/request", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfReportByRequesters(
            @RequestParam ("userName") String username ) {
        ByteArrayInputStream bis = pdfService.generatePdfReportByRequesters(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=request_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/check_list/{from}/{to}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfChecklistByDateRange(
            @PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam ("userName") String username ) {
        ByteArrayInputStream bis = pdfService.generatePdfChecklistByDateRange(from, to,username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/check_list", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfByCheckLists(@RequestParam ("userName") String username ) {
        ByteArrayInputStream bis = pdfService.generatePdfByCheckLists(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=checklist_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/failed-traffics/{from}/{to}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfFailedTrafficByDateRange(
            @PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam("userName") String username) {
        ByteArrayInputStream bis = pdfService.generatePdfFailedTrafficByDateRange(from, to,username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/failed-traffics", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfByFailedTraffics(
            @RequestParam("userName") String username) {
        ByteArrayInputStream bis = pdfService.generatePdfByFailedTraffics(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=failed-traffic_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/sixmclist/{from}/{to}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfSixCListByByDateRange(
            @PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam("userName") String username) {
        ByteArrayInputStream bis = pdfService.generatePdfSixCListByByDateRange(from, to,username);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/sixmclist", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdfBySixCList(
            @RequestParam("userName") String username) {
        ByteArrayInputStream bis = pdfService.generatePdfBySixCList(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=sixmclist_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}





