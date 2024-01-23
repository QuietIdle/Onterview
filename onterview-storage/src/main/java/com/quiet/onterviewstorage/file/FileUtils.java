package com.quiet.onterviewstorage.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
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

    public static String getThumbnail(File source) throws IOException, JCodecException {
        File thumbnail = new File(source.getParent(),
                source.getName().split("\\.")[0] + "." + EXTENSION);

        FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));

        frameGrab.seekToSecondPrecise(0);

        Picture picture = frameGrab.getNativeFrame();

        BufferedImage bi = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bi, EXTENSION, thumbnail);

        return thumbnail.getName();
    }

    public static double getVideoLength(File source) throws IOException, JCodecException {
        FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));
        double durationInSeconds = frameGrab.getVideoTrack().getMeta().getTotalDuration();
        log.info("Video length: {} seconds", durationInSeconds);
        return durationInSeconds;
    }
}
