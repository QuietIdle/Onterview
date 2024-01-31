package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.entity.InterviewRoom;
import com.quiet.onterview.interview.mapper.InterviewRoomMapper;
import com.quiet.onterview.interview.repository.InterviewRoomRepository;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.service.CommonQuestionFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewRoomServiceImpl implements InterviewRoomService {

    private final CommonQuestionFolderService commonQuestionFolderService;
    private final InterviewQuestionService interviewQuestionService;
    private final InterviewRoomRepository interviewRoomRepository;
    private final InterviewRoomMapper interviewRoomMapper;

    @Override
    public void createInterviewRoom(InterviewRoomRequest interviewRoomRequest) {
        String commonQuestionFolderName = interviewRoomRequest.getRoomType().getCommonQuestionFolder();
        int numToSelect = interviewRoomRequest.getNumToSelect();

        List<CommonQuestion> randomCommonQuestionList = commonQuestionFolderService.getRandomCommonQuestionList(commonQuestionFolderName, numToSelect);

        InterviewRoom interviewRoom = interviewRoomMapper.interviewRoomRequestToEntity(interviewRoomRequest);

        randomCommonQuestionList.forEach(randomCommonQuestion ->
                interviewRoom.addInterviewQuestion(
                        interviewQuestionService.createInterviewQuestion(randomCommonQuestion)));

        interviewRoomRepository.save(interviewRoom);
    }
}