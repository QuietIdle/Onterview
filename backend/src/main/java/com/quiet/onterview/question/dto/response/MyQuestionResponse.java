package com.quiet.onterview.question.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyQuestionResponse {

    Long commonQuestionId;
    Long myQuestionId;
    String question;
}
