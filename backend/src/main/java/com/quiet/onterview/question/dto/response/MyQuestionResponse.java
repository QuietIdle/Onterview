package com.quiet.onterview.question.dto.response;

import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyQuestionResponse {

    Long commonQuestionId;
    Long myQuestionId;
    String question;
}
