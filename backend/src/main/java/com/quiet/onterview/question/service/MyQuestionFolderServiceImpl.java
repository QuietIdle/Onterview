package com.quiet.onterview.question.service;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.mapper.MyQuestionFolderMapper;
import com.quiet.onterview.question.repository.MyQuestionFolderRepository;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyQuestionFolderServiceImpl implements MyQuestionFolderService {

    private final MyQuestionFolderRepository myQuestionFolderRepository;
    private final MyQuestionFolderMapper myQuestionFolderMapper;


    public List<MyQuestionFolderResponse> getMyQuestionFolder(Long memberId) {
        List<MyQuestionFolder> myQuestionFolderList = myQuestionFolderRepository.findMyQuestionFolder(memberId);
        return myQuestionFolderList.stream()
                .map(myQuestionFolderMapper::myQuestionFolderToMyQuestionFolderResponse)
                .toList();
    }

    @Override
    public void createMyQuestionFolder(MyQuestionFolderRequest myQuestionFolderRequest) {
        MyQuestionFolder myQuestionFolder = myQuestionFolderMapper.myQuestionFolderRequestToEntity(myQuestionFolderRequest);
        myQuestionFolderRepository.save(myQuestionFolder);
    }

}