package com.quiet.onterview.question.dto.request;

import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import lombok.Getter;

@Getter
public class MyQuestionRequest {

    Long myQuestionFolderId;
    String question;
    String answer;
    Long commonQuestionId;
}
