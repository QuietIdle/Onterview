package com.quiet.onterviewstorage.file.service;

import com.quiet.onterviewstorage.file.dto.FileDto;
import com.quiet.onterviewstorage.file.dto.ResourceDto;
import com.quiet.onterviewstorage.util.FFmpegManager;
import com.quiet.onterviewstorage.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChunkService {

    private final FFmpegManager fFmpegManager;
    private final FileUtils fileUtils;

    public boolean chunkUpload(MultipartFile file, FileDto.VideoRequest request)
            throws IOException {
        int chunkNumber = request.getChunkNumber();
        int endOfChunk = request.getEndOfChunk();
        String filename = request.getFilename();
        String username = request.getUsername();

        Path path = createFolder(username, filename);
        saveTempFile(file, chunkNumber, path);

        // 파일이 전송중인 경우
        if (endOfChunk == 0) {
            return false;
        }

        Path outputFilePath = mergeTempFile(file, path, filename, chunkNumber);
        String outputFilename = String.valueOf(outputFilePath);
        log.info("File uploaded successfully filename: " + outputFilename);

        fFmpegManager.createThumbnail(outputFilename);

        return true;
    }

    public Optional<ResourceDto> getStreamResource(HttpHeaders headers, String filename)
            throws IOException {
        Path path = Paths.get(fileUtils.VIDEO_PATH, filename);

        Resource resource = new FileSystemResource(path);

        long chunkSize = 1024 * 1024;
        long contentLength = resource.contentLength();

        HttpRange httpRange = headers.getRange().stream().findFirst()
                .orElse(HttpRange.createByteRange(0, contentLength - 1));

        long rangeLength = calculateRangeLength(httpRange, contentLength, chunkSize);
        long rangeStart = httpRange.getRangeStart(contentLength);

        log.debug("contentLength " + contentLength);
        log.debug("rangeStart: " + rangeStart);
        if (rangeStart > contentLength) {
            return Optional.empty();
        }

        ResourceRegion region = new ResourceRegion(resource, httpRange.getRangeStart(contentLength),
                rangeLength);

        return Optional.of(new ResourceDto(
                MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM),
                String.valueOf(path),
                region
        ));
    }

    public void delete(String fileName) throws IOException {
        Path path = Path.of(fileUtils.VIDEO_PATH, fileName);
        File file = new File(String.valueOf(path));

        if (file.exists()) {
            Files.delete(path);
        }
    }

    private static Path mergeTempFile(MultipartFile file, Path path, String filename,
            int chunkNumber)
            throws IOException {
        Path outputFilePath = Path.of(String.valueOf(path), filename + ".mkv");
        Files.createFile(outputFilePath);

        for (int number = 1; number <= chunkNumber; number++) {
            Path chunkFile = Paths.get(String.valueOf(path),
                    file.getOriginalFilename() + ".part" + number);
            Files.write(outputFilePath, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
            Files.delete(chunkFile);
        }
        return outputFilePath;
    }

    private static void saveTempFile(MultipartFile file, int chunkNumber, Path path)
            throws IOException {
        // 임시 저장 파일 이름
        String tempFilename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path tempFilePath = Paths.get(String.valueOf(path), tempFilename);
        // 임시 저장
        Files.write(tempFilePath, file.getBytes());
        log.debug(tempFilename, "임시 파일 생성");
    }

    private Path createFolder(String username, String filename) {
        Path path = Path.of(fileUtils.VIDEO_PATH, username, filename);
        File dir = new File(String.valueOf(path));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    private long calculateRangeLength(HttpRange httpRange, long contentLength, long chunkSize) {
        long start = httpRange.getRangeStart(contentLength);
        long end = httpRange.getRangeEnd(contentLength);
        return Long.min(chunkSize, end - start + 1);
    }
}