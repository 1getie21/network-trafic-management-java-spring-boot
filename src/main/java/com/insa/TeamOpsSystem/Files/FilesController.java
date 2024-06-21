package com.insa.TeamOpsSystem.Files;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://10.10.10.112:8088"})
public class FilesController {
    private final FilesStorageService storageService;

    @PostMapping()
    public Object uploadFile(@RequestParam("file") MultipartFile file ) {
        try {
            return storageService.save(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }


    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
        try {
            Resource resource = storageService.load(filename);
            String contentType = null;
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }


    @DeleteMapping("/{filename:.+}")
    @ResponseBody
    public void deleteFileByFileName(@PathVariable String filename) {
        storageService.deleteByFileName(filename);

    }

    @DeleteMapping()
    @ResponseBody
    public void deleteFile() {
        storageService.deleteAll();
    }
}
