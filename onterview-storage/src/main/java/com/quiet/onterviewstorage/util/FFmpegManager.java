package com.quiet.onterviewstorage.util;

import com.quiet.onterviewstorage.file.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public double getDuration(String sourcePath) throws IOException {
        // 영상 경로
        Path videoPath = Paths.get(sourcePath);

        // 영상 메타데이터 조회
        FFmpegProbeResult probeResult = ffprobe.probe(videoPath.toString());

        // 영상 길이 추출
        FFmpegStream videoStream = probeResult.getStreams().get(0);
        return videoStream.duration;
    }
}