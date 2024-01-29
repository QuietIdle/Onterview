package com.quiet.onterview.question.mapper;

import com.quiet.onterview.question.dto.request.CommonQuestionRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import org.springframework.stereotype.Component;

@Component
public class CommonQuestionMapper {

    public CommonQuestionResponse commonQuestionToCommonQuestionResponse(CommonQuestion commonQuestion) {
        return CommonQuestionResponse.builder()
                .commonQuestionId(commonQuestion.getCommonQuestionId())
                .CommonQuestion(commonQuestion.getCommonQuestion())
                .build();
    }

    public CommonQuestion commonQuestionRequestToEntity(CommonQuestionRequest commonQuestionRequest) {
        return CommonQuestion.builder()
                .commonQuestion(commonQuestionRequest.getCommonQuestion())
                .build();

    }
}