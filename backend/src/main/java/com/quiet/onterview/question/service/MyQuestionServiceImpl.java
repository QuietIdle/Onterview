package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.mapper.MyQuestionMapper;
import com.quiet.onterview.question.repository.CommonQuestionRepository;
import com.quiet.onterview.question.repository.MyQuestionFolderRepository;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

        Optional<CommonQuestion> commonQuestion = commonQuestionRepository.findById(myQuestionRequest.getCommonQuestionId());
        commonQuestion.ifPresent(myQuestion::saveCommonQuestion);

        myQuestionRepository.save(myQuestion);
    }
}
