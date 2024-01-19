package com.quiet.onterview.video.mapper;

import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.entity.Video;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public VideoDetailResponse videoToVideoDetailResponse(Video video) {
        return VideoDetailResponse.builder()
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .myQuestionId(video.getMyQuestion().getMyQuestionId())
                .bookmark(video.getBookmark())
                .videoUrl(video.getVideoUrl())
                .thumbnailUrl(video.getThumbnailUrl())
                .feedback(video.getFeedback())
                .build();
    }

    public List<VideoInformationResponse> videoListToVideoInformationResponseList(
            Long myQuestionId,
            List<Video> videos
    ) {
        return videos.stream()
                .map(video -> VideoInformationResponse.builder()
                        .videoId(video.getVideoId())
                        .myQuestionId(myQuestionId)
                        .title(video.getTitle())
                        .feedback(video.getFeedback())
                        .thumbnailUrl(video.getThumbnailUrl())
                        .bookmark(video.getBookmark())
                        .build())
                .collect(Collectors.toList());
    }
}
