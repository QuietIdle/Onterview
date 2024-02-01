package com.quiet.onterview.interview.mapper;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import com.quiet.onterview.interview.entity.InterviewRoom;
import com.quiet.onterview.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InterviewRoomMapper {

    public InterviewRoom interviewRoomRequestToEntity(Member member, InterviewRoomRequest interviewRoomRequest) {
        return InterviewRoom.builder()
                .member(member)
                .interviewQuestionList(new ArrayList<>())
                .roomType(interviewRoomRequest.getRoomType())
                .build();

    }

    public InterviewRoomResponse interviewRoomTointerviewRoomResponse(InterviewRoom interviewRoom) {
        return InterviewRoomResponse.builder()
                .interviewRoomId(interviewRoom.getInterviewRoomId())
                .runTime(interviewRoom.getRunTime())
                .roomType(interviewRoom.getRoomType())
                .createAt(interviewRoom.getCreateAt())
                .numOfQuestion(interviewRoom.getInterviewQuestionList().size())
                .build();
    }
}