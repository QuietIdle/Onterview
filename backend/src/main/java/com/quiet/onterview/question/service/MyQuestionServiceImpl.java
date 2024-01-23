package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.mapper.MyQuestionMapper;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyQuestionServiceImpl implements MyQuestionService{

    private final MyQuestionRepository myQuestionRepository;
    private final MyQuestionMapper myQuestionMapper;

    @Override
    public MyAnswerAndVideoResponse getMyAnswerAndVideo(Long myQuestionId) {
        MyQuestion myQuestion = myQuestionRepository.findMyAnswerAndVideo(myQuestionId);
        return myQuestionMapper.myQuestionToMyAnswerAndVideoResponse(myQuestion);
    }
}
