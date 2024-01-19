package com.quiet.onterview.video.mapper;

import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.entity.Video;
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
}
