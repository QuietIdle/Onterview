package com.quiet.onterviewstorage.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Slf4j
public class FileController {

    @PostMapping("/file")
    public ResponseEntity<String> imageUpload(@RequestPart("file") MultipartFile image)
            throws IOException {
        log.info(image.getOriginalFilename());
        String uuidFileName = createUUIDFileName(image.getOriginalFilename().split("\\.")[1]);
        image.transferTo(Path.of("image/" + uuidFileName));
        return ResponseEntity.ok(uuidFileName);
    }

    private String createUUIDFileName(String extension) {
        return String.format("%s.%s", UUID.randomUUID(), extension);
    }
}
