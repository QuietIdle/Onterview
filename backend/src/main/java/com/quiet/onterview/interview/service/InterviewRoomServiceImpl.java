package com.quiet.onterview.interview.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomDetailResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.InterviewRoom;
import com.quiet.onterview.interview.entity.Interviewee;
import com.quiet.onterview.interview.exception.InterviewRoomNotFoundException;
import com.quiet.onterview.interview.mapper.InterviewRoomMapper;
import com.quiet.onterview.interview.repository.InterviewRoomRepository;
import com.quiet.onterview.interview.repository.IntervieweeRepository;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.mapper.CommonQuestionMapper;
import com.quiet.onterview.question.service.CommonQuestionFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewRoomServiceImpl implements InterviewRoomService {

    private final CommonQuestionFolderService commonQuestionFolderService;
    private final InterviewQuestionService interviewQuestionService;
    private final IntervieweeService intervieweeService;
    private final InterviewRoomRepository interviewRoomRepository;
    private final IntervieweeRepository intervieweeRepository;
    private final MemberRepository memberRepository;
    private final InterviewRoomMapper interviewRoomMapper;
    private final CommonQuestionMapper commonQuestionMapper ;

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
        InterviewRoom interviewRoom = interviewRoomRepository.findById(interviewRoomId).orElseThrow(InterviewRoomNotFoundException::new);
        List<Interviewee> intervieweeList = interviewRoom.getIntervieweeList();

        Interviewee foundInterviewee = intervieweeList.stream()
                .filter(interviewee -> interviewee.getMember().getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(InterviewRoomNotFoundException::new);

        List<InterviewQuestion> interviewQuestionList = foundInterviewee.getInterviewQuestionList();

        return interviewRoomMapper.interviewRoomToInterviewRoomDetailResponse(interviewQuestionList, interviewRoom);
    }

    @Override
    public List<List<InterviewQuestionCreateResponse>> createInterviewRoom(InterviewRoomRequest interviewRoomRequest) {

        InterviewRoom interviewRoom = interviewRoomMapper.interviewRoomRequestToEntity(interviewRoomRequest);

        String commonQuestionFolderName = interviewRoomRequest.getQuestionType().getCommonQuestionFolder();
        int numToSelect = interviewRoomRequest.getNumToSelect();
        List<CommonQuestion> randomCommonQuestionList = commonQuestionFolderService.getRandomCommonQuestionList(commonQuestionFolderName, numToSelect);

        List<Interviewee> interviewees = new ArrayList<>();

        for (Long memberId : interviewRoomRequest.getMemberIdList()) {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(ErrorCode.MEMBERID_NOT_EXISTS));
            Interviewee interviewee = intervieweeService.createInterviewee(member);
            interviewRoom.addInterviewee(interviewee);
            interviewees.add(interviewee);

            for (CommonQuestion randomCommonQuestion : randomCommonQuestionList) {
                InterviewQuestion interviewQuestion = interviewQuestionService.createInterviewQuestion(interviewee, randomCommonQuestion);
                interviewee.addInterviewQuestion(interviewQuestion);
            }
        }

        interviewRoomRepository.save(interviewRoom);

        return interviewees.stream()
                .map(interviewee -> interviewee.getInterviewQuestionList().stream()
                        .map(interviewQuestion -> InterviewQuestionCreateResponse
                                .builder()
                                .interviewQuestionId(interviewQuestion.getInterviewQuestionId())
                                .commonQuestionId(interviewQuestion.getCommonQuestion().getCommonQuestionId())
                                .commonQuestion(interviewQuestion.getCommonQuestion().getCommonQuestion())
                                .build())
                        .toList()
                ).toList();
    }

    @Override
    public void deleteInterviewRoom(Long memberId, Long interviewRoomId) {
        InterviewRoom interviewRoom = interviewRoomRepository.findById(interviewRoomId)
                .orElseThrow(InterviewRoomNotFoundException::new);
        List<Interviewee> intervieweeList = interviewRoom.getIntervieweeList();

        Interviewee user = intervieweeList.stream()
                .filter(interviewee -> interviewee.getMember().getMemberId().equals(memberId)).findAny()
                .orElseThrow(InterviewRoomNotFoundException::new);

        intervieweeList.remove(user);
        if (intervieweeList.isEmpty()) {
            interviewRoomRepository.delete(interviewRoom);
        }
    }

}