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

    @GetMapping("/file")
    public ResponseEntity<?> sendFile(@RequestParam("fileName") String fileName)
            throws IOException {
        File file = new File("video/" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition", "attachment" + file.getName());
        String path = file.getPath();
        byte[] body = Files.readAllBytes(Path.of(path));
        return ResponseEntity.ok()
                .headers(headers)
                .body(body);
    }

    private String createUUIDFileName(String extension) {
        return String.format("%s.%s", UUID.randomUUID(), extension);
    }
}
