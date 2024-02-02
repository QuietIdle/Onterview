package com.quiet.onterview.video.mapper;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.file.mapper.FileInformationMapper;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.mapper.MyQuestionMapper;
import com.quiet.onterview.video.dto.request.VideoInformationRequest;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.entity.Video.VideoBuilder;
import java.util.List;
import java.util.Optional;
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
                .interviewQuestionId(video.getInterviewQuestion().getInterviewQuestionId())
                .title(video.getTitle())
                .thumbnailUrl(
                        fileInformationMapper.fileInformationToResponse(video.getThumbnailUrl()))
                .feedback(video.getFeedback())
                .bookmark(video.getBookmark())
                .myQuestionId(video.getMyQuestion().getMyQuestionId())
                .videoUrl(fileInformationMapper.fileInformationToResponse(video.getVideoUrl()))
                .build();
    }

    public Video videoInformationToEntity(
            VideoInformationRequest videoInformationRequest,
            MyQuestion myQuestion,
            InterviewQuestion interviewQuestion) {
        return Video.builder().thumbnailUrl(fileInformationMapper.fileInformationRequestToEntity(
                        videoInformationRequest.getThumbnailInformation()))
                .videoUrl(fileInformationMapper.fileInformationRequestToEntity(
                        videoInformationRequest.getVideoInformation()))
                .title(videoInformationRequest.getTitle())
                .bookmark(Boolean.FALSE)
                .videoLength(videoInformationRequest.getVideoLength())
                .myQuestion(myQuestion)
                .interviewQuestion(interviewQuestion)
                .build();
    }

    public List<VideoInformationResponse> allVideoToInformationResponse(List<Video> videos) {
        return videos.stream().map(this::videoToInformationResponse).collect(Collectors.toList());
    }

    public VideoInformationResponse videoToInformationResponse(Video video) {
        return VideoInformationResponse.builder()
                .videoId(video.getVideoId())
                .interviewQuestionId(video.getInterviewQuestion().getInterviewQuestionId())
                .myQuestionId(video.getMyQuestion().getMyQuestionId())
                .title(video.getTitle())
                .thumbnailUrl(
                        fileInformationMapper.imageInformationToResponse(video.getThumbnailUrl()))
                .feedback(video.getFeedback())
                .bookmark(video.getBookmark())
                .build();
    }
}
