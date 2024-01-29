package com.quiet.onterview.question.mapper;

import com.quiet.onterview.question.dto.request.CommonQuestionRequest;
import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.video.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommonQuestionMapper {

    public CommonQuestion commonQuestionRequestToEntity(CommonQuestionRequest commonQuestionRequest) {
        return CommonQuestion.builder()
                .commonQuestion(commonQuestionRequest.getCommonQuestion())
                .build();

    }
}