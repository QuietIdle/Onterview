package com.quiet.onterviewstorage.file.service;

import com.quiet.onterviewstorage.file.dto.FileDto.VideoResponse;
import com.quiet.onterviewstorage.file.dto.ResourceDto;
import com.quiet.onterviewstorage.util.FFmpegManager;
import com.quiet.onterviewstorage.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;
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

    public Optional<VideoResponse> chunkUpload(MultipartFile file, int chunkNumber, int endOfChunk)
            throws IOException {
        File dir = new File(fileUtils.VIDEO_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 임시 저장 파일 이름
        String tempFilename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path tempFilePath = Paths.get(fileUtils.VIDEO_PATH, tempFilename);
        // 임시 저장
        Files.write(tempFilePath, file.getBytes());

        // 파일이 전송중인 경우
        if (endOfChunk == 0) {
            return Optional.empty();
        }

        log.info("모든 청크 받기 완료");

        String outputFilename = UUID.randomUUID() + ".mkv";
        Path outputFile = Paths.get(fileUtils.VIDEO_PATH, outputFilename);
        Files.createFile(outputFile);

        // 임시 파일들을 하나로 합침
        for (int i = 1; i <= chunkNumber; i++) {
            Path chunkFile = Paths.get(fileUtils.VIDEO_PATH,
                    file.getOriginalFilename() + ".part" + i);
            Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
            Files.delete(chunkFile);
        }

        log.info("File uploaded successfully filename: " + outputFilename);
        String thumbnail = fFmpegManager.getThumbnail(outputFilename);
        long videoLength = (long) fFmpegManager.getDuration(outputFilename);

        return Optional.of(new VideoResponse(
                String.valueOf(outputFile.getFileName()),
                videoLength,
                thumbnail
        ));
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
        log.info("contentLength " + contentLength);
        log.info("rangeStart: " + rangeStart);

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

    private long calculateRangeLength(HttpRange httpRange, long contentLength, long chunkSize) {
        long start = httpRange.getRangeStart(contentLength);
        long end = httpRange.getRangeEnd(contentLength);
        return Long.min(chunkSize, end - start + 1);
    }

    public void delete(String fileName) throws IOException {
        Path path = Path.of(fileUtils.VIDEO_PATH, fileName);
        File file = new File(String.valueOf(path));

        if (file.exists()) {
            Files.delete(path);
        }
    }
}