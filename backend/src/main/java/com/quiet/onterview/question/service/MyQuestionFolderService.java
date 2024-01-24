package com.quiet.onterview.question.service;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;

import java.util.List;

public interface MyQuestionFolderService {

    List<MyQuestionFolderResponse> getMyQuestionFolder(Long memberId);
    void createMyQuestionFolder(MyQuestionFolderRequest myQuestionFolderRequest);
    List<MyQuestionFolderResponse> getMyQuestionFolder(Member member);
    void updateMyQuestionFolder(Long myQuestionFolderId, MyQuestionFolderRequest myQuestionFolderRequest);
    void deleteMyQuestionFolder(Long myQuestionFolderId);
}