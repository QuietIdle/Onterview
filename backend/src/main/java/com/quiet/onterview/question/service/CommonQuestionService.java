package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionRequest;
import com.quiet.onterview.question.dto.request.CommonQuestionUpdateRequest;

public interface CommonQuestionService {

    void createCommonQuestion(CommonQuestionRequest commonQuestionRequest);
    void updateCommonQuestion(Long commonQuestionId, CommonQuestionUpdateRequest commonQuestionUpdateRequest);
    void deleteCommonQuestion(Long commonQuestionId);
}