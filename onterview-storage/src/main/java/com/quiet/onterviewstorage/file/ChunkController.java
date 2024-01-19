package com.quiet.onterviewstorage.file;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class ChunkController {

    private final ChunkService chunkService;

    @GetMapping("/chunk")
    public String chunkUploadPage() {
        return "chunk";
    }

    @ResponseBody
    @PostMapping("/chunk/upload")
    public ResponseEntity<String> chunkUpload(@RequestParam("chunk") MultipartFile file,
            @RequestParam("chunkNumber") int chunkNumber,
            @RequestParam("totalChunks") int totalChunks) throws IOException {
        boolean isDone = chunkService.chunkUpload(file, chunkNumber, totalChunks);

        return isDone ?
                ResponseEntity.ok("File uploaded successfully") :
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }
}
