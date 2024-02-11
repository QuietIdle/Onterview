package com.quiet.onterview.interview.mapper;

import com.quiet.onterview.interview.dto.response.InterviewQuestionResponse;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InterviewQuestionMapper {

    private final VideoMapper videoMapper;

    public InterviewQuestionResponse interviewQuestionToInterviewQuestionResponse(
            InterviewQuestion interviewQuestion) {
        Video video = interviewQuestion.getVideo();

        InterviewQuestionResponse.InterviewQuestionResponseBuilder responseBuilder =
                InterviewQuestionResponse.builder()
                        .interviewQuestionId(interviewQuestion.getInterviewQuestionId())
                        .commonQuestionId(
                                interviewQuestion.getCommonQuestion().getCommonQuestionId())
                        .commonQuestion(interviewQuestion.getCommonQuestion().getCommonQuestion());

        Optional.ofNullable(video)
                .ifPresent(o -> responseBuilder.videoInformation(
                        videoMapper.videoToInformationResponse(o))
                );
        VideoInformationResponse videoInformationResponse = videoMapper.videoToInformationResponse(video);

        return InterviewQuestionResponse.builder()
                .interviewQuestionId(interviewQuestion.getInterviewQuestionId())
                .commonQuestionId(interviewQuestion.getCommonQuestion().getCommonQuestionId())
                .commonQuestion(interviewQuestion.getCommonQuestion().getCommonQuestion())
                .videoInformation(videoInformationResponse)
                .build();
    }
}