package com.quiet.onterview.interview.dto.request;

import com.quiet.onterview.interview.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRoomRequest {

    private RoomType roomType;
    private int numToSelect = 5;
}
