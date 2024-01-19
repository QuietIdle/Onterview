package com.quiet.onterview.video.controller;

import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/upload")
    public ResponseEntity<Void> getUpload() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDetailResponse> getVideoInformation(@PathVariable Long videoId) {
        return ResponseEntity.ok(videoService.loadVideoInformation(videoId));
    }
}
