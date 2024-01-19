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

@Slf4j
public class FileUtils {

    private static final String EXTENSION = "png";
    public static final String DEFAULT_IMAGE_PATH = "image";
    public static final String DEFAULT_VIDEO_PATH = "video";

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
