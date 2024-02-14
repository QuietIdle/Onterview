package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomDetailResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InterviewRoomService {
     Page<InterviewRoomResponse> getSingleInterviewRoomList(Long memberId, Pageable pageable);
     Page<InterviewRoomResponse> getMultiInterviewRoomList(Long memberId, Pageable pageable);
     InterviewRoomDetailResponse getInterviewRoomDetail(Long memberId, Long interviewRoomId);
     List<List<InterviewQuestionCreateResponse>> createInterviewRoom(InterviewRoomRequest interviewRoomRequest);
     void deleteInterviewRoom(Long memberId, Long interviewRoomId);
}