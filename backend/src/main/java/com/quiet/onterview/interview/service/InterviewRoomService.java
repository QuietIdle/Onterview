package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;

public interface InterviewRoomService {

     void createInterviewRoom(Long MemberId, InterviewRoomRequest interviewRoomRequest);
}