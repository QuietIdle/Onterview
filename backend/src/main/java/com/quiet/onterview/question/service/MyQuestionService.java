package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;

public interface MyQuestionService {

    MyAnswerAndVideoResponse getMyAnswerAndVideo(Long myQuestionId);
}