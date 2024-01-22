package com.quiet.onterview.video.dto.response;

import com.quiet.onterview.file.dto.response.FileInformationResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoInformationResponse {

    private Long videoId;
    private Long myQuestionId;
    private String title;
    private FileInformationResponse thumbnailUrl;
    private String feedback;
    private Boolean bookmark;
}
