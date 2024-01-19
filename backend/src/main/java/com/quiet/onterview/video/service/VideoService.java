package com.quiet.onterview.video.service;

import com.quiet.onterview.video.dto.response.VideoDetailResponse;

public interface VideoService {
    VideoDetailResponse loadVideoInformation(Long videoId);
}
