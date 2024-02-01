package com.quiet.onterview.interview.dto.response;

import com.quiet.onterview.interview.entity.RoomType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class InterviewRoomResponse {

    private Long interviewRoomId;
    private RoomType roomType;
    private Integer numOfQuestion;
    private Integer runTime;
    private LocalDateTime createAt;
}