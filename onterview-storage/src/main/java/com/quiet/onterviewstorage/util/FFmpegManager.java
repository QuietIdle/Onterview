package com.quiet.onterviewstorage.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.quiet.onterviewstorage.global.BaseException;
import com.quiet.onterviewstorage.global.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FFmpegManager {

    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;
    private final FileUtils fileUtils;

    public String createThumbnail(Path videoPath, Path imagePath, String filename) {
        // 썸네일 저장할 경로
        if (!videoPath.startsWith(fileUtils.VIDEO_PATH) || !fileUtils.isVideo(String.valueOf(videoPath))) {
            throw new BaseException(ErrorCode.FILE_NOT_FOUND);
        }

        System.out.println(Path.of(fileUtils.DEFAULT_URL, videoPath.toString()));
        try {
            // ffmpeg cli 명령어 생성
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(Path.of(fileUtils.DEFAULT_URL, videoPath.toString()).toString())
                    .overrideOutputFiles(true)
                    .addOutput(Path.of(fileUtils.DEFAULT_URL, imagePath.toString(), filename + ".png").toString())
                    .setFormat("image2")
                    .setFrames(1)
                    .setVideoFrameRate(1)
                    .done();

            // 명령어 실행
            ffmpeg.run(builder);

            log.info(fileUtils.DEFAULT_URL + imagePath + "에 썸네일 생성");
            return String.valueOf(imagePath);
        } catch (Exception e) {
            return fileUtils.DEFAULT_IMAGE;
        }
    }
}