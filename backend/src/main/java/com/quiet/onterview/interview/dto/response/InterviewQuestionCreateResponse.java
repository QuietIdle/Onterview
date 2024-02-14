package com.quiet.onterview.interview.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InterviewQuestionCreateResponse {

    private Long interviewQuestionId;
    private Long commonQuestionId;
    private String commonQuestion;
}