package com.quiet.onterview.question.mapper;

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
public class MyQuestionMapper {

    private final VideoMapper videoMapper;

    public MyQuestionResponse myQuestionToMyQuestionResponse(MyQuestion myQuestion) {
        return MyQuestionResponse.builder()
                .commonQuestionId(Optional.ofNullable(myQuestion.getCommonQuestion())
                        .map(CommonQuestion::getCommonQuestionId)
                        .orElse(null))
                .myQuestionId(myQuestion.getMyQuestionId())
                .question(myQuestion.getQuestion())
                .build();
    }

    public MyAnswerAndVideoResponse myQuestionToMyAnswerAndVideoResponse(MyQuestion myQuestion) {
        return MyAnswerAndVideoResponse.builder()
                .myQuestionId(myQuestion.getMyQuestionId())
                .question(myQuestion.getQuestion())
                .answer(myQuestion.getAnswer())
                .videoInformationResponseList(videoMapper.allVideoToInformationResponse(myQuestion.getVideoList()))
                .build();
    }
}