package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionFolderResponse;

import java.util.List;

public interface CommonQuestionFolderService {

    List<CommonQuestionFolderResponse> getCommonQuestionFolder();
    void createCommonQuestionFolder(CommonQuestionFolderRequest commonQuestionFolderRequest);
    void updateCommonQuestionFolder(Long commonQuestionFolderId, CommonQuestionFolderRequest commonQuestionFolderRequest);
    void deleteCommonQuestionFolder(Long commonQuestionFolderId);
}