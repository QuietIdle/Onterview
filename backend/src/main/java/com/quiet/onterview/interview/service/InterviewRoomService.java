package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomDetailResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import com.quiet.onterview.video.dto.response.VideoStorageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InterviewRoomService {

     Page<InterviewRoomResponse> getInterviewRoomList(Long memberId, Pageable pageable);
     Page<InterviewRoomResponse> getSingleInterviewRoomList(Long memberId, Pageable pageable);
     Page<InterviewRoomResponse> getMultiInterviewRoomList(Long memberId, Pageable pageable);
     List<VideoStorageResponse> getSingleVideoList(Long memberId);
     List<VideoStorageResponse> getMultiVideoList(Long memberId);
     InterviewRoomDetailResponse getInterviewRoomDetail(Long memberId, Long interviewRoomId);
     List<InterviewQuestionCreateResponse> createInterviewRoom(Long memberId, InterviewRoomRequest interviewRoomRequest);
     void deleteInterviewRoom(Long interviewRoomId);
}