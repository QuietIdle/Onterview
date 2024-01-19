package com.quiet.onterviewstorage.file.controller;

import com.quiet.onterviewstorage.file.ChunkService;
import com.quiet.onterviewstorage.file.FileDto.VideoResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.JCodecException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam("chunk") MultipartFile file,
            @RequestParam("chunkNumber") int chunkNumber,
            @RequestParam("endOfChunk") int endOfChunk
    ) throws IOException, JCodecException {
        Optional<VideoResponse> isDone = chunkService.chunkUpload(file, chunkNumber,
                endOfChunk);

        return isDone.isEmpty() ?
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build() :
                ResponseEntity.ok("File uploaded successfully]");
    }
}
