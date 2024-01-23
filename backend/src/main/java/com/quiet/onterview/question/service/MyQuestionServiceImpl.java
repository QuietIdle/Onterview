package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.MyAnswerUpdateRequest;
import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.request.MyQuestionUpdateRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.exception.MyQuestionNotFoundException;
import com.quiet.onterview.question.mapper.MyQuestionMapper;
import com.quiet.onterview.question.repository.CommonQuestionRepository;
import com.quiet.onterview.question.repository.MyQuestionFolderRepository;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyQuestionServiceImpl implements MyQuestionService{

    private final MyQuestionFolderRepository myQuestionFolderRepository;
    private final MyQuestionRepository myQuestionRepository;
    private final CommonQuestionRepository commonQuestionRepository;
    private final MyQuestionMapper myQuestionMapper;

    @Override
    public MyAnswerAndVideoResponse getMyAnswerAndVideo(Long myQuestionId) {
        MyQuestion myQuestion = myQuestionRepository.findMyAnswerAndVideo(myQuestionId);
        return myQuestionMapper.myQuestionToMyAnswerAndVideoResponse(myQuestion);
    }

    @Override
    public void createMyQuestion(MyQuestionRequest myQuestionRequest) {
        MyQuestion myQuestion = myQuestionMapper.myQuestionRequestToEntity(myQuestionRequest);

        Optional<MyQuestionFolder> myQuesitonFolder = myQuestionFolderRepository.findById(myQuestionRequest.getMyQuestionFolderId());
        myQuesitonFolder.ifPresent(myQuestion::changeMyQuestionFolder);

        Optional.ofNullable(myQuestionRequest.getCommonQuestionId())
                .flatMap(commonQuestionRepository::findById)
                .ifPresent(myQuestion::saveCommonQuestion);

        myQuestionRepository.save(myQuestion);
    }

    @Override
    public void updateMyQuestion(Long myQuestionId, MyQuestionUpdateRequest myQuestionUpdateRequest) {
        MyQuestion myQuestion = myQuestionRepository.findById(myQuestionId)
                .orElseThrow(MyQuestionNotFoundException::new);
        Optional.ofNullable(myQuestionUpdateRequest.getQuestion())
                .ifPresent(myQuestion::updateMyQuestion);
    }

    @Override
    public void updateMyAnswer(Long myQuestionId, MyAnswerUpdateRequest myAnswerUpdateRequest) {
        MyQuestion myQuestion = myQuestionRepository.findById(myQuestionId)
                .orElseThrow(MyQuestionNotFoundException::new);
        Optional.ofNullable(myAnswerUpdateRequest.getAnswer())
                .ifPresent(myQuestion::updateMyAnswer);
    }
}
