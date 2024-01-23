package com.quiet.onterviewstorage.file;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileUtils {

    private static final String EXTENSION = "png";
    @Value("${image.path}")
    public String IMAGE_PATH;
    @Value("${video.path}")
    public String VIDEO_PATH;
    @Value("${root.path}")
    public String DEFAULT_URL;
    @Value("${default-image.path}")
    public String DEFAULT_IMAGE;

    public String createUUIDFileName(String extension) {
        return String.format("%s.%s", UUID.randomUUID(), extension);
    }
}