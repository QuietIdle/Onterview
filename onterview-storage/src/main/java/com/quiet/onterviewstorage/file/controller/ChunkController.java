package com.quiet.onterviewstorage.file.controller;

import com.quiet.onterviewstorage.file.dto.FileDto.VideoResponse;
import com.quiet.onterviewstorage.file.dto.ResourceDto;
import com.quiet.onterviewstorage.file.service.ChunkService;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
@RequestMapping("/api/chunk")
@Slf4j
public class ChunkController {

    private final ChunkService chunkService;

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<String> chunkUpload(
            @RequestPart("chunk") MultipartFile file,
            @RequestParam("chunkNumber") int chunkNumber,
            @RequestParam("endOfChunk") int endOfChunk
    ) throws IOException {
        Optional<VideoResponse> isDone = chunkService.chunkUpload(file, chunkNumber,
                endOfChunk);

        return isDone.isEmpty() ?
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build() :
                ResponseEntity.ok("File uploaded successfully]");
    }

    @GetMapping("/stream/{filename}")
    public ResponseEntity<ResourceRegion> streamVideo(
            @RequestHeader HttpHeaders headers,
            @PathVariable String filename
    ) throws IOException {
        ResourceDto response = chunkService.getStreamResource(headers,
                filename);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .contentType(response.getMediaType())
                .header("Accept-Ranges", "bytes")
                .eTag(response.getPath())
                .body(response.getRegion());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("fileName") String fileName) throws IOException {
        chunkService.delete(fileName);

        return ResponseEntity.noContent().build();
    }
}