package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;

import java.util.List;

public interface InterviewRoomService {

     List<InterviewRoomResponse> getInterviewRoomList(Long memberId);
     void createInterviewRoom(Long memberId, InterviewRoomRequest interviewRoomRequest);
     void deleteinterviewRoom(Long interviewRoomId);
}