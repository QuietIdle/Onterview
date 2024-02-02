package com.quiet.onterview.interview.dto.response;

import com.quiet.onterview.interview.entity.QuestionType;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class InterviewRoomDetailResponse {

    private Long interviewRoomId;
    private QuestionType questionType;
    private Integer runTime;
    private LocalDateTime createAt;
    private String feedback;
    private List<InterviewQuestionResponse> interviewQuestionResponseList;
    private VideoDetailResponse videoDetail;
}