package com.quiet.onterview.question.service;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.exception.MyQuestionFolderNotFoundException;
import com.quiet.onterview.question.mapper.MyQuestionFolderMapper;
import com.quiet.onterview.question.repository.MyQuestionFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyQuestionFolderServiceImpl implements MyQuestionFolderService {

    private final MyQuestionFolderRepository myQuestionFolderRepository;
    private final MyQuestionFolderMapper myQuestionFolderMapper;


    @Override
    public List<MyQuestionFolderResponse> getMyQuestionFolder(Member member) {
        return null;
    }

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

    @Override
    public void updateMyQuestionFolder(Long myQuestionFolderId, MyQuestionFolderRequest myQuestionFolderRequest) {
        MyQuestionFolder myQuestionFolder = myQuestionFolderRepository.findById(myQuestionFolderId)
                .orElseThrow(MyQuestionFolderNotFoundException::new);
        Optional.ofNullable(myQuestionFolderRequest.getMyQuestionFolder())
                .ifPresent(myQuestionFolder::updateMyQuestionFolder);
    }

    @Override
    public void deleteMyQuestionFolder(Long myQuestionFolderId) {
        myQuestionFolderRepository.delete(
                myQuestionFolderRepository.findById(myQuestionFolderId)
                        .orElseThrow(MyQuestionFolderNotFoundException::new));
    }

}