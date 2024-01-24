package com.quiet.onterview.question.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyQuestionFolderResponse {

    Long myQuestionFolderId;
    String myQuestionFolder;
    List<MyQuestionResponse> myQuestionList;
}
