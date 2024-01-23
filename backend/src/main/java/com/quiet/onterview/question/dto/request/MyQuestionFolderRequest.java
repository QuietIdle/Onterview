package com.quiet.onterview.question.dto.request;

import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import lombok.Getter;

@Getter
public class MyQuestionFolderRequest {

    Long myQuestionFolderId;
    Long memberId;
    String folder;
}
