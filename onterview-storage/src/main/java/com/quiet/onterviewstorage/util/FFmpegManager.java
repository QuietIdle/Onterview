package com.quiet.onterviewstorage.util;

import com.quiet.onterviewstorage.file.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FFmpegManager {

    private final FFmpeg ffmpeg;
    private final FileUtils fileUtils;

    public String getThumbnail(String sourcePath) {
        // 썸네일 저장할 경로
        final String outputPath = sourcePath.split("\\.")[0] + ".png";

        try {
            // ffmpeg cli 명령어 생성
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(fileUtils.DEFAULT_URL + sourcePath)
                    .overrideOutputFiles(true)
                    .addOutput(fileUtils.DEFAULT_URL + outputPath)
                    .setFormat("image2")
                    .setFrames(1)
                    .setVideoFrameRate(1)
                    .done();

            // 명령어 실행
            ffmpeg.run(builder);
            return outputPath;
        } catch (Exception e) {
            return fileUtils.DEFAULT_IMAGE;
        }
    }
}