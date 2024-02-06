package com.quiet.onterview.interview.dto.response;

import com.quiet.onterview.file.entity.FileInformation;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InterviewVideoResponse {

    private Long videoId;
    private FileInformation thumbnailUrl;
    private String title;
    private String question;
}