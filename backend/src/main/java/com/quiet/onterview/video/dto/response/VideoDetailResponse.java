package com.quiet.onterview.video.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDetailResponse {

    private Long videoId;
    private Long myQuestionId;
    private String title;
    private String videoUrl;
    private String thumbnailUrl;
    private String feedback;
    private Boolean bookmark;
}
