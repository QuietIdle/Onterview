package com.quiet.onterviewstorage.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FileDto {

    @Getter
    @RequiredArgsConstructor
    public static class VideoResponse {

        private final String videoUrl;
        private final Long videoLength;
        private final String thumbnailUrl;
    }

}
