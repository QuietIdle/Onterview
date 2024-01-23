package com.quiet.onterviewstorage.file.service;

import com.quiet.onterviewstorage.file.dto.FileDto.VideoResponse;
import com.quiet.onterviewstorage.file.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.api.JCodecException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChunkService {

    private final FileUtils fileUtils;

    public Optional<VideoResponse> chunkUpload(MultipartFile file, int chunkNumber, int totalChunks)
            throws IOException, JCodecException {
        File dir = new File(fileUtils.VIDEO_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 임시 저장 파일 이름
        String filename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path filePath = Paths.get(fileUtils.VIDEO_PATH, filename);
        // 임시 저장
        Files.write(filePath, file.getBytes());

        // 파일이 전송중인 경우
        if (totalChunks == 0) {
            return Optional.empty();
        }

        String[] split = file.getOriginalFilename().split("\\.");
        String outputFilename = UUID.randomUUID() + ".mp4";
        Path outputFile = Paths.get(fileUtils.VIDEO_PATH, outputFilename);
        Files.createFile(outputFile);

        // 임시 파일들을 하나로 합침
        for (int i = 1; i <= chunkNumber; i++) {
            Path chunkFile = Paths.get(fileUtils.VIDEO_PATH, file.getOriginalFilename() + ".part" + i);
            Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
            Files.delete(chunkFile);
        }

        log.info("File uploaded successfully");
        File thumbnail = new File(fileUtils.VIDEO_PATH, outputFilename);
        String thumbnailName = FileUtils.getThumbnail(thumbnail);
        long duration = (long) FileUtils.getVideoLength(thumbnail);
        log.info(thumbnailName, duration);

        return Optional.of(new VideoResponse(
                String.valueOf(outputFile.getFileName()),
                duration,
                thumbnailName
        ));
    }
}