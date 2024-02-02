package com.quiet.onterview.video.mapper;

import com.quiet.onterview.file.mapper.FileInformationMapper;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.video.dto.request.VideoInformationRequest;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.entity.Video;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final FileInformationMapper fileInformationMapper;

    public VideoDetailResponse videoToDetailResponse(Video video) {
        return VideoDetailResponse.builder()
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .thumbnailUrl(fileInformationMapper.fileInformationToResponse(video.getThumbnailUrl()))
                .feedback(video.getFeedback())
                .bookmark(video.getBookmark())
                .myQuestionId(video.getMyQuestion().getMyQuestionId())
                .videoUrl(fileInformationMapper.fileInformationToResponse(video.getVideoUrl()))
                .build();
    }

    public Video videoInformationToEntity(VideoInformationRequest videoInformationRequest, MyQuestion myQuestion) {
        return Video.builder()
                .thumbnailUrl(fileInformationMapper.fileInformationRequestToEntity(
                        videoInformationRequest.getThumbnailInformation()))
                .videoUrl(fileInformationMapper.fileInformationRequestToEntity(videoInformationRequest.getVideoInformation()))
                .title(videoInformationRequest.getTitle())
                .bookmark(Boolean.FALSE)
                .videoLength(videoInformationRequest.getVideoLength())
                .myQuestion(myQuestion)
                .build();
    }
    public List<VideoInformationResponse> allVideoToInformationResponse(List<Video> videos) {
        return videos.stream().map(this::videoToInformationResponse).collect(Collectors.toList());
    }

    public VideoInformationResponse videoToInformationResponse(Video video) {
        return VideoInformationResponse.builder()
                .videoId(video.getVideoId())
                .myQuestionId(video.getMyQuestion().getMyQuestionId())
                .title(video.getTitle())
                .thumbnailUrl(fileInformationMapper.imageInformationToResponse(video.getThumbnailUrl()))
                .feedback(video.getFeedback())
                .bookmark(video.getBookmark())
                .build();
    }
}
