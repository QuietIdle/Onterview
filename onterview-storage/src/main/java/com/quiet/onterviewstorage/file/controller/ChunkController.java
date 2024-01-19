package com.quiet.onterviewstorage.file.controller;

import com.quiet.onterviewstorage.file.service.ChunkService;
import com.quiet.onterviewstorage.file.dto.FileDto.VideoResponse;
import com.quiet.onterviewstorage.file.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.api.JCodecException;
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
    ) throws IOException, JCodecException {
        Optional<VideoResponse> isDone = chunkService.chunkUpload(file, chunkNumber,
                endOfChunk);

        return isDone.isEmpty() ?
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build() :
                ResponseEntity.ok("File uploaded successfully]");
    }

    @GetMapping("/download")
    public ResponseEntity<?> streamVideo(@RequestParam("filePath") String filePath)
            throws IOException {

        Path path = Path.of(FileUtils.DEFAULT_VIDEO_PATH + "/" + filePath);
        byte[] bytes = Files.readAllBytes(path);

        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(bytes);
    }
}
