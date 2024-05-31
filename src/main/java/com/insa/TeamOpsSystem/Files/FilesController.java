package com.insa.TeamOpsSystem.Files;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {
    private final FilesStorageService storageService;

    @PostMapping()
    @ResponseBody
    public Object uploadFile(@RequestParam("file") MultipartFile file
            , @RequestParam("file2") MultipartFile file2 ) {
        try {
            return storageService.save(file, file2);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }




    @GetMapping("/{filename:.+}")
    @ResponseBody
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
