package com.quiet.onterviewstorage.file.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FileDto {

    @Getter
    @RequiredArgsConstructor
    public static class FileResponse {

        private final String filePath;
        private final String fileName;
    }

    @Getter
    @RequiredArgsConstructor
    public static class VideoResponse {

        private final String videoUrl;
        private final Long videoLength;
        private final String thumbnailUrl;
    }

    @Getter
    @RequiredArgsConstructor
    public static class VideoRequest {

        private final String username;
        private final String filename;
        private final int chunkNumber;
        private final int endOfChunk;
    }
}
