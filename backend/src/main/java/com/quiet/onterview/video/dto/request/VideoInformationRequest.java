package com.quiet.onterview.video.dto.request;

import com.quiet.onterview.file.dto.request.FileInformationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VideoInformationRequest {

    Long questionId;
    Long videoLength;
    String title;
    FileInformationRequest videoInformation;
    FileInformationRequest thumbnailInformation;
}
