package com.quiet.onterview.video.controller;

import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.video.dto.request.VideoDeleteRequest;
import com.quiet.onterview.video.dto.request.VideoInformationRequest;
import com.quiet.onterview.video.dto.request.VideoUpdateRequest;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.service.VideoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

    @GetMapping
    public ResponseEntity<List<VideoInformationResponse>> getAllMyVideoByCategory(
            @AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(videoService.loadAllMyVideo(user.getEmail()));
    }

    @PostMapping
    public ResponseEntity<Void> registerVideo(
            @RequestBody VideoInformationRequest videoInformationRequest) {
        videoService.createVideoInformation(videoInformationRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{videoId}")
    public ResponseEntity<Void> updateVideo(
            @PathVariable Long videoId,
            @RequestBody VideoUpdateRequest videoUpdateRequest
    ) {
        videoService.updateVideo(videoId, videoUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteVideo(@RequestBody VideoDeleteRequest videos) {
        videoService.deleteVideo(videos);
        return ResponseEntity.ok().build();
    }
}