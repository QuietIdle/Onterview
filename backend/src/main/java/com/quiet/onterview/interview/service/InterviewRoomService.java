package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewRoomDetailResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterviewRoomService {

     Page<InterviewRoomResponse> getInterviewRoomList(Long memberId, Pageable pageable);
     Page<InterviewRoomResponse> getSingleInterviewRoomList(Long memberId, Pageable pageable);
     Page<InterviewRoomResponse> getMultiInterviewRoomList(Long memberId, Pageable pageable);
     InterviewRoomDetailResponse getInterviewRoomDetail(Long memberId, Long interviewRoomId);
     void createInterviewRoom(Long memberId, InterviewRoomRequest interviewRoomRequest);
     void deleteInterviewRoom(Long interviewRoomId);
}