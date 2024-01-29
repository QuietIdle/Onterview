package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;

public interface CommonQuestionFolderService {

    void createCommonQuestionFolder(CommonQuestionFolderRequest commonQuestionFolderRequest);
    void updateCommonQuestionFolder(Long commonQuestionFolderId, CommonQuestionFolderRequest commonQuestionFolderRequest);
    void deleteCommonQuestionFolder(Long commonQuestionFolderId);
}