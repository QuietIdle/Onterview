package com.quiet.onterview.question.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MyQuestionResponse {

    Long commonQuestionId;
    Long myQuestionId;
    String question;
}
