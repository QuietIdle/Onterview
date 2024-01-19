package com.quiet.onterview.video.service;

import com.quiet.onterview.video.dto.request.VideoInformationRequest;
import com.quiet.onterview.video.dto.request.VideoUpdateRequest;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import java.util.List;

public interface VideoService {
    VideoDetailResponse loadVideoInformation(Long videoId);
    List<VideoInformationResponse> loadVideoInformationByMyQuestion(Long myQuestionId);
    void registerVideo(VideoInformationRequest videoInformationRequest);
    void updateVideo(Long videoId, VideoUpdateRequest videoUpdateRequest);
    void deleteVideo(Long videoId);
}
