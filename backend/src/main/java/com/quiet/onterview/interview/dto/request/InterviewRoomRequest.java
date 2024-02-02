package com.quiet.onterview.interview.dto.request;

import com.quiet.onterview.interview.entity.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRoomRequest {

    private QuestionType questionType;
    private int numToSelect = 5;
}
