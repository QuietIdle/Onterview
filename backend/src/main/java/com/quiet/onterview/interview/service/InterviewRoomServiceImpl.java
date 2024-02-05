package com.quiet.onterview.interview.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewRoomDetailResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import com.quiet.onterview.interview.entity.InterviewRoom;
import com.quiet.onterview.interview.exception.InterviewRoomNotFoundException;
import com.quiet.onterview.interview.mapper.InterviewRoomMapper;
import com.quiet.onterview.interview.repository.InterviewRoomRepository;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.service.CommonQuestionFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final MemberRepository memberRepository;


    @Override
    public Page<InterviewRoomResponse> getInterviewRoomList(Long memberId, Pageable pageable) {
        return interviewRoomRepository.findInterviewRoom(memberId, pageable)
                .map(interviewRoomMapper::interviewRoomTointerviewRoomResponse);
    }

    @Override
    public Page<InterviewRoomResponse> getSingleInterviewRoomList(Long memberId, Pageable pageable) {
        return interviewRoomRepository.findSingleInterviewRoom(memberId, pageable)
                .map(interviewRoomMapper::interviewRoomTointerviewRoomResponse);
    }

    @Override
    public Page<InterviewRoomResponse> getMultiInterviewRoomList(Long memberId, Pageable pageable) {
        return interviewRoomRepository.findMultiInterviewRoom(memberId, pageable)
                .map(interviewRoomMapper::interviewRoomTointerviewRoomResponse);
    }

    @Override
    public InterviewRoomDetailResponse getInterviewRoomDetail(Long memberId, Long interviewRoomId) {

        InterviewRoom interviewRoom = interviewRoomRepository.findInterviewRoomDetail(memberId, interviewRoomId);
        return interviewRoomMapper.interviewRoomToInterviewRoomDetailResponse(interviewRoom);
    }

    @Override
    public void createInterviewRoom(Long memberId, InterviewRoomRequest interviewRoomRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(ErrorCode.MEMBERID_NOT_EXISTS));

        String commonQuestionFolderName = interviewRoomRequest.getQuestionType().getCommonQuestionFolder();
        int numToSelect = interviewRoomRequest.getNumToSelect();

        List<CommonQuestion> randomCommonQuestionList = commonQuestionFolderService.getRandomCommonQuestionList(commonQuestionFolderName, numToSelect);

        InterviewRoom interviewRoom = interviewRoomMapper.interviewRoomRequestToEntity(member, interviewRoomRequest);

        randomCommonQuestionList.forEach(randomCommonQuestion ->
                interviewRoom.addInterviewQuestion(
                        interviewQuestionService.createInterviewQuestion(randomCommonQuestion)));

        interviewRoomRepository.save(interviewRoom);
    }

    @Override
    public void deleteInterviewRoom(Long interviewRoomId) {
        interviewRoomRepository.delete(
                interviewRoomRepository.findById(interviewRoomId)
                        .orElseThrow(InterviewRoomNotFoundException::new));
    }
}